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

import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseResetEdgeAppearanceValue {
  resetEdgeStyleProperties: (
    editingContextId: string,
    representationId: string,
    edgeId: string,
    propertiesToReset: string[]
  ) => void;
}

export interface GQLResetEdgeAppearanceData {
  resetEdgeAppearance: GQLResetEdgeAppearancePayload;
}

export type GQLResetEdgeAppearancePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLResetEdgeAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  edgeId: string;
  propertiesToReset: string[];
}

export interface GQLResetEdgeAppearanceVariables {
  input: GQLResetEdgeAppearanceInput;
}
