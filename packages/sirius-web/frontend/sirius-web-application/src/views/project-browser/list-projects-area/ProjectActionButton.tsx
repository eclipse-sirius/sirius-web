/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import { ServerContext, ServerContextValue, useData } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Tooltip from '@material-ui/core/Tooltip';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import GetAppIcon from '@material-ui/icons/GetApp';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import { ComponentType, useContext, useState } from 'react';
import { DeleteProjectModal } from '../../../modals/delete-project/DeleteProjectModal';
import { RenameProjectModal } from '../../../modals/rename-project/RenameProjectModal';
import {
  ProjectActionButtonProps,
  ProjectActionButtonState,
  ProjectContextMenuModal,
  ProjectContextMenuModalProps,
  ProjectContextMenuProps,
  ProjectContextMenuState,
} from './ProjectActionButton.types';
import { projectActionButtonMenuItemExtensionPoint } from './ProjectActionButtonExtensionPoints';

export const ProjectActionButton = ({ project, onChange }: ProjectActionButtonProps) => {
  const [state, setState] = useState<ProjectActionButtonState>({
    contextMenuAnchorElement: null,
  });

  const onClick: React.MouseEventHandler<HTMLButtonElement> = (event) =>
    setState((prevState) => ({ ...prevState, contextMenuAnchorElement: event.currentTarget }));

  const onCloseContextMenu = () => setState((prevState) => ({ ...prevState, contextMenuAnchorElement: null }));

  return (
    <>
      <Tooltip title="More">
        <IconButton aria-label="more" onClick={onClick} size="small" data-testid="more">
          <MoreHorizIcon fontSize="small" />
        </IconButton>
      </Tooltip>
      {state.contextMenuAnchorElement ? (
        <ProjectContextMenu
          menuAnchor={state.contextMenuAnchorElement}
          project={project}
          onChange={onChange}
          onClose={onCloseContextMenu}
        />
      ) : null}
    </>
  );
};

const modals: Record<ProjectContextMenuModal, ComponentType<ProjectContextMenuModalProps>> = {
  RENAME_PROJECT_DIALOG: RenameProjectModal,
  DELETE_PROJECT_DIALOG: DeleteProjectModal,
};

const ProjectContextMenu = ({ menuAnchor, project, onChange, onClose }: ProjectContextMenuProps) => {
  const [state, setState] = useState<ProjectContextMenuState>({
    modalToDisplay: null,
  });

  const onRename = () => setState((prevState) => ({ ...prevState, modalToDisplay: 'RENAME_PROJECT_DIALOG' }));
  const onDelete = () => setState((prevState) => ({ ...prevState, modalToDisplay: 'DELETE_PROJECT_DIALOG' }));
  const onCancel = () => setState((prevState) => ({ ...prevState, modalToDisplay: null }));

  const onSuccess = () => {
    setState((prevState) => ({ ...prevState, modalToDisplay: null }));
    onChange();
  };

  const ModalComponent = modals[state.modalToDisplay];

  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { data: menuItemProps } = useData(projectActionButtonMenuItemExtensionPoint);
  return (
    <>
      <Menu
        data-testid="project-actions-contextmenu"
        id="project-actions-contextmenu"
        anchorEl={menuAnchor}
        keepMounted
        open={true}
        onClose={onClose}>
        <MenuItem onClick={onRename} data-testid="rename">
          <ListItemIcon>
            <EditIcon />
          </ListItemIcon>
          <ListItemText primary="Rename" />
        </MenuItem>
        <MenuItem component="a" href={`${httpOrigin}/api/projects/${project.id}`} type="application/octet-stream">
          <ListItemIcon>
            <GetAppIcon />
          </ListItemIcon>
          <ListItemText primary="Download" />
        </MenuItem>
        <MenuItem onClick={onDelete} data-testid="delete">
          <ListItemIcon>
            <DeleteIcon />
          </ListItemIcon>
          <ListItemText primary="Delete" />
        </MenuItem>
        {menuItemProps.map((props, index) => (
          <MenuItem {...props} key={index} />
        ))}
      </Menu>
      {ModalComponent ? <ModalComponent project={project} onSuccess={onSuccess} onCancel={onCancel} /> : null}
    </>
  );
};
