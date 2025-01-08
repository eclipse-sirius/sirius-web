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
import { GQLGetExpandAllTreePathData, GQLGetExpandAllTreePathVariables } from './TreeView.types';

export interface UseExpandAllValue {
  getExpandAllTreePath: LazyQueryExecFunction<GQLGetExpandAllTreePathData, GQLGetExpandAllTreePathVariables>;
  expanded: string[] | null;
  maxDepth: number;
  loading: boolean;
}

export interface UseExpandAllState {
  expanded: string[] | null;
  maxDepth: number;
}
