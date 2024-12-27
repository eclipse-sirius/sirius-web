/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UsePinDiagramElements {
  pinDiagramElements: (nodeIds: string[], pinned: boolean) => void;
  loading: boolean;
  data: GQLPinDiagramElementData | null;
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

export interface GQLPinDiagramElementData {
  pinDiagramElement: GQLPinDiagramElementPayload;
}

export type GQLPinDiagramElementPayload = GQLErrorPayload | GQLSuccessPayload;
