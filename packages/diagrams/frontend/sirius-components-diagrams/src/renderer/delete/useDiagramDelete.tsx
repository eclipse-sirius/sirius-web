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
import { useDeletionConfirmationDialog } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useDelete } from '../tools/useDelete';
import { UseDiagramDeleteValue } from './useDiagramDelete.types';

export const useDiagramDelete = (): UseDiagramDeleteValue => {
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { getNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const { deleteDiagramElements } = useDelete();

  const onDelete = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key } = event;
      /*If a modifier key is hit alone, do nothing*/
      const isTextField = event.target instanceof HTMLInputElement || event.target instanceof HTMLTextAreaElement;
      if ((event.altKey && key === 'Alt') || (event.shiftKey && key === 'Shift') || isTextField) {
        return;
      }

      if (key === 'Delete' && editingContextId && diagramId && !readOnly) {
        event.preventDefault();
        const nodeToDeleteIds: string[] = getNodes()
          .filter((node) => node.selected && node.data.deletable)
          .map((node) => node.id);
        const edgeToDeleteIds: string[] = getEdges()
          .filter((edge) => edge.selected && edge.data?.deletable)
          .map((edge) => edge.id);
        if (nodeToDeleteIds.length + edgeToDeleteIds.length > 0) {
          showDeletionConfirmation(() => {
            deleteDiagramElements(editingContextId, diagramId, nodeToDeleteIds, edgeToDeleteIds);
          });
        }
      }
    },
    [deleteDiagramElements]
  );

  return {
    onDelete,
  };
};
