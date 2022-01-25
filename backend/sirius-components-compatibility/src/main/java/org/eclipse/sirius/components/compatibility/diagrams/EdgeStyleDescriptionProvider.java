/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.ConditionalEdgeStyleDescription;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.style.EdgeStyleDescription;

/**
 * Compute the proper style description to use for a edge mapping.
 *
 * @author sbegaudeau
 */
public class EdgeStyleDescriptionProvider {
    private final AQLInterpreter interpreter;

    private final EdgeMapping edgeMapping;

    public EdgeStyleDescriptionProvider(AQLInterpreter interpreter, EdgeMapping edgeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.edgeMapping = Objects.requireNonNull(edgeMapping);
    }

    public EdgeStyleDescription getEdgeStyleDescription(VariableManager variableManager) {
        EdgeStyleDescription styleDescription = this.edgeMapping.getStyle();
        List<ConditionalEdgeStyleDescription> conditionnalStyles = this.edgeMapping.getConditionnalStyles();
        for (ConditionalEdgeStyleDescription conditionalStyle : conditionnalStyles) {
            String predicateExpression = conditionalStyle.getPredicateExpression();
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), predicateExpression);
            boolean shouldUseStyle = result.asBoolean().orElse(Boolean.FALSE).booleanValue();
            if (shouldUseStyle) {
                styleDescription = conditionalStyle.getStyle();
                break;
            }
        }
        return styleDescription;
    }
}
