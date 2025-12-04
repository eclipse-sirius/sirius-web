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

export interface UseUpdateRectangularNodeAppearanceValue {
  updateRectangularNodeAppearance: (
    editingContextId: string,
    representationId: string,
    nodeIds: string[],
    appearance: Partial<GQLRectangularNodeAppearanceInput>
  ) => void;
}

export interface GQLEditRectangularNodeApparenceData {
  editRectangularNodeAppearance: GQLEditRectangularNodeApparencePayload;
}

export type GQLEditRectangularNodeApparencePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditRectangularNodeApparenceVariables {
  input: GQLEditRectangularNodeApparenceInput;
}

export interface GQLEditRectangularNodeApparenceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeIds: string[];
  appearance: Partial<GQLRectangularNodeAppearanceInput>;
}

export interface GQLRectangularNodeAppearanceInput {
  background: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: string;
}
