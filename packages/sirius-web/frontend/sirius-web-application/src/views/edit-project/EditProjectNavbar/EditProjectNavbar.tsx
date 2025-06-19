/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import SettingsIcon from '@mui/icons-material/Settings';
import IconButton from '@mui/material/IconButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { emphasize } from '@mui/material/styles';
import React, { useEffect, useState } from 'react';
import { Navigate, Link as RouterLink } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { DeleteProjectModal } from '../../../modals/delete-project/DeleteProjectModal';
import { RenameProjectModal } from '../../../modals/rename-project/RenameProjectModal';
import { NavigationBar } from '../../../navigationBar/NavigationBar';
import { useCurrentProject } from '../useCurrentProject';
import {
  EditProjectNavbarMenuEntryProps,
  EditProjectNavbarProps,
  EditProjectNavbarState,
  ModalName,
} from './EditProjectNavbar.types';
import {
  editProjectNavbarMenuContainerExtensionPoint,
  editProjectNavbarMenuEntryExtensionPoint,
} from './EditProjectNavbarMenuExtensionPoints';
import { useProjectEventSubscription } from './useProjectEventSubscription';
import { GQLProjectEventPayload, GQLProjectRenamedEventPayload } from './useProjectEventSubscription.types';

const useEditProjectViewNavbarStyles = makeStyles()((theme) => ({
  center: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  title: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  titleLabel: {
    marginRight: theme.spacing(2),
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    maxWidth: '100ch',
  },
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
}));

const isProjectRenamedEventPayload = (
  payload: GQLProjectEventPayload | null
): payload is GQLProjectRenamedEventPayload => payload && payload.__typename === 'ProjectRenamedEventPayload';

export const EditProjectNavbar = ({ readOnly }: EditProjectNavbarProps) => {
  const { project } = useCurrentProject();
  const { classes } = useEditProjectViewNavbarStyles();

  const [state, setState] = useState<EditProjectNavbarState>({
    modalDisplayed: null,
    projectName: project.name,
    projectMenuAnchor: null,
  });

  const onCloseModal = () => onSwitchModal(null);

  const onSwitchModal = (modalName: ModalName) =>
    setState((prevState) => ({
      ...prevState,
      modalDisplayed: modalName,
    }));

  const { payload: projectEventPayload } = useProjectEventSubscription(project.id);
  useEffect(() => {
    if (isProjectRenamedEventPayload(projectEventPayload)) {
      const { newName } = projectEventPayload;

      setState((prevState) => ({
        ...prevState,
        projectName: newName,
      }));
    }
  }, [projectEventPayload]);

  const onMoreClick = (event: React.MouseEvent<HTMLElement>) => {
    setState((prevState) => ({
      ...prevState,
      projectMenuAnchor: event.currentTarget,
    }));
  };

  const onCloseContextMenu = () => {
    setState((prevState) => ({
      ...prevState,
      projectMenuAnchor: null,
    }));
  };

  const onProjectDeleted = () => {
    setState((prevState) => ({
      ...prevState,
      projectName: '',
    }));
  };

  if (!state.projectName) {
    return <Navigate to={'/projects'} />;
  }

  let modal = null;
  if (project && state.projectMenuAnchor) {
    if (state.modalDisplayed === 'RenameProject') {
      modal = <RenameProjectModal project={project} onSuccess={onCloseModal} onCancel={onCloseModal} />;
    } else if (state.modalDisplayed === 'DeleteProject') {
      modal = <DeleteProjectModal project={project} onSuccess={onProjectDeleted} onCancel={onCloseModal} />;
    }
  }

  const { Component: ContextMenuContainer } = useComponent(editProjectNavbarMenuContainerExtensionPoint);
  const menuItemComponentExtensions: ComponentExtension<EditProjectNavbarMenuEntryProps>[] = useComponents(
    editProjectNavbarMenuEntryExtensionPoint
  );

  return (
    <>
      <NavigationBar>
        <div className={classes.center}>
          <div className={classes.title}>
            <Typography variant="h6" noWrap className={classes.titleLabel} data-testid={`navbar-${state.projectName}`}>
              {state.projectName}
            </Typography>
            <IconButton
              className={classes.onDarkBackground}
              edge="start"
              size="small" // Per #3591 it should remain "small" to keep vertical space for a potential subtitle
              aria-label="more"
              aria-controls="more-menu"
              aria-haspopup="true"
              onClick={onMoreClick}
              color="inherit"
              data-testid="more">
              <MoreVertIcon />
            </IconButton>
          </div>
        </div>
      </NavigationBar>
      {state.projectMenuAnchor ? (
        <ContextMenuContainer>
          <Menu open anchorEl={state.projectMenuAnchor} data-testid="navbar-contextmenu" onClose={onCloseContextMenu}>
            <MenuItem onClick={() => onSwitchModal('RenameProject')} disabled={readOnly} data-testid="rename">
              <ListItemIcon>
                <EditIcon />
              </ListItemIcon>
              <ListItemText primary="Rename" />
            </MenuItem>
            {menuItemComponentExtensions.map(({ Component: ProjectContextMenuItem }, index) => (
              <ProjectContextMenuItem
                key={index}
                projectId={project?.id || ''}
                onCloseContextMenu={onCloseContextMenu}
              />
            ))}
            <MenuItem
              divider
              component={RouterLink}
              to={`/projects/${project?.id}/settings`}
              onClick={onCloseContextMenu}
              data-testid="project-settings-link">
              <ListItemIcon>
                <SettingsIcon />
              </ListItemIcon>
              <ListItemText primary="Settings" />
            </MenuItem>
            <MenuItem onClick={() => onSwitchModal('DeleteProject')} disabled={readOnly} data-testid="delete">
              <ListItemIcon>
                <DeleteIcon />
              </ListItemIcon>
              <ListItemText primary="Delete" />
            </MenuItem>
          </Menu>
        </ContextMenuContainer>
      ) : null}
      {modal}
    </>
  );
};
