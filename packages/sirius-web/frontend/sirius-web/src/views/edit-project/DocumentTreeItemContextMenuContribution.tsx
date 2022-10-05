/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
import { NewRootObjectModal } from '@eclipse-sirius/sirius-components';
import { Selection, ServerContext } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import AddIcon from '@material-ui/icons/Add';
import GetAppIcon from '@material-ui/icons/GetApp';
import { forwardRef, Fragment, useContext, useState } from 'react';

type Modal = 'CreateNewRootObject';

export const DocumentTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, setSelection, expandItem, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const { httpOrigin } = useContext(ServerContext);
    const [modal, setModal] = useState<Modal>(null);

    const onObjectCreated = (selection: Selection) => {
      setSelection(selection);
      expandItem();
      onClose();
    };

    let modalElement = null;
    if (modal === 'CreateNewRootObject') {
      modalElement = (
        <NewRootObjectModal
          editingContextId={editingContextId}
          item={item}
          onObjectCreated={onObjectCreated}
          onClose={onClose}
        />
      );
    }

    return (
      <Fragment key="document-tree-item-context-menu-contribution">
        <MenuItem
          key="new-object"
          data-testid="new-object"
          onClick={() => setModal('CreateNewRootObject')}
          ref={ref}
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="New object" />
        </MenuItem>
        <MenuItem
          key="download"
          divider
          onClick={onClose}
          component="a"
          href={`${httpOrigin}/api/editingcontexts/${editingContextId}/documents/${item.id}`}
          type="application/octet-stream"
          data-testid="download"
          aria-disabled>
          <ListItemIcon>
            <GetAppIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Download" aria-disabled />
        </MenuItem>
        {modalElement}
      </Fragment>
    );
  }
);

/*
if (!item.expanded && item.hasChildren) {
      onExpand(item.id, depth);
    }
    const { id, label, kind } = object;
    setSelection({ id, label, kind });
*/
