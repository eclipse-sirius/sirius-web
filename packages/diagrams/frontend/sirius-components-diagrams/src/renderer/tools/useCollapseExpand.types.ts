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

export interface UseCollapseExpandValue {
  collapseExpandElement: (
    editingContextId: string,
    diagramId: string,
    nodeId: string,
    collapsingState: GQLCollapsingState
  ) => void;
  loading: boolean;
  data: GQLUpdateCollapsingStateData | null;
}

export enum GQLCollapsingState {
  EXPANDED = 'EXPANDED',
  COLLAPSED = 'COLLAPSED',
}

export interface GQLUpdateCollapsingStateVariables {
  input: GQLUpdateCollapsingStateInput;
}

export interface GQLUpdateCollapsingStateInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  collapsingState: GQLCollapsingState;
}

export interface GQLUpdateCollapsingStateData {
  updateCollapsingState: GQLUpdateCollapsingStatePayload;
}

export type GQLUpdateCollapsingStatePayload = GQLErrorPayload | GQLSuccessPayload;
