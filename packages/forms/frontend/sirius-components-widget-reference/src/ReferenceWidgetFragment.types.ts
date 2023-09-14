/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { GQLMessage, GQLWidget } from '@eclipse-sirius/sirius-components-forms';

export interface GQLReferenceWidget extends GQLWidget {
  label: string;
  reference: GQLReference;
  referenceValues: GQLReferenceValue[] | null;
  referenceOptions: GQLReferenceValue[] | null;
  style: GQLReferenceWidgetStyle | null;
  ownerId: string;
}

export interface GQLReferenceWidgetStyle {
  color: string | null;
  fontSize: number | null;
  italic: boolean | null;
  bold: boolean | null;
  underline: boolean | null;
  strikeThrough: boolean | null;
}

export interface GQLReference {
  typeName: string;
  referenceKind: string;
  containment: boolean;
  manyValued: boolean;
}

export interface GQLReferenceValue {
  id: string;
  label: string;
  kind: string;
  iconURL: string | null;
  hasClickAction: boolean;
}

export interface GQLErrorPayload extends GQLClickReferenceValuePayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLClickReferenceValuePayload {
  messages: GQLMessage[];
}

export interface GQLClickReferenceValueMutationData {
  clickReferenceValue: GQLClickReferenceValuePayload;
}

export interface GQLClickReferenceValuePayload {
  __typename: string;
}

export interface GQLClickReferenceValueMutationVariables {
  input: GQLClickReferenceValueInput;
}

export interface GQLClickReferenceValueInput {
  id: string;
  editingContextId: string;
  representationId: string;
  referenceWidgetId: string;
  referenceValueId: string;
  clickEventKind: 'SINGLE_CLICK' | 'DOUBLE_CLICK';
}

export interface GQLClearReferenceMutationData {
  clearReference: GQLClearReferencePayload;
}

export interface GQLClearReferencePayload {
  __typename: string;
}

export interface GQLClearReferenceMutationVariables {
  input: GQLClearReferenceInput;
}

export interface GQLClearReferenceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  referenceWidgetId: string;
}

export interface GQLRemoveReferenceValueMutationData {
  removeReferenceValue: GQLRemoveReferenceValuePayload;
}

export interface GQLRemoveReferenceValuePayload {
  __typename: string;
}

export interface GQLRemoveReferenceValueMutationVariables {
  input: GQLRemoveReferenceValueInput;
}

export interface GQLRemoveReferenceValueInput {
  id: string;
  editingContextId: string;
  representationId: string;
  referenceWidgetId: string;
  referenceValueId: string;
}

export interface GQLSetReferenceValueMutationData {
  setReferenceValue: GQLSetReferenceValuePayload;
}

export interface GQLSetReferenceValuePayload {
  __typename: string;
}

export interface GQLSetReferenceValueMutationVariables {
  input: GQLSetReferenceValueInput;
}

export interface GQLSetReferenceValueInput {
  id: string;
  editingContextId: string;
  representationId: string;
  referenceWidgetId: string;
  newValueId: string;
}

export interface GQLAddReferenceValuesMutationData {
  addReferenceValues: GQLAddReferenceValuesPayload;
}

export interface GQLAddReferenceValuesPayload {
  __typename: string;
}

export interface GQLAddReferenceValuesMutationVariables {
  input: GQLAddReferenceValuesInput;
}

export interface GQLAddReferenceValuesInput {
  id: string;
  editingContextId: string;
  representationId: string;
  referenceWidgetId: string;
  newValueIds: Array<string>;
}

export interface GQLMoveReferenceValueMutationData {
  moveReferenceValue: GQLMoveReferenceValuePayload;
}

export interface GQLMoveReferenceValuePayload {
  __typename: string;
}

export interface GQLMoveReferenceValueMutationVariables {
  input: GQLMoveReferenceValueInput;
}

export interface GQLMoveReferenceValueInput {
  id: string;
  editingContextId: string;
  representationId: string;
  referenceWidgetId: string;
  referenceValueId: string;
  fromIndex: number;
  toIndex: number;
}
