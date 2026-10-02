/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { ViewMode } from '@ObeoNetwork/gantt-task-react';
import { TaskListColumnEnum } from '../representation/Gantt.types';

export interface ToolbarProps {
  representationId: string;
  viewMode: ViewMode;
  zoomLevel: number;
  columns: TaskListColumnEnum[];
  onChangeViewMode: (_: ViewMode) => any;
  onChangeZoomLevel: (_: number) => any;
  onChangeDisplayColumns: () => any;
  onChangeColumns: (_: TaskListColumnEnum[]) => any;
  fullscreenNode: React.RefObject<HTMLDivElement | null>;
}

export interface ToolbarState {
  modal: 'share' | null;
}
