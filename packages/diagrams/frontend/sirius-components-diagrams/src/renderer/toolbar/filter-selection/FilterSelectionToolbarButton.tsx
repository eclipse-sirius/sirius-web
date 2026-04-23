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
import { Edge, Node, useStoreApi } from '@xyflow/react';
import React, { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { useStore } from '../../../representation/useStore';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { FilterSelectionToolbarButtonProps } from './FilterSelectionToolbarButton.types';
import { useFilterContents } from './useFilterContents';
import { useInvokeFilterSelection } from './useInvokeFilterSelection';
export const FilterSelectionToolbarButton = ({}: FilterSelectionToolbarButtonProps) => {
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'diagramToolbar' });
  const { invokeFilterSelection, invokeFilterSelectionData } = useInvokeFilterSelection();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { setNodes, setEdges } = useStore();
  const { fetchFilterMenuItems, filterSelectionMenuItems, loading } = useFilterContents();
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

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
    const selectedElementsIds = getSelectedElementsIds();
    fetchFilterMenuItems(selectedElementsIds);
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const onMenuItemClick = (filterSelectionMenuItem: string) => {
    const selectedElementsIds = getSelectedElementsIds();
    invokeFilterSelection(selectedElementsIds, filterSelectionMenuItem);
    setAnchorEl(null);
  };

  useEffect(() => {
    if (!!invokeFilterSelectionData) {
      const { newSelection } = invokeFilterSelectionData;
      const selectedEdgeIds = newSelection.filter(
        (newSelectedElementId) => !!store.getState().edgeLookup.get(newSelectedElementId)
      );
      const selectedNodeIds = newSelection.filter(
        (newSelectedElementId) =>
          !!store.getState().nodeLookup.get(newSelectedElementId) &&
          store.getState().nodeLookup.get(newSelectedElementId)?.type !== 'edgeAnchorNode'
      );

      setNodes((previousNodes) =>
        previousNodes.map((previousNode) => {
          if (previousNode.selected && !selectedNodeIds.find((nodeId) => nodeId === previousNode.id)) {
            return {
              ...previousNode,
              selected: false,
              data: {
                ...previousNode.data,
                isLastNodeSelected: false,
                connectionLinePositionOnNode: 'none',
              },
            };
          } else if (selectedNodeIds.find((nodeId) => nodeId === previousNode.id)) {
            return {
              ...previousNode,
              selected: true,
              data: {
                ...previousNode.data,
                isLastNodeSelected: previousNode.id === selectedNodeIds.at(selectedNodeIds.length - 1),
                connectionLinePositionOnNode: 'none',
              },
            };
          } else {
            return previousNode;
          }
        })
      );
      setEdges((previousEdges) =>
        previousEdges.map((previousEdge) => {
          if (previousEdge.selected && !selectedEdgeIds.find((edgeId) => edgeId === previousEdge.id)) {
            return {
              ...previousEdge,
              selected: false,
            };
          } else if (!previousEdge.selected && selectedEdgeIds.find((edgeId) => edgeId === previousEdge.id)) {
            return {
              ...previousEdge,
              selected: true,
            };
          } else {
            return previousEdge;
          }
        })
      );
    }
  }, [invokeFilterSelectionData]);

  return (
    <div>
      <Tooltip title={t('filterSelectedElement')}>
        <IconButton
          id="fade-IconButton"
          aria-controls={open ? 'fade-menu' : undefined}
          aria-haspopup="true"
          aria-expanded={open ? 'true' : undefined}
          onClick={handleClick}
          data-testid={'toolbar_filter_selection'}>
          <DeselectIcon />
          <KeyboardArrowDownIcon />
        </IconButton>
      </Tooltip>
      <Menu
        id="fade-menu"
        slotProps={{
          list: {
            'aria-labelledby': 'fade-button',
          },
        }}
        slots={{ transition: Fade }}
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}>
        {!loading && filterSelectionMenuItems.length > 0
          ? filterSelectionMenuItems.map((filterSelectionMenuItem) => {
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
