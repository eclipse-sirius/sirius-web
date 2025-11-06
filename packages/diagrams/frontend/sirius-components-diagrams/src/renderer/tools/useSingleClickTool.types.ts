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
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';
import { GQLTool } from '../palette/Palette.types';

export interface UseSingleClickToolValue {
  invokeSingleClickTool: (
    editingContextId: string,
    diagramId: string,
    tool: GQLTool,
    diagramElementIds: string[],
    targetObjectId: string,
    x: number,
    y: number
  ) => void;
  loading: boolean;
  data: GQLInvokeSingleClickOnDiagramElementToolData | null;
}

/**
 * The state used for the execution of a single click tool on a diagram element.
 *
 * @since v2025.8.0
 */
export interface UseSingleClickToolState {
  /**
   * The tool that is currently being executed.
   *
   * This tool is kept in the state to be able to give it to the impact analysis dialog
   * when the user clicks on the confirmation button.
   */
  currentTool: GQLTool | null;

  /**
   * The behavior to execute when the tool is invoked.
   *
   * This behavior can be kept in the state in order to be able to execute it later,
   * for example when the user clicks on the confirmation button of the impact analysis dialog.
   */
  onToolExecution: () => void;
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
}

export interface GQLInvokeSingleClickOnDiagramElementToolVariables {
  input: GQLInvokeSingleClickOnDiagramElementToolInput;
}

export interface GQLInvokeSingleClickOnDiagramElementToolInput {
  id: string;
  editingContextId: string;
  representationId: string;
  diagramElementIds: string[];
  toolId: string;
  startingPositionX: number;
  startingPositionY: number;
  variables: GQLToolVariable[];
}

/**
 * A variable that can be added by the frontend to a tool invocation.
 *
 * It is used by various dialogs to add additional information to the tool invocation.
 * For example, the selection dialog adds the selected object(s) to the tool invocation.
 *
 * @since v2024.9.0
 */
export interface GQLToolVariable {
  name: string;
  value: string;
  type: GQLToolVariableType;
}

/**
 * Used to represent the type of a tool variable.
 *
 * @since v2024.9.0
 */
export type GQLToolVariableType = 'STRING' | 'OBJECT_ID' | 'OBJECT_ID_ARRAY';
