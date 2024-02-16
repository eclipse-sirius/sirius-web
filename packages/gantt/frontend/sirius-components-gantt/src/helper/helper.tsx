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
  Column,
  ColumnProps,
  DateEndColumn,
  DateStartColumn,
  Dependency,
  EmptyTask,
  TaskOrEmpty,
  TaskType,
  TitleColumn,
} from '@ObeoNetwork/gantt-task-react';
import { GQLGantt, GQLTask, GQLTaskDetail, SelectableTask } from '../graphql/subscription/GanttSubscription.types';
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
    const dependencies: Dependency[] = gQLTask.dependencyTaskIds.map((dependencyTaskId) => {
      return {
        sourceId: dependencyTaskId,
        sourceTarget: 'endOfTask',
        ownTarget: 'startOfTask',
      };
    });
    let task: SelectableTask | EmptyTask;
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
        hideChildren: false,
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

const ProgressColumn: React.FC<ColumnProps> = ({ data: { task } }) => {
  if (task.type === 'project' || task.type === 'task') {
    return <>{task.progress}%</>;
  }

  return null;
};

export const getAllColumns = () => {
  const columnEnums = [
    TaskListColumnEnum.NAME,
    TaskListColumnEnum.FROM,
    TaskListColumnEnum.TO,
    TaskListColumnEnum.PROGRESS,
  ];
  const columns: Column[] = [];
  columnEnums.forEach((columnType) => {
    if (columnType === TaskListColumnEnum.NAME) {
      columns.push({
        component: TitleColumn,
        width: 210,
        title: 'Name',
        id: TaskListColumnEnum.NAME,
      });
    } else if (columnType === TaskListColumnEnum.FROM) {
      columns.push({
        component: DateStartColumn,
        width: 150,
        title: 'Date of start',
        id: TaskListColumnEnum.FROM,
      });
    } else if (columnType === TaskListColumnEnum.TO) {
      columns.push({
        component: DateEndColumn,
        width: 150,
        title: 'Date of end',
        id: TaskListColumnEnum.TO,
      });
    } else if (columnType === TaskListColumnEnum.PROGRESS) {
      columns.push({
        component: ProgressColumn,
        width: 40,
        title: 'Progress',
        id: TaskListColumnEnum.PROGRESS,
      });
    }
  });

  return columns;
};
