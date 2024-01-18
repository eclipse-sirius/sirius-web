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
import { Task, TaskListColumnEnum, ViewMode } from '@ObeoNetwork/gantt-task-react';

export interface GanttState {
  zoomLevel: ViewMode;
  columns: TaskListColumnEnum[];
  displayColumns: boolean;
}

export interface GanttProps {
  editingContextId: string;
  representationId: string;
  tasks: Task[];
  setSelection: (selection: Selection) => void;
  onCreateTask: (Task: Task) => void;
  onEditTask: (Task: Task) => void;
  onDeleteTask: (Task: Task) => void;
  onExpandCollapse: (Task: Task) => void;
  onSelect: (Task, isSelected: boolean) => void;
}
