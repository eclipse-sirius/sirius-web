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

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
}
export interface GQLRepresentation {
  id: string;
}
export interface GQLChart extends GQLRepresentation {
  metadata: GQLRepresentationMetadata;
}

export interface GQLBarChart extends GQLChart {
  label: string;
  entries: GQLBarChartEntry[];
}

export interface GQLBarChartEntry {
  key: string;
  value: number;
}
