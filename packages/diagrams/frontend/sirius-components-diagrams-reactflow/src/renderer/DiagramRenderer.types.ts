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

import { Selection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node } from 'reactflow';
import { GQLDiagramDescription, GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagramRefreshedEventPayload } from '../graphql/subscription/diagramEventSubscription.types';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { DiagramNodeType } from './node/NodeTypes.types';

export interface DiagramRendererProps {
  diagramRefreshedEventPayload: GQLDiagramRefreshedEventPayload;
  diagramDescription: GQLDiagramDescription;
  selection: Selection;
  setSelection: (selection: Selection) => void;
}

export interface DiagramRendererState {
  snapToGrid: boolean;
}

export interface DiagramPaletteState {
  opened: boolean;
  x: number;
  y: number;
}

export interface Diagram {
  metadata: DiagramMetadata;
  nodes: Node<NodeData, DiagramNodeType>[];
  edges: Edge<MultiLabelEdgeData>[];
}

export interface DiagramMetadata {
  id: string;
  kind: string;
  label: string;
  targetObjectId: string;
}

export interface NodeData {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  descriptionId: string;
  label: Label | undefined;
  faded: boolean;
  nodeDescription: GQLNodeDescription | undefined;
  defaultWidth: number | null;
  defaultHeight: number | null;
  isBorderNode: boolean;
  borderNodePosition: BorderNodePositon | null;
  labelEditable: boolean;
  style: React.CSSProperties;
}

export enum BorderNodePositon {
  NORTH,
  EAST,
  SOUTH,
  WEST,
}

export interface EdgeData {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  label: Label | null;
  faded: boolean;
}

export interface Label {
  id: string;
  text: string;
  iconURL: string[];
  style: React.CSSProperties;
  isHeader?: boolean;
  displayHeaderSeparator?: boolean;
}
