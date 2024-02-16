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

import { Task, TaskOrEmpty } from '@ObeoNetwork/gantt-task-react';
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface UseGanttMutations {
  editTask: (task: TaskOrEmpty) => void;
  createTask: (task: Task) => void;
  deleteTask: (tasks: readonly TaskOrEmpty[]) => void;
  dropTask: (droppedTask: TaskOrEmpty, targetTask: TaskOrEmpty | undefined, dropIndex: number) => void;
}

export interface GQLDeleteTaskVariables {
  input: GQLDeleteGanttTaskInput;
}
export interface GQLDeleteGanttTaskInput {
  id: string;
  editingContextId: string;
  representationId: string;
  taskId: string;
}
export interface GQLDeleteTaskData {
  deleteGanttTask: GQLPayload;
}

export interface GQLEditTaskVariables {
  input: GQLEditGanttTaskInput;
}
export interface GQLEditGanttTaskDetailInput {
  name: string;
  description: string;
  startTime?: string;
  endTime?: string;
  progress: number;
}
export interface GQLEditGanttTaskInput {
  id: string;
  editingContextId: string;
  representationId: string;
  taskId: string;
  newDetail: GQLEditGanttTaskDetailInput;
}
export interface GQLEditTaskData {
  editGanttTask: GQLPayload;
}

export interface GQLCreateTaskVariables {
  input: GQLCreateGanttTaskInput;
}
export interface GQLCreateGanttTaskInput {
  id: string;
  editingContextId: string;
  representationId: string;
  currentTaskId: string;
}
export interface GQLCreateTaskData {
  createTask: GQLPayload;
}

export interface GQLDropTaskVariables {
  input: GQLDropGanttTaskInput;
}
export interface GQLDropGanttTaskInput {
  id: string;
  editingContextId: string;
  representationId: string;
  droppedTaskId: string;
  targetTaskId?: string;
  dropIndex: number;
}

export interface GQLDropTaskData {
  payload: GQLPayload;
}

export interface GQLPayload {
  __typename: string;
  messages: GQLMessage[];
}
