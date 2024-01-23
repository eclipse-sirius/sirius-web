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

import { GQLMessage } from '../form/FormEventFragments.types';

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

export interface GQLErrorPayload extends GQLEditRichTextPayload, GQLUpdateWidgetFocusPayload {
  messages: GQLMessage[];
}

export interface GQLUpdateWidgetFocusMutationVariables {
  input: GQLUpdateWidgetFocusInput;
}

export interface GQLUpdateWidgetFocusInput {
  id: string;
  editingContextId: string;
  representationId: string;
  widgetId: string;
  selected: boolean;
}

export interface GQLUpdateWidgetFocusMutationData {
  updateWidgetFocus: GQLUpdateWidgetFocusPayload;
}

export interface GQLUpdateWidgetFocusPayload {
  __typename: string;
}

export interface GQLUpdateWidgetFocusSuccessPayload extends GQLUpdateWidgetFocusPayload {}
