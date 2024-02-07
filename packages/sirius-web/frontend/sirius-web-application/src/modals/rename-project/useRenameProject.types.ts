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

export interface UseRenameProjectValue {
  renameProject: (projectId: string, newName: string) => void;
  loading: boolean;
  projectRenamed: boolean;
}

export interface GQLRenameProjectMutationData {
  renameProject: GQLRenameProjectPayload;
}

export interface GQLRenameProjectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLRenameProjectPayload {
  messages: GQLMessage[];
}

export interface GQLRenameProjectMutationVariables {
  input: GQLRenameProjectMutationInput;
}

export interface GQLRenameProjectMutationInput {
  id: string;
  projectId: string;
  newName: string;
}
