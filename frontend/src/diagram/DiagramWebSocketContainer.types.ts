/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
export interface Subscriber {
  username: string;
}

export interface Bounds {
  x: number;
  y: number;
  width: number;
  height: number;
}

export interface Position {
  x: number;
  y: number;
}

export interface Palette {
  startingPosition: Position;
  canvasBounds: Bounds;
  origin: Position;
  element: any;
  renameable: boolean;
  deletable: boolean;
}

export interface ToolSection {
  id: string;
  label: string;
  imageURL: string;
  tools: Tool[];
  defaultTool: Tool | null;
}

export interface Tool {
  id: string;
  label: string;
  imageURL: string;
}

export interface CreateNodeTool extends Tool {
  appliesToDiagramRoot: boolean;
  selectionDescriptionId: string;
}

export interface CreateEdgeTool extends Tool {
  edgeCandidates: EdgeCandidate[];
}

export interface EdgeCandidate {
  sources: NodeDescription[];
  targets: NodeDescription[];
}

export interface NodeDescription {
  id: string;
}

export interface GQLGetToolSectionsVariables {}

export interface GQLGetToolSectionsData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentation;
}

export interface GQLRepresentation {
  __typename: string;
}
export interface GQLDiagram extends GQLRepresentation {
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
}
