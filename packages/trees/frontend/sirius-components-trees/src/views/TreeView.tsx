/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { gql, useLazyQuery, useSubscription } from '@apollo/client';
import { DataExtension, Toast, useData, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';
import { Tree } from '../trees/Tree';
import {
  GQLGetExpandAllTreePathData,
  GQLGetExpandAllTreePathVariables,
  GQLGetTreePathData,
  GQLGetTreePathVariables,
  GQLTree,
  GQLTreeEventData,
  GQLTreeEventVariables,
  GQLTreeItem,
  TreeConverter,
  TreeViewComponentProps,
} from './TreeView.types';
import { treeViewTreeConverterExtensionPoint } from './TreeViewExtensionPoints';
import {
  AutoExpandToRevealSelectionEvent,
  HandleCompleteEvent,
  HandleExpandAllTreePathEvent,
  HandleExpandedEvent,
  HandleOnExpandAllEvent,
  HandleSubscriptionResultEvent,
  HandleTreePathEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  SynchronizeWithSelectionEvent,
  TreeViewContext,
  TreeViewEvent,
  treeViewMachine,
} from './TreeViewMachine';
import { getTreeEventSubscription } from './getTreeEventSubscription';

const getTreePathQuery = gql`
  query getTreePath($editingContextId: ID!, $treeId: ID!, $selectionEntryIds: [ID!]!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        treePath(treeId: $treeId, selectionEntryIds: $selectionEntryIds) {
          treeItemIdsToExpand
          maxDepth
        }
      }
    }
  }
`;

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

export const TreeView = ({
  editingContextId,
  readOnly,
  treeId,
  enableMultiSelection,
  synchronizedWithSelection,
  activeFilterIds,
  textToHighlight,
  textToFilter,
  markedItemIds = [],
}: TreeViewComponentProps) => {
  const [{ value, context }, dispatch] = useMachine<TreeViewContext, TreeViewEvent>(treeViewMachine, {
    context: {
      synchronizedWithSelection: synchronizedWithSelection,
    },
  });
  const { selection } = useSelection();

  const { toast, treeView } = value as SchemaValue;
  const { id, tree, expanded, maxDepth, autoExpandToRevealSelection, treeItemToExpandAll, message } = context;

  const [getTreePath, { loading: treePathLoading, data: treePathData, error: treePathError }] = useLazyQuery<
    GQLGetTreePathData,
    GQLGetTreePathVariables
  >(getTreePathQuery);

  const [
    getExpandAllTreePath,
    { loading: expandAllTreePathLoading, data: expandAllTreePathData, error: expandAllTreePathError },
  ] = useLazyQuery<GQLGetExpandAllTreePathData, GQLGetExpandAllTreePathVariables>(getExpandAllTreePathQuery);

  // If we should auto-expand to reveal the selection, we need to compute the tree path to expand
  const selectionKey: string = selection?.entries
    .map((entry) => entry.id)
    .sort()
    .join(':');
  useEffect(() => {
    if (tree && autoExpandToRevealSelection) {
      const variables: GQLGetTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        selectionEntryIds: selection.entries.map((entry) => entry.id),
      };
      getTreePath({ variables });
    }
  }, [editingContextId, tree, selectionKey, autoExpandToRevealSelection, getTreePath]);

  useEffect(() => {
    if (!treePathLoading) {
      if (treePathData) {
        const handleTreePathEvent: HandleTreePathEvent = { type: 'HANDLE_TREE_PATH', treePathData };
        dispatch(handleTreePathEvent);
      }
      if (treePathError) {
        const { message } = treePathError;
        const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
        dispatch(showToastEvent);
      }
    }
  }, [treePathLoading, treePathData, treePathError]);

  useEffect(() => {
    if (tree && treeItemToExpandAll) {
      const variables: GQLGetExpandAllTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        treeItemId: treeItemToExpandAll,
      };
      getExpandAllTreePath({ variables });
    }
  }, [editingContextId, tree, treeItemToExpandAll, getExpandAllTreePathQuery]);

  useEffect(() => {
    if (!expandAllTreePathLoading) {
      if (expandAllTreePathData) {
        const handleExpandAllTreePathEvent: HandleExpandAllTreePathEvent = {
          type: 'HANDLE_EXPAND_ALL_TREE_PATH',
          expandAllTreePathData,
        };
        dispatch(handleExpandAllTreePathEvent);
      }
      if (expandAllTreePathError) {
        const { message } = expandAllTreePathError;
        const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
        dispatch(showToastEvent);
      }
    }
  }, [expandAllTreePathLoading, expandAllTreePathData, expandAllTreePathError]);

  const { error } = useSubscription<GQLTreeEventData, GQLTreeEventVariables>(gql(getTreeEventSubscription(maxDepth)), {
    variables: {
      input: {
        id,
        treeId,
        editingContextId,
        expanded,
        activeFilterIds,
      },
    },
    fetchPolicy: 'no-cache',
    skip: treeView === 'complete',
    onData: ({ data }) => {
      const handleDataEvent: HandleSubscriptionResultEvent = {
        type: 'HANDLE_SUBSCRIPTION_RESULT',
        result: data,
      };
      dispatch(handleDataEvent);
    },
    onComplete: () => {
      const completeEvent: HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
      dispatch(completeEvent);
    },
  });
  useEffect(() => {
    if (error) {
      const { message } = error;
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  }, [error, dispatch]);

  useEffect(() => {
    const autoExpandToRevealSelectionEvent: AutoExpandToRevealSelectionEvent = {
      type: 'AUTO_EXPAND_TO_REVEAL_SELECTION',
      autoExpandToRevealSelection: true,
    };
    dispatch(autoExpandToRevealSelectionEvent);
  }, [selection]);

  useEffect(() => {
    const synchronizeWithSelectionEvent: SynchronizeWithSelectionEvent = {
      type: 'SYNCHRONIZE_WITH_SELECTION',
      synchronizedWithSelection: synchronizedWithSelection,
    };
    dispatch(synchronizeWithSelectionEvent);
  }, [synchronizedWithSelection]);

  const onExpand = (id: string, depth: number) => {
    const handleExpandedEvent: HandleExpandedEvent = { type: 'HANDLE_EXPANDED', id, depth };
    dispatch(handleExpandedEvent);
  };

  const onExpandAll = (treeItem: GQLTreeItem) => {
    const handleOnExpandAllEvent: HandleOnExpandAllEvent = { type: 'HANDLE_ON_EXPAND_ALL', treeItemId: treeItem.id };
    dispatch(handleOnExpandAllEvent);
  };

  const { data: treeConverters }: DataExtension<TreeConverter[]> = useData(treeViewTreeConverterExtensionPoint);

  let convertedTree: GQLTree = tree;
  treeConverters.forEach((treeConverter) => {
    convertedTree = treeConverter.convert(editingContextId, convertedTree);
  });

  return (
    <>
      <div data-testid={treeId}>
        {tree ? (
          <Tree
            editingContextId={editingContextId}
            tree={convertedTree}
            onExpand={onExpand}
            onExpandAll={onExpandAll}
            readOnly={readOnly}
            enableMultiSelection={enableMultiSelection}
            markedItemIds={markedItemIds}
            textToFilter={textToFilter}
            textToHighlight={textToHighlight}
          />
        ) : null}
      </div>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
