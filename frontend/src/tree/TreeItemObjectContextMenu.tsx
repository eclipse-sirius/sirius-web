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
import React from 'react';
import { TreeItemObjectContextMenuProps } from './TreeItemObjectContextMenu.types';

export const TreeItemObjectContextMenu = ({
  x,
  y,
  onCreateNewObject,
  onCreateRepresentation,
  onRenameObject,
  editable,
  onDeleteObject,
  onClose,
  readOnly,
}: TreeItemObjectContextMenuProps) => {
  let renameEntry = null;
  if (editable) {
    renameEntry = (
      <>
        <Entry
          icon={<Edit title="" />}
          label="Rename"
          onClick={onRenameObject}
          data-testid="rename-object"
          disabled={readOnly}></Entry>
        <Separator />
      </>
    );
  }
  return (
    <ContextMenu x={x} y={y} caretPosition={LEFT_START} onClose={onClose} data-testid="treeitemobject-contextmenu">
      <Entry label="New object" onClick={onCreateNewObject} data-testid="new-object" disabled={readOnly} />
      <Entry
        label="New representation"
        onClick={onCreateRepresentation}
        data-testid="new-representation"
        disabled={readOnly}
      />
      <Separator />
      {renameEntry}
      <Entry
        icon={<Delete title="" />}
        label="Delete"
        onClick={onDeleteObject}
        data-testid="delete-object"
        disabled={readOnly}
      />
    </ContextMenu>
  );
};
