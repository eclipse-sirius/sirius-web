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
  container: boolean;
  manyValued: boolean;
  referenceValues: Array<GQLReferenceValue> | null;
}

export interface GQLReferenceValue {
  id: string;
  label: string;
  kind: string;
  iconURL: string | null;
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
