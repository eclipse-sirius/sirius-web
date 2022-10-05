/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { ServerContext } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import GetAppIcon from '@material-ui/icons/Image';
import React, { forwardRef, Fragment, useContext } from 'react';

export const DiagramTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLAnchorElement>
  ) => {
    const { httpOrigin } = useContext(ServerContext);

    return (
      <Fragment key="diagram-tree-item-context-menu-contribution">
        <MenuItem
          key="exportSVG"
          divider
          ref={ref}
          component="a"
          onClick={onClose}
          href={`${httpOrigin}/api/editingcontexts/${editingContextId}/representations/${item.id}`}
          type="application/octet-stream"
          data-testid="exportSVG">
          <ListItemIcon>
            <GetAppIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Export to SVG" />
        </MenuItem>
      </Fragment>
    );
  }
);
