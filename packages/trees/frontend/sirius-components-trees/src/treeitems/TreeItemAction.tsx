/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import MoreVertIcon from '@mui/icons-material/MoreVert';
import IconButton from '@mui/material/IconButton';
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { getString } from './TreeItem';
import { TreeItemActionProps, TreeItemActionState } from './TreeItemAction.types';
import { TreeItemContextMenu } from './context-menu/TreeItemContextMenu';

const useTreeItemActionStyle = makeStyles()((theme) => ({
  more: {
    hover: {
      backgroundColor: theme.palette.action.hover,
    },
    focus: {
      backgroundColor: theme.palette.action.selected,
    },
  },
}));

export const TreeItemAction = ({
  editingContextId,
  treeId,
  item,
  readOnly,
  depth,
  onExpand,
  onExpandAll,
  onEnterEditingMode,
}: TreeItemActionProps) => {
  const { classes } = useTreeItemActionStyle();
  const [state, setState] = useState<TreeItemActionState>({
    showContextMenu: false,
    menuAnchor: null,
  });

  const openContextMenu = (event) => {
    if (!state.showContextMenu) {
      const { currentTarget } = event;
      setState((prevState) => ({
        ...prevState,
        showContextMenu: true,
        menuAnchor: currentTarget,
      }));
    }
  };

  let contextMenu: JSX.Element | null = null;
  if (state.showContextMenu && state.menuAnchor) {
    const closeContextMenu = () => {
      setState((prevState) => ({
        ...prevState,
        showContextMenu: false,
        menuAnchor: null,
      }));
    };
    const enterEditingMode = () => {
      setState((prevState) => ({
        ...prevState,
        showContextMenu: false,
        menuAnchor: null,
      }));
      onEnterEditingMode();
    };

    contextMenu = (
      <TreeItemContextMenu
        menuAnchor={state.menuAnchor}
        editingContextId={editingContextId}
        treeId={treeId}
        item={item}
        readOnly={readOnly}
        depth={depth}
        onExpand={onExpand}
        onExpandAll={onExpandAll}
        enterEditingMode={enterEditingMode}
        onClose={closeContextMenu}
      />
    );
  }

  return (
    <>
      <IconButton
        className={classes.more}
        size="small"
        onClick={openContextMenu}
        data-testid={`${getString(item.label)}-more`}>
        <MoreVertIcon style={{ fontSize: 12 }} />
      </IconButton>
      {contextMenu}
    </>
  );
};
