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

export interface UseUpdateEllipseNodeAppearanceValue {
  updateEllipseNodeAppearance: (
    editingContextId: string,
    representationId: string,
    nodeId: string,
    appearance: Partial<GQLEllipseNodeAppearanceInput>
  ) => void;
}

export interface GQLEditEllipseNodeAppearanceData {
  editEllipseNodeAppearance: GQLEditEllipseNodeAppearancePayload;
}

export type GQLEditEllipseNodeAppearancePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditEllipseNodeAppearanceVariables {
  input: GQLEditEllipseNodeAppearanceInput;
}

export interface GQLEditEllipseNodeAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeId: string;
  appearance: Partial<GQLEllipseNodeAppearanceInput>;
}

export interface GQLEllipseNodeAppearanceInput {
  background: string;
  borderColor: string;
  borderRadius: number;
  borderSize: number;
  borderStyle: string;
}
