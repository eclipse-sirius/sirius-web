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
package org.eclipse.sirius.components.gantt.renderer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Comparator;
import java.util.List;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.Task;
import org.eclipse.sirius.components.gantt.TaskDetail;
import org.eclipse.sirius.components.gantt.TemporalType;
import org.eclipse.sirius.components.gantt.renderer.elements.GanttElementProps;
import org.eclipse.sirius.components.gantt.renderer.elements.TaskElementProps;
import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to instantiate the elements of the Gantt representation.
 *
 * @author lfasani
 */
public class GanttElementFactory implements IElementFactory {

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (GanttElementProps.TYPE.equals(type) && props instanceof GanttElementProps ganttElementProps) {
            object = this.instantiateGantt(ganttElementProps, children);
        } else if (TaskElementProps.TYPE.equals(type) && props instanceof TaskElementProps ganttTaskElementProps) {
            object = this.instantiateTask(ganttTaskElementProps, children);
        }
        return object;
    }

    private Gantt instantiateGantt(GanttElementProps props, List<Object> children) {
        List<Task> tasks = children.stream()
                .filter(Task.class::isInstance)
                .map(Task.class::cast)
                .toList();

        return new Gantt(props.id(), props.descriptionId(), props.targetObjectId(), props.label(), tasks, props.columns(), props.dateRounding());
    }

    private Task instantiateTask(TaskElementProps props, List<Object> children) {
        List<Task> subTasks = children.stream()
                .filter(Task.class::isInstance)
                .map(Task.class::cast)
                .toList();

        TaskDetail detail = props.detail();
        if (detail.computeStartEndDynamically() && !subTasks.isEmpty()) {

            String startTime = subTasks.stream()
                    .map(task -> task.detail().startTime())
                    .min(Comparator.comparing(timeString -> timeString))
                    .orElse(props.detail().startTime());

            String endTime =  subTasks.stream()
                    .map(task -> task.detail().endTime())
                    .max(Comparator.comparing(timeString -> timeString))
                    .orElse(props.detail().endTime());

            // compute the ratio
            var numerator = subTasks.stream()
                    .mapToLong(task -> {
                        long value = 0;
                        Temporal start = getTemporal(task.detail().startTime(), task.detail().temporalType());
                        Temporal end = getTemporal(task.detail().endTime(), task.detail().temporalType());
                        if (start != null && end != null) {
                            value = task.detail().progress() * start.until(end, ChronoUnit.SECONDS);
                        }
                        return value;
                    })
                    .sum();

            var denominator = subTasks.stream()
                    .mapToLong(task -> {
                        long value = 0;
                        Temporal start = getTemporal(task.detail().startTime(), task.detail().temporalType());
                        Temporal end = getTemporal(task.detail().endTime(), task.detail().temporalType());
                        if (start != null && end != null) {
                            value = start.until(end, ChronoUnit.SECONDS);
                        }
                        return value;
                    })
                    .sum();

            int newProgress = 0;
            if (denominator > 0) {
                newProgress = (int) (numerator / denominator);
            }

            detail = new TaskDetail(detail.name(), detail.description(), startTime, endTime, getTemporalType(subTasks), newProgress, detail.computeStartEndDynamically(), detail.collapsed());
        }

        return new Task(props.id(), props.descriptionId(), props.targetObjectId(), props.targetObjectKind(), props.targetObjectLabel(), detail, props.dependencyObjectIds(), subTasks);
    }

    private Temporal getTemporal(String temporalString, TemporalType temporalType) {
        Temporal temporal = null;
        if (temporalString != null && !temporalString.isBlank()) {
            if (TemporalType.DATE.equals(temporalType)) {
                temporal = LocalDate.parse(temporalString);
            } else if (TemporalType.DATE_TIME.equals(temporalType)) {
                temporal = Instant.parse(temporalString);
            }
        }

        return temporal;
    }

    private TemporalType getTemporalType(List<Task> subTasks) {
        return subTasks.stream()
                .map(Task::detail)
                .map(TaskDetail::temporalType)
                .findFirst()
                .orElse(null);
    }
}
