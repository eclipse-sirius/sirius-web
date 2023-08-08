/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { useSubscription } from '@apollo/client';
import { RepresentationComponentProps, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { Gantt } from '../Gantt';
import { getTaskFromGQLTask } from '../helper/helper';
import {
  GQLErrorPayload,
  GQLGanttEventPayload,
  GQLGanttEventSubscription,
  GQLGanttRefreshedEventPayload,
} from './GanttRepresentation.types';
import {
  CompleteEvent,
  GanttRefreshedEvent,
  GanttRepresentationContext,
  GanttRepresentationEvent,
  SchemaValue,
  SwitchRepresentationEvent,
  ganttRepresentationMachine,
} from './GanttRepresentationMachine';
import { ganttEventSubscription } from './operations';

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
 * Connect the Gantt component to the GraphQL API over Web Socket.
 */
export const GanttRepresentation = ({
  editingContextId,
  representationId,
  setSelection,
}: RepresentationComponentProps) => {
  const classes = useGanttRepresentationStyles();

  const { addErrorMessage, addMessages } = useMultiToast();

  const [{ value, context }, dispatch] = useMachine<GanttRepresentationContext, GanttRepresentationEvent>(
    ganttRepresentationMachine,
    {
      context: {
        displayedGanttId: representationId,
      },
    }
  );
  const { ganttRepresentation } = value as SchemaValue;
  const { id, displayedGanttId, gantt } = context;
  /**
   * Displays an other gantt if the selection indicates that we should display another gantt.
   */
  useEffect(() => {
    if (displayedGanttId !== representationId) {
      const switchGanttEvent: SwitchRepresentationEvent = { type: 'SWITCH_REPRESENTATION', representationId };
      dispatch(switchGanttEvent);
    }
  }, [representationId, displayedGanttId, dispatch]);

  const { error } = useSubscription<GQLGanttEventSubscription>(ganttEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        ganttId: representationId,
      },
    },
    fetchPolicy: 'no-cache',
    onSubscriptionData: ({ subscriptionData }) => {
      if (subscriptionData?.data) {
        const { ganttEvent } = subscriptionData.data;
        if (isGanttRefreshedEventPayload(ganttEvent)) {
          const ganttRefreshedEvent: GanttRefreshedEvent = {
            type: 'HANDLE_GANTT_REFRESHED',
            gantt: ganttEvent.gantt,
          };
          dispatch(ganttRefreshedEvent);
        } else if (isErrorPayload(ganttEvent)) {
          addMessages(ganttEvent.messages);
        }
      }
    },
    onSubscriptionComplete: () => {
      const completeEvent: CompleteEvent = { type: 'HANDLE_COMPLETE' };
      dispatch(completeEvent);
    },
  });

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error, dispatch]);

  const onTaskChange = () => {};
  const onTaskDelete = () => {};
  const onExpandCollapse = () => {};

  let content: JSX.Element | null = null;
  if (ganttRepresentation === 'ready') {
    const tasks = getTaskFromGQLTask(gantt.tasks, '');
    content = (
      <Gantt
        tasks={tasks}
        setSelection={setSelection}
        onSelect={onselect}
        onTaskChange={onTaskChange}
        onTaskDelete={onTaskDelete}
        onExpandCollapse={onExpandCollapse}
      />
    );
  } else if (ganttRepresentation === 'complete') {
    content = (
      <div className={classes.complete}>
        <Typography variant="h5" align="center">
          The Gantt does not exist anymore
        </Typography>
      </div>
    );
  }

  return <>{content}</>;
};
