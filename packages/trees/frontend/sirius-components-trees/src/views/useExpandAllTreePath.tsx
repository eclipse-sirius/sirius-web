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
import { gql, useLazyQuery } from '@apollo/client';
import { GQLGetExpandAllTreePathData, GQLGetExpandAllTreePathVariables } from './TreeView.types';
import { useEffect } from 'react';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { UseExpandAllTreePathValue } from './useExpandAllTreePath.types';

const getExpandAllTreePathQuery = gql`
  query getExpandAllTreePath($editingContextId: ID!, $treeId: ID!, $treeItemId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        expandAllTreePath(treeId: $treeId, treeItemId: $treeItemId) {
          treeItemIdsToExpand
          maxDepth
        }
      }
    }
  }
`;

export const useExpandAllTreePath = (expanded: string[], maxDepth: number): UseExpandAllTreePathValue => {
  const [getExpandAllTreePath, { loading, data, error }] = useLazyQuery<
    GQLGetExpandAllTreePathData,
    GQLGetExpandAllTreePathVariables
  >(getExpandAllTreePathQuery);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  const treePath = data?.viewer.editingContext.expandAllTreePath;

  let newExpanded: string[] = [];
  let newMaxDepth = -1;

  if (!loading) {
    if (treePath) {
      newExpanded = [...expanded];
      treePath.treeItemIdsToExpand.forEach((itemToExpand) => {
        if (!expanded.includes(itemToExpand)) {
          newExpanded.push(itemToExpand);
        }
      });
      newMaxDepth = Math.max(treePath.maxDepth, maxDepth);
    }
  }

  return {
    getExpandAllTreePath,
    expanded: newExpanded,
    maxDepth: newMaxDepth,
    loading,
  };
};
