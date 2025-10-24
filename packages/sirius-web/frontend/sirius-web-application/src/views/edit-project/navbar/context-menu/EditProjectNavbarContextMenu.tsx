/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import Menu from '@mui/material/Menu';
import { useState } from 'react';
import { Navigate } from 'react-router-dom';
import { useCurrentProject } from '../../useCurrentProject';
import { DeleteProjectMenuItem } from './DeleteProjectMenuItem';
import { DownloadProjectMenuItem } from './DownloadProjectMenuItem';
import { DuplicateProjectMenuItem } from './DuplicateProjectMenuItem';
import {
  EditProjectNavbarContextMenuProps,
  EditProjectNavbarContextMenuState,
  EditProjectNavbarMenuEntryProps,
} from './EditProjectNavbarContextMenu.types';
import {
  editProjectNavbarMenuContainerExtensionPoint,
  editProjectNavbarMenuEntryExtensionPoint,
} from './EditProjectNavbarMenuExtensionPoints';
import { ProjectSettingsMenuItem } from './ProjectSettingsMenuItem';
import { RenameProjectMenuItem } from './RenameProjectMenuItem';
import { ShareProjectMenuItem } from './ShareProjectMenuItem';

export const EditProjectNavbarContextMenu = ({
  anchorEl,
  onClose,
  workbenchHandle,
}: EditProjectNavbarContextMenuProps) => {
  const [state, setState] = useState<EditProjectNavbarContextMenuState>({
    redirectUrl: null,
  });

  const { Component: ContextMenuContainer } = useComponent(editProjectNavbarMenuContainerExtensionPoint);
  const menuItemComponentExtensions: ComponentExtension<EditProjectNavbarMenuEntryProps>[] = useComponents(
    editProjectNavbarMenuEntryExtensionPoint
  );

  const { project } = useCurrentProject();

  const onProjectDeleted = () => setState((prevState) => ({ ...prevState, redirectUrl: '/projects' }));

  if (state.redirectUrl) {
    return <Navigate to={state.redirectUrl} />;
  }

  return (
    <ContextMenuContainer>
      <Menu open anchorEl={anchorEl} data-testid="navbar-contextmenu" onClose={onClose}>
        {project.capabilities.canRename ? (
          <RenameProjectMenuItem project={project} onCancel={onClose} onSuccess={onClose} />
        ) : null}
        <ShareProjectMenuItem workbenchHandle={workbenchHandle} />
        {project.capabilities.canDuplicate ? (
          <DuplicateProjectMenuItem projectId={project.id} onClick={onClose} />
        ) : null}
        {project.capabilities.canDownload ? <DownloadProjectMenuItem project={project} onClick={onClose} /> : null}
        {menuItemComponentExtensions.map(({ Component: ProjectContextMenuItem }, index) => (
          <ProjectContextMenuItem key={index} projectId={project.id} onCloseContextMenu={onClose} />
        ))}
        {project.capabilities.settings.canView ? <ProjectSettingsMenuItem project={project} onClick={onClose} /> : null}
        {project.capabilities.canDelete ? (
          <DeleteProjectMenuItem project={project} onCancel={onClose} onSuccess={onProjectDeleted} />
        ) : null}
      </Menu>
    </ContextMenuContainer>
  );
};
