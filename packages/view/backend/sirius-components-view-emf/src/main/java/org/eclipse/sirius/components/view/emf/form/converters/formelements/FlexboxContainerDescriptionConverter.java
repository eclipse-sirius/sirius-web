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
package org.eclipse.sirius.components.view.emf.form.converters.formelements;

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

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
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.formelements.api.IFlexboxContainerDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.formelements.api.IFormElementForDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.formelements.api.IFormElementIfDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * Used to convert flexbox container description.
 *
 * @author sbegaudeau
 */
public class FlexboxContainerDescriptionConverter implements IFlexboxContainerDescriptionConverter {

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    private final List<IWidgetDescriptionConverter> widgetDescriptionConverters;

    private final IFormElementForDescriptionConverter formElementForDescriptionConverter;

    private final IFormElementIfDescriptionConverter formElementIfDescriptionConverter;

    public FlexboxContainerDescriptionConverter(IObjectService objectService, IFormIdProvider widgetIdProvider, List<IWidgetDescriptionConverter> widgetDescriptionConverters, IFormElementForDescriptionConverter formElementForDescriptionConverter, IFormElementIfDescriptionConverter formElementIfDescriptionConverter) {
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.widgetDescriptionConverters = Objects.requireNonNull(widgetDescriptionConverters);
        this.formElementForDescriptionConverter = Objects.requireNonNull(formElementForDescriptionConverter);
        this.formElementIfDescriptionConverter = Objects.requireNonNull(formElementIfDescriptionConverter);
    }

    @Override
    public Optional<FlexboxContainerDescription> convert(org.eclipse.sirius.components.view.form.FlexboxContainerDescription viewFlexboxContainerDescription, AQLInterpreter interpreter) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewFlexboxContainerDescription);

        FlexDirection flexDirection = FlexDirection.valueOf(viewFlexboxContainerDescription.getFlexDirection().getName());

        Function<VariableManager, ContainerBorderStyle> borderStyleProvider = variableManager -> {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            var effectiveStyle = viewFlexboxContainerDescription.getConditionalBorderStyles().stream()
                    .filter(style -> interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
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

        var controlDescriptions = viewFlexboxContainerDescription.getChildren().stream()
                .map(formElementDescription -> this.convertChildren(formElementDescription, interpreter))
                .flatMap(Optional::stream)
                .toList();

        var flexboxContainerDescription = FlexboxContainerDescription.newFlexboxContainerDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .labelProvider(new StringValueProvider(interpreter, viewFlexboxContainerDescription.getLabelExpression()))
                .isReadOnlyProvider(new ReadOnlyValueProvider(interpreter, viewFlexboxContainerDescription.getIsEnabledExpression()))
                .flexDirection(flexDirection)
                .children(controlDescriptions)
                .borderStyleProvider(borderStyleProvider)
                .diagnosticsProvider(new DiagnosticProvider(interpreter, viewFlexboxContainerDescription.getDiagnosticsExpression()))
                .kindProvider(new DiagnosticKindProvider())
                .messageProvider(new DiagnosticMessageProvider())
                .helpTextProvider(new StringValueProvider(interpreter, viewFlexboxContainerDescription.getHelpExpression()))
                .build();

        return Optional.of(flexboxContainerDescription);
    }

    private Optional<AbstractControlDescription> convertChildren(FormElementDescription formElementDescription, AQLInterpreter interpreter) {
        Optional<AbstractControlDescription> optionalControlDescription = Optional.empty();

        if (formElementDescription instanceof org.eclipse.sirius.components.view.form.FlexboxContainerDescription flexboxContainerDescription) {
            optionalControlDescription = this.convert(flexboxContainerDescription, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof FormElementIf formElementIf) {
            optionalControlDescription = this.formElementIfDescriptionConverter.convert(formElementIf, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof FormElementFor formElementFor) {
            optionalControlDescription = this.formElementForDescriptionConverter.convert(formElementFor, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof WidgetDescription widgetDescription) {
            optionalControlDescription = this.widgetDescriptionConverters.stream()
                    .filter(converter -> converter.canConvert(widgetDescription))
                    .findFirst()
                    .flatMap(converter -> converter.convert(widgetDescription, interpreter));
        }

        return optionalControlDescription;
    }
}
