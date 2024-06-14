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
import {
  BarMoveAction,
  Column,
  ColumnProps,
  DateExtremity,
  Dependency,
  GanttDateRounding,
  GanttDateRoundingTimeUnit,
  TaskOrEmpty,
  TaskType,
  TitleColumn,
} from '@ObeoNetwork/gantt-task-react';
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

const getDayOfTheYear = (date: Date) => {
  const start = new Date(date.getFullYear(), 0, 0);
  const diff = date.getTime() - start.getTime() + (start.getTimezoneOffset() - date.getTimezoneOffset()) * 60 * 1000;
  const oneDay = 1000 * 60 * 60 * 24;
  const day = Math.floor(diff / oneDay);
  return day;
};

export const roundDate = (
  date: Date,
  dateExtremity: DateExtremity,
  action: BarMoveAction,
  dateMoveStep: GanttDateRounding
): Date => {
  let value = dateMoveStep.value;
  const dimension = dateMoveStep.timeUnit;
  const newdate = new Date(date);
  if (dimension == GanttDateRoundingTimeUnit.DAY) {
    let dayOfTheYear: number = getDayOfTheYear(newdate);
    if (newdate.getMinutes() != 0 || newdate.getHours() != 0 || (dayOfTheYear - 1) % value > 0) {
      newdate.setMinutes(0);
      newdate.setHours(0);
      if (dateExtremity == 'startOfTask' || action == 'move') {
        // In case of move we need to round start and end date with the same direction (floor) so that the duration keeps unchanged
        dayOfTheYear = Math.floor((dayOfTheYear - 1) / value) * value + 1;
      } else if (dateExtremity == 'endOfTask') {
        dayOfTheYear = Math.ceil(dayOfTheYear / value) * value + 1;
      }
      // setDate behaves differently according to the current date so ensure to start from the first day in the year
      newdate.setMonth(0);
      newdate.setDate(1);
      newdate.setDate(dayOfTheYear);
    }
  } else if (dimension == GanttDateRoundingTimeUnit.HOUR) {
    if (newdate.getMinutes() != 0 || newdate.getHours() % value > 0) {
      newdate.setMinutes(0);
      let hours = newdate.getHours();
      if (dateExtremity == 'startOfTask' || action == 'move') {
        // In case of move we need to round start and end date with the same direction (floor) so that the duration keeps unchanged
        hours = Math.floor(newdate.getHours() / value) * value;
      } else if (dateExtremity == 'endOfTask') {
        hours = Math.ceil(newdate.getHours() / value) * value;
      }
      newdate.setHours(hours);
    }
  } else if (dimension == GanttDateRoundingTimeUnit.MINUTE) {
    if (newdate.getMinutes() % value > 0) {
      let minutes = newdate.getMinutes();
      if (dateExtremity == 'startOfTask' || action == 'move') {
        // In case of move we need to round start and end date with the same direction (floor) so that the duration keeps unchanged
        minutes = Math.floor(newdate.getMinutes() / value) * value;
      } else if (dateExtremity == 'endOfTask') {
        minutes = Math.ceil(newdate.getMinutes() / value) * value;
      }
      newdate.setMinutes(minutes);
    }
  }
  newdate.setSeconds(0);
  newdate.setMilliseconds(0);
  return newdate;
};

export const checkIsHoliday = (date: Date, _, __, dateExtremity: DateExtremity) => {
  let isHoliday = false;

  const day = date.getDay();
  const isMondayStart = date.getDay() == 1 && date.getHours() == 0 && date.getMinutes() == 0;
  const isStaturdayStart = date.getDay() == 6 && date.getHours() == 0 && date.getMinutes() == 0;
  if (dateExtremity == 'startOfTask') {
    //Monday 00:00 is excluded from WE
    isHoliday = day == 6 || (day == 0 && !isMondayStart);
  } else if (dateExtremity == 'endOfTask') {
    //Saturday 00:00 is included from WE
    isHoliday = (day == 6 && !isStaturdayStart) || day == 0 || isMondayStart;
  }

  return isHoliday;
};
