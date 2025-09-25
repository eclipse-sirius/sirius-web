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
import { GQLErrorPayload } from '@eclipse-sirius/sirius-components-core';
import { GQLToolVariable } from '@eclipse-sirius/sirius-components-palette';

export interface UseSingleClickOnTwoDiagramElementToolValue {
  invokeSingleClickOnTwoDiagramElementsTool: (input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput) => void;
  loading: boolean;
  data: GQLInvokeSingleClickOnTwoDiagramElementsToolData | null;
}

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolVariables {
  input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput;
}
export interface GQLInvokeSingleClickOnTwoDiagramElementsToolInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramSourceElementId: string;
  diagramTargetElementId: string;
  sourcePositionX: number;
  sourcePositionY: number;
  targetPositionX: number;
  targetPositionY: number;
  toolId: string;
  variables: GQLToolVariable[];
}

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolData {
  invokeSingleClickOnTwoDiagramElementsTool: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload;
}

export interface GQLWorkbenchSelection {
  entries: GQLWorkbenchSelectionEntry[];
}

export interface GQLWorkbenchSelectionEntry {
  id: string;
}

export interface GQLMessage {
  body: string;
  level: string;
}

export type GQLInvokeSingleClickOnTwoDiagramElementsToolPayload =
  | GQLErrorPayload
  | GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload;

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload {
  id: string;
  messages: GQLMessage[];
  newSelection: GQLWorkbenchSelection;
  __typename: string;
}
