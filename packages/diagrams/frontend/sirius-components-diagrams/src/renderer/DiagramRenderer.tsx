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

import { Selection, useData, useSelection } from '@eclipse-sirius/sirius-components-core';
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
  OnMove,
  OnNodesChange,
  ReactFlow,
  ReactFlowProps,
  applyNodeChanges,
  useStoreApi,
} from '@xyflow/react';
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
import { ConnectorContextualMenu } from './connector/ConnectorContextualMenu';
import { useConnector } from './connector/useConnector';
import { DebugPanel } from './debug/DebugPanel';
import { useDiagramDelete } from './delete/useDiagramDelete';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { useNodesDraggable } from './drag/useNodesDraggable';
import { useDrop } from './drop/useDrop';
import { useDropDiagramStyle } from './dropNode/useDropDiagramStyle';
import { useDropNode } from './dropNode/useDropNode';
import { ConnectionLine } from './edge/ConnectionLine';
import { edgeTypes } from './edge/EdgeTypes';
import { useEdgeType } from './edge/useEdgeType';
import { useInitialFitToScreen } from './fit-to-screen/useInitialFitToScreen';
import { useHandleChange } from './handles/useHandleChange';
import { HelperLines } from './helper-lines/HelperLines';
import { useHelperLines } from './helper-lines/useHelperLines';
import { useNodeHover } from './hover/useNodeHover';
import { useFilterReadOnlyChanges } from './layout-events/useFilterReadOnlyChanges';
import { useLayoutOnBoundsChange } from './layout-events/useLayoutOnBoundsChange';
import { RawDiagram } from './layout/layout.types';
import { useLayout } from './layout/useLayout';
import { useSynchronizeLayoutData } from './layout/useSynchronizeLayoutData';
import { useMoveChange } from './move/useMoveChange';
import { useNodeType } from './node/useNodeType';
import { DiagramPalette } from './palette/DiagramPalette';
import { GroupPalette } from './palette/group-tool/GroupPalette';
import { useGroupPalette } from './palette/group-tool/useGroupPalette';
import { useDiagramElementPalette } from './palette/useDiagramElementPalette';
import { useDiagramPalette } from './palette/useDiagramPalette';
import { DiagramPanel } from './panel/DiagramPanel';
import { useReconnectEdge } from './reconnect-edge/useReconnectEdge';
import { useResizeChange } from './resize/useResizeChange';
import { useDiagramSelection } from './selection/useDiagramSelection';
import { useShiftSelection } from './selection/useShiftSelection';
import { useSnapToGrid } from './snap-to-grid/useSnapToGrid';

import '@xyflow/react/dist/style.css';

const GRID_STEP: number = 10;

