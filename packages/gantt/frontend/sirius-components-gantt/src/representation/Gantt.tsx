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
import '@ObeoNetwork/gantt-task-react';
import { ColorStyles, Column, Gantt as GanttDiagram, Task, TaskOrEmpty, ViewMode } from '@ObeoNetwork/gantt-task-react';
import '@ObeoNetwork/gantt-task-react/dist/style.css';
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import { useRef, useState } from 'react';
import { SelectableTask } from '../graphql/subscription/GanttSubscription.types';
import { getAllColumns } from '../helper/helper';
import { getContextalPalette } from '../palette/ContextualPalette';
import { Toolbar } from '../toolbar/Toolbar';
import { GanttProps, GanttState, TaskListColumnEnum } from './Gantt.types';

const useGanttStyle = makeStyles((theme) => ({
  dragOver: {
    color: theme.palette.primary.main,
  },
  ganttContainer: {
    backgroundColor: theme.palette.background.default,
  },
}));

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
  const [{ zoomLevel, selectedColumns, columns, displayColumns }, setState] = useState<GanttState>({
    zoomLevel: ViewMode.Day,
    selectedColumns: [
      TaskListColumnEnum.NAME,
      TaskListColumnEnum.FROM,
      TaskListColumnEnum.TO,
      TaskListColumnEnum.PROGRESS,
    ],
    columns: getAllColumns(),
    displayColumns: true,
  });
  const ganttContainerRef = useRef<HTMLDivElement | null>(null);

  const ganttClasses = useGanttStyle();

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

  const handleSelection = (task: TaskOrEmpty) => {
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
      return { ...prevState, selectedColumns: columnTypes };
    });
  };

  const handleDeleteTaskOnContextualPalette = (task: Task) => {
    onDeleteTask([task]);
  };

  let tableColumns: Column[] = [];
  if (displayColumns) {
    tableColumns = columns.filter((col) => {
      if (col.id != undefined) {
        return selectedColumns.map((colEnum) => colEnum as string).includes(col.id);
      }
      return false;
    });
  }

  const colors: Partial<ColorStyles> = {
    taskDragColor: ganttClasses.dragOver,
  };

  return (
    <div ref={ganttContainerRef} className={ganttClasses.ganttContainer} data-testid={`gantt-representation`}>
      <Toolbar
        editingContextId={editingContextId}
        representationId={representationId}
        zoomLevel={zoomLevel}
        columns={selectedColumns}
        tasks={tasks}
        onChangeZoomLevel={onChangeZoomLevel}
        onChangeDisplayColumns={onChangeDisplayColumns}
        onChangeColumns={onChangeColumns}
        fullscreenNode={ganttContainerRef}
      />
      <GanttDiagram
        tasks={tasks}
        columns={tableColumns}
        colors={colors}
        viewMode={zoomLevel}
        onDateChange={onEditTask}
        onProgressChange={onEditTask}
        onDelete={onDeleteTask}
        onDoubleClick={onExpandCollapse}
        onClick={handleSelection}
        roundEndDate={(date: Date) => date}
        roundStartDate={(date: Date) => date}
        onWheel={onwheel}
        ContextualPalette={getContextalPalette({
          onCreateTask,
          onDeleteTask: handleDeleteTaskOnContextualPalette,
          onEditTask,
        })}
        isMoveChildsWithParent={false}
      />
    </div>
  );
};
