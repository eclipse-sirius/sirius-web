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
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverterSwitch;
import org.eclipse.sirius.components.view.form.FormElementFor;

/**
 * Used to convert the form element for.
 *
 * @author sbegaudeau
 */
public class FormElementForDescriptionConverter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    private final ViewFormDescriptionConverterSwitch viewFormDescriptionConverterSwitch;

    public FormElementForDescriptionConverter(AQLInterpreter interpreter, IObjectService objectService, IFormIdProvider widgetIdProvider, ViewFormDescriptionConverterSwitch viewFormDescriptionConverterSwitch) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.viewFormDescriptionConverterSwitch = Objects.requireNonNull(viewFormDescriptionConverterSwitch);
    }

    public ForDescription convert(FormElementFor formElementFor) {
        String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(formElementFor);

        Function<VariableManager, List<?>> iterableProvider = variableManager -> {
            String safeIterabeExpression = Optional.ofNullable(formElementFor.getIterableExpression()).orElse("");
            if (!safeIterabeExpression.isBlank()) {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                Result result = this.interpreter.evaluateExpression(childVariableManager.getVariables(), safeIterabeExpression);
                return result.asObjects().orElse(List.of());
            } else {
                return List.of();
            }
        };

        List<Optional<AbstractControlDescription>> controlDescriptions = formElementFor.getChildren().stream()
                .map(this.viewFormDescriptionConverterSwitch::doSwitch)
                .toList();

        return ForDescription.newForDescription(descriptionId)
                .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                .iterator(formElementFor.getIterator())
                .iterableProvider(iterableProvider)
                .controlDescriptions(controlDescriptions.stream().filter(Optional::isPresent).map(Optional::get).toList())
                .build();
    }
}
