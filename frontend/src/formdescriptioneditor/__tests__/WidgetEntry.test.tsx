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
import { act, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { addWidgetMutation } from 'formdescriptioneditor/FormDescriptionEditorEventFragment';
import {
  GQLAddWidgetMutationData,
  GQLAddWidgetMutationVariables,
  GQLAddWidgetSuccessPayload,
  GQLFormDescriptionEditor,
  GQLFormDescriptionEditorWidget,
} from 'formdescriptioneditor/FormDescriptionEditorEventFragment.types';
import { WidgetEntry } from 'formdescriptioneditor/WidgetEntry';
import React from 'react';
import { Selection } from 'workbench/Workbench.types';
import { DataTransfer } from './DataTransfer';

jest.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = (selection: Selection) => {};

const addWidgetVariables: GQLAddWidgetMutationVariables = {
  input: {
    id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
    editingContextId: 'editingContextId',
    representationId: 'formDescriptionEditorId',
    kind: 'Textfield',
    index: 0,
  },
};
const addWidgetSuccessPayload: GQLAddWidgetSuccessPayload = {
  __typename: 'AddWidgetSuccessPayload',
  id: '48be95fc-3422-45d3-b1f9-d590e847e9e1',
};
const addWidgetSuccessData: GQLAddWidgetMutationData = { addWidget: addWidgetSuccessPayload };

test('should drop the Textfield in the drop area', async () => {
  const formDescriptionEditor: GQLFormDescriptionEditor = {
    id: 'FormDescriptionEditor',
    widgets: [{ id: 'TextfieldDescription', kind: 'TextfieldDescription', label: 'Textfield1' }],
    metadata: {
      id: 'FormDescriptionEditor',
      kind: 'FormDescriptionEditor',
      label: 'FormDescriptionEditor',
      description: { id: 'FormDescriptionEditorDescription' },
    },
  };

  const textfieldWidget: GQLFormDescriptionEditorWidget = {
    id: 'Textfield1',
    label: 'Textfield1',
    kind: 'Textfield',
  };

  let addWidgetCalled: boolean = false;
  const addWidgetSuccessMock: MockedResponse<Record<string, any>> = {
    request: {
      query: addWidgetMutation,
      variables: addWidgetVariables,
    },
    result: () => {
      addWidgetCalled = true;
      return { data: addWidgetSuccessData };
    },
  };

  const mocks: MockedResponse<Record<string, any>>[] = [addWidgetSuccessMock];

  render(
    <MockedProvider mocks={mocks}>
      <WidgetEntry
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        formDescriptionEditor={formDescriptionEditor}
        widget={textfieldWidget}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );

  const element: HTMLElement = screen.getByTestId('WidgetEntry-DropArea');

  const dataTransfer: DataTransfer = new DataTransfer();
  dataTransfer.setData('text/plain', 'Textfield');
  fireEvent.drop(element, { dataTransfer });

  await act(async () => {
    await new Promise((resolve) => setTimeout(resolve, 0));
    await waitFor(() => {
      expect(addWidgetCalled).toBeTruthy();
    });
  });
});
