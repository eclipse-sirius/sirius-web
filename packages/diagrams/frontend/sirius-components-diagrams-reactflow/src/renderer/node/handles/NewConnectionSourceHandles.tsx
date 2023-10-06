/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import React, { useContext, useEffect, useState } from 'react';
import { Handle, Position, useUpdateNodeInternals } from 'reactflow';
import { DiagramContext } from '../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../contexts/DiagramContext.types';
import { ConnectorContext } from '../../connector/ConnectorContext';
import { ConnectorContextValue } from '../../connector/ConnectorContext.types';
import { useConnector } from '../../connector/useConnector';
import {
  GQLDiagramDescription,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLNodeDescription,
  GQLRepresentationDescription,
  GQLSingleClickOnTwoDiagramElementsTool,
  GQLTool,
} from '../../connector/useConnector.types';
import { ConnectionSourceHandlesProps, ConnectionSourceHandlesStates } from './NewConnectionSourceHandles.types';

const getToolSectionsQuery = gql`
  query getToolSections($editingContextId: ID!, $diagramId: ID!, $diagramElementId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $diagramId) {
          description {
            ... on DiagramDescription {
              palette(diagramElementId: $diagramElementId) {
                tools {
                  __typename
                  ... on SingleClickOnTwoDiagramElementsTool {
                    candidates {
                      sources {
                        id
                      }
                      targets {
                        id
                      }
                    }
                  }
                }
                toolSections {
                  tools {
                    __typename
                    ... on SingleClickOnTwoDiagramElementsTool {
                      candidates {
                        sources {
                          id
                        }
                        targets {
                          id
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
    }
  }
`;

const newConnectionSourceHandleStyle = (
  position: Position,
  theme: Theme,
  isHovered: Position | null,
  isMouseDown: Position | null
): React.CSSProperties => {
  const style: React.CSSProperties = {
    position: 'absolute',
    borderRadius: '0',
    border: 'solid black',
    borderWidth: '0 2px 2px 0',
    display: 'inline-block',
    padding: '2px',
    borderColor: theme.palette.secondary.light,
    backgroundColor: 'transparent',
    width: 12,
    height: 12,
    zIndex: 9999,
  };
  switch (position) {
    case Position.Left:
      style.left = '-15px';
      style.transform = 'rotate(135deg)';
      if (isHovered === Position.Left || isMouseDown === Position.Left) {
        style.borderColor = theme.palette.primary.main;
      }
      break;
    case Position.Right:
      style.right = '-15px';
      style.transform = 'rotate(-45deg)';
      if (isHovered === Position.Right || isMouseDown === Position.Right) {
        style.borderColor = theme.palette.primary.main;
      }
      break;
    case Position.Top:
      style.top = '-15px';
      style.transform = 'rotate(-135deg)';
      if (isHovered === Position.Top || isMouseDown === Position.Top) {
        style.borderColor = theme.palette.primary.main;
      }
      break;
    case Position.Bottom:
      style.bottom = '-15px';
      style.transform = 'rotate(45deg)';
      if (isHovered === Position.Bottom || isMouseDown === Position.Bottom) {
        style.borderColor = theme.palette.primary.main;
      }
      break;
  }
  return style;
};

const isSingleClickOnTwoDiagramElementsTool = (tool: GQLTool): tool is GQLSingleClickOnTwoDiagramElementsTool =>
  tool.__typename === 'SingleClickOnTwoDiagramElementsTool';

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const ConnectionSourceHandles = ({ nodeId }: ConnectionSourceHandlesProps) => {
  const { onConnectionStartElementClick } = useConnector();
  const updateNodeInternals = useUpdateNodeInternals();
  const theme = useTheme();
  const { setCandidates } = useContext<ConnectorContextValue>(ConnectorContext);
  const { addErrorMessage } = useMultiToast();
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  //Refresh handles
  useEffect(() => {
    updateNodeInternals(nodeId);
  }, []);
  const {
    loading: toolSectionsLoading,
    data: toolSectionsData,
    error: toolSectionsError,
  } = useQuery<GQLGetToolSectionsData, GQLGetToolSectionsVariables>(getToolSectionsQuery, {
    variables: {
      editingContextId,
      diagramId,
      diagramElementId: nodeId,
    },
  });

  const [state, setState] = useState<ConnectionSourceHandlesStates>({
    shouldRender: false,
    isHovered: null,
    isMouseDown: null,
  });

  useEffect(() => {
    if (!toolSectionsLoading && toolSectionsData) {
      const diagramDescription: GQLRepresentationDescription =
        toolSectionsData.viewer.editingContext.representation.description;
      const nodesCandidates: GQLNodeDescription[] = [];
      if (isDiagramDescription(diagramDescription)) {
        diagramDescription.palette.tools.filter(isSingleClickOnTwoDiagramElementsTool).forEach((tool) => {
          tool.candidates.forEach((candidate) => nodesCandidates.push(...candidate.targets));
        });
        diagramDescription.palette.toolSections.forEach((toolSection) => {
          toolSection.tools.filter(isSingleClickOnTwoDiagramElementsTool).forEach((tool) => {
            tool.candidates.forEach((candidate) => nodesCandidates.push(...candidate.targets));
          });
        });
      }
      if (nodesCandidates.length > 0) {
        setState((prevState) => ({
          ...prevState,
          shouldRender: true,
        }));
        setCandidates(nodesCandidates);
      }

      if (toolSectionsError) {
        addErrorMessage(toolSectionsError.message);
      }
    }
  }, [toolSectionsLoading, toolSectionsData, toolSectionsError]);

  const handleOnMouseDown = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, position: Position) => {
    onConnectionStartElementClick(event);
    setState((prevState) => ({
      ...prevState,
      isMouseDown: position,
    }));
  };

  const handleOnMouseEnter = (position: Position) => {
    setState((prevState) => ({
      ...prevState,
      isHovered: position,
    }));
  };

  const handleOnMouseLeave = () => {
    setState((prevState) => ({
      ...prevState,
      isHovered: null,
    }));
  };

  return (
    <>
      {state.shouldRender
        ? Object.values(Position).map((key) => {
            return (
              <Handle
                id={`handle--${nodeId}--${key}`}
                type="source"
                position={key}
                style={newConnectionSourceHandleStyle(key, theme, state.isHovered, state.isMouseDown)}
                onMouseDown={(event) => handleOnMouseDown(event, key)}
                onMouseEnter={() => handleOnMouseEnter(key)}
                onMouseLeave={handleOnMouseLeave}
                isConnectableStart={true}
                key={key}
              />
            );
          })
        : null}
    </>
  );
};
