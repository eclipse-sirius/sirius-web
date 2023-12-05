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
  GQLFadeDiagramElementData,
  GQLFadeDiagramElementInput,
  GQLFadeDiagramElementPayload,
  GQLFadeDiagramElementVariables,
  GQLSuccessPayload,
  UseFadeDiagramElements,
} from './useFadeDiagramElements.types';

const fadeDiagramElementMutation = gql`
  mutation fadeDiagramElement($input: FadeDiagramElementInput!) {
    fadeDiagramElement(input: $input) {
      __typename
      ... on SuccessPayload {
        id
        messages {
          body
          level
        }
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

const isErrorPayload = (payload: GQLFadeDiagramElementPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLFadeDiagramElementPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useFadeDiagramElements = (): UseFadeDiagramElements => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [fadeElementMutation, { error: fadeDiagramElementError }] = useMutation<
    GQLFadeDiagramElementData,
    GQLFadeDiagramElementVariables
  >(fadeDiagramElementMutation);

  useEffect(() => {
    if (fadeDiagramElementError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [fadeDiagramElementError]);

  const fadeDiagramElements = useCallback(
    async (nodeId: string[], fade: boolean) => {
      const input: GQLFadeDiagramElementInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        elementIds: nodeId,
        fade,
      };
      const { data: fadeDiagramElementData } = await fadeElementMutation({ variables: { input } });
      if (fadeDiagramElementData) {
        const { fadeDiagramElement } = fadeDiagramElementData;
        if (isSuccessPayload(fadeDiagramElement) || isErrorPayload(fadeDiagramElement)) {
          addMessages(fadeDiagramElement.messages);
        }
      }
    },
    [editingContextId, diagramId, fadeElementMutation]
  );

  return { fadeDiagramElements };
};
