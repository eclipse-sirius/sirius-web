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

import { gql, useLazyQuery, useMutation } from '@apollo/client';
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import TonalityIcon from '@material-ui/icons/Tonality';
import VisibilityOffIcon from '@material-ui/icons/VisibilityOff';
import React, { useCallback, useContext, useEffect, useState } from 'react';
import { useEdges, useNodes } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useFadeDiagramElements } from '../fade/useFadeDiagramElements';
import { useHideDiagramElements } from '../hide/useHideDiagramElements';
import { Tool } from '../Tool';
import { DiagramPaletteToolContextValue } from './DiagramPalette.types';
import { DiagramPaletteToolContext } from './DiagramPaletteToolContext';
import { DiagramPaletteToolContributionComponentProps } from './DiagramPaletteToolContribution.types';
import {
  ContextualPaletteStyleProps,
  GQLCollapsingState,
  GQLDeleteFromDiagramData,
  GQLDeleteFromDiagramInput,
  GQLDeleteFromDiagramVariables,
  GQLDeletionPolicy,
  GQLDiagramDescription,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLInvokeSingleClickOnDiagramElementToolData,
  GQLInvokeSingleClickOnDiagramElementToolInput,
  GQLInvokeSingleClickOnDiagramElementToolVariables,
  GQLPalette,
  GQLRepresentationDescription,
  GQLSingleClickOnDiagramElementTool,
  GQLTool,
  GQLUpdateCollapsingStateData,
  GQLUpdateCollapsingStateInput,
  GQLUpdateCollapsingStateVariables,
  PaletteProps,
} from './Palette.types';
import { ToolSection } from './tool-section/ToolSection';

const usePaletteStyle = makeStyles((theme) => ({
  toolEntries: {
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
    imageURL
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
                  imageURL
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
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const deleteFromDiagramMutation = gql`
  mutation deleteFromDiagram($input: DeleteFromDiagramInput!) {
    deleteFromDiagram(input: $input) {
      __typename
      ... on ErrorPayload {
        message
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

const isSingleClickOnDiagramElementTool = (tool: GQLTool): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const Palette = ({ diagramElementId, onDirectEditClick, isDiagramElementPalette }: PaletteProps) => {
  const [palette, setPalette] = useState<GQLPalette | undefined>(undefined);
  const [toolSectionExpandId, setToolSectionExpandId] = useState<string | undefined>(undefined);
  const { fadeDiagramElements } = useFadeDiagramElements();
  const { hideDiagramElements } = useHideDiagramElements();
  const nodes = useNodes<NodeData>();
  const edges = useEdges<EdgeData>();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const diagramPaletteToolComponents = useContext<DiagramPaletteToolContextValue>(DiagramPaletteToolContext)
    .filter((contribution) => contribution.props.canHandle(diagramId, diagramElementId))
    .map((contribution) => contribution.props.component);

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

  const [getPalette, { loading: paletteLoading, data: paletteData, error: paletteError }] = useLazyQuery<
    GQLGetToolSectionsData,
    GQLGetToolSectionsVariables
  >(getPaletteQuery);

  useEffect(() => {
    if (!paletteLoading) {
      if (paletteData) {
        const representationDescription: GQLRepresentationDescription =
          paletteData.viewer.editingContext.representation.description;
        if (isDiagramDescription(representationDescription)) {
          setPalette(representationDescription.palette);
        }
      }
    }
  }, [paletteLoading, paletteData, paletteError, setPalette, isSingleClickOnDiagramElementTool, isDiagramDescription]);

  useEffect(() => {
    getPalette({
      variables: {
        editingContextId: editingContextId,
        diagramId,
        diagramElementId,
      },
    });
  }, [editingContextId, diagramId, getPalette]);

  const [deleteElementsMutation] = useMutation<GQLDeleteFromDiagramData, GQLDeleteFromDiagramVariables>(
    deleteFromDiagramMutation
  );

  const [invokeSingleClickOnDiagramElementTool] = useMutation<
    GQLInvokeSingleClickOnDiagramElementToolData,
    GQLInvokeSingleClickOnDiagramElementToolVariables
  >(invokeSingleClickOnDiagramElementToolMutation);

  const invokeSingleClickTool = useCallback(
    (tool: GQLTool) => {
      if (isSingleClickOnDiagramElementTool(tool)) {
        const { id: toolId } = tool;
        const input: GQLInvokeSingleClickOnDiagramElementToolInput = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: diagramId,
          diagramElementId,
          toolId,
          startingPositionX: 0,
          startingPositionY: 0,
          selectedObjectId: null,
        };

        invokeSingleClickOnDiagramElementTool({ variables: { input } });
      }
    },
    [
      editingContextId,
      diagramId,
      diagramElementId,
      invokeSingleClickOnDiagramElementTool,
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
    (nodeIds: string[], edgeIds: string[], deletionPolicy: GQLDeletionPolicy) => {
      const input: GQLDeleteFromDiagramInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        nodeIds,
        edgeIds,
        deletionPolicy,
      };
      deleteElementsMutation({ variables: { input } });
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

  return (
    <div className={classes.toolEntries}>
      {palette?.tools.filter(isSingleClickOnDiagramElementTool).map((tool) => (
        <Tool tool={tool} onClick={handleToolClick} thumbnail key={tool.id} />
      ))}
      {palette?.toolSections.map((toolSection) => (
        <ToolSection
          toolSection={toolSection}
          onToolClick={handleToolClick}
          key={toolSection.id}
          onExpand={setToolSectionExpandId}
          toolSectionExpandId={toolSectionExpandId}
        />
      ))}
      {diagramPaletteToolComponents.map((component, index) => {
        const props: DiagramPaletteToolContributionComponentProps = {
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
