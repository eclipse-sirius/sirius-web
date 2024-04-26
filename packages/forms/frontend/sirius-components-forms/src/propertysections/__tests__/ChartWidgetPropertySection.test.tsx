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
import { afterEach, expect, test } from 'vitest';
import {
  GQLBarChart,
  GQLBarChartEntry,
  GQLChartWidget,
  GQLPieChart,
  GQLPieChartEntry,
  GQLRepresentationMetadata,
} from '../../form/FormEventFragments.types';
import { ChartWidgetPropertySection } from '../ChartWidgetPropertySection';

afterEach(() => cleanup());
const pieChartentries: GQLPieChartEntry[] = [
  { key: 'a', value: 10 },
  { key: 'b', value: 15 },
  { key: 'c', value: 5 },
  { key: 'd', value: 20 },
];

const pieChartMetadata: GQLRepresentationMetadata = {
  id: 'pieChartId',
  label: 'PieChartLabel',
  kind: 'PieChart',
  description: {
    id: 'descriptionId',
  },
};

const defaultPieChart: GQLPieChart = {
  label: 'PieChartLabel',
  metadata: pieChartMetadata,
  entries: pieChartentries,
  style: null,
};

const defaultPieChartWithStyle: GQLPieChart = {
  label: 'PieChartLabel',
  metadata: pieChartMetadata,
  entries: pieChartentries,
  style: {
    strokeWidth: 2,
    strokeColor: 'Aquamarine',
    colors: ['CadetBlue', 'CornflowerBlue', 'Lavender', 'LightSteelBlue'],
    bold: true,
    italic: true,
    fontSize: 15,
    strikeThrough: true,
    underline: true,
  },
};

const defaultPieChartWithEmptyStyle: GQLPieChart = {
  label: 'PieChartLabel',
  metadata: pieChartMetadata,
  entries: pieChartentries,
  style: {
    strokeWidth: null,
    strokeColor: '',
    colors: [],
    bold: false,
    italic: false,
    fontSize: null,
    strikeThrough: false,
    underline: false,
  },
};

const barChartentries: GQLBarChartEntry[] = [
  { key: 'a', value: 10 },
  { key: 'b', value: 15 },
  { key: 'c', value: 5 },
  { key: 'd', value: 20 },
];
const barChartMetadata: GQLRepresentationMetadata = {
  id: 'BarChartId',
  label: 'barChartLabel',
  kind: 'BarChart',
  description: {
    id: 'descriptionId',
  },
};
const defaultBarChart: GQLBarChart = {
  label: 'values',
  metadata: barChartMetadata,
  entries: barChartentries,
  style: null,
  width: 500,
  height: 250,
};
const defaultBarChartWithStyle: GQLBarChart = {
  label: 'values',
  metadata: barChartMetadata,
  entries: barChartentries,
  style: {
    barsColor: 'Aquamarine',
    bold: true,
    italic: true,
    fontSize: 15,
    strikeThrough: true,
    underline: true,
  },
  width: 500,
  height: 250,
};

const defaultBarChartWithEmptyStyle: GQLBarChart = {
  label: 'values',
  metadata: barChartMetadata,
  entries: barChartentries,
  style: {
    barsColor: '',
    bold: false,
    italic: false,
    fontSize: 12,
    strikeThrough: false,
    underline: false,
  },
  width: 500,
  height: 250,
};
const defaultPieChartWidget: GQLChartWidget = {
  id: 'id',
  label: 'myPieChart',
  iconURL: [],
  hasHelpText: false,
  chart: defaultPieChart,
  __typename: 'ChartWidget',
  diagnostics: [],
  readOnly: false,
};
const defaultPieChartWidgetWithStyle: GQLChartWidget = {
  id: 'id',
  label: 'myPieChart',
  iconURL: [],
  hasHelpText: false,
  chart: defaultPieChartWithStyle,
  __typename: 'ChartWidget',
  diagnostics: [],
  readOnly: false,
};
const defaultPieChartWidgetWithEmptyStyle: GQLChartWidget = {
  id: 'id',
  label: 'myPieChart',
  iconURL: [],
  hasHelpText: false,
  chart: defaultPieChartWithEmptyStyle,
  __typename: 'ChartWidget',
  diagnostics: [],
  readOnly: false,
};

const defaultBarChartWidget: GQLChartWidget = {
  id: 'id',
  label: 'myBarChart',
  iconURL: [],
  hasHelpText: false,
  chart: defaultBarChart,
  __typename: 'ChartWidget',
  diagnostics: [],
  readOnly: false,
};

const defaultBarChartWidgetWithStyle: GQLChartWidget = {
  id: 'id',
  label: 'myBarChart',
  iconURL: [],
  hasHelpText: false,
  chart: defaultBarChartWithStyle,
  __typename: 'ChartWidget',
  diagnostics: [],
  readOnly: false,
};

const defaultBarChartWidgetWithEmptyStyle: GQLChartWidget = {
  id: 'id',
  label: 'myBarChart',
  iconURL: [],
  hasHelpText: false,
  chart: defaultBarChartWithEmptyStyle,
  __typename: 'ChartWidget',
  diagnostics: [],
  readOnly: false,
};

test('render pie-chart widget', () => {
  const { container } = render(
    <MockedProvider>
      <ChartWidgetPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultPieChartWidget}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('render pie-chart widget with style', () => {
  const { container } = render(
    <MockedProvider>
      <ChartWidgetPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultPieChartWidgetWithStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('render pie-chart widget with empty style', () => {
  const { container } = render(
    <MockedProvider>
      <ChartWidgetPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultPieChartWidgetWithEmptyStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('render the bar-chart widget', () => {
  const { container } = render(
    <MockedProvider>
      <ChartWidgetPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultBarChartWidget}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('render the bar-chart widget with style', () => {
  const { container } = render(
    <MockedProvider>
      <ChartWidgetPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultBarChartWidgetWithStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('render the bar-chart widget with empty style', () => {
  const { container } = render(
    <MockedProvider>
      <ChartWidgetPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={defaultBarChartWidgetWithEmptyStyle}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});

test('render pie-chart widget with help hint', () => {
  const { container } = render(
    <MockedProvider>
      <ChartWidgetPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={{ ...defaultPieChartWidgetWithEmptyStyle, hasHelpText: true }}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
test('render the bar-chart widget with help hint', () => {
  const { container } = render(
    <MockedProvider>
      <ChartWidgetPropertySection
        editingContextId="editingContextId"
        formId="formId"
        widget={{ ...defaultBarChartWidgetWithEmptyStyle, hasHelpText: true }}
        subscribers={[]}
        readOnly={false}
      />
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
