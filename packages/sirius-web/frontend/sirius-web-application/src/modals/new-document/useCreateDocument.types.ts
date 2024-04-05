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

export interface UseCreateDocumentValue {
  createDocument: (editingContextId: string, stereotypeId: string, name: string) => void;
  loading: boolean;
  documentCreated: GQLCreateDocumentSuccessPayload | null;
}

export interface GQLCreateDocumentMutationData {
  createDocument: GQLCreateDocumentPayload;
}

export interface GQLCreateDocumentPayload {
  __typename: string;
}

export interface GQLCreateDocumentSuccessPayload extends GQLCreateDocumentPayload {
  document: GQLDocument;
}

export interface GQLDocument {
  id: string;
  name: string;
  kind: string;
}

export interface GQLErrorPayload extends GQLCreateDocumentPayload {
  messages: GQLMessage[];
}

export interface GQLCreateDocumentMutationVariables {
  input: GQLCreateDocumentInput;
}

export interface GQLCreateDocumentInput {
  id: string;
  editingContextId: string;
  stereotypeId: string;
  name: string;
}
