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
  GQLEditLabelAppearanceData,
  GQLEditLabelAppearanceVariables,
  GQLLabelAppearanceInput,
  UseEditLabelAppearanceValue,
} from './useEditLabelAppearance.types';

export const editLabelAppearanceMutation = gql`
  mutation editLabelAppearance($input: EditLabelAppearanceInput!) {
    editLabelAppearance(input: $input) {
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

export const useEditLabelAppearance = (): UseEditLabelAppearanceValue => {
  const [editLabelAppearance, editLabelAppearanceResult] = useMutation<
    GQLEditLabelAppearanceData,
    GQLEditLabelAppearanceVariables
  >(editLabelAppearanceMutation);

  useReporting(editLabelAppearanceResult, (data: GQLEditLabelAppearanceData) => data.editLabelAppearance);

  const updateLabelAppearance = (
    editingContextId: string,
    representationId: string,
    diagramElementIds: string[],
    labelIds: string[],
    appearance: Partial<GQLLabelAppearanceInput>
  ) => {
    editLabelAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          diagramElementIds,
          labelIds,
          appearance,
        },
      },
    });
  };

  return {
    updateLabelAppearance,
  };
};
