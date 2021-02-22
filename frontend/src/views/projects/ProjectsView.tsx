/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import { useQuery } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import Grid from '@material-ui/core/Grid';
import IconButton from '@material-ui/core/IconButton';
import Link from '@material-ui/core/Link';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Paper from '@material-ui/core/Paper';
import Snackbar from '@material-ui/core/Snackbar';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Tooltip from '@material-ui/core/Tooltip';
import Typography from '@material-ui/core/Typography';
import CloseIcon from '@material-ui/icons/Close';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import GetAppIcon from '@material-ui/icons/GetApp';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import { useMachine } from '@xstate/react';
import { useBranding } from 'common/BrandingContext';
import { httpOrigin } from 'common/URL';
import gql from 'graphql-tag';
import { DeleteProjectModal } from 'modals/delete-project/DeleteProjectModal';
import { RenameProjectModal } from 'modals/rename-project/RenameProjectModal';
import { LoggedInNavbar } from 'navbar/LoggedInNavbar';
import React, { useEffect } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import {
  GQLGetProjectsQueryData,
  GQLGetProjectsQueryVariables,
  Project,
  ProjectContextMenuProps,
  ProjectsTableProps,
} from 'views/projects/ProjectsView.types';
import {
  CloseMenuEvent,
  CloseModalEvent,
  FetchedProjectsEvent,
  HideToastEvent,
  OpenMenuEvent,
  OpenModalEvent,
  ProjectsViewContext,
  ProjectsViewEvent,
  projectsViewMachine,
  SchemaValue,
  ShowToastEvent,
} from 'views/projects/ProjectsViewMachine';

const getProjectsQuery = gql`
  query getProjects {
    viewer {
      projects {
        id
        name
        accessLevel
      }
    }
  }
`;

const useProjectsViewStyles = makeStyles((theme) => ({
  projectsView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
  },
  projectsViewContainer: {
    display: 'flex',
    flexDirection: 'column',
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: theme.spacing(5),
  },
  actions: {
    display: 'flex',
    flexDirection: 'row',
    '& > *': {
      marginLeft: theme.spacing(2),
    },
  },
}));

