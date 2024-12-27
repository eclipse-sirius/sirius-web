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
import { useAdjustSize } from '../adjust-size/useAdjustSize';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useEditableEdgePath } from '../edge/useEditableEdgePath';
import { useFadeDiagramElements } from '../fade/useFadeDiagramElements';
import { useHandlesLayout } from '../handles/useHandlesLayout';
import { useLabelResetPosition } from '../move/useLabelResetPosition';
import { GQLTool } from '../palette/Palette.types';
import { usePinDiagramElements } from '../pin/usePinDiagramElements';
import { useCollapseExpand } from './useCollapseExpand';
import { GQLCollapsingState } from './useCollapseExpand.types';
import { useDelete } from './useDelete';
import { UseInvokePaletteToolValue } from './useInvokePaletteTool.types';
import { useSingleClickTool } from './useSingleClickTool';

export const useInvokePaletteTool = (): UseInvokePaletteToolValue => {
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { removeOutsideLabelLayoutData } = useLabelResetPosition();
  const { removeNodeHandleLayoutData } = useHandlesLayout();
  const { removeEdgeLayoutData } = useEditableEdgePath();
  const { invokeSingleClickTool } = useSingleClickTool();
  const { collapseExpandElement } = useCollapseExpand();
  const { fadeDiagramElements } = useFadeDiagramElements();
  const { pinDiagramElements } = usePinDiagramElements();
  const { deleteDiagramElements } = useDelete();
  const { adjustSize } = useAdjustSize();
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
    diagramElementId: string,
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
          invokeDelete(diagramElementId);
        });
        break;
      case 'expand':
        collapseExpandElement(editingContextId, diagramId, diagramElementId, GQLCollapsingState.EXPANDED);
        break;
      case 'collapse':
        collapseExpandElement(editingContextId, diagramId, diagramElementId, GQLCollapsingState.COLLAPSED);
        break;
      case 'fade':
        fadeDiagramElements([diagramElementId], true);
        break;
      case 'unfade':
        fadeDiagramElements([diagramElementId], false);
        break;
      case 'pin':
        pinDiagramElements([diagramElementId], true);
        break;
      case 'unpin':
        pinDiagramElements([diagramElementId], false);
        break;
      case 'reset-outside-label-position':
        removeOutsideLabelLayoutData(diagramElementId);
        break;
      case 'reset-bending-points':
        removeEdgeLayoutData(diagramElementId);
        break;
      case 'reset-handles-position':
        removeNodeHandleLayoutData(diagramElementId);
        break;
      case 'adjust-size':
        adjustSize(diagramElementId);
        break;
      default:
        invokeSingleClickTool(editingContextId, diagramId, tool, diagramElementId, targetObjectId, x, y);
        break;
    }
  };
  return { invokeTool };
};
