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
import '@xyflow/react/dist/style.css';
import React, {
  MouseEvent as ReactMouseEvent,
  memo,
  useCallback,
  useContext,
  useEffect,
  useMemo,
  useRef,
  useState,
} from 'react';
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
import { useResetXYFlowConnection } from './connector/useResetXYFlowConnection';
import { DebugPanel } from './debug/DebugPanel';
import { useDiagramDelete } from './delete/useDiagramDelete';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { useNodesDraggable } from './drag/useNodesDraggable';
import { useDrop } from './drop/useDrop';
import { useDropDiagramStyle } from './dropNode/useDropDiagramStyle';
import { useDropNode } from './dropNode/useDropNode';
import { ConnectionLine } from './edge/ConnectionLine';
import { edgeTypes } from './edge/EdgeTypes';
import { useSelectEdgeChange } from './edgeChange/useSelectEdgeChange';
import { useInitialFitToScreen } from './fit-to-screen/useInitialFitToScreen';
import { useHandleChange } from './handles/useHandleChange';
import { useHandleResizedChange } from './handles/useHandleResizedChange';
import { HelperLines } from './helper-lines/HelperLines';
import { useHelperLines } from './helper-lines/useHelperLines';
import { useEdgeHover } from './hover/useEdgeHover';
import { useNodeHover } from './hover/useNodeHover';
import { useFilterReadOnlyChanges } from './layout-events/useFilterReadOnlyChanges';
import { useLayoutOnBoundsChange } from './layout-events/useLayoutOnBoundsChange';
import { RawDiagram } from './layout/layout.types';
import { useLayout } from './layout/useLayout';
import { useSynchronizeLayoutData } from './layout/useSynchronizeLayoutData';
import { useMoveChange } from './move/useMoveChange';
import { useNodeType } from './node/useNodeType';
import { DiagramPalette } from './palette/DiagramPalette';
import { GQLTool } from './palette/Palette.types';
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
import { useInvokePaletteTool } from './tools/useInvokePaletteTool';

const GRID_STEP: number = 10;

/**
 * Helper to keep track of the state of the "Alt" key used to trigger the "repeat last tool"
 * behavior.
 */
const useAltKeyPressedStatus = (refDomNode: React.MutableRefObject<HTMLElement | null>) => {
  const [isKeyPressed, setKeyPressed] = useState<boolean>(false);

  useEffect(() => {
    const onKeyDown = (event: KeyboardEvent) => {
      if (event.altKey) {
        setKeyPressed(true);
      }
    };

    const onKeyUp = (event: KeyboardEvent) => {
      if (!event.altKey) {
        setKeyPressed(false);
      }
    };

    refDomNode.current?.addEventListener('keydown', onKeyDown);
    refDomNode.current?.addEventListener('keyup', onKeyUp);

    return () => {
      refDomNode.current?.removeEventListener('keydown', onKeyDown);
      refDomNode.current?.removeEventListener('keyup', onKeyUp);
    };
  }, [refDomNode]);

  return isKeyPressed;
};

