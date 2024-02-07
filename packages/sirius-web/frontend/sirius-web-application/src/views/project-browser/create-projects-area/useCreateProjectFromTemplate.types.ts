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

export interface UseCreateProjectFromTemplateValue {
  createProjectFromTemplate: (templateId: string) => void;
  loading: boolean;
  projectCreatedFromTemplate: GQLCreateProjectFromTemplateSuccessPayload | null;
}

export interface GQLCreateProjectFromTemplateMutationData {
  createProjectFromTemplate: GQLCreateProjectFromTemplatePayload;
}

export interface GQLCreateProjectFromTemplatePayload {
  __typename: string;
}

export interface GQLCreateProjectFromTemplateSuccessPayload extends GQLCreateProjectFromTemplatePayload {
  project: GQLProject;
  representationToOpen: GQLRepresentationMetadata | null;
}

export interface GQLProject {
  id: string;
}

export interface GQLRepresentationMetadata {
  id: string;
}

export interface GQLErrorPayload extends GQLCreateProjectFromTemplatePayload {
  messages: GQLMessage[];
}

export interface GQLCreateProjectFromTemplateMutationVariables {
  input: GQLCreateProjectFromTemplateInput;
}

export interface GQLCreateProjectFromTemplateInput {
  id: string;
  templateId: string;
}
