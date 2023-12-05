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

export interface UseFadeDiagramElements {
  fadeDiagramElements: (nodeId: string[], fade: boolean) => void;
}

export interface GQLFadeDiagramElementInput {
  id: string;
  editingContextId: string;
  representationId: string;
  elementIds: string[];
  fade: boolean;
}

export interface GQLFadeDiagramElementVariables {
  input: GQLFadeDiagramElementInput;
}

export interface GQLFadeDiagramElementPayload {
  __typename: string;
}

export interface GQLFadeDiagramElementData {
  fadeDiagramElement: GQLFadeDiagramElementPayload;
}

export interface GQLErrorPayload extends GQLFadeDiagramElementPayload {
  messages: GQLMessage[];
}

export interface GQLSuccessPayload extends GQLFadeDiagramElementPayload {
  messages: GQLMessage[];
}
