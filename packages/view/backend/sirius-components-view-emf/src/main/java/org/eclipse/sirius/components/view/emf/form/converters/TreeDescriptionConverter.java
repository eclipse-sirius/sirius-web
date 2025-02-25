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
package org.eclipse.sirius.components.view.emf.form.converters;

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverterSwitch.VARIABLE_MANAGER;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.TreeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.BooleanValueProvider;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;

/**
 * Used to convert tree descriptions.
 *
 * @author sbegaudeau
 */
public class TreeDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public TreeDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IFeedbackMessageService feedbackMessageService, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    public TreeDescription convert(org.eclipse.sirius.components.view.form.TreeDescription viewTreeDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewTreeDescription);

        Function<VariableManager, String> nodeIdProvider = variableManager -> {
            Object treeItem = variableManager.getVariables().get(VariableManager.SELF);
            return this.objectService.getId(treeItem);
        };
        Function<VariableManager, String> itemKindProvider = variableManager -> {
            Object candidate = variableManager.getVariables().get(VariableManager.SELF);
            return this.objectService.getKind(candidate);
        };
        Function<VariableManager, List<String>> nodeIconURLProvider = this.getTreeBeginIconValue(viewTreeDescription.getTreeItemBeginIconExpression());
        Function<VariableManager, List<List<String>>> nodeIconEndURLProvider = this.getTreeEndIconValue(viewTreeDescription.getTreeItemEndIconsExpression());
        Function<VariableManager, List<? extends Object>> childrenProvider = new MultiValueProvider(this.interpreter, viewTreeDescription.getChildrenExpression(), Object.class);
        Function<VariableManager, Boolean> nodeSelectableProvider = new BooleanValueProvider(this.interpreter, viewTreeDescription.getIsTreeItemSelectableExpression());
        Function<VariableManager, Boolean> nodeCheckbableProvider = new BooleanValueProvider(this.interpreter, viewTreeDescription.getIsCheckableExpression());

        String valueExpression = Optional.ofNullable(viewTreeDescription.getCheckedValueExpression()).orElse("");
        BooleanValueProvider valueProvider = new BooleanValueProvider(this.interpreter, valueExpression);
        BiFunction<VariableManager, Boolean, IStatus> newValueHandler = new NewValueHandler(this.interpreter, this.editService, this.feedbackMessageService, viewTreeDescription.getBody());

        return TreeDescription.newTreeDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewTreeDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewTreeDescription.getIsEnabledExpression()))
                .iconURLProvider(variableManager -> List.of())
                .childrenProvider(childrenProvider)
                .nodeIdProvider(nodeIdProvider)
                .nodeLabelProvider(new StringValueProvider(this.interpreter, viewTreeDescription.getTreeItemLabelExpression()))
                .nodeKindProvider(itemKindProvider)
                .nodeIconURLProvider(nodeIconURLProvider)
                .nodeEndIconsURLProvider(nodeIconEndURLProvider)
                .nodeSelectableProvider(nodeSelectableProvider)
                .isCheckableProvider(nodeCheckbableProvider)
                .checkedValueProvider(valueProvider)
                .newCheckedValueHandler(newValueHandler)
                .expandedNodeIdsProvider(variableManager -> List.of())
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewTreeDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewTreeDescription.getHelpExpression()))
                .build();
    }

    private Function<VariableManager, List<String>> getTreeBeginIconValue(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            List<String> values = new ArrayList<>();
            if (!safeValueExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Optional<List<Object>> optionalResult = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression).asObjects();
                if (optionalResult.isPresent()) {
                    values = optionalResult.get().stream().filter(String.class::isInstance).map(String.class::cast).toList();
                }
            }
            return values;
        };
    }

    private Function<VariableManager, List<List<String>>> getTreeEndIconValue(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            List<List<String>> values = new ArrayList<>(new ArrayList<>());
            if (!safeValueExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Optional<List<Object>> optionalResult = this.interpreter.evaluateExpression(childVariableManager.getVariables(), safeValueExpression).asObjects();
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
