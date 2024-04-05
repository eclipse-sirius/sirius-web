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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLCreateDocumentMutationData,
  GQLCreateDocumentMutationVariables,
  GQLCreateDocumentPayload,
  GQLCreateDocumentSuccessPayload,
  GQLErrorPayload,
  UseCreateDocumentValue,
} from './useCreateDocument.types';

const createDocumentMutation = gql`
  mutation createDocument($input: CreateDocumentInput!) {
    createDocument(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLCreateDocumentPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isCreateDocumentSuccessPayload = (
  payload: GQLCreateDocumentPayload
): payload is GQLCreateDocumentSuccessPayload => payload.__typename === 'CreateDocumentSuccessPayload';

export const useCreateDocument = (): UseCreateDocumentValue => {
  const [performDocumentCreation, { data, loading, error }] = useMutation<
    GQLCreateDocumentMutationData,
    GQLCreateDocumentMutationVariables
  >(createDocumentMutation);

  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (data) {
      const { createDocument } = data;
      if (isErrorPayload(createDocument)) {
        addMessages(createDocument.messages);
      }
    }
  }, [data, error]);

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
