/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

export interface ContextualPaletteStyleProps {
  toolCount: number;
}

export interface PaletteProps {
  diagramElementId: string;
  onDirectEditClick: () => void;
  isDiagramElementPalette: boolean;
}

export interface GQLErrorPayload
  extends GQLInvokeSingleClickOnDiagramElementToolPayload,
    GQLDeleteFromDiagramPayload,
    GQLUpdateCollapsingStatePayload {
  message: string;
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
  selectedObjectId: string | null;
}

export interface GQLTool {
  id: string;
  label: string;
  iconURL: string[];
  __typename: string;
}

export interface GQLSingleClickOnDiagramElementTool extends GQLTool {
  appliesToDiagramRoot: boolean;
  selectionDescriptionId: string;
}

export interface GQLGetToolSectionsVariables {
  editingContextId: string;
  diagramId: string;
  diagramElementId: string;
}

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

export interface GQLPalette {
  id: string;
  tools: GQLTool[];
  toolSections: GQLToolSection[];
}

export interface GQLToolSection {
  id: string;
  label: string;
  iconURL: string[];
  tools: GQLTool[];
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
  collapseExpandDiagramElement: GQLUpdateCollapsingStatePayload;
}

export interface GQLUpdateCollapsingStatePayload {
  __typename: string;
}
