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

import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import React, { useCallback, useContext, useEffect, useRef } from 'react';
import {
  Background,
  BackgroundVariant,
  ConnectionLineType,
  ConnectionMode,
  EdgeChange,
  Node,
  NodeChange,
  NodeDragHandler,
  NodePositionChange,
  OnEdgesChange,
  OnMove,
  OnNodesChange,
  ReactFlow,
  applyNodeChanges,
  useEdgesState,
  useNodesState,
} from 'reactflow';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramContextValue } from '../contexts/DiagramContext.types';
import { NodeTypeContext } from '../contexts/NodeContext';
import { NodeTypeContextValue } from '../contexts/NodeContext.types';
import { useDiagramDescription } from '../contexts/useDiagramDescription';
import { convertDiagram } from '../converter/convertDiagram';
import { Diagram, DiagramRendererProps, NodeData } from './DiagramRenderer.types';
import { useBorderChange } from './border/useBorderChange';
import { ConnectorContextualMenu } from './connector/ConnectorContextualMenu';
import { useConnector } from './connector/useConnector';
import { DebugPanel } from './debug/DebugPanel';
import { useDiagramDelete } from './delete/useDiagramDelete';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { useDrop } from './drop/useDrop';
import { useDropNode } from './dropNode/useDropNode';
import { ConnectionLine } from './edge/ConnectionLine';
import { edgeTypes } from './edge/EdgeTypes';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { useInitialFitToScreen } from './fit-to-screen/useInitialFitToScreen';
import { useHandleChange } from './handles/useHandleChange';
import { useNodeHover } from './hover/useNodeHover';
import { useFilterReadOnlyChanges } from './layout-events/useFilterReadOnlyChanges';
import { useLayoutOnBoundsChange } from './layout-events/useLayoutOnBoundsChange';
import { RawDiagram } from './layout/layout.types';
import { useLayout } from './layout/useLayout';
import { useSynchronizeLayoutData } from './layout/useSynchronizeLayoutData';
import { useMoveChange } from './move/useMoveChange';
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
import { useResizeChange } from './resize/useResizeChange';

const GRID_STEP: number = 10;

