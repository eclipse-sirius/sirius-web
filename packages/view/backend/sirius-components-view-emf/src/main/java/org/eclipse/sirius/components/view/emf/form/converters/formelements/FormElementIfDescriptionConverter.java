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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.BooleanValueProvider;
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
 * Used to convert the form element if.
 *
 * @author sbegaudeau
 */
public class FormElementIfDescriptionConverter implements IFormElementIfDescriptionConverter {

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    private final List<IWidgetDescriptionConverter> widgetDescriptionConverters;

    private final IFormElementForDescriptionConverter formElementForDescriptionConverter;

    private final IFlexboxContainerDescriptionConverter flexboxContainerDescriptionConverter;

    public FormElementIfDescriptionConverter(IObjectService objectService, IFormIdProvider widgetIdProvider, List<IWidgetDescriptionConverter> widgetDescriptionConverters, IFormElementForDescriptionConverter formElementForDescriptionConverter, IFlexboxContainerDescriptionConverter flexboxContainerDescriptionConverter) {
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.widgetDescriptionConverters = Objects.requireNonNull(widgetDescriptionConverters);
        this.formElementForDescriptionConverter = Objects.requireNonNull(formElementForDescriptionConverter);
        this.flexboxContainerDescriptionConverter = Objects.requireNonNull(flexboxContainerDescriptionConverter);
    }

    @Override
    public Optional<IfDescription> convert(FormElementIf formElementIf, AQLInterpreter interpreter) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(formElementIf);

        var controlDescriptions = formElementIf.getChildren().stream()
                .map(formElementDescription -> this.convertChildren(formElementDescription, interpreter))
                .flatMap(Optional::stream)
                .toList();

        var ifDescription = IfDescription.newIfDescription(descriptionId)
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .predicate(new BooleanValueProvider(interpreter, formElementIf.getPredicateExpression()))
                .controlDescriptions(controlDescriptions)
                .build();

        return Optional.of(ifDescription);
    }

    private Optional<AbstractControlDescription> convertChildren(FormElementDescription formElementDescription, AQLInterpreter interpreter) {
        Optional<AbstractControlDescription> optionalControlDescription = Optional.empty();

        if (formElementDescription instanceof FormElementIf formElementIf) {
            optionalControlDescription = this.convert(formElementIf, interpreter)
                    .map(AbstractControlDescription.class::cast);
        } else if (formElementDescription instanceof FormElementFor formElementFor) {
            optionalControlDescription = this.formElementForDescriptionConverter.convert(formElementFor, interpreter)
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
