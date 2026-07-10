/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import {
  PaletteToolOverriddenContributionComponentProps,
  ToolListItemText,
} from '@eclipse-sirius/sirius-components-palette';
import { TreePaletteContext, TreePaletteContextValue } from '@eclipse-sirius/sirius-components-trees';
import GetAppIcon from '@mui/icons-material/GetApp';
import ListItemIcon from '@mui/material/ListItemIcon';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, useContext } from 'react';

export const DownloadDocumentToolContribution = forwardRef(
  (
    { onInvoked, tool }: PaletteToolOverriddenContributionComponentProps,
    ref: React.ForwardedRef<HTMLAnchorElement>
  ) => {
    const { editingContextId, item, onClose } = useContext<TreePaletteContextValue>(TreePaletteContext);
    const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

    const handleClick = () => {
      onInvoked();
      onClose();
    };

    return (
      <MenuItem
        key="download-document"
        divider
        onClick={handleClick}
        ref={ref}
        component="a"
        href={`${httpOrigin}/api/editingcontexts/${editingContextId}/documents/${item.id}`}
        type="application/octet-stream"
        data-testid="download">
        <ListItemIcon>
          <GetAppIcon fontSize="small" />
        </ListItemIcon>
        <ToolListItemText label={tool.label} searchedValue={null} />
      </MenuItem>
    );
  }
);
