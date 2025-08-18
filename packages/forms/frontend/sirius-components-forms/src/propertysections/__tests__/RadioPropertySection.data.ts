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
import { GQLRadio } from '../../form/FormEventFragments.types';
import { GQLEditRadioMutationData, GQLErrorPayload, GQLSuccessPayload } from '../RadioPropertySection.types';
import { MockedResponse } from '@apollo/client/testing';
import { editRadioMutation } from '../RadioPropertySection';
import { vi } from 'vitest';

const defaultRadio = {
  __typename: 'Radio',
  id: 'radioId',
  label: 'Status:',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  readOnly: false,
  options: [
    {
      id: '0',
      label: 'inactive',
      selected: true,
    },
    {
      id: '1',
      label: 'active',
      selected: false,
    },
  ],
};

export const radio: GQLRadio = {
  ...defaultRadio,
  style: {
    color: '',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
};

const successPayload: GQLSuccessPayload = { messages: [], __typename: 'SuccessPayload' };
const editRadioSuccessData: GQLEditRadioMutationData = { editRadio: successPayload };
export const editRadioSuccessMock: MockedResponse<Record<string, any>> = {
  request: {
    query: editRadioMutation,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: editRadioSuccessData };
  }),
};

const editRadioErrorPayload: GQLErrorPayload = {
  __typename: 'ErrorPayload',
  messages: [
    {
      body: 'An error has occurred, please refresh the page',
      level: 'ERROR',
    },
    {
      body: 'FeedbackMessage',
      level: 'INFO',
    },
  ],
};
const editRadioErrorData: GQLEditRadioMutationData = {
  editRadio: editRadioErrorPayload,
};
export const editRadioErrorMock: MockedResponse<Record<string, any>> = {
  request: {
    query: editRadioMutation,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: editRadioErrorData };
  }),
};
