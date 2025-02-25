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
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.ContainerBorderStyleProvider;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverterSwitch;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;

/**
 * Used to convert flexbox container description.
 *
 * @author sbegaudeau
 */
public class FlexboxContainerDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    private final ViewFormDescriptionConverterSwitch viewFormDescriptionConverterSwitch;

    public FlexboxContainerDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IFormIdProvider widgetIdProvider, ViewFormDescriptionConverterSwitch viewFormDescriptionConverterSwitch) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.viewFormDescriptionConverterSwitch = Objects.requireNonNull(viewFormDescriptionConverterSwitch);
    }

    public FlexboxContainerDescription convert(org.eclipse.sirius.components.view.form.FlexboxContainerDescription viewFlexboxContainerDescription) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewFlexboxContainerDescription);

        FlexDirection flexDirection = FlexDirection.valueOf(viewFlexboxContainerDescription.getFlexDirection().getName());

        Function<VariableManager, ContainerBorderStyle> borderStyleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewFlexboxContainerDescription.getConditionalBorderStyles().stream()
                    .filter(style -> this.interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                            .asBoolean()
                            .orElse(Boolean.FALSE))
                    .map(org.eclipse.sirius.components.view.form.ContainerBorderStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewFlexboxContainerDescription::getBorderStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ContainerBorderStyleProvider(effectiveStyle).apply(childVariableManager);
        };
        List<Optional<AbstractControlDescription>> children = new ArrayList<>();
        viewFlexboxContainerDescription.getChildren().forEach(widget -> children.add(this.viewFormDescriptionConverterSwitch.doSwitch(widget)));

        return FlexboxContainerDescription.newFlexboxContainerDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(this.interpreter, viewFlexboxContainerDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(this.interpreter, viewFlexboxContainerDescription.getIsEnabledExpression()))
                .flexDirection(flexDirection)
                .children(children.stream().filter(Optional::isPresent).map(Optional::get).toList())
                .borderStyleProvider(borderStyleProvider)
                .diagnosticsProvider(new DiagnosticProvider(this.interpreter, viewFlexboxContainerDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(this.interpreter, viewFlexboxContainerDescription.getHelpExpression()))
                .build();
    }
}
