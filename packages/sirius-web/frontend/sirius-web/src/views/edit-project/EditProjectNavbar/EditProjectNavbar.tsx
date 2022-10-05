/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
  DeleteProjectModal,
  NewDocumentModal,
  RenameProjectModal,
  UploadDocumentModal,
} from '@eclipse-sirius/sirius-components';
import { ServerContext } from '@eclipse-sirius/sirius-components-core';
import IconButton from '@material-ui/core/IconButton';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { emphasize, makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import AddIcon from '@material-ui/icons/Add';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import GetAppIcon from '@material-ui/icons/GetApp';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import PublishIcon from '@material-ui/icons/Publish';
import React, { useContext, useReducer } from 'react';
import { Redirect } from 'react-router-dom';
import { NavigationBar } from '../../../navigationBar/NavigationBar';
import { EditProjectNavbarProps } from './EditProjectNavbar.types';
import {
  CONTEXTUAL_MENU_DISPLAYED__STATE,
  EMPTY__STATE,
  HANDLE_CLOSE_CONTEXT_MENU__ACTION,
  HANDLE_CLOSE_MODAL__ACTION,
  HANDLE_REDIRECTING__ACTION,
  HANDLE_SHOW_CONTEXT_MENU__ACTION,
  HANDLE_SHOW_MODAL__ACTION,
  REDIRECT__STATE,
} from './machine';
import { initialState, reducer } from './reducer';

const useEditProjectViewNavbarStyles = makeStyles((theme) => ({
  center: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  title: {
    marginRight: theme.spacing(2),
  },
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
}));

export const EditProjectNavbar = ({ project }: EditProjectNavbarProps) => {
  const classes = useEditProjectViewNavbarStyles();
  const { httpOrigin } = useContext(ServerContext);
  const [state, dispatch] = useReducer(reducer, initialState);

  const onMoreClick = (event: React.MouseEvent<HTMLElement>) => {
    if (state.viewState === EMPTY__STATE) {
      const action = {
        type: HANDLE_SHOW_CONTEXT_MENU__ACTION,
        projectMenuAnchor: event.currentTarget,
      };
      dispatch(action);
    }
  };

  const { viewState, to, modalDisplayed, projectMenuAnchor } = state;

  const onCloseModal = () => dispatch({ type: HANDLE_CLOSE_MODAL__ACTION });

  const onProjectDeleted = () => {
    dispatch({
      type: HANDLE_REDIRECTING__ACTION,
      to: '/projects',
      modalDisplayed: null,
      projectMenuAnchor: null,
    });
  };

  if (viewState === REDIRECT__STATE) {
    return <Redirect to={to} push />;
  }

  let modal = null;
  if (project) {
    if (modalDisplayed === 'CreateDocument') {
      modal = <NewDocumentModal editingContextId={project.currentEditingContext.id} onClose={onCloseModal} />;
    } else if (modalDisplayed === 'UploadDocument') {
      modal = (
        <UploadDocumentModal editingContextId={project.id} onDocumentUploaded={onCloseModal} onClose={onCloseModal} />
      );
    } else if (modalDisplayed === 'RenameProject') {
      modal = (
        <RenameProjectModal
          projectId={project.id}
          initialProjectName={project.name}
          onRename={onCloseModal}
          onClose={onCloseModal}
        />
      );
    } else if (modalDisplayed === 'DeleteProject') {
      modal = <DeleteProjectModal projectId={project.id} onDelete={onProjectDeleted} onClose={onCloseModal} />;
    }
  }
  return (
    <>
      <NavigationBar>
        <div className={classes.center}>
          <Typography variant="h6" noWrap className={classes.title}>
            {project?.name}
          </Typography>
          <IconButton
            className={classes.onDarkBackground}
            edge="start"
            aria-label="more"
            aria-controls="more-menu"
            aria-haspopup="true"
            onClick={onMoreClick}
            color="inherit"
            data-testid="more">
            <MoreVertIcon />
          </IconButton>
        </div>
      </NavigationBar>

      <Menu
        open={viewState === CONTEXTUAL_MENU_DISPLAYED__STATE}
        anchorEl={projectMenuAnchor}
        data-testid="navbar-contextmenu"
        onClose={() => dispatch({ type: HANDLE_CLOSE_CONTEXT_MENU__ACTION })}>
        <MenuItem
          onClick={() => dispatch({ modalDisplayed: 'CreateDocument', type: HANDLE_SHOW_MODAL__ACTION })}
          data-testid="new-model">
          <ListItemIcon>
            <AddIcon />
          </ListItemIcon>
          <ListItemText primary="New model" />
        </MenuItem>
        <MenuItem
          divider
          data-testid="upload-document"
          onClick={() => dispatch({ modalDisplayed: 'UploadDocument', type: HANDLE_SHOW_MODAL__ACTION })}>
          <ListItemIcon>
            <PublishIcon />
          </ListItemIcon>
          <ListItemText primary="Upload model" />
        </MenuItem>
        <MenuItem onClick={() => dispatch({ modalDisplayed: 'RenameProject', type: HANDLE_SHOW_MODAL__ACTION })}>
          <ListItemIcon>
            <EditIcon />
          </ListItemIcon>
          <ListItemText primary="Rename" />
        </MenuItem>
        <MenuItem
          divider
          component="a"
          href={`${httpOrigin}/api/projects/${project?.id}`}
          type="application/octet-stream"
          onClick={() => dispatch({ type: HANDLE_CLOSE_CONTEXT_MENU__ACTION })}
          data-testid="download-link">
          <ListItemIcon>
            <GetAppIcon />
          </ListItemIcon>
          <ListItemText primary="Download" />
        </MenuItem>
        <MenuItem
          onClick={() => dispatch({ modalDisplayed: 'DeleteProject', type: HANDLE_SHOW_MODAL__ACTION })}
          data-testid="delete">
          <ListItemIcon>
            <DeleteIcon />
          </ListItemIcon>
          <ListItemText primary="Delete" />
        </MenuItem>
      </Menu>

      {modal}
    </>
  );
};
