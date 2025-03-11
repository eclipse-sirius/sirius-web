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
  OnNodesChange,
  ReactFlow,
  ReactFlowProps,
  applyNodeChanges,
  useReactFlow,
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
    onDiagramBackgroundContextMenu,
    onDiagramElementContextMenu: diagramPaletteOnDiagramElementContextMenu,
    hideDiagramPalette,
  } = useDiagramPalette();
  const { onDiagramElementContextMenu: elementPaletteOnDiagramElementContextMenu, hideDiagramElementPalette } =
    useDiagramElementPalette();

  const {
    onDiagramElementContextMenu: groupPaletteOnDiagramElementContextMenu,
    hideGroupPalette,
    position: groupPalettePosition,
    isOpened: isGroupPaletteOpened,
    refElementId: groupPaletteRefElementId,
  } = useGroupPalette();

  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { reconnectEdge } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();
  const { onNodeDragStart, onNodeDrag, onNodeDragStop } = useDropNode();
  const { backgroundColor, largeGridColor, smallGridColor } = useDropDiagramStyle();
  const { nodeTypes } = useNodeType();

  const { nodeConverters } = useContext<NodeTypeContextValue>(NodeTypeContext);

  const { selection, setSelection } = useSelection();
  const { edgeType, setEdgeType } = useEdgeType();

  useInitialFitToScreen();

  const { getNode } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  useEffect(() => {
    const { diagram, cause } = diagramRefreshedEventPayload;
    const convertedDiagram: Diagram = convertDiagram(diagram, nodeConverters, diagramDescription, edgeType);

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
        return convertedNode;
      } else if (currentNode) {
        return currentNode;
      } else {
        return convertedNode;
      }
    });

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
        hideAllPalettes();

        if (!readOnly) {
          synchronizeLayoutData(diagramRefreshedEventPayload.id, 'refresh', laidOutDiagram);
        }
      });
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
  const { layoutOnBoundsChange } = useLayoutOnBoundsChange();
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

  const handlePaneContextMenu = useCallback(
    (event: MouseEvent | React.MouseEvent<Element, MouseEvent>) => {
      if (!event.shiftKey) {
        const {
          diagram: { id },
        } = diagramRefreshedEventPayload;
        const selection: Selection = {
          entries: [
            {
              id,
            },
          ],
        };
        setSelection(selection);
        onDiagramBackgroundContextMenu(event);
      }
    },
    [setSelection]
  );

  const onKeyDown = useCallback((event: React.KeyboardEvent<Element>) => {
    onDirectEdit(event);
    onDelete(event);
  }, []);

  const { snapToGrid, onSnapToGrid } = useSnapToGrid();

  const hideAllPalettes = useCallback(() => {
    hideDiagramPalette();
    hideDiagramElementPalette();
    hideGroupPalette();
  }, [hideDiagramPalette, hideDiagramElementPalette, hideGroupPalette]);

  const handleNodeContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, element: Node<NodeData>) => {
      if (selection.entries.length <= 1 && !event.shiftKey) {
        const rightClickAndSelect: Selection = {
          entries: [
            {
              id: element.data?.targetObjectId || '',
            },
          ],
        };
        store.getState().addSelectedNodes([element.id]);
        setSelection(rightClickAndSelect);
        diagramPaletteOnDiagramElementContextMenu();
        elementPaletteOnDiagramElementContextMenu(event, element);
      } else if (!event.shiftKey) {
        groupPaletteOnDiagramElementContextMenu(event, element);
      }
    },
    [
      setSelection,
      elementPaletteOnDiagramElementContextMenu,
      diagramPaletteOnDiagramElementContextMenu,
      groupPaletteOnDiagramElementContextMenu,
    ]
  );

  const handleEdgeContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, element: Edge<EdgeData>) => {
      if (selection.entries.length <= 1 && !event.shiftKey) {
        const rightClickAndSelect: Selection = {
          entries: [
            {
              id: element.data?.targetObjectId || '',
            },
          ],
        };
        store.getState().addSelectedEdges([element.id]);
        setSelection(rightClickAndSelect);
        diagramPaletteOnDiagramElementContextMenu();
        elementPaletteOnDiagramElementContextMenu(event, element);
      } else if (!event.shiftKey) {
        groupPaletteOnDiagramElementContextMenu(event, element);
      }
    },
    [
      setSelection,
      elementPaletteOnDiagramElementContextMenu,
      diagramPaletteOnDiagramElementContextMenu,
      groupPaletteOnDiagramElementContextMenu,
    ]
  );

  const handleSelectionStart = useCallback(() => {
    hideAllPalettes();
    setShiftSelection(true);
  }, [hideAllPalettes, setShiftSelection]);

  const handleSelectionEnd = useCallback(() => {
    setShiftSelection(false);
  }, [setShiftSelection]);

  const handleSelectionContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      groupPaletteOnDiagramElementContextMenu(event, null);
    },
    [groupPaletteOnDiagramElementContextMenu]
  );

  const onClick = useCallback(
    (_: React.MouseEvent<Element, MouseEvent>) => {
      hideAllPalettes();
    },
    [hideAllPalettes]
  );

  const { onNodeMouseEnter, onNodeMouseLeave } = useNodeHover();

  const handleNodeDrag = useCallback(
    (event: ReactMouseEvent, node: Node<NodeData>, nodes: Node<NodeData>[]) => {
      onNodeDrag(event, node, nodes);
      hideAllPalettes();
    },
    [onNodeDrag, hideAllPalettes]
  );

  const { nodesDraggable } = useNodesDraggable();

  let reactFlowProps: ReactFlowProps<Node<NodeData>, Edge<EdgeData>> = {
    nodes: nodes,
    nodeTypes: nodeTypes,
    onNodesChange: handleNodesChange,
    onMoveStart: hideAllPalettes,
    edges: edges,
    edgeTypes: edgeTypes,
    edgesReconnectable: !readOnly,
    onKeyDown: onKeyDown,
    onClick: onClick,
    onConnect: onConnect,
    onConnectStart: onConnectStart,
    onConnectEnd: onConnectEnd,
    connectionLineComponent: ConnectionLine,
    connectionRadius: 0,
    onEdgesChange: handleEdgesChange,
    onReconnect: reconnectEdge,
    onPaneContextMenu: handlePaneContextMenu,
    onEdgeContextMenu: handleEdgeContextMenu,
    onNodeContextMenu: handleNodeContextMenu,
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
    onSelectionContextMenu: handleSelectionContextMenu,
    maxZoom: 40,
    minZoom: 0.1,
    snapToGrid: snapToGrid,
    snapGrid: useMemo(() => [GRID_STEP, GRID_STEP], []),
    connectionMode: ConnectionMode.Loose,
    zoomOnDoubleClick: false,
    connectionLineType: ConnectionLineType.SmoothStep,
    nodesDraggable: nodesDraggable,
    tabIndex: -1,
    children: (
      <>
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
