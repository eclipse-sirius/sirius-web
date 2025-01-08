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
import { useEffect, useState } from 'react';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { UseExpandAllState, UseExpandAllValue } from './useExpandAll.types';

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

export const useExpandAll = (expanded: string[], maxDepth: number): UseExpandAllValue => {
  const [getExpandAllTreePath, { loading, data, error }] = useLazyQuery<
    GQLGetExpandAllTreePathData,
    GQLGetExpandAllTreePathVariables
  >(getExpandAllTreePathQuery);

  const [state, setState] = useState<UseExpandAllState>({ expanded: null, maxDepth: -1 });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  const treePath = data?.viewer.editingContext.expandAllTreePath;

  useEffect(() => {
    if (!loading) {
      if (treePath) {
        const newExpanded: string[] = [...expanded];
        treePath.treeItemIdsToExpand.forEach((itemToExpand) => {
          if (!expanded.includes(itemToExpand)) {
            newExpanded.push(itemToExpand);
          }
        });
        setState({
          expanded: newExpanded,
          maxDepth: Math.max(treePath.maxDepth, maxDepth),
        });
      }
    }
  }, [treePath, loading]);

  return {
    getExpandAllTreePath,
    expanded: state.expanded,
    maxDepth: state.maxDepth,
    loading,
  };
};
