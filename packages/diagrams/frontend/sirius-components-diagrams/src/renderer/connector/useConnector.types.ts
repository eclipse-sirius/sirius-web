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

import { OnConnect, OnConnectEnd, OnConnectStart } from '@xyflow/react';

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
