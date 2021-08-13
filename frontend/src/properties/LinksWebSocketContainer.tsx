/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { GQLLinksEventSubscription } from 'form/FormEventFragments.types';
import gql from 'graphql-tag';
import {
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  LinksWebSocketContainerContext,
  LinksWebSocketContainerEvent,
  linksWebSocketContainerMachine,
  SchemaValue,
  ShowToastEvent,
  SwitchSelectionEvent,
} from 'properties/LinksWebSocketContainerMachine';
import { Properties } from 'properties/Properties';
import React, { useContext, useEffect } from 'react';
import { RepresentationContext } from 'workbench/RepresentationContext';
import { LinksWebSocketContainerProps } from './LinksWebSocketContainer.types';

const linksEventSubscription = gql`
  subscription linksEvent($input: LinksEventInput!) {
    linksEvent(input: $input) {
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
export const LinksWebSocketContainer = ({ editingContextId, selection }: LinksWebSocketContainerProps) => {
  const classes = usePropertiesWebSocketContainerStyles();
  const [{ value, context }, dispatch] = useMachine<LinksWebSocketContainerContext, LinksWebSocketContainerEvent>(
    linksWebSocketContainerMachine
  );
  const { toast, linksWebSocketContainer } = value as SchemaValue;
  const { id, currentSelection, form, subscribers, widgetSubscriptions, message } = context;
  const { registry } = useContext(RepresentationContext);

  /**
   * Displays an other form if the selection indicates that we should display another properties view.
   */
  useEffect(() => {
    if (currentSelection?.id !== selection?.id) {
      const isRepresentation = registry.isRepresentation(selection.kind);
      const switchSelectionEvent: SwitchSelectionEvent = { type: 'SWITCH_SELECTION', selection, isRepresentation };
      dispatch(switchSelectionEvent);
    }
  }, [currentSelection, registry, selection, dispatch]);

  const { error } = useSubscription<GQLLinksEventSubscription>(linksEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        objectId: currentSelection?.id,
      },
    },
    fetchPolicy: 'no-cache',
    skip: linksWebSocketContainer === 'empty' || linksWebSocketContainer === 'unsupportedSelection',
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
  });

  useEffect(() => {
    if (error) {
      const { message } = error;
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  }, [error, dispatch]);

  let content = null;
  if (!selection || linksWebSocketContainer === 'unsupportedSelection') {
    content = (
      <div className={classes.idle}>
        <Typography variant="subtitle2">No object selected</Typography>
      </div>
    );
  }
  if ((linksWebSocketContainer === 'idle' && form) || linksWebSocketContainer === 'ready') {
    content = (
      <Properties
        editingContextId={editingContextId}
        form={form}
        subscribers={subscribers}
        widgetSubscriptions={widgetSubscriptions}
        readOnly={true}
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
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
