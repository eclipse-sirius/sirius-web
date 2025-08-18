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
import { pushButtonMutation } from '../ButtonPropertySection';
import { GQLSuccessPayload, GQLPushButtonMutationData, GQLErrorPayload } from '../ButtonPropertySection.types';
import { GQLButton, GQLSplitButton } from '../../form/FormEventFragments.types';
import { vi } from 'vitest';

const defaultButton: GQLButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  buttonLabel: 'ButtonLabel',
  imageURL: null,
  style: {
    backgroundColor: null,
    foregroundColor: null,
    fontSize: null,
    italic: null,
    bold: null,
    underline: null,
    strikeThrough: null,
  },
  readOnly: false,
};

export const splitButton: GQLSplitButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  readOnly: false,
  actions: [defaultButton, defaultButton],
};

const successPayload: GQLSuccessPayload = { __typename: 'SuccessPayload', messages: [] };
const pushButtonSuccessData: GQLPushButtonMutationData = { pushButton: successPayload };
export const pushButtonSuccessMock: MockedResponse<Record<string, any>> = {
  request: {
    query: pushButtonMutation,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: pushButtonSuccessData };
  }),
};

const pushButtonErrorPayload: GQLErrorPayload = {
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
const pushButtonErrorData: GQLPushButtonMutationData = {
  pushButton: pushButtonErrorPayload,
};
export const pushButtonErrorMock: MockedResponse<Record<string, any>> = {
  request: {
    query: pushButtonMutation,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: pushButtonErrorData };
  }),
};
