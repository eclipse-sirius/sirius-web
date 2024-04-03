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
import { MutationResult, useMutation } from '@apollo/client';
import { GQLErrorPayload, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import { GQLTaskDetail } from '../subscription/GanttSubscription.types';
import {
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
  GQLDeleteTaskVariables,
  GQLDropGanttTaskInput,
  GQLDropTaskData,
  GQLDropTaskVariables,
  GQLEditGanttTaskInput,
  GQLEditTaskData,
  GQLEditTaskVariables,
  GQLPayload,
  UseGanttMutations,
} from './GanttMutation.types';
import {
  changeTaskCollapseStateMutation,
  createTaskDependencyMutation,
  createTaskMutation,
  deleteTaskMutation,
  dropTaskMutation,
  editTaskMutation,
} from './ganttMutation';

const isErrorPayload = (payload): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload): payload is GQLPayload => payload.__typename === 'SuccessPayload';

function useErrorReporting<T>(
  result: MutationResult<T>,
  extractPayload: (data: T | null) => GQLPayload | undefined,
  addErrorMessage: (message: string) => any,
  addMessages: (messages: any) => any
) {
  useEffect(() => {
    const { loading, data, error } = result;
    if (!loading) {
      if (error) {
        addErrorMessage(error.message);
      }
      const payload: GQLPayload | undefined = extractPayload(data || null);
      if (payload && (isSuccessPayload(payload) || isErrorPayload(payload))) {
        const { messages } = payload;
        addMessages(messages);
      }
    }
  }, [result.loading, result.data, result.error]);
}

export const useGanttMutations = (editingContextId: string, representationId: string): UseGanttMutations => {
  const { addErrorMessage, addMessages } = useMultiToast();

  const [mutationDeleteGanttTask, mutationDeleteTaskResult] = useMutation<GQLDeleteTaskData, GQLDeleteTaskVariables>(
    deleteTaskMutation
  );
  useErrorReporting(mutationDeleteTaskResult, (data) => data?.deleteGanttTask, addErrorMessage, addMessages);

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
  useErrorReporting(mutationCreateTaskResult, (data) => data?.createTask, addErrorMessage, addMessages);

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
  useErrorReporting(mutationEditTaskResult, (data) => data?.editGanttTask, addErrorMessage, addMessages);

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
  useErrorReporting(mutationDropTaskResult, (data) => data?.payload, addErrorMessage, addMessages);

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
  useErrorReporting(mutationCreateTaskDependencyResult, (data) => data?.payload, addErrorMessage, addMessages);

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

  const [mutationChangeTaskCollapseState, mutationChangeTaskCollapseStateResult] = useMutation<
    GQLChangeTaskCollapseStateData,
    GQLChangeTaskCollapseStateVariables
  >(changeTaskCollapseStateMutation);
  useErrorReporting(mutationChangeTaskCollapseStateResult, (data) => data?.payload, addErrorMessage, addMessages);

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

  return {
    deleteTask,
    createTask,
    editTask,
    dropTask,
    createTaskDependency,
    changeTaskCollapseState,
  };
};
