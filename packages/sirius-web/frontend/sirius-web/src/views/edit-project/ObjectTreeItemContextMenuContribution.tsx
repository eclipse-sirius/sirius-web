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
import { NewObjectModal, NewRepresentationModal } from '@eclipse-sirius/sirius-components';
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { DynamicDialogModal } from '@eclipse-sirius/sirius-components-dynamicdialog';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import AddIcon from '@material-ui/icons/Add';
import { Fragment, forwardRef, useState } from 'react';

type Modal = 'CreateNewObject' | 'CreateNewRepresentation' | 'OpenDynamicDialog';

export const ObjectTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, setSelection, expandItem, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [modal, setModal] = useState<Modal>(null);

    const onObjectCreated = (selection: Selection) => {
      setSelection(selection);
      expandItem();
      onClose();
    };

    let modalElement = null;
    if (modal === 'CreateNewObject') {
      modalElement = (
        <NewObjectModal
          editingContextId={editingContextId}
          item={item}
          onObjectCreated={onObjectCreated}
          onClose={onClose}
        />
      );
    } else if (modal === 'CreateNewRepresentation') {
      modalElement = (
        <NewRepresentationModal
          editingContextId={editingContextId}
          item={item}
          onRepresentationCreated={onObjectCreated}
          onClose={onClose}
        />
      );
    } else if (modal === 'OpenDynamicDialog') {
      modalElement = (
        <DynamicDialogModal
          dialogDescriptionId="dialogId"
          objectId={item.id}
          onClose={onClose}
          editingContextId={editingContextId}
          onMutationDone={onClose}></DynamicDialogModal>
      );
    }

    return (
      <Fragment key="object-tree-item-context-menu-contribution">
        <MenuItem
          key="new-object"
          onClick={() => setModal('CreateNewObject')}
          data-testid="new-object"
          disabled={readOnly}
          ref={ref}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="New object" />
        </MenuItem>
        <MenuItem
          key="new-representation"
          onClick={() => setModal('CreateNewRepresentation')}
          data-testid="new-representation"
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="New representation" />
        </MenuItem>
        <MenuItem
          key="open-dynamic-dialog"
          onClick={() => setModal('OpenDynamicDialog')}
          data-testid="new-representation"
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Dynamic Dialog" />
        </MenuItem>
        {modalElement}
      </Fragment>
    );
  }
);
