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
import { useCallback, useContext, useEffect, useState } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { Tool } from '../Tool';
import { useFadeDiagramElements } from '../fade/useFadeDiagramElements';
import { useHideDiagramElements } from '../hide/useHideDiagramElements';
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
  GQLRepresentationDescription,
  GQLSingleClickOnDiagramElementTool,
  GQLTool,
  GQLUpdateCollapsingStateData,
  GQLUpdateCollapsingStateInput,
  GQLUpdateCollapsingStateVariables,
  PaletteProps,
} from './Palette.types';

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

export const getToolSectionsQuery = gql`
  query getToolSections($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              toolSections(diagramElementId: $diagramElementId) {
                id
                label
                imageURL
                tools {
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

export const Palette = ({ diagramElementId, onDirectEditClick, isNodePalette }: PaletteProps) => {
  const [tools, setTools] = useState<GQLTool[]>([]);
  const { fadeDiagramElements } = useFadeDiagramElements();
  const { hideDiagramElements } = useHideDiagramElements();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const toolCount = tools.length + (isNodePalette ? 2 : 0);
  const classes = usePaletteStyle({ toolCount });

  const [getTools, { loading: toolSectionsLoading, data: toolSectionsData, error: toolSectionsError }] = useLazyQuery<
    GQLGetToolSectionsData,
    GQLGetToolSectionsVariables
  >(getToolSectionsQuery);

  useEffect(() => {
    if (!toolSectionsLoading) {
      if (toolSectionsData) {
        const representationDescription: GQLRepresentationDescription =
          toolSectionsData.viewer.editingContext.representation.description;
        if (isDiagramDescription(representationDescription)) {
          const nodeTools = representationDescription.toolSections
            .flatMap((toolSection) => {
              return toolSection.tools;
            })
            .filter(isSingleClickOnDiagramElementTool);
          setTools(nodeTools);
        }
      }
    }
  }, [
    toolSectionsLoading,
    toolSectionsData,
    toolSectionsError,
    setTools,
    isSingleClickOnDiagramElementTool,
    isDiagramDescription,
  ]);

  useEffect(() => {
    getTools({
      variables: {
        editingContextId: editingContextId,
        diagramId,
        diagramElementId,
      },
    });
  }, [editingContextId, diagramId, getTools]);

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

  const invokeDelete = useCallback(
    (nodeIds: string[], deletionPolicy: GQLDeletionPolicy) => {
      const input: GQLDeleteFromDiagramInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        nodeIds,
        edgeIds: [],
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
        invokeDelete([diagramElementId], GQLDeletionPolicy.SEMANTIC);
        break;
      case 'graphical-delete':
        invokeDelete([diagramElementId], GQLDeletionPolicy.GRAPHICAL);
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
      {tools.filter(isSingleClickOnDiagramElementTool).map((tool) => (
        <Tool tool={tool} onClick={handleToolClick} key={tool.id} />
      ))}
      {isNodePalette ? (
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
