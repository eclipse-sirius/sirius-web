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
import {
  BarMoveAction,
  ColorStyles,
  Column,
  DateExtremity,
  Distances,
  GanttDateRounding,
  GanttDateRoundingTimeUnit,
  Gantt as GanttDiagram,
  Icons,
  OnMoveTaskBeforeAfter,
  OnMoveTaskInside,
  OnRelationChange,
  RelationKind,
  Task,
  TaskOrEmpty,
  ViewMode,
} from '@ObeoNetwork/gantt-task-react';
import '@ObeoNetwork/gantt-task-react/dist/style.css';
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { Theme, makeStyles, useTheme } from '@material-ui/core/styles';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import { useEffect, useRef, useState } from 'react';
import { GQLGanttDateRoundingTimeUnit, SelectableTask } from '../graphql/subscription/GanttSubscription.types';
import { checkIsHoliday, getDisplayedColumns, getSelectedColumns, roundDate } from '../helper/helper';
import { getTaskContextualPalette, getTaskDependencyContextualPalette } from '../palette/ContextualPalette';
import { Toolbar } from '../toolbar/Toolbar';
import { GanttProps, GanttState, TaskListColumnEnum } from './Gantt.types';

const useGanttStyle = makeStyles((theme) => ({
  ganttContainer: {
    backgroundColor: theme.palette.background.default,
    overflowX: 'hidden',
    overflowY: 'auto',
    display: 'flex',
    flexDirection: 'column',
  },
  collapseToggleArrow: {
    cursor: 'pointer',
  },
  noChildren: {
    paddingLeft: '20px',
  },
}));

