/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { GQLLabel } from './labelFragment.types';

export interface GQLEdge {
  id: string;
  type: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  descriptionId: string;
  sourceId: string;
  targetId: string;
  state: string;
  beginLabel: GQLLabel | null;
  centerLabel: GQLLabel | null;
  endLabel: GQLLabel | null;
  style: GQLEdgeStyle;
  routingPoints: GQLRoutingPoint[];
}

export interface GQLEdgeStyle {
  size: number;
  lineStyle: string;
  sourceArrow: string;
  targetArrow: string;
  color: string;
}

export interface GQLRoutingPoint {
  x: number;
  y: number;
}
