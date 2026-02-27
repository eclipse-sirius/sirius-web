/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import {
  RepresentationLoadingIndicator,
  SelectionEntry,
  useSelection,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import {
  GQLTree,
  GQLTreeItem,
  TreeItemAction,
  TreeView,
  useTreeSelection,
} from '@eclipse-sirius/sirius-components-trees';
import Typography from '@mui/material/Typography';
import React, { forwardRef, useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { ViewsExplorerViewState } from './ViewsExplorerView.types';
import { useViewsExplorerViewSubscription } from './useViewsExplorerViewSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useViewsExplorerViewSubscription.types';

const useStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  treeView: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: '1fr',
    justifyItems: 'stretch',
    overflow: 'auto',
  },
  treeContent: {
    overflow: 'auto',
  },
}));

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload && payload.__typename === 'TreeRefreshedEventPayload';

export const ViewsExplorerView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ editingContextId, readOnly }: WorkbenchViewComponentProps) => {
    const { classes } = useStyles();
    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'viewsExplorerView' });

    const [state, setState] = useState<ViewsExplorerViewState>({
      expanded: [],
      tree: null,
      selectedTreeItemIds: [],
    });

    const { payload } = useViewsExplorerViewSubscription(editingContextId, state.expanded);

    useEffect(() => {
      if (isTreeRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, tree: payload.tree }));
      }
    }, [payload]);

    const { selection, setSelection } = useSelection();
    const { treeItemClick } = useTreeSelection();

    const onExpandedElementChange = (newExpandedIds: string[]) => {
      setState((prevState) => ({
        ...prevState,
        expanded: newExpandedIds,
      }));
    };

    const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => {
      const localSelection = treeItemClick(event, tree, item, state.selectedTreeItemIds, true);
      setState((prevState) => ({
        ...prevState,
        selectedTreeItemIds: localSelection.selectedTreeItemIds,
      }));
      const globalSelection = treeItemClick(
        event,
        state.tree,
        item,
        selection.entries.map((entry) => entry.id),
        true
      );
      setSelection({ entries: globalSelection.selectedTreeItemIds.map<SelectionEntry>((id) => ({ id })) });
    };

    if (!state.tree) {
      return (
        <div className={classes.treeView}>
          <RepresentationLoadingIndicator />
        </div>
      );
    }

    return (
      <div className={classes.treeView}>
        {state.tree.children.length === 0 ? (
          <div className={classes.idle}>
            <Typography variant="subtitle2">{t('noRepresentation')}</Typography>
          </div>
        ) : (
          <div className={classes.treeContent}>
            <TreeView
              editingContextId={editingContextId}
              readOnly={readOnly}
              tree={state.tree}
              textToHighlight=""
              textToFilter={null}
              onExpandedElementChange={onExpandedElementChange}
              expanded={state.expanded}
              maxDepth={1}
              treeItemActionRender={(props) => {
                if (
                  props.item.kind == 'siriusWeb://representationKind' ||
                  props.item.kind == 'siriusWeb://representationDescriptionType'
                ) {
                  return null;
                } else {
                  return <TreeItemAction {...props} />;
                }
              }}
              onTreeItemClick={onTreeItemClick}
              selectTreeItems={(selectedTreeItemIds: string[]) =>
                setState((prevState) => {
                  return { ...prevState, selectedTreeItemIds };
                })
              }
              selectedTreeItemIds={state.selectedTreeItemIds}
              data-testid="viewsexplorer://"
            />
          </div>
        )}
      </div>
    );
  }
);

ViewsExplorerView.displayName = 'ViewsExplorerView';
