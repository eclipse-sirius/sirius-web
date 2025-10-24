/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

export interface UseContextMenuEntriesValue {
  contextMenuEntries: GQLTreeItemContextMenuEntry[];
  loading: boolean;
}

export interface GQLGetAllContextMenuEntriesVariables {
  editingContextId: string;
  representationId: string;
  treeItemId: string;
}

export interface GQLGetAllContextMenuEntriesData {
  viewer: GQLGetAllContextMenuEntriesViewer;
}

export interface GQLGetAllContextMenuEntriesViewer {
  editingContext: GQLGetAllContextMenuEntriesEditingContext;
}

export interface GQLGetAllContextMenuEntriesEditingContext {
  representation: GQLGetAllContextMenuEntriesRepresentationMetadata;
}

export interface GQLGetAllContextMenuEntriesRepresentationMetadata {
  description: GQLGetAllContextMenuEntriesRepresentationDescription;
}

export interface GQLGetAllContextMenuEntriesRepresentationDescription {
  contextMenu: GQLTreeItemContextMenuEntry[];
}

export interface GQLTreeItemContextMenuEntry {
  __typename: string;
  id: string;
  label: string;
  iconURL: string[];
  withImpactAnalysis: boolean;
}
