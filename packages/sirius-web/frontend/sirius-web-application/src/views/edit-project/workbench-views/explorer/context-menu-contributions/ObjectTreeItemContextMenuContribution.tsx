/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import AddIcon from '@mui/icons-material/Add';
import AddToPhotosIcon from '@mui/icons-material/AddToPhotos';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Fragment, forwardRef, useState } from 'react';
import { DuplicateObjectModal } from '../../../../../modals/duplicate-object/DuplicateObjectModal';
import { NewObjectModal } from '../../../../../modals/new-object/NewObjectModal';
import { NewRepresentationModal } from '../../../../../modals/new-representation/NewRepresentationModal';

type Modal = 'CreateNewObject' | 'CreateNewRepresentation' | 'DuplicateObject';

export const ObjectTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, treeId, item, readOnly, expandItem, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [modal, setModal] = useState<Modal>(null);
    const { setSelection } = useSelection();

    if (!treeId.startsWith('explorer://') || !item.kind.startsWith('siriusComponents://semantic')) {
      return null;
    }

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
    } else if (modal === 'DuplicateObject') {
      modalElement = (
        <DuplicateObjectModal
          editingContextId={editingContextId}
          objectToDuplicateId={item.id}
          objectToDuplicateKind={item.kind}
          onObjectDuplicated={onObjectCreated}
          onClose={onClose}
        />
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
          key="duplicate-object"
          onClick={() => setModal('DuplicateObject')}
          data-testid="duplicate-object"
          disabled={readOnly}
          aria-disabled>
          <ListItemIcon>
            <AddToPhotosIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Duplicate object" />
        </MenuItem>
        {modalElement}
      </Fragment>
    );
  }
);
