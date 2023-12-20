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
export interface UsePinDiagramElements {
  pinDiagramElements: (nodeId: string[], pinned: boolean) => void;
}

export interface GQLPinDiagramElementInput {
  id: string;
  editingContextId: string;
  representationId: string;
  elementIds: string[];
  pinned: boolean;
}

export interface GQLPinDiagramElementVariables {
  input: GQLPinDiagramElementInput;
}

export interface GQLPinDiagramElementPayload {
  __typename: string;
}

export interface GQLPinDiagramElementData {
  pinDiagramElement: GQLPinDiagramElementPayload;
}

export interface GQLErrorPayload extends GQLPinDiagramElementPayload {
  message: string;
}
