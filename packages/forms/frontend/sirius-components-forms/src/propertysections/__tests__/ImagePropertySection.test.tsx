/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import { afterEach, expect, test, vi } from 'vitest';
import { ImagePropertySection } from '../ImagePropertySection';
import { imageWithMaxWidth, imageWithNoMaxWidth } from './ImagePropertySection.data';

afterEach(() => {
  cleanup();
  vi.clearAllMocks();
});

test('render image widget with maxWidth', () => {
  render(
    <MockedProvider>
      <ImagePropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={imageWithMaxWidth}
        readOnly={false}
      />
    </MockedProvider>
  );
  const element = document.querySelectorAll('div[class$="container"]')[0];
  expect(element).toBeDefined();
  const containerStyle = window.getComputedStyle(element!);
  expect(containerStyle.display).toEqual('grid');
  expect(containerStyle['grid-template-columns']).toEqual('minmax(auto, 42px)');
});

test('render image widget without maxWidth', () => {
  render(
    <MockedProvider>
      <ImagePropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={imageWithNoMaxWidth}
        readOnly={false}
      />
    </MockedProvider>
  );
  const element = document.querySelectorAll('div[class$="container"]')[0];
  expect(element).toBeDefined();
  const containerStyle = window.getComputedStyle(element!);
  expect(containerStyle.display).toEqual('grid');
  expect(containerStyle['grid-template-columns']).toEqual('1fr');
});

test('render image widget with help hint', () => {
  render(
    <MockedProvider>
      <ImagePropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={{ ...imageWithMaxWidth, hasHelpText: true }}
        readOnly={false}
      />
    </MockedProvider>
  );
  const element = document.querySelectorAll('div[class$="container"]')[0];
  expect(element).toBeDefined();
  const containerStyle = window.getComputedStyle(element!);
  expect(containerStyle.display).toEqual('grid');
  expect(containerStyle['grid-template-columns']).toEqual('minmax(auto, 42px)');
});
