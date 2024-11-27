/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { GQLMessage } from './../Tool.types';
import { GQLPalette, GQLTool } from './Palette.types';
export interface UsePaletteProps {
  x: number;
  y: number;
  diagramElementId: string;
  targetObjectId: string;
  onDirectEditClick: () => void;
}

export interface UsePaletteValue {
  handleToolClick: (tool: GQLTool) => void;
  palette: GQLPalette | null;
}
export interface GQLGetToolSectionsVariables {
  editingContextId: string;
  diagramId: string;
  diagramElementId: string;
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

export interface GQLDeleteFromDiagramSuccessPayload extends GQLDeleteFromDiagramPayload {
  messages: GQLMessage[];
}

export interface GQLDeleteFromDiagramVariables {
  input: GQLDeleteFromDiagramInput;
}

export interface GQLDeleteFromDiagramInput {
  id: string;
  editingContextId: string;
  representationId: string;
  nodeIds: string[];
  edgeIds: string[];
  deletionPolicy: GQLDeletionPolicy;
}

export interface GQLDeleteFromDiagramData {
  deleteFromDiagram: GQLDeleteFromDiagramPayload;
}

export interface GQLDeleteFromDiagramPayload {
  __typename: string;
}

export enum GQLDeletionPolicy {
  SEMANTIC = 'SEMANTIC',
  GRAPHICAL = 'GRAPHICAL',
}

export interface GQLErrorPayload
  extends GQLInvokeSingleClickOnDiagramElementToolPayload,
    GQLDeleteFromDiagramPayload,
    GQLUpdateCollapsingStatePayload {
  message: string;
  messages: GQLMessage[];
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
  label: string;
  kind: string;
}

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

export interface GQLToolVariable {
  name: string;
  value: string;
  type: GQLToolVariableType;
}

export type GQLToolVariableType = 'STRING' | 'OBJECT_ID' | 'OBJECT_ID_ARRAY';

export interface GQLGetToolSectionsData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
  description: GQLRepresentationDescription;
}

export interface GQLRepresentationDescription {
  id: string;
  __typename: string;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  palette: GQLPalette;
}
