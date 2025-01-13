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
import { LazyQueryExecFunction } from '@apollo/client';

export interface UseRowContextMenuEntriesValue {
  callRowContextMenuEntriesQuery: LazyQueryExecFunction<
    GQLGetAllRowContextMenuEntriesData,
    GQLGetAllRowContextMenuEntriesVariables
  >;
  loading: boolean;
  data: GQLGetAllRowContextMenuEntriesData | null;
}

export interface GQLGetAllRowContextMenuEntriesVariables {
  editingContextId: string;
  representationId: string;
  tableId: string;
  rowId: string;
}

export interface GQLGetAllRowContextMenuEntriesData {
  viewer: GQLGetAllRowContextMenuEntriesViewer;
}

export interface GQLGetAllRowContextMenuEntriesViewer {
  editingContext: GQLGetAllRowContextMenuEntriesEditingContext;
}

export interface GQLGetAllRowContextMenuEntriesEditingContext {
  representation: GQLGetAllRowContextMenuEntriesRepresentationMetadata;
}

export interface GQLGetAllRowContextMenuEntriesRepresentationMetadata {
  description: GQLGetAllRowContextMenuEntriesRepresentationDescription;
}

export interface GQLGetAllRowContextMenuEntriesRepresentationDescription {
  contextMenu: GQLRowContextMenuEntry[];
}

export interface GQLRowContextMenuEntry {
  __typename: string;
  id: string;
  label: string;
  iconURLs: string[];
}
