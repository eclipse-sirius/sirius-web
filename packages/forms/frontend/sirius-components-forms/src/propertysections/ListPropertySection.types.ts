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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLList, GQLMessage, GQLSubscriber } from '../form/FormEventFragments.types';

export interface ListStyleProps {
  color: string | undefined;
  fontSize: number | undefined;
  italic: boolean | undefined;
  bold: boolean | undefined;
  underline: boolean | undefined;
  strikeThrough: boolean | undefined;
}

export interface ListPropertySectionProps {
  editingContextId: string;
  formId: string;
  widget: GQLList;
  subscribers: GQLSubscriber[];
  readOnly: boolean;
  setSelection: (selection: Selection) => void;
}

export interface GQLDeleteListItemMutationData {
  deleteListItem: GQLDeleteListItemPayload;
}

export interface GQLClickListItemMutationData {
  clickListItem: GQLClickListItemPayload;
}

export interface GQLDeleteListItemPayload {
  __typename: string;
}

export interface GQLClickListItemPayload {
  __typename: string;
}

export interface GQLClickListItemMutationVariables {
  input: GQLClickListItemInput;
}

export interface GQLClickListItemInput {
  id: string;
  editingContextId: string;
  representationId: string;
  listId: string;
  listItemId: string;
  clickEventKind: 'SINGLE_CLICK' | 'DOUBLE_CLICK';
}

export interface GQLErrorPayload extends GQLDeleteListItemPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLDeleteListItemPayload {
  messages: GQLMessage[];
}
