/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

export interface UseManageVisibilityActionsProps {
  diagramElementId: string;
}

export interface UseManageVisibilityActionsValue {
  actions: GQLManageVisibilityAction[];
}

export interface GQLGetManageVisibilityActionsVariables {
  editingContextId: string;
  diagramId: string;
  diagramElementId: string;
}

export interface GQLGetManageVisibilityActionsData {
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
  manageVisibilityActions: GQLManageVisibilityAction[];
}

export interface GQLManageVisibilityAction {
  id: string;
  label: string;
}
