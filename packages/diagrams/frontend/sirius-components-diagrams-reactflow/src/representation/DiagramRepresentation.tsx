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

import { OnDataOptions, gql, useSubscription } from '@apollo/client';
import { RepresentationComponentProps } from '@eclipse-sirius/sirius-components-core';
import { useState } from 'react';
import { ReactFlowProvider } from 'reactflow';
import { DiagramContext } from '../contexts/DiagramContext';
import { convertDiagram } from '../converter/convertDiagram';
import { diagramEventSubscription } from '../graphql/subscription/diagramEventSubscription';
import {
  GQLDiagramEventPayload,
  GQLDiagramRefreshedEventPayload,
} from '../graphql/subscription/diagramEventSubscription.types';
import { DiagramRenderer } from '../renderer/DiagramRenderer';
import { Diagram } from '../renderer/DiagramRenderer.types';
import { ConnectorContextProvider } from '../renderer/connector/ConnectorContext';
import { DiagramDirectEditContextProvider } from '../renderer/direct-edit/DiagramDirectEditContext';
import { MarkerDefinitions } from '../renderer/edge/MarkerDefinitions';
import { FullscreenContextProvider } from '../renderer/fullscreen/FullscreenContext';
import { useLayout } from '../renderer/layout/useLayout';
import { DiagramPaletteContextProvider } from '../renderer/palette/DiagramPaletteContext';
import { EdgePaletteContextProvider } from '../renderer/palette/EdgePaletteContext';
import {
  DiagramRepresentationState,
  GQLDiagramEventData,
  GQLDiagramEventVariables,
} from './DiagramRepresentation.types';

const subscription = gql(diagramEventSubscription);

const isDiagramRefreshedEventPayload = (payload: GQLDiagramEventPayload): payload is GQLDiagramRefreshedEventPayload =>
  payload.__typename === 'DiagramRefreshedEventPayload';

export const DiagramRepresentation = ({
  editingContextId,
  representationId,
  selection,
  setSelection,
}: RepresentationComponentProps) => {
  const [state, setState] = useState<DiagramRepresentationState>({
    id: crypto.randomUUID(),
    diagram: null,
    complete: false,
    message: null,
  });
  const { layout } = useLayout();

  const variables: GQLDiagramEventVariables = {
    input: {
      id: state.id,
      editingContextId,
      diagramId: representationId,
    },
  };

  const onDiagramLaidout = (laidoutDiagram: Diagram) => {
    setState((prevState) => ({ ...prevState, diagram: laidoutDiagram }));
  };

  const onData = ({ data }: OnDataOptions<GQLDiagramEventData>) => {
    if (data.data) {
      const { diagramEvent } = data.data;
      if (isDiagramRefreshedEventPayload(diagramEvent)) {
        const { diagram } = diagramEvent;
        const convertedDiagram: Diagram = convertDiagram(diagram);
        const previousLayoutedDiagram: Diagram | null = state.diagram;
        layout(previousLayoutedDiagram, convertedDiagram, onDiagramLaidout);
      }
    }
  };

  const onComplete = () => {
    setState((prevState) => ({ ...prevState, diagram: null, complete: true }));
  };

  const { error } = useSubscription<GQLDiagramEventData>(subscription, {
    variables,
    fetchPolicy: 'no-cache',
    onData,
    onComplete,
  });

  if (state.message) {
    return <div>{state.message}</div>;
  }
  if (error) {
    return <div>{error.message}</div>;
  }
  if (state.complete) {
    return <div>The representation is not available anymore</div>;
  }
  if (!state.diagram) {
    return <div></div>;
  }

  return (
    <ReactFlowProvider>
      <DiagramContext.Provider value={{ editingContextId, diagramId: representationId }}>
        <DiagramDirectEditContextProvider>
          <DiagramPaletteContextProvider>
            <EdgePaletteContextProvider>
              <ConnectorContextProvider>
                <div style={{ display: 'inline-block', position: 'relative' }}>
                  <MarkerDefinitions />
                  <FullscreenContextProvider>
                    <DiagramRenderer diagram={state.diagram} selection={selection} setSelection={setSelection} />
                  </FullscreenContextProvider>
                </div>
              </ConnectorContextProvider>
            </EdgePaletteContextProvider>
          </DiagramPaletteContextProvider>
        </DiagramDirectEditContextProvider>
      </DiagramContext.Provider>
    </ReactFlowProvider>
  );
};
