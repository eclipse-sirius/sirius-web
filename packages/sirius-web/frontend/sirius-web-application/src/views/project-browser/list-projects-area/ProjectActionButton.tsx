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

import { ComponentExtension, useComponent, useComponents } from '@eclipse-sirius/sirius-components-core';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import Tooltip from '@mui/material/Tooltip';
import { useState } from 'react';
import { DeleteProjectMenuItem } from '../../edit-project/navbar/context-menu/DeleteProjectMenuItem';
import { DownloadProjectMenuItem } from '../../edit-project/navbar/context-menu/DownloadProjectMenuItem';
import { DuplicateProjectMenuItem } from '../../edit-project/navbar/context-menu/DuplicateProjectMenuItem';
import { RenameProjectMenuItem } from '../../edit-project/navbar/context-menu/RenameProjectMenuItem';
import {
  ProjectActionButtonProps,
  ProjectActionButtonState,
  ProjectContextMenuEntryProps,
  ProjectContextMenuProps,
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

const ProjectContextMenu = ({ menuAnchor, project, onChange, onClose }: ProjectContextMenuProps) => {
  const { Component: ProjectContextMenuContainer } = useComponent(projectContextMenuContainerExtensionPoint);

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
          <RenameProjectMenuItem project={project} onCancel={onClose} onSuccess={onChange} />
        ) : null}
        {project.capabilities.canDuplicate ? (
          <DuplicateProjectMenuItem projectId={project.id} onClick={onClose} />
        ) : null}
        {project.capabilities.canDownload ? (
          <DownloadProjectMenuItem project={project} name={null} onClick={onClose} />
        ) : null}
        {menuItemComponentExtensions.map(({ Component: ProjectContextMenuItem }, index) => (
          <ProjectContextMenuItem key={index} project={project} onChange={onChange} onClose={onClose} />
        ))}
        {project.capabilities.canDelete ? (
          <DeleteProjectMenuItem project={project} onCancel={onClose} onSuccess={onChange} />
        ) : null}
      </Menu>
    </ProjectContextMenuContainer>
  );
};
