/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { useData, useSelection } from '@eclipse-sirius/sirius-components-core';
import {
  Background,
  BackgroundVariant,
  ConnectionLineType,
  ConnectionMode,
  Edge,
  EdgeChange,
  MiniMap,
  Node,
  NodeChange,
  OnEdgesChange,
  OnNodesChange,
  ReactFlow,
  ReactFlowProps,
  applyNodeChanges,
  useReactFlow,
  useStoreApi,
} from '@xyflow/react';
import '@xyflow/react/dist/style.css';
import React, { MouseEvent as ReactMouseEvent, memo, useCallback, useContext, useEffect, useMemo, useRef } from 'react';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramContextValue } from '../contexts/DiagramContext.types';
import { NodeTypeContext } from '../contexts/NodeContext';
import { NodeTypeContextValue } from '../contexts/NodeContext.types';
import { useDiagramDescription } from '../contexts/useDiagramDescription';
import { convertDiagram } from '../converter/convertDiagram';
import { useStore } from '../representation/useStore';
import { Diagram, DiagramRendererProps, EdgeData, NodeData, ReactFlowPropsCustomizer } from './DiagramRenderer.types';
import { diagramRendererReactFlowPropsCustomizerExtensionPoint } from './DiagramRendererExtensionPoints';
import { useBorderChange } from './border/useBorderChange';
import { ConnectorPalette } from './connector/ConnectorPalette';
import { useConnector } from './connector/useConnector';
import { useConnectorPalette } from './connector/useConnectorPalette';
import { useResetXYFlowConnection } from './connector/useResetXYFlowConnection';
import { DebugPanel } from './debug/DebugPanel';
import { useDiagramDelete } from './delete/useDiagramDelete';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { useNodesDraggable } from './drag/useNodesDraggable';
import { useDrop } from './drop/useDrop';
import { useDropDiagramStyle } from './dropNode/useDropDiagramStyle';
import { useDropNodes } from './dropNode/useDropNodes';
import { ConnectionLine } from './edge/ConnectionLine';
import { edgeTypes } from './edge/EdgeTypes';
import { useEdgeCrossingFades } from './edge/crossings/useEdgeCrossingFades';
import { useSelectEdgeChange } from './edgeChange/useSelectEdgeChange';
import { useInitialFitToScreen } from './fit-to-screen/useInitialFitToScreen';
import { useHandleChange } from './handles/useHandleChange';
import { useHandleResizedChange } from './handles/useHandleResizedChange';
import { HelperLines } from './helper-lines/HelperLines';
import { HelperLinesContext } from './helper-lines/HelperLinesContext';
import { HelperLinesContextValue } from './helper-lines/HelperLinesContext.types';
import { useHelperLines } from './helper-lines/useHelperLines';
import { useEdgeHover } from './hover/useEdgeHover';
import { useNodeHover } from './hover/useNodeHover';
import { useFilterReadOnlyChanges } from './layout-events/useFilterReadOnlyChanges';
import { useLayoutOnBoundsChange } from './layout-events/useLayoutOnBoundsChange';
import { RawDiagram } from './layout/layout.types';
import { useLayout } from './layout/useLayout';
import { useSynchronizeLayoutData } from './layout/useSynchronizeLayoutData';
import { MiniMapContext } from './mini-map/MiniMapContext';
import { MiniMapContextValue } from './mini-map/MiniMapContext.types';
import { useMoveChange } from './move/useMoveChange';
import { useNodeType } from './node/useNodeType';
import { DiagramPalette } from './palette/DiagramPalette';
import { useDiagramPalette } from './palette/useDiagramPalette';
import { DiagramPanel } from './panel/DiagramPanel';
import { useReconnectEdge } from './reconnect-edge/useReconnectEdge';
import { useResizeChange } from './resize/useResizeChange';
import { useDiagramSelection } from './selection/useDiagramSelection';
import { useLastElementSelectedChange } from './selection/useLastElementSelectedChange';
import { useOnRightClickElement } from './selection/useOnRightClickElement';
import { SnapToGridContext } from './snap-to-grid/SnapToGridContext';
import { SnapToGridContextValue } from './snap-to-grid/SnapToGridContext.types';

