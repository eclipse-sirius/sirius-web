/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { useAdjustSize } from '../adjust-size/useAdjustSize';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useEditableEdgePath } from '../edge/useEditableEdgePath';
import { useHandlesLayout } from '../handles/useHandlesLayout';
import { useLabelResetPosition } from '../move/useLabelResetPosition';
import { GQLTool } from '../palette/Palette.types';
import { useLabelResetSize } from '../resize/useLabelResetSize';
import { useDelete } from './useDelete';
import { UseInvokePaletteToolValue } from './useInvokePaletteTool.types';
import { useSingleClickTool } from './useSingleClickTool';

export const useInvokePaletteTool = (): UseInvokePaletteToolValue => {
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { removeOutsideLabelLayoutData } = useLabelResetPosition();
  const { removeLabelSizeLayoutData } = useLabelResetSize();
  const { removeNodeHandleLayoutData } = useHandlesLayout();
  const { removeEdgeLayoutData } = useEditableEdgePath();
  const { invokeSingleClickTool } = useSingleClickTool();
  const { deleteDiagramElements } = useDelete();
  const { adjustSize } = useAdjustSize();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const invokeDelete = (diagramElementIds: string[]) => {
    const nodeIds = diagramElementIds.filter((id) => nodeLookup.has(id) && !!nodeLookup.get(id));
    const edgeIds = diagramElementIds.filter((id) => edgeLookup.has(id) && !!edgeLookup.get(id));
    deleteDiagramElements(editingContextId, diagramId, nodeIds, edgeIds);
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
        showDeletionConfirmation(() => {
          invokeDelete(diagramElementIds);
        });
        break;
      case 'reset-outside-label-position':
        if (diagramElementIds.length === 1 && diagramElementIds[0]) {
          removeOutsideLabelLayoutData(diagramElementIds[0]);
        }
        break;
      case 'reset-label-resize':
        if (diagramElementIds.length === 1 && diagramElementIds[0]) {
          removeLabelSizeLayoutData(diagramElementIds[0]);
        }
        break;
      case 'reset-bending-points':
        removeEdgeLayoutData(diagramElementIds);
        break;
      case 'reset-handles-position':
        if (diagramElementIds.length === 1 && diagramElementIds[0]) {
          removeNodeHandleLayoutData(diagramElementIds[0]);
        }
        break;
      case 'adjust-size':
        adjustSize(diagramElementIds);
        break;
      default:
        invokeSingleClickTool(editingContextId, diagramId, tool, diagramElementIds, targetObjectId, x, y);
        break;
    }
  };
  return { invokeTool };
};
