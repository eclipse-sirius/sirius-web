/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

export interface UseFilterContentsValue {
  filterSelectionMenuItems: GQLFilterSelectionMenuItem[];
  fetchFilterMenuItems: (diagramElementIds: string[]) => void;
  loading: boolean;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  toolbar: GQLDiagramToolbar | null;
}

export interface GQLRepresentationDescription {
  __typename: string;
}

export interface GQLFilterContentsVariables {
  editingContextId: string;
  representationId: string;
  diagramElementIds: string[];
}

export interface GQLFilterContentsData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext | null;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata | null;
}

export interface GQLRepresentationMetadata {
  description: GQLRepresentationDescription;
}

export interface GQLDiagramToolbar {
  filterSelectionMenuItems: GQLFilterSelectionMenuItem[];
}

export interface GQLFilterSelectionMenuItem {
  id: string;
  label: string;
}
