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

export interface UseCreateProjectValue {
  createProject: (name: string, natures: string[]) => void;
  loading: boolean;
  newProjectId: string | null;
}

export interface GQLCreateProjectMutationVariables {
  input: GQLCreateProjectMutationInput;
}

export interface GQLCreateProjectMutationInput {
  id: string;
  name: string;
  natures: string[];
}

export interface GQLCreateProjectMutationData {
  createProject: GQLCreateProjectPayload;
}

export interface GQLCreateProjectPayload {
  __typename: string;
}

export interface GQLCreateProjectSuccessPayload extends GQLCreateProjectPayload {
  project: GQLProject;
}

export interface GQLProject {
  id: string;
}

export interface GQLErrorPayload extends GQLCreateProjectPayload {
  messages: GQLMessage[];
}
