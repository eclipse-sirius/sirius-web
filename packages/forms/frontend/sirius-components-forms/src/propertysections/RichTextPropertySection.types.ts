/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

export interface GQLEditRichTextMutationData {
  editRichText: GQLEditRichTextPayload;
}

export interface GQLEditRichTextMutationVariables {
  input: GQLEditRichTextInput;
}

export interface GQLEditRichTextInput {
  id: string;
  editingContextId: string;
  representationId: string;
  richTextId: string;
  newValue: string;
}

export interface GQLEditRichTextPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLEditRichTextPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLEditRichTextPayload {
  messages: GQLMessage[];
}
