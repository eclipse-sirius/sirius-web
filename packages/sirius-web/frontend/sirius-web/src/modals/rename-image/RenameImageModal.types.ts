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
export interface RenameImageModalProps {
  imageId: string;
  initialImageName: string;
  onImageRenamed: () => void;
  onClose: () => void;
}

export interface GQLRenameImageMutationVariables {
  input: GQLRenameImageMutationInput;
}

export interface GQLRenameImageMutationInput {
  id: string;
  imageId: string;
  newLabel: string;
}

export interface GQLRenameImageMutationData {
  renameImage: GQLRenameImagePayload;
}

export interface GQLRenameImagePayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLRenameImagePayload {
  message: string;
}
