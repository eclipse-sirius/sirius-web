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
import { useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { GQLTaskDetail } from '../subscription/GanttSubscription.types';
import {
  GQLChangeColumnData,
  GQLChangeColumnInput,
  GQLChangeColumnVariables,
  GQLChangeTaskCollapseStateData,
  GQLChangeTaskCollapseStateInput,
  GQLChangeTaskCollapseStateVariables,
  GQLCreateGanttTaskDependencyInput,
  GQLCreateGanttTaskInput,
  GQLCreateTaskData,
  GQLCreateTaskDependencyData,
  GQLCreateTaskDependencyVariables,
  GQLCreateTaskVariables,
  GQLDeleteGanttTaskInput,
  GQLDeleteTaskData,
  GQLDeleteTaskDependencyData,
  GQLDeleteTaskDependencyInput,
  GQLDeleteTaskDependencyVariables,
  GQLDeleteTaskVariables,
  GQLDropGanttTaskInput,
  GQLDropTaskData,
  GQLDropTaskVariables,
  GQLEditGanttTaskInput,
  GQLEditTaskData,
  GQLEditTaskVariables,
  UseGanttMutations,
} from './GanttMutation.types';
import {
  changeColumnMutation,
  changeTaskCollapseStateMutation,
  createTaskDependencyMutation,
  createTaskMutation,
  deleteTaskDependencyMutation,
  deleteTaskMutation,
  dropTaskMutation,
  editTaskMutation,
} from './ganttMutation';

export const useGanttMutations = (editingContextId: string, representationId: string): UseGanttMutations => {
  const [mutationDeleteGanttTask, mutationDeleteTaskResult] = useMutation<GQLDeleteTaskData, GQLDeleteTaskVariables>(
    deleteTaskMutation
  );
  useReporting(mutationDeleteTaskResult, (data: GQLDeleteTaskData) => data.deleteGanttTask);

  const deleteTask = (tasks: readonly TaskOrEmpty[]) => {
    const taskId = tasks?.at(0)?.id;
    if (taskId) {
      const input: GQLDeleteGanttTaskInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        taskId,
      };
      mutationDeleteGanttTask({ variables: { input } });
    }
  };

  const [mutationCreateTask, mutationCreateTaskResult] = useMutation<GQLCreateTaskData, GQLCreateTaskVariables>(
    createTaskMutation
  );
  useReporting(mutationCreateTaskResult, (data: GQLCreateTaskData) => data.createGanttTask);

  const createTask = (task: Task) => {
    const input: GQLCreateGanttTaskInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      currentTaskId: task.id,
    };
    mutationCreateTask({ variables: { input } });
  };

  const [mutationEditTask, mutationEditTaskResult] = useMutation<GQLEditTaskData, GQLEditTaskVariables>(
    editTaskMutation
  );
  useReporting(mutationEditTaskResult, (data: GQLEditTaskData) => data.editGanttTask);

  const editTask = (task: TaskOrEmpty) => {
    const newDetail: GQLTaskDetail = {
      name: task.name,
      description: '',
      startTime: (task as Task)?.start?.toISOString(),
      endTime: (task as Task)?.end?.toISOString(),
      progress: (task as Task)?.progress,
      computeStartEndDynamically: task.isDisabled,
    };
    const input: GQLEditGanttTaskInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      taskId: task.id,
      newDetail,
    };

    mutationEditTask({ variables: { input } });
  };

  const [mutationDropTask, mutationDropTaskResult] = useMutation<GQLDropTaskData, GQLDropTaskVariables>(
    dropTaskMutation
  );
  useReporting(mutationDropTaskResult, (data: GQLDropTaskData) => data.dropGanttTask);

  const dropTask = (droppedTask: TaskOrEmpty, targetTask: TaskOrEmpty | undefined, dropIndex: number) => {
    const input: GQLDropGanttTaskInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      droppedTaskId: droppedTask.id,
      dropIndex,
    };
    if (targetTask) {
      input.targetTaskId = targetTask.id;
    }

    mutationDropTask({ variables: { input } });
  };

  const [mutationCreateTaskDependency, mutationCreateTaskDependencyResult] = useMutation<
    GQLCreateTaskDependencyData,
    GQLCreateTaskDependencyVariables
  >(createTaskDependencyMutation);
  useReporting(
    mutationCreateTaskDependencyResult,
    (data: GQLCreateTaskDependencyData) => data.createGanttTaskDependency
  );

  const createTaskDependency = (sourceTaskId: string, targetTaskId: string) => {
    const input: GQLCreateGanttTaskDependencyInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      sourceTaskId,
      targetTaskId,
    };

    mutationCreateTaskDependency({ variables: { input } });
  };

  const [mutationDeleteTaskDependency, mutationDeleteTaskDependencyResult] = useMutation<
    GQLDeleteTaskDependencyData,
    GQLDeleteTaskDependencyVariables
  >(deleteTaskDependencyMutation);
  useReporting(
    mutationDeleteTaskDependencyResult,
    (data: GQLDeleteTaskDependencyData) => data.deleteGanttTaskDependency
  );

  const deleteTaskDependency = (sourceTaskId: string, targetTaskId: string) => {
    const input: GQLDeleteTaskDependencyInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      sourceTaskId,
      targetTaskId,
    };

    mutationDeleteTaskDependency({ variables: { input } });
  };

  const [mutationChangeTaskCollapseState, mutationChangeTaskCollapseStateResult] = useMutation<
    GQLChangeTaskCollapseStateData,
    GQLChangeTaskCollapseStateVariables
  >(changeTaskCollapseStateMutation);
  useReporting(
    mutationChangeTaskCollapseStateResult,
    (data: GQLChangeTaskCollapseStateData) => data.changeGanttTaskCollapseState
  );

  const changeTaskCollapseState = (taskId: string, collapsed: boolean) => {
    const input: GQLChangeTaskCollapseStateInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      taskId,
      collapsed,
    };

    mutationChangeTaskCollapseState({ variables: { input } });
  };

  const [mutationChangeColumn, mutationChangeColumnResult] = useMutation<GQLChangeColumnData, GQLChangeColumnVariables>(
    changeColumnMutation
  );
  useReporting(mutationChangeColumnResult, (data: GQLChangeColumnData) => data.changeGanttColumn);

  const changeColumn = (columnId: string, displayed: boolean, width: number) => {
    const input: GQLChangeColumnInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      columnId,
      displayed,
      width,
    };

    mutationChangeColumn({ variables: { input } });
  };

  return {
    deleteTask,
    createTask,
    editTask,
    dropTask,
    createTaskDependency,
    deleteTaskDependency,
    changeTaskCollapseState,
    changeColumn,
  };
};
