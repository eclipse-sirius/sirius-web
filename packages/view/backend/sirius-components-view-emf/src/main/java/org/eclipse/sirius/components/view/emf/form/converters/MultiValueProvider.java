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
import java.util.function.Function;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to evaluate an expression returning a list of values.
 *
 * @param <T>
 *     The expected type of the elements of the collection returned
 *
 * @author sbegaudeau
 */
public class MultiValueProvider<T> implements Function<VariableManager, List<T>> {

    private final AQLInterpreter interpreter;

    private final String expression;

    private final Class<T> type;

    public MultiValueProvider(AQLInterpreter interpreter, String expression, Class<T> type) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.expression = expression;
        this.type = type;
    }

    @Override
    public List<T> apply(VariableManager variableManager) {
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VARIABLE_MANAGER, variableManager);
        if (this.expression != null && !this.expression.isBlank()) {
            var optionalCollection = this.interpreter.evaluateExpression(childVariableManager.getVariables(), expression).asObjects();
            if (optionalCollection.isPresent()) {
                var collection = optionalCollection.get();
                return collection.stream()
                        .filter(this.type::isInstance)
                        .map(this.type::cast)
                        .toList();
            }
        }
        return List.of();
    }
}
