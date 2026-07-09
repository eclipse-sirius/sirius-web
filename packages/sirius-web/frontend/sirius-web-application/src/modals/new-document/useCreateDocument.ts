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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLCreateDocumentMutationData,
  GQLCreateDocumentMutationVariables,
  GQLErrorPayload,
  GQLCreateDocumentPayload,
  GQLCreateDocumentSuccessPayload,
  UseCreateDocumentValue,
} from './useCreateDocument.types';

const createDocumentMutation = gql`
  mutation createDocument($input: CreateDocumentInput!) {
    createDocument(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;
const isCreateDocumentSuccessPayload = (
  payload: GQLCreateDocumentPayload
): payload is GQLCreateDocumentSuccessPayload => payload.__typename === 'CreateDocumentSuccessPayload';

const isErrorPayload = (payload: GQLCreateDocumentPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useCreateDocument = (): UseCreateDocumentValue => {
  const [performDocumentCreation, mutationResult] = useMutation<
    GQLCreateDocumentMutationData,
    GQLCreateDocumentMutationVariables
  >(createDocumentMutation);
  const { data, loading } = mutationResult;
  const { addErrorMessage, addMessages } = useMultiToast();

  useEffect(() => {
    if (mutationResult.error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data && isErrorPayload(data.createDocument)) {
      addMessages(data.createDocument.messages);
    }
  }, [data, mutationResult.error, addErrorMessage, addMessages]);

  const createDocument = (editingContextId: string, stereotypeId: string, name: string) => {
    const variables: GQLCreateDocumentMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        stereotypeId,
        name,
      },
    };

    performDocumentCreation({ variables });
  };

  let documentCreated: GQLCreateDocumentSuccessPayload | null = null;
  if (data && isCreateDocumentSuccessPayload(data.createDocument)) {
    documentCreated = data.createDocument;
  }

  return {
    createDocument,
    loading,
    documentCreated,
  };
};
