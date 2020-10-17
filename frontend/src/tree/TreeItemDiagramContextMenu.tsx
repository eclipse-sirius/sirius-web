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
import { ContextMenu, Entry, Separator } from 'core/contextmenu/ContextMenu';
import { Delete, Edit } from 'icons';
import { Permission } from 'project/Permission';
import PropTypes from 'prop-types';
import React from 'react';

const propTypes = {
  x: PropTypes.number.isRequired,
  y: PropTypes.number.isRequired,
  onRenameRepresentation: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};
export const TreeItemDiagramContextMenu = ({ x, y, onDeleteRepresentation, onRenameRepresentation, onClose }) => {
  return (
    <ContextMenu x={x} y={y} caretPosition="left" onClose={onClose} data-testid="treeitemdiagram-contextmenu">
      <Permission requiredAccessLevel="EDIT">
        <Entry
          icon={<Edit title="" />}
          label="Rename"
          onClick={onRenameRepresentation}
          data-testid="rename-representation"
        />
      </Permission>
      <Separator />
      <Permission requiredAccessLevel="EDIT">
        <Entry
          icon={<Delete title="" />}
          label="Delete"
          onClick={onDeleteRepresentation}
          data-testid="delete-representation"
        />
      </Permission>
    </ContextMenu>
  );
};
TreeItemDiagramContextMenu.propTypes = propTypes;
