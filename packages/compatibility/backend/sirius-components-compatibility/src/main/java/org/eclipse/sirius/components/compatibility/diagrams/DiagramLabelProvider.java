/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;

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
            titleExpression = MessageFormat.format("aql:''new {0}''", defaultName.replace("'", "''"));
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
            return result.asString().orElse("");
        });
    }

}
