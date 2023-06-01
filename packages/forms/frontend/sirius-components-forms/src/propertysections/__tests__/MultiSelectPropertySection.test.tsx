/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { cleanup, render } from '@testing-library/react';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLMultiSelect } from '../../form/FormEventFragments.types';
import { MultiSelectPropertySection } from '../MultiSelectPropertySection';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

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

const mockEnqueue = vi.fn();

const toastContextMock: ToastContextValue = {
  useToast: () => {
    return {
      enqueueSnackbar: mockEnqueue,
      closeSnackbar: () => {},
    };
  },
};

test('should render the multiSelect without style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <MultiSelectPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={defaultMultiSelect}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the multiSelect with style', () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <MultiSelectPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={multiSelectWithStyle}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});

test('should render the multiSelect with empty style', async () => {
  const { baseElement } = render(
    <MockedProvider>
      <ToastContext.Provider value={toastContextMock}>
        <MultiSelectPropertySection
          editingContextId="editingContextId"
          formId="formId"
          widget={multiSelectWithEmptyStyle}
          subscribers={[]}
          readOnly={false}
        />
      </ToastContext.Provider>
    </MockedProvider>
  );
  expect(baseElement).toMatchSnapshot();
});
