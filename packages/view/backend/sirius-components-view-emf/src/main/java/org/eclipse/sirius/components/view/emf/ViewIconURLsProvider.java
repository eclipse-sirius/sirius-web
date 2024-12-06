/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the iconURLs of a representation.
 *
 * @author sbegaudeau
 */
public class ViewIconURLsProvider implements Function<VariableManager, List<String>> {

    private final AQLInterpreter interpreter;

    private final String iconExpression;

    public ViewIconURLsProvider(AQLInterpreter interpreter, String iconExpression) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.iconExpression = Objects.requireNonNull(iconExpression);
    }

    @Override
    public List<String> apply(VariableManager variableManager) {
        if (this.iconExpression.trim().isBlank()) {
            return List.of();
        }
        return interpreter.evaluateExpression(variableManager.getVariables(), this.iconExpression)
                .asObjects()
                .stream()
                .flatMap(Collection::stream)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }
}
