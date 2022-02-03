/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo and others.
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
import { GQLDeletionPolicy } from 'diagram/DiagramWebSocketContainer.types';
import { SModelElement } from 'sprotty';

export interface ContextualPaletteProps {
  editingContextId: string;
  representationId: string;
  diagramElement: SModelElement;
  invokeTool: (tool: GQLTool) => void;
  invokeConnectorTool: (toolSections: GQLToolSection[]) => void;
  invokeLabelEdit: () => void | null;
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

export interface GQLCreateNodeTool extends GQLTool {
  appliesToDiagramRoot: boolean;
  selectionDescriptionId: string;
}

export interface GQLCreateEdgeTool extends GQLTool {
  edgeCandidates: GQLEdgeCandidate[];
}

export interface GQLEdgeCandidate {
  sources: GQLNodeDescription[];
  targets: GQLNodeDescription[];
}

export interface GQLNodeDescription {
  id: string;
}
