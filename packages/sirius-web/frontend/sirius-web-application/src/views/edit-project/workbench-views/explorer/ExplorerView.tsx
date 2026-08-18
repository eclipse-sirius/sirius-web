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
  WorkbenchViewComponentProps,
  WorkbenchViewHandle,
} from '@eclipse-sirius/sirius-components-core';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import Box from '@mui/material/Box';
import { Theme } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, useEffect, useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ExplorerRenderer } from './ExplorerRenderer';
import { ExplorerTreeToolbarRenderer } from './ExplorerTreeToolbarRenderer';
import { ExplorerViewConfiguration, ExplorerViewState } from './ExplorerView.types';
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

    const resetTree = () => {
      setState((prevState) => ({ ...prevState, tree: null }));
    };

    const onFilter = () => {
      setState((prevState) => {
        return !prevState.filterBar
          ? { ...prevState, filterBar: true, filterBarText: '', filterBarTreeFiltering: false }
          : { ...prevState, filterBar: false, filterBarText: '', filterBarTreeFiltering: false };
      });
    };

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
          {!state.tree || treeFiltersLoading || !activeTreeDescriptionId ? (
            <RepresentationLoadingIndicator />
          ) : (
            <>
              <ExplorerTreeToolbarRenderer
                editingContextId={editingContextId}
                readOnly={readOnly}
                activeTreeDescriptionId={activeTreeDescriptionId}
                explorerDescriptions={explorerDescriptions}
                treeFilters={treeFilters}
                resetTree={resetTree}
                setTreeFilters={setTreeFilters}
                setActiveDescriptionId={setActiveDescriptionId}
                onRevealSelection={onRevealSelection}
                onFilter={onFilter}
              />
              <ExplorerRenderer
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
            </>
          )}
        </Box>
      </Box>
    );
  }
);
