/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import {
  GQLCollapsingState,
  GQLUpdateCollapsingStateData,
  GQLUpdateCollapsingStateInput,
  GQLUpdateCollapsingStateVariables,
  UseCollapseExpandValue,
} from './useCollapseExpand.types';

const updateCollapsingStateMutation = gql`
  mutation updateCollapsingState($input: UpdateCollapsingStateInput!) {
    updateCollapsingState(input: $input) {
      __typename
      ... on SuccessPayload {
        id
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        message
        messages {
          body
          level
        }
      }
    }
  }
`;

export const useCollapseExpand = (): UseCollapseExpandValue => {
  const [rawCollapseExpand, rawCollapseExpandResult] = useMutation<
    GQLUpdateCollapsingStateData,
    GQLUpdateCollapsingStateVariables
  >(updateCollapsingStateMutation);

  const collapseExpandElement = (
    editingContextId: string,
    diagramId: string,
    nodeId: string,
    collapsingState: GQLCollapsingState
  ) => {
    const input: GQLUpdateCollapsingStateInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      diagramElementId: nodeId,
      collapsingState,
    };
    rawCollapseExpand({ variables: { input } });
  };

  useReporting(rawCollapseExpandResult, (data: GQLUpdateCollapsingStateData) => data.collapseExpandDiagramElement);

  return {
    collapseExpandElement,
    loading: rawCollapseExpandResult.loading,
    data: rawCollapseExpandResult.data || null,
  };
};
