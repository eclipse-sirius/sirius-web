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
import Avatar from '@material-ui/core/Avatar';
import FormControl from '@material-ui/core/FormControl';
import IconButton from '@material-ui/core/IconButton';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { makeStyles } from '@material-ui/core/styles';
import Tooltip from '@material-ui/core/Tooltip';
import AspectRatioIcon from '@material-ui/icons/AspectRatio';
import ShareIcon from '@material-ui/icons/Share';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import { ShareDiagramModal } from 'modals/share-diagram/ShareDiagramModal';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';

const propTypes = {
  onZoomIn: PropTypes.func.isRequired,
  onZoomOut: PropTypes.func.isRequired,
  onFitToScreen: PropTypes.func.isRequired,
  setZoomLevel: PropTypes.func.isRequired,
  zoomLevel: PropTypes.string,
  subscribers: PropTypes.array.isRequired,
};

const useToolbarStyles = makeStyles((theme) => ({
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    height: theme.spacing(4),
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    borderBottomWidth: '1px',
    borderBottomStyle: 'solid',
    borderBottomColor: theme.palette.divider,
  },
  selectFormControl: {
    minWidth: 70,
  },
  subscribers: {
    marginLeft: 'auto',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    '& > *': {
      marginLeft: theme.spacing(0.5),
      marginRight: theme.spacing(0.5),
    },
  },
  avatar: {
    fontSize: '1rem',
    width: theme.spacing(3),
    height: theme.spacing(3),
    backgroundColor: theme.palette.primary.main,
  },
}));

export const Toolbar = ({ onZoomIn, onZoomOut, onFitToScreen, setZoomLevel, zoomLevel, subscribers }) => {
  const classes = useToolbarStyles();
  const [state, setState] = useState({ modal: undefined, currentZoomLevel: zoomLevel });
  const onShare = () => setState({ modal: 'ShareDiagramModal', currentZoomLevel: state.currentZoomLevel });
  const closeModal = () => setState({ modal: undefined, currentZoomLevel: state.currentZoomLevel });

  const { modal, currentZoomLevel } = state;

  useEffect(() => {
    setState({ modal: undefined, currentZoomLevel: zoomLevel });
  }, [zoomLevel]);

  const updateZoomLevel = (event) => {
    const newZoomLevel = event.target.value;
    setState({ modal: state.modal, currentZoomLevel: newZoomLevel.toString() });
    setZoomLevel(newZoomLevel.toString());
  };

  let modalElement = null;
  if (modal === 'ShareDiagramModal') {
    modalElement = <ShareDiagramModal url={window.location.href} onClose={closeModal} />;
  }
  return (
    <>
      <div className={classes.toolbar}>
        <FormControl className={classes.selectFormControl}>
          <Select
            value={currentZoomLevel}
            onChange={updateZoomLevel}
            variant="standard"
            disableUnderline
            data-testid="zoom-level">
            <MenuItem value={'4'}>400%</MenuItem>
            <MenuItem value={'2'}>200%</MenuItem>
            <MenuItem value={'1.75'}>175%</MenuItem>
            <MenuItem value={'1.5'}>150%</MenuItem>
            <MenuItem value={'1.25'}>125%</MenuItem>
            <MenuItem value={'1'}>100%</MenuItem>
            <MenuItem value={'0.75'}>75%</MenuItem>
            <MenuItem value={'0.5'}>50%</MenuItem>
            <MenuItem value={'0.25'}>25%</MenuItem>
            <MenuItem value={'0.1'}>10%</MenuItem>
            <MenuItem value={'0.05'}>5%</MenuItem>
          </Select>
        </FormControl>
        <IconButton size="small" color="inherit" aria-label="zoom in" onClick={onZoomIn} data-testid="zoom-in">
          <ZoomInIcon fontSize="small" />
        </IconButton>
        <IconButton size="small" color="inherit" aria-label="zoom out" onClick={onZoomOut} data-testid="zoom-out">
          <ZoomOutIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="fit to screen"
          onClick={onFitToScreen}
          data-testid="fit-to-screen">
          <AspectRatioIcon fontSize="small" />
        </IconButton>
        <IconButton size="small" color="inherit" aria-label="share" onClick={onShare} data-testid="share">
          <ShareIcon fontSize="small" />
        </IconButton>

        <div className={classes.subscribers}>
          {subscribers.map((subscriber) => (
            <Tooltip title={subscriber.username} arrow key={subscriber.username}>
              <Avatar classes={{ root: classes.avatar }}>{subscriber.username.substring(0, 1).toUpperCase()}</Avatar>
            </Tooltip>
          ))}
        </div>
      </div>
      {modalElement}
    </>
  );
};
Toolbar.propTypes = propTypes;
