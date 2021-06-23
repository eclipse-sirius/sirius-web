/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
export interface UploadDocumentModalProps {
  editingContextId: string;
  onDocumentUploaded: () => void;
  onClose: () => void;
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
