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

import { gql, OnDataOptions, useQuery, useSubscription } from '@apollo/client';
import { RepresentationComponentProps, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import { ReactFlowProvider } from 'reactflow';
import { DiagramContext } from '../contexts/DiagramContext';
import { DiagramDescriptionContext } from '../contexts/DiagramDescriptionContext';
import { diagramEventSubscription } from '../graphql/subscription/diagramEventSubscription';
import {
  GQLDiagramEventPayload,
  GQLDiagramRefreshedEventPayload,
} from '../graphql/subscription/diagramEventSubscription.types';
import { ConnectorContextProvider } from '../renderer/connector/ConnectorContext';
import { DiagramRenderer } from '../renderer/DiagramRenderer';
import { DiagramDirectEditContextProvider } from '../renderer/direct-edit/DiagramDirectEditContext';
import { DropNodeContextProvider } from '../renderer/dropNode/DropNodeContext';
import { MarkerDefinitions } from '../renderer/edge/MarkerDefinitions';
import { FullscreenContextProvider } from '../renderer/fullscreen/FullscreenContext';
import { NodeContextProvider } from '../renderer/node/NodeContext';
import { DiagramElementPaletteContextProvider } from '../renderer/palette/DiagramElementPaletteContext';
import { DiagramPaletteContextProvider } from '../renderer/palette/DiagramPaletteContext';
import {
  DiagramRepresentationState,
  GQLDiagramDescription,
  GQLDiagramDescriptionData,
  GQLDiagramDescriptionVariables,
  GQLDiagramEventData,
  GQLDiagramEventVariables,
} from './DiagramRepresentation.types';
import { StoreContextProvider } from './StoreContext';

const subscription = gql(diagramEventSubscription);

export const getDiagramDescription = gql`
  query getDiagramDescription($editingContextId: ID!, $representationId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on DiagramDescription {
              id
              nodeDescriptions {
                id
                userResizable
                keepAspectRatio
                childNodeDescriptionIds
                borderNodeDescriptionIds
              }
              childNodeDescriptionIds
              dropNodeCompatibility {
                droppedNodeDescriptionId
                droppableOnDiagram
                droppableOnNodeTypes
              }
              debug
              arrangeLayoutDirection
            }
          }
        }
      }
    }
  }
`;

const isDiagramRefreshedEventPayload = (payload: GQLDiagramEventPayload): payload is GQLDiagramRefreshedEventPayload =>
  payload.__typename === 'DiagramRefreshedEventPayload';

export const DiagramRepresentation = ({
  editingContextId,
  representationId,
  readOnly,
}: RepresentationComponentProps) => {
  const [state, setState] = useState<DiagramRepresentationState>({
    id: crypto.randomUUID(),
    diagramRefreshedEventPayload: null,
    complete: false,
    message: null,
  });
  const { addErrorMessage } = useMultiToast();

  const variables: GQLDiagramEventVariables = {
    input: {
      id: state.id,
      editingContextId,
      diagramId: representationId,
    },
  };

  const onData = ({ data }: OnDataOptions<GQLDiagramEventData>) => {
    if (data.data) {
      const { diagramEvent } = data.data;
      if (isDiagramRefreshedEventPayload(diagramEvent)) {
        setState((prevState) => ({ ...prevState, diagramRefreshedEventPayload: diagramEvent }));
      }
    }
  };

  const {
    loading: diagramDescriptionLoading,
    data: diagramDescriptionData,
    error: diagramDescriptionError,
  } = useQuery<GQLDiagramDescriptionData, GQLDiagramDescriptionVariables>(getDiagramDescription, {
    variables: {
      editingContextId,
      representationId,
    },
    skip: state.diagramRefreshedEventPayload === null,
  });

  useEffect(() => {
    if (!diagramDescriptionLoading) {
      setState((prevState) => ({
        ...prevState,
        diagramDescription: diagramDescriptionData?.viewer.editingContext.representation.description,
      }));
    }
    if (diagramDescriptionError) {
      const { message } = diagramDescriptionError;
      addErrorMessage(message);
    }
  }, [diagramDescriptionLoading, diagramDescriptionData, diagramDescriptionError]);

  const onComplete = () => {
    setState((prevState) => ({ ...prevState, diagramRefreshedEventPayload: null, complete: true }));
  };

  const { error } = useSubscription<GQLDiagramEventData>(subscription, {
    variables,
    fetchPolicy: 'no-cache',
    onData,
    onComplete,
  });

  const diagramDescription: GQLDiagramDescription | undefined =
    diagramDescriptionData?.viewer.editingContext.representation.description;

  if (state.message) {
    return <div>{state.message}</div>;
  }
  if (error) {
    return <div>{error.message}</div>;
  }
  if (state.complete) {
    return <div>The representation is not available anymore</div>;
  }
  if (!state.diagramRefreshedEventPayload || !diagramDescription) {
    return <div></div>;
  }

  return (
    <ReactFlowProvider>
      <DiagramContext.Provider
        value={{
          editingContextId,
          diagramId: representationId,
          refreshEventPayloadId: state.diagramRefreshedEventPayload.id,
          readOnly,
        }}>
        <DiagramDescriptionContext.Provider value={{ diagramDescription }}>
          <StoreContextProvider>
            <DiagramDirectEditContextProvider>
              <DiagramPaletteContextProvider>
                <DiagramElementPaletteContextProvider>
                  <ConnectorContextProvider>
                    <DropNodeContextProvider>
                      <NodeContextProvider>
                        <div
                          style={{ display: 'inline-block', position: 'relative' }}
                          data-representation-kind="diagram"
                          data-representation-label={state.diagramRefreshedEventPayload.diagram.metadata.label}>
                          <MarkerDefinitions />
                          <FullscreenContextProvider>
                            <DiagramRenderer
                              key={state.diagramRefreshedEventPayload.diagram.id}
                              diagramRefreshedEventPayload={state.diagramRefreshedEventPayload}
                            />
                          </FullscreenContextProvider>
                        </div>
                      </NodeContextProvider>
                    </DropNodeContextProvider>
                  </ConnectorContextProvider>
                </DiagramElementPaletteContextProvider>
              </DiagramPaletteContextProvider>
            </DiagramDirectEditContextProvider>
          </StoreContextProvider>
        </DiagramDescriptionContext.Provider>
      </DiagramContext.Provider>
    </ReactFlowProvider>
  );
};
