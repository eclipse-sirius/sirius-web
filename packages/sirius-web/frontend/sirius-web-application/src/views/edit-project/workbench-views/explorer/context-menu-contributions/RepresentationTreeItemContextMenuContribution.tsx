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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import AddToPhotosIcon from '@mui/icons-material/AddToPhotos';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Fragment, forwardRef, useEffect } from 'react';
import { useDuplicateRepresentation } from './useDuplicateRepresentation';

export const RepresentationTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, treeId, item, readOnly, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const { setSelection } = useSelection();
    const { duplicateRepresentation, duplicatedRepresentationMetadata } = useDuplicateRepresentation();
    const onDuplicate = () => {
      duplicateRepresentation(editingContextId, item.id);
    };

    useEffect(() => {
      if (duplicatedRepresentationMetadata) {
        setSelection({
          entries: [
            {
              id: duplicatedRepresentationMetadata.id,
            },
          ],
        });
        onClose();
      }
    }, [duplicatedRepresentationMetadata, setSelection]);

    if (!treeId.startsWith('explorer://') || !item.kind.startsWith('siriusComponents://representation')) {
      return null;
    }

    return (
      <Fragment key="representation-tree-item-context-menu-contribution">
        <MenuItem
          key="duplicate-representation"
          onClick={onDuplicate}
          data-testid="duplicate-representation"
          disabled={readOnly}
          ref={ref}
          aria-disabled>
          <ListItemIcon>
            <AddToPhotosIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Duplicate representation" />
        </MenuItem>
      </Fragment>
    );
  }
);
