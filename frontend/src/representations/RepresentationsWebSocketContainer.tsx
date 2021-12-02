/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
import gql from 'graphql-tag';
import { ListPropertySection } from 'properties/propertysections/ListPropertySection';
import React, { useContext, useEffect } from 'react';
import { RepresentationContext } from 'workbench/RepresentationContext';
import { RepresentationsWebSocketContainerProps } from './RepresentationsWebSocketContainer.types';
import {
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  RepresentationsWebSocketContainerContext,
  RepresentationsWebSocketContainerEvent,
  representationsWebSocketContainerMachine,
  SchemaValue,
  ShowToastEvent,
  SwitchSelectionEvent,
} from './RepresentationsWebSocketContainerMachine';

const representationsEventSubscription = gql`
  subscription representationsEvent($input: RepresentationsEventInput!) {
    representationsEvent(input: $input) {
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

const useRepresentationsWebSocketContainerStyles = makeStyles((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
}));

export const RepresentationsWebSocketContainer = ({
  editingContextId,
  selection,
  setSelection,
  readOnly,
}: RepresentationsWebSocketContainerProps) => {
  const classes = useRepresentationsWebSocketContainerStyles();

  const [{ value, context }, dispatch] = useMachine<
    RepresentationsWebSocketContainerContext,
    RepresentationsWebSocketContainerEvent
  >(representationsWebSocketContainerMachine);

  const { toast, representationsWebSocketContainer } = value as SchemaValue;
  const { id, currentSelection, formId, widget, subscribers, message } = context;
  const { registry } = useContext(RepresentationContext);

  useEffect(() => {
    if (selection?.entries.length > 0 && selection.entries[0].id !== currentSelection?.id) {
      const isRepresentation = registry.isRepresentation(selection.entries[0].kind);
      const switchSelectionEvent: SwitchSelectionEvent = {
        type: 'SWITCH_SELECTION',
        selection: selection.entries[0],
        isRepresentation,
      };
      dispatch(switchSelectionEvent);
    }
  }, [currentSelection, registry, selection, dispatch]);

  const { error } = useSubscription(representationsEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        objectId: currentSelection?.id,
      },
    },
    fetchPolicy: 'no-cache',
    skip: representationsWebSocketContainer === 'empty' || representationsWebSocketContainer === 'unsupportedSelection',
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
  if (!selection || representationsWebSocketContainer === 'unsupportedSelection') {
    content = (
      <div className={classes.idle}>
        <Typography variant="subtitle2">No object selected</Typography>
      </div>
    );
  }
  if ((representationsWebSocketContainer === 'idle' && widget) || representationsWebSocketContainer === 'ready') {
    content = (
      <ListPropertySection
        editingContextId={editingContextId}
        formId={formId}
        readOnly={readOnly}
        widget={widget}
        subscribers={subscribers}
        setSelection={setSelection}
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
