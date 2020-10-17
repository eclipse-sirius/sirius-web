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
package org.eclipse.sirius.web.compat.diagrams;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Computes the label expression of the diagram.
 *
 * @author sbegaudeau
 * @author pcdavid
 */
public class DiagramLabelProvider implements Function<VariableManager, String> {

    private final AQLInterpreter interpreter;

    private final String labelExpression;

    public DiagramLabelProvider(AQLInterpreter interpreter, org.eclipse.sirius.diagram.description.DiagramDescription diagramDescription) {
        this.interpreter = Objects.requireNonNull(interpreter);

        String titleExpression = diagramDescription.getTitleExpression();
        if (titleExpression.isBlank()) {
            String defaultName = Optional.ofNullable(diagramDescription.getLabel()).orElse(diagramDescription.getName());
            titleExpression = MessageFormat.format("aql:''new {0}''", defaultName.replace("'", "''")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        this.labelExpression = titleExpression;
    }

    @Override
    public String apply(VariableManager variableManager) {
        Object label = variableManager.getVariables().get(DiagramDescription.LABEL);

        // @formatter:off
        var optionalLabel = Optional.ofNullable(label)
                .filter(String.class::isInstance)
                .map(String.class::cast);
        // @formatter:on

        return optionalLabel.orElseGet(() -> {
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), this.labelExpression);
            return result.asString().orElse(""); //$NON-NLS-1$
        });
    }

}
