/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
  useSelection,
  WorkbenchViewComponentProps,
} from '@eclipse-sirius/sirius-components-core';
import {
  FilterBar,
  GQLGetTreePathVariables,
  GQLTreeItem,
  TreeFilter,
  TreeToolBar,
  TreeToolBarContext,
  TreeToolBarContextValue,
  TreeView,
  useTreeFilters,
  useTreePath,
} from '@eclipse-sirius/sirius-components-trees';
import { Theme } from '@mui/material/styles';
import { useCallback, useContext, useEffect, useMemo, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectKeyboardShortcut } from '../../../../modals/duplicate-object/DuplicateObjectKeyboardShortcut';
import { ExplorerViewState } from './ExplorerView.types';
import { TreeDescriptionsMenu } from './TreeDescriptionsMenu';
import { useExplorerDescriptions } from './useExplorerDescriptions';
import { useExplorerSubscription } from './useExplorerSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useExplorerSubscription.types';

const NO_MARKED_ITEMS: string[] = [];

const useStyles = makeStyles()((theme: Theme) => ({
  treeView: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto auto 1fr',
    justifyItems: 'stretch',
    overflow: 'auto',
  },
  treeFilter: {
    paddingTop: theme.spacing(1),
  },
  treeContent: {
    overflow: 'auto',
  },
}));

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload && payload.__typename === 'TreeRefreshedEventPayload';

