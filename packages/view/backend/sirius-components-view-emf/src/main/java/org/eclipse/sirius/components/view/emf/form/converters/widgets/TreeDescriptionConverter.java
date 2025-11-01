/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.BooleanValueProvider;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.MultiValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.NewValueHandler;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert tree descriptions.
 *
 * @author sbegaudeau
 */
@Service
public class TreeDescriptionConverter implements IWidgetDescriptionConverter {

    private final IIdentityService identityService;

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public TreeDescriptionConverter(IIdentityService identityService, IReadOnlyObjectPredicate readOnlyObjectPredicate, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.TreeDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.TreeDescription viewTreeDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewTreeDescription);

            Function<VariableManager, String> nodeIdProvider = variableManager -> {
                Object treeItem = variableManager.getVariables().get(VariableManager.SELF);
                return this.identityService.getId(treeItem);
            };
            Function<VariableManager, String> itemKindProvider = variableManager -> {
                Object candidate = variableManager.getVariables().get(VariableManager.SELF);
                return this.identityService.getKind(candidate);
            };
            Function<VariableManager, List<String>> nodeIconURLProvider = this.getTreeBeginIconValue(interpreter, viewTreeDescription.getTreeItemBeginIconExpression());
            Function<VariableManager, List<List<String>>> nodeIconEndURLProvider = this.getTreeEndIconValue(interpreter, viewTreeDescription.getTreeItemEndIconsExpression());
            Function<VariableManager, List<? extends Object>> childrenProvider = new MultiValueProvider(interpreter, viewTreeDescription.getChildrenExpression(), Object.class);
            Function<VariableManager, Boolean> nodeSelectableProvider = new BooleanValueProvider(interpreter, viewTreeDescription.getIsTreeItemSelectableExpression());
            Function<VariableManager, Boolean> nodeCheckbableProvider = new BooleanValueProvider(interpreter, viewTreeDescription.getIsCheckableExpression());

            String valueExpression = Optional.ofNullable(viewTreeDescription.getCheckedValueExpression()).orElse("");
            BooleanValueProvider valueProvider = new BooleanValueProvider(interpreter, valueExpression);
            BiFunction<VariableManager, Boolean, IStatus> newValueHandler = new NewValueHandler<>(interpreter, this.operationExecutor, this.feedbackMessageService, viewTreeDescription.getBody());

            var treeDescription = TreeDescription.newTreeDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                    .labelProvider(new StringValueProvider(interpreter, viewTreeDescription.getLabelExpression()))
                    .isReadOnlyProvider(new ReadOnlyValueProvider(this.readOnlyObjectPredicate, interpreter, viewTreeDescription.getIsEnabledExpression()))
                    .iconURLProvider(variableManager -> List.of())
                    .childrenProvider(childrenProvider)
                    .nodeIdProvider(nodeIdProvider)
                    .nodeLabelProvider(new StringValueProvider(interpreter, viewTreeDescription.getTreeItemLabelExpression()))
                    .nodeKindProvider(itemKindProvider)
                    .nodeIconURLProvider(nodeIconURLProvider)
                    .nodeEndIconsURLProvider(nodeIconEndURLProvider)
                    .nodeSelectableProvider(nodeSelectableProvider)
                    .isCheckableProvider(nodeCheckbableProvider)
                    .checkedValueProvider(valueProvider)
                    .newCheckedValueHandler(newValueHandler)
                    .expandedNodeIdsProvider(variableManager -> List.of())
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewTreeDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewTreeDescription.getHelpExpression()))
                    .build();

            return Optional.of(treeDescription);
        }
        return Optional.empty();
    }

    private Function<VariableManager, List<String>> getTreeBeginIconValue(AQLInterpreter interpreter, String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            List<String> values = new ArrayList<>();
            if (!safeValueExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Optional<List<Object>> optionalResult = interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression).asObjects();
                if (optionalResult.isPresent()) {
                    values = optionalResult.get().stream().filter(String.class::isInstance).map(String.class::cast).toList();
                }
            }
            return values;
        };
    }

    private Function<VariableManager, List<List<String>>> getTreeEndIconValue(AQLInterpreter interpreter, String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            List<List<String>> values = new ArrayList<>(new ArrayList<>());
            if (!safeValueExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Optional<List<Object>> optionalResult = interpreter.evaluateExpression(childVariableManager.getVariables(), safeValueExpression).asObjects();
                if (optionalResult.isPresent()) {
                    var list = optionalResult.get().stream().filter(List.class::isInstance).map(List.class::cast).toList();
                    return list.stream().map(valuesList -> (List<String>) valuesList.stream().filter(String.class::isInstance).map(String.class::cast).toList())
                            .toList();
                }
            }
            return values;
        };
    }
}
