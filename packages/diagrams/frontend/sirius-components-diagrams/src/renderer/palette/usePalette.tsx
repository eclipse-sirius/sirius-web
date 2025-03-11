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

import { gql, useMutation } from '@apollo/client';
import { useDeletionConfirmationDialog, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDialog } from '../../dialog/useDialog';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useCollapseExpand } from '../tools/useCollapseExpand';
import { GQLCollapsingState } from '../tools/useCollapseExpand.types';
import { useDelete } from '../tools/useDelete';
import { GQLDeletionPolicy } from '../tools/useDelete.types';
import { GQLPalette, GQLSingleClickOnDiagramElementTool, GQLTool } from './Palette.types';
import { useDiagramElementPalette } from './useDiagramElementPalette';
import { useDiagramPalette } from './useDiagramPalette';
import {
  GQLErrorPayload,
  GQLInvokeSingleClickOnDiagramElementToolData,
  GQLInvokeSingleClickOnDiagramElementToolInput,
  GQLInvokeSingleClickOnDiagramElementToolPayload,
  GQLInvokeSingleClickOnDiagramElementToolSuccessPayload,
  GQLInvokeSingleClickOnDiagramElementToolVariables,
  GQLToolVariable,
  UsePaletteProps,
  UsePaletteValue,
} from './usePalette.types';
import { usePaletteContents } from './usePaletteContents';

const invokeSingleClickOnDiagramElementToolMutation = gql`
  mutation invokeSingleClickOnDiagramElementTool($input: InvokeSingleClickOnDiagramElementToolInput!) {
    invokeSingleClickOnDiagramElementTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnDiagramElementToolSuccessPayload {
        newSelection {
          entries {
            id
          }
        }
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLInvokeSingleClickOnDiagramElementToolPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isInvokeSingleClickSuccessPayload = (
  payload: GQLInvokeSingleClickOnDiagramElementToolPayload
): payload is GQLInvokeSingleClickOnDiagramElementToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnDiagramElementToolSuccessPayload';

const isSingleClickOnDiagramElementTool = (tool: GQLTool): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

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

  const { addMessages } = useMultiToast();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();
  const { showDialog } = useDialog();
  const { setSelection } = useSelection();

  const { hideDiagramPalette } = useDiagramPalette();
  const { hideDiagramElementPalette } = useDiagramElementPalette();

  const closeAllPalettes = useCallback(() => {
    hideDiagramPalette();
    hideDiagramElementPalette();
    domNode?.focus();
  }, [hideDiagramPalette, hideDiagramElementPalette]);

  const [invokeSingleClickOnDiagramElementTool] = useMutation<
    GQLInvokeSingleClickOnDiagramElementToolData,
    GQLInvokeSingleClickOnDiagramElementToolVariables
  >(invokeSingleClickOnDiagramElementToolMutation);

  const invokeSingleClickTool = useCallback(
    async (tool: GQLTool, variables: GQLToolVariable[]) => {
      if (isSingleClickOnDiagramElementTool(tool)) {
        const { id: toolId } = tool;
        const input: GQLInvokeSingleClickOnDiagramElementToolInput = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: diagramId,
          diagramElementId,
          toolId,
          startingPositionX: x,
          startingPositionY: y,
          variables,
        };

        const { data } = await invokeSingleClickOnDiagramElementTool({
          variables: { input },
        });
        if (data) {
          const { invokeSingleClickOnDiagramElementTool } = data;
          if (isInvokeSingleClickSuccessPayload(invokeSingleClickOnDiagramElementTool)) {
            const { newSelection } = invokeSingleClickOnDiagramElementTool;
            if (newSelection?.entries.length ?? 0 > 0) {
              setSelection(newSelection);
            }
            addMessages(invokeSingleClickOnDiagramElementTool.messages);
          }
          if (isErrorPayload(invokeSingleClickOnDiagramElementTool)) {
            addMessages(invokeSingleClickOnDiagramElementTool.messages);
          }
        }
      }
    },
    [
      x,
      y,
      editingContextId,
      diagramId,
      diagramElementId,
      invokeSingleClickOnDiagramElementToolMutation,
      isSingleClickOnDiagramElementTool,
    ]
  );

  const { deleteDiagramElements } = useDelete(editingContextId, diagramId);

  const invokeDelete = (diagramElementId: string, deletionPolicy: GQLDeletionPolicy) => {
    if (!!nodeLookup.get(diagramElementId)) {
      deleteDiagramElements([diagramElementId], [], deletionPolicy);
    } else if (!!edgeLookup.get(diagramElementId)) {
      deleteDiagramElements([], [diagramElementId], deletionPolicy);
    }
  };

  const { collapseExpandElement } = useCollapseExpand(editingContextId, diagramId);

  const handleDialogDescription = (tool: GQLSingleClickOnDiagramElementTool) => {
    const onConfirm = (variables: GQLToolVariable[]) => {
      invokeSingleClickTool(tool, variables);
    };
    showDialog(tool.dialogDescriptionId, [{ name: 'targetObjectId', value: targetObjectId }], onConfirm, () => {});
  };

  const { setLastToolInvoked } = useDiagramPalette();

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
        if (isSingleClickOnDiagramElementTool(tool)) {
          if (tool.dialogDescriptionId) {
            handleDialogDescription(tool);
          } else {
            invokeSingleClickTool(tool, []);
          }
        }
        break;
    }
    if (palette) {
      setLastToolInvoked(palette.id, tool);
    }
  };
  return { handleToolClick, palette };
};
