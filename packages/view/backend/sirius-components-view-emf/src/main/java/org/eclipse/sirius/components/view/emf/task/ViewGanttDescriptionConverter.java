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
package org.eclipse.sirius.components.view.emf.task;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.compatibility.emf.DomainClassPredicate;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.gantt.TaskDetail;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.gantt.description.TaskDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based gantt description into an equivalent {@link GanttDescription}.
 *
 * @author lfasani
 */
@Service
public class ViewGanttDescriptionConverter implements IRepresentationDescriptionConverter {

    private static final String DEFAULT_GANTT_LABEL = "Gantt";

    private static final String DEFAULT_GANTT_DESCRIPTION_LABEL = "Gantt Description";

    private final IObjectService objectService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    private final Function<VariableManager, String> semanticTargetLabelProvider;

    private final GanttIdProvider ganttIdProvider;

    public ViewGanttDescriptionConverter(IObjectService objectService, GanttIdProvider ganttIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.ganttIdProvider = Objects.requireNonNull(ganttIdProvider);
        this.semanticTargetIdProvider = variableManager -> this.self(variableManager).map(this.objectService::getId).orElse(null);
        this.semanticTargetKindProvider = variableManager -> this.self(variableManager).map(this.objectService::getKind).orElse(null);
        this.semanticTargetLabelProvider = variableManager -> this.self(variableManager).map(this.objectService::getLabel).orElse(null);

    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.gantt.GanttDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription viewRepresentationDescription, List<RepresentationDescription> allRepresentationDescriptions, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.gantt.GanttDescription viewGanttDescription = (org.eclipse.sirius.components.view.gantt.GanttDescription) viewRepresentationDescription;

        List<TaskDescription> taskDescriptions = viewGanttDescription.getTaskElementDescriptions().stream().map(taskDescription -> this.convert(taskDescription, interpreter)).toList();

        return GanttDescription.newGanttDescription(this.ganttIdProvider.getId(viewGanttDescription))
                .label(Optional.ofNullable(viewGanttDescription.getName()).orElse(DEFAULT_GANTT_DESCRIPTION_LABEL))
                .idProvider(new GetOrCreateRandomIdProvider())
                .canCreatePredicate(variableManager -> this.canCreate(viewGanttDescription.getDomainType(), viewGanttDescription.getPreconditionExpression(), variableManager, interpreter))
                .labelProvider(variableManager -> this.computeGanttLabel(viewGanttDescription, variableManager, interpreter))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .taskDescriptions(taskDescriptions)
                .build();
    }

    private TaskDescription convert(org.eclipse.sirius.components.view.gantt.TaskDescription viewTaskDescription, AQLInterpreter interpreter) {
        List<String> reusedTaskDescriptionIds = viewTaskDescription.getReusedTaskElementDescriptions().stream().map(this.ganttIdProvider::getId).toList();
        TaskDescription taskDescription = TaskDescription.newTaskDescription(this.ganttIdProvider.getId(viewTaskDescription))//
                .semanticElementsProvider(variableManager -> this.getSemanticElements(viewTaskDescription, variableManager, interpreter))//
                .taskDetailProvider(variableManager -> this.getTaskDetail(viewTaskDescription, variableManager, interpreter))//
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectLabelProvider(this.semanticTargetLabelProvider)
                .reusedTaskDescriptionIds(reusedTaskDescriptionIds).build();
        return taskDescription;
    }

    private TaskDetail getTaskDetail(org.eclipse.sirius.components.view.gantt.TaskDescription viewTaskDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        TaskDetail detail = interpreter.evaluateExpression(variableManager.getVariables(), viewTaskDescription.getTaskDetailExpression()).asObject()//
                .filter(TaskDetail.class::isInstance)//
                .map(TaskDetail.class::cast)//
                .orElseGet(() -> new TaskDetail("name", "description", Instant.now().getEpochSecond(), Instant.now().getEpochSecond(), 0));

        return detail;
    }

    private List<Object> getSemanticElements(org.eclipse.sirius.components.view.gantt.TaskDescription viewTaskDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        List<Object> semanticObjects = interpreter.evaluateExpression(variableManager.getVariables(), viewTaskDescription.getSemanticCandidatesExpression())//
                .asObjects().orElseGet(() -> List.of()).stream()//
                .filter(EObject.class::isInstance)//
                .toList();
        return semanticObjects;
    }

    private boolean canCreate(String domainType, String preconditionExpression, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        Optional<EClass> optionalEClass = variableManager.get(VariableManager.SELF, EObject.class).map(EObject::eClass).filter(new DomainClassPredicate(domainType));
        if (optionalEClass.isPresent()) {
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }

    private String computeGanttLabel(org.eclipse.sirius.components.view.gantt.GanttDescription viewGanttDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String title = variableManager.get(GanttDescription.LABEL, String.class)
                .orElseGet(() -> this.evaluateString(interpreter, variableManager, viewGanttDescription.getTitleExpression()));
        if (title == null || title.isBlank()) {
            return DEFAULT_GANTT_LABEL;
        } else {
            return title;
        }
    }

    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }
}
