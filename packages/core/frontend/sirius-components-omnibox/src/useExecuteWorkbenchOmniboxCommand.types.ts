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

import { GQLMessage, Selection } from '@eclipse-sirius/sirius-components-core';

export interface UseExecuteWorkbenchOmniboxCommandValue {
  executeWorkbenchOmniboxCommand: (editingContextId: string, selectedObjectIds: string[], commandId: string) => void;
  loading: boolean;
  data: GQLExecuteWorkbenchOmniboxCommandData | null;
}

export interface GQLExecuteWorkbenchOmniboxCommandVariables {
  input: GQLExecuteWorkbenchOmniboxInput;
}

export interface GQLExecuteWorkbenchOmniboxInput {
  id: string;
  editingContextId: string;
  selectedObjectIds: string[];
  commandId: string;
}

export interface GQLExecuteWorkbenchOmniboxCommandData {
  executeWorkbenchOmniboxCommand: GQLExecuteWorkbenchOmniboxCommandPayload;
}

export interface GQLExecuteWorkbenchOmniboxCommandPayload {
  __typename: string;
}

export interface GQLExecuteWorkbenchOmniboxCommandSuccessPayload extends GQLExecuteWorkbenchOmniboxCommandPayload {
  newSelection: Selection | null;
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLExecuteWorkbenchOmniboxCommandPayload {
  messages: GQLMessage[];
}
