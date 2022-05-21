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
import { BarChart } from 'charts/Charts.type';
import { ChartWidget } from 'form/Form.types';

export interface ChartWidgetPropertySectionProps {
  widget: ChartWidget;
}

export interface BarChartD3ChartProps {
  width: number;
  height: number;
  chart: BarChart;
}
