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
import { GQLTreePath } from './useTreePath.types';

export interface UseExpandAllTreePathValue {
  getExpandAllTreePath: LazyQueryExecFunction<GQLGetExpandAllTreePathData, GQLGetExpandAllTreePathVariables>;
  loading: boolean;
  data: GQLGetExpandAllTreePathData | null;
}

export interface GQLGetExpandAllTreePathVariables {
  editingContextId: string;
  treeId: string;
  treeItemId: string;
}

export interface GQLGetExpandAllTreePathData {
  viewer: GQLGetExpandAllTreePathViewer;
}

export interface GQLGetExpandAllTreePathViewer {
  editingContext: GQLGetExpandAllTreePathEditingContext;
}

export interface GQLGetExpandAllTreePathEditingContext {
  expandAllTreePath: GQLTreePath;
}
