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

export interface UseImportLibrariesValue {
  importLibraries: (projectId: string, type: string, libraryIds: string[]) => void;
  loading: boolean;
  data: GQLImportLibrariesPayload | null;
}

export interface GQLImportLibrariesMutationVariables {
  input: GQLImportLibrariesMutationInput;
}

export interface GQLImportLibrariesMutationInput {
  id: string;
  editingContextId: string;
  type: string;
  libraryIds: string[];
}

export interface GQLImportLibrariesMutationData {
  importLibraries: GQLImportLibrariesPayload;
}

export interface GQLImportLibrariesPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLImportLibrariesPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLImportLibrariesPayload {
  messages: GQLMessage[];
}
