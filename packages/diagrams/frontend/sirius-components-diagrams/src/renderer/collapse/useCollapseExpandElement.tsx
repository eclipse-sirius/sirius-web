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
  GQLCollapsingState,
  GQLErrorPayload,
  GQLUpdateCollapsingStateData,
  GQLUpdateCollapsingStateInput,
  GQLUpdateCollapsingStatePayload,
  GQLUpdateCollapsingStateVariables,
  UseCollapseExpandElement,
} from './useCollapseExpandElement.types';

const updateCollapsingStateMutation = gql`
  mutation updateCollapsingState($input: UpdateCollapsingStateInput!) {
    updateCollapsingState(input: $input) {
      __typename
      ... on SuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLUpdateCollapsingStatePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useCollapseExpandElement = (): UseCollapseExpandElement => {
  const { addMessages, addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [collapseExpandMutation, { error: collpaseNodeError }] = useMutation<
    GQLUpdateCollapsingStateData,
    GQLUpdateCollapsingStateVariables
  >(updateCollapsingStateMutation);

  const collapseExpandElement = useCallback(
    async (nodeId: string, collapsingState: GQLCollapsingState) => {
      const input: GQLUpdateCollapsingStateInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        diagramElementId: nodeId,
        collapsingState,
      };
      const { data: collapseNodeData } = await collapseExpandMutation({ variables: { input } });
      if (collapseNodeData) {
        const { collapseExpandDiagramElement } = collapseNodeData;
        if (isErrorPayload(collapseExpandDiagramElement)) {
          addMessages(collapseExpandDiagramElement.messages);
        }
      }
    },
    [editingContextId, diagramId, collapseExpandMutation]
  );

  useEffect(() => {
    if (collpaseNodeError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [collpaseNodeError]);

  return { collapseExpandElement };
};
