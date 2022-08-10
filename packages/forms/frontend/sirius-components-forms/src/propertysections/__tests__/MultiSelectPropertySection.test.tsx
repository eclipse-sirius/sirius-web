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
import { v4 } from 'uuid';
import { expect, test, vi } from 'vitest';
import { GQLMultiSelect } from '../../form/FormEventFragments.types';
import { MultiSelectPropertySection } from '../MultiSelectPropertySection';

const mock = vi.fn().mockImplementation(v4);
mock.mockImplementation(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

const defaultMultiSelect: GQLMultiSelect = {
  __typename: 'MultiSelect',
  id: 'multiSelectId',
  label: 'MultiSelectLabel',
  iconURL: null,
  diagnostics: [],
  values: [],
  options: [],
  style: null,
};

const multiSelectWithStyle: GQLMultiSelect = {
  __typename: 'MultiSelect',
  id: 'multiSelectId',
  label: 'MultiSelectLabel',
  iconURL: null,
  diagnostics: [],
  values: [],
  options: [],
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

const multiSelectWithEmptyStyle: GQLMultiSelect = {
  __typename: 'MultiSelect',
  id: 'multiSelectId',
  label: 'MultiSelectLabel',
  iconURL: null,
  diagnostics: [],
  values: [],
  options: [],
  style: {
    backgroundColor: '',
    foregroundColor: '',
    fontSize: 14,
    italic: false,
    bold: false,
    underline: false,
    strikeThrough: false,
  },
};

test('should render the multiSelect without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <MultiSelectPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultMultiSelect}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the multiSelect with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <MultiSelectPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={multiSelectWithStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the multiSelect with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <MultiSelectPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={multiSelectWithEmptyStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});
