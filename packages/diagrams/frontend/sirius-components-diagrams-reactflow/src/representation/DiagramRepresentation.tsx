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

import { OnSubscriptionDataOptions, gql, useSubscription } from '@apollo/client';
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

  const variables: GQLDiagramEventVariables = {
    input: {
      id: state.id,
      editingContextId,
      diagramId: representationId,
    },
  };

  const onSubscriptionData = ({ subscriptionData }: OnSubscriptionDataOptions<GQLDiagramEventData>) => {
    if (subscriptionData.data) {
      const { diagramEvent } = subscriptionData.data;
      if (isDiagramRefreshedEventPayload(diagramEvent)) {
        const { diagram } = diagramEvent;
        const convertedDiagram: Diagram = convertDiagram(diagram);
        setState((prevState) => ({ ...prevState, diagram: convertedDiagram }));
      }
    }
  };

  const onSubscriptionComplete = () => {
    setState((prevState) => ({ ...prevState, diagram: null, convertedDiagram: null, complete: true }));
  };

  const { error } = useSubscription<GQLDiagramEventData>(subscription, {
    variables,
    fetchPolicy: 'no-cache',
    onSubscriptionData,
    onSubscriptionComplete,
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
    <DiagramContext.Provider value={{ editingContextId, diagramId: representationId }}>
      <ReactFlowProvider>
        <DiagramRenderer diagram={state.diagram} selection={selection} setSelection={setSelection} />
      </ReactFlowProvider>
    </DiagramContext.Provider>
  );
};
