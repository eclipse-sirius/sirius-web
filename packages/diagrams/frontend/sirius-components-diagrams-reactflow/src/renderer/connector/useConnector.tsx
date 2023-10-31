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

import { gql } from '@apollo/client';
import { useLazyQuery } from '@apollo/client/react';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useTheme } from '@material-ui/core/styles';
import { useContext, useEffect, useState } from 'react';
import { Connection, OnConnect, OnConnectEnd, OnConnectStart, OnConnectStartParams } from 'reactflow';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import {
  GQLDiagramDescription,
  GQLGetToolSectionsData,
  GQLGetToolSectionsVariables,
  GQLNodeDescription,
  GQLRepresentationDescription,
  GQLSingleClickOnTwoDiagramElementsTool,
  GQLTool,
  NodeStyleProvider,
  UseConnectorValue,
} from './useConnector.types';

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

const isSingleClickOnTwoDiagramElementsTool = (tool: GQLTool): tool is GQLSingleClickOnTwoDiagramElementsTool =>
  tool.__typename === 'SingleClickOnTwoDiagramElementsTool';

const isDiagramDescription = (
  representationDescription: GQLRepresentationDescription
): representationDescription is GQLDiagramDescription => representationDescription.__typename === 'DiagramDescription';

export const useConnector = (): UseConnectorValue => {
  const { connection, setConnection, resetConnection, candidates, setCandidates, isNewConnection, setIsNewConnection } =
    useContext<ConnectorContextValue>(ConnectorContext);
  const { addErrorMessage } = useMultiToast();
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const [state, setState] = useState<OnConnectStartParams>({
    handleType: null,
    nodeId: null,
    handleId: null,
  });

  const theme = useTheme();

  const newConnectionStyleProvider: NodeStyleProvider = {
    getNodeStyle: (id: string): React.CSSProperties => {
      const nodeStyle: React.CSSProperties = {};
      if (isNewConnection && candidates.map((node) => node.id).includes(id)) {
        nodeStyle.boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
        nodeStyle.opacity = 1;
      } else if (isNewConnection) {
        nodeStyle.opacity = 0.4;
      }
      return nodeStyle;
    },
    getHandleStyle: (id: string, isNodeSelected: boolean): React.CSSProperties => {
      const handleStyle: React.CSSProperties = {};
      if (isNewConnection && candidates.map((node) => node.id).includes(id)) {
        handleStyle.boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
      } else if (isNewConnection) {
        handleStyle.opacity = 0.4;
      } else if (!isNodeSelected) {
        handleStyle.opacity = 0.0;
      }
      return handleStyle;
    },
  };

  const onConnect: OnConnect = (connection: Connection) => setConnection(connection);

  const onConnectStart: OnConnectStart = (
    _event: React.MouseEvent | React.TouchEvent,
    onConnectStartParams: OnConnectStartParams
  ) => {
    setState((prevState) => ({
      ...prevState,
      handleType: onConnectStartParams.handleType,
      nodeId: onConnectStartParams.nodeId,
      handleId: onConnectStartParams.handleId,
    }));
  };

  useEffect(() => {
    if (isNewConnection && state.nodeId && state.handleType) {
      getToolSections({
        variables: {
          editingContextId,
          diagramId,
          diagramElementId: state.nodeId,
        },
      });
    }
  }, [isNewConnection, state]);

  const onConnectorContextualMenuClose = () => resetConnection();

  const [getToolSections, { loading: toolSectionsLoading, data: toolSectionsData, error: toolSectionsError }] =
    useLazyQuery<GQLGetToolSectionsData, GQLGetToolSectionsVariables>(getToolSectionsQuery);

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
      setCandidates(nodesCandidates);

      if (toolSectionsError) {
        addErrorMessage(toolSectionsError.message);
      }
    }
  }, [toolSectionsLoading, toolSectionsData, toolSectionsError]);

  const onConnectEnd: OnConnectEnd = (_event: MouseEvent | TouchEvent) => {
    setCandidates([]);
    setIsNewConnection(false);
  };

  const onConnectionStartElementClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (event.button === 0) {
      setIsNewConnection(true);
    }
  };

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
    onConnectorContextualMenuClose,
    onConnectionStartElementClick,
    newConnectionStyleProvider,
    connection,
  };
};
