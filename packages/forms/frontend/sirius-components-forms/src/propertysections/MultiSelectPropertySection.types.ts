/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import { GQLMessage, GQLMultiSelect, GQLSubscriber } from '../form/FormEventFragments.types';

export interface MultiSelectStyleProps {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface MultiSelectPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLMultiSelect;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
}

export interface GQLEditMultiSelectMutationData {
  editMultiSelect: GQLEditMultiSelectPayload;
}

export interface GQLEditMultiSelectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditMultiSelectPayload, GQLUpdateWidgetFocusPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLEditMultiSelectPayload {
  messages: GQLMessage[];
}

export interface GQLUpdateWidgetFocusMutationData {
  updateWidgetFocus: GQLUpdateWidgetFocusPayload;
}

export interface GQLUpdateWidgetFocusPayload {
  __typename: string;
}
