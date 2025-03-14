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
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Fragment, forwardRef, useState } from 'react';
import RefreshIcon from '@mui/icons-material/Refresh';
import { UpdateLibraryModal } from '../../modals/update-library/UpdateLibraryModal';
import { useObject } from './useObject';

export const UpdateLibraryTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, readOnly, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLLIElement>
  ) => {
    const [state, setState] = useState({
      open: false,
    });

    let fragment = null;

    const { data, loading } = useObject(editingContextId, item.id);
    if (!loading) {
      if (data) {
        const library = data.viewer?.editingContext?.object?.library;
        if (library) {
          fragment = (
            <Fragment key="update-library-tree-item-context-menu-contribution">
              <MenuItem
                key="update-library"
                onClick={() => setState((prevState) => ({ ...prevState, open: true }))}
                data-testid="update-library"
                disabled={readOnly}
                ref={ref}
                aria-disabled>
                <ListItemIcon>
                  <RefreshIcon fontSize="small" />
                </ListItemIcon>
                <ListItemText primary="Update Library" />
              </MenuItem>
              <UpdateLibraryModal
                open={state.open}
                namespace={library.namespace}
                name={library.name}
                version={library.version}
                title={`Update ${item.label.styledStringFragments.map((fragment) => fragment.text).join('')}`}
                onClose={onClose}
              />
            </Fragment>
          );
        }
      }
    }

    return fragment;
  }
);
