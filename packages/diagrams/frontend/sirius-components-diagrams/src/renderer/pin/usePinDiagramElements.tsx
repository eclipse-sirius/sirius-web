/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLPinDiagramElementData,
  GQLPinDiagramElementInput,
  GQLPinDiagramElementVariables,
  UsePinDiagramElements,
} from './usePinDiagramElements.types';

const pinDiagramElementMutation = gql`
  mutation pinDiagramElement($input: PinDiagramElementInput!) {
    pinDiagramElement(input: $input) {
      __typename
      ... on SuccessPayload {
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

export const usePinDiagramElements = (): UsePinDiagramElements => {
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);

  const [pinElementMutation, pinElementMutationResult] = useMutation<
    GQLPinDiagramElementData,
    GQLPinDiagramElementVariables
  >(pinDiagramElementMutation);

  useReporting(pinElementMutationResult, (data: GQLPinDiagramElementData) => data.pinDiagramElement);

  const pinDiagramElements = useCallback(
    (elementIds: string[], pinned: boolean) => {
      const input: GQLPinDiagramElementInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        elementIds,
        pinned,
      };
      if (!readOnly) {
        pinElementMutation({ variables: { input } });
      }
    },
    [editingContextId, diagramId, readOnly, pinElementMutation]
  );

  return { pinDiagramElements };
};
