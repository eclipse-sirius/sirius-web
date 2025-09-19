/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { GQLToolVariable } from '@eclipse-sirius/sirius-components-palette';
import { GQLConnectorTool } from './useConnector.types';

export interface ConnectorContextualMenuProps {}

export interface GetConnectorToolsVariables {
  editingContextId: string;
  representationId: string;
  sourceDiagramElementId: string;
  targetDiagramElementId: string;
}

export interface GetConnectorToolsData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext | null;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata | null;
}

export interface GQLRepresentationMetadata {
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  __typename: string;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  connectorTools: GQLConnectorTool[];
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

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolPayload {
  __typename: string;
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

export interface GQLErrorPayload extends GQLInvokeSingleClickOnTwoDiagramElementsToolPayload {
  messages: GQLMessage[];
}

export interface GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload
  extends GQLInvokeSingleClickOnTwoDiagramElementsToolPayload {
  messages: GQLMessage[];
  newSelection: GQLWorkbenchSelection;
}
