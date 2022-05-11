/*******************************************************************************
 * Copyright (c) 2022 Obeo and others.
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

import { Tool } from 'diagram/DiagramWebSocketContainer.types';
import { SModelElement } from 'sprotty';

export interface ContextualMenuProps {
  editingContextId: string;
  representationId: string;
  sourceDiagramElement: SModelElement;
  targetDiagramElement: SModelElement;
  invokeTool: (tool: Tool) => void;
  invokeClose: () => void;
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

export interface GQLGetConnectorToolsData {
  viewer: GQLViewer;
}

export interface GQLRepresentationDescription {
  id: string;
  __typename: string;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  connectorTools: GQLTool[];
}

export interface GQLTool {
  id: string;
  label: string;
  imageURL: string;
  __typename: string;
}

export interface GQLGetConnectorToolsVariables {
  editingContextId: string;
  representationId: string;
  sourceDiagramElementId: string;
  targetDiagramElementId: string;
}
