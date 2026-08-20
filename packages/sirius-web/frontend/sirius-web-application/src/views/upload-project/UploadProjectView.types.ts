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
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UploadProjectViewState {
  file: File | null;
  loading: boolean;
  newProjectId: string | null;
}

export interface GQLUploadProjectMutationData {
  uploadProject: GQLUploadProjectPayload;
}

export interface GQLUploadProjectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLUploadProjectPayload {
  messages: GQLMessage[];
}

export interface GQLUploadProjectSuccessPayload extends GQLUploadProjectPayload {
  project: GQLProject;
}

export interface GQLProject {
  id: string;
}
