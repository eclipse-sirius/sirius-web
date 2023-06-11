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
package org.eclipse.sirius.components.collaborative.dynamicdialogs.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.IQueryService;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.ApplyDynamicDialogInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogQueryBasedObjectsInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogQueryBasedObjectsSuccessPayload;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogSuccessPayload;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.DynamicDialogVariable;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.GetDynamicDialogValidationMessagesInput;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.dto.GetDynamicDialogValidationMessagesSuccessPayload;
import org.eclipse.sirius.components.collaborative.dynamicdialogs.services.api.IDynamicDialogService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IDynamicDialogDescription;
import org.eclipse.sirius.components.core.api.IDynamicDialogDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.dynamicdialogs.DynamicDialog;
import org.eclipse.sirius.components.dynamicdialogs.DynamicDialogValidationMessage;
import org.eclipse.sirius.components.dynamicdialogs.description.AbstractDWidgetDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DSelectWidgetDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DWidgetObjectOutputDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DWidgetOutputDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DynamicDialogDescription;
import org.eclipse.sirius.components.dynamicdialogs.renderer.DynamicDialogRenderer;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Service related to the dynamic dialog.
 *
 * @author lfasani
 */
@Service
public class DynamicDialogService implements IDynamicDialogService {

    // private final DynamicDialogDescriptionRegistry registry;
    private final IDynamicDialogDescriptionSearchService dynamicDialogDescriptionSearchService;

    private final IObjectService objectService;

    private final IQueryService queryService;

    public DynamicDialogService(IDynamicDialogDescriptionSearchService dynamicDialogDescriptionSearchService, IObjectService objectService, IQueryService queryService) {
        this.dynamicDialogDescriptionSearchService = Objects.requireNonNull(dynamicDialogDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.queryService = Objects.requireNonNull(queryService);
    }

    @Override
    public IPayload renderDynamicDialog(IEditingContext editingContext, DynamicDialogInput dynamicDialogInput) {
        Optional<IDynamicDialogDescription> dynamicDialogDescriptionOpt = this.dynamicDialogDescriptionSearchService.findById(editingContext, dynamicDialogInput.dialogDescriptionId());

        Optional<Object> object = this.objectService.getObject(editingContext, dynamicDialogInput.objectId());

        IPayload payload = new ErrorPayload(dynamicDialogInput.id(), "renderDynamicDialog failed");
        if (object.isPresent() && dynamicDialogDescriptionOpt.isPresent() && dynamicDialogDescriptionOpt.get() instanceof DynamicDialogDescription dynamicDialogDescription) {
            DynamicDialog dynamicDialog = new DynamicDialogRenderer().render(dynamicDialogDescription, object.get());

            payload = new DynamicDialogSuccessPayload(dynamicDialogInput.id(), dynamicDialog);
        }

        return payload;
    }

    @Override
    public IPayload getQueryBasedObjects(IEditingContext editingContext, DynamicDialogQueryBasedObjectsInput idynamicDialogQueryBasedObjectsInput) {

        Optional<IDynamicDialogDescription> dynamicDialogDescriptionOpt = this.dynamicDialogDescriptionSearchService.findById(editingContext,
                idynamicDialogQueryBasedObjectsInput.dialogDescriptionId());

        Optional<AbstractDWidgetDescription> widgetDescriptionOpt = dynamicDialogDescriptionOpt.filter(DynamicDialogDescription.class::isInstance)//
                .map(DynamicDialogDescription.class::cast)//
                .map(dynDesc -> dynDesc.getWidgetDescriptions()).stream()//
                .flatMap(List::stream)//
                .filter(widgetDesc -> widgetDesc.getId().equals(idynamicDialogQueryBasedObjectsInput.widgetDescriptionId()))//
                .findFirst();

        List<Object> resultList = new ArrayList<>();
        if (widgetDescriptionOpt.isPresent()) {
            VariableManager variableManager = this.convertVariables(editingContext, widgetDescriptionOpt.get(), idynamicDialogQueryBasedObjectsInput.variables());

            if (widgetDescriptionOpt.get() instanceof DSelectWidgetDescription dSelectWidgetDescription) {
                List<?> results = dSelectWidgetDescription.getOptionsProvider().apply(variableManager);
                VariableManager variableManagerOption = new VariableManager(variableManager);
                for (Object object : results) {
                    variableManagerOption.put(VariableManager.SELF, object);
                    String id = dSelectWidgetDescription.getOptionIdProvider().apply(variableManagerOption);
                    String label = dSelectWidgetDescription.getOptionLabelProvider().apply(variableManagerOption);

                    Map<String, String> resultsMap = new LinkedHashMap<>();
                    resultsMap.put("id", id);
                    resultsMap.put("label", label);
                    resultList.add(resultsMap);
                }

                List<Object> convertedResults = this.convertResults(widgetDescriptionOpt.get(), results);
            }
        }

        return new DynamicDialogQueryBasedObjectsSuccessPayload(idynamicDialogQueryBasedObjectsInput.id(), resultList);
    }

    /**
     * According to the type of the variable, the real value is gotten from the given string value.<br/>
     * For example, an object id is converted to an real object.
     *
     * @param variables
     * @return
     */
    private VariableManager convertVariables(IEditingContext editingContext, AbstractDWidgetDescription widgetDescription, Map<Object, Object> variables) {
        VariableManager variableManager = new VariableManager();
        Map<String, DWidgetOutputDescription> outputDescs = new LinkedHashMap<>();
        for (DWidgetOutputDescription inputVariableDesc : widgetDescription.getInputs()) {
            outputDescs.put(inputVariableDesc.name(), inputVariableDesc);
        }

        variables.forEach((name, value) -> {
            DWidgetOutputDescription dWidgetOutputDescription = outputDescs.get(name);
            if (dWidgetOutputDescription instanceof DWidgetObjectOutputDescription || VariableManager.SELF.equals(name)) {
                this.objectService.getObject(editingContext, (String) value).ifPresent(object -> variableManager.put((String) name, object));
            } else {
                variableManager.put((String) name, (String) value);
            }
        });

        return variableManager;
    }

    /**
     * According to the type of the variable, the result value is gotten from the real object.<br/>
     * For example, an object is converted to an object id.
     *
     * @param variables
     * @return
     */
    private List<Object> convertResults(AbstractDWidgetDescription widgetDescription, List<?> resultsToConvert) {
        List<Object> results = new ArrayList<>();
        if (widgetDescription.getOutput() instanceof DWidgetObjectOutputDescription) {
            for (Object object : resultsToConvert) {
                results.add(this.objectService.getId(object));
            }
        } else {
            for (Object object : results) {
                results.add(object);
            }
        }
        return results;
    }

    @Override
    public IPayload applyDialog(IEditingContext editingContext, ApplyDynamicDialogInput applyDynamicDialogInput) {

        Optional<IDynamicDialogDescription> dynamicDialogDescriptionOpt = this.dynamicDialogDescriptionSearchService.findById(editingContext, applyDynamicDialogInput.dialogDescriptionId());

        Optional<Object> object = this.objectService.getObject(editingContext, applyDynamicDialogInput.objectId());

        IPayload payload = new ErrorPayload(applyDynamicDialogInput.id(), "applyDialog failed");
        if (object.isPresent() && dynamicDialogDescriptionOpt.isPresent() && dynamicDialogDescriptionOpt.get() instanceof DynamicDialogDescription dynamicDialogDescription) {

            VariableManager variableManager = this.convertAllVariables(editingContext, dynamicDialogDescription, applyDynamicDialogInput.widgetVariables());
            variableManager.put(VariableManager.SELF, object.get());
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);


            IStatus status = dynamicDialogDescription.getApplyDialogProvider().apply(variableManager);
            if (status instanceof Success) {
                payload = new SuccessPayload(applyDynamicDialogInput.id(), new ArrayList<Message>());
            }
        }

        return payload;
    }

