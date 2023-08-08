/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { GQLTask, SelectableTask } from '../representation/GanttRepresentation.types';
export function getTaskFromGQLTask(gQLTasks: GQLTask[], parentId: string) {
  const tasks: Task[] = [];
  gQLTasks.forEach((gQLTask) => {
    let type: TaskType = 'task';
    const isProject = gQLTask.subTasks && gQLTask.subTasks.length > 0;
    if (isProject) {
      type = 'project';
    } else if (gQLTask.detail.startDate === gQLTask.detail.endDate) {
      type = 'milestone';
    }
    const task: SelectableTask = {
      id: gQLTask.id,
      name: gQLTask.detail.name,
      start: new Date(gQLTask.detail.startDate * 1000),
      end: new Date(gQLTask.detail.endDate * 1000),
      progress: gQLTask.detail.progress,
      type,
      dependencies: gQLTask.dependencies?.map((dep) => dep.id),
      project: parentId,
      hideChildren: false,
      targetObjectId: gQLTask.targetObjectId,
      targetObjectKind: gQLTask.targetObjectKind,
      targetObjectLabel: gQLTask.targetObjectLabel,
    };

    tasks.push(task);
    if (isProject) {
      const children: Task[] = getTaskFromGQLTask(gQLTask.subTasks, gQLTask.id);
      tasks.push(...children);
    }
  });
  return tasks;
}
