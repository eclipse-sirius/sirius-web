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

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the value of a select.
 *
 * @author sbegaudeau
 */
public class SelectValueProvider implements Function<VariableManager, String> {

    private final AQLInterpreter interpreter;

    private final IIdentityService identityService;

    private final String expression;

    public SelectValueProvider(AQLInterpreter interpreter, IIdentityService identityService, String expression) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.identityService = Objects.requireNonNull(identityService);
        this.expression = expression;
    }

    @Override
    public String apply(VariableManager variableManager) {
        if (this.expression != null && !this.expression.isBlank()) {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            Result result = this.interpreter.evaluateExpression(childVariableManager.getVariables(), expression);
            var rawValue = result.asObject();
            return rawValue.map(this.identityService::getId)
                    .orElseGet(() -> rawValue.map(Objects::toString)
                            .orElse(""));
        }
        return "";
    }
}
