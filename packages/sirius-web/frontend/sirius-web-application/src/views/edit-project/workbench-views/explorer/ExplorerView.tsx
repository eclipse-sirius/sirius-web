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
import { FilterBarContextProvider } from '@eclipse-sirius/sirius-components-trees';
import Box from '@mui/material/Box';
import { ForwardedRef, forwardRef, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ExplorerToolbarRenderer } from './ExplorerToolbarRenderer';
import { ExplorerTreeRenderer } from './ExplorerTreeRenderer';
import { ExplorerViewConfiguration, ExplorerViewState } from './ExplorerView.types';
import { useExplorerDescriptions } from './useExplorerDescriptions';
import { useExplorerSelection } from './useExplorerSelection';
import { useExplorerSubscription } from './useExplorerSubscription';
import { GQLTreeEventPayload, GQLTreeRefreshedEventPayload } from './useExplorerSubscription.types';
import { useExplorerViewHandle } from './useExplorerViewHandle';
import { useTreeFiltering } from './useTreeFiltering';
import { useTreeStateContainer } from './useTreeStateContainer';

const useStyles = makeStyles()(() => ({
  treeView: {
    display: 'grid',
    gridTemplateColumns: 'auto',
    gridTemplateRows: 'auto minmax(0, 1fr)',
    justifyItems: 'stretch',
    overflow: 'hidden',
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

    return (
      <FilterBarContextProvider containerRef={treeElement}>
        <ViewAccordion id={id} title="Explorer">
          <ViewAccordionToolbar>
            <ExplorerToolbarRenderer
              editingContextId={editingContextId}
              readOnly={readOnly}
              activeTreeDescriptionId={activeTreeDescriptionId}
              explorerDescriptions={explorerDescriptions}
              treeFilters={treeFilters}
              resetTree={() => setState((prevState) => ({ ...prevState, tree: null }))}
              setTreeFilters={setTreeFilters}
              setActiveDescriptionId={setActiveDescriptionId}
              onRevealSelection={onRevealSelection}
            />
          </ViewAccordionToolbar>
          <ViewAccordionContent>
            <Box className={styles.treeView} ref={treeElement}>
              {!state.tree || treeFiltersLoading ? (
                <RepresentationLoadingIndicator />
              ) : (
                <ExplorerTreeRenderer
                  editingContextId={editingContextId}
                  readOnly={readOnly}
                  tree={state.tree}
                  target={treeElement?.current}
                  selectedTreeItem={singleTreeItemSelected}
                  selectedTreeItemIds={selectedTreeItemIds}
                  onTreeItemClick={onTreeItemClick}
                  selectTreeItems={setSelectedTreeItemIds}
                  expanded={expanded}
                  maxDepth={maxDepth}
                  onExpandedElementChange={onExpandedElementChange}
                />
              )}
            </Box>
          </ViewAccordionContent>
        </ViewAccordion>
      </FilterBarContextProvider>
    );
  }
);
