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
import { GQLDeletionPolicy } from './useDelete.types';
import { UseInvokePaletteToolProps, UseInvokePaletteToolValue } from './useInvokePaletteTool.types';
import { useSingleClickTool } from './useSingleClickTool';

export const useInvokePaletteTool = ({
  x,
  y,
  diagramElementId,
  targetObjectId,
  onDirectEditClick,
}: UseInvokePaletteToolProps): UseInvokePaletteToolValue => {
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { invokeSingleClickTool } = useSingleClickTool();
  const { collapseExpandElement } = useCollapseExpand();
  const { deleteDiagramElements } = useDelete();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const invokeDelete = (diagramElementId: string, deletionPolicy: GQLDeletionPolicy) => {
    if (!!nodeLookup.get(diagramElementId)) {
      deleteDiagramElements(editingContextId, diagramId, [diagramElementId], [], deletionPolicy);
    } else if (!!edgeLookup.get(diagramElementId)) {
      deleteDiagramElements(editingContextId, diagramId, [], [diagramElementId], deletionPolicy);
    }
  };

  const invokeTool = (tool: GQLTool) => {
    switch (tool.id) {
      case 'edit':
        onDirectEditClick();
        break;
      case 'semantic-delete':
        showDeletionConfirmation(() => {
          invokeDelete(diagramElementId, GQLDeletionPolicy.SEMANTIC);
        });
        break;
      case 'graphical-delete':
        invokeDelete(diagramElementId, GQLDeletionPolicy.GRAPHICAL);
        break;
      case 'expand':
        collapseExpandElement(editingContextId, diagramId, diagramElementId, GQLCollapsingState.EXPANDED);
        break;
      case 'collapse':
        collapseExpandElement(editingContextId, diagramId, diagramElementId, GQLCollapsingState.COLLAPSED);
        break;
      default:
        invokeSingleClickTool(editingContextId, diagramId, tool, diagramElementId, targetObjectId, x, y);
        break;
    }
  };
  return { invokeTool };
};
