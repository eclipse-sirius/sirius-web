/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { Gantt as GanttDiagram, Task, TaskListColumnEnum, ViewMode } from '@ObeoNetwork/gantt-task-react';
import '@ObeoNetwork/gantt-task-react/dist/style.css';
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useState } from 'react';
import { GanttProps } from './Gantt.types';
import { SelectableTask } from './representation/GanttRepresentation.types';
import { Toolbar } from './toolbar/Toolbar';

export const Gantt = ({ tasks, onTaskChange, onExpandCollapse, onTaskDelete }: GanttProps) => {
  const { setSelection } = useSelection();

  const [zoomLevel, setZoomLevel] = useState<ViewMode>(ViewMode.Day);
  const allColumns = [
    { columntype: TaskListColumnEnum.NAME, columnWidth: '120px' },
    { columntype: TaskListColumnEnum.FROM, columnWidth: '155px' },
    { columntype: TaskListColumnEnum.TO, columnWidth: '155px' },
    { columntype: TaskListColumnEnum.ASSIGNEE, columnWidth: '80px' },
  ];
  const [columns, setColumns] = useState<any[]>(allColumns);

  const [isColumnDisplayed, setIsColumnDisplayed] = useState<boolean>(true);
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
        setZoomLevel(newZoomLevel);
      }
    } else if (deltaY > 0 && zoomLevel !== ViewMode.Month) {
      const currentIndex = Object.values(ViewMode).indexOf(zoomLevel);
      const newZoomLevel = Object.values(ViewMode).at(currentIndex + 1);
      if (newZoomLevel) {
        setZoomLevel(newZoomLevel);
      }
    }
  };

  const onFitToScreen = () => {
    const minTime: number = tasks
      .map((task) => task.start.getTime())
      .reduce((previous, current) => {
        return previous < current ? previous : current;
      });
    const maxTime: number = tasks
      .map((task) => task.end.getTime())
      .reduce((previous, current) => {
        return previous > current ? previous : current;
      });
    const fullTime: number = (maxTime - minTime) / 1000 / 3600;
    let zoomLevel: ViewMode = ViewMode.Day;
    if (fullTime < 10) {
      zoomLevel = ViewMode.Hour;
    } else if (fullTime < 48) {
      zoomLevel = ViewMode.QuarterDay;
    } else if (fullTime < 24 * 4) {
      zoomLevel = ViewMode.HalfDay;
    } else if (fullTime < 24 * 10) {
      zoomLevel = ViewMode.Day;
    } else if (fullTime < 24 * 14) {
      zoomLevel = ViewMode.Week;
    } else {
      zoomLevel = ViewMode.Month;
    }

    setZoomLevel(zoomLevel);
  };

  const onColumnDisplayed = () => {
    setIsColumnDisplayed(!isColumnDisplayed);
  };

  const handleChangeColumns = (columnTypes: any[]) => {
    const newColumns = [];
    allColumns.forEach((column) => {
      if (columnTypes.includes(column.columntype)) {
        newColumns.push(column);
      }
    });
    setColumns(newColumns);
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
  const toolbarColumns = columns.map((col) => col.columntype);

  return (
    <div>
      <Toolbar
        onZoomLevel={setZoomLevel}
        onColumnDisplayed={onColumnDisplayed}
        onFitToScreen={onFitToScreen}
        zoomLevel={zoomLevel}
        columns={toolbarColumns}
        onChangeColumns={handleChangeColumns}></Toolbar>
      <GanttDiagram
        tasks={tasks}
        enableGridDrag={true}
        displayTaskList={isColumnDisplayed}
        columns={columns}
        viewMode={zoomLevel}
        onDateChange={onTaskChange}
        onProgressChange={onTaskChange}
        onDelete={onTaskDelete}
        onDoubleClick={onExpandCollapse}
        onSelect={handleSelection}
        onExpanderClick={onExpandCollapse}
        columnWidth={columnWidth}
        onWheel={onwheel}
      />
    </div>
  );
};
