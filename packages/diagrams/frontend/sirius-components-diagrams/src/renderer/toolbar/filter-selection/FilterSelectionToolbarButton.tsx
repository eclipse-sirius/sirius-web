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

import DeselectIcon from '@mui/icons-material/Deselect';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import Fade from '@mui/material/Fade';
import IconButton from '@mui/material/IconButton';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Tooltip from '@mui/material/Tooltip';
import { Edge, EdgeSelectionChange, Node, NodeSelectionChange, useStoreApi } from '@xyflow/react';
import React from 'react';
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
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramToolbar' });
  const { invokeFilterSelection } = useInvokeFilterSelection();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { getNodes, getEdges } = useStore();
  const { fetchFilterMenuItems } = useFilterContents();

  const [state, setState] = React.useState<FilterSelectionToolbarButtonStates>({
    anchorEl: null,
    filterMenuItems: [],
  });
  const isOpen = Boolean(state.anchorEl);

  const getSelectedElementsIds = (): string[] => {
    return store
      .getState()
      .nodes.filter((node) => !!node.selected)
      .map((node) => node.id)
      .concat(
        store
          .getState()
          .edges.filter((edge) => !!edge.selected)
          .map((edge) => edge.id)
      );
  };

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    const filterSelectionMenuItems = fetchFilterMenuItems(getSelectedElementsIds());
    setState((prevState) => ({
      ...prevState,
      anchorEl: event.currentTarget,
      filterMenuItems: filterSelectionMenuItems,
    }));
  };

  const handleClose = () => {
    setState((prevState) => ({ ...prevState, anchorEl: null, filterMenuItems: [] }));
  };

  const onMenuItemClick = (filterSelectionMenuItem: string) => {
    const currentSelectedElementsIds = getSelectedElementsIds();
    const newlySelectedElementsIds = invokeFilterSelection(currentSelectedElementsIds, filterSelectionMenuItem);

    const selectedEdgeIds = newlySelectedElementsIds.filter(
      (newSelectedElementId) => !!store.getState().edgeLookup.get(newSelectedElementId)
    );
    const selectedNodeIds = newlySelectedElementsIds.filter((newSelectedElementId) => {
      const node = store.getState().nodeLookup.get(newSelectedElementId);
      return node && isNotUtilityNode(node);
    });

    const selectedNodeIdSet = new Set(selectedNodeIds);
    const selectedEdgeIdSet = new Set(selectedEdgeIds);

    const nodeChanges: NodeSelectionChange[] = [];
    getNodes().forEach((node) => {
      if (node.selected && !selectedNodeIdSet.has(node.id)) {
        nodeChanges.push({ id: node.id, selected: false, type: 'select' });
      } else if (!node.selected && selectedNodeIdSet.has(node.id)) {
        nodeChanges.push({ id: node.id, selected: true, type: 'select' });
      }
    });
    store.getState().triggerNodeChanges(nodeChanges);

    const edgeChanges: EdgeSelectionChange[] = [];
    getEdges().forEach((edge) => {
      if (edge.selected && !selectedEdgeIdSet.has(edge.id)) {
        edgeChanges.push({ id: edge.id, selected: false, type: 'select' });
      } else if (!edge.selected && selectedEdgeIdSet.has(edge.id)) {
        edgeChanges.push({ id: edge.id, selected: true, type: 'select' });
      }
    });
    store.getState().triggerEdgeChanges(edgeChanges);

    setState((prevState) => ({ ...prevState, anchorEl: null }));
  };

  return (
    <div>
      <Tooltip title={t('filterSelectedElement')}>
        <IconButton
          id="manage-selection-IconButton"
          aria-controls={isOpen ? 'manage-selection-menu' : undefined}
          aria-haspopup="true"
          aria-expanded={isOpen ? 'true' : undefined}
          onClick={handleClick}
          data-testid={'toolbar_filter_selection'}>
          <DeselectIcon />
          <KeyboardArrowDownIcon />
        </IconButton>
      </Tooltip>
      <Menu
        id="manage-selection-menu"
        slotProps={{
          list: {
            'aria-labelledby': 'manage-selection-button',
          },
        }}
        slots={{ transition: Fade }}
        anchorEl={state.anchorEl}
        open={isOpen}
        onClose={handleClose}>
        {state.filterMenuItems.length > 0
          ? state.filterMenuItems.map((filterSelectionMenuItem) => {
              return (
                <MenuItem
                  key={filterSelectionMenuItem.id}
                  data-testid={`filter_selection_${filterSelectionMenuItem.id}`}
                  onClick={() => onMenuItemClick(filterSelectionMenuItem.id)}>
                  <ListItemText primary={filterSelectionMenuItem.label} />
                </MenuItem>
              );
            })
          : null}
      </Menu>
    </div>
  );
};
