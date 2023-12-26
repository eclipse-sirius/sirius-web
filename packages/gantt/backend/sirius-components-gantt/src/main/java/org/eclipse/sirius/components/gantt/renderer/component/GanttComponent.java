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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.Task;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.gantt.description.TaskDescription;
import org.eclipse.sirius.components.gantt.renderer.elements.GanttElementProps;
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

        List<Task> previousTasks = optionalPreviousGantt.map(Gantt::tasks).orElse(List.of());

        Map<String, TaskDescription> id2TaskDescription = new LinkedHashMap<>();
        this.computeId2TaskDescriptions(ganttDescription.taskDescriptions(), id2TaskDescription);

        List<Element> children = ganttDescription.taskDescriptions().stream()
                .map(taskDescription -> {
                    TaskDescriptionComponentProps taskComponentProps = new TaskDescriptionComponentProps(variableManager, taskDescription, previousTasks, ganttId, id2TaskDescription);
                    return new Element(TaskDescriptionComponent.class, taskComponentProps);
                }).toList();

        GanttElementProps ganttElementProps = new GanttElementProps(ganttId, ganttDescription.getId(), targetObjectId, label, children);
        return new Element(GanttElementProps.TYPE, ganttElementProps);
    }

    private void computeId2TaskDescriptions(List<TaskDescription> taskDescriptions, Map<String, TaskDescription> id2TaskDescription) {
        taskDescriptions.forEach(taskDescription -> {
            id2TaskDescription.put(taskDescription.id(), taskDescription);

            this.computeId2TaskDescriptions(taskDescription.subTaskDescriptions(), id2TaskDescription);
        });
    }
}
