/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import Delete from '@material-ui/icons/Delete';
import { TaskIcon } from '../icons/TaskIcon';

import { TaskContextualPaletteProps } from '@ObeoNetwork/gantt-task-react';
import { ContextualPaletteProps } from './ContextualPalette.types';

const useContextualPaletteStyle = makeStyles((theme) => ({
  buttonEntries: {
    display: 'grid',
    gridTemplateColumns: `repeat(2, 28px)`,
    gridTemplateRows: '28px',
    gridAutoRows: '28px',
    placeItems: 'center',
  },
  toolIcon: {
    color: theme.palette.text.primary,
  },
  close: {
    gridRowStart: '1',
    gridRowEnd: '2',
    gridColumnStart: '-2',
    gridColumnEnd: '-1',
  },
}));

export const getContextalPalette = ({ onCreateTask, onDeleteTask }: ContextualPaletteProps) => {
  const ContextualPalette: React.FC<TaskContextualPaletteProps> = ({ selectedTask }) => {
    const classes = useContextualPaletteStyle();
    return (
      <div className={classes.buttonEntries}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="create task"
          title="Create task"
          onClick={() => onCreateTask(selectedTask)}
          data-testid="create-task">
          <TaskIcon fontSize="small" />
        </IconButton>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label="Delete task"
          title="Delete task"
          onClick={() => onDeleteTask(selectedTask)}
          data-testid="delete-task">
          <Delete fontSize="small" />
        </IconButton>
      </div>
    );
  };

  return ContextualPalette;
};
