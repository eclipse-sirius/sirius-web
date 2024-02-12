/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

export interface UseTreeFilterValue {
  treeFilters: GQLTreeFilter[];
  loading: boolean;
}

export interface GQLGetAllTreeFiltersVariables {
  editingContextId: string;
  treeId: string;
}

export interface GQLGetAllTreeFiltersData {
  viewer: GQLGetAllTreeFiltersViewer;
}

export interface GQLGetAllTreeFiltersViewer {
  editingContext: GQLGetAllTreeFiltersEditingContext;
}

export interface GQLGetAllTreeFiltersEditingContext {
  representation: GQLGetAllTreeFiltersRepresentation;
}

export interface GQLGetAllTreeFiltersRepresentation {
  description: GQLTreeDescription;
}

export interface GQLTreeDescription {
  id: string;
  filters: GQLTreeFilter[];
}

export interface GQLTreeFilter {
  id: string;
  label: string;
  defaultState: boolean;
}
