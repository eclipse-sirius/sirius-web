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
import { GQLMessage } from '../../Tool.types';
import { GQLPalette, GQLTool } from '../Palette.types';

export interface InvokeToolProps {
  x: number;
  y: number;
  diagramElementId: string;
  targetObjectId: string;
  palette: GQLPalette | null;
  onDirectEditClick: () => void;
}

export interface InvokeTool {
  invokeTool: (tool: GQLTool, variables: GQLToolVariable[]) => void;
}

export interface GQLInvokeSingleClickOnDiagramElementToolData {
  invokeSingleClickOnDiagramElementTool: GQLInvokeSingleClickOnDiagramElementToolPayload;
}

export interface GQLInvokeSingleClickOnDiagramElementToolPayload {
  __typename: string;
}

export interface GQLInvokeSingleClickOnDiagramElementToolSuccessPayload
  extends GQLInvokeSingleClickOnDiagramElementToolPayload {
  id: string;
  newSelection: GQLWorkbenchSelection;
  messages: GQLMessage[];
}

export interface GQLWorkbenchSelection {
  entries: GQLWorkbenchSelectionEntry[];
}

export interface GQLWorkbenchSelectionEntry {
  id: string;
  kind: string;
}

export interface GQLToolVariable {
  name: string;
  value: string;
  type: GQLToolVariableType;
}

export type GQLToolVariableType = 'STRING' | 'OBJECT_ID' | 'OBJECT_ID_ARRAY';

export interface GQLInvokeSingleClickOnDiagramElementToolVariables {
  input: GQLInvokeSingleClickOnDiagramElementToolInput;
}

export interface GQLInvokeSingleClickOnDiagramElementToolInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementId: string;
  toolId: string;
  startingPositionX: number;
  startingPositionY: number;
  variables: GQLToolVariable[];
}

export interface GQLErrorPayload extends GQLInvokeSingleClickOnDiagramElementToolPayload {
  message: string;
  messages: GQLMessage[];
}
