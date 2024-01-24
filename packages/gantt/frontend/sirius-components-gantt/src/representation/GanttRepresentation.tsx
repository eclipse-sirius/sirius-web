/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
import { Task } from '@ObeoNetwork/gantt-task-react';
import { ApolloError, useMutation, useSubscription } from '@apollo/client';
import { RepresentationComponentProps, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useCallback, useEffect, useState } from 'react';
import {
  GQLCreateGanttTaskInput,
  GQLCreateTaskData,
  GQLCreateTaskVariables,
  GQLDeleteGanttTaskInput,
  GQLDeleteTaskData,
  GQLDeleteTaskVariables,
  GQLEditGanttTaskInput,
  GQLEditTaskData,
  GQLEditTaskVariables,
} from '../graphql/mutation/GanttMutation.types';
import { createTaskMutation, deleteTaskMutation, editTaskMutation } from '../graphql/mutation/ganttMutation';
import {
  GQLErrorPayload,
  GQLGanttEventPayload,
  GQLGanttEventSubscription,
  GQLGanttRefreshedEventPayload,
  GQLTaskDetail,
} from '../graphql/subscription/GanttSubscription.types';
import { ganttEventSubscription } from '../graphql/subscription/ganttSubscription';
import { getTaskFromGQLTask, updateTask } from '../helper/helper';
import { Gantt } from './Gantt';
import { GanttRepresentationState } from './GanttRepresentation.types';
const useGanttRepresentationStyles = makeStyles((theme) => ({
  page: {
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    overflowY: 'scroll',
  },
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'flex-end',
    alignItems: 'center',
    maxHeight: theme.spacing(4),
    textTransform: 'none',
  },
  toolbarAction: {
    paddingRight: theme.spacing(1),
    whiteSpace: 'nowrap',
  },
}));

const isGanttRefreshedEventPayload = (payload: GQLGanttEventPayload): payload is GQLGanttRefreshedEventPayload =>
  payload.__typename === 'GanttRefreshedEventPayload';
const isErrorPayload = (payload: GQLGanttEventPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

/**
 * Connect the Gantt component to the GraphQL API.
 */
export const GanttRepresentation = ({ editingContextId, representationId }: RepresentationComponentProps) => {
  const classes = useGanttRepresentationStyles();

  const { addErrorMessage, addMessages } = useMultiToast();

  const { setSelection } = useSelection();

  const [{ id, gantt, complete }, setState] = useState<GanttRepresentationState>({
    id: crypto.randomUUID(),
    gantt: null,
    complete: false,
  });

  const { error } = useSubscription<GQLGanttEventSubscription>(ganttEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        ganttId: representationId,
      },
    },
    fetchPolicy: 'no-cache',
    onData: ({ data: subscriptionData }) => {
      if (subscriptionData?.data) {
        const { ganttEvent } = subscriptionData.data;
        if (isGanttRefreshedEventPayload(ganttEvent)) {
          setState((previousState) => {
            return { ...previousState, gantt: ganttEvent.gantt };
          });
        } else if (isErrorPayload(ganttEvent)) {
          addMessages(ganttEvent.messages);
        }
      }
    },
    onComplete: () => {
      setState((previousState) => {
        return { ...previousState, complete: true, gantt: null };
      });
    },
  });

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  //---------------------------------
  // Mutations
  const [deleteGanttTask, { loading: deleteGanttTaskLoading, data: deleteGanttTaskData, error: deleteGanttTaskError }] =
    useMutation<GQLDeleteTaskData, GQLDeleteTaskVariables>(deleteTaskMutation);
  const [editGanttTask, { loading: editTaskLoading, data: editTaskData, error: editTaskError }] = useMutation<
    GQLEditTaskData,
    GQLEditTaskVariables
  >(editTaskMutation);
  const [createTask, { loading: createTaskLoading, data: createTaskData, error: createTaskError }] = useMutation<
    GQLCreateTaskData,
    GQLCreateTaskVariables
  >(createTaskMutation);

  const isStandardErrorPayload = (field): field is GQLErrorPayload => field.__typename === 'ErrorPayload';
  const handleError = useCallback(
    (loading: boolean, data, error: ApolloError | undefined) => {
      if (!loading) {
        if (error) {
          addErrorMessage(error.message);
        }
        if (data) {
          const keys = Object.keys(data);
          if (keys.length > 0) {
            const firstKey = keys[0];
            if (firstKey) {
              const firstField = data[firstKey];
              if (isStandardErrorPayload(firstField)) {
                const { messages } = firstField;
                addMessages(messages);
              }
            }
          }
        }
      }
    },
    [addErrorMessage, addMessages]
  );

  useEffect(() => {
    handleError(deleteGanttTaskLoading, deleteGanttTaskData, deleteGanttTaskError);
  }, [deleteGanttTaskLoading, deleteGanttTaskData, deleteGanttTaskError, handleError]);
  useEffect(() => {
    handleError(editTaskLoading, editTaskData, editTaskError);
  }, [editTaskLoading, editTaskData, editTaskError, handleError]);
  useEffect(() => {
    handleError(createTaskLoading, createTaskData, createTaskError);
  }, [createTaskLoading, createTaskData, createTaskError, handleError]);

  const handleEditTask = (task: Task) => {
    const newDetail: GQLTaskDetail = {
      name: task.name,
      description: '',
      startTime: task.start?.toISOString(),
      endTime: task.end?.toISOString(),
      progress: task.progress,
      computeStartEndDynamically: task.isDisabled,
    };
    const input: GQLEditGanttTaskInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      taskId: task.id,
      newDetail,
    };

    // to avoid blink because useMutation implies a re-render as the task value is the old one
    updateTask(gantt, task.id, newDetail);
    editGanttTask({ variables: { input } });
  };
  const handleDeleteTask = (task: Task) => {
    const input: GQLDeleteGanttTaskInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      taskId: task.id,
    };
    deleteGanttTask({ variables: { input } });
  };
  const handleCreateTask = (task: Task) => {
    const input: GQLCreateGanttTaskInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      currentTaskId: task.id,
    };
    createTask({ variables: { input } });
  };
  const onExpandCollapse = () => {};

  let content: JSX.Element | null = null;
  if (complete) {
    content = (
      <div className={classes.complete}>
        <Typography variant="h5" align="center">
          The Gantt does not exist anymore
        </Typography>
      </div>
    );
  } else if (gantt) {
    const tasks = getTaskFromGQLTask(gantt.tasks, '');
    content = (
      <Gantt
        editingContextId={editingContextId}
        representationId={representationId}
        tasks={tasks}
        setSelection={setSelection}
        onCreateTask={handleCreateTask}
        onEditTask={handleEditTask}
        onDeleteTask={handleDeleteTask}
        onExpandCollapse={onExpandCollapse}
      />
    );
  }
  return <>{content}</>;
};
