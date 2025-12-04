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

export interface UseEditEdgeAppearanceValue {
  updateEdgeAppearance: (
    editingContextId: string,
    representationId: string,
    edgeIds: string[],
    appearance: Partial<GQLEdgeAppearanceInput>
  ) => void;
}

export interface GQLEditEdgeAppearanceData {
  editEdgeAppearance: GQLEditEdgeAppearancePayload;
}

export type GQLEditEdgeAppearancePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLEditEdgeAppearanceVariables {
  input: GQLEditEdgeAppearanceInput;
}

export interface GQLEditEdgeAppearanceInput {
  id: string;
  editingContextId: string;
  representationId: string;
  edgeIds: string[];
  appearance: Partial<GQLEdgeAppearanceInput>;
}

export interface GQLEdgeAppearanceInput {
  size: number;
  color: string;
  lineStyle: string;
  sourceArrowStyle: string;
  targetArrowStyle: string;
  edgeType: string;
}
