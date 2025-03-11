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
import { useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useCollapseExpand } from '../tools/useCollapseExpand';
import { GQLCollapsingState } from '../tools/useCollapseExpand.types';
import { useDelete } from '../tools/useDelete';
import { GQLDeletionPolicy } from '../tools/useDelete.types';
import { useSingleClickTool } from '../tools/useSingleClickTool';
import { GQLPalette, GQLTool } from './Palette.types';
import { useDiagramElementPalette } from './useDiagramElementPalette';
import { useDiagramPalette } from './useDiagramPalette';
import { UsePaletteProps, UsePaletteValue } from './usePalette.types';
import { usePaletteContents } from './usePaletteContents';

export const usePalette = ({
  x,
  y,
  diagramElementId,
  targetObjectId,
  onDirectEditClick,
}: UsePaletteProps): UsePaletteValue => {
  const { nodeLookup, edgeLookup, domNode } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const palette: GQLPalette | null = usePaletteContents(diagramElementId);
  const { invokeSingleClickTool } = useSingleClickTool(editingContextId, diagramId);
  const { collapseExpandElement } = useCollapseExpand(editingContextId, diagramId);
  const { deleteDiagramElements } = useDelete(editingContextId, diagramId);

  const { showDeletionConfirmation } = useDeletionConfirmationDialog();
  const { hideDiagramPalette, setLastToolInvoked } = useDiagramPalette();
  const { hideDiagramElementPalette } = useDiagramElementPalette();

  const closeAllPalettes = useCallback(() => {
    hideDiagramPalette();
    hideDiagramElementPalette();
    domNode?.focus();
  }, [hideDiagramPalette, hideDiagramElementPalette]);

  const invokeDelete = (diagramElementId: string, deletionPolicy: GQLDeletionPolicy) => {
    if (!!nodeLookup.get(diagramElementId)) {
      deleteDiagramElements([diagramElementId], [], deletionPolicy);
    } else if (!!edgeLookup.get(diagramElementId)) {
      deleteDiagramElements([], [diagramElementId], deletionPolicy);
    }
  };

  const handleToolClick = (tool: GQLTool) => {
    closeAllPalettes();
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
        collapseExpandElement(diagramElementId, GQLCollapsingState.EXPANDED);
        break;
      case 'collapse':
        collapseExpandElement(diagramElementId, GQLCollapsingState.COLLAPSED);
        break;
      default:
        invokeSingleClickTool(tool, diagramElementId, targetObjectId, x, y);
        break;
    }
    if (palette) {
      setLastToolInvoked(palette.id, tool);
    }
  };
  return { handleToolClick, palette };
};
