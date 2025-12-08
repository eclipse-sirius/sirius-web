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
  GQLEdgeAppearanceInput,
  GQLEditEdgeAppearanceData,
  GQLEditEdgeAppearanceVariables,
  UseEditEdgeAppearanceValue,
} from './useEditEdgeAppearance.types';

export const editEdgeAppearanceMutation = gql`
  mutation editEdgeAppearance($input: EditEdgeAppearanceInput!) {
    editEdgeAppearance(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const useEditEdgeAppearance = (): UseEditEdgeAppearanceValue => {
  const [editEdgeAppearance, editEdgeAppearanceResult] = useMutation<
    GQLEditEdgeAppearanceData,
    GQLEditEdgeAppearanceVariables
  >(editEdgeAppearanceMutation);

  useReporting(editEdgeAppearanceResult, (data: GQLEditEdgeAppearanceData) => data.editEdgeAppearance);

  const updateEdgeAppearance = (
    editingContextId: string,
    representationId: string,
    edgeIds: string[],
    appearance: Partial<GQLEdgeAppearanceInput>
  ) => {
    editEdgeAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          edgeIds,
          appearance,
        },
      },
    });
  };

  return {
    updateEdgeAppearance,
  };
};
