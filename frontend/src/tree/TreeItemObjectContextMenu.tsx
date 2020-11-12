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
import { ContextMenu, Entry, LEFT_START, Separator } from 'core/contextmenu/ContextMenu';
import { Delete, Edit } from 'icons';
import { Permission } from 'project/Permission';
import PropTypes from 'prop-types';
import React from 'react';

const propTypes = {
  x: PropTypes.number.isRequired,
  y: PropTypes.number.isRequired,
  onCreateNewObject: PropTypes.func.isRequired,
  onCreateRepresentation: PropTypes.func.isRequired,
  editable: PropTypes.bool,
  onRenameObject: PropTypes.func,
  onDeleteObject: PropTypes.func.isRequired,
  onClose: PropTypes.func.isRequired,
};
export const TreeItemObjectContextMenu = ({
  x,
  y,
  onCreateNewObject,
  onCreateRepresentation,
  onRenameObject,
  editable,
  onDeleteObject,
  onClose,
}) => {
  let renameEntry = null;
  if (editable) {
    renameEntry = (
      <>
        <Permission requiredAccessLevel="EDIT">
          <Entry icon={<Edit title="" />} label="Rename" onClick={onRenameObject} data-testid="rename-object"></Entry>
        </Permission>
        <Separator />
      </>
    );
  }
  return (
    <ContextMenu x={x} y={y} caretPosition={LEFT_START} onClose={onClose} data-testid="treeitemobject-contextmenu">
      <Permission requiredAccessLevel="EDIT">
        <Entry label="New object" onClick={onCreateNewObject} data-testid="new-object" />
      </Permission>
      <Permission requiredAccessLevel="EDIT">
        <Entry label="New representation" onClick={onCreateRepresentation} data-testid="new-representation" />
      </Permission>
      <Separator />
      {renameEntry}
      <Permission requiredAccessLevel="EDIT">
        <Entry icon={<Delete title="" />} label="Delete" onClick={onDeleteObject} data-testid="delete-object" />
      </Permission>
    </ContextMenu>
  );
};
TreeItemObjectContextMenu.propTypes = propTypes;
