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
import { BarChartComponent } from 'charts/barChart/BarChartComponent';
import { BarChart, Chart, PieChart } from 'charts/Charts.types';
import { PieChartComponent } from 'charts/pieChart/PieChartComponent';
import { ChartWidgetPropertySectionProps } from 'properties/propertysections/ChartWidgetPropertySection.types';
import React from 'react';
import { PropertySectionLabel } from './PropertySectionLabel';

/**
 * Defines the content of a Link property section.
 */
export const ChartWidgetPropertySection = ({ widget, subscribers }: ChartWidgetPropertySectionProps) => {
  const { chart } = widget;
  let chartComponent: JSX.Element | null;
  if (isBarChart(chart)) {
    chartComponent = <BarChartComponent width={500} height={250} chart={chart} />;
  } else if (isPieChart(chart)) {
    chartComponent = <PieChartComponent width={300} height={300} chart={chart} />;
  }
  let content: JSX.Element | null;
  if (chartComponent) {
    content = (
      <>
        <PropertySectionLabel label={widget.label} subscribers={subscribers} />
        {chartComponent}
      </>
    );
  }
  return <div>{content}</div>;
};

const isBarChart = (chart: Chart): chart is BarChart => {
  return chart.metadata.kind === 'BarChart';
};

const isPieChart = (chart: Chart): chart is PieChart => {
  return chart.metadata.kind === 'PieChart';
};
