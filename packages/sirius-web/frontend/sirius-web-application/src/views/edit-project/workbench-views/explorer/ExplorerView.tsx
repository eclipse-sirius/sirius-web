/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
  TreeToolBar,
  TreeToolBarContext,
  TreeToolBarContextValue,
  TreeView,
  useTreePath,
  useTreeSelection,
} from '@eclipse-sirius/sirius-components-trees';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import Box from '@mui/material/Box';
import { Theme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, useCallback, useContext, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectKeyboardShortcut } from './context-menu-contributions/duplicate-object/DuplicateObjectKeyboardShortcut';
import { ExplorerViewConfiguration, ExplorerViewState } from './ExplorerView.types';
import { TreeDescriptionsMenu } from './TreeDescriptionsMenu';
import { useExplorerDescriptions } from './useExplorerDescriptions';
import { useExplorerSubscription } from './useExplorerSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useExplorerSubscription.types';
import { useExplorerViewHandle } from './useExplorerViewHandle';
import { useTreeFiltering } from './useTreeFiltering';
import { useTreeStateContainer } from './useTreeStateContainer';

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
      tree: null,
      selectedTreeItemIds: [],
      singleTreeItemSelected: null,
      selectionPivotTreeItemId: null,
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

    const configuredActiveTreeDescriptionId = initialExplorerViewConfiguration?.activeTreeDescriptionId ?? null;

    const { explorerDescriptions } = useExplorerDescriptions(editingContextId);
    const { activeTreeDescriptionId, expanded, maxDepth, onExpandedElementChange, setActiveDescriptionId } =
      useTreeStateContainer(configuredActiveTreeDescriptionId, explorerDescriptions);

    const {
      treeFilters,
      loading: treeFiltersLoading,
      setTreeFilters,
    } = useTreeFiltering(
      editingContextId,
      activeTreeDescriptionId,
      initialExplorerViewConfiguration?.activeTreeFilters ?? []
    );

    useExplorerViewHandle(id, state.tree?.id, treeFilters, activeTreeDescriptionId, applySelection, ref);

    const treeToolBarContributionComponents = useContext<TreeToolBarContextValue>(TreeToolBarContext).map(
      (contribution) => contribution.props.component
    );

    const activeTreeFilterIds = treeFilters.filter((filter) => filter.state).map((filter) => filter.id);

    const { payload } = useExplorerSubscription(
      editingContextId,
      activeTreeDescriptionId,
      activeTreeFilterIds,
      expanded,
      maxDepth
    );

    useEffect(() => {
      if (isTreeRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, tree: payload.tree }));
      }
    }, [payload]);

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
    const { treeItemClick } = useTreeSelection();

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
        const expandedIds = treePathData.viewer.editingContext.treePath.treeItemIdsToExpand.filter(
          (id) => !expanded.includes(id)
        );
        const newExpandedIds = [...expanded, ...expandedIds];
        onExpandedElementChange(newExpandedIds, treePathData.viewer.editingContext.treePath.maxDepth);
        setState((prevState) => {
          return {
            ...prevState,
            selectedTreeItemIds: selection.entries.map((entry) => entry.id),
          };
        });
      }
    }, [treePathData]);

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

    const onTreeItemClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tree: GQLTree, item: GQLTreeItem) => {
      var localSelection = treeItemClick(event, tree, item, state.selectedTreeItemIds, true);
      setState((prevState) => ({
        ...prevState,
        selectedTreeItemIds: localSelection.selectedTreeItemIds,
        singleTreeItemSelected: localSelection.singleTreeItemSelected,
      }));
      var globalSelection = treeItemClick(
        event,
        state.tree,
        item,
        selection.entries.map((entry) => entry.id),
        true
      );
      setSelection({ entries: globalSelection.selectedTreeItemIds.map<SelectionEntry>((id) => ({ id })) });
    };

    const treeDescriptionSelector: JSX.Element = explorerDescriptions.length > 1 && (
      <TreeDescriptionsMenu
        treeDescriptions={explorerDescriptions}
        activeTreeDescriptionId={activeTreeDescriptionId}
        onTreeDescriptionChange={(treeDescription) => {
          setActiveDescriptionId(treeDescription.id);
          setState((prevState) => ({
            ...prevState,
            tree: null,
          }));
        }}
      />
    );

    return (
      <Box sx={{ display: 'flex', flexDirection: 'column' }} data-testid="view-Explorer">
        <Box
          sx={(theme) => ({
            display: 'flex',
            flexDirection: 'row',
            borderBottomWidth: '1px',
            borderBottomStyle: 'solid',
            borderBottomColor: theme.palette.divider,
          })}>
          <AccountTreeIcon sx={(theme) => ({ margin: theme.spacing(1) })} />
          <Typography
            sx={(theme) => ({
              marginTop: theme.spacing(1),
              marginRight: theme.spacing(1),
              marginBottom: theme.spacing(1),
            })}>
            Explorer
          </Typography>
        </Box>
        <Box className={styles.treeView} sx={{ flexGrow: 1, minHeight: 0 }} ref={treeElement}>
          {!state.tree || treeFiltersLoading ? (
            <RepresentationLoadingIndicator />
          ) : (
            <>
              <TreeToolBar
                editingContextId={editingContextId}
                readOnly={readOnly}
                treeFilters={treeFilters}
                onRevealSelection={revealSelection}
                onTreeFilterMenuItemClick={setTreeFilters}
                onFilter={() => {
                  setState((prevState) => {
                    return !prevState.filterBar
                      ? { ...prevState, filterBar: true, filterBarText: '', filterBarTreeFiltering: false }
                      : { ...prevState, filterBar: false, filterBarText: '', filterBarTreeFiltering: false };
                  });
                }}
                treeToolBarContributionComponents={treeToolBarContributionComponents}>
                {treeDescriptionSelector}
              </TreeToolBar>
              <DuplicateObjectKeyboardShortcut
                target={treeElement?.current}
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
                  <TreeView
                    editingContextId={editingContextId}
                    readOnly={readOnly}
                    tree={state.tree}
                    textToHighlight={state.filterBarText}
                    textToFilter={state.filterBarTreeFiltering ? state.filterBarText : null}
                    onExpandedElementChange={onExpandedElementChange}
                    expanded={expanded}
                    maxDepth={maxDepth}
                    onTreeItemClick={onTreeItemClick}
                    selectTreeItems={(selectedTreeItemIds: string[]) =>
                      setState((prevState) => {
                        return { ...prevState, selectedTreeItemIds };
                      })
                    }
                    selectedTreeItemIds={state.selectedTreeItemIds}
                    data-testid="explorer://"
                    useTreePalette={state.tree.capabilities.useTreePalette}
                  />
                </div>
              </DuplicateObjectKeyboardShortcut>
            </>
          )}
        </Box>
      </Box>
    );
  }
);
