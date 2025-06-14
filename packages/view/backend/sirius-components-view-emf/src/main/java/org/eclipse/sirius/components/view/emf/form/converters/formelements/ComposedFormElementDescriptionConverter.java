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
package org.eclipse.sirius.components.view.emf.form.converters.formelements;

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.FlexboxContainerDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.BooleanValueProvider;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.ContainerBorderStyleProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.ReadOnlyValueProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.formelements.api.IComposedFormElementDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert composed form element descriptions.
 *
 * @author sbegaudeau
 */
@Service
public class ComposedFormElementDescriptionConverter implements IComposedFormElementDescriptionConverter {

    private final IIdentityService identityService;

    private final IFormIdProvider widgetIdProvider;

    private final List<IWidgetDescriptionConverter> widgetDescriptionConverters;

    public ComposedFormElementDescriptionConverter(IIdentityService identityService, IFormIdProvider widgetIdProvider, List<IWidgetDescriptionConverter> widgetDescriptionConverters) {
        this.identityService = Objects.requireNonNull(identityService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.widgetDescriptionConverters = Objects.requireNonNull(widgetDescriptionConverters);
    }

    @Override
    public Optional<AbstractControlDescription> convert(FormElementDescription formElementDescription, AQLInterpreter interpreter) {
        Optional<AbstractControlDescription> optionalControlDescription = Optional.empty();

        if (formElementDescription instanceof FormElementFor formElementFor) {
            optionalControlDescription = this.convert(formElementFor, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof FormElementIf formElementIf) {
            optionalControlDescription = this.convert(formElementIf, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof org.eclipse.sirius.components.view.form.FlexboxContainerDescription flexboxContainerDescription) {
            optionalControlDescription = this.convert(flexboxContainerDescription, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof WidgetDescription widgetDescription) {
            optionalControlDescription = this.widgetDescriptionConverters.stream()
                    .filter(converter -> converter.canConvert(widgetDescription))
                    .findFirst()
                    .flatMap(converter -> converter.convert(widgetDescription, interpreter));
        }

        return optionalControlDescription;
    }

    private Optional<ForDescription> convert(FormElementFor formElementFor, AQLInterpreter interpreter) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(formElementFor);

        Function<VariableManager, List<?>> iterableProvider = variableManager -> {
            String safeIterabeExpression = Optional.ofNullable(formElementFor.getIterableExpression()).orElse("");
            if (!safeIterabeExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Result result = interpreter.evaluateExpression(childVariableManager.getVariables(), safeIterabeExpression);
                return result.asObjects().orElse(List.of());
            } else {
                return List.of();
            }
        };

        List<AbstractControlDescription> controlDescriptions = formElementFor.getChildren().stream()
                .map(childFormElementDescription -> this.convert(childFormElementDescription, interpreter))
                .flatMap(Optional::stream)
                .toList();

        var forDescription = ForDescription.newForDescription(descriptionId)
                .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                .iterator(formElementFor.getIterator())
                .iterableProvider(iterableProvider)
                .controlDescriptions(controlDescriptions)
                .build();

        return Optional.of(forDescription);
    }

    private Optional<IfDescription> convert(FormElementIf formElementIf, AQLInterpreter interpreter) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(formElementIf);

        var controlDescriptions = formElementIf.getChildren().stream()
                .map(formElementDescription -> this.convert(formElementDescription, interpreter))
                .flatMap(Optional::stream)
                .toList();

        var ifDescription = IfDescription.newIfDescription(descriptionId)
                .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
                .predicate(new BooleanValueProvider(interpreter, formElementIf.getPredicateExpression()))
                .controlDescriptions(controlDescriptions)
                .build();

        return Optional.of(ifDescription);
    }

    private Optional<FlexboxContainerDescription> convert(org.eclipse.sirius.components.view.form.FlexboxContainerDescription viewFlexboxContainerDescription, AQLInterpreter interpreter) {
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
                .map(formElementDescription -> this.convert(formElementDescription, interpreter))
                .flatMap(Optional::stream)
                .toList();

        var flexboxContainerDescription = FlexboxContainerDescription.newFlexboxContainerDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .targetObjectIdProvider(new TargetObjectIdProvider(this.identityService))
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
}
