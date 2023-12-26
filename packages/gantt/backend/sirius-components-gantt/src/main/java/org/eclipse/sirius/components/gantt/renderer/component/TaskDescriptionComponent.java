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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.sirius.components.gantt.TaskDetail;
import org.eclipse.sirius.components.gantt.description.TaskDescription;
import org.eclipse.sirius.components.gantt.renderer.elements.TaskElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render tasks.
 *
 * @author lfasani
 */
public class TaskDescriptionComponent implements IComponent {

    private final TaskDescriptionComponentProps props;

    public TaskDescriptionComponent(TaskDescriptionComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        TaskDescription taskDescription = this.props.taskDescription();

        List<Element> children = new ArrayList<>();

        List<Object> semanticElements = taskDescription.semanticElementsProvider().apply(variableManager);
        for (Object semanticElement : semanticElements) {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, semanticElement);

            String targetObjectId = taskDescription.targetObjectIdProvider().apply(childVariableManager);

            if (this.shouldRender(targetObjectId, childVariableManager)) {
                Element taskElement = this.doRender(childVariableManager, targetObjectId);
                children.add(taskElement);
            }
        }
        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element doRender(VariableManager childVariableManager, String targetObjectId) {
        TaskDescription taskDescription = this.props.taskDescription();
        String name = taskDescription.nameProvider().apply(childVariableManager);
        String description = taskDescription.descriptionProvider().apply(childVariableManager);
        Instant startTime = taskDescription.startTimeProvider().apply(childVariableManager);
        Instant endTime = taskDescription.endTimeProvider().apply(childVariableManager);
        Integer progress = taskDescription.progressProvider().apply(childVariableManager);
        Boolean computeDatesDynamicallyProvider = taskDescription.computeDatesDynamicallyProvider().apply(childVariableManager);
        List<Object> dependencyObjects = taskDescription.dependenciesProvider().apply(childVariableManager);
        List<String> dependencyObjectIds = dependencyObjects.stream()
            .map(semanticElement -> {
                VariableManager dependencyVariableManager = childVariableManager.createChild();
                dependencyVariableManager.put(VariableManager.SELF, semanticElement);
                String objectId = taskDescription.targetObjectIdProvider().apply(dependencyVariableManager);
                return UUID.nameUUIDFromBytes(objectId.getBytes()).toString();
            })
            .toList();
        List<Element> childrenElements = this.getChildren(childVariableManager, taskDescription);
        String targetObjectKind = taskDescription.targetObjectKindProvider().apply(childVariableManager);
        String targetObjectLabel = taskDescription.targetObjectLabelProvider().apply(childVariableManager);

        TaskDetail detail = new TaskDetail(name, description, startTime, endTime, progress, computeDatesDynamicallyProvider);
        TaskElementProps taskElementProps = new TaskElementProps(UUID.nameUUIDFromBytes(targetObjectId.getBytes()).toString(), taskDescription.id(), targetObjectId, targetObjectKind, targetObjectLabel, detail, dependencyObjectIds, childrenElements);
        return new Element(TaskElementProps.TYPE, taskElementProps);
    }

    private List<Element> getChildren(VariableManager variableManager, TaskDescription taskDescription) {
        Stream<TaskDescription> childrenTaskDescription = Optional.ofNullable(taskDescription.subTaskDescriptions()).orElse(List.of()).stream();

        Stream<TaskDescription> reusedTaskDescriptions = Optional.ofNullable(taskDescription.reusedTaskDescriptionIds()).orElse(List.of()).stream()
                .map(this.props.id2tasksDescription()::get);

        List<Element> childrenElements = Stream.concat(childrenTaskDescription, reusedTaskDescriptions)
                .map(childTaskDescription -> {
                    TaskDescriptionComponentProps taskComponentProps = new TaskDescriptionComponentProps(variableManager, childTaskDescription, null, null, this.props.id2tasksDescription());
                    return new Element(TaskDescriptionComponent.class, taskComponentProps);
                }).toList();

        return childrenElements;

    }

    private boolean shouldRender(String targetObjectId, VariableManager childVariableManager) {
        return true;
    }

}
