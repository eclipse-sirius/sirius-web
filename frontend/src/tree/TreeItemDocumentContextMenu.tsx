/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { httpOrigin } from 'common/URL';
import { ContextMenu, Entry, LEFT_START, Separator } from 'core/contextmenu/ContextMenu';
import { Delete, Edit } from 'icons';
import React from 'react';
import { TreeItemDocumentContextMenuProps } from './TreeItemDocumentContextMenu.types';

export const TreeItemDocumentContextMenu = ({
  projectId,
  documentId,
  x,
  y,
  onNewObject,
  onRenameDocument,
  onDownload,
  onDeleteDocument,
  onClose,
  readOnly,
}: TreeItemDocumentContextMenuProps) => {
  return (
    <ContextMenu x={x} y={y} caretPosition={LEFT_START} onClose={onClose} data-testid="treeitemdocument-contextmenu">
      <Entry label="New object" onClick={onNewObject} data-testid="new-object" disabled={readOnly} />
      <Entry
        icon={<Edit title="" />}
        label="Rename"
        onClick={onRenameDocument}
        data-testid="rename"
        disabled={readOnly}
      />
      <a
        href={`${httpOrigin}/api/projects/${projectId}/documents/${documentId}`}
        type="application/octet-stream"
        data-testid="download-link">
        <Entry label="Download" onClick={onDownload} data-testid="download" />
      </a>
      <Separator />
      <Entry
        icon={<Delete title="" />}
        label="Delete"
        onClick={onDeleteDocument}
        data-testid="delete"
        disabled={readOnly}
      />
    </ContextMenu>
  );
};
