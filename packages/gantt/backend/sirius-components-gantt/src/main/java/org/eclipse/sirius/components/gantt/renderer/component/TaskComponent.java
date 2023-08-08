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
package org.eclipse.sirius.components.gantt.renderer.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
public class TaskComponent implements IComponent {

    private final TaskComponentProps props;

    public TaskComponent(TaskComponentProps props) {
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
                Element nodeElement = this.doRender(childVariableManager, targetObjectId);
                children.add(nodeElement);
            }
        }
        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private Element doRender(VariableManager childVariableManager, String targetObjectId) {
        TaskDescription taskDescription = this.props.taskDescription();
        TaskDetail detail = taskDescription.taskDetailProvider().apply(childVariableManager);
        List<Element> childrenElements = this.getChildren(childVariableManager, taskDescription);
        String targetObjectKind = taskDescription.targetObjectKindProvider().apply(childVariableManager);
        String targetObjectLabel = taskDescription.targetObjectLabelProvider().apply(childVariableManager);

        TaskElementProps taskElementProps = new TaskElementProps(UUID.randomUUID().toString(), taskDescription.id(), targetObjectId, targetObjectKind, targetObjectLabel, detail, childrenElements);
        return new Element(TaskElementProps.TYPE, taskElementProps);
    }

    private List<Element> getChildren(VariableManager variableManager, TaskDescription taskDescription) {
        List<Element> reusedTaskDescription = taskDescription.reusedTaskDescriptionIds().stream()
                .map(this.props.id2tasksDescription()::get)
                .map(childTaskDescription -> {
                    TaskComponentProps taskComponentProps = new TaskComponentProps(variableManager, taskDescription, null, null, this.props.id2tasksDescription());
                    return new Element(TaskComponent.class, taskComponentProps);
                }).toList();

        return reusedTaskDescription;

    }

    private boolean shouldRender(String targetObjectId, VariableManager childVariableManager) {
        return true;
    }

}
