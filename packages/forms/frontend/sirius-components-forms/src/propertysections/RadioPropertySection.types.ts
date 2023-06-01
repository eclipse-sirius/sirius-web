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
import { GQLMessage, GQLRadio, GQLSubscriber } from '../form/FormEventFragments.types';

export interface RadioStyleProps {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface RadioPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLRadio;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
}

export interface GQLEditRadioMutationData {
  editRadio: GQLEditRadioPayload;
}

export interface GQLEditRadioPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditRadioPayload, GQLUpdateWidgetFocusPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLEditRadioPayload {
  messages: GQLMessage[];
}

export interface GQLUpdateWidgetFocusMutationData {
  updateWidgetFocus: GQLUpdateWidgetFocusPayload;
}

export interface GQLUpdateWidgetFocusPayload {
  __typename: string;
}

export interface GQLEditRadioMutationVariables {
  input: GQLEditRadioInput;
}

export interface GQLEditRadioInput {
  id: string;
  editingContextId: string;
  representationId: string;
  radioId: string;
  newValue: string;
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

export interface GQLUpdateWidgetFocusSuccessPayload extends GQLUpdateWidgetFocusPayload {}
