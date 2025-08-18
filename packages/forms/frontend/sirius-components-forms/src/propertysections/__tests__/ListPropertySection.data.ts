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
import { GQLListItem, GQLList } from '../../form/FormEventFragments.types';
import { GQLSuccessPayload, GQLClickListItemMutationData } from '../ListPropertySection.types';
import { MockedResponse } from '@apollo/client/testing';
import { clickListItemMutation } from '../ListPropertySection';
import { vi } from 'vitest';

const defaultListItems: GQLListItem[] = [
  {
    id: 'item1',
    label: 'item1Label',
    kind: 'itemKind',
    iconURL: [],
    deletable: true,
  },
  {
    id: 'item2',
    label: 'item2Label',
    kind: 'itemKind',
    iconURL: [],
    deletable: true,
  },
  {
    id: 'item3',
    label: 'item3Label',
    kind: 'itemKind',
    iconURL: [],
    deletable: true,
  },
];
const defaultList = {
  label: 'myList',
  iconURL: [],
  hasHelpText: false,
  items: defaultListItems,
  style: null,
  __typename: 'List',
  diagnostics: [],
  id: 'listId',
  readOnly: false,
};

export const list: GQLList = {
  ...defaultList,
  style: {
    color: 'RebeccaPurple',
    bold: true,
    fontSize: 20,
    italic: true,
    strikeThrough: true,
    underline: true,
  },
};

export const listReadOnly: GQLList = {
  ...list,
  readOnly: true,
};

const successPayload: GQLSuccessPayload = { messages: [], __typename: 'SuccessPayload' };

const clickListItemSuccessData: GQLClickListItemMutationData = {
  clickListItem: successPayload,
};

export const itemClickCalledCalledSuccessMock: MockedResponse<Record<string, any>> = {
  request: {
    query: clickListItemMutation,
  },
  variableMatcher: () => true,
  result: vi.fn(() => {
    return { data: clickListItemSuccessData };
  }),
};
