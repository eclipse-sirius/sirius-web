/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import Box from '@mui/material/Box';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';
import { Theme, useTheme } from '@mui/material/styles';
import { Node, useNodes, useReactFlow } from '@xyflow/react';
import React, { useState } from 'react';
import Draggable from 'react-draggable';
import { NodeData } from '../../DiagramRenderer.types';
import { useHideDiagramElements } from '../../hide/useHideDiagramElements';
import { useGetUpdatedModalPosition } from '../../hooks/useGetUpdatedModalPosition';
import { getNodeLabel } from '../../node/NodeUtils';
import { ManageVisibilityModalHeader } from './ManageVisibilityModalHeader';
import { ManageVisibilityModalListItems } from './ManageVisibilityModalListItems';
import { ManageVisibilityModalMenuCheckBox } from './ManageVisibilityModalMenuCheckBox';
import { ManageVisibilityModalSearchField } from './ManageVisibilityModalSearchField';
import { ManageVisibilityNodeActionProps, ManageVisibilityNodeActionState } from './ManageVisibilityNodeAction.types';

const paletteWidth = 200;

export const ManageVisibilityNodeAction = ({
  initialPosition,
  diagramElementId,
  closeDialog,
}: ManageVisibilityNodeActionProps) => {
  const theme: Theme = useTheme();
  // We use useNodes in order to keep the modal in sync
  const nodes = useNodes<Node<NodeData>>();
  const { getNode } = useReactFlow<Node<NodeData>>();
  const targetedNode = getNode(diagramElementId);
  const nodeRef = React.useRef<HTMLDivElement>(null);
  const { hideDiagramElements } = useHideDiagramElements();
  const [state, setState] = useState<ManageVisibilityNodeActionState>({
    searchValue: '',
  });

  const { getUpdatedModalPosition } = useGetUpdatedModalPosition();
  const position = getUpdatedModalPosition(initialPosition, nodeRef);

  if (!targetedNode) {
    return null;
  }

  const getChildren = (parent: Node<NodeData>): Node<NodeData>[] => nodes.filter((node) => parent.id === node.parentId);

  const getAllChildren = (children: Node<NodeData>[], parent: Node<NodeData>): Node<NodeData>[] => {
    getChildren(parent).forEach((child) => {
      children.push(child);
      getAllChildren(children, child);
    });
    return children;
  };

  let items: Node<NodeData>[] = getAllChildren([], targetedNode);

  const onSearchValueChanged = (newValue: string): void => {
    setState((prevState) => ({ ...prevState, searchValue: newValue }));
  };

  if (state.searchValue) {
    items = items.filter((node) => getNodeLabel(node).includes(state.searchValue));
  }

  const isOneElementRevealed = !!items.find((item) => !item.hidden);

  const onCheckingAllElement = (checked: boolean) => {
    if (checked) {
      const candidateNodesToReveal = items.filter((item) => item.hidden).map((item) => item.id);
      hideDiagramElements(candidateNodesToReveal, false);
    } else {
      const candidateNodesToHide = items.filter((item) => !item.hidden).map((item) => item.id);
      hideDiagramElements(candidateNodesToHide, true);
    }
  };

  const onListItemClick = (node: Node<NodeData>) => {
    hideDiagramElements([node.id], !node.hidden);
  };

  return (
    <Draggable nodeRef={nodeRef} disabled={true} position={position}>
      <Paper
        ref={nodeRef}
        sx={{
          border: `1px solid ${theme.palette.divider}`,
          borderRadius: '10px',
          zIndex: 5,
          position: 'fixed',
          width: paletteWidth,
        }}>
        <ClickAwayListener mouseEvent="onPointerDown" onClickAway={closeDialog}>
          <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'stretch' }}>
            <ManageVisibilityModalHeader closeDialog={closeDialog}></ManageVisibilityModalHeader>
            <Divider />
            <ManageVisibilityModalSearchField onValueChanged={onSearchValueChanged}></ManageVisibilityModalSearchField>
            <ManageVisibilityModalMenuCheckBox
              isOneElementChecked={isOneElementRevealed}
              onCheckingAllElement={onCheckingAllElement}
              diagramElementId={diagramElementId}></ManageVisibilityModalMenuCheckBox>
            <ManageVisibilityModalListItems
              nodes={items}
              onListItemClick={onListItemClick}></ManageVisibilityModalListItems>
          </Box>
        </ClickAwayListener>
      </Paper>
    </Draggable>
  );
};
