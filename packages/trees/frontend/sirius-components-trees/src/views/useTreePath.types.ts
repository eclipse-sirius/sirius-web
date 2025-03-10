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

export interface UseTreePathValue {
  getTreePath: LazyQueryExecFunction<GQLGetTreePathData, GQLGetTreePathVariables>;
  loading: boolean;
  data: GQLGetTreePathData | null;
}

export interface GQLGetTreePathVariables {
  editingContextId: string;
  treeId: string;
  selectionEntryIds: string[];
}

export interface GQLGetTreePathData {
  viewer: GQLGetTreePathViewer;
}

export interface GQLGetTreePathViewer {
  editingContext: GQLGetTreePathEditingContext;
}

export interface GQLGetTreePathEditingContext {
  treePath: GQLTreePath;
}

export interface GQLTreePath {
  treeItemIdsToExpand: string[];
  maxDepth: number;
}
