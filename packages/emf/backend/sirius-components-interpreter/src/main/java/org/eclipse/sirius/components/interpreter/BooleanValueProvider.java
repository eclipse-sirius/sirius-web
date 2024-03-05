/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.interpreter;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Utility class used to provide a boolean value from expression.
 *
 * @author fbarbin
 */
public class BooleanValueProvider implements Function<VariableManager, Boolean> {
    private final AQLInterpreter interpreter;

    private final String expression;

    public BooleanValueProvider(AQLInterpreter interpreter, String expression) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.expression = Objects.requireNonNull(expression);
    }

    @Override
    public Boolean apply(VariableManager variableManager) {
        if (!this.expression.isBlank()) {
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), this.expression);
            return result.asBoolean().orElse(Boolean.FALSE);
        }
        return Boolean.FALSE;
    }
}
