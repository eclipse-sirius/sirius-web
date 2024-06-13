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
import { GQLButton } from '../form/FormEventFragments.types';

export interface ButtonPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLButton;
  readOnly: boolean;
}

export interface ButtonStyleProps {
  backgroundColor: string | null;
  foregroundColor: string | null;
  fontSize: number | string | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
  iconOnly: boolean;
}

export interface GQLPushButtonMutationData {
  pushButton: GQLPushButtonPayload;
}

export interface GQLPushButtonMutationVariables {
  input: GQLPushButtonInput;
}

export interface GQLPushButtonInput {
  id: string;
  editingContextId: string;
  representationId: string;
  buttonId: string;
}

export interface GQLPushButtonPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLPushButtonPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLPushButtonPayload {
  messages: GQLMessage[];
}
