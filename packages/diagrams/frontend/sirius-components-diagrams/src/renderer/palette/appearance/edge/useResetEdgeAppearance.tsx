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
  GQLResetEdgeAppearanceData,
  GQLResetEdgeAppearanceVariables,
  UseResetEdgeAppearanceValue,
} from './useResetEdgeAppearance.types';

export const GQLResetEdgeAppearanceMutation = gql`
  mutation resetEdgeAppearance($input: ResetEdgeAppearanceInput!) {
    resetEdgeAppearance(input: $input) {
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

export const useResetEdgeAppearance = (): UseResetEdgeAppearanceValue => {
  const [resetEdgeAppearance, resetEdgeAppearanceResult] = useMutation<
    GQLResetEdgeAppearanceData,
    GQLResetEdgeAppearanceVariables
  >(GQLResetEdgeAppearanceMutation);

  useReporting(resetEdgeAppearanceResult, (data: GQLResetEdgeAppearanceData) => data.resetEdgeAppearance);

  const resetEdgeStyleProperties = (
    editingContextId: string,
    representationId: string,
    edgeId: string,
    propertiesToReset: string[]
  ) =>
    resetEdgeAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          edgeId,
          propertiesToReset,
        },
      },
    });

  return {
    resetEdgeStyleProperties,
  };
};
