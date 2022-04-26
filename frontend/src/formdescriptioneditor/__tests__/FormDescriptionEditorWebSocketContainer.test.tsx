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
import { MockedProvider } from '@apollo/client/testing';
import { render } from '@testing-library/react';
import { FormDescriptionEditorWebSocketContainer } from 'formdescriptioneditor/FormDescriptionEditorWebSocketContainer';
import React from 'react';
import { Selection } from 'workbench/Workbench.types';

jest.mock('uuid', () => ({ v4: () => '48be95fc-3422-45d3-b1f9-d590e847e9e1' }));

const emptySelection: Selection = {
  entries: [],
};

const emptySetSelection = (selection: Selection) => {};

test('should drop the Textfield in the drop area', () => {
  render(
    <MockedProvider>
      <FormDescriptionEditorWebSocketContainer
        editingContextId="editingContextId"
        representationId="formDescriptionEditorId"
        readOnly={false}
        selection={emptySelection}
        setSelection={emptySetSelection}
      />
    </MockedProvider>
  );
  // Will be activated when the drop event will be connected to the backend through graphql mutation
  // const element: HTMLElement = screen.getByTestId('FormDescriptionEditor-DropArea');
  // expect(screen.queryByTestId('Textfield')).toBeNull();

  // const dataTransfer: DataTransfer = new DataTransfer();
  // dataTransfer.setData('text/plain', 'Textfield');
  // fireEvent.drop(element, { dataTransfer });

  // expect(screen.queryByTestId('Textfield')).not.toBeNull();
});
