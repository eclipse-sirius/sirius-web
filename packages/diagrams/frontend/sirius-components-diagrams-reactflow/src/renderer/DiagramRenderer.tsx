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

import { Selection, SelectionEntry } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect, useRef, useState } from 'react';
import {
  Background,
  BackgroundVariant,
  ConnectionMode,
  EdgeChange,
  EdgeSelectionChange,
  Node,
  NodeChange,
  NodePositionChange,
  NodeSelectionChange,
  OnEdgesChange,
  OnNodesChange,
  Position,
  ReactFlow,
  getConnectedEdges,
  useEdgesState,
  useNodesState,
  useReactFlow,
  useStoreApi,
} from 'reactflow';
import 'reactflow/dist/style.css';
import { convertDiagram } from '../converter/convertDiagram';
import {
  ConnectionHandle,
  Diagram,
  DiagramRendererProps,
  DiagramRendererState,
  EdgeData,
  NodeData,
} from './DiagramRenderer.types';
import { useBorderChange } from './border/useBorderChange';
import { ConnectorContextualMenu } from './connector/ConnectorContextualMenu';
import { useConnector } from './connector/useConnector';
import { useDiagramDelete } from './delete/useDiagramDelete';
import { useDiagramDirectEdit } from './direct-edit/useDiagramDirectEdit';
import { useDrop } from './drop/useDrop';
import { useDropNode } from './dropNode/useDropNode';
import { getNodeCenter } from './edge/EdgeLayout';
import { edgeTypes } from './edge/EdgeTypes';
import { MultiLabelEdgeData } from './edge/MultiLabelEdge.types';
import { useLayout } from './layout/useLayout';
import { NodeContext } from './node/NodeContext';
import { NodeContextValue } from './node/NodeContext.types';
import { nodeTypes } from './node/NodeTypes';
import { DiagramNodeType } from './node/NodeTypes.types';
import { DiagramPalette } from './palette/DiagramPalette';
import { useDiagramElementPalette } from './palette/useDiagramElementPalette';
import { useDiagramPalette } from './palette/useDiagramPalette';
import { DiagramPanel } from './panel/DiagramPanel';
import { useReconnectEdge } from './reconnect-edge/useReconnectEdge';

const GRID_STEP: number = 10;

const isNodeSelectChange = (change: NodeChange): change is NodeSelectionChange => change.type === 'select';
const isEdgeSelectChange = (change: EdgeChange): change is EdgeSelectionChange => change.type === 'select';

