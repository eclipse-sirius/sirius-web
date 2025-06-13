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
  GQLEditRectangularNodeApparenceData,
  GQLEditRectangularNodeApparenceVariables,
  UseUpdateRectangularNodeAppearanceValue,
} from './useUpdateRectangularNodeAppearance.types';

export const editRectangularNodeAppearanceMutation = gql`
  mutation editRectangularNodeAppearance($input: EditRectangularNodeAppearanceInput!) {
    editRectangularNodeAppearance(input: $input) {
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

export const useUpdateRectangularNodeAppearance = (): UseUpdateRectangularNodeAppearanceValue => {
  const [editRectangularNodeApparence, editRectangularNodeApparenceResult] = useMutation<
    GQLEditRectangularNodeApparenceData,
    GQLEditRectangularNodeApparenceVariables
  >(editRectangularNodeAppearanceMutation);

  useReporting(
    editRectangularNodeApparenceResult,
    (data: GQLEditRectangularNodeApparenceData) => data.editRectangularNodeAppearance
  );

  const updateBackground = (editingContextId: string, representationId: string, nodeId: string, background: string) =>
    editRectangularNodeApparence({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId,
          nodeId,
          appearance: {
            background,
          },
        },
      },
    });

  return {
    updateBackground,
  };
};
