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
package org.eclipse.sirius.components.gantt.renderer.component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.GanttColumn;
import org.eclipse.sirius.components.gantt.GanttDateRounding;
import org.eclipse.sirius.components.gantt.GanttDateRoundingTimeUnit;
import org.eclipse.sirius.components.gantt.Task;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.gantt.description.TaskDescription;
import org.eclipse.sirius.components.gantt.renderer.elements.GanttElementProps;
import org.eclipse.sirius.components.gantt.renderer.events.ChangeGanttColumnEvent;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the Gantt representation.
 *
 * @author lfasani
 */
public class GanttComponent implements IComponent {

    private final GanttComponentProps props;

    public GanttComponent(GanttComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        GanttDescription ganttDescription = this.props.ganttDescription();
        Optional<Gantt> optionalPreviousGantt = this.props.previousGantt();

        String ganttId = optionalPreviousGantt.map(Gantt::getId).orElseGet(() -> UUID.randomUUID().toString());
        String targetObjectId = ganttDescription.targetObjectIdProvider().apply(variableManager);
        String label = optionalPreviousGantt.map(Gantt::getLabel).orElseGet(() -> ganttDescription.labelProvider().apply(variableManager));
        List<GanttColumn> columns = computeColumn(optionalPreviousGantt);
        String dateRoundingString = ganttDescription.dateRoundingProvider().apply(variableManager);
        GanttDateRounding dateRounding = getDateRounding(dateRoundingString);

        List<Task> previousTasks = optionalPreviousGantt.map(Gantt::tasks).orElse(List.of());

        Map<String, TaskDescription> id2TaskDescription = new LinkedHashMap<>();
        this.computeId2TaskDescriptions(ganttDescription.taskDescriptions(), id2TaskDescription);

        List<Element> children = ganttDescription.taskDescriptions().stream()
                .map(taskDescription -> {
                    TaskDescriptionComponentProps taskComponentProps = new TaskDescriptionComponentProps(variableManager, taskDescription, previousTasks, ganttId, id2TaskDescription, this.props.ganttEventOptional());
                    return new Element(TaskDescriptionComponent.class, taskComponentProps);
                }).toList();

        GanttElementProps ganttElementProps = new GanttElementProps(ganttId, ganttDescription.getId(), targetObjectId, label, children, columns, dateRounding);
        return new Element(GanttElementProps.TYPE, ganttElementProps);
    }

    private GanttDateRounding getDateRounding(String dateRoundingString) {
        GanttDateRounding ganttDateRounding = new GanttDateRounding(1, GanttDateRoundingTimeUnit.DAY);

        String regex = "^(\\d+)([DHm])$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(dateRoundingString);
        if (matcher.find()) {
            try {
                Integer value = Integer.parseInt(matcher.group(1));
                String timeUnitLetter = matcher.group(2);
                if ("m".equals(timeUnitLetter)) {
                    ganttDateRounding = new GanttDateRounding(value, GanttDateRoundingTimeUnit.MINUTE);
                } else if ("H".equals(timeUnitLetter)) {
                    ganttDateRounding = new GanttDateRounding(value, GanttDateRoundingTimeUnit.HOUR);
                } else if ("D".equals(timeUnitLetter)) {
                    ganttDateRounding = new GanttDateRounding(value, GanttDateRoundingTimeUnit.DAY);
                }
            } catch (IllegalStateException | NumberFormatException | IndexOutOfBoundsException e) {
                // do nothing
            }
        }
        return ganttDateRounding;
    }


    private List<GanttColumn> computeColumn(Optional<Gantt> optionalPreviousGantt) {
        return optionalPreviousGantt.map(previousGantt -> {
            List<GanttColumn> columns = previousGantt.columns();
            return this.props.ganttEventOptional()
                    .filter(ChangeGanttColumnEvent.class::isInstance)
                    .map(ChangeGanttColumnEvent.class::cast)
                    .map(event -> {
                        List<GanttColumn> newGanttColumns = new ArrayList<>(columns);
                        newGanttColumns.removeIf(col-> col.id().equals(event.columnId()));
                        newGanttColumns.add(new GanttColumn(event.columnId(), event.displayed(), event.width()));
                        return newGanttColumns;
                    })
                    .orElse(columns);
        })
        .orElse(getDefaultColumns());
    }

    private List<GanttColumn> getDefaultColumns() {
        List<GanttColumn> columns = new ArrayList<>();
        columns.add(new GanttColumn("NAME", true, 210));
        columns.add(new GanttColumn("START_DATE", true, 130));
        columns.add(new GanttColumn("END_DATE", true, 130));
        columns.add(new GanttColumn("PROGRESS", true, 40));
        return columns;
    }

    private void computeId2TaskDescriptions(List<TaskDescription> taskDescriptions, Map<String, TaskDescription> id2TaskDescription) {
        taskDescriptions.forEach(taskDescription -> {
            id2TaskDescription.put(taskDescription.id(), taskDescription);

            this.computeId2TaskDescriptions(taskDescription.subTaskDescriptions(), id2TaskDescription);
        });
    }
}