export const DiagramRenderer = ({ diagramRefreshedEventPayload, selection, setSelection }: DiagramRendererProps) => {
  const store = useStoreApi();
  const reactFlowInstance = useReactFlow<NodeData, EdgeData>();
  const { onDirectEdit } = useDiagramDirectEdit();
  const { onDelete } = useDiagramDelete();

  const ref = useRef<HTMLDivElement | null>(null);
  const [state, setState] = useState<DiagramRendererState>({
    snapToGrid: false,
    fitviewLifecycle: 'neverRendered',
  });

  const { layout, resetReferencePosition } = useLayout();
  const { onDiagramBackgroundClick, hideDiagramPalette } = useDiagramPalette();
  const { onDiagramElementClick } = useDiagramElementPalette();

  const { onConnect, onConnectStart, onConnectEnd } = useConnector();
  const { reconnectEdge } = useReconnectEdge();
  const { onDrop, onDragOver } = useDrop();
  const { onBorderChange } = useBorderChange();

  const { setHoveredNode } = useContext<NodeContextValue>(NodeContext);

  const [nodes, setNodes, onNodesChange] = useNodesState<NodeData>([]);
  const [edges, setEdges, onEdgesChange] = useEdgesState<MultiLabelEdgeData>([]);

  useEffect(() => {
    const { diagram } = diagramRefreshedEventPayload;
    const convertedDiagram: Diagram = convertDiagram(diagram);

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
      if (state.fitviewLifecycle === 'neverRendered') {
        setState((prevState) => ({ ...prevState, fitviewLifecycle: 'shouldFitview' }));
      }
    });
  }, [diagramRefreshedEventPayload]);

  useEffect(() => {
    if (state.fitviewLifecycle === 'shouldFitview') {
      reactFlowInstance.fitView({ minZoom: 0.5 });
      setState((prevState) => ({ ...prevState, fitviewLifecycle: 'viewfit' }));
    }
  }, [state.fitviewLifecycle]);

  useEffect(() => {
    const selectionEntryIds = selection.entries.map((entry) => entry.id);
    const edgesMatchingWorkbenchSelection = edges.filter((edge) =>
      selectionEntryIds.includes(edge.data ? edge.data.targetObjectId : '')
    );
    const nodesMatchingWorkbenchSelection = nodes.filter((node) =>
      selectionEntryIds.includes(node.data.targetObjectId)
    );

    const alreadySelectedNodesMatchingWorkbenchSelection = nodesMatchingWorkbenchSelection.filter(
      (node) => node.selected
    );
    const firstNodeMatchingWorkbenchSelection =
      alreadySelectedNodesMatchingWorkbenchSelection[0] ?? nodesMatchingWorkbenchSelection[0];

    if (edgesMatchingWorkbenchSelection.length === 0 && firstNodeMatchingWorkbenchSelection) {
      const firstNodeIdMatchingWorkbenchSelection = firstNodeMatchingWorkbenchSelection.id;

      // Support single graphical selection to display the palette on node containing compartment based on the same targetObjectId.
      const reactFlowState = store.getState();
      const currentlySelectedNodes = reactFlowState.getNodes().filter((node) => node.selected);

      const isAlreadySelected = currentlySelectedNodes
        .map((node) => node.id)
        .includes(firstNodeIdMatchingWorkbenchSelection);
      if (!isAlreadySelected) {
        reactFlowState.unselectNodesAndEdges();
        reactFlowState.addSelectedNodes([firstNodeIdMatchingWorkbenchSelection]);

        const selectedNodes = reactFlowState
          .getNodes()
          .filter((node) => firstNodeIdMatchingWorkbenchSelection === node.id);
        reactFlowInstance.fitView({ nodes: selectedNodes, maxZoom: 2, duration: 1000 });
      }
    }
  }, [selection]);

  const handleNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    onNodesChange(onBorderChange(changes));

    changes.map((change) => {
      if (change.type === 'position' && change.positionAbsolute) {
        const movedNode = reactFlowInstance.getNodes().find((node) => change.id === node.id);

        if (movedNode) {
          const connectedEdges = getConnectedEdges([movedNode], edges);
          connectedEdges.forEach((edge) => {
            const { sourceNode, targetNode, id } = edge;
            if (sourceNode?.id === movedNode.id && targetNode) {
              const nodeCenterSourceNode = {
                x: change.positionAbsolute?.x ?? 0 + (movedNode.width ?? 0) / 2,
                y: change.positionAbsolute?.y ?? 0 + (movedNode.height ?? 0) / 2,
              };
              const nodeCenterTargetNode = getNodeCenter(targetNode, nodes);
              const horizontallDifference = Math.abs(nodeCenterTargetNode.x - nodeCenterSourceNode.x);
              const verticalDifference = Math.abs(nodeCenterTargetNode.y - nodeCenterSourceNode.y);

              let newPositionSource: Position;
              if (horizontallDifference > verticalDifference) {
                newPositionSource = nodeCenterTargetNode.x > nodeCenterSourceNode.x ? Position.Right : Position.Left;
              } else {
                newPositionSource = nodeCenterTargetNode.y > nodeCenterSourceNode.y ? Position.Bottom : Position.Top;
              }

              let newPositionTarget: Position;
              if (horizontallDifference > verticalDifference) {
                newPositionTarget = nodeCenterTargetNode.x > nodeCenterSourceNode.x ? Position.Left : Position.Right;
              } else {
                newPositionTarget = nodeCenterTargetNode.y > nodeCenterSourceNode.y ? Position.Top : Position.Bottom;
              }

              const nodeSourceConnectionHandles: ConnectionHandle[] = sourceNode.data.connectionHandles;
              const nodeTargetConnectionHandles: ConnectionHandle[] = targetNode.data.connectionHandles;
              const nodeSourceConnectionHandle: ConnectionHandle | undefined = nodeSourceConnectionHandles.find(
                (connectionHandle) => connectionHandle.edgeId === id
              );
              const nodeTargetConnectionHandle: ConnectionHandle | undefined = nodeTargetConnectionHandles.find(
                (connectionHandle) => connectionHandle.edgeId === id
              );

              if (
                nodeSourceConnectionHandle?.position !== newPositionSource &&
                nodeTargetConnectionHandle?.position !== newPositionTarget
              ) {
                nodeSourceConnectionHandles.map((nodeConnectionHandle: ConnectionHandle) => {
                  if (nodeConnectionHandle.edgeId === id && nodeConnectionHandle.type === 'source') {
                    nodeConnectionHandle.position = newPositionSource;
                    //nodeConnectionHandle.id = `handle--source--${id}`;
                  }
                  return nodeConnectionHandle;
                });

                nodeTargetConnectionHandles.map((nodeConnectionHandle: ConnectionHandle) => {
                  if (nodeConnectionHandle.edgeId === id && nodeConnectionHandle.type === 'target') {
                    nodeConnectionHandle.position = newPositionTarget;
                    //nodeConnectionHandle.id = `handle--target--${id}`;
                  }
                  return nodeConnectionHandle;
                });

                reactFlowInstance.setNodes((nodes: Node<NodeData>[]) =>
                  nodes.map((node) => {
                    //console.log('reactFlowInstance.setNodes');
                    if (sourceNode.id === node.id) {
                      node.data = { ...node.data, connectionHandles: nodeSourceConnectionHandles };
                    }
                    if (targetNode.id === node.id) {
                      node.data = { ...node.data, connectionHandles: nodeTargetConnectionHandles };
                    }
                    return node;
                  })
                );
              }
            }
          });
        }
      }
      return change;
    });

    const selectionEntries: SelectionEntry[] = changes
      .filter(isNodeSelectChange)
      .filter((change) => change.selected)
      .flatMap((change) => nodes.filter((node) => node.id === change.id))
      .map((node) => {
        const { targetObjectId, targetObjectKind, targetObjectLabel } = node.data;
        return {
          id: targetObjectId,
          kind: targetObjectKind,
          label: targetObjectLabel,
        };
      });

    const currentSelectionEntryIds = selection.entries.map((selectionEntry) => selectionEntry.id);
    const shouldUpdateSelection =
      selectionEntries.map((entry) => entry.id).filter((entryId) => currentSelectionEntryIds.includes(entryId))
        .length !== selectionEntries.length;

    if (selectionEntries.length > 0 && shouldUpdateSelection) {
      setSelection({ entries: selectionEntries });
    }

    if (selectionEntries.length > 0) {
      hideDiagramPalette();
    }
  };

  const handleEdgesChange: OnEdgesChange = (changes: EdgeChange[]) => {
    onEdgesChange(changes);

    const selectionEntries: SelectionEntry[] = changes
      .filter(isEdgeSelectChange)
      .filter((change) => change.selected)
      .flatMap((change) => edges.filter((edge) => edge.id === change.id))
      .map((edge) => {
        if (edge.data) {
          const { targetObjectId, targetObjectKind, targetObjectLabel } = edge.data;
          return {
            id: targetObjectId,
            kind: targetObjectKind,
            label: targetObjectLabel,
          };
        } else {
          return { id: '', kind: '', label: '' };
        }
      });

    const currentSelectionEntryIds = selection.entries.map((selectionEntry) => selectionEntry.id);
    const shouldUpdateSelection =
      selectionEntries.map((entry) => entry.id).filter((entryId) => currentSelectionEntryIds.includes(entryId))
        .length !== selectionEntries.length;

    if (selectionEntries.length > 0 && shouldUpdateSelection) {
      setSelection({ entries: selectionEntries });
    }
    if (selectionEntries.length > 0) {
      hideDiagramPalette();
    }
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

  const handleSnapToGrid = (snapToGrid: boolean) => setState((prevState) => ({ ...prevState, snapToGrid }));

  const onKeyDown = (event: React.KeyboardEvent<Element>) => {
    onDirectEdit(event);
    onDelete(event);
  };

  const { onNodeDragStart, onNodeDrag, onNodeDragStop, dropFeedbackStyleProvider } = useDropNode();

  const { backgroundColor, smallGridColor, largeGridColor } = dropFeedbackStyleProvider.getDiagramBackgroundStyle();

  const handleOnNodeDrag = (
    event: React.MouseEvent<Element, MouseEvent>,
    node: Node<any, string | undefined>,
    nodes: Node<any, string | undefined>[]
  ) => {
    onNodeDrag(event, node, nodes);
  };

  return (
    <ReactFlow
      nodes={nodes}
      nodeTypes={nodeTypes}
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
      onMove={() => hideDiagramPalette()}
      onDrop={onDrop}
      onDragOver={onDragOver}
      onNodeDrag={handleOnNodeDrag}
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
      snapToGrid={state.snapToGrid}
      snapGrid={[GRID_STEP, GRID_STEP]}
      connectionMode={ConnectionMode.Strict}
      zoomOnDoubleClick={false}
      ref={ref}>
      {state.snapToGrid ? (
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
      <DiagramPanel snapToGrid={state.snapToGrid} onSnapToGrid={handleSnapToGrid} />
      <DiagramPalette targetObjectId={diagramRefreshedEventPayload.diagram.id} />
      <ConnectorContextualMenu />
    </ReactFlow>
  );
};
