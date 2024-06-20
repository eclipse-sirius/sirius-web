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
import { GQLErrorPayload, GQLSuccessPayload } from '@eclipse-sirius/sirius-components-core';

export interface UseGanttMutations {
  editTask: (task: TaskOrEmpty) => void;
  createTask: (task: Task) => void;
  deleteTask: (tasks: readonly TaskOrEmpty[]) => void;
  dropTask: (droppedTask: TaskOrEmpty, targetTask: TaskOrEmpty | undefined, dropIndex: number) => void;
  createTaskDependency: (sourceTaskId: string, targetTaskId: string) => void;
  deleteTaskDependency: (sourceTaskId: string, targetTaskId: string) => void;
  changeTaskCollapseState: (taskId: string, collapsed: boolean) => void;
  changeColumn: (columnId: string, displayed: boolean, width: number) => void;
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
  deleteGanttTask: GQLDeleteGanttTaskPayload;
}

export type GQLDeleteGanttTaskPayload = GQLErrorPayload | GQLSuccessPayload;

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
  editGanttTask: GQLEditGanttTaskPayload;
}

export type GQLEditGanttTaskPayload = GQLErrorPayload | GQLSuccessPayload;

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
  createGanttTask: GQLCreateGanttTaskPayload;
}

export type GQLCreateGanttTaskPayload = GQLErrorPayload | GQLSuccessPayload;

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
  dropGanttTask: GQLDropGanttTaskPayload;
}

export type GQLDropGanttTaskPayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLCreateTaskDependencyVariables {
  input: GQLCreateGanttTaskDependencyInput;
}
export interface GQLCreateGanttTaskDependencyInput {
  id: string;
  editingContextId: string;
  representationId: string;
  sourceTaskId: string;
  targetTaskId: string;
}
export interface GQLCreateTaskDependencyData {
  createGanttTaskDependency: GQLCreateGanttTaskDependencyPayload;
}

export type GQLCreateGanttTaskDependencyPayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLDeleteTaskDependencyVariables {
  input: GQLDeleteTaskDependencyInput;
}
export interface GQLDeleteTaskDependencyInput {
  id: string;
  editingContextId: string;
  representationId: string;
  sourceTaskId: string;
  targetTaskId: string;
}
export interface GQLDeleteTaskDependencyData {
  deleteGanttTaskDependency: GQLDeleteTaskDependencyPayload;
}

export type GQLDeleteTaskDependencyPayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLChangeTaskCollapseStateVariables {
  input: GQLChangeTaskCollapseStateInput;
}
export interface GQLChangeTaskCollapseStateInput {
  id: string;
  editingContextId: string;
  representationId: string;
  taskId: string;
  collapsed: boolean;
}
export interface GQLChangeTaskCollapseStateData {
  changeGanttTaskCollapseState: GQLChangeGanttTaskCollapseStatePayload;
}

export type GQLChangeGanttTaskCollapseStatePayload = GQLErrorPayload | GQLSuccessPayload;

export interface GQLChangeColumnVariables {
  input: GQLChangeColumnInput;
}
export interface GQLChangeColumnInput {
  id: string;
  editingContextId: string;
  representationId: string;
  columnId: string;
  displayed: boolean;
  width: number;
}
export interface GQLChangeColumnData {
  changeGanttColumn: GQLChangeGanttColumnPayload;
}

export type GQLChangeGanttColumnPayload = GQLErrorPayload | GQLSuccessPayload;
