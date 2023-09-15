/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { MessageOptions, ServerContext, ToastContext, ToastContextValue } from '@eclipse-sirius/sirius-components-core';
import { render } from '@testing-library/react';
import { expect, test, vi } from 'vitest';
import { GQLFlexboxContainer } from '../../form/FormEventFragments.types';
import { FlexboxContainerPropertySection } from '../FlexboxContainerPropertySection';

const defaultFlexboxContainer: GQLFlexboxContainer = {
  __typename: 'Flexbox',
  id: 'flexboxId',
  label: 'label',
  borderStyle: null,
  children: [],
  diagnostics: [],
  flexDirection: 'row',
  flexGrow: 1,
  flexWrap: 'nowrap',
  hasHelpText: false,
  iconURL: [],
};

const flexboxContainerWithStyle: GQLFlexboxContainer = {
  ...defaultFlexboxContainer,
  borderStyle: {
    color: 'black',
    lineStyle: 'solid',
    radius: 3,
    size: 1,
  },
};

const mockEnqueue = vi.fn<[string, MessageOptions?], void>();

const toastContextMock: ToastContextValue = {
  enqueueSnackbar: mockEnqueue,
};

test('should render the flexbox container without style', () => {
  const { container } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <FlexboxContainerPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={defaultFlexboxContainer}
            widgetSubscriptions={[]}
            readOnly={false}
            setSelection={() => {}}
          />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('should render the flexbox container with border style', () => {
  const { container } = render(
    <MockedProvider>
      <ServerContext.Provider value={{ httpOrigin: 'http://localhost' }}>
        <ToastContext.Provider value={toastContextMock}>
          <FlexboxContainerPropertySection
            editingContextId="editingContextId"
            formId="formId"
            widget={flexboxContainerWithStyle}
            widgetSubscriptions={[]}
            readOnly={false}
            setSelection={() => {}}
          />
        </ToastContext.Provider>
      </ServerContext.Provider>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
