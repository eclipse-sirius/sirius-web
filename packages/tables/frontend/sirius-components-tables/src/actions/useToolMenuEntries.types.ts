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

export interface UseToolMenuEntriesValue {
  toolMenuEntries: GQLTableToolMenuEntry[];
  loading: boolean;
}

export interface GQLGetAllToolMenuEntriesVariables {
  editingContextId: string;
  representationId: string;
  tableId: string;
}

export interface GQLGetAllToolMenuEntriesData {
  viewer: GQLGetAllToolMenuEntriesViewer;
}

export interface GQLGetAllToolMenuEntriesViewer {
  editingContext: GQLGetAllToolMenuEntriesEditingContext;
}

export interface GQLGetAllToolMenuEntriesEditingContext {
  representation: GQLGetAllToolMenuEntriesRepresentationMetadata;
}

export interface GQLGetAllToolMenuEntriesRepresentationMetadata {
  description: GQLGetAllToolMenuEntriesRepresentationDescription;
}

export interface GQLGetAllToolMenuEntriesRepresentationDescription {
  toolMenuEntries: GQLTableToolMenuEntry[];
}

export interface GQLTableToolMenuEntry {
  __typename: string;
  id: string;
  label: string;
  iconURLs: string[];
}
