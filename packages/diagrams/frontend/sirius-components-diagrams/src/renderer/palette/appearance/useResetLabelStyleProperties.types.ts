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

export interface UseResetLabelAppearanceValue {
  resetInsideLabelBold: (labelId: string) => void;
}

export interface GQLResetLabelApparenceData {
  resetLabelAppearance: GQLResetLabelApparencePayload;
}

export type GQLResetLabelApparencePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLResetLabelAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  labelId: string;
  propertiesToReset: string[];
}

export interface GQLResetLabelApparenceVariables {
  input: GQLResetLabelAppearanceInput;
}
