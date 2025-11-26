/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import Delete from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import {
  TaskContextualPaletteProps as GanttTaskContextualPaletteProps,
  TaskDependencyContextualPaletteProps as GanttTaskDependencyContextualPaletteProps,
  Task,
} from '@ObeoNetwork/gantt-task-react';
import { useTranslation } from 'react-i18next';
import { makeStyles } from 'tss-react/mui';
import { TaskIcon } from '../icons/TaskIcon';
import { TaskContextualPaletteProps, TaskDependencyContextualPaletteProps } from './ContextualPalette.types';

const useContextualPaletteStyle = makeStyles()((theme) => ({
  taskButtonEntries: {
    display: 'grid',
    gridTemplateColumns: `repeat(2, 28px)`,
    gridTemplateRows: '28px',
    gridAutoRows: '28px',
    placeItems: 'center',
  },
  taskDependencyButtonEntries: {
    display: 'grid',
    gridTemplateColumns: `repeat(1, 28px)`,
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

export const getTaskContextualPalette = ({ onCreateTask, onDeleteTask }: TaskContextualPaletteProps) => {
  const TaskContextualPalette: React.FC<GanttTaskContextualPaletteProps> = ({ selectedTask, onClosePalette }) => {
    const { classes } = useContextualPaletteStyle();
    const { t } = useTranslation('sirius-components-gantt', { keyPrefix: 'getTaskContextualPalette' });

    const handleCreateTask = (task: Task) => {
      onClosePalette();
      onCreateTask(task);
    };
    const handleDeleteTask = (task: Task) => {
      onClosePalette();
      onDeleteTask(task);
    };

    return (
      <div className={classes.taskButtonEntries}>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label={t('createTask')}
          title={t('createTask')}
          onClick={() => handleCreateTask(selectedTask)}
          data-testid="create-task">
          <TaskIcon fontSize="small" />
        </IconButton>
        <IconButton
          className={classes.toolIcon}
          size="small"
          aria-label={t('deleteTask')}
          title={t('deleteTask')}
          onClick={() => handleDeleteTask(selectedTask)}
          data-testid="delete-task">
          <Delete fontSize="small" />
        </IconButton>
      </div>
    );
  };

  return TaskContextualPalette;
};

export const getTaskDependencyContextualPalette = ({
  onDeleteTaskDependency,
}: TaskDependencyContextualPaletteProps) => {
  const handleTaskDependencyDelete = (taskFrom: Task, taskTo: Task, onClosePalette: () => any) => {
    onClosePalette();
    onDeleteTaskDependency(taskFrom.id, taskTo.id);
  };

  const { classes } = useContextualPaletteStyle();

  const DependencyContextualPalette: React.FC<GanttTaskDependencyContextualPaletteProps> = ({
    taskFrom,
    taskTo,
    onClosePalette,
  }) => {
    const { t } = useTranslation('sirius-components-gantt', { keyPrefix: 'getTaskDependencyContextualPalette' });
    return (
      <div className={classes.taskDependencyButtonEntries}>
        <IconButton
          size="small"
          aria-label={t('deleteTaskDependency')}
          title={t('deleteTaskDependency')}
          onClick={() => handleTaskDependencyDelete(taskFrom, taskTo, onClosePalette)}
          data-testid="delete-task-dependency">
          <Delete fontSize="small" />
        </IconButton>
      </div>
    );
  };
  return DependencyContextualPalette;
};
