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
import { gql, useSubscription } from '@apollo/client';
import { RepresentationComponentProps } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { Form } from '../form/Form';
import {
  formRefreshedEventPayloadFragment,
  subscribersUpdatedEventPayloadFragment,
  widgetSubscriptionsUpdatedEventPayloadFragment,
} from '../form/FormEventFragments';
import { GQLFormEventSubscription } from '../form/FormEventFragments.types';
import {
  FormRepresentationContext,
  FormRepresentationEvent,
  formRepresentationMachine,
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  SwitchFormEvent,
} from './FormRepresentationMachine';

const formEventSubscription = gql(`
  subscription formEvent($input: FormEventInput!) {
    formEvent(input: $input) {
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
`);

const useFormRepresentationStyles = makeStyles(() => ({
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
}));

/**
 * Connect the Form component to the GraphQL API over Web Socket.
 */
export const FormRepresentation = ({
  editingContextId,
  representationId,
  setSelection,
  readOnly,
}: RepresentationComponentProps) => {
  const classes = useFormRepresentationStyles();
  const [{ value, context }, dispatch] = useMachine<FormRepresentationContext, FormRepresentationEvent>(
    formRepresentationMachine,
    {
      context: {
        formId: representationId,
      },
    }
  );
  const { toast, formRepresentation } = value as SchemaValue;
  const { id, formId, form, widgetSubscriptions, message } = context;

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
        editingContextId,
        formId: representationId,
      },
    },
    fetchPolicy: 'no-cache',
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
  if (formRepresentation === 'ready') {
    content = (
      <Form
        editingContextId={editingContextId}
        form={form}
        widgetSubscriptions={widgetSubscriptions}
        setSelection={setSelection}
        readOnly={readOnly}
      />
    );
  } else if (formRepresentation === 'complete') {
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
