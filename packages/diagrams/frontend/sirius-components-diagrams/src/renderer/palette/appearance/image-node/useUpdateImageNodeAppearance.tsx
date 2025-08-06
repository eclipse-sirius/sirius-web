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
  GQLEditImageNodeAppearanceData,
  GQLEditImageNodeAppearanceVariables,
  GQLImageNodeAppearanceInput,
  UseUpdateImageNodeAppearanceValue,
} from './useUpdateImageNodeAppearance.types';

export const editImageNodeAppearanceMutation = gql`
  mutation editImageNodeAppearance($input: EditImageNodeAppearanceInput!) {
    editImageNodeAppearance(input: $input) {
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

export const useUpdateImageNodeAppearance = (): UseUpdateImageNodeAppearanceValue => {
  const [editImageNodeAppearance, editImageNodeAppearanceResult] = useMutation<
    GQLEditImageNodeAppearanceData,
    GQLEditImageNodeAppearanceVariables
  >(editImageNodeAppearanceMutation);

  useReporting(editImageNodeAppearanceResult, (data: GQLEditImageNodeAppearanceData) => data.editImageNodeAppearance);

  const updateImageNodeAppearance = (
    editingContextId: string,
    representationId: string,
    nodeId: string,
    appearance: Partial<GQLImageNodeAppearanceInput>
  ) =>
    editImageNodeAppearance({
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
    updateImageNodeAppearance,
  };
};
