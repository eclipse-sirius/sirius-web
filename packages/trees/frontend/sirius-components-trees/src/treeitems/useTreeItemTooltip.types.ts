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
export interface UseTreeItemTooltipValue {
  tooltip: string;
  loading: boolean;
}

export interface GQLGetTreeItemTooltipVariables {
  editingContextId: string;
  representationId: string;
  treeItemId: string;
}

export interface GQLGetTreeItemTooltipData {
  viewer: GQLGetTreeItemTooltipViewer;
}

export interface GQLGetTreeItemTooltipViewer {
  editingContext: GQLGetTreeItemTooltipEditingContext;
}

export interface GQLGetTreeItemTooltipEditingContext {
  representation: GQLGetTreeItemTooltipRepresentationMetadata;
}

export interface GQLGetTreeItemTooltipRepresentationMetadata {
  description: GQLGetTreeItemTooltipRepresentationDescription;
}

export interface GQLGetTreeItemTooltipRepresentationDescription {
  treeItemTooltip: string;
}
