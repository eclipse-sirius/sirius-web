/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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

export interface GQLGetDiagramDescriptionVariables {
  editingContextId: string;
  diagramId: string;
}

export interface GQLGetDiagramDescriptionData {
  viewer: GQLGetDiagramDescriptionViewer | null;
}

export interface GQLGetDiagramDescriptionViewer {
  editingContext: GQLGetDiagramDescriptionEditingContext | null;
}

export interface GQLGetDiagramDescriptionEditingContext {
  representation: GQLGetDiagramDescriptionRepresentationMetadata | null;
}

export interface GQLGetDiagramDescriptionRepresentationMetadata {
  description: GQLGetDiagramDescriptionRepresentationDescription;
}

export interface GQLGetDiagramDescriptionRepresentationDescription {
  __typename: string;
}

export interface GQLGetDiagramDescriptionDiagramDescription extends GQLGetDiagramDescriptionRepresentationDescription {
  autoLayout: boolean;
}
