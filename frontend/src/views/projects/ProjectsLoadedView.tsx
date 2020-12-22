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
import IconButton from '@material-ui/core/IconButton';
import Link from '@material-ui/core/Link';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Tooltip from '@material-ui/core/Tooltip';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import GetAppIcon from '@material-ui/icons/GetApp';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import { useMachine } from '@xstate/react';
import { httpOrigin } from 'common/URL';
import { DeleteProjectModal } from 'modals/delete-project/DeleteProjectModal';
import { RenameProjectModal } from 'modals/rename-project/RenameProjectModal';
import { UnsynchronizedPermission } from 'project/Permission';
import PropTypes from 'prop-types';
import React from 'react';
import { View } from 'views/View';
import {
  OpenDialogEvent,
  OpenMenuEvent,
  ProjectsLoadedViewContext,
  ProjectsLoadedViewEvent,
  projectsLoadedViewMachine,
} from './ProjectsLoadedViewMachine';
import { ProjectsViewContainer } from './ProjectsViewContainer';

const projectType = PropTypes.shape({
  id: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
});
const propTypes = {
  projects: PropTypes.arrayOf(projectType.isRequired).isRequired,
  onProjectUpdated: PropTypes.func.isRequired,
};
const useStyles = makeStyles((theme) => ({
  tableContainer: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: '1fr',
  },
}));
export const ProjectsLoadedView = ({ projects, onProjectUpdated }) => {
  const [{ context }, dispatch] = useMachine<ProjectsLoadedViewContext, ProjectsLoadedViewEvent>(
    projectsLoadedViewMachine
  );
  const { modalDisplayed, menuAnchor, project } = context;
  const classes = useStyles();

  const onMore = (event, project) => {
    let openMenu = { type: 'OPEN_MENU', menuAnchor: event.currentTarget, project } as OpenMenuEvent;
    dispatch(openMenu);
  };

  const onRename = () => {
    let openDialog = { type: 'OPEN_DIALOG', modalDisplayed: 'RenameProject' } as OpenDialogEvent;
    dispatch(openDialog);
  };
  const onDelete = () => {
    let openDialog = { type: 'OPEN_DIALOG', modalDisplayed: 'DeleteProject' } as OpenDialogEvent;
    dispatch(openDialog);
  };

  const onCloseModal = () => {
    dispatch({ type: 'CLOSE_DIALOG' });
    onProjectUpdated();
  };
  const onCloseContextMenu = () => dispatch({ type: 'CLOSE_MENU' });
  let contextMenu = null;
  if (menuAnchor) {
    contextMenu = (
      <ProjectContextMenu
        menuAnchor={menuAnchor}
        onCloseContextMenu={onCloseContextMenu}
        onRename={onRename}
        onDelete={onDelete}
        project={project}
      />
    );
  }
  let modal = null;
  if (modalDisplayed === 'RenameProject') {
    modal = (
      <RenameProjectModal
        projectId={project.id}
        initialProjectName={project.name}
        onProjectRenamed={onCloseModal}
        onClose={onCloseModal}
      />
    );
  } else if (modalDisplayed === 'DeleteProject') {
    modal = <DeleteProjectModal projectId={project.id} onProjectDeleted={onCloseModal} onClose={onCloseModal} />;
  }

  return (
    <View>
      <ProjectsViewContainer>
        <TableContainer className={classes.tableContainer}>
          <Table data-testid="projects">
            <TableHead>
              <TableRow>
                <TableCell variant="head">Name</TableCell>
                <TableCell variant="head"></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {projects.map((project) => (
                <ProjectTableRow project={project} onMore={onMore} />
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </ProjectsViewContainer>
      {contextMenu}
      {modal}
    </View>
  );
};

const ProjectTableRow = ({ project, onMore }) => {
  return (
    <TableRow key={project.id}>
      <TableCell component="th" scope="row">
        <Link href={`/projects/${project.id}/edit`}>{project.name}</Link>
      </TableCell>
      <TableCell align="right">
        <Tooltip title="More">
          <IconButton
            aria-label="more"
            onClick={(event) => {
              onMore(event, project);
            }}
            data-testid="more">
            <MoreHorizIcon />
          </IconButton>
        </Tooltip>
      </TableCell>
    </TableRow>
  );
};

const ProjectContextMenu = ({ menuAnchor, onCloseContextMenu, onRename, onDelete, project }) => {
  return (
    <Menu
      data-testid="project-actions-contextmenu"
      id="project-actions-contextmenu"
      anchorEl={menuAnchor}
      keepMounted
      open={true}
      onClose={onCloseContextMenu}>
      {' '}
      <UnsynchronizedPermission requiredAccessLevel="EDIT" accessLevel={project.accessLevel}>
        <MenuItem onClick={onRename} data-testid="rename">
          <ListItemIcon>
            <EditIcon />
          </ListItemIcon>
          <ListItemText primary="Rename" />
        </MenuItem>
      </UnsynchronizedPermission>
      <MenuItem data-testid="download">
        <ListItemIcon>
          <GetAppIcon />
        </ListItemIcon>
        <Link
          href={`${httpOrigin}/api/projects/${project.id}`}
          type="application/octet-stream"
          onClick={onCloseContextMenu}
          data-testid="download-link">
          Download
        </Link>
      </MenuItem>
      <UnsynchronizedPermission requiredAccessLevel="ADMIN" accessLevel={project.accessLevel}>
        <MenuItem onClick={onDelete} data-testid="delete">
          <ListItemIcon>
            <DeleteIcon />
          </ListItemIcon>
          <ListItemText primary="Delete" />
        </MenuItem>
      </UnsynchronizedPermission>
    </Menu>
  );
};
ProjectsLoadedView.propTypes = propTypes;