export const DiagramRenderer = memo(({ diagramRefreshedEventPayload }: DiagramRendererProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { diagramDescription } = useDiagramDescription();
  const { getEdges, onEdgesChange, getNodes, setEdges, setNodes } = useStore();
  const nodes = getNodes();
  const edges = getEdges();

  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const {
    onDiagramBackgroundClick,
    onDiagramElementClick: diagramPaletteOnDiagramElementClick,
    hideDiagramPalette,
    isOpened: isDiagramPaletteOpened,
  } = useDiagramPalette();
  const {
    onDiagramElementClick: elementPaletteOnDiagramElementClick,
    hideDiagramElementPalette,
    isOpened: isDiagramElementPaletteOpened,
  } = useDiagramElementPalette();

  const {
    onDiagramElementClick: groupPaletteOnDiagramElementClick,
    hideGroupPalette,
    position: groupPalettePosition,
    isOpened: isGroupPaletteOpened,
    refElementId: groupPaletteRefElementId,
  } = useGroupPalette();

  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { reconnectEdge } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();
  const { onNodeDragStart, onNodeDrag, onNodeDragStop } = useDropNode();
  const { background, setBackground, largeGridColor, smallGridColor } = useDropDiagramStyle();
  const { nodeTypes } = useNodeType();

  const { nodeConverters } = useContext<NodeTypeContextValue>(NodeTypeContext);

  const { selection, setSelection } = useSelection();
  const { edgeType, setEdgeType } = useEdgeType();

  useInitialFitToScreen();

  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  useEffect(() => {
    const { diagram, cause } = diagramRefreshedEventPayload;
    const convertedDiagram: Diagram = convertDiagram(diagram, nodeConverters, diagramDescription, edgeType);

    if (cause === 'layout') {
      const diagramElementIds: string[] = [
        ...getNodes().map((node) => node.data.targetObjectId),
        ...getEdges().map((edge) => edge.data?.targetObjectId ?? ''),
      ];

      const selectionDiagramEntryIds = selection.entries
        .map((entry) => entry.id)
        .filter((id) => diagramElementIds.includes(id))
        .sort((id1: string, id2: string) => id1.localeCompare(id2));
      const selectedDiagramElementIds = [
        ...new Set(
          [...getNodes(), ...getEdges()]
            .filter((element) => element.selected)
            .map((element) => element.data?.targetObjectId ?? '')
        ),
      ];
      selectedDiagramElementIds.sort((id1: string, id2: string) => id1.localeCompare(id2));

      const semanticElementsViews: Map<string, string[]> = new Map();
      [...getNodes(), ...getEdges()].forEach((element) => {
        const viewId = element.id;
        const semanticElementId = element.data?.targetObjectId ?? '';
        if (!semanticElementsViews.has(semanticElementId)) {
          semanticElementsViews.set(semanticElementId, [viewId]);
        } else {
          semanticElementsViews.get(semanticElementId)?.push(viewId);
        }
      });

      // For each selected semantic element which appears on the diagram,
      // determine which of its views should be selected.
      const viewsToSelect: Map<string, string[]> = new Map();
      const previouslySelectedViews = [...getNodes(), ...getEdges()].filter((element) => element.selected);
      for (var semanticElementId of selectionDiagramEntryIds) {
        const allRelatedViews = semanticElementsViews.get(semanticElementId) || [];
        const alreadySelectedViews = allRelatedViews.filter(
          (viewId) => !!previouslySelectedViews.find((view: Node<NodeData> | Edge<EdgeData>) => view.id === viewId)
        );
        if (alreadySelectedViews.length > 0) {
          // Keep the previous graphical selection if there was one that is still valid
          viewsToSelect.set(semanticElementId, alreadySelectedViews);
        } else if (allRelatedViews.length > 0 && allRelatedViews[0]) {
          // Otherwise select a single view among the candidates.
          // Given the order we receive the views from the backend, if there
          // are multiple candidates in the same view hierarchy, the parent
          // will appear first, and it's the "main" view we want to select.
          viewsToSelect.set(semanticElementId, [allRelatedViews[0]]);
        }
      }

      // Apply the new graphical selection
      convertedDiagram.nodes = convertedDiagram.nodes.map((node) => ({
        ...node,
        selected: viewsToSelect.get(node.data?.targetObjectId)?.includes(node.id),
      }));
      convertedDiagram.edges = convertedDiagram.edges.map((edge) => ({
        ...edge,
        selected: !!(edge.data?.targetObjectId && viewsToSelect.get(edge.data?.targetObjectId)?.includes(edge.id)),
      }));

      setEdges(convertedDiagram.edges);
      setNodes(convertedDiagram.nodes);
    } else if (cause === 'refresh') {
      const previousDiagram: RawDiagram = {
        nodes,
        edges,
      };
      layout(previousDiagram, convertedDiagram, diagramRefreshedEventPayload.referencePosition, (laidOutDiagram) => {
        const { nodeLookup, edgeLookup } = store.getState();

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
        closeAllPalettes();

        synchronizeLayoutData(diagramRefreshedEventPayload.id, laidOutDiagram);
      });
    }
    if (convertedDiagram.style.background) {
      setBackground(String(convertedDiagram.style.background));
    }
  }, [diagramRefreshedEventPayload, diagramDescription, edgeType]);

  useEffect(() => {
    setEdges((oldEdges) => oldEdges.map((edge) => ({ ...edge, reconnectable: !!edge.selected })));
  }, [edges.map((edge) => edge.id + edge.selected).join()]);

  const { onShiftSelection, setShiftSelection } = useShiftSelection();
  useDiagramSelection(onShiftSelection);
  const { transformBorderNodeChanges } = useBorderChange();
  const { transformUndraggableListNodeChanges, applyMoveChange } = useMoveChange();
  const { transformResizeListNodeChanges } = useResizeChange();
  const { applyHandleChange } = useHandleChange();
  const { layoutOnBoundsChange } = useLayoutOnBoundsChange(diagramRefreshedEventPayload.id);
  const { filterReadOnlyChanges } = useFilterReadOnlyChanges();
  const {
    helperLinesEnabled,
    setHelperLinesEnabled,
    horizontalHelperLine,
    verticalHelperLine,
    applyHelperLines,
    resetHelperLines,
  } = useHelperLines();

  const handleNodesChange: OnNodesChange<Node<NodeData>> = useCallback(
    (changes: NodeChange<Node<NodeData>>[]) => {
      const noReadOnlyChanges = filterReadOnlyChanges(changes);
      const isResetChange = changes.find((change) => change.type === 'replace');
      if (
        isResetChange ||
        (noReadOnlyChanges.length === 1 &&
          noReadOnlyChanges[0]?.type === 'dimensions' &&
          typeof noReadOnlyChanges[0].resizing !== 'boolean')
      ) {
        setNodes((oldNodes) => applyNodeChanges<Node<NodeData>>(noReadOnlyChanges, oldNodes));
      } else {
        resetHelperLines(changes);
        let transformedNodeChanges: NodeChange<Node<NodeData>>[] = transformBorderNodeChanges(noReadOnlyChanges, nodes);
        transformedNodeChanges = transformUndraggableListNodeChanges(transformedNodeChanges);
        transformedNodeChanges = transformResizeListNodeChanges(transformedNodeChanges);
        transformedNodeChanges = applyHelperLines(transformedNodeChanges);

        let newNodes = applyNodeChanges(transformedNodeChanges, nodes);

        newNodes = applyMoveChange(transformedNodeChanges, newNodes);
        newNodes = applyHandleChange(transformedNodeChanges, newNodes);

        layoutOnBoundsChange(transformedNodeChanges, newNodes);
        setNodes(newNodes);
      }
    },
    [layoutOnBoundsChange, getNodes, getEdges]
  );

  const handleEdgesChange: OnEdgesChange<Edge<EdgeData>> = useCallback(
    (changes: EdgeChange<Edge<EdgeData>>[]) => {
      onEdgesChange(changes);
    },
    [onEdgesChange]
  );

  const handlePaneClick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      const {
        diagram: {
          id,
          metadata: { kind },
        },
      } = diagramRefreshedEventPayload;
      const selection: Selection = {
        entries: [
          {
            id,
            kind,
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

  const closeAllPalettes = useCallback(() => {
    hideDiagramPalette();
    hideDiagramElementPalette();
    hideGroupPalette();
  }, [hideDiagramPalette, hideDiagramElementPalette, hideGroupPalette]);

  const handleMove: OnMove = useCallback(() => {
    closeAllPalettes();
  }, [isDiagramElementPaletteOpened, isDiagramPaletteOpened]);

  const handleDiagramElementCLick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, element: Node<NodeData> | Edge<EdgeData>) => {
      diagramPaletteOnDiagramElementClick();
      elementPaletteOnDiagramElementClick(event, element);
      groupPaletteOnDiagramElementClick(event, element);
    },
    [elementPaletteOnDiagramElementClick, diagramPaletteOnDiagramElementClick, groupPaletteOnDiagramElementClick]
  );

  const handleSelectionStart = useCallback(() => {
    closeAllPalettes();
    setShiftSelection(true);
  }, [closeAllPalettes, setShiftSelection]);

  const handleSelectionEnd = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      groupPaletteOnDiagramElementClick(event, null);
      setShiftSelection(false);
    },
    [groupPaletteOnDiagramElementClick, setShiftSelection]
  );

  const { onNodeMouseEnter, onNodeMouseLeave } = useNodeHover();

  const handleNodeDrag = useCallback(
    (event: ReactMouseEvent, node: Node<NodeData>, nodes: Node<NodeData>[]) => {
      onNodeDrag(event, node, nodes);
      closeAllPalettes();
    },
    [onNodeDrag, closeAllPalettes]
  );

  const { nodesDraggable } = useNodesDraggable();

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
    onEdgesChange: handleEdgesChange,
    onReconnect: reconnectEdge,
    onPaneClick: handlePaneClick,
    onEdgeClick: handleDiagramElementCLick,
    onNodeClick: handleDiagramElementCLick,
    onMove: handleMove,
    nodeDragThreshold: 1,
    onDrop: onDrop,
    onDragOver: onDragOver,
    onNodeDrag: handleNodeDrag,
    onNodeDragStart: onNodeDragStart,
    onNodeDragStop: onNodeDragStop,
    onNodeMouseEnter: onNodeMouseEnter,
    onNodeMouseLeave: onNodeMouseLeave,
    onSelectionStart: handleSelectionStart,
    onSelectionEnd: handleSelectionEnd,
    maxZoom: 40,
    minZoom: 0.1,
    snapToGrid: snapToGrid,
    snapGrid: useMemo(() => [GRID_STEP, GRID_STEP], []),
    connectionMode: ConnectionMode.Loose,
    zoomOnDoubleClick: false,
    connectionLineType: ConnectionLineType.SmoothStep,
    nodesDraggable: nodesDraggable,
    children: (
      <>
        {snapToGrid ? (
          <>
            <Background
              id="small-grid"
              style={{ background }}
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
          <Background style={{ background }} color="transparent" />
        )}
        <DiagramPanel
          snapToGrid={snapToGrid}
          onSnapToGrid={onSnapToGrid}
          helperLines={helperLinesEnabled}
          onHelperLines={setHelperLinesEnabled}
          reactFlowWrapper={ref}
          edgeType={edgeType}
          onEdgeType={setEdgeType}
        />
        <GroupPalette
          x={groupPalettePosition?.x}
          y={groupPalettePosition?.y}
          isOpened={isGroupPaletteOpened}
          refElementId={groupPaletteRefElementId}
          hidePalette={hideGroupPalette}
        />
        <DiagramPalette
          diagramElementId={diagramRefreshedEventPayload.diagram.id}
          targetObjectId={diagramRefreshedEventPayload.diagram.targetObjectId}
        />
        {diagramDescription.debug ? <DebugPanel reactFlowWrapper={ref} /> : null}
        <ConnectorContextualMenu />
        {helperLinesEnabled ? <HelperLines horizontal={horizontalHelperLine} vertical={verticalHelperLine} /> : null}
        <MiniMap pannable zoomable zoomStep={2} />
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
