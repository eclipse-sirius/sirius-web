/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { GQLTool } from '../palette/Palette.types';
import { useCollapseExpand } from './useCollapseExpand';
import { GQLCollapsingState } from './useCollapseExpand.types';
import { useDelete } from './useDelete';
import { UseInvokePaletteToolValue } from './useInvokePaletteTool.types';
import { useSingleClickTool } from './useSingleClickTool';

export const useInvokePaletteTool = (): UseInvokePaletteToolValue => {
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { invokeSingleClickTool } = useSingleClickTool();
  const { collapseExpandElement } = useCollapseExpand();
  const { deleteDiagramElements } = useDelete();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const invokeDelete = (diagramElementId: string) => {
    if (!!nodeLookup.get(diagramElementId)) {
      deleteDiagramElements(editingContextId, diagramId, [diagramElementId], []);
    } else if (!!edgeLookup.get(diagramElementId)) {
      deleteDiagramElements(editingContextId, diagramId, [], [diagramElementId]);
    }
  };

  const invokeTool = (
    x: number,
    y: number,
    diagramElementIds: string[],
    targetObjectId: string,
    onDirectEditClick: () => void,
    tool: GQLTool
  ) => {
    switch (tool.id) {
      case 'edit':
        onDirectEditClick();
        break;
      case 'semantic-delete':
        if (diagramElementIds.length === 1) {
          showDeletionConfirmation(() => {
            if (diagramElementIds[0]) {
              invokeDelete(diagramElementIds[0]);
            }
          });
        }
        break;
      case 'expand':
        if (diagramElementIds[0]) {
          collapseExpandElement(editingContextId, diagramId, diagramElementIds[0], GQLCollapsingState.EXPANDED);
        }
        break;
      case 'collapse':
        if (diagramElementIds[0]) {
          collapseExpandElement(editingContextId, diagramId, diagramElementIds[0], GQLCollapsingState.COLLAPSED);
        }
        break;
      default:
        invokeSingleClickTool(editingContextId, diagramId, tool, diagramElementIds, targetObjectId, x, y);
        break;
    }
  };
  return { invokeTool };
};
