/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { Task, TaskOrEmpty, ViewMode } from '@ObeoNetwork/gantt-task-react';
import { ShareRepresentationModal } from '@eclipse-sirius/sirius-components-core';
import Checkbox from '@material-ui/core/Checkbox';
import FormControl from '@material-ui/core/FormControl';
import IconButton from '@material-ui/core/IconButton';
import ListItemText from '@material-ui/core/ListItemText';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import Tooltip from '@material-ui/core/Tooltip/Tooltip';
import { makeStyles } from '@material-ui/core/styles';
import { ViewColumn } from '@material-ui/icons';
import AspectRatioIcon from '@material-ui/icons/AspectRatio';
import FullscreenIcon from '@material-ui/icons/Fullscreen';
import FullscreenExitIcon from '@material-ui/icons/FullscreenExit';
import ShareIcon from '@material-ui/icons/Share';
import ZoomInIcon from '@material-ui/icons/ZoomIn';
import ZoomOutIcon from '@material-ui/icons/ZoomOut';
import React, { useEffect, useState } from 'react';
import { TaskListColumnEnum } from '../representation/Gantt.types';
import { ToolbarProps, ToolbarState } from './Toolbar.types';
import { useFullscreen } from './useFullScreen';

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
    minWidth: 115,
  },
  selectColumns: {
    minWidth: 15,
    maxWidth: 15,
  },
}));

