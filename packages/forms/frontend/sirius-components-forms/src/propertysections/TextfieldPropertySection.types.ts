/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { GQLSubscriber, GQLTextarea, GQLTextfield } from '../form/FormEventFragments.types';

export interface TextfieldPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLTextfield | GQLTextarea;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
}

export interface GQLEditTextfieldMutationData {
  editTextfield: GQLEditTextfieldPayload;
}

export interface GQLEditTextfieldMutationVariables {
  input: GQLEditTextfieldInput;
}

export interface GQLEditTextfieldInput {
  id: string;
  editingContextId: string;
  representationId: string;
  textfieldId: string;
  newValue: string;
}

export interface GQLEditTextfieldPayload {
  __typename: string;
}

export interface GQLEditTextfieldSuccessPayload extends GQLEditTextfieldPayload {}

export interface GQLErrorPayload extends GQLEditTextfieldPayload, GQLUpdateWidgetFocusPayload {
  message: string;
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
