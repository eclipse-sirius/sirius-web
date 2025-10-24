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
  Selection,
  SelectionEntry,
  useSelection,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
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
import { ForwardedRef, forwardRef, useCallback, useContext, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectKeyboardShortcut } from '../../../../modals/duplicate-object/DuplicateObjectKeyboardShortcut';
import { ExplorerViewConfiguration, ExplorerViewState } from './ExplorerView.types';
import { TreeDescriptionsMenu } from './TreeDescriptionsMenu';
import { useExplorerDescriptions } from './useExplorerDescriptions';
import { useExplorerSubscription } from './useExplorerSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useExplorerSubscription.types';
import { useExplorerViewHandle } from './useExplorerViewHandle';

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

export const ExplorerView = forwardRef<WorkbenchViewHandle, WorkbenchViewComponentProps>(
  (
    { editingContextId, id, initialConfiguration, readOnly }: WorkbenchViewComponentProps,
    ref: ForwardedRef<WorkbenchViewHandle>
  ) => {
    const { classes: styles } = useStyles();

    const initialExplorerViewConfiguration: ExplorerViewConfiguration =
      initialConfiguration as unknown as ExplorerViewConfiguration;
    const [state, setState] = useState<ExplorerViewState>({
      filterBar: false,
      filterBarText: '',
      filterBarTreeFiltering: false,
      treeFilters: initialExplorerViewConfiguration?.activeTreeFilters ?? [],
      activeTreeDescriptionId: initialExplorerViewConfiguration?.activeTreeDescriptionId ?? null,
      expanded: {},
      maxDepth: {},
      tree: null,
      selectedTreeItemIds: [],
      singleTreeItemSelected: null,
    });

    // If we are requested to reveal the global selection, we need to compute the tree path to expand
    const { getTreePath, data: treePathData } = useTreePath();

    const applySelection = (selection: Selection) => {
      const newSelectedTreeItemIds = selection.entries.map((entry) => entry.id);
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

    useExplorerViewHandle(id, state.treeFilters, state.activeTreeDescriptionId, applySelection, ref);

    const treeToolBarContributionComponents = useContext<TreeToolBarContextValue>(TreeToolBarContext).map(
      (contribution) => contribution.props.component
    );
    const activeTreeFilterIds = state.treeFilters.filter((filter) => filter.state).map((filter) => filter.id);

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
          activeTreeDescriptionId: state.activeTreeDescriptionId ?? explorerDescriptions[0].id,
          expanded: expandedInitiated,
          maxDepth: maxDepthInitiated,
        }));
      }
    }, [explorerDescriptions]);

    const { loading, treeFilters } = useTreeFilters(editingContextId, 'explorer://');

    useEffect(() => {
      if (!loading) {
        const allAvailableFilters: TreeFilter[] = treeFilters.map((gqlTreeFilter) => ({
          id: gqlTreeFilter.id,
          label: gqlTreeFilter.label,
          state: gqlTreeFilter.defaultState,
        }));
        setState((prevState) => ({
          ...prevState,
          treeFilters: allAvailableFilters.map((availableFilter) => {
            const existingFilter: TreeFilter = state.treeFilters.find((filter) => filter.id === availableFilter.id);
            if (existingFilter) {
              return {
                ...availableFilter,
                state: existingFilter.state,
              };
            } else {
              return availableFilter;
            }
          }),
        }));
      }
    }, [loading, treeFilters]);

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

    const { selection, setSelection } = useSelection();

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
          const newExpanded: string[] = [...expanded[prevState.activeTreeDescriptionId]];

          treeItemIdsToExpand?.forEach((itemToExpand) => {
            if (!expanded[prevState.activeTreeDescriptionId].includes(itemToExpand)) {
              newExpanded.push(itemToExpand);
            }
          });
          return {
            ...prevState,
            selectedTreeItemIds: selection.entries.map((entry) => entry.id),
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

    const onExpandedElementChange = (newExpandedIds: string[], newMaxDepth: number) => {
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
    };

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

    const onTreeItemClick = (event, item: GQLTreeItem) => {
      if (event.ctrlKey || event.metaKey) {
        event.stopPropagation();
        // Update the global selection
        const isItemInSelection = selection.entries.find((entry) => entry.id === item.id);
        if (isItemInSelection) {
          const newSelection: Selection = { entries: selection.entries.filter((entry) => entry.id !== item.id) };
          setSelection(newSelection);
        } else {
          const { id } = item;
          const newEntry: SelectionEntry = { id };
          const newSelection: Selection = { entries: [...selection.entries, newEntry] };
          setSelection(newSelection);
        }
        // Update the local selection
        const isItemInLocalSelection = state.selectedTreeItemIds.includes(item.id);
        if (isItemInLocalSelection) {
          const newSelectedTreeItemIds = state.selectedTreeItemIds.filter((selectedId) => selectedId !== item.id);
          setState((prevState) => ({ ...prevState, selectedTreeItemIds: newSelectedTreeItemIds }));
        } else {
          const { id } = item;
          const newSelectedTreeItemIds = [...state.selectedTreeItemIds, id];
          setState((prevState) => ({ ...prevState, selectedTreeItemIds: newSelectedTreeItemIds }));
        }
        setState((prevState) => ({ ...prevState, singleTreeItemSelected: null }));
      } else {
        const { id } = item;
        setState((prevState) => ({ ...prevState, selectedTreeItemIds: [id], singleTreeItemSelected: item }));
        setSelection({ entries: [{ id }] });
      }
    };

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
          treeFilters={state.treeFilters}
          onRevealSelection={revealSelection}
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
          selectedTreeItem={state.singleTreeItemSelected}
          selectTreeItems={(selectedTreeItemIds: string[]) =>
            setState((prevState) => {
              return { ...prevState, selectedTreeItemIds };
            })
          }>
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
                onExpandedElementChange={onExpandedElementChange}
                expanded={state.expanded[state.activeTreeDescriptionId]}
                maxDepth={state.maxDepth[state.activeTreeDescriptionId]}
                onTreeItemClick={onTreeItemClick}
                selectTreeItems={(selectedTreeItemIds: string[]) =>
                  setState((prevState) => {
                    return { ...prevState, selectedTreeItemIds };
                  })
                }
                selectedTreeItemIds={state.selectedTreeItemIds}
              />
            ) : null}
          </div>
        </DuplicateObjectKeyboardShortcut>
      </div>
    );
  }
);
