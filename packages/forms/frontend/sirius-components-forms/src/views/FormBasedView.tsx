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
import { Toast, useSelection } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useContext, useEffect } from 'react';
import { Form } from '../form/Form';
import { WidgetContribution } from '../form/Form.types';
import { PropertySectionContext } from '../form/FormContext';
import { PropertySectionContextValue } from '../form/FormContext.types';
import { formRefreshedEventPayloadFragment } from '../form/FormEventFragments';
import {
  GQLPropertiesEventInput,
  GQLPropertiesEventSubscription,
  GQLPropertiesEventVariables,
} from '../form/FormEventFragments.types';
import { FormBasedViewProps } from './FormBasedView.types';
import {
  FormBasedViewContext,
  FormBasedViewEvent,
  HandleCompleteEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  SwitchSelectionEvent,
  formBasedViewMachine,
} from './FormBasedViewMachine';
import { FormConverter } from './FormConverter.types';

export const getFormEventSubscription = (subscriptionName: string, contributions: Array<WidgetContribution>) => {
  return `
  subscription ${subscriptionName}($input: PropertiesEventInput!) {
    ${subscriptionName}(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment(contributions)}
`;
};

const useFormBasedViewStyles = makeStyles((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
}));

/**
 * Used to define workbench views based on a form.
 */
export const FormBasedView = ({
  editingContextId,
  readOnly,
  subscriptionName,
  converter,
  postProcessor,
}: FormBasedViewProps) => {
  const classes = useFormBasedViewStyles();
  const [{ value, context }, dispatch] = useMachine<FormBasedViewContext, FormBasedViewEvent>(formBasedViewMachine);
  const { toast, formBasedView } = value as SchemaValue;
  const { id, currentSelection, form, message } = context;
  const { selection } = useSelection();

  /**
   * Displays another form if the selection indicates that we should display another properties view.
   */
  const currentSelectionKey: string = currentSelection?.entries
    .map((entry) => entry.id)
    .sort()
    .join(':');
  const newSelectionKey: string = selection?.entries
    .map((entry) => entry.id)
    .sort()
    .join(':');
  useEffect(() => {
    if (selection.entries.length > 0 && currentSelectionKey !== newSelectionKey) {
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
  }, [currentSelectionKey, newSelectionKey, dispatch]);

  const input: GQLPropertiesEventInput = {
    id,
    editingContextId,
    objectIds: currentSelection?.entries.map((entry) => entry.id),
  };
  const variables: GQLPropertiesEventVariables = { input };
  const { propertySectionsRegistry } = useContext<PropertySectionContextValue>(PropertySectionContext);
  const formSubscription = getFormEventSubscription(
    subscriptionName,
    propertySectionsRegistry.getWidgetContributions()
  );
  const { error } = useSubscription<GQLPropertiesEventSubscription, GQLPropertiesEventVariables>(
    gql(formSubscription),
    {
      variables,
      fetchPolicy: 'no-cache',
      skip: formBasedView === 'empty' || formBasedView === 'unsupportedSelection',
      onData: ({ data }) => {
        const handleDataEvent: HandleSubscriptionResultEvent = {
          type: 'HANDLE_SUBSCRIPTION_RESULT',
          result: data.data[subscriptionName],
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

  const formConverter: FormConverter = converter ? converter : { convert: (gqlForm) => gqlForm };

  let content: JSX.Element | null = null;
  if (formBasedView === 'empty' || formBasedView === 'unsupportedSelection' || formBasedView === 'complete') {
    content = (
      <div className={classes.idle}>
        <Typography variant="subtitle2">No object selected</Typography>
      </div>
    );
  }
  if ((formBasedView === 'idle' && form) || formBasedView === 'ready') {
    if (postProcessor) {
      content = postProcessor({ editingContextId, readOnly }, formConverter.convert(form));
    } else {
      content = <Form editingContextId={editingContextId} form={formConverter.convert(form)} readOnly={readOnly} />;
    }
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
