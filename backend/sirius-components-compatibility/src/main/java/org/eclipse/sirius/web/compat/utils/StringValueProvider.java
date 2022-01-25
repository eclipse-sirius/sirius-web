/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.utils;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Utility class used to provide a string value from value expression.
 *
 * @author sbegaudeau
 */
public class StringValueProvider implements Function<VariableManager, String> {

    private static final String EMPTY_STRING = ""; //$NON-NLS-1$

    private AQLInterpreter interpreter;

    private String expression;

    public StringValueProvider(AQLInterpreter interpreter, String expression) {
        this.interpreter = interpreter;
        this.expression = Objects.requireNonNull(expression);
    }

    @Override
    public String apply(VariableManager variableManager) {
        if (!this.expression.isBlank()) {
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), this.expression);
            return result.asString().orElse(EMPTY_STRING);
        }
        return EMPTY_STRING;
    }
}
