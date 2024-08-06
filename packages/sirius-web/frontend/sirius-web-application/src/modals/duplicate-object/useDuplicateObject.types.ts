/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { GQLErrorPayload, GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseDuplicateObjectValue {
  duplicateObject: (
    editingContextId: string,
    objectId: string,
    containerId: string,
    containmentFeatureName: string,
    duplicateContent: boolean,
    copyOutgoingReferences: boolean,
    updateIncomingReferences: boolean
  ) => void;
  duplicatedObject: GQLObject;
}

export interface GQLDuplicateObjectVariables {
  input: GQLDuplicateObjectInput;
}
export interface GQLDuplicateObjectInput {
  id: string;
  editingContextId: string;
  objectId: string;
  containerId: string;
  containmentFeatureName: string;
  duplicateContent: boolean;
  copyOutgoingReferences: boolean;
  updateIncomingReferences: boolean;
}
export interface GQLDuplicateObjectData {
  duplicateObject: GQLDuplicateObjectPayload;
}

export interface GQLDuplicateObjectSuccessPayload {
  __typename: 'DuplicateObjectSuccessPayload';
  id: string | null;
  object: GQLObject;
  messages: GQLMessage[] | null;
}

export interface GQLObject {
  id: string;
  label: string;
  kind: string;
}

export type GQLDuplicateObjectPayload = GQLErrorPayload | GQLDuplicateObjectSuccessPayload;
