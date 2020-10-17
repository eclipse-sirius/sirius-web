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
import { useCapabilities } from 'capabilities/useCapabilities';
import { httpOrigin } from 'common/URL';
import { ContextMenu, Entry, Separator } from 'core/contextmenu/ContextMenu';
import { Go } from 'core/go/Go';
import { Delete, Edit } from 'icons';
import { Permission } from 'project/Permission';
import { useProject } from 'project/ProjectProvider';
import PropTypes from 'prop-types';
import React from 'react';

const propTypes = {
  x: PropTypes.number.isRequired,
  y: PropTypes.number.isRequired,
  onCreateDocument: PropTypes.func.isRequired,
  onUploadDocument: PropTypes.func.isRequired,
  onRename: PropTypes.func.isRequired,
  onDelete: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};
export const EditProjectNavbarContextMenu = ({
  x,
  y,
  onCreateDocument,
  onUploadDocument,
  onRename,
  onDelete,
  onClose,
}) => {
  const { id } = useProject() as any;

  const capabilities = useCapabilities();
  const settingsURL = `/projects/${id}/settings`;
  let settingsEntry;
  if (capabilities.overrides(settingsURL)) {
    settingsEntry = (
      <Go to={settingsURL} data-testid="settings-link">
        <Entry label="Settings" data-testid="settings" />
      </Go>
    );
  }

  return (
    <ContextMenu caretPosition="top" x={x} y={y} onClose={onClose} data-testid="navbar-contextmenu">
      <Permission requiredAccessLevel="EDIT">
        <Entry label="New model" onClick={onCreateDocument} data-testid="new-document" />
      </Permission>
      <Permission requiredAccessLevel="EDIT">
        <Entry label="Upload model" onClick={onUploadDocument} data-testid="upload-document" />
      </Permission>
      <Separator />
      <Permission requiredAccessLevel="EDIT">
        <Entry icon={<Edit title="" />} label="Rename" onClick={onRename} data-testid="rename" />
      </Permission>
      <a
        href={`${httpOrigin}/api/projects/${id}`}
        type="application/octet-stream"
        onClick={onClose}
        data-testid="download-link">
        <Entry label="Download" data-testid="download" />
      </a>
      {settingsEntry}
      <Separator />
      <a href={`/projects`} data-testid="projects-link">
        <Entry label="Back to all projects" data-testid="projects" />
      </a>
      <Separator />
      <Permission requiredAccessLevel="EDIT">
        <Entry icon={<Delete title="" />} label="Delete" onClick={onDelete} data-testid="delete" />
      </Permission>
    </ContextMenu>
  );
};
EditProjectNavbarContextMenu.propTypes = propTypes;
