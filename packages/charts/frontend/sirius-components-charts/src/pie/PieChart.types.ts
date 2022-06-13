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

import { FontStyle } from '../Charts.types';

export interface PieChartProps {
  width: number;
  height: number;
  chart: PieChartRepresentation;
}

export interface PieChartRepresentation {
  entries: PieChartRepresentationEntry[];
  style: PieChartStyle | null;
}

export interface PieChartRepresentationEntry {
  key: string;
  value: number;
}

export interface PieChartStyle extends FontStyle {
  colors: string[] | null;
  strokeColor: string | null;
  strokeWidth: number | null;
}
