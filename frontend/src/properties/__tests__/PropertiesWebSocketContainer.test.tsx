/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { MockedProvider, MockedResponse } from '@apollo/client/testing';
import { act, render, screen, waitFor } from '@testing-library/react';
import {
  GQLCheckbox,
  GQLForm,
  GQLFormRefreshedEventPayload,
  GQLGroup,
  GQLPage,
  GQLPropertiesEventSubscription,
  GQLPropertiesEventVariables,
  GQLTextfield,
} from 'form/FormEventFragments.types';
import { propertiesEventSubscription, PropertiesWebSocketContainer } from 'properties/PropertiesWebSocketContainer';
import React from 'react';
import { Selection } from 'workbench/Workbench.types';

jest.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

const defaultCheckbox: GQLCheckbox = {
  __typename: 'Checkbox',
  id: 'checkboxId',
  label: 'CheckboxLabel',
  diagnostics: [],
  booleanValue: false,
};

const defaultTextField: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name:',
  stringValue: 'Composite Processor',
  diagnostics: [],
};

const defaultGroup: GQLGroup = {
  id: 'groupId',
  label: 'groupeLabel',
  widgets: [defaultCheckbox, defaultTextField],
};

const defaultPage: GQLPage = {
  id: 'pageId',
  label: 'pageLabel',
  groups: [defaultGroup],
};

const defaultGQLForm: GQLForm = {
  id: 'formId',
  metadata: undefined,
  pages: [defaultPage],
};

const propertiesEventSubscriptionSuccessPayload: GQLFormRefreshedEventPayload = {
  __typename: 'FormRefreshedEventPayload',
  id: 'payloadId',
  form: defaultGQLForm,
};

const propertiesEventFormRefreshedSubscription: GQLPropertiesEventSubscription = {
  propertiesEvent: propertiesEventSubscriptionSuccessPayload,
};

const propertiesEventSubscriptionVariables: GQLPropertiesEventVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    objectIds: ['selectionId'],
  },
};

test('should render the form', async () => {
  let propertiesEventSubscriptionCalled = false;
  const propertiesEventSubscriptionMock: MockedResponse<Record<string, any>> = {
    request: {
      query: propertiesEventSubscription,
      variables: propertiesEventSubscriptionVariables,
    },
    result: () => {
      propertiesEventSubscriptionCalled = true;
      return { data: propertiesEventFormRefreshedSubscription };
    },
  };

  const selection: Selection = {
    entries: [
      {
        id: 'selectionId',
        label: 'selectionLabel',
        kind: 'selectionKind',
      },
    ],
  };
  const mocks = [propertiesEventSubscriptionMock];
  const { container } = render(
    <MockedProvider mocks={mocks}>
      <PropertiesWebSocketContainer
        editingContextId="editingContextId"
        readOnly={false}
        selection={selection}
        setSelection={function (selection: Selection): void {}}
      />
    </MockedProvider>
  );

  await act(async () => {
    await waitFor(() => {
      //The problem is that the mock sends the subscription termination signal
      //and I did not find how to mock it.
      //This test works before the signal is sent, but is rerun when the termination signal is received
      //since the component is rerendered.
      expect(propertiesEventSubscriptionCalled).toBeTruthy();
      expect(container).toMatchSnapshot();

      const textField: HTMLInputElement = screen.getByTestId('Name:').querySelector('input');
      expect(textField.value).toBe('Composite Processor');

      const checkBox: HTMLInputElement = screen.getByTestId('CheckboxLabel').querySelector('input');
      expect(checkBox.checked).toBe(false);
    });
  });
});
