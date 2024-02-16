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
import { Task, TaskOrEmpty } from '@ObeoNetwork/gantt-task-react';
import { useSubscription } from '@apollo/client';
import { RepresentationComponentProps, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { useGanttMutations } from '../graphql/mutation/useGanttMutations';
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
  const { deleteTask, editTask, createTask, dropTask } = useGanttMutations(editingContextId, representationId);

  const handleEditTask = (task: TaskOrEmpty) => {
    const newDetail: GQLTaskDetail = {
      name: task.name,
      description: '',
      startTime: (task as Task)?.start?.toISOString(),
      endTime: (task as Task)?.end?.toISOString(),
      progress: (task as Task)?.progress,
      computeStartEndDynamically: task.isDisabled,
    };

    // to avoid blink because useMutation implies a re-render as the task value is the old one
    updateTask(gantt, task.id, newDetail);
    editTask(task);
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
        onCreateTask={createTask}
        onEditTask={handleEditTask}
        onDeleteTask={deleteTask}
        onExpandCollapse={onExpandCollapse}
        onDropTask={dropTask}
      />
    );
  }
  return <>{content}</>;
};
