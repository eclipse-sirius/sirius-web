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
export interface GQLDeleteGanttTaskPayload {
  __typename: string;
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
  editGanttTask: GQLEditGanttTaskPayload;
}
export interface GQLEditGanttTaskPayload {
  __typename: string;
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
  createTask: GQLCreateGanttTaskPayload;
}
export interface GQLCreateGanttTaskPayload {
  __typename: string;
}
