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
import { GQLCheckbox } from '../../form/FormEventFragments.types';
import { editCheckboxMutation } from '../CheckboxPropertySection';
import { GQLEditCheckboxMutationData, GQLErrorPayload, GQLSuccessPayload } from '../CheckboxPropertySection.types';

export const checkbox: GQLCheckbox = {
  __typename: 'Checkbox',
  id: 'checkboxId',
  label: 'Click Me',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  booleanValue: false,
  readOnly: false,
  style: {
    color: '#de1000',
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

export const checkboxReadOnly: GQLCheckbox = {
  __typename: 'Checkbox',
  id: 'checkboxId',
  label: 'Click Me',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  booleanValue: false,
  readOnly: false,
  style: {
    color: '#de1000',
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

const editCheckboxSuccessData: GQLEditCheckboxMutationData = { editCheckbox: successPayload };

export const editCheckboxSuccessMock: MockedResponse<Record<string, any>> = {
  request: {
    query: editCheckboxMutation,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: editCheckboxSuccessData };
  }),
};

const editCheckboxErrorPayload: GQLErrorPayload = {
  __typename: 'ErrorPayload',
  messages: [
    {
      body: 'An error has occurred, please refresh the page',
      level: 'ERROR',
    },
  ],
};

const editCheckboxErrorData: GQLEditCheckboxMutationData = {
  editCheckbox: editCheckboxErrorPayload,
};

export const editCheckboxErrorMock: MockedResponse<Record<string, any>> = {
  request: {
    query: editCheckboxMutation,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: editCheckboxErrorData };
  }),
};
