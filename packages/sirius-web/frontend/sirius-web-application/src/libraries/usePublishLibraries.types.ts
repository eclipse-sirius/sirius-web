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

export interface UsePublishLibrariesValue {
  publishLibraries: (projectId: string, publicationKind: string, version: string, description: string) => void;
  loading: boolean;
  data: GQLPublishLibrariesMutationData | null;
}

export interface GQLPublishLibrariesMutationVariables {
  input: GQLPublishLibrariesMutationInput;
}

export interface GQLPublishLibrariesMutationInput {
  id: string;
  projectId: string;
  publicationKind: string;
  version: string;
  description: string;
}

export interface GQLPublishLibrariesMutationData {
  publishLibraries: GQLPublishLibrariesPayload;
}

export interface GQLPublishLibrariesPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLPublishLibrariesPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLPublishLibrariesPayload {
  messages: GQLMessage[];
}
