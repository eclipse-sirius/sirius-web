/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { GQLMessage } from '../Tool.types';

export interface UseHideDiagramElements {
  hideDiagramElements: (nodeId: string[], hide: boolean) => void;
}

export interface GQLHideDiagramElementInput {
  id: string;
  editingContextId: string;
  representationId: string;
  elementIds: string[];
  hide: boolean;
}

export interface GQLHideDiagramElementVariables {
  input: GQLHideDiagramElementInput;
}

export interface GQLHideDiagramElementPayload {
  __typename: string;
}

export interface GQLHideDiagramElementData {
  hideDiagramElement: GQLHideDiagramElementPayload;
}

export interface GQLErrorPayload extends GQLHideDiagramElementPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLHideDiagramElementPayload {
  messages: GQLMessage[];
}