export const ProjectsView = () => {
  const classes = useProjectsViewStyles();
  const { footer } = useBranding();
  const [{ value, context }, dispatch] = useMachine<ProjectsViewContext, ProjectsViewEvent>(projectsViewMachine);
  const { toast, projectsView } = value as SchemaValue;
  const { projects, selectedProject, menuAnchor, modalToDisplay, message } = context;

  const { loading, data, error, refetch } = useQuery<GQLGetProjectsQueryData, GQLGetProjectsQueryVariables>(
    getProjectsQuery,
    {}
  );
  useEffect(() => {
    if (!loading) {
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, please refresh the page',
        };
        dispatch(showToastEvent);
      }
      if (data) {
        const fetchProjectsEvent: FetchedProjectsEvent = { type: 'HANDLE_FETCHED_PROJECTS', data };
        dispatch(fetchProjectsEvent);
      }
    }
  }, [loading, data, error, dispatch]);

  const onMore = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>, project: Project) =>
    dispatch({ type: 'OPEN_MENU', menuAnchor: event.currentTarget, project } as OpenMenuEvent);
  const onClose = () => dispatch({ type: 'CLOSE_MENU' } as CloseMenuEvent);
  const onRename = () => dispatch({ type: 'OPEN_MODAL', modalToDisplay: 'Rename' } as OpenModalEvent);
  const onDelete = () => dispatch({ type: 'OPEN_MODAL', modalToDisplay: 'Delete' } as OpenModalEvent);
  const onCloseModal = () => dispatch({ type: 'CLOSE_MODAL' } as CloseModalEvent);

  const refreshProjects = () => {
    dispatch({ type: 'CLOSE_MODAL' } as CloseModalEvent);
    refetch();
  };

  let main = null;
  if (projectsView === 'loaded') {
    let contextMenu = null;
    let modal = null;

    if (selectedProject) {
      if (menuAnchor) {
        contextMenu = (
          <ProjectContextMenu
            menuAnchor={menuAnchor}
            project={selectedProject}
            onRename={onRename}
            onDelete={onDelete}
            onClose={onClose}
          />
        );
      }
      if (modalToDisplay === 'Rename') {
        modal = (
          <RenameProjectModal
            projectId={selectedProject.id}
            initialProjectName={selectedProject.name}
            onRename={refreshProjects}
            onClose={onCloseModal}
          />
        );
      } else if (modalToDisplay === 'Delete') {
        modal = <DeleteProjectModal projectId={selectedProject.id} onDelete={refreshProjects} onClose={onCloseModal} />;
      }
    }
    main = (
      <>
        <ProjectsTable projects={projects} onMore={onMore} />
        {contextMenu}
        {modal}
      </>
    );
  } else if (projectsView === 'empty') {
    main = <Message content="No projects available, start by creating one" />;
  }

  return (
    <>
      <div className={classes.projectsView}>
        <LoggedInNavbar />
        <main className={classes.main}>
          <Container maxWidth="xl">
            <Grid container justify="center">
              <Grid item xs={8}>
                <div className={classes.projectsViewContainer}>
                  <div className={classes.header}>
                    <Typography variant="h3">Projects</Typography>
                    <div className={classes.actions}>
                      <Button
                        to={`/new/project`}
                        component={RouterLink}
                        data-testid="create"
                        color="primary"
                        variant="contained">
                        New
                      </Button>
                      <Button
                        to={`/upload/project`}
                        component={RouterLink}
                        data-testid="create"
                        color="primary"
                        variant="contained">
                        Upload
                      </Button>
                    </div>
                  </div>
                  {main}
                </div>
              </Grid>
            </Grid>
          </Container>
        </main>
        {footer}
      </div>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};

const Message = ({ content }) => {
  return (
    <Grid container justify="center">
      <Grid item xs={6}>
        <Typography variant="h4" align="center" gutterBottom>
          {content}
        </Typography>
      </Grid>
    </Grid>
  );
};

const ProjectsTable = ({ projects, onMore }: ProjectsTableProps) => {
  return (
    <Paper>
      <TableContainer>
        <Table>
          <colgroup>
            <col width="60%" />
            <col width="30%" />
            <col width="10%" />
          </colgroup>
          <TableHead>
            <TableRow>
              <TableCell variant="head">Name</TableCell>
              <TableCell variant="head"></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {projects.map((project) => (
              <TableRow key={project.id}>
                <TableCell>
                  <Link component={RouterLink} to={`/projects/${project.id}/edit`} color="inherit">
                    {project.name}
                  </Link>
                </TableCell>
                <TableCell align="right">
                  <Tooltip title="More">
                    <IconButton
                      aria-label="more"
                      onClick={(event) => onMore(event, project)}
                      size="small"
                      data-testid="more">
                      <MoreHorizIcon fontSize="small" />
                    </IconButton>
                  </Tooltip>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
};

const ProjectContextMenu = ({ menuAnchor, project, onClose, onRename, onDelete }: ProjectContextMenuProps) => {
  return (
    <Menu
      data-testid="modeler-actions-contextmenu"
      id="modeler-actions-contextmenu"
      anchorEl={menuAnchor}
      keepMounted
      open={true}
      onClose={onClose}>
      <MenuItem onClick={onRename} data-testid="rename" disabled={project.accessLevel !== 'ADMIN'}>
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
      <MenuItem onClick={onDelete} data-testid="delete" disabled={project.accessLevel !== 'ADMIN'}>
        <ListItemIcon>
          <DeleteIcon />
        </ListItemIcon>
        <ListItemText primary="Delete" />
      </MenuItem>
    </Menu>
  );
};
