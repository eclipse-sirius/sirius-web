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
import { makeStyles } from '@material-ui/core/styles';
import { useCallback, useContext, useEffect, useState } from 'react';
import { NodeToolbar, Position } from 'reactflow';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramContextValue } from '../contexts/DiagramContext.types';
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
import { Tool } from './Tool';

const useContextualPaletteStyle = makeStyles((theme) => ({
  toolbar: {
    background: theme.palette.background.paper,
    border: '1px solid #d1dadb',
    boxShadow: '0px 2px 5px #002b3c40',
    borderRadius: '2px',
    zIndex: 2,
    position: 'fixed',
    display: 'flex',
    alignItems: 'center',
  },
  toolEntries: {
    display: 'grid',
    gridTemplateColumns: ({ toolCount }: ContextualPaletteStyleProps) => `repeat(${Math.min(toolCount, 10)}, 36px)`,
    gridTemplateRows: '28px',
    gridAutoRows: '28px',
    placeItems: 'center',
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

export const Palette = ({ diagramElementId }: PaletteProps) => {
  const [tools, setTools] = useState<GQLTool[]>([]);

  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const classes = useContextualPaletteStyle({ toolCount: tools.length });

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
        console.warn('not Implemented yet');
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

  return (
    <NodeToolbar className={classes.toolbar} position={Position.Top}>
      <div className={classes.toolEntries}>
        {tools.filter(isSingleClickOnDiagramElementTool).map((tool) => (
          <Tool tool={tool} onClick={handleToolClick} key={tool.id} />
        ))}
      </div>
    </NodeToolbar>
  );
};
