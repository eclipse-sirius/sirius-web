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
  UseFadeDiagramElements,
} from './useFadeDiagramElements.types';

const fadeDiagramElementMutation = gql`
  mutation fadeDiagramElement($input: FadeDiagramElementInput!) {
    fadeDiagramElement(input: $input) {
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

const isErrorPayload = (payload: GQLFadeDiagramElementPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useFadeDiagramElements = (): UseFadeDiagramElements => {
  const { addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [fadeElementMutation, { data: fadeDiagramElementData, error: fadeDiagramElementError }] = useMutation<
    GQLFadeDiagramElementData,
    GQLFadeDiagramElementVariables
  >(fadeDiagramElementMutation);

  useEffect(() => {
    if (fadeDiagramElementError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (fadeDiagramElementData) {
      const { fadeDiagramElement } = fadeDiagramElementData;
      if (isErrorPayload(fadeDiagramElement)) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
    }
  }, [fadeDiagramElementData, fadeDiagramElementError]);

  const fadeDiagramElements = useCallback(
    (nodeId: string[], fade: boolean) => {
      const input: GQLFadeDiagramElementInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        elementIds: nodeId,
        fade,
      };
      fadeElementMutation({ variables: { input } });
    },
    [editingContextId, diagramId, fadeElementMutation]
  );

  return { fadeDiagramElements };
};
