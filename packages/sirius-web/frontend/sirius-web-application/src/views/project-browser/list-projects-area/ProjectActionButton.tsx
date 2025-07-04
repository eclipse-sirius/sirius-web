/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import {
  ComponentExtension,
  ServerContext,
  ServerContextValue,
  useComponent,
  useComponents,
} from '@eclipse-sirius/sirius-components-core';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import GetAppIcon from '@mui/icons-material/GetApp';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import IconButton from '@mui/material/IconButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Tooltip from '@mui/material/Tooltip';
import { ComponentType, useContext, useState } from 'react';
import { DeleteProjectModal } from '../../../modals/delete-project/DeleteProjectModal';
import { RenameProjectModal } from '../../../modals/rename-project/RenameProjectModal';
import {
  ProjectActionButtonProps,
  ProjectActionButtonState,
  ProjectContextMenuEntryProps,
  ProjectContextMenuModal,
  ProjectContextMenuModalProps,
  ProjectContextMenuProps,
  ProjectContextMenuState,
} from './ProjectActionButton.types';
import {
  projectContextMenuContainerExtensionPoint,
  projectContextMenuEntryExtensionPoint,
} from './ProjectContextMenuExtensionPoints';

export const ProjectActionButton = ({ project, onChange }: ProjectActionButtonProps) => {
  const [state, setState] = useState<ProjectActionButtonState>({
    contextMenuAnchorElement: null,
  });

  const onClick: React.MouseEventHandler<HTMLButtonElement> = (event) =>
    setState((prevState) => ({ ...prevState, contextMenuAnchorElement: event.currentTarget }));

  const onCloseContextMenu = () => setState((prevState) => ({ ...prevState, contextMenuAnchorElement: null }));

  const handleChange = () => {
    onCloseContextMenu();
    onChange();
  };

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
          onChange={handleChange}
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
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const { Component: ProjectContextMenuContainer } = useComponent(projectContextMenuContainerExtensionPoint);

  const onRename = () => setState((prevState) => ({ ...prevState, modalToDisplay: 'RENAME_PROJECT_DIALOG' }));
  const onDelete = () => setState((prevState) => ({ ...prevState, modalToDisplay: 'DELETE_PROJECT_DIALOG' }));
  const onCancel = () => {
    setState((prevState) => ({ ...prevState, modalToDisplay: null }));
    onClose();
  };

  const onSuccess = () => {
    setState((prevState) => ({ ...prevState, modalToDisplay: null }));
    onChange();
  };

  const ModalComponent = modals[state.modalToDisplay];

  const menuItemComponentExtensions: ComponentExtension<ProjectContextMenuEntryProps>[] = useComponents(
    projectContextMenuEntryExtensionPoint
  );
  return (
    <ProjectContextMenuContainer>
      <Menu
        data-testid="project-actions-contextmenu"
        id="project-actions-contextmenu"
        anchorEl={menuAnchor}
        keepMounted
        open={true}
        onClose={onClose}>
        {project.capabilities.canRename ? (
          <MenuItem onClick={onRename} data-testid="rename">
            <ListItemIcon>
              <EditIcon />
            </ListItemIcon>
            <ListItemText primary="Rename" />
          </MenuItem>
        ) : null}
        {project.capabilities.canDownload ? (
          <MenuItem
            data-testid="project-download-action"
            component="a"
            href={`${httpOrigin}/api/projects/${project.id}`}
            type="application/octet-stream"
            onClick={onClose}>
            <ListItemIcon>
              <GetAppIcon />
            </ListItemIcon>
            <ListItemText primary="Download" />
          </MenuItem>
        ) : null}
        {project.capabilities.canDelete ? (
          <MenuItem onClick={onDelete} data-testid="delete">
            <ListItemIcon>
              <DeleteIcon />
            </ListItemIcon>
            <ListItemText primary="Delete" />
          </MenuItem>
        ) : null}
        {menuItemComponentExtensions.map(({ Component: ProjectContextMenuItem }, index) => (
          <ProjectContextMenuItem key={index} project={project} onChange={onChange} onClose={onClose} />
        ))}
      </Menu>
      {ModalComponent ? <ModalComponent project={project} onSuccess={onSuccess} onCancel={onCancel} /> : null}
    </ProjectContextMenuContainer>
  );
};
