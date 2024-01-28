/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { GQLEdge } from './edgeFragment.types';
import { GQLNode, GQLNodeStyle, GQLPosition, GQLSize } from './nodeFragment.types';

export interface GQLDiagram {
  id: string;
  targetObjectId: string;
  metadata: GQLRepresentationMetadata;
  nodes: GQLNode<GQLNodeStyle>[];
  edges: GQLEdge[];
  layoutData: GQLDiagramLayoutData;
}

export interface GQLDiagramLayoutData {
  nodeLayoutData: GQLNodeLayoutData[];
}

export interface GQLNodeLayoutData {
  id: string;
  position: GQLPosition;
  size: GQLSize;
  resizedByUser: boolean;
}

export interface GQLRepresentationMetadata {
  kind: string;
  label: string;
}
