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
import { GQLMessage, GQLSelect, GQLSubscriber } from '../form/FormEventFragments.types';

export interface SelectStyleProps {
  backgroundColor: string | undefined;
  foregroundColor: string | undefined;
  fontSize: number | undefined;
  italic: boolean | undefined;
  bold: boolean | undefined;
  underline: boolean | undefined;
  strikeThrough: boolean | undefined;
}

export interface SelectPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLSelect;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
}

export interface GQLEditSelectMutationData {
  editSelect: GQLEditSelectPayload;
}

export interface GQLEditSelectPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditSelectPayload, GQLUpdateWidgetFocusPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLEditSelectPayload {
  messages: GQLMessage[];
}

export interface GQLUpdateWidgetFocusMutationData {
  updateWidgetFocus: GQLUpdateWidgetFocusPayload;
}

export interface GQLUpdateWidgetFocusPayload {
  __typename: string;
}
