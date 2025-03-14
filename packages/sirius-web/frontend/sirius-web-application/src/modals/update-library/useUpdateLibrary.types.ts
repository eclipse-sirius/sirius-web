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

export interface UseUpdateLibraryValue {
  updateLibrary: (editingContextId: string, libraryId: string) => void;
  loading: boolean;
  data: GQLUpdateLibraryMutationData | null;
}

export interface GQLUpdateLibraryMutationVariables {
  input: GQLUpdateLibraryMutationInput;
}

export interface GQLUpdateLibraryMutationInput {
  id: string;
  editingContextId: string;
  libraryId: string;
}

export interface GQLUpdateLibraryMutationData {
  updateLibrary: GQLUpdateLibraryPayload;
}

export interface GQLUpdateLibraryPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLUpdateLibraryPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLUpdateLibraryPayload {
  messages: GQLMessage[];
}
