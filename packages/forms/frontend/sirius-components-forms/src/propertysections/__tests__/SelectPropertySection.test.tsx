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
import { GQLSelect } from '../../form/FormEventFragments.types';
import { SelectPropertySection } from '../SelectPropertySection';

const mock = vi.fn().mockImplementation(v4);
mock.mockImplementation(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

const defaultSelect: GQLSelect = {
  __typename: 'Select',
  id: 'selectId',
  label: 'SelectLabel',
  iconURL: null,
  diagnostics: [],
  value: '',
  options: [],
  style: null,
};

const selectWithStyle: GQLSelect = {
  __typename: 'Select',
  id: 'selectId',
  label: 'SelectLabel',
  iconURL: null,
  diagnostics: [],
  value: '',
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

const selectWithEmptyStyle: GQLSelect = {
  __typename: 'Select',
  id: 'selectId',
  label: 'SelectLabel',
  iconURL: null,
  diagnostics: [],
  value: '',
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

test('should render the select without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <SelectPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultSelect}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the select with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <SelectPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={selectWithStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the select with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <SelectPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={selectWithEmptyStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});
