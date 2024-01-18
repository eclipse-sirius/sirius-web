/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { Task, TaskListColumnEnum, ViewMode } from '@ObeoNetwork/gantt-task-react/dist/types/public-types';

export interface ToolbarProps {
  editingContextId: string;
  representationId: string;
  zoomLevel: ViewMode;
  columns: TaskListColumnEnum[];
  tasks: Task[];
  onChangeZoomLevel: (_: ViewMode) => any;
  onChangeDisplayColumns: () => any;
  onChangeColumns: (_: TaskListColumnEnum[]) => any;
}

export interface ToolbarState {
  modal: 'share' | null;
}
