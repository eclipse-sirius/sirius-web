/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { OnConnect, OnConnectEnd, OnConnectStart } from '@xyflow/react';
import { GQLTool } from '../palette/Palette.types';

export interface UseConnectorValue {
  onConnectStart: OnConnectStart;
  onConnect: OnConnect;
  onConnectEnd: OnConnectEnd;
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
  connectorToolsCandidates: GQConnectorToolsDescriptionCandidates;
}

export interface GQConnectorToolsDescriptionCandidates {
  id: string;
  candidateDescriptionIds: string[];
  __typename: string;
}

export interface GQLGetToolSectionsData {
  viewer: GQLViewer;
}
export interface GQLGetToolSectionsVariables {
  editingContextId: string;
  diagramId: string;
  sourceDiagramElementId: string;
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
