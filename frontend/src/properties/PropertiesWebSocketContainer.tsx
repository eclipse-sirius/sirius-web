/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import makeStyles from '@material-ui/core/styles/makeStyles';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import {
  formRefreshedEventPayloadFragment,
  subscribersUpdatedEventPayloadFragment,
  widgetSubscriptionsUpdatedEventPayloadFragment,
} from 'form/FormEventFragments';
import {
  GQLPropertiesEventInput,
  GQLPropertiesEventSubscription,
  GQLPropertiesEventVariables,
} from 'form/FormEventFragments.types';
import gql from 'graphql-tag';
import { Properties } from 'properties/Properties';
import {
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  PropertiesWebSocketContainerContext,
  PropertiesWebSocketContainerEvent,
  propertiesWebSocketContainerMachine,
  SchemaValue,
  ShowToastEvent,
  SwitchSelectionEvent,
} from 'properties/PropertiesWebSocketContainerMachine';
import React, { useEffect } from 'react';
import { WorkbenchViewComponentProps } from 'workbench/Workbench.types';

export const propertiesEventSubscription = gql`
  subscription propertiesEvent($input: PropertiesEventInput!) {
    propertiesEvent(input: $input) {
      __typename
      ... on SubscribersUpdatedEventPayload {
        ...subscribersUpdatedEventPayloadFragment
      }
      ... on WidgetSubscriptionsUpdatedEventPayload {
        ...widgetSubscriptionsUpdatedEventPayloadFragment
      }
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${subscribersUpdatedEventPayloadFragment}
  ${widgetSubscriptionsUpdatedEventPayloadFragment}
  ${formRefreshedEventPayloadFragment}
`;

const usePropertiesWebSocketContainerStyles = makeStyles((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
}));

/**
 * Connect the Properties component to the GraphQL API over Web Socket.
 */
export const PropertiesWebSocketContainer = ({
  editingContextId,
  selection,
  setSelection,
  readOnly,
}: WorkbenchViewComponentProps) => {
  const classes = usePropertiesWebSocketContainerStyles();
  const [{ value, context }, dispatch] = useMachine<
    PropertiesWebSocketContainerContext,
    PropertiesWebSocketContainerEvent
  >(propertiesWebSocketContainerMachine);
  const { toast, propertiesWebSocketContainer } = value as SchemaValue;
  const { id, currentSelection, form, widgetSubscriptions, message } = context;

  /**
   * Displays an other form if the selection indicates that we should display another properties view.
   */
  useEffect(() => {
    if (selection.entries.length > 0 && selection !== currentSelection) {
      const switchSelectionEvent: SwitchSelectionEvent = {
        type: 'SWITCH_SELECTION',
        selection: selection,
      };
      dispatch(switchSelectionEvent);
    } else if (selection.entries.length === 0) {
      const switchSelectionEvent: SwitchSelectionEvent = {
        type: 'SWITCH_SELECTION',
        selection: null,
      };
      dispatch(switchSelectionEvent);
    }
  }, [currentSelection, selection, dispatch]);

  const input: GQLPropertiesEventInput = {
    id,
    editingContextId,
    objectIds: currentSelection?.entries.map((entry) => entry.id),
  };
  const variables: GQLPropertiesEventVariables = { input };

  const { error } = useSubscription<GQLPropertiesEventSubscription, GQLPropertiesEventVariables>(
    propertiesEventSubscription,
    {
      variables,
      fetchPolicy: 'no-cache',
      skip: propertiesWebSocketContainer === 'empty' || propertiesWebSocketContainer === 'unsupportedSelection',
      onSubscriptionData: ({ subscriptionData }) => {
        const handleDataEvent: HandleSubscriptionResultEvent = {
          type: 'HANDLE_SUBSCRIPTION_RESULT',
          result: subscriptionData,
        };
        dispatch(handleDataEvent);
      },
      onSubscriptionComplete: () => {
        const completeEvent: HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
        dispatch(completeEvent);
      },
    }
  );

  useEffect(() => {
    if (error) {
      const { message } = error;
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  }, [error, dispatch]);

  let content: JSX.Element | null = null;
  if (
    propertiesWebSocketContainer === 'empty' ||
    propertiesWebSocketContainer === 'unsupportedSelection' ||
    propertiesWebSocketContainer === 'complete'
  ) {
    content = (
      <div className={classes.idle}>
        <Typography variant="subtitle2">No object selected</Typography>
      </div>
    );
  }
  if ((propertiesWebSocketContainer === 'idle' && form) || propertiesWebSocketContainer === 'ready') {
    content = (
      <Properties
        editingContextId={editingContextId}
        form={form}
        widgetSubscriptions={widgetSubscriptions}
        setSelection={setSelection}
        readOnly={readOnly}
      />
    );
  }
  return (
    <>
      {content}
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
