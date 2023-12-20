/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLErrorPayload,
  GQLPinDiagramElementData,
  GQLPinDiagramElementInput,
  GQLPinDiagramElementPayload,
  GQLPinDiagramElementVariables,
  UsePinDiagramElements,
} from './usePinDiagramElements.types';

const pinDiagramElementMutation = gql`
  mutation pinDiagramElement($input: PinDiagramElementInput!) {
    pinDiagramElement(input: $input) {
      __typename
      ... on SuccessPayload {
        id
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

const isErrorPayload = (payload: GQLPinDiagramElementPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const usePinDiagramElements = (): UsePinDiagramElements => {
  const { addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [pinElementMutation, { data: pinDiagramElementData, error: pinDiagramElementError }] = useMutation<
    GQLPinDiagramElementData,
    GQLPinDiagramElementVariables
  >(pinDiagramElementMutation);

  useEffect(() => {
    if (pinDiagramElementError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (pinDiagramElementData) {
      const { pinDiagramElement } = pinDiagramElementData;
      if (isErrorPayload(pinDiagramElement)) {
        addErrorMessage(pinDiagramElement.message);
      }
    }
  }, [pinDiagramElementData, pinDiagramElementError]);

  const pinDiagramElements = useCallback(
    (nodeId: string[], pinned: boolean) => {
      const input: GQLPinDiagramElementInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        elementIds: nodeId,
        pinned,
      };
      pinElementMutation({ variables: { input } });
    },
    [editingContextId, diagramId, pinElementMutation]
  );

  return { pinDiagramElements };
};