export const ExplorerView = ({ editingContextId, readOnly }: WorkbenchViewComponentProps) => {
  const { classes: styles } = useStyles();

  const initialState: ExplorerViewState = {
    synchronizedWithSelection: true,
    filterBar: false,
    filterBarText: '',
    filterBarTreeFiltering: false,
    treeFilters: [],
    activeTreeDescriptionId: null,
    expanded: {},
    maxDepth: {},
    tree: null,
    singleTreeItemSelectedId: null,
    singleTreeItemSelectedKind: null,
  };
  const [state, setState] = useState<ExplorerViewState>(initialState);
  const treeToolBarContributionComponents = useContext<TreeToolBarContextValue>(TreeToolBarContext).map(
    (contribution) => contribution.props.component
  );
  const activeTreeFilterIds = state.treeFilters.filter((filter) => filter.state).map((filter) => filter.id);

  const { selection, setSelection, toggleSelected } = useSelection();

  const { payload } = useExplorerSubscription(
    editingContextId,
    state.activeTreeDescriptionId,
    activeTreeFilterIds,
    state.expanded[state.activeTreeDescriptionId] ?? [],
    state.maxDepth[state.activeTreeDescriptionId] ?? 1
  );

  useEffect(() => {
    if (isTreeRefreshedEventPayload(payload)) {
      setState((prevState) => ({ ...prevState, tree: payload.tree }));
    }
  }, [payload]);

  const { loading, treeFilters } = useTreeFilters(editingContextId, 'explorer://');

  const { explorerDescriptions } = useExplorerDescriptions(editingContextId);

  useEffect(() => {
    if (explorerDescriptions && explorerDescriptions.length > 0) {
      const expandedInitiated: { [key: string]: string[] } = {};
      const maxDepthInitiated: { [key: string]: number } = {};
      explorerDescriptions.forEach((explorerDescription) => {
        expandedInitiated[explorerDescription.id] = [];
        maxDepthInitiated[explorerDescription.id] = 1;
      });

      setState((prevState) => ({
        ...prevState,
        activeTreeDescriptionId: explorerDescriptions[0].id,
        expanded: expandedInitiated,
        maxDepth: maxDepthInitiated,
      }));
    }
  }, [explorerDescriptions]);

  useEffect(() => {
    if (!loading) {
      const allFilters: TreeFilter[] = treeFilters.map((gqlTreeFilter) => ({
        id: gqlTreeFilter.id,
        label: gqlTreeFilter.label,
        state: gqlTreeFilter.defaultState,
      }));
      setState((prevState) => ({ ...prevState, treeFilters: allFilters }));
    }
  }, [loading, treeFilters]);

  const treeElement = useRef<HTMLDivElement>(null);
  useEffect(() => {
    const downHandler = (event) => {
      if ((event.ctrlKey === true || event.metaKey === true) && event.key === 'f' && event.target.tagName !== 'INPUT') {
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

  const { getTreePath, data: treePathData } = useTreePath();

  // If we should auto-expand to reveal the selection, we need to compute the tree path to expand
  const selectionKey: string = selection?.entries
    .map((entry) => entry.id)
    .sort()
    .join(':');
  useEffect(() => {
    if (state.synchronizedWithSelection && state.tree) {
      const variables: GQLGetTreePathVariables = {
        editingContextId,
        treeId: state.tree.id,
        selectionEntryIds: selection.entries.map((entry) => entry.id),
      };
      getTreePath({ variables });
    }
  }, [editingContextId, selectionKey, state.synchronizedWithSelection, state.tree, getTreePath]);

  useEffect(() => {
    if (treePathData && treePathData.viewer?.editingContext?.treePath) {
      setState((prevState) => {
        const { expanded, maxDepth } = prevState;
        const { treeItemIdsToExpand, maxDepth: expandedMaxDepth } = treePathData.viewer.editingContext.treePath;
        const newExpanded: string[] = [...expanded[prevState.activeTreeDescriptionId]];

        treeItemIdsToExpand?.forEach((itemToExpand) => {
          if (!expanded[prevState.activeTreeDescriptionId].includes(itemToExpand)) {
            newExpanded.push(itemToExpand);
          }
        });
        return {
          ...prevState,
          expanded: {
            ...prevState.expanded,
            [prevState.activeTreeDescriptionId]: newExpanded,
          },
          maxDepth: {
            ...prevState.maxDepth,
            [prevState.activeTreeDescriptionId]: Math.max(
              expandedMaxDepth,
              maxDepth[prevState.activeTreeDescriptionId]
            ),
          },
        };
      });
    }
  }, [treePathData]);

  const onExpandedElementChange = useCallback((newExpandedIds: string[], newMaxDepth: number) => {
    setState((prevState) => ({
      ...prevState,
      expanded: {
        ...prevState.expanded,
        [prevState.activeTreeDescriptionId]: newExpandedIds,
      },
      maxDepth: {
        ...prevState.maxDepth,
        [prevState.activeTreeDescriptionId]: Math.max(
          newMaxDepth,
          prevState.maxDepth[prevState.activeTreeDescriptionId]
        ),
      },
    }));
  }, []);

  let filterBar: JSX.Element = <div />;
  if (state.filterBar) {
    filterBar = (
      <div className={styles.treeFilter}>
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

  const selectedTreeItemIds = useMemo(() => selection.entries.map((entry) => entry.id), [selection]);

  const onTreeItemClick = useCallback(
    (event: React.MouseEvent<HTMLDivElement>, item: GQLTreeItem) => {
      if (event.ctrlKey || event.metaKey) {
        event.stopPropagation();
        toggleSelected({ id: item.id });
      } else {
        const target = event.target as HTMLDivElement;
        const itemId = target.getAttribute('data-treeitemid') || '';
        const isItemSelected = selectedTreeItemIds.some((selectedItemId) => selectedItemId === itemId);
        if (!isItemSelected) {
          setSelection({ entries: [{ id: item.id }] });
          setState((prevState) => ({
            ...prevState,
            singleTreeItemSelectedId: item.id,
            singleTreeItemSelectedKind: item.kind,
          }));
        }
      }
    },
    [selectedTreeItemIds]
  );

  const treeDescriptionSelector: JSX.Element = explorerDescriptions.length > 1 && (
    <TreeDescriptionsMenu
      treeDescriptions={explorerDescriptions}
      activeTreeDescriptionId={state.activeTreeDescriptionId}
      onTreeDescriptionChange={(treeDescription) =>
        setState((prevState) => ({
          ...prevState,
          activeTreeDescriptionId: treeDescription.id,
          tree: null,
        }))
      }
    />
  );

  if (!state.tree || loading) {
    return (
      <div className={styles.treeView} ref={treeElement}>
        <RepresentationLoadingIndicator />
      </div>
    );
  }

  return (
    <div className={styles.treeView} ref={treeElement}>
      <TreeToolBar
        editingContextId={editingContextId}
        readOnly={readOnly}
        onSynchronizedClick={() =>
          setState((prevState) => {
            return { ...prevState, synchronizedWithSelection: !state.synchronizedWithSelection };
          })
        }
        synchronized={state.synchronizedWithSelection}
        treeFilters={state.treeFilters}
        onTreeFilterMenuItemClick={(treeFilters) =>
          setState((prevState) => {
            return { ...prevState, treeFilters };
          })
        }
        treeToolBarContributionComponents={treeToolBarContributionComponents}>
        {treeDescriptionSelector}
      </TreeToolBar>
      <DuplicateObjectKeyboardShortcut
        editingContextId={editingContextId}
        readOnly={readOnly}
        selectedTreeItemId={state.singleTreeItemSelectedId}
        selectedTreeItemKind={state.singleTreeItemSelectedKind}>
        {filterBar}
        <div className={styles.treeContent}>
          {state.tree !== null ? (
            <TreeView
              editingContextId={editingContextId}
              readOnly={readOnly}
              treeId={'explorer://'}
              tree={state.tree}
              textToHighlight={state.filterBarText}
              textToFilter={state.filterBarTreeFiltering ? state.filterBarText : null}
              selectedTreeItemIds={selectedTreeItemIds}
              markedItemIds={NO_MARKED_ITEMS}
              expanded={state.expanded[state.activeTreeDescriptionId]}
              maxDepth={state.maxDepth[state.activeTreeDescriptionId]}
              onExpandedElementChange={onExpandedElementChange}
              onTreeItemClick={onTreeItemClick}
            />
          ) : null}
        </div>
      </DuplicateObjectKeyboardShortcut>
    </div>
  );
};
