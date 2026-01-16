/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLErrorPayload,
  GQLPublishLibrariesMutationData,
  GQLPublishLibrariesMutationVariables,
  GQLPublishLibrariesPayload,
  GQLSuccessPayload,
  UsePublishLibrariesValue,
} from './usePublishLibraries.types';

const publishLibrariesMutation = gql`
  mutation publishLibraries($input: PublishLibrariesInput!) {
    publishLibraries(input: $input) {
      __typename
      ... on SuccessPayload {
        messages {
          level
          body
        }
      }
      ... on ErrorPayload {
        messages {
          level
          body
        }
      }
    }
  }
`;

const isSuccessPayload = (payload: GQLPublishLibrariesPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

const isErrorPayload = (payload: GQLPublishLibrariesPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const usePublishLibraries = (): UsePublishLibrariesValue => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [performPublishLibraries, { loading, data, error }] = useMutation<
    GQLPublishLibrariesMutationData,
    GQLPublishLibrariesMutationVariables
  >(publishLibrariesMutation);

  useEffect(() => {
    if (data) {
      const { publishLibraries } = data;
      if (isErrorPayload(publishLibraries)) {
        addMessages(publishLibraries.messages);
      }
      if (isSuccessPayload(publishLibraries)) {
        addMessages(publishLibraries.messages);
      }
    }
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [error, data]);

  const publishLibraries = (
    editingContextId: string,
    publicationKind: string,
    version: string,
    description: string
  ) => {
    const variables: GQLPublishLibrariesMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        publicationKind,
        version,
        description,
      },
    };
    performPublishLibraries({ variables });
  };

  return {
    publishLibraries,
    loading,
    data: data ?? null,
  };
};
