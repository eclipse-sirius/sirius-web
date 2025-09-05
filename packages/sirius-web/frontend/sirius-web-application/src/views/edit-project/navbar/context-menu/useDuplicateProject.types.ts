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

import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseDuplicateProjectValue {
  duplicateProject: (projectId: string) => void;
  loading: boolean;
  newProjectId: string | null;
}

export interface GQLDuplicateProjectMutationData {
  duplicateProject: GQLDuplicateProjectPayload;
}

export interface GQLDuplicateProjectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLDuplicateProjectPayload {
  messages: GQLMessage[];
}

export interface GQLProject {
  id: string;
}

export interface GQLDuplicateSuccessPayload extends GQLDuplicateProjectPayload {
  project: GQLProject;
}

export interface GQLDuplicateProjectMutationVariables {
  input: GQLDuplicateProjectMutationInput;
}

export interface GQLDuplicateProjectMutationInput {
  id: string;
  projectId: string;
}
