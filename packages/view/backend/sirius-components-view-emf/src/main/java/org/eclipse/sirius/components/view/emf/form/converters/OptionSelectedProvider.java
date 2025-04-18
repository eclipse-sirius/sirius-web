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
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.forms.components.RadioComponent;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the selected state of an option.
 *
 * @author sbegaudeau
 */
public class OptionSelectedProvider implements Function<VariableManager, Boolean> {

    private final AQLInterpreter interpreter;

    private final String expression;

    public OptionSelectedProvider(AQLInterpreter interpreter, String expression) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.expression = expression;
    }

    @Override
    public Boolean apply(VariableManager variableManager) {
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VARIABLE_MANAGER, variableManager);
        Optional<Object> optionalResult = this.interpreter.evaluateExpression(childVariableManager.getVariables(), this.expression).asObject();
        Object candidate = variableManager.getVariables().get(RadioComponent.CANDIDATE_VARIABLE);
        return optionalResult.map(candidate::equals).orElse(Boolean.FALSE);
    }
}
