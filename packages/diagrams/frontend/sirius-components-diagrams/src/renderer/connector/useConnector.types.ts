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

import { Connection, OnConnect, OnConnectEnd, OnConnectStart, XYPosition } from 'reactflow';

export interface UseConnectorValue {
  onConnect: OnConnect;
  onConnectStart: OnConnectStart;
  onConnectEnd: OnConnectEnd;
  onConnectorContextualMenuClose: () => void;
  onConnectionStartElementClick: (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void;
  addTempConnectionLine: () => void;
  removeTempConnectionLine: () => void;
  connection: Connection | null;
  position: XYPosition | null;
  isConnectionInProgress: () => boolean;
  isReconnectionInProgress: () => boolean;
  candidates: GQLNodeDescription[];
}

export interface GQLGetToolSectionsData {
  viewer: GQLViewer;
}
export interface GQLGetToolSectionsVariables {
  editingContextId: string;
  diagramId: string;
  diagramElementId: string;
}
export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
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
  imageURL: string;
  tools: GQLTool[];
  __typename: string;
}

export interface GQLTool {
  id: string;
  label: string;
  imageURL: string;
  __typename: string;
}
export interface GQLSingleClickOnTwoDiagramElementsTool extends GQLTool {
  candidates: GQLSingleClickOnTwoDiagramElementsCandidate[];
}
export interface GQLSingleClickOnTwoDiagramElementsCandidate {
  sources: GQLNodeDescription[];
  targets: GQLNodeDescription[];
}

export interface GQLNodeDescription {
  id: string;
}
