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
package org.eclipse.sirius.components.view.emf.task;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.DomainClassPredicate;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.gantt.description.TaskDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
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

    private final IEditService editService;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final Function<VariableManager, String> semanticTargetKindProvider;

    private final Function<VariableManager, String> semanticTargetLabelProvider;

    private final GanttIdProvider ganttIdProvider;


    public ViewGanttDescriptionConverter(IObjectService objectService, IEditService editService, GanttIdProvider ganttIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
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

        Map<org.eclipse.sirius.components.view.gantt.TaskDescription, String> taskDescription2Ids = new LinkedHashMap<>();
        this.computeTaskDescription2Ids(viewGanttDescription.getTaskElementDescriptions(), taskDescription2Ids);


        List<TaskDescription> taskDescriptions = viewGanttDescription.getTaskElementDescriptions().stream().map(taskDescription -> this.convert(taskDescription, interpreter, taskDescription2Ids)).toList();

        return GanttDescription.newGanttDescription(this.ganttIdProvider.getId(viewGanttDescription))
                .label(Optional.ofNullable(viewGanttDescription.getName()).orElse(DEFAULT_GANTT_DESCRIPTION_LABEL))
                .idProvider(new GetOrCreateRandomIdProvider())
                .canCreatePredicate(variableManager -> this.canCreate(viewGanttDescription.getDomainType(), viewGanttDescription.getPreconditionExpression(), variableManager, interpreter))
                .labelProvider(variableManager -> this.computeGanttLabel(viewGanttDescription, variableManager, interpreter))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .createTaskProvider(Optional.ofNullable(viewGanttDescription.getCreateTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> { }))
                .editTaskProvider(Optional.ofNullable(viewGanttDescription.getEditTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> { }))
                .deleteTaskProvider(Optional.ofNullable(viewGanttDescription.getDeleteTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> { }))
                .dropTaskProvider(Optional.ofNullable(viewGanttDescription.getDropTool()).map(tool -> this.getOperationsHandler(tool.getBody(), interpreter)).orElse(variable -> { }))
                .taskDescriptions(taskDescriptions)
                .build();
    }

    private void computeTaskDescription2Ids(List<org.eclipse.sirius.components.view.gantt.TaskDescription> taskDescriptions,
            Map<org.eclipse.sirius.components.view.gantt.TaskDescription, String> taskDescription2Ids) {
        if (taskDescriptions != null) {
            taskDescriptions.forEach(taskDescription -> {
                taskDescription2Ids.put(taskDescription, this.ganttIdProvider.getId(taskDescription));

                this.computeTaskDescription2Ids(taskDescription.getSubTaskElementDescriptions(), taskDescription2Ids);
            });
        }
    }

    private Consumer<VariableManager> getOperationsHandler(List<Operation> operations, AQLInterpreter interpreter) {
        return variableManager -> {
            OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
            operationInterpreter.executeOperations(operations, variableManager);
        };
    }

    private TaskDescription convert(org.eclipse.sirius.components.view.gantt.TaskDescription viewTaskDescription, AQLInterpreter interpreter, Map<org.eclipse.sirius.components.view.gantt.TaskDescription, String> taskDescription2Ids) {
        List<String> reusedTaskDescriptionIds = viewTaskDescription.getReusedTaskElementDescriptions().stream()
                .map(taskDescription -> taskDescription2Ids.get(taskDescription))
                .toList();

        List<TaskDescription> subTaskDescriptions = Optional.ofNullable(viewTaskDescription.getSubTaskElementDescriptions())
            .stream()
            .flatMap(viewTaskDescs-> viewTaskDescs.stream())
            .map(viewTaskDesc -> this.convert(viewTaskDesc, interpreter, taskDescription2Ids))
            .toList();

        TaskDescription taskDescription = TaskDescription.newTaskDescription(taskDescription2Ids.get(viewTaskDescription))
                .semanticElementsProvider(variableManager -> this.getSemanticElements(variableManager, interpreter, viewTaskDescription.getSemanticCandidatesExpression()))
                .nameProvider(variableManager -> this.evaluateExpression(variableManager, interpreter, viewTaskDescription.getNameExpression(), String.class, ""))
                .descriptionProvider(variableManager -> this.evaluateExpression(variableManager, interpreter, viewTaskDescription.getDescriptionExpression(), String.class, ""))
                .startTimeProvider(variableManager -> this.evaluateExpression(variableManager, interpreter, viewTaskDescription.getStartTimeExpression(), Instant.class, null))
                .endTimeProvider(variableManager -> this.evaluateExpression(variableManager, interpreter, viewTaskDescription.getEndTimeExpression(), Instant.class, null))
                .progressProvider(variableManager -> this.evaluateExpression(variableManager, interpreter, viewTaskDescription.getProgressExpression(), Integer.class, 0))
                .computeStartEndDynamicallyProvider(variableManager -> this.evaluateExpression(variableManager, interpreter, viewTaskDescription.getComputeStartEndDynamicallyExpression(), Boolean.class, false))
                .dependenciesProvider(variableManager -> this.getSemanticElements(variableManager, interpreter, viewTaskDescription.getDependenciesExpression()))
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .targetObjectKindProvider(this.semanticTargetKindProvider)
                .targetObjectLabelProvider(this.semanticTargetLabelProvider)
                .reusedTaskDescriptionIds(reusedTaskDescriptionIds)
                .subTaskDescriptions(subTaskDescriptions)
                .build();
        return taskDescription;
    }

    private <T> T evaluateExpression(VariableManager variableManager, AQLInterpreter interpreter, String expression, Class<T> type, T defaultValue) {
        T value = interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asObject()
                .filter(type::isInstance)
                .map(type::cast)
                .orElse(defaultValue);

        return value;
    }

    private List<Object> getSemanticElements(VariableManager variableManager, AQLInterpreter interpreter, String expression) {
        List<Object> semanticObjects = interpreter.evaluateExpression(variableManager.getVariables(), expression)
                .asObjects()
                .orElseGet(() -> List.of()).stream()
                .filter(EObject.class::isInstance)
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
