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
  ViewAccordion,
  ViewAccordionContent,
  ViewAccordionToolbar,
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import {
  FilterBar,
  TreeToolBar,
  TreeToolBarContext,
  TreeToolBarContextValue,
  TreeView,
} from '@eclipse-sirius/sirius-components-trees';
import Box from '@mui/material/Box';
import { Theme } from '@mui/material/styles';
import { ForwardedRef, forwardRef, useContext, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DuplicateObjectKeyboardShortcut } from './context-menu-contributions/duplicate-object/DuplicateObjectKeyboardShortcut';
import { ExplorerViewConfiguration, ExplorerViewState } from './ExplorerView.types';
import { TreeDescriptionsMenu } from './TreeDescriptionsMenu';
import { useExplorerDescriptions } from './useExplorerDescriptions';
import { useExplorerSelection } from './useExplorerSelection';
import { useExplorerSubscription } from './useExplorerSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useExplorerSubscription.types';
import { useExplorerViewHandle } from './useExplorerViewHandle';
import { useTreeFiltering } from './useTreeFiltering';
import { useTreeStateContainer } from './useTreeStateContainer';

const useStyles = makeStyles()((theme: Theme) => ({
  treeView: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto minmax(0, 1fr)',
    justifyItems: 'stretch',
    overflow: 'hidden',
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

    const configuredActiveTreeDescriptionId = initialExplorerViewConfiguration?.activeTreeDescriptionId ?? null;

    const [state, setState] = useState<ExplorerViewState>({
      filterBar: false,
      filterBarText: '',
      filterBarTreeFiltering: false,
      tree: null,
    });
    const treeId: string | null = state.tree?.id || null;

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

    const {
      selectedTreeItemIds,
      singleTreeItemSelected,
      onRevealSelection,
      onTreeItemClick,
      applySelection,
      setSelectedTreeItemIds,
    } = useExplorerSelection(editingContextId, treeId, expanded, onExpandedElementChange);

    useExplorerViewHandle(id, treeId, treeFilters, activeTreeDescriptionId, applySelection, ref);

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

    const toolbar = (
      <TreeToolBar
        editingContextId={editingContextId}
        readOnly={readOnly}
        treeFilters={treeFilters}
        onRevealSelection={onRevealSelection}
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
    );

    return (
      <ViewAccordion id={id} title="Explorer">
        <ViewAccordionToolbar>{toolbar}</ViewAccordionToolbar>
        <ViewAccordionContent>
          <Box className={styles.treeView} ref={treeElement}>
            {!state.tree || treeFiltersLoading ? (
              <RepresentationLoadingIndicator />
            ) : (
              <>
                <DuplicateObjectKeyboardShortcut
                  target={treeElement?.current}
                  editingContextId={editingContextId}
                  readOnly={readOnly}
                  selectedTreeItem={singleTreeItemSelected}
                  selectTreeItems={setSelectedTreeItemIds}>
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
                      selectTreeItems={setSelectedTreeItemIds}
                      selectedTreeItemIds={selectedTreeItemIds}
                      data-testid="explorer://"
                      useTreePalette={state.tree.capabilities.useTreePalette}
                    />
                  </div>
                </DuplicateObjectKeyboardShortcut>
              </>
            )}
          </Box>
        </ViewAccordionContent>
      </ViewAccordion>
    );
  }
);
