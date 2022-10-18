/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

export interface UploadImageModalProps {
  projectId: string;
  onImageUploaded: () => void;
  onClose: () => void;
}

export interface GQLUploadImageMutationVariables {
  input: GQLUploadImageMutationInput;
}

export interface GQLUploadImageMutationInput {
  id: string;
  editingContextId: string;
  label: string;
  file: File;
}

export interface GQLUploadImageMutationData {
  uploadImage: GQLUploadImagePayload;
}

export interface GQLUploadImagePayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLUploadImagePayload {
  message: string;
}
