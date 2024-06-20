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
import { Column, Task, TaskOrEmpty, ViewMode } from '@ObeoNetwork/gantt-task-react';
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { GQLColumn, GQLGanttDateRounding } from '../graphql/subscription/GanttSubscription.types';

export enum TaskListColumnEnum {
  NAME = 'NAME',
  START_DATE = 'START_DATE',
  END_DATE = 'END_DATE',
  PROGRESS = 'PROGRESS',
  ASSIGNEES = 'ASSIGNEES',
}

export interface GanttState {
  zoomLevel: ViewMode;
  selectedColumns: TaskListColumnEnum[];
  displayedColumns: Column[];
  displayColumns: boolean;
}

export interface GanttProps {
  editingContextId: string;
  representationId: string;
  tasks: TaskOrEmpty[];
  gqlColumns: GQLColumn[];
  gqlDateRounding: GQLGanttDateRounding;
  setSelection: (selection: Selection) => void;
  onCreateTask: (task: Task) => void;
  onEditTask: (task: TaskOrEmpty) => void;
  onDeleteTask: (tasks: readonly TaskOrEmpty[]) => void;
  onExpandCollapse: (task: Task) => void;
  onDropTask: (droppedTask: TaskOrEmpty, targetTask: TaskOrEmpty | undefined, dropIndex: number) => void;
  onCreateTaskDependency: (sourceTaskId: string, targetTaskId: string) => void;
  onDeleteTaskDependency: (sourceTaskId: string, targetTaskId: string) => void;
  onChangeTaskCollapseState: (taskId: string, collapsed: boolean) => void;
  onChangeColumn: (columnId: string, displayed: boolean, width: number) => void;
}
