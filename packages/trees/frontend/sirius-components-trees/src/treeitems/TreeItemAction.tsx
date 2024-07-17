/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import IconButton from '@material-ui/core/IconButton';
import { makeStyles } from '@material-ui/core/styles';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import { useState } from 'react';
import { TreeItemActionProps, TreeItemActionState } from './TreeItemAction.types';
import { TreeItemContextMenu } from './TreeItemContextMenu';

const useTreeItemActionStyle = makeStyles((theme) => ({
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
  const classes = useTreeItemActionStyle();
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

  let contextMenu = null;
  if (state.showContextMenu) {
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
      <IconButton className={classes.more} size="small" onClick={openContextMenu} data-testid={`${item.label}-more`}>
        <MoreVertIcon style={{ fontSize: 12 }} />
      </IconButton>
      {contextMenu}
    </>
  );
};
