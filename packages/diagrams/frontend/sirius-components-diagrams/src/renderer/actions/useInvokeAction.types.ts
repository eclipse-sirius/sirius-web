/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { GQLAction } from './useActions.types';

export interface UseInvokeActionValue {
  invokeAction: (action: GQLAction) => void;
}

export interface GQLInvokeActionPayload {
  __typename: string;
}

export interface GQLSuccessPayload extends GQLInvokeActionPayload {
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLInvokeActionPayload {
  messages: GQLMessage[];
}

export interface GQLInvokeActionData {
  invokeAction: GQLInvokeActionPayload;
}

export interface GQLInvokeActionVariables {
  input: GQLInvokeActionInput;
}

export interface GQLInvokeActionInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  actionId: string;
}
