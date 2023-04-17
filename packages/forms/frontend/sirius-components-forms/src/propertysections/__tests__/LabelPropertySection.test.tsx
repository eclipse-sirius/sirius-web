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
import { cleanup, render } from '@testing-library/react';
import { afterEach, expect, test, vi } from 'vitest';
import { GQLLabelWidget } from '../../form/FormEventFragments.types';
import { LabelWidgetPropertySection } from '../LabelWidgetPropertySection';

crypto.randomUUID = vi.fn(() => '48be95fc-3422-45d3-b1f9-d590e847e9e1');

afterEach(() => cleanup());

const defaultLabel: GQLLabelWidget = {
  label: 'myLabel',
  stringValue: 'theLabelValue',
  iconURL: null,
  style: {
    color: null,
    bold: null,
    fontSize: null,
    italic: null,
    strikeThrough: null,
    underline: null,
  },
  __typename: 'Label',
  diagnostics: [],
  id: 'labelId',
};

const defaultLabelWithStyle: GQLLabelWidget = {
  label: 'myLabel',
  stringValue: 'theLabelValue',
  iconURL: null,
  style: {
    color: 'RebeccaPurple',
    bold: true,
    fontSize: 20,
    italic: true,
    strikeThrough: true,
    underline: true,
  },
  __typename: 'Label',
  diagnostics: [],
  id: 'labelId',
};

test('render label widget', () => {
  const { container } = render(
    <MockedProvider>
      <LabelWidgetPropertySection widget={defaultLabel} subscribers={[]} />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('render label widget with style', () => {
  const { container } = render(
    <MockedProvider>
      <LabelWidgetPropertySection widget={defaultLabelWithStyle} subscribers={[]} />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
