/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { useCallback } from 'react';
import { useCollapseExpandElement } from '../../collapse/useCollapseExpandElement';
import { GQLCollapsingState } from '../../collapse/useCollapseExpandElement.types';
import { useDiagramDeleteMutation } from '../../delete/useDiagramDeleteMutation';
import { GQLDeletionPolicy } from '../../delete/useDiagramDeleteMutation.types';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { useFadeDiagramElements } from '../../fade/useFadeDiagramElements';
import { useHideDiagramElements } from '../../hide/useHideDiagramElements';
import { usePinDiagramElements } from '../../pin/usePinDiagramElements';
import { GQLTool } from '../Palette.types';
import { useSingleClickOnDiagramElementTool } from './../tools/useSingleClickOnDiagramElementTool';
import { useDiagramElementPalette } from './../useDiagramElementPalette';
import { useDiagramPalette } from './../useDiagramPalette';
import { InvokeTool, InvokeToolProps } from './useInvokeTool.types';
import { GQLToolVariable } from './useSingleClickOnDiagramElementTool.types';

export const useInvokeTool = ({
  x,
  y,
  diagramElementId,
  targetObjectId,
  palette,
  onDirectEditClick,
}: InvokeToolProps): InvokeTool => {
  const { fadeDiagramElements } = useFadeDiagramElements();
  const { hideDiagramElements } = useHideDiagramElements();
  const { pinDiagramElements } = usePinDiagramElements();
  const { collapseExpandElement } = useCollapseExpandElement();
  const { deleteElements } = useDiagramDeleteMutation();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();
  const { invokeSingleClickTool } = useSingleClickOnDiagramElementTool({
    x,
    y,
    diagramElementId,
    targetObjectId,
    onDirectEditClick,
  });

  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { hideDiagramPalette, setLastToolInvoked } = useDiagramPalette();
  const { hideDiagramElementPalette } = useDiagramElementPalette();

  const closeAllPalettes = useCallback(() => {
    hideDiagramPalette();
    hideDiagramElementPalette();
  }, [hideDiagramPalette, hideDiagramElementPalette]);

  const invokeDelete = (diagramElementId: string, deletionPolicy: GQLDeletionPolicy) => {
    if (!!nodeLookup.get(diagramElementId)) {
      deleteElements([diagramElementId], [], deletionPolicy);
    } else if (!!edgeLookup.get(diagramElementId)) {
      deleteElements([], [diagramElementId], deletionPolicy);
    }
  };

  const invokeTool = (tool: GQLTool, variables: GQLToolVariable[]) => {
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
      case 'fade':
        fadeDiagramElements([diagramElementId], true);
        break;
      case 'unfade':
        fadeDiagramElements([diagramElementId], false);
        break;
      case 'hide':
        hideDiagramElements([diagramElementId], true);
        break;
      case 'pin':
        pinDiagramElements([diagramElementId], true);
        break;
      case 'unpin':
        pinDiagramElements([diagramElementId], false);
        break;
      default:
        invokeSingleClickTool(tool, variables);
        break;
    }
    if (palette) {
      setLastToolInvoked(palette.id, tool);
    }
  };

  return { invokeTool };
};
