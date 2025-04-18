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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to provide a value for a multi select.
 *
 * @author sbegaudeau
 */
public class MultiSelectValuesProvider implements Function<VariableManager, List<String>> {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final String expression;

    public MultiSelectValuesProvider(AQLInterpreter interpreter, IObjectService objectService, String expression) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.expression = expression;
    }

    @Override
    public List<String> apply(VariableManager variableManager) {
        if (this.expression != null && !this.expression.isBlank()) {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VARIABLE_MANAGER, variableManager);
            return this.interpreter.evaluateExpression(childVariableManager.getVariables(), expression)
                    .asObjects()
                    .orElse(List.of())
                    .stream()
                    .map(this.objectService::getId)
                    .toList();
        }
        return List.of();
    }
}
