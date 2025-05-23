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

import { GQLErrorPayload, GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseDuplicateRepresentationValue {
  duplicateRepresentation: (editingContextId: string, representationId: string) => void;
  duplicatedRepresentationMetadata: GQLRepresentationMetadata | null;
}

export interface GQLDuplicateRepresentationVariables {
  input: GQLDuplicateRepresentationInput;
}

export interface GQLDuplicateRepresentationInput {
  id: string;
  editingContextId: string;
  representationId: string;
}

export interface GQLDuplicateRepresentationData {
  duplicateRepresentation: GQLDuplicateRepresentationPayload;
}

export interface GQLDuplicateObjectSuccessPayload {
  __typename: 'DuplicateRepresentationSuccessPayload';
  id: string | null;
  representationMetadata: GQLRepresentationMetadata;
  messages: GQLMessage[] | null;
}

export interface GQLRepresentationMetadata {
  id: string;
}

export type GQLDuplicateRepresentationPayload = GQLErrorPayload | GQLDuplicateObjectSuccessPayload;
