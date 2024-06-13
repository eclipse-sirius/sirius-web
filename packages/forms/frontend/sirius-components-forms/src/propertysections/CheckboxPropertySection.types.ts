/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

export interface CheckboxStyleProps {
  color: string | null;
}

export interface GQLEditCheckboxMutationData {
  editCheckbox: GQLEditCheckboxPayload;
}

export interface GQLEditCheckboxPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditCheckboxPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLEditCheckboxPayload {
  messages: GQLMessage[];
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
