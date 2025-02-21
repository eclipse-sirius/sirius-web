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

export interface UseExecuteOmniboxCommandValue {
  executeOmniboxCommand: (editingContextId: string, selectedObjectIds: string[], commandId: string) => void;
  loading: boolean;
  data: GQLExecuteOmniboxCommandData | null;
}

export interface GQLExecuteOmniboxCommandVariables {
  input: GQLExecuteOmniboxInput;
}

export interface GQLExecuteOmniboxInput {
  id: string;
  editingContextId: string;
  selectedObjectIds: string[];
  commandId: string;
}

export interface GQLExecuteOmniboxCommandData {
  executeOmniboxCommand: GQLExecuteOmniboxCommandPayload;
}

export interface GQLExecuteOmniboxCommandPayload {
  __typename: string;
}

export interface GQLExecuteOmniboxCommandSuccessPayload extends GQLExecuteOmniboxCommandPayload {
  newSelection: Selection | null;
  messages: GQLMessage[];
}

export interface GQLErrorPayload extends GQLExecuteOmniboxCommandPayload {
  messages: GQLMessage[];
}
