/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface RenameImageModalState {
  name: string;
}

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

export type GQLRenameImagePayload = GQLSuccessPayload | GQLErrorPayload;