export const DiagramRenderer = memo(({ diagramRefreshedEventPayload }: DiagramRendererProps) => {
  const { readOnly, consumePostToolSelection, toolSelections } = useContext<DiagramContextValue>(DiagramContext);
  const { diagramDescription } = useDiagramDescription();
  const { getEdges, onEdgesChange, getNodes, setEdges, setNodes } = useStore();
  const nodes = getNodes();
  const edges = getEdges();

  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const isAltKeyDown = useAltKeyPressedStatus(ref);
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { onDiagramBackgroundContextMenu, getLastToolInvoked } = useDiagramPalette();
  const { onDiagramElementContextMenu: elementPaletteOnDiagramElementContextMenu } = useDiagramElementPalette();
  const { invokeTool } = useInvokePaletteTool();

  const {
    onDiagramElementContextMenu: groupPaletteOnDiagramElementContextMenu,
    hideGroupPalette,
    position: groupPalettePosition,
    isOpened: isGroupPaletteOpened,
    refElementId: groupPaletteRefElementId,
  } = useGroupPalette();

  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { onReconnectEdgeStart, reconnectEdge, onReconnectEdgeEnd } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();
  const { onNodeDragStart, onNodeDrag, onNodeDragStop } = useDropNode();
  const { backgroundColor, largeGridColor, smallGridColor } = useDropDiagramStyle();
  const { nodeTypes } = useNodeType();

  const { nodeConverters } = useContext<NodeTypeContextValue>(NodeTypeContext);

  const { selection, setSelection } = useSelection();

  useInitialFitToScreen(diagramRefreshedEventPayload.diagram.nodes.length === 0);
  useResetXYFlowConnection();
  const { getNode, screenToFlowPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
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
      layout(previousDiagram, convertedDiagram, diagramRefreshedEventPayload.referencePosition, (laidOutDiagram) => {
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
      });
    }
  }, [diagramRefreshedEventPayload, diagramDescription]);

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
      }));
      const newEdges = getEdges().map((edge) => ({
        ...edge,
        selected: shouldSelectEdge(edge),
      }));
      setEdges(newEdges);
      setNodes(newNodes);
      toolSelections.delete(diagramRefreshedEventPayload.id);
    }
  }, [toolSelections]);

  useEffect(() => {
    setEdges((oldEdges) => oldEdges.map((edge) => ({ ...edge, reconnectable: !!edge.selected && !readOnly })));
  }, [edges.map((edge) => edge.id + edge.selected).join(), readOnly]);

  const { onShiftSelection, setShiftSelection } = useShiftSelection();
  useDiagramSelection(onShiftSelection);
  const { transformBorderNodeChanges } = useBorderChange();
  const { transformUndraggableListNodeChanges, applyMoveChange } = useMoveChange();
  const { transformResizeListNodeChanges } = useResizeChange();
  const { applyHandleChange } = useHandleChange();
  const { applyResizeHandleChange } = useHandleResizedChange();
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
      const isSelectChange = changes.find((change) => change.type === 'select');

      if (
        isResetChange ||
        isSelectChange ||
        (noReadOnlyChanges.length === 1 &&
          noReadOnlyChanges[0]?.type === 'dimensions' &&
          typeof noReadOnlyChanges[0].resizing !== 'boolean')
      ) {
        setNodes((oldNodes) => applyNodeChanges<Node<NodeData>>(noReadOnlyChanges, oldNodes));
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
    [layoutOnBoundsChange, getNodes, getEdges]
  );

  const { onEdgeSelectedChange } = useSelectEdgeChange();
  const handleEdgesChange: OnEdgesChange<Edge<EdgeData>> = useCallback(
    (changes: EdgeChange<Edge<EdgeData>>[]) => {
      onEdgeSelectedChange(changes);
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

  const handleNodeContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, element: Node<NodeData>) => {
      const selectedElements = [...store.getState().nodes, ...store.getState().edges].filter(
        (element) => element.selected
      );
      if (
        (selectedElements.length <= 1 ||
          (selectedElements.length > 1 &&
            !selectedElements.find((selectedElements) => selectedElements.id === element.id))) &&
        !event.shiftKey
      ) {
        const rightClickAndSelect: Selection = {
          entries: [
            {
              id: element.data?.targetObjectId || '',
            },
          ],
        };
        store.getState().addSelectedNodes([element.id]);
        setSelection(rightClickAndSelect);
        elementPaletteOnDiagramElementContextMenu(event, element);
      } else if (!event.shiftKey) {
        groupPaletteOnDiagramElementContextMenu(event, element);
      }
    },
    [elementPaletteOnDiagramElementContextMenu, groupPaletteOnDiagramElementContextMenu]
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
        elementPaletteOnDiagramElementContextMenu(event, element);
      } else if (!event.shiftKey) {
        groupPaletteOnDiagramElementContextMenu(event, element);
      }
    },
    [elementPaletteOnDiagramElementContextMenu, groupPaletteOnDiagramElementContextMenu]
  );

  const handleSelectionStart = useCallback(() => {
    setShiftSelection(true);
  }, [setShiftSelection]);

  const handleSelectionEnd = useCallback(() => {
    setShiftSelection(false);
  }, [setShiftSelection]);

  const handleSelectionContextMenu = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>) => {
      groupPaletteOnDiagramElementContextMenu(event, null);
    },
    [groupPaletteOnDiagramElementContextMenu]
  );

  const handleClick = useCallback(
    (event: React.MouseEvent<Element, MouseEvent>, node: Node<NodeData> | null) => {
      if (isAltKeyDown) {
        const elementDescriptionId = node ? node.data.descriptionId : diagramDescription.id;
        const lastToolInvoked: GQLTool | null = getLastToolInvoked(elementDescriptionId);
        if (lastToolInvoked) {
          const { x, y } = screenToFlowPosition({ x: event.clientX, y: event.clientY });
          const diagramElementId = node ? node.id : diagramRefreshedEventPayload.diagram.id;
          const targetObjectId = node ? node.data.targetObjectId : diagramRefreshedEventPayload.diagram.targetObjectId;
          invokeTool(x, y, diagramElementId, targetObjectId, () => {}, lastToolInvoked);
        }
      }
    },
    [isAltKeyDown, getLastToolInvoked, invokeTool]
  );

  const { onNodeMouseEnter, onNodeMouseLeave } = useNodeHover();
  const { onEdgeMouseEnter, onEdgeMouseLeave } = useEdgeHover();

  const handleNodeDrag = useCallback(
    (event: ReactMouseEvent, node: Node<NodeData>, nodes: Node<NodeData>[]) => {
      onNodeDrag(event, node, nodes);
    },
    [onNodeDrag]
  );

  const { nodesDraggable } = useNodesDraggable();

  let reactFlowProps: ReactFlowProps<Node<NodeData>, Edge<EdgeData>> = {
    className: isAltKeyDown ? 'cursor-crosshair' : '',
    nodes: nodes,
    nodeTypes: nodeTypes,
    onNodesChange: handleNodesChange,
    edges: edges,
    edgeTypes: edgeTypes,
    edgesReconnectable: !readOnly,
    onKeyDown: onKeyDown,
    onPaneClick: (e) => handleClick(e, null),
    onConnect: onConnect,
    onConnectStart: onConnectStart,
    onConnectEnd: onConnectEnd,
    connectionLineComponent: ConnectionLine,
    onReconnectStart: onReconnectEdgeStart,
    onReconnect: reconnectEdge,
    onReconnectEnd: onReconnectEdgeEnd,
    connectionRadius: 0,
    onEdgesChange: handleEdgesChange,
    onPaneContextMenu: handlePaneContextMenu,
    onEdgeContextMenu: handleEdgeContextMenu,
    onNodeContextMenu: handleNodeContextMenu,
    nodeDragThreshold: 1,
    onDrop: onDrop,
    onDragOver: onDragOver,
    onNodeClick: handleClick,
    onNodeDrag: handleNodeDrag,
    onNodeDragStart: onNodeDragStart,
    onNodeDragStop: onNodeDragStop,
    onNodeMouseEnter: onNodeMouseEnter,
    onNodeMouseLeave: onNodeMouseLeave,
    onEdgeMouseEnter: onEdgeMouseEnter,
    onEdgeMouseLeave: onEdgeMouseLeave,
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
    nodesDraggable: nodesDraggable && !readOnly,
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
          elementDescriptionId={diagramDescription.id}
          targetObjectId={diagramRefreshedEventPayload.diagram.targetObjectId}
        />
        {diagramDescription.debug ? <DebugPanel reactFlowWrapper={ref} /> : null}
        <ConnectorContextualMenu />
        {helperLinesEnabled ? <HelperLines horizontal={horizontalHelperLine} vertical={verticalHelperLine} /> : null}
        <MiniMap pannable zoomable zoomStep={2} style={{ width: 150, height: 100, opacity: 0.75 }} />
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
