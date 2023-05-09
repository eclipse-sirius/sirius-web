/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { RepresentationComponentProps, Toast } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import { useMachine } from '@xstate/react';
import { useContext, useEffect } from 'react';
import { Form } from '../form/Form';
import { WidgetContribution } from '../form/Form.types';
import { PropertySectionContext } from '../form/FormContext';
import {
  formRefreshedEventPayloadFragment,
  subscribersUpdatedEventPayloadFragment,
  widgetSubscriptionsUpdatedEventPayloadFragment,
} from '../form/FormEventFragments';
import { GQLFormEventSubscription } from '../form/FormEventFragments.types';
import { Page } from '../pages/Page';
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

const formEventSubscription = (contributions: Array<WidgetContribution>) =>
  gql(`
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
  ${formRefreshedEventPayloadFragment(contributions)}
`);

const useFormRepresentationStyles = makeStyles((theme) => ({
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

  const { propertySectionsRegistry } = useContext(PropertySectionContext);

  const { error } = useSubscription<GQLFormEventSubscription>(
    formEventSubscription(propertySectionsRegistry.getWidgetContributions()),
    {
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
    }
  );

  useEffect(() => {
    if (error) {
      const { message } = error;
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  }, [error, dispatch]);

  let content = null;
  if (formRepresentation === 'ready') {
    if (form.pages.length > 1) {
      content = (
        <Form
          editingContextId={editingContextId}
          form={form}
          widgetSubscriptions={widgetSubscriptions}
          setSelection={setSelection}
          readOnly={readOnly}
        />
      );
    } else {
      content = (
        <div data-testid="page" className={classes.page}>
          <Page
            editingContextId={editingContextId}
            formId={form.id}
            page={form.pages[0]}
            widgetSubscriptions={widgetSubscriptions}
            setSelection={setSelection}
            readOnly={readOnly}
          />
        </div>
      );
    }
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
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
