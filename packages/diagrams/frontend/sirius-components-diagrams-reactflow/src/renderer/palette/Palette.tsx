/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import CircularProgress from '@material-ui/core/CircularProgress';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import TonalityIcon from '@material-ui/icons/Tonality';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import React, { useCallback, useContext, useEffect, useState } from 'react';
import { useEdges, useNodes } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { Tool } from '../Tool';
import { useFadeDiagramElements } from '../fade/useFadeDiagramElements';
import { useHideDiagramElements } from '../hide/useHideDiagramElements';
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
    display: 'grid',
    gridTemplateRows: '28px',
    gridAutoRows: '28px',
    placeItems: 'center',
  },
  toolEntries: {
    gridTemplateColumns: ({ toolCount }: ContextualPaletteStyleProps) => `repeat(${Math.min(toolCount, 10)}, 36px)`,
  },
  loading: {
    gridTemplateColumns: '36px',
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

export const Palette = ({ x, y, diagramElementId, onDirectEditClick, isDiagramElementPalette }: PaletteProps) => {
  const [state, setState] = useState<PaletteState>({ expandedToolSectionId: null });

  const { fadeDiagramElements } = useFadeDiagramElements();
  const { hideDiagramElements } = useHideDiagramElements();
  const nodes = useNodes<NodeData>();
  const edges = useEdges<EdgeData>();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const { addErrorMessage, addMessages } = useMultiToast();

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

  const toolCount =
    (palette
      ? palette.tools.filter(isSingleClickOnDiagramElementTool).length +
        palette.toolSections.filter(
          (toolSection) => toolSection.tools.filter(isSingleClickOnDiagramElementTool).length > 0
        ).length
      : 0) +
    (isDiagramElementPalette ? 2 : 0) +
    diagramPaletteToolComponents.length;
  const classes = usePaletteStyle({ toolCount });

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
    const nodeId = nodes.find((node) => node.id === diagramElementId);
    if (nodeId) {
      invokeDeleteMutation([diagramElementId], [], deletionPolicy);
    } else {
      const edgeId = edges.find((edge) => edge.id === diagramElementId);
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
        invokeDelete(diagramElementId, GQLDeletionPolicy.SEMANTIC);
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

  if (!palette) {
    return (
      <div className={`${classes.palette} ${classes.loading}`}>
        <CircularProgress size={26} />
      </div>
    );
  }

  return (
    <div className={`${classes.palette} ${classes.toolEntries}`} data-testid={'Palette'}>
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
      {isDiagramElementPalette ? (
        <>
          <IconButton
            className={classes.toolIcon}
            size="small"
            aria-label="hide elements"
            title="Hide elements"
            onClick={invokeHideDiagramElementTool}
            data-testid="Hide-elements">
            <VisibilityOffIcon fontSize="small" />
          </IconButton>
          <IconButton
            className={classes.toolIcon}
            size="small"
            aria-label="Fade elements"
            title="Fade elements"
            onClick={invokeFadeDiagramElementTool}
            data-testid="Fade-elements">
            <TonalityIcon fontSize="small" />
          </IconButton>
        </>
      ) : null}
    </div>
  );
};
