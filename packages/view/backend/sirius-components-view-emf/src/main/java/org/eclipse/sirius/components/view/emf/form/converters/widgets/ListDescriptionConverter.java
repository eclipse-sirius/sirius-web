/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form.converters.widgets;

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.components.ListComponent;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.BooleanValueProvider;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ListStyleProvider;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert list descriptions.
 *
 * @author sbegaudeau
 */
@Service
public class ListDescriptionConverter implements IWidgetDescriptionConverter {

    private final IObjectService objectService;

    private final ILabelService labelService;

    private final IEditService editService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public ListDescriptionConverter(IObjectService objectService, ILabelService labelService, IEditService editService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.labelService = Objects.requireNonNull(labelService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.ListDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.ListDescription viewListDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewListDescription);

            Function<VariableManager, String> itemIdProvider = variableManager -> {
                Object candidate = variableManager.getVariables().get(ListComponent.CANDIDATE_VARIABLE);
                return this.objectService.getId(candidate);
            };
            Function<VariableManager, String> itemKindProvider = variableManager -> {
                Object candidate = variableManager.getVariables().get(ListComponent.CANDIDATE_VARIABLE);
                return this.objectService.getKind(candidate);
            };

            Function<VariableManager, IStatus> itemDeleteHandlerProvider = variableManager -> {
                variableManager.get(ListComponent.CANDIDATE_VARIABLE, Object.class).ifPresent(this.editService::delete);
                return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
            };

            Function<VariableManager, IStatus> itemClickHandlerProvider = variableManager -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);

                var result = this.operationExecutor.execute(interpreter, childVariableManager, viewListDescription.getBody());
                if (result.status() == OperationExecutionStatus.FAILURE) {
                    List<Message> errorMessages = new ArrayList<>();
                    errorMessages.add(new Message("Something went wrong while handling the item click.", MessageLevel.ERROR));
                    errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
                    return new Failure(errorMessages);
                }
                return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
            };

            Function<VariableManager, List<String>> itemIconURLProvider = variableManager -> variableManager.get(ListComponent.CANDIDATE_VARIABLE, Object.class)
                    .map(this.labelService::getImagePaths)
                    .orElse(List.of());

            Function<VariableManager, ListStyle> styleProvider = variableManager -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                var effectiveStyle = viewListDescription.getConditionalStyles().stream()
                        .filter(style -> interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                                .asBoolean()
                                .orElse(Boolean.FALSE))
                        .map(ListDescriptionStyle.class::cast)
                        .findFirst()
                        .orElseGet(viewListDescription::getStyle);
                if (effectiveStyle == null) {
                    return null;
                }
                return new ListStyleProvider(effectiveStyle).apply(childVariableManager);
            };

            var listDescription = ListDescription.newListDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                    .labelProvider(new StringValueProvider(interpreter, viewListDescription.getLabelExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(interpreter, viewListDescription.getIsEnabledExpression()))
                    .itemsProvider(new MultiValueProvider(interpreter, viewListDescription.getValueExpression(), Object.class))
                    .itemKindProvider(itemKindProvider)
                    .itemDeleteHandlerProvider(itemDeleteHandlerProvider)
                    .itemIconURLProvider(itemIconURLProvider)
                    .itemIdProvider(itemIdProvider)
                    .itemLabelProvider(new StringValueProvider(interpreter, viewListDescription.getDisplayExpression()))
                    .itemDeletableProvider(new BooleanValueProvider(interpreter, viewListDescription.getIsDeletableExpression()))
                    .itemClickHandlerProvider(itemClickHandlerProvider)
                    .styleProvider(styleProvider)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewListDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewListDescription.getHelpExpression()))
                    .build();

            return Optional.of(listDescription);
        }
        return Optional.empty();
    }
}
