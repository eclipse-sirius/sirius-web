/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useContext, useEffect } from 'react';
import { Form } from '../form/Form';
import { WidgetContribution } from '../form/Form.types';
import { PropertySectionContext } from '../form/FormContext';
import { PropertySectionContextValue } from '../form/FormContext.types';
import { formRefreshedEventPayloadFragment } from '../form/FormEventFragments';
import { GQLFormEventSubscription } from '../form/FormEventFragments.types';
import { Page } from '../pages/Page';
import { ToolbarAction } from '../toolbaraction/ToolbarAction';
import {
  FormRepresentationContext,
  FormRepresentationEvent,
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  SwitchFormEvent,
  formRepresentationMachine,
} from './FormRepresentationMachine';

const formEventSubscription = (contributions: Array<WidgetContribution>) =>
  gql(`
  subscription formEvent($input: FormEventInput!) {
    formEvent(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment(contributions)}
`);

const useFormRepresentationStyles = makeStyles((theme) => ({
  page: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(1),
    paddingTop: theme.spacing(1),
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

/**
 * Connect the Form component to the GraphQL API over Web Socket.
 */
export const FormRepresentation = ({ editingContextId, representationId, readOnly }: RepresentationComponentProps) => {
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
  const { id, formId, form, message } = context;

  /**
   * Displays an other form if the selection indicates that we should display another properties view.
   */
  useEffect(() => {
    if (formId !== representationId) {
      const switchFormEvent: SwitchFormEvent = { type: 'SWITCH_FORM', formId: representationId };
      dispatch(switchFormEvent);
    }
  }, [representationId, formId, dispatch]);

  const { propertySectionsRegistry } = useContext<PropertySectionContextValue>(PropertySectionContext);

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
      onData: ({ data }) => {
        const handleDataEvent: HandleSubscriptionResultEvent = {
          type: 'HANDLE_SUBSCRIPTION_RESULT',
          result: data,
        };
        dispatch(handleDataEvent);
      },
      onComplete: () => {
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
  if (formRepresentation === 'ready') {
    if (form.pages.length > 1) {
      content = <Form editingContextId={editingContextId} form={form} readOnly={readOnly} />;
    } else if (form.pages.length === 1) {
      let selectedPageToolbar = null;
      if (form.pages[0].toolbarActions?.length > 0) {
        selectedPageToolbar = (
          <div className={classes.toolbar}>
            {form.pages[0].toolbarActions.map((toolbarAction) => (
              <div className={classes.toolbarAction} key={toolbarAction.id}>
                <ToolbarAction
                  editingContextId={editingContextId}
                  formId={form.id}
                  readOnly={readOnly}
                  widget={toolbarAction}
                />
              </div>
            ))}
          </div>
        );
      }
      content = (
        <div data-testid="page" className={classes.page}>
          {selectedPageToolbar}
          <Page editingContextId={editingContextId} formId={form.id} page={form.pages[0]} readOnly={readOnly} />
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
    <div data-representation-kind="form">
      {content}
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </div>
  );
};
