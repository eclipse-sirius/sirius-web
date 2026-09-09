/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { Task } from '@obeonetwork/gantt-task-react';

export interface TaskContextualPaletteProps {
  onCreateTask: (task: Task) => void;
  onEditTask: (task: Task) => void;
  onDeleteTask: (task: Task) => void;
}

export interface TaskDependencyContextualPaletteProps {
  onDeleteTaskDependency: (sourceTaskId: string, targetTaskId: string) => void;
}
