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
  GQLResetNodeAppearanceData,
  GQLResetNodeAppearanceVariables,
  UseResetNodeAppearanceValue,
} from './useResetNodeAppearance.types';

export const GQLResetNodeAppearanceMutation = gql`
  mutation resetNodeAppearance($input: ResetNodeAppearanceInput!) {
    resetNodeAppearance(input: $input) {
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

export const useResetNodeAppearance = (): UseResetNodeAppearanceValue => {
  const [resetNodeAppearance, resetNodeAppearanceResult] = useMutation<
    GQLResetNodeAppearanceData,
    GQLResetNodeAppearanceVariables
  >(GQLResetNodeAppearanceMutation);

  useReporting(resetNodeAppearanceResult, (data: GQLResetNodeAppearanceData) => data.resetNodeAppearance);

  const resetNodeStyleProperties = (
    editingContextId: string,
    representationId: string,
    nodeIds: string[],
    propertiesToReset: string[]
  ) =>
    resetNodeAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeIds,
          propertiesToReset,
        },
      },
    });

  return {
    resetNodeStyleProperties,
  };
};
