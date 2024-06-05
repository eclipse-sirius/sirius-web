/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import { gql, useSubscription } from '@apollo/client';
import { ServerContext, ServerContextValue, Toast, useComponent } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Typography from '@material-ui/core/Typography';
import { emphasize, makeStyles } from '@material-ui/core/styles';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import GetAppIcon from '@material-ui/icons/GetApp';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import SettingsIcon from '@material-ui/icons/Settings';
import { useMachine } from '@xstate/react';
import React, { useContext, useEffect } from 'react';
import { Redirect, Link as RouterLink } from 'react-router-dom';
import { DeleteProjectModal } from '../../../modals/delete-project/DeleteProjectModal';
import { RenameProjectModal } from '../../../modals/rename-project/RenameProjectModal';
import { NavigationBar } from '../../../navigationBar/NavigationBar';
import { EditProjectNavbarProps, GQLProjectEventSubscription } from './EditProjectNavbar.types';

import { editProjectNavbarSubtitleExtensionPoint } from './EditProjectNavbarExtensionPoints';
import {
  EditProjectNavbarContext,
  EditProjectNavbarEvent,
  HandleCloseContextMenuEvent,
  HandleCloseModalEvent,
  HandleCompleteEvent,
  HandleRedirectingEvent,
  HandleShowContextMenuEvent,
  HandleShowModalEvent,
  HandleSubscriptionResultEvent,
  HideToastEvent,
  SchemaValue,
  ShowToastEvent,
  editProjectNavbarMachine,
} from './EditProjectNavbarMachine';

const projectEventSubscription = gql`
  subscription projectEvent($input: ProjectEventInput!) {
    projectEvent(input: $input) {
      __typename
      ... on ProjectRenamedEventPayload {
        projectId
        newName
      }
    }
  }
`;

const useEditProjectViewNavbarStyles = makeStyles((theme) => ({
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

export const EditProjectNavbar = ({ project }: EditProjectNavbarProps) => {
  const classes = useEditProjectViewNavbarStyles();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const [{ value, context }, dispatch] = useMachine<EditProjectNavbarContext, EditProjectNavbarEvent>(
    editProjectNavbarMachine,
    {
      context: {
        projectName: project.name,
      },
    }
  );
  const { toast, navbar } = value as SchemaValue;
  const { id, to, modalDisplayed, projectMenuAnchor, projectName, message } = context;

  const { error } = useSubscription<GQLProjectEventSubscription>(projectEventSubscription, {
    variables: {
      input: {
        id,
        projectId: project.id,
      },
    },
    fetchPolicy: 'no-cache',
    skip: navbar === 'complete',
    onData: ({ data }) => {
      const handleDataEvent: HandleSubscriptionResultEvent = {
        type: 'HANDLE_SUBSCRIPTION_RESULT',
        result: data,
      };
      dispatch(handleDataEvent);
    },
    onComplete: () => {
      const completeEvent: HandleCompleteEvent = { type: 'HANDLE_COMPLETE' };
      dispatch(completeEvent);
    },
  });

  useEffect(() => {
    if (error) {
      const { message } = error;
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
      dispatch(showToastEvent);
    }
  }, [error, dispatch]);

  const onMoreClick = (event: React.MouseEvent<HTMLElement>) => {
    if (navbar === 'empty') {
      const showContextMenu: HandleShowContextMenuEvent = {
        type: 'HANDLE_SHOW_CONTEXT_MENU_EVENT',
        projectMenuAnchor: event.currentTarget,
      };
      dispatch(showContextMenu);
    }
  };

  const onCloseModal = () => {
    dispatch({ type: 'HANDLE_CLOSE_MODAL_EVENT' } as HandleCloseModalEvent);
  };

  const onCloseContextMenu = () => {
    dispatch({ type: 'HANDLE_CLOSE_CONTEXT_MENU_EVENT' } as HandleCloseContextMenuEvent);
  };

  const onProjectDeleted = () => {
    const redirectingEvent: HandleRedirectingEvent = {
      type: 'HANDLE_REDIRECTING_EVENT',
      to: '/projects',
    };
    dispatch(redirectingEvent);
  };

  if (navbar === 'redirectState') {
    return <Redirect to={to} push />;
  }

  let modal = null;
  if (project && navbar === 'modalDisplayedState') {
    if (modalDisplayed === 'RenameProject') {
      modal = <RenameProjectModal project={project} onSuccess={onCloseModal} onCancel={onCloseModal} />;
    } else if (modalDisplayed === 'DeleteProject') {
      modal = <DeleteProjectModal project={project} onSuccess={onProjectDeleted} onCancel={onCloseModal} />;
    }
  }

  const { Component: ProjectSubtitle } = useComponent(editProjectNavbarSubtitleExtensionPoint);
  return (
    <>
      <NavigationBar>
        <div className={classes.center}>
          <div className={classes.title}>
            <Typography variant="h6" noWrap className={classes.titleLabel} data-testid={`navbar-${projectName}`}>
              {projectName}
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
          <ProjectSubtitle />
        </div>
      </NavigationBar>

      <Menu
        open={navbar === 'contextualMenuDisplayedState'}
        anchorEl={projectMenuAnchor}
        data-testid="navbar-contextmenu"
        onClose={onCloseContextMenu}>
        <MenuItem
          onClick={() => {
            const showModal: HandleShowModalEvent = {
              type: 'HANDLE_SHOW_MODAL_EVENT',
              modalName: 'RenameProject',
            };
            dispatch(showModal);
          }}
          data-testid="rename">
          <ListItemIcon>
            <EditIcon />
          </ListItemIcon>
          <ListItemText primary="Rename" />
        </MenuItem>
        <MenuItem
          component="a"
          href={`${httpOrigin}/api/projects/${project?.id}`}
          type="application/octet-stream"
          onClick={onCloseContextMenu}
          data-testid="download-link">
          <ListItemIcon>
            <GetAppIcon />
          </ListItemIcon>
          <ListItemText primary="Download" />
        </MenuItem>
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
        <MenuItem
          onClick={() => {
            const showModal: HandleShowModalEvent = {
              type: 'HANDLE_SHOW_MODAL_EVENT',
              modalName: 'DeleteProject',
            };
            dispatch(showModal);
          }}
          data-testid="delete">
          <ListItemIcon>
            <DeleteIcon />
          </ListItemIcon>
          <ListItemText primary="Delete" />
        </MenuItem>
      </Menu>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
      {modal}
    </>
  );
};
