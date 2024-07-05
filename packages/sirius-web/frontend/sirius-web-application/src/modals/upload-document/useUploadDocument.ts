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

import { gql } from '@apollo/client';
import { ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useState } from 'react';
import { sendFile } from '../../core/sendFile';
import {
  GQLErrorPayload,
  GQLUploadDocumentMutationVariables,
  GQLUploadDocumentPayload,
  GQLUploadDocumentSuccessPayload,
  UseUploadDocumentState,
  UseUploadDocumentValue,
} from './useUploadDocument.types';

const uploadDocumentMutationFile = gql`
  mutation uploadDocument($input: UploadDocumentInput!) {
    uploadDocument(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
      ... on UploadDocumentSuccessPayload {
        report
      }
    }
  }
`;

const isUploadDocumentSuccessPayload = (
  payload: GQLUploadDocumentPayload
): payload is GQLUploadDocumentSuccessPayload => payload.__typename === 'UploadDocumentSuccessPayload';
const isErrorPayload = (payload: GQLUploadDocumentPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useUploadDocument = (): UseUploadDocumentValue => {
  const [state, setState] = useState<UseUploadDocumentState>({
    loading: false,
    uploadedDocument: null,
  });
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { addErrorMessage } = useMultiToast();

  const uploadDocument = (editingContextId: string, file: File) => {
    setState((prevState) => ({ ...prevState, loading: true }));

    const variables: GQLUploadDocumentMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        editingContextId,
        file: null, // the file will be send as a part of the multipart POST query.
      },
    };

    try {
      sendFile(httpOrigin, uploadDocumentMutationFile.loc?.source.body ?? '', variables, file).then((result) => {
        const { data, error } = result;
        if (error) {
          addErrorMessage('An unexpected error has occurred, the file uploaded may be too large');
        }
        if (data) {
          const { uploadDocument } = data;
          if (isErrorPayload(uploadDocument)) {
            const { message } = uploadDocument;
            addErrorMessage(message);
            setState((prevState) => ({ ...prevState, loading: false, uploadedDocument: null }));
          } else if (isUploadDocumentSuccessPayload(uploadDocument)) {
            setState((prevState) => ({ ...prevState, loading: false, uploadedDocument: uploadDocument }));
          }
        }
      });
    } catch (exception) {
      // Handle other errors like max file size error send by the backend...
      addErrorMessage('An unexpected error has occurred, the file uploaded may be too large');
      setState((prevState) => ({ ...prevState, loading: false, uploadedDocument: null }));
    }
  };

  const { loading, uploadedDocument } = state;
  return {
    uploadDocument,
    loading,
    uploadedDocument,
  };
};
