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

import { GQLTool } from '@eclipse-sirius/sirius-components-palette';
import { useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useAdjustSize } from '../adjust-size/useAdjustSize';
import { useEditableEdgePath } from '../edge/useEditableEdgePath';
import { useHandlesLayout } from '../handles/useHandlesLayout';
import { useLabelResetPosition } from '../move/useLabelResetPosition';
import { useLabelResetSize } from '../resize/useLabelResetSize';
import { UseInvokePaletteToolValue } from './useInvokePaletteTool.types';
import { useSingleClickTool } from './useSingleClickTool';

export const useInvokePaletteTool = (): UseInvokePaletteToolValue => {
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { removeOutsideLabelLayoutData } = useLabelResetPosition();
  const { removeLabelSizeLayoutData } = useLabelResetSize();
  const { removeNodeHandleLayoutData } = useHandlesLayout();
  const { removeEdgeLayoutData } = useEditableEdgePath();
  const { invokeSingleClickTool } = useSingleClickTool();
  const { adjustSize } = useAdjustSize();

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
