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
  Selection,
  SelectionEntry,
  useSelection,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import {
  FilterBar,
  GQLGetTreePathVariables,
  GQLTree,
  GQLTreeItem,
  TreeItemAction,
  TreeToolBar,
  TreeView,
  useTreePath,
  useTreeSelection,
} from '@eclipse-sirius/sirius-components-trees';
import Typography from '@mui/material/Typography';
import React, { ForwardedRef, forwardRef, ReactElement, useCallback, useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { ViewsExplorerViewState } from './ViewsExplorerView.types';
import { useViewsExplorerViewHandle } from './useViewsExplorerViewHandle';
import { useViewsExplorerViewSubscription } from './useViewsExplorerViewSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useViewsExplorerViewSubscription.types';

const useStyles = makeStyles()((theme) => ({
  idle: {
    padding: theme.spacing(1),
  },
  treeView: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto auto 1fr',
    justifyItems: 'stretch',
    overflow: 'auto',
  },
  treeContent: {
    overflow: 'auto',
  },
  treeFilter: {
    paddingTop: theme.spacing(1),
  },
}));

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload && payload.__typename === 'TreeRefreshedEventPayload';

export const ViewsExplorerView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  ({ editingContextId, id, readOnly }: WorkbenchViewComponentProps, ref: ForwardedRef<WorkbenchViewHandle>) => {
    const { classes } = useStyles();
    const { t } = useTranslation('sirius-web-application', { keyPrefix: 'viewsExplorerView' });

    const [state, setState] = useState<ViewsExplorerViewState>({
      expanded: [],
      tree: null,
      selectedTreeItemIds: [],
      filterBar: false,
      filterBarText: '',
      filterBarTreeFiltering: false,
      maxDepth: 1,
    });

    const { payload } = useViewsExplorerViewSubscription(editingContextId, state.expanded, state.maxDepth);

    useEffect(() => {
      if (isTreeRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, tree: payload.tree }));
      }
    }, [payload]);

    const { selection, setSelection } = useSelection();
    const { treeItemClick } = useTreeSelection();

    // If we are requested to reveal the global selection, we need to compute the tree path to expand
    const { getTreePath, data: treePathData } = useTreePath();

    const applySelection = (appliedSelection: Selection) => {
      const newSelectedTreeItemIds = appliedSelection.entries.map((entry) => entry.id);
      setState((prevState) => ({
        ...prevState,
        selectedTreeItemIds: newSelectedTreeItemIds,
      }));

      if (state.tree && newSelectedTreeItemIds.length > 0) {
        const variables: GQLGetTreePathVariables = {
          editingContextId,
          treeId: state.tree.id,
          selectionEntryIds: newSelectedTreeItemIds,
        };
        getTreePath({ variables });
      }
    };

    useViewsExplorerViewHandle(id, state.tree?.id, applySelection, ref);

    const selectionKey: string = selection?.entries
      .map((entry) => entry.id)
      .sort()
      .join(':');

    const revealSelection = useCallback(() => {
      if (state.tree && selection.entries.length > 0) {
        const variables: GQLGetTreePathVariables = {
          editingContextId,
          treeId: state.tree.id,
          selectionEntryIds: selection.entries.map((entry) => entry.id),
        };
        getTreePath({ variables });
      }
    }, [editingContextId, selectionKey, state.tree, getTreePath]);

    useEffect(() => {
      if (treePathData && treePathData.viewer?.editingContext?.treePath) {
        setState((prevState) => {
          const { expanded, maxDepth } = prevState;
          const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } = treePathData.viewer.editingContext.treePath;
          const newExpanded: string[] = [...expanded];

          treeItemIdsToExpand?.forEach((itemToExpand: string) => {
            if (!expanded.includes(itemToExpand)) {
              newExpanded.push(itemToExpand);
            }
          });
          return {
            ...prevState,
            selectedTreeItemIds: selection.entries.map((entry) => entry.id),
            expanded: newExpanded,
            maxDepth: Math.max(expandedMaxDepth, maxDepth),
          };
        });
      }
    }, [treePathData]);

    const onExpandedElementChange = (newExpandedIds: string[], newMaxDepth: number) => {
      setState((prevState) => ({
        ...prevState,
        expanded: newExpandedIds,
        maxDepth: Math.max(newMaxDepth, prevState.maxDepth),
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

    const treeElement = useRef<HTMLDivElement>(null);
    useEffect(() => {
      const downHandler = (event) => {
        if (
          (event.ctrlKey === true || event.metaKey === true) &&
          event.key === 'f' &&
          event.target.tagName !== 'INPUT'
        ) {
          event.preventDefault();
          setState((prevState) => {
            return { ...prevState, filterBar: true, filterBarText: '', filterBarTreeFiltering: false };
          });
        }
      };
      const element = treeElement?.current;
      if (element) {
        element.addEventListener('keydown', downHandler);

        return () => {
          element.removeEventListener('keydown', downHandler);
        };
      }
      return null;
    }, [treeElement]);

    let filterBar: ReactElement = <div />;
    if (state.filterBar) {
      filterBar = (
        <div className={classes.treeFilter}>
          <FilterBar
            onTextChange={(event) => {
              const {
                target: { value },
              } = event;
              setState((prevState) => {
                return { ...prevState, filterBarText: value };
              });
            }}
            onFilterButtonClick={(enabled) =>
              setState((prevState) => ({
                ...prevState,
                filterBarTreeFiltering: enabled,
              }))
            }
            onClose={() =>
              setState((prevState) => {
                return { ...prevState, filterBar: false, filterBarText: '', filterBarTreeFiltering: false };
              })
            }
          />
        </div>
      );
    }

    if (!state.tree) {
      return (
        <div className={classes.treeView} ref={treeElement}>
          <RepresentationLoadingIndicator />
        </div>
      );
    }

    return (
      <div className={classes.treeView} ref={treeElement}>
        <TreeToolBar
          editingContextId={editingContextId}
          readOnly={readOnly}
          onRevealSelection={revealSelection}
          treeFilters={[]}
          onTreeFilterMenuItemClick={() => {}}
          onFilter={() => {
            setState((prevState) => {
              return !prevState.filterBar
                ? { ...prevState, filterBar: true, filterBarText: '', filterBarTreeFiltering: false }
                : { ...prevState, filterBar: false, filterBarText: '', filterBarTreeFiltering: false };
            });
          }}
          treeToolBarContributionComponents={[]}>
          <></>
        </TreeToolBar>
        {filterBar}
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
              textToHighlight={state.filterBarText}
              textToFilter={state.filterBarTreeFiltering ? state.filterBarText : null}
              onExpandedElementChange={onExpandedElementChange}
              expanded={state.expanded}
              maxDepth={state.maxDepth}
              treeItemActionRender={(props) => {
                if (
                  props.item.kind === 'siriusWeb://representationKind' ||
                  props.item.kind === 'siriusWeb://representationDescriptionType'
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
