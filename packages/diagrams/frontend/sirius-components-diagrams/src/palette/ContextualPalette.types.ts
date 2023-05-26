/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo and others.
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
import { SModelElement } from 'sprotty';
import { GQLDeletionPolicy, ToolSectionWithDefaultTool } from '../representation/DiagramRepresentation.types';
import { DiagramServer } from '../sprotty/DiagramServer';

export interface ContextualPaletteProps {
  editingContextId: string;
  representationId: string;
  diagramElement: SModelElement;
  diagramServer: DiagramServer;
  renameable: boolean;
  defaultTools: ToolSectionWithDefaultTool[];
  invokeTool: (tool: GQLTool, toolSection: GQLToolSection) => void;
  invokeConnectorTool: (toolSections: GQLToolSection[]) => void;
  invokeDelete: (deletionPolicy: GQLDeletionPolicy) => void | null;
  invokeClose: () => void;
}

export interface ContextualPaletteStyleProps {
  toolSectionsCount: number;
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
  toolSections: GQLToolSection[];
}

export interface GQLToolSection {
  id: string;
  label: string;
  imageURL: string;
  tools: GQLTool[];
}

export interface GQLTool {
  id: string;
  label: string;
  imageURL: string;
  __typename: string;
}

export interface GQLSingleClickOnDiagramElementTool extends GQLTool {
  appliesToDiagramRoot: boolean;
  selectionDescriptionId: string;
  targetDescriptions: GQLDiagramElementDescription[];
}

export interface GQLSingleClickOnTwoDiagramElementsTool extends GQLTool {
  candidates: GQLSingleClickOnTwoDiagramElementsCandidate[];
}

export interface GQLSingleClickOnTwoDiagramElementsCandidate {
  sources: GQLNodeDescription[];
  targets: GQLNodeDescription[];
}

export interface GQLDiagramElementDescription {
  id: string;
}
export interface GQLNodeDescription {
  id: string;
}

export interface GQLHideDiagramElementInput {
  id: string;
  editingContextId: string;
  representationId: string;
  elementIds: string[];
  hide: boolean;
}

export interface GQLHideDiagramElementVariables {
  input: GQLHideDiagramElementInput;
}

export interface GQLHideDiagramElementPayload {
  __typename: string;
}
export interface GQLHideDiagramElementData {
  hideDiagramElement: GQLHideDiagramElementPayload;
}

export interface GQLFadeDiagramElementInput {
  id: string;
  editingContextId: string;
  representationId: string;
  elementIds: string[];
  fade: boolean;
}

export interface GQLFadeDiagramElementVariables {
  input: GQLFadeDiagramElementInput;
}

export interface GQLFadeDiagramElementPayload {
  __typename: string;
}
export interface GQLFadeDiagramElementData {
  fadeDiagramElement: GQLFadeDiagramElementPayload;
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

export interface GQLErrorPayload
  extends GQLFadeDiagramElementPayload,
    GQLHideDiagramElementPayload,
    GQLUpdateCollapsingStatePayload {
  message: string;
}
