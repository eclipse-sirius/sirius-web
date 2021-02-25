/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
  preDestroyPayloadFragment,
  subscribersUpdatedEventPayloadFragment,
  widgetSubscriptionsUpdatedEventPayloadFragment,
} from 'form/FormEventFragments';
import { GQLFormEventSubscription } from 'form/FormEventFragments.types';
import {
  FormWebSocketContainerContext,
  FormWebSocketContainerEvent,
  formWebSocketContainerMachine,
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  SwitchFormEvent,
} from 'form/FormWebSocketContainerMachine';
import gql from 'graphql-tag';
import { Properties } from 'properties/Properties';
import React, { useEffect } from 'react';
import { RepresentationComponentProps } from 'workbench/Workbench.types';

const formEventSubscription = gql`
  subscription formEvent($input: FormEventInput!) {
    formEvent(input: $input) {
      __typename
      ... on PreDestroyPayload {
        ...preDestroyPayloadFragment
      }
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
  ${preDestroyPayloadFragment}
  ${subscribersUpdatedEventPayloadFragment}
  ${widgetSubscriptionsUpdatedEventPayloadFragment}
  ${formRefreshedEventPayloadFragment}
`;

const useFormWebSocketContainerStyles = makeStyles((theme) => ({
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
}));

/**
 * Connect the Form component to the GraphQL API over Web Socket.
 */
export const FormWebSocketContainer = ({ editingContextId, representationId }: RepresentationComponentProps) => {
  const classes = useFormWebSocketContainerStyles();
  const [{ value, context }, dispatch] = useMachine<FormWebSocketContainerContext, FormWebSocketContainerEvent>(
    formWebSocketContainerMachine,
    {
      context: {
        formId: representationId,
      },
    }
  );
  const { toast, formWebSocketContainer } = value as SchemaValue;
  const { id, formId, form, subscribers, widgetSubscriptions, message } = context;

  /**
   * Displays an other form if the selection indicates that we should display another properties view.
   */
  useEffect(() => {
    if (formId !== representationId) {
      const switchFormEvent: SwitchFormEvent = { type: 'SWITCH_FORM', formId: representationId };
      dispatch(switchFormEvent);
    }
  }, [representationId, formId, dispatch]);

  const { error } = useSubscription<GQLFormEventSubscription>(formEventSubscription, {
    variables: {
      input: {
        id,
        projectId: editingContextId,
        formId: representationId,
      },
    },
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
  if (formWebSocketContainer === 'ready') {
    content = (
      <Properties
        editingContextId={editingContextId}
        form={form}
        subscribers={subscribers}
        widgetSubscriptions={widgetSubscriptions}
      />
    );
  } else if (formWebSocketContainer === 'complete') {
    content = (
      <div className={classes.complete}>
        <Typography variant="h5" align="center">
          The form does not exist anymore
        </Typography>
      </div>
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
