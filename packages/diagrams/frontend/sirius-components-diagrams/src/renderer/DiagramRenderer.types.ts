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

import { Edge, Node } from 'reactflow';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagramRefreshedEventPayload } from '../graphql/subscription/diagramEventSubscription.types';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { ConnectionHandle } from './handles/ConnectionHandles.types';
import { DiagramNodeType } from './node/NodeTypes.types';

export interface DiagramRendererProps {
  diagramRefreshedEventPayload: GQLDiagramRefreshedEventPayload;
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

export type OutsideLabelLocation = 'BOTTOM_BEGIN' | 'BOTTOM_MIDDLE' | 'BOTTOM_END';

export type OutsideLabels = Partial<Record<OutsideLabelLocation, OutsideLabel>>;

export interface NodeData {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  descriptionId: string;
  insideLabel: InsideLabel | null;
  outsideLabels: OutsideLabels;
  faded: boolean;
  pinned: boolean;
  nodeDescription: GQLNodeDescription;
  defaultWidth: number | null;
  defaultHeight: number | null;
  isBorderNode: boolean;
  borderNodePosition: BorderNodePosition | null;
  labelEditable: boolean;
  style: React.CSSProperties;
  connectionHandles: ConnectionHandle[];
  isNew: boolean;
  resizedByUser: boolean;
  isListChild: boolean;
  isDropNodeTarget: boolean;
  isDropNodeCandidate: boolean;
  isHovered: boolean;
}

export enum BorderNodePosition {
  NORTH,
  EAST,
  SOUTH,
  WEST,
}

export interface EdgeData {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  label: EdgeLabel | null;
  faded: boolean;
  centerLabelEditable: boolean;
}

export interface InsideLabel {
  id: string;
  text: string;
  iconURL: string[];
  style: React.CSSProperties;
  isHeader: boolean;
  displayHeaderSeparator: boolean;
  overflowStrategy: LabelOverflowStrategy;
  headerSeparatorStyle: React.CSSProperties;
  headerPosition: HeaderPosition | undefined;
}

export type HeaderPosition = 'TOP' | 'BOTTOM';

export type LabelOverflowStrategy = 'NONE' | 'WRAP' | 'ELLIPSIS';

export interface EdgeLabel {
  id: string;
  text: string;
  iconURL: string[];
  style: React.CSSProperties;
}

export interface OutsideLabel {
  id: string;
  text: string;
  iconURL: string[];
  style: React.CSSProperties;
  overflowStrategy: LabelOverflowStrategy;
}
