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
import { GQLTreeNode } from '../form/FormEventFragments.types';

export interface TreeItemProps {
  node: GQLTreeNode;
  nodes: GQLTreeNode[];
  readOnly: boolean;
  editingContextId: string;
  formId: string;
  widgetId: string;
}

export interface GQLEditTreeCheckboxMutationData {
  editTreeCheckbox: GQLEditTreeCheckboxPayload;
}

export interface GQLEditTreeCheckboxPayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditTreeCheckboxPayload, GQLUpdateWidgetFocusPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLEditTreeCheckboxPayload {
  messages: GQLMessage[];
}

export interface GQLUpdateWidgetFocusMutationData {
  updateWidgetFocus: GQLUpdateWidgetFocusPayload;
}

export interface GQLUpdateWidgetFocusPayload {
  __typename: string;
}

export interface GQLEditTreeCheckboxMutationVariables {
  input: GQLEditTreeCheckboxInput;
}

export interface GQLEditTreeCheckboxInput {
  id: string;
  editingContextId: string;
  representationId: string;
  treeId: string;
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
