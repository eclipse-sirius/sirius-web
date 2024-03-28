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

import { Edge, EdgeProps, Node, NodeProps } from '@xyflow/react';
import { FC } from 'react';
import { GQLNodeDescription } from '../graphql/query/nodeDescriptionFragment.types';
import { GQLDiagramRefreshedEventPayload } from '../graphql/subscription/diagramEventSubscription.types';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { ConnectionHandle } from './handles/ConnectionHandles.types';
import { FreeFormNodeData } from './node/FreeFormNode.types';
import { IconLabelNodeData } from './node/IconsLabelNode.types';
import { ListNodeData } from './node/ListNode.types';
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

export interface CommonData {
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
}

export enum BorderNodePosition {
  NORTH,
  EAST,
  SOUTH,
  WEST,
}

export interface CommonEdgeData {
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
}

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
}

export interface EdgeData extends Record<string, unknown>, CommonEdgeData {}
export interface NodeData extends Record<string, unknown>, CommonData {}

export interface NodeDataMap {
  freeFormNode: FreeFormNodeData;
  iconLabelNode: IconLabelNodeData;
  listNode: ListNodeData;
}
export type NodeComponentsMap = {
  [K in keyof NodeDataMap]: FC<NodeProps<Node<NodeDataMap[K], K>>>;
};

export type NodePropsMap = {
  [K in keyof NodeDataMap]: NodeProps<Node<NodeDataMap[K], K>>;
};

export type AnyNodeType = {
  [K in keyof NodeDataMap]: Node<NodeDataMap[K], K>;
}[keyof NodeDataMap];

export interface EdgeDataMap {
  multiLabelEdge: MultiLabelEdgeData;
}
export type EdgeComponentsMap = {
  [K in keyof EdgeDataMap]: FC<EdgeProps<Edge<EdgeDataMap[K], K>>>;
};

export type EdgePropsMap = {
  [K in keyof EdgeDataMap]: EdgeProps<Edge<EdgeDataMap[K], K>>;
};

export type AnyEdgeType = {
  [K in keyof EdgeDataMap]: Edge<EdgeDataMap[K], K>;
}[keyof EdgeDataMap];
