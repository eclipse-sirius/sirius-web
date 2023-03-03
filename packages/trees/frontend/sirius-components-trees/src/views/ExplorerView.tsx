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
import { WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import { Close as CloseIcon } from '@material-ui/icons';
import { useMachine } from '@xstate/react';
import { useEffect } from 'react';

import { TreeToolBar } from '../toolbar/TreeToolBar';
import { Tree } from '../trees/Tree';
import {
  GQLExplorerEventData,
  GQLExplorerEventVariables,
  GQLGetTreePathData,
  GQLGetTreePathVariables,
} from './ExplorerView.types';
import {
  ExplorerViewContext,
  ExplorerViewEvent,
  explorerViewMachine,
  HandleCompleteEvent,
  HandleExpandedEvent,
  HandleSubscriptionResultEvent,
  HandleTreePathEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  SynchronizeSelectionEvent,
  SynchronizeWithRepresentationEvent,
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
  const { id, tree, expanded, maxDepth, synchronizedSelection, synchronizedWithRepresentation, message } = context;

  const [getTreePath, { loading: treePathLoading, data: treePathData, error: treePathError }] = useLazyQuery<
    GQLGetTreePathData,
    GQLGetTreePathVariables
  >(getTreePathQuery);

  useEffect(() => {
    if (tree && synchronizedSelection) {
      const variables: GQLGetTreePathVariables = {
        editingContextId,
        treeId: tree.id,
        selectionEntryIds: synchronizedSelection ? selection.entries.map((entry) => entry.id) : [],
      };
      getTreePath({
        variables,
      });
    }
  }, [editingContextId, tree, selection, synchronizedSelection, getTreePath]);

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

  const { error } = useSubscription<GQLExplorerEventData, GQLExplorerEventVariables>(
    gql(getTreeEventSubscription(maxDepth)),
    {
      variables: {
        input: {
          id,
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
    if (synchronizedWithRepresentation) {
      const synchronizeSelectionEvent: SynchronizeSelectionEvent = {
        type: 'SYNCHRONIZE_SELECTION',
        synchronizedSelection: true,
      };
      dispatch(synchronizeSelectionEvent);
    }
  }, [selection]);

  const onExpand = (id: string, depth: number) => {
    const handleExpandedEvent: HandleExpandedEvent = { type: 'HANDLE_EXPANDED', id, depth };
    dispatch(handleExpandedEvent);
  };

  const onSynchronizedClick = () => {
    const synchronizeWithRepresentationEvent: SynchronizeWithRepresentationEvent = {
      type: 'SYNCHRONISE_WITH_REPRESENTATION',
      synchronizedWithRepresentation: !synchronizedWithRepresentation,
    };
    dispatch(synchronizeWithRepresentationEvent);
  };
  return (
    <div className={styles.explorerView}>
      <TreeToolBar
        editingContextId={editingContextId}
        onSynchronizedClick={onSynchronizedClick}
        synchronized={synchronizedWithRepresentation}
        readOnly={readOnly}></TreeToolBar>
      <div className={styles.explorerTree} data-testid="explorerTree">
        {tree ? (
          <Tree
            editingContextId={editingContextId}
            tree={tree}
            onExpand={onExpand}
            selection={selection}
            setSelection={setSelection}
            readOnly={readOnly}
          />
        ) : null}
      </div>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </div>
  );
};
