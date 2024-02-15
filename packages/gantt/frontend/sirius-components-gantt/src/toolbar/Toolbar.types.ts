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
import { TaskOrEmpty, ViewMode } from '@ObeoNetwork/gantt-task-react';
import { TaskListColumnEnum } from '../representation/Gantt.types';

export interface ToolbarProps {
  editingContextId: string;
  representationId: string;
  zoomLevel: ViewMode;
  columns: TaskListColumnEnum[];
  tasks: TaskOrEmpty[];
  onChangeZoomLevel: (_: ViewMode) => any;
  onChangeDisplayColumns: () => any;
  onChangeColumns: (_: TaskListColumnEnum[]) => any;
  fullscreenNode: React.RefObject<HTMLDivElement>;
}

export interface ToolbarState {
  modal: 'share' | null;
}
