/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import { Toast, WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';

import { TreeToolBar } from '../toolbar/TreeToolBar';
import { Tree } from '../trees/Tree';
import {
  GQLExplorerEventData,
  GQLExplorerEventVariables,
  GQLGetExpandAllTreePathData,
  GQLGetExpandAllTreePathVariables,
  GQLGetTreePathData,
  GQLGetTreePathVariables,
  GQLTreeItem,
} from './ExplorerView.types';
import {
  AutoExpandToRevealSelectionEvent,
  ExplorerViewContext,
  ExplorerViewEvent,
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
  explorerViewMachine,
} from './ExplorerViewMachine';
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

const useExplorerViewStyles = makeStyles((theme) => ({
  explorerView: {
    flexGrow: 1,
    display: 'flex',
    flexDirection: 'column',
  },
  explorerTree: {
    paddingTop: theme.spacing(1),
    flexGrow: 1,
    overflow: 'auto',
  },
}));

export const ExplorerView = ({ editingContextId, selection, setSelection, readOnly }: WorkbenchViewComponentProps) => {
  const styles = useExplorerViewStyles();

  const [{ value, context }, dispatch] = useMachine<ExplorerViewContext, ExplorerViewEvent>(explorerViewMachine);
  const { toast, explorerView } = value as SchemaValue;
  const {
    id,
    tree,
    expanded,
    maxDepth,
    autoExpandToRevealSelection,
    treeItemToExpandAll,
    synchronizedWithSelection,
    message,
  } = context;

  const [getTreePath, { loading: treePathLoading, data: treePathData, error: treePathError }] = useLazyQuery<
    GQLGetTreePathData,
    GQLGetTreePathVariables
  >(getTreePathQuery);

  const [
    getExpandAllTreePath,
    { loading: expandAllTreePathLoading, data: expandAllTreePathData, error: expandAllTreePathError },
  ] = useLazyQuery<GQLGetExpandAllTreePathData, GQLGetExpandAllTreePathVariables>(getExpandAllTreePathQuery);

  // If we should auto-expand to reveal the selection, we need to compute the tree path to expand
  useEffect(() => {
    if (tree && autoExpandToRevealSelection) {
      const variables: GQLGetTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        selectionEntryIds: selection.entries.map((entry) => entry.id),
      };
      getTreePath({ variables });
    }
  }, [editingContextId, tree, selection, autoExpandToRevealSelection, getTreePath]);

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

  const { error } = useSubscription<GQLExplorerEventData, GQLExplorerEventVariables>(
    gql(getTreeEventSubscription(maxDepth)),
    {
      variables: {
        input: {
          id,
          treeId: 'explorer',
          editingContextId,
          expanded,
        },
      },
      fetchPolicy: 'no-cache',
      skip: explorerView === 'complete',
      onSubscriptionData: ({ subscriptionData }) => {
        const handleDataEvent: HandleSubscriptionResultEvent = {
          type: 'HANDLE_SUBSCRIPTION_RESULT',
          result: subscriptionData,
        };
        dispatch(handleDataEvent);
      },
      onSubscriptionComplete: () => {
        const completeEvent: HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
        dispatch(completeEvent);
      },
    }
  );
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

  const onExpand = (id: string, depth: number) => {
    const handleExpandedEvent: HandleExpandedEvent = { type: 'HANDLE_EXPANDED', id, depth };
    dispatch(handleExpandedEvent);
  };

  const onExpandAll = (treeItem: GQLTreeItem) => {
    const handleOnExpandAllEvent: HandleOnExpandAllEvent = { type: 'HANDLE_ON_EXPAND_ALL', treeItemId: treeItem.id };
    dispatch(handleOnExpandAllEvent);
  };

  const onSynchronizedClick = () => {
    const synchronizeWithSelectionEvent: SynchronizeWithSelectionEvent = {
      type: 'SYNCHRONIZE_WITH_SELECTION',
      synchronizedWithSelection: !synchronizedWithSelection,
    };
    dispatch(synchronizeWithSelectionEvent);
  };
  return (
    <div className={styles.explorerView}>
      <TreeToolBar
        editingContextId={editingContextId}
        onSynchronizedClick={onSynchronizedClick}
        synchronized={synchronizedWithSelection}
        readOnly={readOnly}
      />
      <div className={styles.explorerTree} data-testid="explorerTree">
        {tree ? (
          <Tree
            editingContextId={editingContextId}
            tree={tree}
            onExpand={onExpand}
            onExpandAll={onExpandAll}
            selection={selection}
            setSelection={setSelection}
            readOnly={readOnly}
          />
        ) : null}
      </div>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </div>
  );
};
