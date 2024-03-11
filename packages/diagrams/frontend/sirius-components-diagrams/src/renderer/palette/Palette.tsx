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

import { gql, useMutation, useQuery } from '@apollo/client';
import { useDeletionConfirmationDialog, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import Paper from '@material-ui/core/Paper';
import Tooltip from '@material-ui/core/Tooltip';
import { makeStyles } from '@material-ui/core/styles';
import AdjustIcon from '@material-ui/icons/Adjust';
import TonalityIcon from '@material-ui/icons/Tonality';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import React, { useCallback, useContext, useEffect, useState } from 'react';
import { useReactFlow, useViewport } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { PinIcon } from '../../icons/PinIcon';
import { UnpinIcon } from '../../icons/UnpinIcon';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { Tool } from '../Tool';
import { useAdjustSize } from '../adjust-size/useAdjustSize';
import { useFadeDiagramElements } from '../fade/useFadeDiagramElements';
import { useHideDiagramElements } from '../hide/useHideDiagramElements';
import { usePinDiagramElements } from '../pin/usePinDiagramElements';
import { DiagramPaletteToolContextValue } from './DiagramPalette.types';
import { DiagramPaletteToolContext } from './DiagramPaletteToolContext';
import { DiagramPaletteToolContributionComponentProps } from './DiagramPaletteToolContribution.types';
import {
  ContextualPaletteStyleProps,
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
  GQLPalette,
  GQLRepresentationDescription,
  GQLSingleClickOnDiagramElementTool,
  GQLTool,
  GQLUpdateCollapsingStateData,
  GQLUpdateCollapsingStateInput,
  GQLUpdateCollapsingStateVariables,
  PaletteProps,
  PaletteState,
} from './Palette.types';
import { ToolSection } from './tool-section/ToolSection';

const usePaletteStyle = makeStyles((theme) => ({
  palette: {
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '2px',
    zIndex: 2,
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
  },
  paletteContent: {
    display: 'grid',
    gridTemplateColumns: ({ toolCount }: ContextualPaletteStyleProps) => `repeat(${Math.min(toolCount, 10)}, 36px)`,
    gridTemplateRows: '28px',
    gridAutoRows: '28px',
    placeItems: 'center',
  },
  toolIcon: {
    color: theme.palette.text.primary,
  },
}));

const ToolFields = gql`
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
      selectionDescriptionId
    }
  }
`;

export const getPaletteQuery = gql`
  ${ToolFields}
  query getPalette($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              palette(diagramElementId: $diagramElementId) {
                id
                tools {
                  ...ToolFields
                }
                toolSections {
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
`;

const invokeSingleClickOnDiagramElementToolMutation = gql`
  mutation invokeSingleClickOnDiagramElementTool($input: InvokeSingleClickOnDiagramElementToolInput!) {
    invokeSingleClickOnDiagramElementTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnDiagramElementToolSuccessPayload {
        newSelection {
          entries {
            id
            label
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

export const Palette = ({
  x: paletteX,
  y: paletteY,
  diagramElementId,
  onDirectEditClick,
  hideableDiagramElement,
}: PaletteProps) => {
  const [state, setState] = useState<PaletteState>({ expandedToolSectionId: null });

  const { fadeDiagramElements } = useFadeDiagramElements();
  const { pinDiagramElements } = usePinDiagramElements();
  const { adjustSize } = useAdjustSize();
  const { hideDiagramElements } = useHideDiagramElements();
  const { getNodes, getEdges } = useReactFlow<NodeData, EdgeData>();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { addErrorMessage, addMessages } = useMultiToast();
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const diagramPaletteToolComponents = useContext<DiagramPaletteToolContextValue>(DiagramPaletteToolContext)
    .filter((contribution) => contribution.props.canHandle(diagramId, diagramElementId))
    .map((contribution) => contribution.props.component);

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

  const description: GQLRepresentationDescription | undefined =
    paletteData?.viewer.editingContext.representation.description;
  const palette: GQLPalette | null = description && isDiagramDescription(description) ? description.palette : null;
  const node = getNodes().find((node) => node.id === diagramElementId);

  const toolCount =
    (palette
      ? palette.tools.filter(isSingleClickOnDiagramElementTool).length +
        palette.toolSections.filter(
          (toolSection) => toolSection.tools.filter(isSingleClickOnDiagramElementTool).length > 0
        ).length
      : 0) +
    (hideableDiagramElement ? (node ? 4 : 2) : 0) +
    diagramPaletteToolComponents.length;
  const classes = usePaletteStyle({ toolCount });

  let pinUnpinTool: JSX.Element | undefined;
  let adjustSizeTool: JSX.Element | undefined;
  if (node) {
    pinUnpinTool = node.data.pinned ? (
      <Tooltip title="Unpin element">
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Unpin element"
          onClick={() => pinDiagramElements([diagramElementId], !node.data.pinned)}
          data-testid="Unpin-element">
          <UnpinIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    ) : (
      <Tooltip title="Pin element">
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Pin element"
          onClick={() => pinDiagramElements([diagramElementId], true)}
          data-testid="Pin-element">
          <PinIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    );
    adjustSizeTool = (
      <Tooltip title="Adjust size">
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Adjust element"
          onClick={() => adjustSize(diagramElementId)}
          data-testid="adjust-element">
          <AdjustIcon fontSize="small" />
        </IconButton>
      </Tooltip>
    );
  }

  let x: number = 0;
  let y: number = 0;
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  if (viewportZoom !== 0 && paletteX && paletteY) {
    x = (paletteX - viewportX) / viewportZoom;
    y = (paletteY - viewportY) / viewportZoom;
  }

  useEffect(() => {
    if (paletteError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [paletteError]);

  const handleToolSectionExpand = (expandedToolSectionId: string | null) =>
    setState((prevState) => ({ ...prevState, expandedToolSectionId }));

  const [deleteElementsMutation] = useMutation<GQLDeleteFromDiagramData, GQLDeleteFromDiagramVariables>(
    deleteFromDiagramMutation
  );

  const [invokeSingleClickOnDiagramElementTool] = useMutation<
    GQLInvokeSingleClickOnDiagramElementToolData,
    GQLInvokeSingleClickOnDiagramElementToolVariables
  >(invokeSingleClickOnDiagramElementToolMutation);

  const invokeSingleClickTool = useCallback(
    async (tool: GQLTool) => {
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
          selectedObjectId: null,
        };

        const { data } = await invokeSingleClickOnDiagramElementTool({
          variables: { input },
        });
        if (data) {
          const { invokeSingleClickOnDiagramElementTool } = data;
          if (isInvokeSingleClickSuccessPayload(invokeSingleClickOnDiagramElementTool)) {
            addMessages(invokeSingleClickOnDiagramElementTool.messages);
          }
          if (isErrorPayload(invokeSingleClickOnDiagramElementTool)) {
            addMessages(invokeSingleClickOnDiagramElementTool.messages);
          }
        }
      }
    },
    [
      editingContextId,
      diagramId,
      diagramElementId,
      invokeSingleClickOnDiagramElementToolMutation,
      isSingleClickOnDiagramElementTool,
    ]
  );

  const invokeDelete = (diagramElementId: string, deletionPolicy: GQLDeletionPolicy) => {
    const nodeId = getNodes().find((node) => node.id === diagramElementId);
    if (nodeId) {
      invokeDeleteMutation([diagramElementId], [], deletionPolicy);
    } else {
      const edgeId = getEdges().find((edge) => edge.id === diagramElementId);
      if (edgeId) {
        invokeDeleteMutation([], [diagramElementId], deletionPolicy);
      }
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

  const handleToolClick = (tool: GQLTool) => {
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
        invokeSingleClickTool(tool);
        break;
    }
  };

  const invokeFadeDiagramElementTool = () => {
    fadeDiagramElements([diagramElementId], true);
  };

  const invokeHideDiagramElementTool = () => {
    hideDiagramElements([diagramElementId], true);
  };

  const shouldRender = palette && (node || (!node && toolCount > 0));
  if (!shouldRender) {
    return null;
  }

  return (
    <Paper
      className={classes.palette}
      style={{ position: 'absolute', left: paletteX, top: paletteY }}
      data-testid="Palette">
      <div className={classes.paletteContent}>
        {palette?.tools.filter(isSingleClickOnDiagramElementTool).map((tool) => (
          <Tool tool={tool} onClick={handleToolClick} thumbnail key={tool.id} />
        ))}
        {palette?.toolSections.map((toolSection) => (
          <ToolSection
            toolSection={toolSection}
            onToolClick={handleToolClick}
            key={toolSection.id}
            onExpand={handleToolSectionExpand}
            toolSectionExpandId={state.expandedToolSectionId}
          />
        ))}
        {diagramPaletteToolComponents.map((component, index) => {
          const props: DiagramPaletteToolContributionComponentProps = {
            x,
            y,
            diagramElementId,
            key: index.toString(),
          };
          return React.createElement(component, props);
        })}
        {hideableDiagramElement ? (
          <>
            <Tooltip title="Hide element">
              <IconButton
                className={classes.toolIcon}
                size="small"
                aria-label="Hide element"
                onClick={invokeHideDiagramElementTool}
                data-testid="Hide-element">
                <VisibilityOffIcon fontSize="small" />
              </IconButton>
            </Tooltip>
            <Tooltip title="Fade element">
              <IconButton
                className={classes.toolIcon}
                size="small"
                aria-label="Fade element"
                onClick={invokeFadeDiagramElementTool}
                data-testid="Fade-element">
                <TonalityIcon fontSize="small" />
              </IconButton>
            </Tooltip>
            {pinUnpinTool}
            {adjustSizeTool}
          </>
        ) : null}
      </div>
    </Paper>
  );
};
