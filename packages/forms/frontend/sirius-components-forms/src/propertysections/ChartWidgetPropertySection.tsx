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
import { BarChart, PieChart } from '@eclipse-sirius/sirius-components-charts';
import { makeStyles, Theme } from '@material-ui/core/styles';
import { GQLBarChart, GQLChart, GQLPieChart } from '../form/FormEventFragments.types';
import { ChartWidgetPropertySectionProps } from './ChartWidgetPropertySection.types';
import { PropertySectionLabel } from './PropertySectionLabel';

const useStyles = makeStyles<Theme>(() => ({
  chart: {
    overflowX: 'auto',
  },
}));

/**
 * Defines the content of a Chart property section.
 */
export const ChartWidgetPropertySection = ({ widget, subscribers }: ChartWidgetPropertySectionProps) => {
  const classes = useStyles();
  const { chart } = widget;
  let chartComponent: JSX.Element | null;
  if (isBarChart(chart)) {
    chartComponent = (
      <div className={classes.chart}>
        <BarChart chart={chart} />
      </div>
    );
  } else if (isPieChart(chart)) {
    chartComponent = <PieChart width={300} height={300} chart={chart} />;
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

const isBarChart = (chart: GQLChart): chart is GQLBarChart => {
  return chart.metadata.kind === 'BarChart';
};

const isPieChart = (chart: GQLChart): chart is GQLPieChart => {
  return chart.metadata.kind === 'PieChart';
};
