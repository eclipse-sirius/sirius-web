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
import { Task } from '@ObeoNetwork/gantt-task-react';
import { TaskType } from '@ObeoNetwork/gantt-task-react/dist/types/public-types';
import { GQLGantt, GQLTask, GQLTaskDetail, SelectableTask } from '../../graphql/subscription/GanttSubscription.types';

export function getTaskFromGQLTask(gQLTasks: GQLTask[], parentId: string): Task[] {
  const tasks: Task[] = [];
  gQLTasks.forEach((gQLTask: GQLTask) => {
    let type: TaskType = 'task';
    const isProject = gQLTask.subTasks && gQLTask.subTasks.length > 0;
    if (isProject) {
      type = 'project';
    } else if (gQLTask.detail.startTime === gQLTask.detail.endTime) {
      type = 'milestone';
    }
    const task: SelectableTask = {
      id: gQLTask.id,
      name: gQLTask.detail.name,
      progress: gQLTask.detail.progress,
      type,
      dependencies: gQLTask.dependencyTaskIds,
      project: parentId,
      hideChildren: false,
      targetObjectId: gQLTask.targetObjectId,
      targetObjectKind: gQLTask.targetObjectKind,
      targetObjectLabel: gQLTask.targetObjectLabel,
    };
    if (!!gQLTask.detail.startTime) {
      task.start = new Date(gQLTask.detail.startTime);
    }
    if (!!gQLTask.detail.endTime) {
      task.end = new Date(gQLTask.detail.endTime);
    }

    tasks.push(task);
    if (isProject) {
      if (gQLTask.detail.computeStartEndDynamically) {
        task.isDisabled = true;
      }
      const children: Task[] = getTaskFromGQLTask(gQLTask.subTasks, gQLTask.id);
      tasks.push(...children);
    }
  });
  return tasks;
}

export const updateTask = (gantt: GQLGantt, taskId: string, newDetail: GQLTaskDetail) => {
  const task = findTask(gantt.tasks, taskId);
  if (!!task) {
    task.detail = newDetail;
  }
};

const findTask = (tasks: GQLTask[], taskId: string): GQLTask | undefined => {
  let foundTask: GQLTask = undefined;
  tasks.every((value) => {
    if (value.id === taskId) {
      foundTask = value;
    } else {
      foundTask = findTask(value.subTasks, taskId);
    }
    return !foundTask;
  });
  return foundTask;
};
