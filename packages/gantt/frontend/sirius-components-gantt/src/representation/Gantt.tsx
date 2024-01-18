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
import {
  Gantt as GanttDiagram,
  Task,
  TaskListColumn,
  TaskListColumnEnum,
  ViewMode,
} from '@ObeoNetwork/gantt-task-react';
import '@ObeoNetwork/gantt-task-react/dist/style.css';
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { useState } from 'react';
import { SelectableTask } from '../../graphql/subscription/GanttSubscription.types';
import { getContextalPalette } from '../palette/ContextualPalette';
import { Toolbar } from '../toolbar/Toolbar';
import { GanttProps, GanttState } from './Gantt.types';

export const Gantt = ({
  editingContextId,
  representationId,
  tasks,
  setSelection,
  onCreateTask,
  onEditTask,
  onExpandCollapse,
  onDeleteTask,
}: GanttProps) => {
  const [{ zoomLevel, columns, displayColumns }, setState] = useState<GanttState>({
    zoomLevel: ViewMode.Day,
    columns: [TaskListColumnEnum.NAME, TaskListColumnEnum.FROM, TaskListColumnEnum.TO, TaskListColumnEnum.ASSIGNEE],
    displayColumns: true,
  });
  let columnWidth = 65;
  if (zoomLevel === ViewMode.Month) {
    columnWidth = 300;
  } else if (zoomLevel === ViewMode.Week) {
    columnWidth = 250;
  }

  const onwheel = (wheelEvent: WheelEvent) => {
    const deltaY = wheelEvent.deltaY;

    if (deltaY < 0 && zoomLevel !== ViewMode.Hour) {
      const currentIndex = Object.values(ViewMode).indexOf(zoomLevel);
      const newZoomLevel = Object.values(ViewMode).at(currentIndex - 1);
      if (newZoomLevel) {
        setState((prevState) => {
          return { ...prevState, zoomLevel: newZoomLevel };
        });
      }
    } else if (deltaY > 0 && zoomLevel !== ViewMode.Month) {
      const currentIndex = Object.values(ViewMode).indexOf(zoomLevel);
      const newZoomLevel = Object.values(ViewMode).at(currentIndex + 1);
      if (newZoomLevel) {
        setState((prevState) => {
          return { ...prevState, zoomLevel: newZoomLevel };
        });
      }
    }
  };

  const handleSelection = (task: Task) => {
    const selectableTask = task as SelectableTask;
    const newSelection: Selection = {
      entries: [
        {
          id: selectableTask.targetObjectId,
          label: selectableTask.targetObjectLabel,
          kind: selectableTask.targetObjectKind,
        },
      ],
    };

    setSelection(newSelection);
  };

  const allColumns: TaskListColumn[] = [
    { columntype: TaskListColumnEnum.NAME, columnWidth: '120px' },
    { columntype: TaskListColumnEnum.FROM, columnWidth: '155px' },
    { columntype: TaskListColumnEnum.TO, columnWidth: '155px' },
    { columntype: TaskListColumnEnum.ASSIGNEE, columnWidth: '80px' },
  ];
  const columnsToDisplay = allColumns.filter((column) => {
    return columns.includes(column.columntype);
  });

  const onChangeZoomLevel = (zoomLevel: ViewMode) => {
    setState((prevState) => {
      return { ...prevState, zoomLevel: zoomLevel };
    });
  };
  const onChangeDisplayColumns = () => {
    setState((prevState) => {
      return { ...prevState, displayColumns: !displayColumns };
    });
  };
  const onChangeColumns = (columnTypes: TaskListColumnEnum[]) => {
    setState((prevState) => {
      return { ...prevState, columns: columnTypes };
    });
  };

  return (
    <div>
      <Toolbar
        editingContextId={editingContextId}
        representationId={representationId}
        zoomLevel={zoomLevel}
        columns={columns}
        tasks={tasks}
        onChangeZoomLevel={onChangeZoomLevel}
        onChangeDisplayColumns={onChangeDisplayColumns}
        onChangeColumns={onChangeColumns}
      />
      <GanttDiagram
        tasks={tasks}
        enableGridDrag={true}
        displayTaskList={displayColumns}
        columns={columnsToDisplay}
        viewMode={zoomLevel}
        onDateChange={onEditTask}
        onProgressChange={onEditTask}
        onDelete={onDeleteTask}
        onDoubleClick={onExpandCollapse}
        onSelect={handleSelection}
        onExpanderClick={onExpandCollapse}
        columnWidth={columnWidth}
        onWheel={onwheel}
        ContextualPalette={getContextalPalette({ onCreateTask, onDeleteTask, onEditTask })}
      />
    </div>
  );
};
