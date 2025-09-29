/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { Theme, useTheme } from '@mui/material/styles';
import { Connection, Edge, Node, useReactFlow, useStoreApi } from '@xyflow/react';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getEdgeParameters } from '../edge/EdgeLayout';
import { UseTemporaryEdgeValue } from './useTemporaryEdge.types';

const tempConnectionLineStyle = (theme: Theme): React.CSSProperties => {
  return {
    stroke: theme.palette.selected,
    strokeWidth: theme.spacing(0.2),
  };
};

export const useTemporaryEdge = (): UseTemporaryEdgeValue => {
  const { diagramDescription } = useDiagramDescription();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = store.getState();
  const { setEdges, setNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const theme = useTheme();

  const addTempConnectionLine = (connection: Connection) => {
    const sourceNode = nodeLookup.get(connection?.source ?? '');
    const targetNode = nodeLookup.get(connection?.target ?? '');
    if (sourceNode && targetNode && !!connection) {
      const { targetPosition, sourcePosition } = getEdgeParameters(
        sourceNode,
        targetNode,
        nodeLookup,
        diagramDescription.arrangeLayoutDirection,
        []
      );

      const edge: Edge<EdgeData> = {
        id: 'temp',
        source: connection.source ?? '',
        target: connection.target ?? '',
        sourceHandle: `creationhandle--${connection.source}--${sourcePosition}`,
        targetHandle: `handle--${connection.target}--temp--${targetPosition}`,
        type: 'smoothstep',
        animated: true,
        reconnectable: false,
        style: tempConnectionLineStyle(theme),
        zIndex: 2002,
      };
      setEdges((previousEdges) => [...previousEdges, edge]);
    }
  };

  const removeTempConnectionLine = () => {
    setEdges((previousEdges) => previousEdges.filter((previousEdge) => !previousEdge.id.includes('temp')));
    setNodes((previousNodes) =>
      previousNodes.map((previousNode) => {
        if (previousNode.data.connectionLinePositionOnNode !== 'none' || previousNode.data.isHovered) {
          return {
            ...previousNode,
            data: {
              ...previousNode.data,
              connectionLinePositionOnNode: 'none',
              isHovered: false,
            },
          };
        }
        return previousNode;
      })
    );
  };

  return {
    addTempConnectionLine,
    removeTempConnectionLine,
  };
};
