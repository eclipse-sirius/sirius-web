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

import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseDeleteProjectValue {
  deleteProject: (projectId: string) => void;
  loading: boolean;
  projectDeleted: boolean;
}

export interface GQLDeleteProjectMutationData {
  deleteProject: GQLDeleteProjectPayload;
}

export interface GQLDeleteProjectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLDeleteProjectPayload {
  messages: GQLMessage[];
}

export interface GQLDeleteProjectMutationVariables {
  input: GQLDeleteProjectMutationInput;
}

export interface GQLDeleteProjectMutationInput {
  id: string;
  projectId: string;
}
