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
  GQLResetLabelApparenceData,
  GQLResetLabelApparenceVariables,
  UseResetLabelAppearanceValue,
} from './useResetLabelStyleProperties.types';

export const GQLResetLabelAppearanceMutation = gql`
  mutation resetLabelAppearance($input: ResetLabelAppearanceInput!) {
    resetLabelAppearance(input: $input) {
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

export const useResetLabelAppearance = (
  editingContextId: string,
  representationId: string,
  nodeId: string
): UseResetLabelAppearanceValue => {
  const [resetLabelApparence, resetLabelApparenceResult] = useMutation<
    GQLResetLabelApparenceData,
    GQLResetLabelApparenceVariables
  >(GQLResetLabelAppearanceMutation);

  useReporting(resetLabelApparenceResult, (data: GQLResetLabelApparenceData) => data.resetLabelAppearance);

  const resetLabelStyleProperties = (labelId: string, propertiesToReset: string[]) =>
    resetLabelApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementId: nodeId,
          labelId,
          propertiesToReset,
        },
      },
    });

  const resetInsideLabelBold = (labelId: string) => resetLabelStyleProperties(labelId, ['BOLD']);

  return {
    resetInsideLabelBold,
  };
};
