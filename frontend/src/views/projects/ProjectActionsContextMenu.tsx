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
import React from 'react';
import PropTypes from 'prop-types';

import { httpOrigin } from 'common/URL';
import { ContextMenu, Entry } from 'core/contextmenu/ContextMenu';
import { Delete } from 'icons/Delete';
import { Edit } from 'icons/Edit';
import { Download } from 'icons/Download';

const propTypes = {
  projectId: PropTypes.string.isRequired,
  x: PropTypes.number.isRequired,
  y: PropTypes.number.isRequired,
  onDelete: PropTypes.func.isRequired,
  onRename: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};
export const ProjectActionsContextMenu = ({ projectId, x, y, onDelete, onRename, onClose }) => {
  return (
    <ContextMenu x={x} y={y} caretPosition="left" onClose={onClose} data-testid="project-actions-contextmenu">
      <Entry icon={<Edit title="" />} label="Rename" onClick={onRename} data-testid="rename" />
      <a
        href={`${httpOrigin}/api/projects/${projectId}`}
        type="application/octet-stream"
        onClick={onClose}
        data-testid="download-link">
        <Entry icon={<Download title="" />} label="Download" data-testid="download" />
      </a>
      <Entry icon={<Delete title="" />} label="Delete" onClick={onDelete} data-testid="delete" />
    </ContextMenu>
  );
};
ProjectActionsContextMenu.propTypes = propTypes;
