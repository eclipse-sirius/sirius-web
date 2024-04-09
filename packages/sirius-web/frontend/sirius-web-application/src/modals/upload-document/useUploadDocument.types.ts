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
export interface UseUploadDocumentValue {
  uploadDocument: (editingContextId: string, file: File) => void;
  loading: boolean;
  uploadedDocument: GQLUploadDocumentSuccessPayload | null;
}

export interface UseUploadDocumentState {
  loading: boolean;
  uploadedDocument: GQLUploadDocumentSuccessPayload | null;
}

export interface GQLUploadDocumentMutationVariables {
  input: GQLUploadDocumentInput;
}

export interface GQLUploadDocumentInput {
  id: string;
  editingContextId: string;
  file: File | null;
}

export interface GQLUploadDocumentMutationData {
  uploadDocument: GQLUploadDocumentPayload;
}

export interface GQLUploadDocumentPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLUploadDocumentPayload {
  message: string;
}

export interface GQLUploadDocumentSuccessPayload extends GQLUploadDocumentPayload {
  report: string;
}
