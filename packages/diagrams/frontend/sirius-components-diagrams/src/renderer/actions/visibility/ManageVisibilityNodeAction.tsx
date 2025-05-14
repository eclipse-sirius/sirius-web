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
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Divider from '@mui/material/Divider';
import Paper from '@mui/material/Paper';
import Stack from '@mui/material/Stack';
import { Node, useNodes, useReactFlow } from '@xyflow/react';
import React, { useState } from 'react';
import Draggable from 'react-draggable';
import { makeStyles } from 'tss-react/mui';
import { NodeData } from '../../DiagramRenderer.types';
import { useHideDiagramElements } from '../../hide/useHideDiagramElements';
import { useGetUpdatedModalPosition } from '../../hooks/useGetUpdatedModalPosition';
import { ManageVisibilityModalHeader } from './ManageVisibilityModalHeader';
import { ManageVisibilityModalListItems } from './ManageVisibilityModalListItems';
import { ManageVisibilityModalMenuCheckBox } from './ManageVisibilityModalMenuCheckBox';
import { ManageVisibilityModalSearchField } from './ManageVisibilityModalSearchField';
import { ManageVisibilityNodeActionProps, ManageVisibilityNodeActionState } from './ManageVisibilityNodeAction.types';

const paletteWidth = 200;

const useManageVisibilityStyle = makeStyles()((theme) => ({
  palette: {
    border: `1px solid ${theme.palette.divider}`,
    borderRadius: '10px',
    zIndex: 5,
    position: 'fixed',
    width: paletteWidth,
  },
}));

const getLabel = (node: Node<NodeData>): string => {
  if (node.data.insideLabel) {
    return node.data.insideLabel.text;
  } else {
    return node.data.targetObjectLabel;
  }
};

export const ManageVisibilityNodeAction = ({
  initialPosition,
  diagramElementId,
  closeDialog,
}: ManageVisibilityNodeActionProps) => {
  // We use useNodes in order to keep the modal in sync
  const nodes = useNodes<Node<NodeData>>();
  const { getNode } = useReactFlow<Node<NodeData>>();
  const targetedNode = getNode(diagramElementId);
  const { classes } = useManageVisibilityStyle();
  const nodeRef = React.useRef<HTMLDivElement>(null);
  const { hideDiagramElements } = useHideDiagramElements();
  const [state, setState] = useState<ManageVisibilityNodeActionState>({
    searchValue: '',
  });

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
    items = items.filter((item) => getLabel(item).includes(state.searchValue));
  }

  const isOneElementHidden = !!items.find((item) => item.hidden);

  const onAllElementCheck = (checked: boolean) => {
    if (checked) {
      const candidateNodesToHide = items.filter((item) => !item.hidden).map((item) => item.id);
      hideDiagramElements(candidateNodesToHide, true);
    } else {
      const candidateNodesToReveal = items.filter((item) => item.hidden).map((item) => item.id);
      hideDiagramElements(candidateNodesToReveal, false);
    }
  };

  const onElementCheck = (value: Node<NodeData>) => {
    hideDiagramElements([value.id], !value.hidden);
  };

  const { getUpdatedModalPosition } = useGetUpdatedModalPosition();
  const position = getUpdatedModalPosition(initialPosition, nodeRef);

  return (
    <Draggable nodeRef={nodeRef} disabled={true} position={position}>
      <Paper ref={nodeRef} className={classes.palette}>
        <ClickAwayListener mouseEvent="onPointerDown" onClickAway={closeDialog}>
          <Stack>
            <ManageVisibilityModalHeader closeDialog={closeDialog}></ManageVisibilityModalHeader>
            <Divider />
            <ManageVisibilityModalSearchField onValueChanged={onSearchValueChanged}></ManageVisibilityModalSearchField>
            <ManageVisibilityModalMenuCheckBox
              isChecked={isOneElementHidden}
              onChecked={onAllElementCheck}></ManageVisibilityModalMenuCheckBox>
            <ManageVisibilityModalListItems items={items} onChecked={onElementCheck}></ManageVisibilityModalListItems>
          </Stack>
        </ClickAwayListener>
      </Paper>
    </Draggable>
  );
};