export const DiagramRenderer = ({ diagramRefreshedEventPayload }: DiagramRendererProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { diagramDescription } = useDiagramDescription();
  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

  const { onDiagramBackgroundClick, hideDiagramPalette, isOpened: isDiagramPaletteOpened } = useDiagramPalette();
  const {
    onDiagramElementClick,
    hideDiagramElementPalette,
    isOpened: isDiagramElementPaletteOpened,
  } = useDiagramElementPalette();

  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { reconnectEdge } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();
  const { onNodeDragStart, onNodeDrag, onNodeDragStop, diagramBackgroundStyle, targetNodeId, draggedNode } =
    useDropNode();
  const { nodeTypes } = useNodeType();

  const [nodes, setNodes, onNodesChange] = useNodesState<NodeData>([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState<MultiLabelEdgeData>([]);

  const { nodeConverters } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const { fitToScreen } = useInitialFitToScreen();
  const { setSelection } = useSelection();

  useEffect(() => {
    const { diagram, cause } = diagramRefreshedEventPayload;
    const convertedDiagram: Diagram = convertDiagram(diagram, nodeConverters, diagramDescription);

    const selectedNodeIds = nodes.filter((node) => node.selected).map((node) => node.id);
    if (cause === 'layout') {
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
        laidOutDiagram.nodes
          .filter((node) => selectedNodeIds.includes(node.id))
          .forEach((node) => (node.selected = true));

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
  const { transformUndraggableListNodeChanges, applyMoveChange } = useMoveChange();
  const { transformResizeListNodeChanges } = useResizeChange();
  const { applyHandleChange } = useHandleChange();
  const { layoutOnBoundsChange } = useLayoutOnBoundsChange(diagramRefreshedEventPayload.id);
  const { filterReadOnlyChanges } = useFilterReadOnlyChanges();

  const handleNodesChange: OnNodesChange = useCallback(
    (changes: NodeChange[]) => {
      const noReadOnlyChanges = filterReadOnlyChanges(changes);
      if (
        noReadOnlyChanges.length === 1 &&
        noReadOnlyChanges[0]?.type === 'dimensions' &&
        typeof noReadOnlyChanges[0].resizing !== 'boolean'
      ) {
        setNodes((oldNodes) => applyNodeChanges(noReadOnlyChanges, oldNodes));
      } else {
        setNodes((oldNodes) => {
          let transformedNodeChanges = transformBorderNodeChanges(noReadOnlyChanges);
          transformedNodeChanges = transformUndraggableListNodeChanges(transformedNodeChanges);
          transformedNodeChanges = transformResizeListNodeChanges(transformedNodeChanges);

          if (transformedNodeChanges.some((change) => change.type === 'position')) {
            hideDiagramElementPalette();
          }

          let newNodes = applyNodeChanges(transformedNodeChanges, oldNodes);

          newNodes = applyMoveChange(transformedNodeChanges, newNodes);
          newNodes = applyHandleChange(transformedNodeChanges, newNodes as Node<NodeData, DiagramNodeType>[]);
          setNodes(newNodes);
          layoutOnBoundsChange(transformedNodeChanges, newNodes as Node<NodeData, DiagramNodeType>[]);

          updateSelectionOnNodesChange(noReadOnlyChanges);
          return newNodes;
        });
      }
    },
    [setNodes, targetNodeId, draggedNode?.id]
  );

  const handleEdgesChange: OnEdgesChange = (changes: EdgeChange[]) => {
    onEdgesChange(changes);
    updateSelectionOnEdgesChange(changes);
  };

  const handlePaneClick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
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
    },
    [setSelection]
  );

  const onKeyDown = useCallback((event: React.KeyboardEvent<Element>) => {
    onDirectEdit(event);
    onDelete(event);
  }, []);

  const { snapToGrid, onSnapToGrid } = useSnapToGrid();

  const { backgroundColor, smallGridColor, largeGridColor } = diagramBackgroundStyle;

  const handleMove: OnMove = useCallback(() => {
    hideDiagramPalette();
    hideDiagramElementPalette();
  }, [isDiagramElementPaletteOpened, isDiagramPaletteOpened]);

  const handleNodeDragStop: NodeDragHandler = onNodeDragStop((node: Node) => {
    const resetPosition: NodePositionChange = {
      id: node.id,
      type: 'position',
      position: node.position,
      positionAbsolute: node.positionAbsolute,
    };
    onNodesChange([resetPosition]);
  });

  const { onNodeMouseEnter, onNodeMouseLeave } = useNodeHover();

  return (
    <ReactFlow
      nodes={nodes}
      nodeTypes={nodeTypes}
      onNodesChange={handleNodesChange}
      edges={edges}
      edgeTypes={edgeTypes}
      edgesUpdatable={!readOnly}
      onKeyDown={onKeyDown}
      onConnect={onConnect}
      onConnectStart={onConnectStart}
      onConnectEnd={onConnectEnd}
      connectionLineComponent={ConnectionLine}
      onEdgesChange={handleEdgesChange}
      onEdgeUpdate={reconnectEdge}
      onPaneClick={handlePaneClick}
      onEdgeClick={onDiagramElementClick}
      onNodeClick={onDiagramElementClick}
      onMove={handleMove}
      nodeDragThreshold={1}
      onDrop={onDrop}
      onDragOver={onDragOver}
      onNodeDrag={onNodeDrag}
      onNodeDragStart={onNodeDragStart}
      onNodeDragStop={handleNodeDragStop}
      onNodeMouseEnter={onNodeMouseEnter}
      onNodeMouseLeave={onNodeMouseLeave}
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
      <DiagramPanel
        snapToGrid={snapToGrid}
        onSnapToGrid={onSnapToGrid}
        refreshEventPayloadId={diagramRefreshedEventPayload.id}
      />

      <DiagramPalette diagramElementId={diagramRefreshedEventPayload.diagram.id} />
      {diagramDescription.debug ? <DebugPanel reactFlowWrapper={ref} /> : null}
      <ConnectorContextualMenu />
    </ReactFlow>
  );
};
