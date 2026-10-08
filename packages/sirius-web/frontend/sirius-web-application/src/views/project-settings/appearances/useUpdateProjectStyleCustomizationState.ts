/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { GQLSuccessPayload, useReporting } from '@eclipse-sirius/sirius-components-core';
import {
  GQLUpdateProjectStyleCustomizationStateData,
  GQLUpdateProjectStyleCustomizationStatePayload,
  GQLUpdateProjectStyleCustomizationStateVariables,
  UseUpdateProjectStyleCustomizationStateValue,
} from './useUpdateProjectStyleCustomizationState.types';

const updateProjectStyleCustomizationStateMutation = gql`
  mutation updateProjectStyleCustomizationState($input: UpdateProjectStyleCustomizationStateInput!) {
    updateProjectStyleCustomizationState(input: $input) {
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

const isSuccessPayload = (
  payload: GQLUpdateProjectStyleCustomizationStatePayload | undefined
): payload is GQLSuccessPayload => payload?.__typename === 'SuccessPayload';

export const useUpdateProjectStyleCustomizationState = (): UseUpdateProjectStyleCustomizationStateValue => {
  const [doUpdateProjectStyleCustomizationState, updateProjectStyleCustomizationStateResult] = useMutation<
    GQLUpdateProjectStyleCustomizationStateData,
    GQLUpdateProjectStyleCustomizationStateVariables
  >(updateProjectStyleCustomizationStateMutation);

  useReporting(
    updateProjectStyleCustomizationStateResult,
    (data: GQLUpdateProjectStyleCustomizationStateData) => data.updateProjectStyleCustomizationState
  );

  const updateProjectStyleCustomizationState = (
    projectId: string,
    styleCustomizationDescriptionId: string,
    enable: boolean
  ) => {
    doUpdateProjectStyleCustomizationState({
      variables: {
        input: {
          id: crypto.randomUUID(),
          projectId,
          styleCustomizationDescriptionId,
          enable,
        },
      },
    });
  };

  return {
    updateProjectStyleCustomizationState,
    updateSuccess: isSuccessPayload(
      updateProjectStyleCustomizationStateResult.data?.updateProjectStyleCustomizationState
    ),
  };
};
