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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { Column, Task, TaskOrEmpty, ViewMode } from '@ObeoNetwork/gantt-task-react';

export enum TaskListColumnEnum {
  NAME = 'Name',
  FROM = 'From',
  TO = 'To',
  PROGRESS = 'Progress',
  ASSIGNEE = 'Assignee',
}

export interface GanttState {
  zoomLevel: ViewMode;
  selectedColumns: TaskListColumnEnum[];
  columns: Column[];
  displayColumns: boolean;
}

export interface GanttProps {
  editingContextId: string;
  representationId: string;
  tasks: TaskOrEmpty[];
  setSelection: (selection: Selection) => void;
  onCreateTask: (task: Task) => void;
  onEditTask: (task: TaskOrEmpty) => void;
  onDeleteTask: (tasks: readonly TaskOrEmpty[]) => void;
  onExpandCollapse: (task: Task) => void;
  onDropTask: (droppedTask: TaskOrEmpty, targetTask: TaskOrEmpty | undefined, dropIndex: number) => void;
}
