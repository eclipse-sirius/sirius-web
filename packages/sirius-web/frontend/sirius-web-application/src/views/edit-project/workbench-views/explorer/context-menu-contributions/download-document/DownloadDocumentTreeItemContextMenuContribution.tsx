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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import { TreeItemContextMenuComponentProps } from '@eclipse-sirius/sirius-components-trees';
import GetAppIcon from '@mui/icons-material/GetApp';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { forwardRef, useContext } from 'react';
import { useTranslation } from 'react-i18next';

export const DownloadDocumentTreeItemContextMenuContribution = forwardRef(
  (
    { editingContextId, item, onClose }: TreeItemContextMenuComponentProps,
    ref: React.ForwardedRef<HTMLAnchorElement>
  ) => {
    const { t } = useTranslation('sirius-web-application', {
      keyPrefix: 'downloadDocumentTreeItemContextMenuContribution',
    });
    const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

    return (
      <MenuItem
        key="download-document"
        divider
        onClick={onClose}
        ref={ref}
        component="a"
        href={`${httpOrigin}/api/editingcontexts/${editingContextId}/documents/${item.id}`}
        type="application/octet-stream"
        data-testid="download"
        aria-disabled>
        <ListItemIcon>
          <GetAppIcon fontSize="small" />
        </ListItemIcon>
        <ListItemText primary={t('download')} aria-disabled />
      </MenuItem>
    );
  }
);
