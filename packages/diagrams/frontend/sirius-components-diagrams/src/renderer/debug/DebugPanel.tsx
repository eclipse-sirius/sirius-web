/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo and others.
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

import Box from '@material-ui/core/Box';
import { Edge, Node, Panel, useReactFlow } from '@xyflow/react';
import { useContext } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { NodeContext } from '../node/NodeContext';
import { NodeContextValue } from '../node/NodeContext.types';
import { DebugPanelProps } from './DebugPanel.types';
import { EdgePanelInfos } from './EdgePanelInfos';
import { NodePanelInfos } from './NodePanelInfos';

const debugPanelStyle = (reactFlowBounds: number): React.CSSProperties => {
  const debugPanelStyles: React.CSSProperties = {
    overflowX: 'auto',
    maxWidth: reactFlowBounds,
  };

  return debugPanelStyles;
};

export const DebugPanel = ({ reactFlowWrapper }: DebugPanelProps) => {
  const reactFlowInstance = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const selectedNodes: Node<NodeData>[] = reactFlowInstance.getNodes().filter((node) => node.selected);
  const selectedEdges: Edge<EdgeData>[] = reactFlowInstance.getEdges().filter((edge) => edge.selected);
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);
  const reactFlowBounds = reactFlowWrapper?.current?.getBoundingClientRect().width ?? 0;

  return (
    <Panel position="bottom-left" style={debugPanelStyle(reactFlowBounds)}>
      <Box display="flex" style={{ columnGap: '5px' }}>
        {hoveredNode ? <NodePanelInfos title={'Hovered Node'} node={hoveredNode}></NodePanelInfos> : ''}
        {selectedNodes.map((selectedNode, index) => {
          return <NodePanelInfos title={'Selected Node'} node={selectedNode} key={index}></NodePanelInfos>;
        })}
        {selectedEdges.map((selectedEdge, index) => {
          return <EdgePanelInfos title={'Selected Edge'} edge={selectedEdge} key={index}></EdgePanelInfos>;
        })}
      </Box>
    </Panel>
  );
};
