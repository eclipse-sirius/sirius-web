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
import { GQLCheckbox, GQLSubscriber } from '../form/FormEventFragments.types';

export interface CheckboxStyleProps {
  color: string | null;
}

export interface CheckboxPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLCheckbox;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
}

export interface GQLEditCheckboxMutationData {
  editCheckbox: GQLEditCheckboxPayload;
}

export interface GQLEditCheckboxPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditCheckboxPayload, GQLUpdateWidgetFocusPayload {
  message: string;
}

export interface GQLSuccessPayload extends GQLEditCheckboxPayload {}

export interface GQLUpdateWidgetFocusMutationData {
  updateWidgetFocus: GQLUpdateWidgetFocusPayload;
}

export interface GQLUpdateWidgetFocusPayload {
  __typename: string;
}

export interface GQLEditCheckboxMutationVariables {
  input: GQLEditCheckboxInput;
}

export interface GQLEditCheckboxInput {
  id: string;
  editingContextId: string;
  representationId: string;
  checkboxId: string;
  newValue: boolean;
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
