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
  GQLEditEllipseNodeAppearanceData,
  GQLEditEllipseNodeAppearanceVariables,
  GQLEllipseNodeAppearanceInput,
  UseUpdateEllipseNodeAppearanceValue,
} from './useUpdateEllipseNodeAppearance.types';

export const editEllipseNodeAppearanceMutation = gql`
  mutation editEllipseNodeAppearance($input: EditEllipseNodeAppearanceInput!) {
    editEllipseNodeAppearance(input: $input) {
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

export const useUpdateEllipseNodeAppearance = (): UseUpdateEllipseNodeAppearanceValue => {
  const [editEllipseNodeAppearance, editEllipseNodeAppearanceResult] = useMutation<
    GQLEditEllipseNodeAppearanceData,
    GQLEditEllipseNodeAppearanceVariables
  >(editEllipseNodeAppearanceMutation);

  useReporting(
    editEllipseNodeAppearanceResult,
    (data: GQLEditEllipseNodeAppearanceData) => data.editEllipseNodeAppearance
  );

  const updateEllipseNodeAppearance = (
    editingContextId: string,
    representationId: string,
    nodeIds: string[],
    appearance: Partial<GQLEllipseNodeAppearanceInput>
  ) =>
    editEllipseNodeAppearance({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeIds,
          appearance,
        },
      },
    });

  return {
    updateEllipseNodeAppearance,
  };
};
