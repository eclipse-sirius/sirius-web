/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { cleanup, render } from '@testing-library/react';
import React from 'react';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLImage } from '../../form/FormEventFragments.types';
import { ImagePropertySection } from '../ImagePropertySection';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const imageWithMaxWidth: GQLImage = {
  label: 'myImage',
  url: 'https://www.eclipse.org/sirius/common_assets/images/logos/logo_sirius.png',
  maxWidth: '42px',
  iconURL: [],
  hasHelpText: false,
  __typename: 'Image',
  diagnostics: [],
  id: 'imageId',
  readOnly: false,
};

const imageWithNoMaxWidth: GQLImage = {
  label: 'myImage',
  url: 'https://www.eclipse.org/sirius/common_assets/images/logos/logo_sirius.png',
  maxWidth: '',
  iconURL: [],
  hasHelpText: false,
  __typename: 'Image',
  diagnostics: [],
  id: 'imageId',
  readOnly: false,
};

test('render image widget with maxWidth', () => {
  const { container } = render(
    <MockedProvider>
      <ImagePropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={imageWithMaxWidth}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  const containerStyle = window.getComputedStyle(document.querySelectorAll('div')[4]);
  expect(containerStyle.display).toEqual('grid');
  expect(containerStyle['grid-template-columns']).toEqual('minmax(auto, 42px)');
});

test('render image widget without maxWidth', () => {
  const { container } = render(
    <MockedProvider>
      <ImagePropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={imageWithNoMaxWidth}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  const containerStyle = window.getComputedStyle(document.querySelectorAll('div')[4]);
  expect(containerStyle.display).toEqual('grid');
  expect(containerStyle['grid-template-columns']).toEqual('1fr');
});

test('render image widget with help hint', () => {
  const { container } = render(
    <MockedProvider>
      <ImagePropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={{ ...imageWithMaxWidth, hasHelpText: true }}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
  const containerStyle = window.getComputedStyle(document.querySelectorAll('div')[4]);
  expect(containerStyle.display).toEqual('grid');
  expect(containerStyle['grid-template-columns']).toEqual('minmax(auto, 42px)');
});
