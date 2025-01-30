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
package org.eclipse.sirius.components.view.emf.form.converters.validation;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the diagnostics of a widget.
 *
 * @author sbegaudeau
 */
public class DiagnosticProvider implements Function<VariableManager, List<?>> {

    private final AQLInterpreter interpreter;

    private final String diagnosticExpression;

    public DiagnosticProvider(AQLInterpreter interpreter, String diagnosticExpression) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.diagnosticExpression = diagnosticExpression;
    }

    @Override
    public List<?> apply(VariableManager variableManager) {
        if (this.diagnosticExpression != null && !this.diagnosticExpression.isBlank()) {
            return this.interpreter.evaluateExpression(variableManager.getVariables(), this.diagnosticExpression)
                    .asObjects()
                    .orElse(List.of());
        }
        return List.of();
    }
}
