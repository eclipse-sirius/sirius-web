/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
  GQLDuplicateObjectData,
  GQLDuplicateObjectInput,
  GQLDuplicateObjectVariables,
  GQLObject,
  UseDuplicateObjectValue,
} from './useDuplicateObject.types';

const duplicateObjectMutation = gql`
  mutation duplicateObject($input: DuplicateObjectInput!) {
    duplicateObject(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on DuplicateObjectSuccessPayload {
        object {
          id
          label
          kind
        }
        messages {
          body
          level
        }
      }
    }
  }
`;

export const useDuplicateObject = (): UseDuplicateObjectValue => {
  const [mutationDuplicateObject, duplicateObjectResult] = useMutation<
    GQLDuplicateObjectData,
    GQLDuplicateObjectVariables
  >(duplicateObjectMutation);
  useReporting(duplicateObjectResult, (data: GQLDuplicateObjectData) => {
    const payload = data.duplicateObject;
    if (payload.__typename === 'DuplicateObjectSuccessPayload') {
      return { __typename: 'SuccessPayload', id: payload.id, messages: payload.messages };
    } else {
      return { __typename: 'ErrorPayload', id: payload.id, messages: payload.messages };
    }
  });

  const duplicateObject = (
    editingContextId: string,
    objectId: string,
    containerId: string,
    containmentFeatureName: string,
    duplicateContent: boolean,
    copyOutgoingReferences: boolean,
    updateIncomingReferences: boolean
  ) => {
    const input: GQLDuplicateObjectInput = {
      id: crypto.randomUUID(),
      editingContextId,
      objectId,
      containerId,
      containmentFeatureName,
      duplicateContent,
      copyOutgoingReferences,
      updateIncomingReferences,
    };
    mutationDuplicateObject({ variables: { input } });
  };

  let duplicatedObject: GQLObject = null;
  if (duplicateObjectResult?.data?.duplicateObject?.__typename === 'DuplicateObjectSuccessPayload') {
    duplicatedObject = duplicateObjectResult.data.duplicateObject.object;
  }

  return { duplicateObject, duplicatedObject };
};
