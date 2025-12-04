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

export interface UseUpdateImageNodeAppearanceValue {
  updateImageNodeAppearance: (
    editingContextId: string,
    representationId: string,
    nodeIds: string[],
    appearance: Partial<GQLImageNodeAppearanceInput>
  ) => void;
}

export interface GQLEditImageNodeAppearanceData {
  editImageNodeAppearance: GQLEditImageNodeAppearancePayload;
}

export type GQLEditImageNodeAppearancePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditImageNodeAppearanceVariables {
  input: GQLEditImageNodeAppearanceInput;
}

export interface GQLEditImageNodeAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeIds: string[];
  appearance: Partial<GQLImageNodeAppearanceInput>;
}

export interface GQLImageNodeAppearanceInput {
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: string;
}
