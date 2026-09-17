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

import { useSelection } from '@eclipse-sirius/sirius-components-core';
import DeselectIcon from '@mui/icons-material/Deselect';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import Fade from '@mui/material/Fade';
import IconButton from '@mui/material/IconButton';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Tooltip from '@mui/material/Tooltip';
import { Edge, EdgeSelectionChange, Node, NodeSelectionChange, useStoreApi } from '@xyflow/react';
import React, { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useStore } from '../../../representation/useStore';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { isNotUtilityNode } from '../../node/NodeTypes';
import {
  FilterSelectionToolbarButtonProps,
  FilterSelectionToolbarButtonStates,
} from './FilterSelectionToolbarButton.types';
import { useFilterContents } from './useFilterContents';
import { useInvokeFilterSelection } from './useInvokeFilterSelection';

export const FilterSelectionToolbarButton = ({}: FilterSelectionToolbarButtonProps) => {
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'filterSelectionToolbarButton' });
  const { invokeFilterSelection, invokeFilterSelectionData } = useInvokeFilterSelection();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { getEdges, getNodes } = useStore();
  const { fetchFilterMenuItems, filterSelectionMenuItems, loading } = useFilterContents();
  const { setSelection } = useSelection();

  const [state, setState] = React.useState<FilterSelectionToolbarButtonStates>({
    anchorEl: null,
  });
  const isOpen = Boolean(state.anchorEl);

  const getSelectedElementsIds = (): string[] => {
    return getNodes()
      .filter((node) => !!node.selected)
      .map((node) => node.id)
      .concat(
        getEdges()
          .filter((edge) => !!edge.selected)
          .map((edge) => edge.id)
      );
  };

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setState((prevState) => ({
      ...prevState,
      anchorEl: event.currentTarget,
    }));
    fetchFilterMenuItems(getSelectedElementsIds());
  };

  const handleClose = () => {
    setState((prevState) => ({ ...prevState, anchorEl: null }));
  };

  const onMenuItemClick = (filterSelectionMenuItem: string) => {
    const selectedElementsIds = getSelectedElementsIds();
    invokeFilterSelection(selectedElementsIds, filterSelectionMenuItem);
    handleClose();
  };

  useEffect(() => {
    if (!!invokeFilterSelectionData) {
      const { newSelection } = invokeFilterSelectionData;
      const selectedEdgeIds = newSelection.filter(
        (newSelectedElementId) => !!store.getState().edgeLookup.get(newSelectedElementId)
      );
      const selectedNodeIds = newSelection.filter((newSelectedElementId) => {
        const node = store.getState().nodeLookup.get(newSelectedElementId);
        return !!node && isNotUtilityNode(node);
      });

      const nodesSelectChanges: NodeSelectionChange[] = [];
      const edgesSelectChanges: EdgeSelectionChange[] = [];

      getNodes().forEach((node) => {
        if (node.selected && !selectedNodeIds.find((nodeId) => nodeId === node.id)) {
          nodesSelectChanges.push({
            id: node.id,
            selected: false,
            type: 'select',
          });
        } else if (selectedNodeIds.find((nodeId) => nodeId === node.id)) {
          nodesSelectChanges.push({
            id: node.id,
            selected: true,
            type: 'select',
          });
        }
      });

      getEdges().forEach((edge) => {
        if (edge.selected && !selectedEdgeIds.find((edgeId) => edgeId === edge.id)) {
          edgesSelectChanges.push({
            id: edge.id,
            selected: false,
            type: 'select',
          });
        } else if (selectedEdgeIds.find((edgeId) => edgeId === edge.id)) {
          edgesSelectChanges.push({
            id: edge.id,
            selected: true,
            type: 'select',
          });
        }
      });

      store.getState().triggerEdgeChanges(edgesSelectChanges);
      store.getState().triggerNodeChanges(nodesSelectChanges);

      if (newSelection.length === 0) {
        setSelection({ entries: [] });
      }
    }
  }, [invokeFilterSelectionData, store, setSelection]);

  const isEmpty = !loading && filterSelectionMenuItems.length === 0;

  return (
    <div>
      <Tooltip title={t('filterSelectedElement')}>
        <IconButton
          id="filter-selection-IconButton"
          aria-controls={isOpen ? 'filter-selection-menu' : undefined}
          aria-haspopup="true"
          aria-expanded={isOpen ? 'true' : undefined}
          onClick={handleClick}
          data-testid={'toolbar_filter_selection'}>
          <DeselectIcon />
          <KeyboardArrowDownIcon />
        </IconButton>
      </Tooltip>
      <Menu
        id="filter-selection-menu"
        slotProps={{
          list: {
            'aria-labelledby': 'filter-selection-IconButton',
          },
        }}
        slots={{ transition: Fade }}
        anchorEl={state.anchorEl}
        open={isOpen}
        onClose={handleClose}>
        {!isEmpty ? (
          filterSelectionMenuItems.map((filterSelectionMenuItem) => {
            return (
              <MenuItem
                key={filterSelectionMenuItem.id}
                data-testid={`filter_selection_${filterSelectionMenuItem.id}`}
                onClick={() => onMenuItemClick(filterSelectionMenuItem.id)}>
                <ListItemText primary={filterSelectionMenuItem.label} />
              </MenuItem>
            );
          })
        ) : (
          <MenuItem>
            <ListItemText primary={t('noAvailableTools')} />
          </MenuItem>
        )}
      </Menu>
    </div>
  );
};
