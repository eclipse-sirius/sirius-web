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

import { gql, useMutation, useQuery } from '@apollo/client';
import { useDeletionConfirmationDialog, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDialog } from '../../dialog/useDialog';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { GQLPalette, GQLSingleClickOnDiagramElementTool, GQLTool } from './Palette.types';

import { useDiagramElementPalette } from './useDiagramElementPalette';
import { useDiagramPalette } from './useDiagramPalette';
import {
  GQLCollapsingState,
  GQLDeleteFromDiagramData,
  GQLDeleteFromDiagramInput,
  GQLDeleteFromDiagramPayload,
  GQLDeleteFromDiagramSuccessPayload,
  GQLDeleteFromDiagramVariables,
  GQLDeletionPolicy,
  GQLDiagramDescription,
  GQLErrorPayload,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLInvokeSingleClickOnDiagramElementToolData,
  GQLInvokeSingleClickOnDiagramElementToolInput,
  GQLInvokeSingleClickOnDiagramElementToolPayload,
  GQLInvokeSingleClickOnDiagramElementToolSuccessPayload,
  GQLInvokeSingleClickOnDiagramElementToolVariables,
  GQLRepresentationDescription,
  GQLToolVariable,
  GQLUpdateCollapsingStateData,
  GQLUpdateCollapsingStateInput,
  GQLUpdateCollapsingStateVariables,
  UsePaletteProps,
  UsePaletteValue,
} from './usePalette.types';

export const getPaletteQuery = gql`
  fragment ToolFields on Tool {
    __typename
    id
    label
    iconURL
    ... on SingleClickOnDiagramElementTool {
      targetDescriptions {
        id
      }
      appliesToDiagramRoot
      dialogDescriptionId
    }
  }
  query getPalette($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              palette(diagramElementId: $diagramElementId) {
                id
                quickAccessTools {
                  ...ToolFields
                }
                paletteEntries {
                  ...ToolFields
                  ... on ToolSection {
                    id
                    label
                    iconURL
                    tools {
                      ...ToolFields
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
`;

const invokeSingleClickOnDiagramElementToolMutation = gql`
  mutation invokeSingleClickOnDiagramElementTool($input: InvokeSingleClickOnDiagramElementToolInput!) {
    invokeSingleClickOnDiagramElementTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnDiagramElementToolSuccessPayload {
        newSelection {
          entries {
            id
            kind
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

export const deleteFromDiagramMutation = gql`
  mutation deleteFromDiagram($input: DeleteFromDiagramInput!) {
    deleteFromDiagram(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on DeleteFromDiagramSuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const updateCollapsingStateMutation = gql`
  mutation updateCollapsingState($input: UpdateCollapsingStateInput!) {
    updateCollapsingState(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (
  payload: GQLDeleteFromDiagramPayload | GQLInvokeSingleClickOnDiagramElementToolPayload
): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';
const isDeleteSuccessPayload = (payload: GQLDeleteFromDiagramPayload): payload is GQLDeleteFromDiagramSuccessPayload =>
  payload.__typename === 'DeleteFromDiagramSuccessPayload';
const isInvokeSingleClickSuccessPayload = (
  payload: GQLInvokeSingleClickOnDiagramElementToolPayload
): payload is GQLInvokeSingleClickOnDiagramElementToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnDiagramElementToolSuccessPayload';

const isSingleClickOnDiagramElementTool = (tool: GQLTool): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const usePalette = ({
  x,
  y,
  diagramElementId,
  targetObjectId,
  onDirectEditClick,
}: UsePaletteProps): UsePaletteValue => {
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { addErrorMessage, addMessages } = useMultiToast();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();
  const { showDialog } = useDialog();
  const { setSelection } = useSelection();

  const { data: paletteData, error: paletteError } = useQuery<GQLGetToolSectionsData, GQLGetToolSectionsVariables>(
    getPaletteQuery,
    {
      variables: {
        editingContextId,
        diagramId,
        diagramElementId,
      },
    }
  );

  const { hideDiagramPalette } = useDiagramPalette();
  const { hideDiagramElementPalette } = useDiagramElementPalette();

  const closeAllPalettes = useCallback(() => {
    hideDiagramPalette();
    hideDiagramElementPalette();
  }, [hideDiagramPalette, hideDiagramElementPalette]);

  const description: GQLRepresentationDescription | undefined =
    paletteData?.viewer.editingContext.representation.description;
  const palette: GQLPalette | null = description && isDiagramDescription(description) ? description.palette : null;
  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  const [deleteElementsMutation] = useMutation<GQLDeleteFromDiagramData, GQLDeleteFromDiagramVariables>(
    deleteFromDiagramMutation
  );

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

  const invokeDelete = (diagramElementId: string, deletionPolicy: GQLDeletionPolicy) => {
    if (!!nodeLookup.get(diagramElementId)) {
      invokeDeleteMutation([diagramElementId], [], deletionPolicy);
    } else if (!!edgeLookup.get(diagramElementId)) {
      invokeDeleteMutation([], [diagramElementId], deletionPolicy);
    }
  };

  const invokeDeleteMutation = useCallback(
    async (nodeIds: string[], edgeIds: string[], deletionPolicy: GQLDeletionPolicy) => {
      const input: GQLDeleteFromDiagramInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        nodeIds,
        edgeIds,
        deletionPolicy,
      };
      const { data } = await deleteElementsMutation({ variables: { input } });
      if (data) {
        const { deleteFromDiagram } = data;
        if (isErrorPayload(deleteFromDiagram) || isDeleteSuccessPayload(deleteFromDiagram)) {
          addMessages(deleteFromDiagram.messages);
        }
      }
    },
    [editingContextId, diagramId, deleteElementsMutation]
  );

  const [collapseExpandMutation] = useMutation<GQLUpdateCollapsingStateData, GQLUpdateCollapsingStateVariables>(
    updateCollapsingStateMutation
  );

  const collapseExpandElement = useCallback(
    (nodeId: string, collapsingState: GQLCollapsingState) => {
      const input: GQLUpdateCollapsingStateInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        diagramElementId: nodeId,
        collapsingState,
      };
      collapseExpandMutation({ variables: { input } });
    },
    [editingContextId, diagramId, collapseExpandMutation]
  );

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