    private VariableManager convertAllVariables(IEditingContext editingContext, DynamicDialogDescription dynamicDialogDescription, List<DynamicDialogVariable> variables) {

        Map<String, DWidgetOutputDescription> widgetOutputVariableNameToType = dynamicDialogDescription.getWidgetDescriptions().stream()
                .collect(Collectors.toMap(dWidgetOutputDescription -> dWidgetOutputDescription.getOutput().name(), dWidgetOutputDescription -> dWidgetOutputDescription.getOutput()));

        VariableManager variableManager = new VariableManager();
        // Map<String, DWidgetOutputDescription> outputDescs = new LinkedHashMap<>();
        // for (DWidgetOutputDescription inputVariableDesc : widgetDescription.getInputs()) {
        // outputDescs.put(inputVariableDesc.name(), inputVariableDesc);
        // }

        variables.forEach((variable) -> {
            DWidgetOutputDescription dWidgetOutputDescription = widgetOutputVariableNameToType.get(variable.name());
            if (dWidgetOutputDescription instanceof DWidgetObjectOutputDescription || VariableManager.SELF.equals(variable.name())) {
                this.objectService.getObject(editingContext, variable.value()).ifPresent(object -> variableManager.put(variable.name(), object));
            } else {
                variableManager.put(variable.name(), variable.value());
            }
        });

        return variableManager;
    }

    @Override
    public IPayload getValidationMessages(IEditingContext editingContext, GetDynamicDialogValidationMessagesInput input) {
        Optional<IDynamicDialogDescription> dynamicDialogDescriptionOpt = this.dynamicDialogDescriptionSearchService.findById(editingContext, input.dialogDescriptionId());

        Optional<Object> object = this.objectService.getObject(editingContext, input.objectId());

        IPayload payload = new ErrorPayload(input.id(), "applyDialog failed");
        if (object.isPresent() && dynamicDialogDescriptionOpt.isPresent() && dynamicDialogDescriptionOpt.get() instanceof DynamicDialogDescription dynamicDialogDescription) {

            VariableManager variableManager = new VariableManager();
            variableManager.put(VariableManager.SELF, object.get());
            for (DynamicDialogVariable dynamicDialogVariable : input.widgetVariables()) {
                variableManager.put(dynamicDialogVariable.name(), dynamicDialogVariable.value());
            }
            List<DynamicDialogValidationMessage> validationMessages = dynamicDialogDescription.getValidationMessageProvider().apply(variableManager);
            payload = new GetDynamicDialogValidationMessagesSuccessPayload(input.id(), validationMessages);
        }

        return payload;
    }

}
