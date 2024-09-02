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
import { WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import {
  FilterBar,
  TreeFilter,
  TreeToolBar,
  TreeToolBarContext,
  TreeToolBarContextValue,
  TreeView,
  useTreeFilters,
} from '@eclipse-sirius/sirius-components-trees';
import { Theme } from '@mui/material/styles';
import { useContext, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ExplorerViewState } from './ExplorerView.types';
import { TreeDescriptionsMenu } from './TreeDescriptionsMenu';
import { useExplorerDescriptions } from './useExplorerDescriptions';
import { useExplorerSubscription } from './useExplorerSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useExplorerSubscription.types';

const useStyles = makeStyles()((theme: Theme) => ({
  treeView: {
    display: 'flex',
    flexDirection: 'column',
  },
  treeContent: {
    paddingTop: theme.spacing(1),
    flexGrow: 1,
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
  };
  const [state, setState] = useState<ExplorerViewState>(initialState);
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

  const { loading, treeFilters } = useTreeFilters(editingContextId, 'explorer://');

  const { explorerDescriptions } = useExplorerDescriptions(editingContextId);

  useEffect(() => {
    if (explorerDescriptions && explorerDescriptions.length > 0) {
      setState((prevState) => ({ ...prevState, activeTreeDescriptionId: explorerDescriptions[0].id }));
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

  let filterBar: JSX.Element;
  if (state.filterBar) {
    filterBar = (
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
    );
  }
  const onExpandedElementChange = (expanded: string[], maxDepth: number) => {
    setState((prevState) => ({
      ...prevState,
      expanded: {
        ...prevState.expanded,
        [prevState.activeTreeDescriptionId]: expanded,
      },
      maxDepth: {
        ...prevState.maxDepth,
        [prevState.activeTreeDescriptionId]: maxDepth,
      },
    }));
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
      <div className={styles.treeContent}>
        {filterBar}
        {state.tree !== null ? (
          <TreeView
            editingContextId={editingContextId}
            readOnly={readOnly}
            treeId={'explorer://'}
            tree={state.tree}
            enableMultiSelection={true}
            synchronizedWithSelection={state.synchronizedWithSelection}
            textToHighlight={state.filterBarText}
            textToFilter={state.filterBarTreeFiltering ? state.filterBarText : null}
            onExpandedElementChange={onExpandedElementChange}
            expanded={state.expanded[state.activeTreeDescriptionId] ?? []}
            maxDepth={state.maxDepth[state.activeTreeDescriptionId] ?? 1}
          />
        ) : null}
      </div>
    </div>
  );
};