export const Gantt = ({
  editingContextId,
  representationId,
  tasks,
  gqlColumns,
  gqlDateRounding,
  setSelection,
  onCreateTask,
  onEditTask,
  onDeleteTask,
  onDropTask,
  onCreateTaskDependency,
  onDeleteTaskDependency,
  onChangeTaskCollapseState,
  onChangeColumn,
}: GanttProps) => {
  // all Columns state is used to avoid the blink effect when resizing the column
  const [{ zoomLevel, selectedColumns, displayedColumns, displayColumns }, setState] = useState<GanttState>({
    zoomLevel: ViewMode.Day,
    selectedColumns: getSelectedColumns(gqlColumns),
    displayedColumns: getDisplayedColumns(gqlColumns),
    displayColumns: true,
  });
  const ganttContainerRef = useRef<HTMLDivElement | null>(null);

  const ganttClasses = useGanttStyle();
  const theme: Theme = useTheme();

  const onwheel = (wheelEvent: WheelEvent) => {
    if (wheelEvent.ctrlKey) {
      wheelEvent.preventDefault();
      const deltaY = wheelEvent.deltaY;
      if (deltaY < 0 && zoomLevel !== ViewMode.Hour) {
        const currentIndex = Object.values(ViewMode).indexOf(zoomLevel);
        const newZoomLevel = Object.values(ViewMode).at(currentIndex - 1);
        if (newZoomLevel) {
          setState((prevState) => {
            return { ...prevState, zoomLevel: newZoomLevel };
          });
        }
      } else if (deltaY > 0 && zoomLevel !== ViewMode.Year) {
        const currentIndex = Object.values(ViewMode).indexOf(zoomLevel);
        const newZoomLevel = Object.values(ViewMode).at(currentIndex + 1);
        if (newZoomLevel) {
          setState((prevState) => {
            return { ...prevState, zoomLevel: newZoomLevel };
          });
        }
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

  const handleMoveTaskAfter: OnMoveTaskBeforeAfter = (task, taskForMove) => {
    const index = tasks.filter((t) => t.parent === task.parent).findIndex((t) => t.id === task.id);
    const parent = tasks.find((t) => t.id === task.parent);
    onDropTask(taskForMove, parent, index + 1);
  };

  const handleMoveTaskBefore: OnMoveTaskBeforeAfter = (task, taskForMove) => {
    const index = tasks.filter((t) => t.parent === task.parent).findIndex((t) => t.id === task.id);
    const parent = tasks.find((t) => t.id === task.parent);
    onDropTask(taskForMove, parent, index);
  };

  const handleMoveTaskInside: OnMoveTaskInside = (parent: Task, childs: readonly TaskOrEmpty[]) => {
    if (childs[0]) {
      onDropTask(childs[0], parent, -1);
    }
  };

  const handleRelationChange: OnRelationChange = (
    from: [Task, DateExtremity, number],
    to: [Task, DateExtremity, number]
  ) => {
    if (from[0].id !== to[0].id) {
      onCreateTaskDependency(from[0].id, to[0].id);
    }
  };

  // Columns display
  useEffect(() => {
    // This useEffect is use if the column resize is done externally
    if (gqlColumns) {
      setState((prevState) => {
        return {
          ...prevState,
          selectedColumns: getSelectedColumns(gqlColumns),
          displayedColumns: getDisplayedColumns(gqlColumns),
        };
      });
    }
  }, [gqlColumns]);

  const onChangeDisplayColumns = () => {
    setState((prevState) => {
      return { ...prevState, displayColumns: !displayColumns };
    });
  };
  const onChangeColumns = (columnTypes: TaskListColumnEnum[]) => {
    setState((prevState) => {
      const allColumns = gqlColumns.map((gqlColumn) => {
        return { ...gqlColumn, displayed: columnTypes.find((colType) => colType === gqlColumn.id) !== undefined };
      });
      return {
        ...prevState,
        selectedColumns: getSelectedColumns(allColumns),
        displayedColumns: getDisplayedColumns(allColumns),
      };
    });

    const changedColumn = gqlColumns.find((gqlColumn) => {
      const colThatIsDisplayed = columnTypes.find((colType) => colType === gqlColumn.id);
      return (
        (colThatIsDisplayed === undefined && gqlColumn.displayed) ||
        (colThatIsDisplayed !== undefined && !gqlColumn.displayed)
      );
    });
    if (changedColumn) {
      onChangeColumn(changedColumn.id, !changedColumn.displayed, changedColumn.width);
    }
  };
  const handleResizeColumn = (nextColumns: readonly Column[], columnIndex: number) => {
    const changedColumn = nextColumns.at(columnIndex);
    if (changedColumn) {
      onChangeColumn(changedColumn.id, true, changedColumn.width);

      // avoid the blink effect
      setState((prevState) => {
        return { ...prevState, displayedColumns: Array.from(nextColumns) };
      });
    }
  };

  let tableColumns: Column[] = [];
  if (displayColumns) {
    tableColumns = displayedColumns;
  }

  const colors: Partial<ColorStyles> = {
    taskDragColor: theme.palette.action.selected,
    selectedTaskBackgroundColor: theme.palette.action.selected,
  };

  const authorizedRelations: RelationKind[] = ['endToStart'];

  const distances: Partial<Distances> = {
    expandIconWidth: 25,
  };
  const icons: Partial<Icons> = {
    renderClosedIcon: (task: TaskOrEmpty) => (
      <ChevronRightIcon className={ganttClasses.collapseToggleArrow} data-testid={`collapsed-task-${task.name}`} />
    ),
    renderNoChildrenIcon: () => <div className={ganttClasses.noChildren} />,
    renderOpenedIcon: (task: TaskOrEmpty) => (
      <ExpandMoreIcon className={ganttClasses.collapseToggleArrow} data-testid={`expanded-task-${task.name}`} />
    ),
  };

  const getTimeUnit = (timeUnit: GQLGanttDateRoundingTimeUnit) => {
    if (timeUnit.toString() == GQLGanttDateRoundingTimeUnit[GQLGanttDateRoundingTimeUnit.DAY]) {
      return GanttDateRoundingTimeUnit.DAY;
    } else if (timeUnit.toString() == GQLGanttDateRoundingTimeUnit[GQLGanttDateRoundingTimeUnit.HOUR]) {
      return GanttDateRoundingTimeUnit.HOUR;
    } else if (timeUnit.toString() == GQLGanttDateRoundingTimeUnit[GQLGanttDateRoundingTimeUnit.MINUTE]) {
      return GanttDateRoundingTimeUnit.MINUTE;
    }
    return GanttDateRoundingTimeUnit.DAY;
  };

  const dateRounding: GanttDateRounding = {
    value: gqlDateRounding.value,
    timeUnit: getTimeUnit(gqlDateRounding.timeUnit),
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
        distances={distances}
        columns={tableColumns}
        colors={colors}
        viewMode={zoomLevel}
        onDateChange={onEditTask}
        onProgressChange={onEditTask}
        onDelete={onDeleteTask}
        onDoubleClick={(task) => onChangeTaskCollapseState(task.id, !task.hideChildren)}
        onClick={handleSelection}
        roundDate={(date: Date, _: ViewMode, dateExtremity: DateExtremity, action: BarMoveAction) =>
          roundDate(date, dateExtremity, action, dateRounding)
        }
        checkIsHoliday={checkIsHoliday}
        dateMoveStep={dateRounding}
        onWheel={onwheel}
        ContextualPalette={getTaskContextualPalette({
          onCreateTask,
          onDeleteTask: (task: Task) => onDeleteTask([task]),
          onEditTask,
        })}
        TaskDependencyContextualPalette={getTaskDependencyContextualPalette({
          onDeleteTaskDependency,
        })}
        isMoveChildsWithParent={false}
        onMoveTaskBefore={handleMoveTaskBefore}
        onMoveTaskAfter={handleMoveTaskAfter}
        onMoveTaskInside={handleMoveTaskInside}
        onRelationChange={handleRelationChange}
        authorizedRelations={authorizedRelations}
        onChangeExpandState={(changedTask) => onChangeTaskCollapseState(changedTask.id, !!changedTask.hideChildren)}
        icons={icons}
        onResizeColumn={handleResizeColumn}
        isAdjustToWorkingDates={true}
      />
    </div>
  );
};
