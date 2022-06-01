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

export interface RepresentationMetadata {
  kind: string;
  description: RepresentationDescription;
}

export interface RepresentationDescription {
  id: string;
}
export interface Representation {
  id: string;
}
export interface Chart extends Representation {
  metadata: RepresentationMetadata;
}

export interface BarChart extends Chart {
  label: string;
  entries: BarChartEntry[];
}

export interface BarChartEntry {
  key: string;
  value: number;
}
