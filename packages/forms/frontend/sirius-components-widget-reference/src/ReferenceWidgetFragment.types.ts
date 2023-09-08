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

export interface GQLEditReferencePayload {
  __typename: string;
}

export interface GQLErrorPayload extends GQLEditReferencePayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLEditReferencePayload {
  messages: GQLMessage[];
}

export interface GQLEditReferenceVariables {
  input: GQLEditReferenceInput;
}

export interface GQLEditReferenceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  referenceWidgetId: string;
  newValueIds: Array<string>;
}

export interface GQLEditReferenceData {
  editReference: GQLEditReferencePayload;
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
