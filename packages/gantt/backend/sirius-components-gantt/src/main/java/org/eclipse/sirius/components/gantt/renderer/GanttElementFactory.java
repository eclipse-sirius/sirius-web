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
import java.util.Comparator;
import java.util.List;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.Task;
import org.eclipse.sirius.components.gantt.TaskDetail;
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

            Instant startTime = subTasks.stream()
                    .filter(task -> task.detail().startTime() != null)
                    .min(Comparator.comparing(task -> task.detail().startTime()))
                    .map(task -> task.detail().startTime())
                    .orElse(props.detail().startTime());

            Instant endTime = subTasks.stream()
                    .filter(task -> task.detail().endTime() != null)
                    .max(Comparator.comparing(task -> task.detail().endTime()))
                    .map(task-> task.detail().endTime())
                    .orElse(props.detail().endTime());

            // compute the ratio
            var numerator =  subTasks.stream()
                .filter(task -> task.detail().startTime() != null && task.detail().endTime() != null)
                .mapToLong(task -> task.detail().progress() * (task.detail().endTime().getEpochSecond() - task.detail().startTime().getEpochSecond()))
                .sum();

            var denominator =  subTasks.stream()
                .filter(task -> task.detail().startTime() != null && task.detail().endTime() != null)
                .mapToLong(task -> task.detail().endTime().getEpochSecond() - task.detail().startTime().getEpochSecond())
                .sum();

            int newProgress = 0;
            if (denominator > 0) {
                newProgress = (int) (numerator / denominator);
            }

            detail = new TaskDetail(detail.name(), detail.description(), startTime, endTime, newProgress, detail.computeStartEndDynamically(), detail.collapsed());
        }

        return new Task(props.id(), props.descriptionId(), props.targetObjectId(), props.targetObjectKind(), props.targetObjectLabel(), detail, props.dependencyObjectIds(), subTasks);
    }

}
