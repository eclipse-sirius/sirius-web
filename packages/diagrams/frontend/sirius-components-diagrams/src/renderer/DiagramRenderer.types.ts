/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { Edge, Node, ReactFlowProps, XYPosition } from '@xyflow/react';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagramRefreshedEventPayload } from '../graphql/subscription/diagramEventSubscription.types';
import { GQLEdgeStyle } from '../graphql/subscription/edgeFragment.types';
import { GQLLabelStyle } from '../graphql/subscription/labelFragment.types';
import { GQLNodeStyle } from '../graphql/subscription/nodeFragment.types';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { ConnectionHandle } from './handles/ConnectionHandles.types';
import { DiagramNodeType } from './node/NodeTypes.types';

export interface DiagramRendererProps {
  diagramRefreshedEventPayload: GQLDiagramRefreshedEventPayload;
}

export interface Diagram {
  nodes: Node<NodeData, DiagramNodeType>[];
  edges: Edge<MultiLabelEdgeData>[];
}

export type OutsideLabelLocation = 'BOTTOM_BEGIN' | 'BOTTOM_MIDDLE' | 'BOTTOM_END';

export type OutsideLabels = Partial<Record<OutsideLabelLocation, OutsideLabel>>;

export interface NodeData extends Record<string, unknown> {
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
  deletable: boolean;
  style: React.CSSProperties;
  connectionHandles: ConnectionHandle[];
  isNew: boolean;
  resizedByUser: boolean;
  movedByUser: boolean;
  isListChild: boolean;
  isDraggedNode: boolean;
  isDropNodeTarget: boolean;
  isDropNodeCandidate: boolean;
  isHovered: boolean;
  isLastNodeSelected: boolean;
  connectionLinePositionOnNode: ConnectionLinePositionOnNode;
  nodeAppearanceData: NodeAppearanceData;
  minComputedWidth: number | null;
  minComputedHeight: number | null;
}

export type ConnectionLinePositionOnNode = 'none' | 'center' | 'border';

export interface NodeAppearanceData {
  customizedStyleProperties: string[];
  gqlStyle: GQLNodeStyle;
}

export interface LabelAppearanceData {
  customizedStyleProperties: string[];
  gqlStyle: GQLLabelStyle;
}

export enum BorderNodePosition {
  NORTH,
  EAST,
  SOUTH,
  WEST,
}

export interface EdgeData extends Record<string, unknown> {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  descriptionId: string;
  label: EdgeLabel | null;
  faded: boolean;
  centerLabelEditable: boolean;
  deletable: boolean;
  bendingPoints: XYPosition[] | null;
  edgePath?: string;
  isHovered: boolean;
  edgeAppearanceData: EdgeAppearanceData;
}

export interface EdgeAppearanceData {
  customizedStyleProperties: string[];
  gqlStyle: GQLEdgeStyle;
}

export interface InsideLabel {
  id: string;
  text: string;
  iconURL: string[];
  style: React.CSSProperties;
  contentStyle: React.CSSProperties;
  isHeader: boolean;
  displayHeaderSeparator: boolean;
  overflowStrategy: LabelOverflowStrategy;
  headerSeparatorStyle: React.CSSProperties;
  headerPosition: HeaderPosition | undefined;
  appearanceData: LabelAppearanceData;
}

export type HeaderPosition = 'TOP' | 'BOTTOM';

export type LabelOverflowStrategy = 'NONE' | 'WRAP' | 'ELLIPSIS';

export interface EdgeLabel {
  id: string;
  text: string;
  iconURL: string[];
  style: React.CSSProperties;
  contentStyle: React.CSSProperties;
  position: XYPosition;
  appearanceData: LabelAppearanceData;
  width: number;
  height: number;
  resizedByUser: boolean;
  overflowStrategy: LabelOverflowStrategy;
}

export interface OutsideLabel {
  id: string;
  text: string;
  iconURL: string[];
  style: React.CSSProperties;
  contentStyle: React.CSSProperties;
  overflowStrategy: LabelOverflowStrategy;
  appearanceData: LabelAppearanceData;
  position: XYPosition;
  width: number;
  height: number;
  resizedByUser: boolean;
}

export type ReactFlowPropsCustomizer = (
  options: ReactFlowProps<Node<NodeData>, Edge<EdgeData>>
) => ReactFlowProps<Node<NodeData>, Edge<EdgeData>>;
