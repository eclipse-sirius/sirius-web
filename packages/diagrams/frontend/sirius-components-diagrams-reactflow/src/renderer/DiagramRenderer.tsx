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
import React, { useContext, useEffect, useRef } from 'react';
import {
  Background,
  BackgroundVariant,
  ConnectionMode,
  EdgeChange,
  Node,
  NodeChange,
  NodePositionChange,
  OnEdgesChange,
  OnNodesChange,
  ReactFlow,
  useEdgesState,
  useNodesState,
} from 'reactflow';
import { NodeTypeContext } from '../contexts/NodeContext';
import { NodeTypeContextValue } from '../contexts/NodeContext.types';
import { convertDiagram } from '../converter/convertDiagram';
import { Diagram, DiagramRendererProps, NodeData } from './DiagramRenderer.types';
import { useBorderChange } from './border/useBorderChange';
import { ConnectorContextualMenu } from './connector/ConnectorContextualMenu';
import { useConnector } from './connector/useConnector';
import { useDiagramDelete } from './delete/useDiagramDelete';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { useDrop } from './drop/useDrop';
import { useDropNode } from './dropNode/useDropNode';
import { edgeTypes } from './edge/EdgeTypes';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { useInitialFitToScreen } from './fit-to-screen/useInitialFitToScreen';
import { useLayout } from './layout/useLayout';
import { DiagramNodeType } from './node/NodeTypes.types';
import { useNodeType } from './node/useNodeType';
import { DiagramPalette } from './palette/DiagramPalette';
import { useDiagramElementPalette } from './palette/useDiagramElementPalette';
import { useDiagramPalette } from './palette/useDiagramPalette';
import { DiagramPanel } from './panel/DiagramPanel';
import { useReconnectEdge } from './reconnect-edge/useReconnectEdge';
import { useDiagramSelection } from './selection/useDiagramSelection';
import { useSnapToGrid } from './snap-to-grid/useSnapToGrid';

import 'reactflow/dist/style.css';

const GRID_STEP: number = 10;

export const DiagramRenderer = ({
  diagramRefreshedEventPayload,
  diagramDescription,
  selection,
  setSelection,
}: DiagramRendererProps) => {
  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const { layout, resetReferencePosition } = useLayout();
  const { onDiagramBackgroundClick, hideDiagramPalette } = useDiagramPalette();
  const { onDiagramElementClick, hideDiagramElementPalette } = useDiagramElementPalette();

  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { reconnectEdge } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();
  const { onBorderChange } = useBorderChange();
  const { getNodeTypes } = useNodeType();

  const [nodes, setNodes, onNodesChange] = useNodesState<NodeData>([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState<MultiLabelEdgeData>([]);

  const { nodeConverterHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const { fitToScreen } = useInitialFitToScreen();

  useEffect(() => {
    const { diagram } = diagramRefreshedEventPayload;
    const convertedDiagram: Diagram = convertDiagram(
      diagram,
      nodeConverterHandlers,
      diagramDescription.nodeDescriptions
    );

    const previousDiagram: Diagram = {
      metadata: { ...convertedDiagram.metadata },
      nodes: nodes as Node<NodeData, DiagramNodeType>[],
      edges,
    };
    layout(previousDiagram, convertedDiagram, (laidOutDiagram) => {
      setNodes(laidOutDiagram.nodes);
      setEdges(laidOutDiagram.edges);
      hideDiagramPalette();
      resetReferencePosition();
      fitToScreen();
    });
  }, [diagramRefreshedEventPayload, diagramDescription]);

  const { updateSelectionOnNodesChange, updateSelectionOnEdgesChange } = useDiagramSelection(selection, setSelection);

  const handleNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    onNodesChange(onBorderChange(changes));
    updateSelectionOnNodesChange(changes);
  };

  const handleEdgesChange: OnEdgesChange = (changes: EdgeChange[]) => {
    onEdgesChange(changes);
    updateSelectionOnEdgesChange(changes);
  };

  const handlePaneClick = (event: React.MouseEvent<Element, MouseEvent>) => {
    const {
      diagram: {
        id,
        metadata: { kind, label },
      },
    } = diagramRefreshedEventPayload;
    const selection: Selection = {
      entries: [
        {
          id,
          kind,
          label,
        },
      ],
    };
    setSelection(selection);
    onDiagramBackgroundClick(event);
  };

  const onKeyDown = (event: React.KeyboardEvent<Element>) => {
    onDirectEdit(event);
    onDelete(event);
  };

  const { snapToGrid, onSnapToGrid } = useSnapToGrid();

  const { onNodeDragStart, onNodeDrag, onNodeDragStop, dropFeedbackStyleProvider } = useDropNode();

  const { backgroundColor, smallGridColor, largeGridColor } = dropFeedbackStyleProvider.getDiagramBackgroundStyle();

  return (
    <ReactFlow
      nodes={nodes}
      nodeTypes={getNodeTypes()}
      onNodesChange={handleNodesChange}
      edges={edges}
      edgeTypes={edgeTypes}
      onKeyDown={onKeyDown}
      onConnect={onConnect}
      onConnectStart={onConnectStart}
      onConnectEnd={onConnectEnd}
      onEdgesChange={handleEdgesChange}
      onEdgeUpdate={reconnectEdge}
      onPaneClick={handlePaneClick}
      onEdgeClick={onDiagramElementClick}
      onNodeClick={onDiagramElementClick}
      onMove={() => {
        hideDiagramPalette();
        hideDiagramElementPalette();
      }}
      onDrop={onDrop}
      onDragOver={onDragOver}
      onNodeDrag={onNodeDrag}
      onNodeDragStart={onNodeDragStart}
      onNodeDragStop={onNodeDragStop((node: Node) => {
        const resetPosition: NodePositionChange = {
          id: node.id,
          type: 'position',
          position: node.position,
          positionAbsolute: node.positionAbsolute,
        };
        onNodesChange([resetPosition]);
      })}
      maxZoom={40}
      minZoom={0.1}
      snapToGrid={snapToGrid}
      snapGrid={[GRID_STEP, GRID_STEP]}
      connectionMode={ConnectionMode.Loose}
      zoomOnDoubleClick={false}
      ref={ref}>
      {snapToGrid ? (
        <>
          <Background
            id="small-grid"
            style={{ backgroundColor }}
            variant={BackgroundVariant.Lines}
            gap={GRID_STEP}
            color={smallGridColor}
          />
          <Background
            id="large-grid"
            variant={BackgroundVariant.Lines}
            gap={10 * GRID_STEP}
            offset={1}
            color={largeGridColor}
          />
        </>
      ) : (
        <Background style={{ backgroundColor }} color={backgroundColor} />
      )}
      <DiagramPanel snapToGrid={snapToGrid} onSnapToGrid={onSnapToGrid} />
      <DiagramPalette targetObjectId={diagramRefreshedEventPayload.diagram.id} />
      <ConnectorContextualMenu />
    </ReactFlow>
  );
};