export const Toolbar = ({
  editingContextId,
  representationId,
  zoomLevel,
  columns,
  tasks,
  onChangeZoomLevel,
  onChangeDisplayColumns,
  onChangeColumns,
  fullscreenNode,
}: ToolbarProps) => {
  const [state, setState] = useState<ToolbarState>({ modal: null });
  const { fullscreen, setFullscreen } = useFullscreen(fullscreenNode);

  const classes = useToolbarStyles();

  const onShare = () => setState((prevState) => ({ ...prevState, modal: 'share' }));
  const closeModal = () => setState((prevState) => ({ ...prevState, modal: null }));
  const isTask = (task: TaskOrEmpty): task is Task => {
    return task.type !== 'empty';
  };
  const onFitToScreen = () => {
    const minTime = Math.min.apply(
      null,
      tasks
        .filter((t) => isTask(t))
        .map((t) => t as Task)
        .filter((task) => Boolean(task.start))
        .map((task) => (task.start ? task.start.getTime() : 0))
    );
    const maxTime = Math.max.apply(
      null,

      tasks
        .filter((t) => isTask(t))
        .map((t) => t as Task)
        .filter((task) => Boolean(task.end))
        .map((task) => (task.end ? task.end.getTime() : 0))
    );

    const fullTime: number = (maxTime - minTime) / 1000 / 3600;
    let zoomLevel: ViewMode = ViewMode.Day;
    if (!isFinite(fullTime)) {
      zoomLevel = ViewMode.Day;
    } else if (fullTime < 10) {
      zoomLevel = ViewMode.Hour;
    } else if (fullTime < 48) {
      zoomLevel = ViewMode.QuarterDay;
    } else if (fullTime < 24 * 4) {
      zoomLevel = ViewMode.HalfDay;
    } else if (fullTime < 24 * 10) {
      zoomLevel = ViewMode.Day;
    } else if (fullTime < 24 * 60) {
      zoomLevel = ViewMode.Week;
    } else if (fullTime < 24 * 30 * 7) {
      zoomLevel = ViewMode.Month;
    } else {
      zoomLevel = ViewMode.Year;
    }

    onChangeZoomLevel(zoomLevel);
  };

  useEffect(() => {
    onFitToScreen();
  }, []);

  const handleDisplayColumns = () => {
    onChangeDisplayColumns();
  };

  const updateZoomLevel = (event) => {
    const newZoomLevel = event.target.value;
    onChangeZoomLevel(newZoomLevel);
  };

  let modalElement: React.ReactElement | null = null;
  if (state.modal === 'share') {
    modalElement = (
      <ShareRepresentationModal
        editingContextId={editingContextId}
        representationId={representationId}
        onClose={closeModal}
      />
    );
  }

  const onZoomIn = () => {
    if (zoomLevel !== ViewMode.Hour) {
      const currentIndex = Object.values(ViewMode).indexOf(zoomLevel);
      const newZoomLevel = Object.values(ViewMode).at(currentIndex - 1);
      onChangeZoomLevel(newZoomLevel ? newZoomLevel : ViewMode.Hour);
    }
  };
  const onZoomOut = () => {
    if (zoomLevel !== ViewMode.Year) {
      const currentIndex = Object.values(ViewMode).indexOf(zoomLevel);
      const newZoomLevel = Object.values(ViewMode).at(currentIndex + 1);
      onChangeZoomLevel(newZoomLevel ? newZoomLevel : ViewMode.Year);
    }
  };

  const handleChangeColumns = (event) => {
    const columnTypes: TaskListColumnEnum[] = event.target.value;

    onChangeColumns(columnTypes);
  };

  const allColumns = [
    { type: TaskListColumnEnum.NAME, name: 'Name' },
    { type: TaskListColumnEnum.START_DATE, name: 'Start Date' },
    { type: TaskListColumnEnum.END_DATE, name: 'End Date' },
    { type: TaskListColumnEnum.PROGRESS, name: 'Progress' },
  ];

  return (
    <>
      <div className={classes.toolbar}>
        {fullscreen ? (
          <Tooltip title="Exit full screen mode">
            <IconButton
              size="small"
              color="inherit"
              aria-label="exit full screen mode"
              onClick={() => setFullscreen(false)}>
              <FullscreenExitIcon />
            </IconButton>
          </Tooltip>
        ) : (
          <Tooltip title="Toggle full screen mode">
            <IconButton
              size="small"
              color="inherit"
              aria-label="toggle full screen mode"
              onClick={() => setFullscreen(true)}>
              <FullscreenIcon />
            </IconButton>
          </Tooltip>
        )}
        <FormControl className={classes.selectFormControl}>
          <Select
            value={zoomLevel}
            onChange={updateZoomLevel}
            variant="standard"
            disableUnderline
            title="Zoom level"
            data-testid="zoom-level">
            <MenuItem value={ViewMode.Hour} data-testid="zoom-level-Hour">
              Hour
            </MenuItem>
            <MenuItem value={ViewMode.QuarterDay}>Quarter Day</MenuItem>
            <MenuItem value={ViewMode.HalfDay}>Half Day</MenuItem>
            <MenuItem value={ViewMode.Day}>Day</MenuItem>
            <MenuItem value={ViewMode.Week}>Week</MenuItem>
            <MenuItem value={ViewMode.Month}>Month</MenuItem>
            <MenuItem value={ViewMode.Year}>Year</MenuItem>
          </Select>
        </FormControl>
        <IconButton
          size="small"
          color="inherit"
          aria-label="zoom in"
          title="Zoom in"
          onClick={onZoomIn}
          data-testid="zoom-in">
          <ZoomInIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="zoom out"
          title="Zoom out"
          onClick={onZoomOut}
          data-testid="zoom-out">
          <ZoomOutIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="fit to screen"
          title="Fit to screen"
          onClick={onFitToScreen}
          data-testid="fit-to-screen">
          <AspectRatioIcon fontSize="small" />
        </IconButton>
        <IconButton
          size="small"
          color="inherit"
          aria-label="display task list columns"
          title="Display columns"
          onClick={handleDisplayColumns}
          data-testid="display-task-list-columns">
          <ViewColumn fontSize="small" />
        </IconButton>
        <FormControl className={classes.selectColumns}>
          <Select
            labelId="columns-checkbox-label"
            id="columns-checkbox"
            multiple
            disableUnderline
            value={columns}
            onChange={handleChangeColumns}
            renderValue={() => ''}
            data-testid="columns-select">
            {allColumns.map((column) => (
              <MenuItem key={column.type} value={column.type} disabled={column.type == TaskListColumnEnum.NAME}>
                <Checkbox checked={columns.indexOf(column.type) > -1} data-testid={`columnType-${column.type}`} />
                <ListItemText primary={column.name} />
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <IconButton size="small" color="inherit" aria-label="share" title="Share" onClick={onShare} data-testid="share">
          <ShareIcon fontSize="small" />
        </IconButton>
      </div>
      {modalElement}
    </>
  );
};
