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

export interface UseCollapseExpandElement {
  collapseExpandElement: (nodeId: string, collapsingState: GQLCollapsingState) => void;
}

export interface GQLUpdateCollapsingStateData {
  collapseExpandDiagramElement: GQLUpdateCollapsingStatePayload;
}

export interface GQLUpdateCollapsingStatePayload {
  __typename: string;
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

export enum GQLCollapsingState {
  EXPANDED = 'EXPANDED',
  COLLAPSED = 'COLLAPSED',
}

export interface GQLErrorPayload extends GQLUpdateCollapsingStatePayload {
  message: string;
  messages: GQLMessage[];
}
