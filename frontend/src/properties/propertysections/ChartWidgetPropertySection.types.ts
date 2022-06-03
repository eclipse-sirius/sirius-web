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
import { BarChart, PieChart } from 'charts/Charts.types';
import { ChartWidget, Subscriber } from 'form/Form.types';

export interface ChartWidgetPropertySectionProps {
  subscribers: Subscriber[];
  widget: ChartWidget;
}
export interface PieChartProps {
  width: number;
  height: number;
  chart: PieChart;
}

export interface BarChartProps {
  width: number;
  height: number;
  chart: BarChart;
}
