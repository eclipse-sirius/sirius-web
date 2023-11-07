/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
export interface DeleteImageModalProps {
  imageId: string;
  onImageDeleted: () => void;
  onClose: () => void;
}

export interface GQLDeleteImageMutationVariables {
  input: GQLDeleteImageMutationInput;
}

export interface GQLDeleteImageMutationInput {
  id: string;
  imageId: string;
}

export interface GQLDeleteImageMutationData {
  deleteImage: GQLDeleteImagePayload;
}

export interface GQLDeleteImagePayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLDeleteImagePayload {
  message: string;
}
