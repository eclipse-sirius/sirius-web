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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLErrorPayload,
  GQLHideDiagramElementData,
  GQLHideDiagramElementInput,
  GQLHideDiagramElementPayload,
  GQLHideDiagramElementVariables,
  UseHideDiagramElements,
} from './useHideDiagramElements.types';

const hideDiagramElementMutation = gql`
  mutation hideDiagramElement($input: HideDiagramElementInput!) {
    hideDiagramElement(input: $input) {
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

const isErrorPayload = (payload: GQLHideDiagramElementPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useHideDiagramElements = (): UseHideDiagramElements => {
  const { addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [hideElementMutation, { data: hideDiagramElementData, error: hideDiagramElementError }] = useMutation<
    GQLHideDiagramElementData,
    GQLHideDiagramElementVariables
  >(hideDiagramElementMutation);

  useEffect(() => {
    if (hideDiagramElementError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (hideDiagramElementData) {
      const { hideDiagramElement } = hideDiagramElementData;
      if (isErrorPayload(hideDiagramElement)) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
    }
  }, [hideDiagramElementData, hideDiagramElementError]);

  const hideDiagramElements = useCallback(
    (nodeId: string[], hide: boolean) => {
      const input: GQLHideDiagramElementInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        elementIds: nodeId,
        hide,
      };
      hideElementMutation({ variables: { input } });
    },
    [editingContextId, diagramId, hideElementMutation]
  );

  return { hideDiagramElements };
};
