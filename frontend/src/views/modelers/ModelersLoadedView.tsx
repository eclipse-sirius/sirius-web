/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
import { Typography } from '@material-ui/core';
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
import EditIcon from '@material-ui/icons/Edit';
import MoreHorizIcon from '@material-ui/icons/MoreHoriz';
import PublishIcon from '@material-ui/icons/Publish';
import { useMachine } from '@xstate/react';
import { PublishModelerModal } from 'modals/publish-modeler/PublishModelerModal';
import { RenameModelerModal } from 'modals/rename-modeler/RenameModelerModal';
import PropTypes from 'prop-types';
import React from 'react';
import { Link as ReactLink, useParams } from 'react-router-dom';
import { View } from 'views/View';
import {
  ModelersLoadedViewContext,
  ModelersLoadedViewEvent,
  modelersLoadedViewMachine,
  OpenDialogEvent,
  OpenMenuEvent,
} from './ModelersLoadedViewMachine';
import { ModelersViewContainer } from './ModelersViewContainer';

const modelerType = PropTypes.shape({
  id: PropTypes.string.isRequired,
  name: PropTypes.string.isRequired,
  status: PropTypes.oneOf(['DRAFT', 'PUBLISHED']).isRequired,
});
const propTypes = {
  modelers: PropTypes.arrayOf(modelerType.isRequired).isRequired,
  onModelerUpdated: PropTypes.func.isRequired,
};
const useStyles = makeStyles((theme) => ({
  tableContainer: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
}));
export const ModelersLoadedView = ({ modelers, onModelerUpdated }) => {
  const { projectId } = useParams();
  const [{ context }, dispatch] = useMachine<ModelersLoadedViewContext, ModelersLoadedViewEvent>(
    modelersLoadedViewMachine
  );
  const { modalDisplayed, menuAnchor, modeler } = context;
  const classes = useStyles();

  const onMore = (event, modeler) => {
    let openMenu = { type: 'OPEN_MENU', menuAnchor: event.currentTarget, modeler } as OpenMenuEvent;
    dispatch(openMenu);
  };

  const onRename = () => {
    let openDialog = { type: 'OPEN_DIALOG', modalDisplayed: 'RenameModeler' } as OpenDialogEvent;
    dispatch(openDialog);
  };

  const onPublish = () => {
    let openDialog = { type: 'OPEN_DIALOG', modalDisplayed: 'PublishModeler' } as OpenDialogEvent;
    dispatch(openDialog);
  };

  const onCloseModal = () => {
    dispatch({ type: 'CLOSE_DIALOG' });
    onModelerUpdated();
  };
  const onCloseContextMenu = () => dispatch({ type: 'CLOSE_MENU' });
  let contextMenu = null;
  if (menuAnchor) {
    contextMenu = (
      <ModelerContextMenu
        menuAnchor={menuAnchor}
        onCloseContextMenu={onCloseContextMenu}
        onRename={onRename}
        onPublish={onPublish}
        modeler={modeler}
      />
    );
  }
  let modal = null;
  if (modalDisplayed === 'RenameModeler') {
    modal = (
      <RenameModelerModal
        modelerId={modeler.id}
        initialModelerName={modeler.name}
        onModelerRenamed={onCloseModal}
        onClose={onCloseModal}
      />
    );
  } else if (modalDisplayed === 'PublishModeler') {
    modal = <PublishModelerModal modelerId={modeler.id} onModelerPublished={onCloseModal} onClose={onCloseModal} />;
  }

  return (
    <View>
      <ModelersViewContainer>
        <TableContainer className={classes.tableContainer}>
          <Table data-testid="modelers">
            <TableHead>
              <TableRow>
                <TableCell variant="head">Name</TableCell>
                <TableCell variant="head">Status</TableCell>
                <TableCell variant="head"></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {modelers.map((modeler) => (
                <ModelerTableRow
                  key={modeler.id}
                  modeler={modeler}
                  modelerURL={`/projects/${projectId}/edit`}
                  onMore={onMore}
                />
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </ModelersViewContainer>
      {contextMenu}
      {modal}
    </View>
  );
};

const ModelerTableRow = ({ modeler, modelerURL, onMore }) => {
  return (
    <TableRow key={modeler.id}>
      <TableCell component="th" scope="row">
        <Link component={ReactLink} to={modelerURL}>
          {modeler.name}
        </Link>
      </TableCell>
      <TableCell component="th" scope="row">
        <Typography>{modeler.status}</Typography>
      </TableCell>
      <TableCell align="right">
        <Tooltip title="More">
          <IconButton
            aria-label="more"
            onClick={(event) => {
              onMore(event, modeler);
            }}
            data-testid="more">
            <MoreHorizIcon />
          </IconButton>
        </Tooltip>
      </TableCell>
    </TableRow>
  );
};

const ModelerContextMenu = ({ menuAnchor, onCloseContextMenu, onRename, onPublish, modeler }) => {
  return (
    <Menu
      data-testid="modeler-actions-contextmenu"
      id="modeler-actions-contextmenu"
      anchorEl={menuAnchor}
      keepMounted
      open={true}
      onClose={onCloseContextMenu}>
      {' '}
      <MenuItem onClick={onRename} data-testid="rename">
        <ListItemIcon>
          <EditIcon />
        </ListItemIcon>
        <ListItemText primary="Rename" />
      </MenuItem>
      <MenuItem disabled={modeler.status === 'PUBLISHED'} onClick={onPublish} data-testid="publish">
        <ListItemIcon>
          <PublishIcon />
        </ListItemIcon>
        <ListItemText primary="Publish" />
      </MenuItem>
    </Menu>
  );
};
ModelersLoadedView.propTypes = propTypes;
