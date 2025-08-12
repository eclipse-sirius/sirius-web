/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { MockedResponse } from '@apollo/client/testing';
import { vi } from 'vitest';
import { GQLTextfield } from '../../form/FormEventFragments.types';
import { editTextfieldMutation, getCompletionProposalsQuery } from '../../propertysections/TextfieldPropertySection';
import {
  GQLCompletionProposalsQueryData,
  GQLEditTextfieldMutationData,
  GQLSuccessPayload,
} from '../../propertysections/TextfieldPropertySection.types';

export const textfield: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  stringValue: 'Composite Processor',
  supportsCompletion: true,
  readOnly: false,
  style: {
    backgroundColor: '',
    foregroundColor: '',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
    widgetGridLayout: {
      gridTemplateColumns: '1fr 1fr',
      gridTemplateRows: '1fr',
      labelGridColumn: '1 / 2',
      labelGridRow: '1 / 2',
      widgetGridColumn: '2 / 3',
      widgetGridRow: '1 / 2',
      gap: '16px',
    },
  },
};

export const textfieldReadOnly: GQLTextfield = {
  __typename: 'Textfield',
  id: 'textfieldId',
  label: 'Name',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  stringValue: 'Composite Processor',
  supportsCompletion: false,
  readOnly: true,
  style: {
    backgroundColor: '',
    foregroundColor: '',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
    widgetGridLayout: {
      gridTemplateColumns: '1fr 1fr',
      gridTemplateRows: '1fr',
      labelGridColumn: '1 / 2',
      labelGridRow: '1 / 2',
      widgetGridColumn: '2 / 3',
      widgetGridRow: '1 / 2',
      gap: '16px',
    },
  },
};

const successPayload: GQLSuccessPayload = { messages: [], __typename: 'SuccessPayload' };

const editTextfieldSuccessData: GQLEditTextfieldMutationData = { editTextfield: successPayload };

export const editTextfieldSuccessMock: MockedResponse<Record<string, any>> = {
  request: {
    query: editTextfieldMutation,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: editTextfieldSuccessData };
  }),
};

const completionResultData: GQLCompletionProposalsQueryData = {
  viewer: {
    editingContext: {
      representation: {
        description: {
          __typename: 'FormDescription',
          completionProposals: [
            {
              description: 'Choice 1',
              textToInsert: 'CompositeProcessor 1',
              charsToReplace: 18,
            },
            {
              description: 'Choice 2',
              textToInsert: 'CompositeProcessor 2',
              charsToReplace: 18,
            },
          ],
        },
      },
    },
  },
};
export const completionRequestMock: MockedResponse<Record<string, any>> = {
  request: {
    query: getCompletionProposalsQuery,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: completionResultData };
  }),
};
