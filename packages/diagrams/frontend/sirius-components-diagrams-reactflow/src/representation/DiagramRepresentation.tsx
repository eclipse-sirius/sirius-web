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
import { RepresentationComponentProps, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect, useState } from 'react';
import { ReactFlowProvider } from 'reactflow';
import { DiagramContext } from '../contexts/DiagramContext';
import { diagramEventSubscription } from '../graphql/subscription/diagramEventSubscription';
import {
  GQLDiagramEventPayload,
  GQLDiagramRefreshedEventPayload,
} from '../graphql/subscription/diagramEventSubscription.types';
import { DiagramRenderer } from '../renderer/DiagramRenderer';
import { ConnectorContextProvider } from '../renderer/connector/ConnectorContext';
import { DiagramDirectEditContextProvider } from '../renderer/direct-edit/DiagramDirectEditContext';
import { MarkerDefinitions } from '../renderer/edge/MarkerDefinitions';
import { FullscreenContextProvider } from '../renderer/fullscreen/FullscreenContext';
import { DiagramElementPaletteContextProvider } from '../renderer/palette/DiagramElementPaletteContext';
import { DiagramPaletteContextProvider } from '../renderer/palette/DiagramPaletteContext';
import {
  DiagramRepresentationState,
  GQLDiagramEventData,
  GQLDiagramEventVariables,
} from './DiagramRepresentation.types';
import { getDiagramDescriptionQuery } from './GetDiagramDescriptionQuery';
import {
  GQLGetDiagramDescriptionData,
  GQLGetDiagramDescriptionDiagramDescription,
  GQLGetDiagramDescriptionRepresentationDescription,
} from './GetDiagramDescriptionQuery.types';

const subscription = gql(diagramEventSubscription);

const isDiagramRefreshedEventPayload = (payload: GQLDiagramEventPayload): payload is GQLDiagramRefreshedEventPayload =>
  payload.__typename === 'DiagramRefreshedEventPayload';

const isDiagramDescription = (
  representationDescription: GQLGetDiagramDescriptionRepresentationDescription
): representationDescription is GQLGetDiagramDescriptionDiagramDescription =>
  representationDescription.__typename === 'DiagramDescription';

export const DiagramRepresentation = ({
  editingContextId,
  representationId,
  selection,
  setSelection,
}: RepresentationComponentProps) => {
  const [state, setState] = useState<DiagramRepresentationState>({
    id: crypto.randomUUID(),
    diagramRefreshedEventPayload: null,
    complete: false,
    message: null,
    autoLayout: false,
  });

  const variables: GQLDiagramEventVariables = {
    input: {
      id: state.id,
      editingContextId,
      diagramId: representationId,
    },
  };
  const { addErrorMessage } = useMultiToast();

  const {
    loading: diagramDescriptionLoading,
    data: diagramDescriptionData,
    error: diagramDescriptionError,
    subscribeToMore,
  } = useQuery<GQLGetDiagramDescriptionData>(getDiagramDescriptionQuery, {
    fetchPolicy: 'network-only',
    variables: {
      editingContextId,
      diagramId: representationId,
    },
  });

  useEffect(() => {
    subscribeToMore<GQLDiagramEventData, GQLDiagramEventVariables>({
      document: subscription,
      variables: variables,
      onError(error) {
        setState((prevState) => ({ ...prevState, message: error.message }));
      },
      updateQuery: (prev, { subscriptionData }) => {
        if (subscriptionData.data) {
          const { diagramEvent } = subscriptionData.data;
          if (isDiagramRefreshedEventPayload(diagramEvent)) {
            setState((prevState) => ({ ...prevState, diagramRefreshedEventPayload: diagramEvent }));
          }
        }
        return prev;
      },
    });
  }, [subscribeToMore]);

  useEffect(() => {
    if (diagramDescriptionError) {
      const { message } = diagramDescriptionError;
      addErrorMessage(message);
    }
    if (!diagramDescriptionLoading && diagramDescriptionData) {
      const representationDescription = diagramDescriptionData.viewer?.editingContext?.representation?.description;
      if (representationDescription && isDiagramDescription(representationDescription)) {
        setState((prevState) => ({ ...prevState, autoLayout: representationDescription.autoLayout }));
      }
    }
  }, [diagramDescriptionLoading, diagramDescriptionData, diagramDescriptionError]);

  if (state.message) {
    return <div>{state.message}</div>;
  }
  if (diagramDescriptionError) {
    return <div>{diagramDescriptionError.message}</div>;
  }
  if (state.complete) {
    return <div>The representation is not available anymore</div>;
  }
  if (!state.diagramRefreshedEventPayload) {
    return <div></div>;
  }

  return (
    <ReactFlowProvider>
      <DiagramContext.Provider value={{ editingContextId, diagramId: representationId }}>
        <DiagramDirectEditContextProvider>
          <DiagramPaletteContextProvider>
            <DiagramElementPaletteContextProvider>
              <ConnectorContextProvider>
                <div style={{ display: 'inline-block', position: 'relative' }}>
                  <MarkerDefinitions />
                  <FullscreenContextProvider>
                    <DiagramRenderer
                      key={state.diagramRefreshedEventPayload.diagram.id}
                      diagramRefreshedEventPayload={state.diagramRefreshedEventPayload}
                      selection={selection}
                      setSelection={setSelection}
                      isAutoLayout={state.autoLayout}
                    />
                  </FullscreenContextProvider>
                </div>
              </ConnectorContextProvider>
            </DiagramElementPaletteContextProvider>
          </DiagramPaletteContextProvider>
        </DiagramDirectEditContextProvider>
      </DiagramContext.Provider>
    </ReactFlowProvider>
  );
};
