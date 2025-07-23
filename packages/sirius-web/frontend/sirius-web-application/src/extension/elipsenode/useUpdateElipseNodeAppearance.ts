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
  GQLEditElipseNodeApparenceData,
  GQLEditElipseNodeApparenceVariables,
  GQLElipseNodeAppearanceInput,
  UseUpdateElipseNodeAppearanceValue,
} from './useUpdateElipseNodeAppearance.types';

export const editElipseNodeAppearanceMutation = gql`
  mutation editElipseNodeAppearance($input: EditElipseNodeAppearanceInput!) {
    editElipseNodeAppearance(input: $input) {
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

export const useUpdateElipseNodeAppearance = (): UseUpdateElipseNodeAppearanceValue => {
  const [editElipseNodeApparence, editElipseNodeApparenceResult] = useMutation<
    GQLEditElipseNodeApparenceData,
    GQLEditElipseNodeApparenceVariables
  >(editElipseNodeAppearanceMutation);

  useReporting(editElipseNodeApparenceResult, (data: GQLEditElipseNodeApparenceData) => data.editElipseNodeAppearance);

  const updateElipseNodeAppearance = (
    editingContextId: string,
    representationId: string,
    nodeId: string,
    appearance: Partial<GQLElipseNodeAppearanceInput>
  ) =>
    editElipseNodeApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeId,
          appearance,
        },
      },
    });

  return {
    updateElipseNodeAppearance,
  };
};
