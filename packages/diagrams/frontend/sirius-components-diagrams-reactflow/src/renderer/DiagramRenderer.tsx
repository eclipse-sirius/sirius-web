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

import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import React, { useContext, useEffect, useRef } from 'react';
import {
  Background,
  BackgroundVariant,
  ConnectionLineType,
  ConnectionMode,
  EdgeChange,
  Node,
  NodeChange,
  NodePositionChange,
  OnEdgesChange,
  OnNodesChange,
  ReactFlow,
  applyNodeChanges,
  useEdgesState,
  useNodesState,
} from 'reactflow';
import { NodeTypeContext } from '../contexts/NodeContext';
import { NodeTypeContextValue } from '../contexts/NodeContext.types';
import { useDiagramDescription } from '../contexts/useDiagramDescription';
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
import { useHandleChange } from './handles/useHandleChange';
import { useLayoutOnBoundsChange } from './layout-events/useLayoutOnBoundsChange';
import { RawDiagram } from './layout/layout.types';
import { useLayout } from './layout/useLayout';
import { useSynchronizeLayoutData } from './layout/useSynchronizeLayoutData';
import { NodeContext } from './node/NodeContext';
import { NodeContextValue } from './node/NodeContext.types';
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

export const DiagramRenderer = ({ diagramRefreshedEventPayload }: DiagramRendererProps) => {
  const { diagramDescription } = useDiagramDescription();
  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { onDiagramBackgroundClick, hideDiagramPalette } = useDiagramPalette();
  const { onDiagramElementClick, hideDiagramElementPalette } = useDiagramElementPalette();

  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { reconnectEdge } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();
  const { getNodeTypes } = useNodeType();

  const [nodes, setNodes, onNodesChange] = useNodesState<NodeData>([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState<MultiLabelEdgeData>([]);

  const { nodeConverters } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const { fitToScreen } = useInitialFitToScreen();
  const { setSelection } = useSelection();

  useEffect(() => {
    const { diagram, cause } = diagramRefreshedEventPayload;
    const convertedDiagram: Diagram = convertDiagram(diagram, nodeConverters, diagramDescription.nodeDescriptions);

    if (cause === 'layout') {
      const selectedNodeIds = nodes.filter((node) => node.selected).map((node) => node.id);
      convertedDiagram.nodes
        .filter((node) => selectedNodeIds.includes(node.id))
        .forEach((node) => (node.selected = true));

      setNodes(convertedDiagram.nodes);
      setEdges(convertedDiagram.edges);
      fitToScreen();
    } else if (cause === 'refresh') {
      const previousDiagram: RawDiagram = {
        nodes: nodes as Node<NodeData, DiagramNodeType>[],
        edges,
      };
      layout(previousDiagram, convertedDiagram, diagramRefreshedEventPayload.referencePosition, (laidOutDiagram) => {
        setNodes(laidOutDiagram.nodes);
        setEdges(laidOutDiagram.edges);
        hideDiagramPalette();
        hideDiagramElementPalette();

        synchronizeLayoutData(diagramRefreshedEventPayload.id, laidOutDiagram);
      });
    }
  }, [diagramRefreshedEventPayload, diagramDescription]);

  const { updateSelectionOnNodesChange, updateSelectionOnEdgesChange } = useDiagramSelection();
  const { transformBorderNodeChanges } = useBorderChange();
  const { applyHandleChange } = useHandleChange();
  const { layoutOnBoundsChange } = useLayoutOnBoundsChange(diagramRefreshedEventPayload.id);

  const handleNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    const transformedNodeChanges = transformBorderNodeChanges(changes);

    if (transformedNodeChanges.some((change) => change.type === 'position')) {
      hideDiagramElementPalette();
    }

    let newNodes = applyNodeChanges(transformedNodeChanges, nodes);

    newNodes = applyHandleChange(transformedNodeChanges, newNodes as Node<NodeData, DiagramNodeType>[]);
    setNodes(newNodes);
    layoutOnBoundsChange(transformedNodeChanges, newNodes as Node<NodeData, DiagramNodeType>[]);

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

  const { onNodeDragStart, onNodeDrag, onNodeDragStop, diagramBackgroundStyle } = useDropNode();
  const { backgroundColor, smallGridColor, largeGridColor } = diagramBackgroundStyle;
  const { setHoveredNode } = useContext<NodeContextValue>(NodeContext);

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
      onNodeMouseEnter={(_event: React.MouseEvent<Element, MouseEvent>, node: Node<NodeData>) => {
        setHoveredNode(node);
      }}
      onNodeMouseLeave={() => setHoveredNode(null)}
      maxZoom={40}
      minZoom={0.1}
      snapToGrid={snapToGrid}
      snapGrid={[GRID_STEP, GRID_STEP]}
      connectionMode={ConnectionMode.Loose}
      zoomOnDoubleClick={false}
      connectionLineType={ConnectionLineType.SmoothStep}
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
