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

import { Connection, OnConnect, OnConnectEnd, OnConnectStart, XYPosition } from '@xyflow/react';
import { GQLTool } from '../palette/Palette.types';

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
  toolCandidates: GQLConnectorTool[];
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
  connectorTools: GQLConnectorTool[];
}

export interface GQLConnectorTool {
  id: string;
  label: string;
  iconURL: string[];
  dialogDescriptionId: string;
  candidateDescriptionIds: string[];
  __typename: string;
}

export interface GQLGetToolSectionsData {
  viewer: GQLViewer;
}
export interface GQLGetToolSectionsVariables {
  editingContextId: string;
  diagramId: string;
  diagramElementId: string;
}

export interface GQLPalette {
  id: string;
  paletteEntries: GQLPaletteEntry[];
}

export interface GQLPaletteEntry {
  id: string;
  __typename: string;
}
export interface GQLPaletteDivider extends GQLPaletteEntry {}

export interface GQLToolSection extends GQLPaletteEntry {
  label: string;
  iconURL: string[];
  tools: GQLTool[];
}

export interface GQLToolSection {
  id: string;
  label: string;
  tools: GQLTool[];
  __typename: string;
}
export interface GQLSingleClickOnTwoDiagramElementsTool extends GQLTool {
  candidates: GQLSingleClickOnTwoDiagramElementsCandidate[];
  dialogDescriptionId: string;
}
export interface GQLSingleClickOnTwoDiagramElementsCandidate {
  sources: GQLDiagramElementDescription[];
  targets: GQLDiagramElementDescription[];
}

export interface GQLEdgeDescription {
  id: string;
}

export type GQLDiagramElementDescription = GQLNodeDescription | GQLEdgeDescription;

export interface GQLNodeDescription {
  id: string;
}
