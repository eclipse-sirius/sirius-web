/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { httpOrigin, ContextMenu, Entry, Separator, TOP_START, Delete, Edit } from '@eclipse-sirius/sirius-components';
import { Link } from 'react-router-dom';
import { EditProjectNavbarContextMenuProps } from 'views/edit-project/EditProjectNavbar/EditProjectNavbarContextMenu.types';

export const EditProjectNavbarContextMenu = ({
  x,
  y,
  projectId,
  onCreateDocument,
  onUploadDocument,
  onRename,
  onDelete,
  onClose,
}: EditProjectNavbarContextMenuProps) => {
  return (
    <ContextMenu caretPosition={TOP_START} x={x} y={y} onClose={onClose} data-testid="navbar-contextmenu">
      <Entry label="New model" onClick={onCreateDocument} data-testid="new-document" />
      <Entry label="Upload model" onClick={onUploadDocument} data-testid="upload-document" />
      <Separator />
      <Entry icon={<Edit title="" />} label="Rename" onClick={onRename} data-testid="rename" />
      <a
        href={`${httpOrigin}/api/projects/${projectId}`}
        type="application/octet-stream"
        onClick={onClose}
        data-testid="download-link">
        <Entry label="Download" data-testid="download" />
      </a>
      <Entry icon={<Delete title="" />} label="Delete" onClick={onDelete} data-testid="delete" />
      <Separator />
      <Link to={`/projects`} data-testid="projects-link">
        <Entry label="Back to all projects" data-testid="projects" />
      </Link>
    </ContextMenu>
  );
};