const GRID_STEP: number = 10;

export const DiagramRenderer = memo(({ diagramRefreshedEventPayload }: DiagramRendererProps) => {
  const { readOnly, consumePostToolSelection, toolSelections } = useContext<DiagramContextValue>(DiagramContext);
  const { diagramDescription } = useDiagramDescription();
  const { getEdges, onEdgesChange, getNodes, setEdges, setNodes } = useStore();
  const nodes = getNodes();
  const edges = getEdges();
  useEdgeCrossingFades();

  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { onReconnectEdgeStart, reconnectEdge, onReconnectEdgeEnd } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();
  const { onNodesDragStart, onNodesDrag, onNodesDragStop } = useDropNodes();
  const { backgroundColor, largeGridColor, smallGridColor } = useDropDiagramStyle();
  const { nodeTypes } = useNodeType();
  const { setSelection } = useSelection();

  const { nodeConverters } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const { isMiniMapVisible } = useContext<MiniMapContextValue>(MiniMapContext);
  const { isHelperLineEnabled } = useContext<HelperLinesContextValue>(HelperLinesContext);
  const { isSnapToGridEnabled } = useContext<SnapToGridContextValue>(SnapToGridContext);

  useInitialFitToScreen(diagramRefreshedEventPayload.diagram.nodes.length === 0);
  useResetXYFlowConnection();
  const { getNode } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  useEffect(() => {
    const { id, diagram, cause, referencePosition } = diagramRefreshedEventPayload;
    const selectionFromTool = consumePostToolSelection(id);
    const convertedDiagram: Diagram = convertDiagram(
      diagram,
      referencePosition,
      nodeConverters,
      diagramDescription,
      store.getState()
    );

    const shouldForceRefreshInternalNodeDimension = convertedDiagram.nodes.some((node) => {
      const prevNode = getNode(node.id);
      return prevNode?.hidden !== node.hidden;
    });

    convertedDiagram.nodes = convertedDiagram.nodes.map((convertedNode) => {
      const currentNode = getNode(convertedNode.id);
      if (
        currentNode &&
        (convertedNode.position.x !== currentNode.position.x ||
          convertedNode.position.y !== currentNode.position.y ||
          convertedNode.width !== currentNode.width ||
          convertedNode.height !== currentNode.height ||
          convertedNode.hidden !== currentNode.hidden ||
          (currentNode && JSON.stringify(convertedNode.data) !== JSON.stringify(currentNode.data)))
      ) {
        if (shouldForceRefreshInternalNodeDimension) {
          return { ...convertedNode, measured: undefined };
        } else {
          return convertedNode;
        }
      } else if (currentNode) {
        return currentNode;
      } else {
        return convertedNode;
      }
    });
    const { nodeLookup, edgeLookup } = store.getState();
    if (cause === 'layout') {
      // Apply the new graphical selection, either from the applicable selectionFromTool, or from the previous state of the diagram
      const semanticElementsSelected: Set<string> = new Set();
      const shouldSelectNode = (node: Node<NodeData>) => {
        const result =
          !node.hidden &&
          (selectionFromTool
            ? selectionFromTool.entries.some(
                (entry) =>
                  entry.id === node.data.targetObjectId && !semanticElementsSelected.has(node.data.targetObjectId)
              )
            : nodeLookup.get(node.id)?.selected);
        if (selectionFromTool && result) {
          // If we "auto-select" a node because it matches what a previous tool invocation
          // asked, we only select the first matching diagram element we find and ignore the rest,
          // even if the new diagram shows the requested semantic element through multiple
          // nodes.
          semanticElementsSelected.add(node.data.targetObjectId);
        }
        return result;
      };
      const shouldSelectEdge = (edge: Edge<EdgeData>) => {
        const result =
          !edge.hidden &&
          (selectionFromTool
            ? selectionFromTool.entries.some(
                (entry) =>
                  entry.id === edge.data?.targetObjectId && !semanticElementsSelected.has(edge.data.targetObjectId)
              )
            : edgeLookup.get(edge.id)?.selected);
        if (selectionFromTool && result && edge.data?.targetObjectId) {
          semanticElementsSelected.add(edge.data.targetObjectId);
        }
        return result;
      };
      convertedDiagram.nodes = convertedDiagram.nodes.map((node) => ({
        ...node,
        selected: shouldSelectNode(node),
        data: {
          ...node.data,
          isLastNodeSelected: !!getNode(node.id)?.data.isLastNodeSelected,
        },
      }));
      convertedDiagram.edges = convertedDiagram.edges.map((edge) => ({
        ...edge,
        selected: shouldSelectEdge(edge),
      }));

      setEdges(convertedDiagram.edges);
      setNodes(convertedDiagram.nodes);
    } else if (cause === 'refresh') {
      const previousDiagram: RawDiagram = {
        nodes,
        edges,
      };

      // If we're refreshing the diagram because of an undo/redo operation we need to update the previous diagram with nodeLayoutData before performing the layout
      previousDiagram.nodes = previousDiagram.nodes.map((previousNode) => {
        const nodeLayoutData = diagramRefreshedEventPayload.diagram.layoutData.nodeLayoutData.find(
          (layoutData) => layoutData.id === previousNode.id
        );
        if (nodeLayoutData) {
          previousNode.position.x = nodeLayoutData.position.x;
          previousNode.position.y = nodeLayoutData.position.y;
          previousNode.width = nodeLayoutData.size.width;
          previousNode.height = nodeLayoutData.size.height;
        }
        return previousNode;
      });

      layout(
        previousDiagram,
        convertedDiagram,
        diagramRefreshedEventPayload.referencePosition,
        diagramDescription.arrangeLayoutDirection,
        (laidOutDiagram) => {
          laidOutDiagram.nodes = laidOutDiagram.nodes.map((node) => {
            if (nodeLookup.get(node.id)) {
              return {
                ...node,
                selected: !!nodeLookup.get(node.id)?.selected,
              };
            }
            return node;
          });

          laidOutDiagram.edges = laidOutDiagram.edges.map((edge) => {
            if (edgeLookup.get(edge.id)) {
              return {
                ...edge,
                selected: !!edgeLookup.get(edge.id)?.selected,
              };
            }
            return edge;
          });

          setEdges(laidOutDiagram.edges);
          setNodes(laidOutDiagram.nodes);

          if (!readOnly) {
            synchronizeLayoutData(diagramRefreshedEventPayload.id, 'refresh', laidOutDiagram);
          }
        }
      );
    }
    if (selectionFromTool) {
      setSelection(selectionFromTool);
    }
  }, [diagramRefreshedEventPayload, diagramDescription, setSelection]);

  useEffect(() => {
    if (toolSelections.has(diagramRefreshedEventPayload.id)) {
      const { nodeLookup, edgeLookup } = store.getState();
      const selectionFromTool = toolSelections.get(diagramRefreshedEventPayload.id);
      // Apply the new graphical selection, either from the applicable selectionFromTool, or from the previous state of the diagram
      const semanticElementsSelected: Set<string> = new Set();
      const shouldSelectNode = (node: Node<NodeData>) => {
        const result =
          !node.hidden &&
          (selectionFromTool
            ? selectionFromTool.entries.some(
                (entry) =>
                  entry.id === node.data.targetObjectId && !semanticElementsSelected.has(node.data.targetObjectId)
              )
            : nodeLookup.get(node.id)?.selected);
        if (selectionFromTool && result) {
          // If we "auto-select" a node because it matches what a previous tool invocation
          // asked, we only select the first matching diagram element we find and ignore the rest,
          // even if the new diagram shows the requested semantic element through multiple
          // nodes.
          semanticElementsSelected.add(node.data.targetObjectId);
        }
        return result;
      };
      const shouldSelectEdge = (edge: Edge<EdgeData>) => {
        const result =
          !edge.hidden &&
          (selectionFromTool
            ? selectionFromTool.entries.some(
                (entry) =>
                  entry.id === edge.data?.targetObjectId && !semanticElementsSelected.has(edge.data.targetObjectId)
              )
            : edgeLookup.get(edge.id)?.selected);
        if (selectionFromTool && result && edge.data?.targetObjectId) {
          semanticElementsSelected.add(edge.data.targetObjectId);
        }
        return result;
      };
      const newNodes = getNodes().map((node) => ({
        ...node,
        selected: shouldSelectNode(node),
        data: {
          ...node.data,
          isLastNodeSelected: !!getNode(node.id)?.data.isLastNodeSelected,
        },
      }));
      const newEdges = getEdges().map((edge) => ({
        ...edge,
        selected: shouldSelectEdge(edge),
      }));
      setEdges(newEdges);
      setNodes(newNodes);
      toolSelections.delete(diagramRefreshedEventPayload.id);
      if (selectionFromTool) {
        setSelection(selectionFromTool);
      }
    }
  }, [toolSelections]);

  const { transformBorderNodeChanges } = useBorderChange();
  const { transformUndraggableListNodeChanges, applyMoveChange } = useMoveChange();
  const { applyLastElementSelected } = useLastElementSelectedChange();
  const { transformResizeListNodeChanges } = useResizeChange();
  const { applyHandleChange } = useHandleChange();
  const { applyResizeHandleChange } = useHandleResizedChange();
  const { layoutOnBoundsChange } = useLayoutOnBoundsChange();
  const { filterReadOnlyChanges } = useFilterReadOnlyChanges();
  const { horizontalHelperLine, verticalHelperLine, applyHelperLines, resetHelperLines } = useHelperLines();
  const { onSelectionChange, selectedElementsIds } = useDiagramSelection();

  const handleNodesChange: OnNodesChange<Node<NodeData>> = useCallback(
    (changes: NodeChange<Node<NodeData>>[]) => {
      const noReadOnlyChanges = filterReadOnlyChanges(changes);
      const isResetChange = changes.find((change) => change.type === 'replace');
      const isSelectChange = changes.find(
        (change) => change.type === 'select' && !change.id.startsWith('edgeAnchorNodeCreationHandles')
      );

      if (
        isResetChange ||
        isSelectChange ||
        (noReadOnlyChanges.length === 1 &&
          noReadOnlyChanges[0]?.type === 'dimensions' &&
          typeof noReadOnlyChanges[0].resizing !== 'boolean')
      ) {
        setNodes((previousNodes) => {
          const newNodes = applyLastElementSelected(changes, previousNodes, selectedElementsIds);
          return applyNodeChanges<Node<NodeData>>(noReadOnlyChanges, newNodes);
        });
      } else {
        resetHelperLines(changes);
        let transformedNodeChanges: NodeChange<Node<NodeData>>[] = transformBorderNodeChanges(noReadOnlyChanges, nodes);
        transformedNodeChanges = transformUndraggableListNodeChanges(transformedNodeChanges);
        transformedNodeChanges = applyHelperLines(transformedNodeChanges);
        transformedNodeChanges = transformResizeListNodeChanges(transformedNodeChanges);

        let newNodes = applyNodeChanges(transformedNodeChanges, nodes);

        newNodes = applyMoveChange(transformedNodeChanges, newNodes);
        newNodes = applyHandleChange(transformedNodeChanges, newNodes);
        newNodes = applyResizeHandleChange(transformedNodeChanges, newNodes);

        layoutOnBoundsChange(transformedNodeChanges, newNodes);

        setNodes(newNodes);
      }
    },
    [layoutOnBoundsChange, getNodes, getEdges, selectedElementsIds]
  );

  const { onEdgeSelectedChange } = useSelectEdgeChange();
  const handleEdgesChange: OnEdgesChange<Edge<EdgeData>> = useCallback(
    (changes: EdgeChange<Edge<EdgeData>>[]) => {
      if (!readOnly) {
        onEdgeSelectedChange(changes);
      }
      onEdgesChange(changes);
    },
    [onEdgesChange]
  );

  const onKeyDown = useCallback((event: React.KeyboardEvent<Element>) => {
    onDirectEdit(event);
    onDelete(event);
  }, []);

  const { onNodeMouseEnter, onNodeMouseLeave } = useNodeHover();
  const { onEdgeMouseEnter, onEdgeMouseLeave } = useEdgeHover();

  const handleNodeDrag = useCallback(
    (event: ReactMouseEvent, node: Node<NodeData>, nodes: Node<NodeData>[]) => {
      onNodesDrag(event, node, nodes);
    },
    [onNodesDrag]
  );

  const { nodesDraggable } = useNodesDraggable();

  const { isOpened: isDiagramPaletteOpened } = useDiagramPalette();
  const { isOpened: isConnectorPaletteOpened } = useConnectorPalette();

  const { onEdgeContextMenu, onNodeContextMenu, onPaneContextMenu, onSelectionContextMenu } =
    useOnRightClickElement(selectedElementsIds);

  let reactFlowProps: ReactFlowProps<Node<NodeData>, Edge<EdgeData>> = {
    nodes: nodes,
    nodeTypes: nodeTypes,
    onNodesChange: handleNodesChange,
    edges: edges,
    edgeTypes: edgeTypes,
    edgesReconnectable: !readOnly,
    onKeyDown: onKeyDown,
    onConnect: onConnect,
    onConnectStart: onConnectStart,
    onConnectEnd: onConnectEnd,
    connectionLineComponent: ConnectionLine,
    onReconnectStart: onReconnectEdgeStart,
    onReconnect: reconnectEdge,
    onReconnectEnd: onReconnectEdgeEnd,
    connectionRadius: 0,
    onEdgesChange: handleEdgesChange,
    onPaneClick: () => {
      // Select the diagram itself when the user left-clicks on the background
      setSelection({ entries: [{ id: diagramRefreshedEventPayload.diagram.id }] });
    },
    onPaneContextMenu: onPaneContextMenu,
    onEdgeContextMenu: onEdgeContextMenu,
    onNodeContextMenu: onNodeContextMenu,
    onSelectionContextMenu: onSelectionContextMenu,
    nodeDragThreshold: 1,
    onDrop: onDrop,
    onDragOver: onDragOver,
    onNodeDrag: handleNodeDrag,
    onNodeDragStart: onNodesDragStart,
    onNodeDragStop: onNodesDragStop,
    onNodeMouseEnter: onNodeMouseEnter,
    onNodeMouseLeave: onNodeMouseLeave,
    onEdgeMouseEnter: onEdgeMouseEnter,
    onEdgeMouseLeave: onEdgeMouseLeave,
    onSelectionChange: onSelectionChange,

    maxZoom: 40,
    minZoom: 0.1,
    snapToGrid: isSnapToGridEnabled,
    snapGrid: useMemo(() => [GRID_STEP, GRID_STEP], []),
    connectionMode: ConnectionMode.Loose,
    zoomOnDoubleClick: false,
    connectionLineType: ConnectionLineType.SmoothStep,
    nodesDraggable: nodesDraggable && !readOnly,
    tabIndex: -1,
    children: (
      <>
        {isSnapToGridEnabled ? (
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
        <DiagramPanel reactFlowWrapper={ref} />

        {isDiagramPaletteOpened ? (
          <DiagramPalette
            diagramId={diagramRefreshedEventPayload.diagram.id}
            diagramTargetObjectId={diagramRefreshedEventPayload.diagram.targetObjectId}
          />
        ) : null}
        {isConnectorPaletteOpened ? <ConnectorPalette /> : null}
        {diagramDescription.debug ? <DebugPanel reactFlowWrapper={ref} /> : null}
        {isHelperLineEnabled ? <HelperLines horizontal={horizontalHelperLine} vertical={verticalHelperLine} /> : null}
        {isMiniMapVisible && (
          <MiniMap pannable zoomable zoomStep={2} style={{ width: 150, height: 100, opacity: 0.75 }} />
        )}
      </>
    ),
  };

  const { data: reactFlowPropsCustomizers } = useData<Array<ReactFlowPropsCustomizer>>(
    diagramRendererReactFlowPropsCustomizerExtensionPoint
  );
  reactFlowPropsCustomizers.forEach((customizer) => {
    reactFlowProps = customizer(reactFlowProps);
  });

  return <ReactFlow {...reactFlowProps} ref={ref} />;
});
