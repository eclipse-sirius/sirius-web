/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { Edge, Node, useStoreApi } from '@xyflow/react';
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { GQLDiagramRefreshedEventPayload } from '../../graphql/subscription/diagramEventSubscription.types';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

export const usePostToolSelection = (diagramRefreshedEventPayload: GQLDiagramRefreshedEventPayload) => {
  const { toolSelections, consumePostToolSelection } = useContext<DiagramContextValue>(DiagramContext);
  const { setNodes, getNodes, getEdges, setEdges } = useStore();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  useEffect(() => {
    const { id } = diagramRefreshedEventPayload;

    if (toolSelections.get(id) && diagramRefreshedEventPayload.cause === 'layout') {
      const selectionFromTool = consumePostToolSelection(id);

      if (selectionFromTool) {
        // If we "auto-select" a node because it matches what a previous tool invocation
        // asked, we only select the first matching diagram element we find and ignore the rest,
        // even if the new diagram shows the requested semantic element through multiple
        // nodes.
        const targetObjectIdAlreadySelected: Set<string> = new Set();
        const nodesToSelect: string[] = [];
        const edgesToSelect: string[] = [];
        let lastNodeSelectedId: string = '';

        getEdges()
          .filter((edge) => !edge.hidden)
          .forEach((edge) => {
            if (edge.data && edge.data.targetObjectId) {
              if (
                selectionFromTool.entries.some((entry) => entry.id === edge?.data?.targetObjectId) &&
                !targetObjectIdAlreadySelected.has(edge.data.targetObjectId)
              ) {
                targetObjectIdAlreadySelected.add(edge.data.targetObjectId);
                edgesToSelect.push(edge.id);
              }
            }
          });

        getNodes()
          .filter((node) => !node.hidden)
          .forEach((node) => {
            if (
              selectionFromTool.entries.some((entry) => entry.id === node?.data?.targetObjectId) &&
              !targetObjectIdAlreadySelected.has(node.data.targetObjectId)
            ) {
              targetObjectIdAlreadySelected.add(node.data.targetObjectId);
              nodesToSelect.push(node.id);
            }
          });

        setNodes((previousNodes) =>
          previousNodes.map((previousNode) => {
            if (nodesToSelect.includes(previousNode.id)) {
              if (!lastNodeSelectedId) {
                lastNodeSelectedId = previousNode.id;
              }
              return {
                ...previousNode,
                selected: true,
                data: {
                  ...previousNode.data,
                  isLastNodeSelected: previousNode.id === lastNodeSelectedId,
                },
              };
            } else if (previousNode.selected) {
              return {
                ...previousNode,
                selected: false,
                data: {
                  ...previousNode.data,
                  isLastNodeSelected: false,
                },
              };
            }
            return previousNode;
          })
        );
        store.getState().addSelectedNodes(nodesToSelect);
        setEdges((previousEdges) =>
          previousEdges.map((previousEdge) => {
            if (edgesToSelect.includes(previousEdge.id)) {
              return {
                ...previousEdge,
                selected: true,
              };
            } else if (previousEdge.selected) {
              return {
                ...previousEdge,
                selected: false,
              };
            }
            return previousEdge;
          })
        );
      }
    }
  }, [diagramRefreshedEventPayload, toolSelections, getNodes, getEdges]);
};
