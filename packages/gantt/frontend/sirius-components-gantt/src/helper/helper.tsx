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
import { Column, ColumnProps, Dependency, TaskOrEmpty, TaskType, TitleColumn } from '@ObeoNetwork/gantt-task-react';
import {
  GQLColumn,
  GQLGantt,
  GQLTask,
  GQLTaskDetail,
  SelectableEmptyTask,
  SelectableTask,
} from '../graphql/subscription/GanttSubscription.types';
import { TaskListColumnEnum } from '../representation/Gantt.types';

export function getTaskFromGQLTask(gQLTasks: GQLTask[], parentId: string): TaskOrEmpty[] {
  const tasks: TaskOrEmpty[] = [];
  gQLTasks.forEach((gQLTask: GQLTask) => {
    let type: TaskType = 'task';
    const isProject = gQLTask.subTasks && gQLTask.subTasks.length > 0;
    if (isProject) {
      type = 'project';
    } else if (gQLTask.detail.startTime === gQLTask.detail.endTime) {
      type = 'milestone';
    }
    const dependencies: Dependency[] = gQLTask.taskDependencyIds.map((dependencyTaskId) => {
      return {
        sourceId: dependencyTaskId,
        sourceTarget: 'endOfTask',
        ownTarget: 'startOfTask',
      };
    });
    let task: SelectableTask | SelectableEmptyTask;
    if (gQLTask.detail.startTime && gQLTask.detail.endTime) {
      task = {
        id: gQLTask.id,
        name: gQLTask.detail.name,
        start: new Date(gQLTask.detail.startTime),
        end: new Date(gQLTask.detail.endTime),
        progress: gQLTask.detail.progress,
        type,
        dependencies,
        parent: parentId,
        hideChildren: gQLTask.detail.collapsed,
        targetObjectId: gQLTask.targetObjectId,
        targetObjectKind: gQLTask.targetObjectKind,
        targetObjectLabel: gQLTask.targetObjectLabel,
      };
    } else {
      task = {
        id: gQLTask.id,
        name: gQLTask.detail.name,
        parent: parentId,
        type: 'empty',
        targetObjectId: gQLTask.targetObjectId,
        targetObjectKind: gQLTask.targetObjectKind,
        targetObjectLabel: gQLTask.targetObjectLabel,
      };
    }

    tasks.push(task);
    if (isProject) {
      if (gQLTask.detail.computeStartEndDynamically) {
        task.isDisabled = true;
      }
      const children: TaskOrEmpty[] = getTaskFromGQLTask(gQLTask.subTasks, gQLTask.id);
      tasks.push(...children);
    }
  });
  return tasks;
}

export const updateTask = (gantt: GQLGantt | null, taskId: string, newDetail: GQLTaskDetail) => {
  if (gantt?.tasks) {
    const task = findTask(gantt.tasks, taskId);
    if (!!task) {
      task.detail = newDetail;
    }
  }
};

const findTask = (tasks: GQLTask[], taskId: string): GQLTask | undefined => {
  let foundTask: GQLTask | undefined = undefined;
  if (tasks) {
    tasks.every((value) => {
      if (value.id === taskId) {
        foundTask = value;
      } else {
        foundTask = findTask(value.subTasks, taskId);
      }
      return !foundTask;
    });
  }
  return foundTask;
};

const ProgressColumn = ({ data: { task } }: ColumnProps) => {
  if (task.type === 'project' || task.type === 'task') {
    return <>{task.progress}%</>;
  }

  return null;
};

const StartDateColumn: React.FC<ColumnProps> = ({ data: { task } }) => {
  if (task.type !== 'empty') {
    return <div>{getFormattedDate(task.start)}</div>;
  }

  return null;
};

const EndDateColumn: React.FC<ColumnProps> = ({ data: { task } }) => {
  if (task.type !== 'empty') {
    return <div>{getFormattedDate(task.end)}</div>;
  }

  return null;
};

const getFormattedDate = (date: Date): string => {
  const locale = navigator.languages[0];
  return date.toLocaleDateString(locale, { weekday: 'short', month: 'short', day: 'numeric', year: 'numeric' });
};

export const getDisplayedColumns = (gqlColumns: GQLColumn[]): Column[] => {
  const columnEnums = [
    TaskListColumnEnum.NAME,
    TaskListColumnEnum.START_DATE,
    TaskListColumnEnum.END_DATE,
    TaskListColumnEnum.PROGRESS,
  ];
  const columns: Column[] = [];
  columnEnums.forEach((columnType) => {
    const gqlColumn = gqlColumns.find((col) => col.id == columnType);
    if (gqlColumn && gqlColumn.displayed) {
      if (columnType === TaskListColumnEnum.NAME) {
        columns.push({
          Cell: TitleColumn,
          width: gqlColumn.width,
          title: 'Name',
          id: TaskListColumnEnum.NAME,
        });
      } else if (columnType === TaskListColumnEnum.START_DATE) {
        columns.push({
          Cell: StartDateColumn,
          width: gqlColumn.width,
          title: 'Start Date',
          id: TaskListColumnEnum.START_DATE,
        });
      } else if (columnType === TaskListColumnEnum.END_DATE) {
        columns.push({
          Cell: EndDateColumn,
          width: gqlColumn.width,
          title: 'End Date',
          id: TaskListColumnEnum.END_DATE,
        });
      } else if (columnType === TaskListColumnEnum.PROGRESS) {
        columns.push({
          Cell: ProgressColumn,
          width: gqlColumn.width,
          title: 'Progress',
          id: TaskListColumnEnum.PROGRESS,
        });
      }
    }
  });

  return columns;
};

export const getSelectedColumns = (gqlColumns: GQLColumn[]) => {
  return gqlColumns.filter((col) => col.displayed === true).map((col) => TaskListColumnEnum[col.id]);
};
