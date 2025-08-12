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
import { GQLButton } from '../../form/FormEventFragments.types';
import { pushButtonMutation } from '../ButtonPropertySection';
import { GQLErrorPayload, GQLPushButtonMutationData, GQLSuccessPayload } from '../ButtonPropertySection.types';

export const button: GQLButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  buttonLabel: 'Click Me',
  imageURL: null,
  readOnly: false,
  style: {
    backgroundColor: '#de1000',
    foregroundColor: '#fbb800',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
};

export const buttonReadOnly: GQLButton = {
  __typename: 'Button',
  id: 'buttonId',
  label: 'Label',
  iconURL: [],
  hasHelpText: false,
  diagnostics: [],
  buttonLabel: 'Click Me',
  imageURL: null,
  readOnly: true,
  style: {
    backgroundColor: '#de1000',
    foregroundColor: '#fbb800',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
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
