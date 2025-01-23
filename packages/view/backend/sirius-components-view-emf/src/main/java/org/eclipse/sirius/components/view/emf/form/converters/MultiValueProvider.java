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
 * @author sbegaudeau
 */
public class MultiValueProvider implements Function<VariableManager, List<?>> {

    private final AQLInterpreter interpreter;

    private final String expression;

    public MultiValueProvider(AQLInterpreter interpreter, String expression) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.expression = expression;
    }

    @Override
    public List<?> apply(VariableManager variableManager) {
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VARIABLE_MANAGER, variableManager);
        if (this.expression != null && !this.expression.isBlank()) {
            return this.interpreter.evaluateExpression(childVariableManager.getVariables(), expression)
                    .asObjects()
                    .orElse(List.of());
        }
        return List.of();
    }
}
