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
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.ForDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.formelements.api.IFlexboxContainerDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.formelements.api.IFormElementForDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.formelements.api.IFormElementIfDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.form.FlexboxContainerDescription;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * Used to convert the form element for.
 *
 * @author sbegaudeau
 */
public class FormElementForDescriptionConverter implements IFormElementForDescriptionConverter {

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    private final List<IWidgetDescriptionConverter> widgetDescriptionConverters;

    private final IFormElementIfDescriptionConverter formElementIfDescriptionConverter;

    private final IFlexboxContainerDescriptionConverter flexboxContainerDescriptionConverter;

    public FormElementForDescriptionConverter(IObjectService objectService, IFormIdProvider widgetIdProvider, List<IWidgetDescriptionConverter> widgetDescriptionConverters, IFormElementIfDescriptionConverter formElementIfDescriptionConverter, IFlexboxContainerDescriptionConverter flexboxContainerDescriptionConverter) {
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.widgetDescriptionConverters = Objects.requireNonNull(widgetDescriptionConverters);
        this.formElementIfDescriptionConverter = Objects.requireNonNull(formElementIfDescriptionConverter);
        this.flexboxContainerDescriptionConverter = Objects.requireNonNull(flexboxContainerDescriptionConverter);
    }

    @Override
    public Optional<ForDescription> convert(FormElementFor formElementFor, AQLInterpreter interpreter) {
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
                .map(childFormElementDescription -> this.convertChildren(childFormElementDescription, interpreter))
                .flatMap(Optional::stream)
                .toList();

        var forDescription = ForDescription.newForDescription(descriptionId)
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .iterator(formElementFor.getIterator())
                .iterableProvider(iterableProvider)
                .controlDescriptions(controlDescriptions)
                .build();

        return Optional.of(forDescription);
    }

    private Optional<AbstractControlDescription> convertChildren(FormElementDescription formElementDescription, AQLInterpreter interpreter) {
        Optional<AbstractControlDescription> optionalControlDescription = Optional.empty();

        if (formElementDescription instanceof FormElementFor formElementFor) {
            optionalControlDescription = this.convert(formElementFor, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof FormElementIf formElementIf) {
            optionalControlDescription = this.formElementIfDescriptionConverter.convert(formElementIf, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof FlexboxContainerDescription flexboxContainerDescription) {
            optionalControlDescription = this.flexboxContainerDescriptionConverter.convert(flexboxContainerDescription, interpreter)
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
