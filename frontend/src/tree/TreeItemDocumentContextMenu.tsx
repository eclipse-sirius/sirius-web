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
import { Permission } from 'project/Permission';
import PropTypes from 'prop-types';
import React from 'react';

const propTypes = {
  projectId: PropTypes.string.isRequired,
  documentId: PropTypes.string.isRequired,
  x: PropTypes.number.isRequired,
  y: PropTypes.number.isRequired,
  onNewObject: PropTypes.func.isRequired,
  onNewCode: PropTypes.func.isRequired,
  onRenameDocument: PropTypes.func.isRequired,
  onDownload: PropTypes.func.isRequired,
  onDeleteDocument: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};

export const TreeItemDocumentContextMenu = ({
  projectId,
  documentId,
  x,
  y,
  onNewCode,
  onNewObject,
  onRenameDocument,
  onDownload,
  onDeleteDocument,
  onClose,
}) => {
  return (
    <ContextMenu x={x} y={y} caretPosition={LEFT_START} onClose={onClose} data-testid="treeitemdocument-contextmenu">
      <Permission requiredAccessLevel="EDIT">
        <Entry label="New object" onClick={onNewObject} data-testid="new-object" />
      </Permission>
      <Permission requiredAccessLevel="EDIT">
        <Entry icon={<Edit title="" />} label="Rename" onClick={onRenameDocument} data-testid="rename" />
      </Permission>
      <Permission requiredAccessLevel="EDIT">
        <Entry label="Run Code Generator" onClick={onNewCode} data-testid="new-code" />
      </Permission>
      <a
        href={`${httpOrigin}/api/projects/${projectId}/documents/${documentId}`}
        type="application/octet-stream"
        data-testid="download-link">
        <Entry label="Download" onClick={onDownload} data-testid="download" />
      </a>
      <Separator />
      <Permission requiredAccessLevel="EDIT">
        <Entry icon={<Delete title="" />} label="Delete" onClick={onDeleteDocument} data-testid="delete" />
      </Permission>
    </ContextMenu>
  );
};
TreeItemDocumentContextMenu.propTypes = propTypes;
