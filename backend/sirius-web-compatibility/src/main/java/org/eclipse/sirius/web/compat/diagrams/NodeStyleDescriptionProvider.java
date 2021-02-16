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
package org.eclipse.sirius.web.compat.diagrams;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.diagram.description.ConditionalNodeStyleDescription;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Compute the proper style description to use for a node mapping.
 *
 * @author arichard
 */
public class NodeStyleDescriptionProvider {

    private final AQLInterpreter interpreter;

    private final NodeMapping nodeMapping;

    public NodeStyleDescriptionProvider(AQLInterpreter interpreter, NodeMapping nodeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.nodeMapping = Objects.requireNonNull(nodeMapping);
    }

    public NodeStyleDescription getNodeStyleDescription(VariableManager variableManager) {
        NodeStyleDescription styleDescription = this.nodeMapping.getStyle();
        List<ConditionalNodeStyleDescription> conditionnalStyles = this.nodeMapping.getConditionnalStyles();
        for (ConditionalNodeStyleDescription conditionalStyle : conditionnalStyles) {
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
