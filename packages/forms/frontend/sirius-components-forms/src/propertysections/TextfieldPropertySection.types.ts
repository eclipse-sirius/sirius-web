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
import { GQLMessage, GQLSubscriber, GQLTextarea, GQLTextfield } from '../form/FormEventFragments.types';

export interface TextfieldStyleProps {
  backgroundColor: string | undefined;
  foregroundColor: string | undefined;
  fontSize: number | undefined;
  italic: boolean | undefined;
  bold: boolean | undefined;
  underline: boolean | undefined;
  strikeThrough: boolean | undefined;
}

export interface TextfieldPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLTextfield | GQLTextarea;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
}

export interface TextFieldState {
  textValue: string;
  cursorPosition: number;
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

export interface GQLSuccessPayload extends GQLEditTextfieldPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLEditTextfieldPayload, GQLUpdateWidgetFocusPayload {
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

export interface GQLCompletionProposalsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentation;
}

export interface GQLRepresentation {
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  __typename: string;
  completionProposals: GQLCompletionProposal[];
}

export interface GQLCompletionProposal {
  description: string;
  textToInsert: string;
  charsToReplace: number;
}

export interface GQLCompletionProposalsQueryVariables {
  editingContextId: string;
  formId: string;
  widgetId: string;
  currentText: string;
  cursorPosition: number;
}

export interface CompletionRequest {
  currentText: string;
  cursorPosition: number;
}
