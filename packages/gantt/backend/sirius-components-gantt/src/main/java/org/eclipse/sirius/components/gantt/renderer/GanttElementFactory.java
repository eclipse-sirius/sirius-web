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
package org.eclipse.sirius.components.gantt.renderer;

import java.util.List;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.Task;
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

        return new Gantt(props.id(), props.descriptionId(), props.targetObjectId(), props.label(), tasks);
    }

    private Task instantiateTask(TaskElementProps props, List<Object> children) {
        List<Task> subTasks = children.stream()
                .filter(Task.class::isInstance)
                .map(Task.class::cast)
                .toList();

        return new Task(props.id(), props.descriptionId(), props.targetObjectId(), props.targetObjectKind(), props.targetObjectLabel(), props.detail(), subTasks);
    }

}
