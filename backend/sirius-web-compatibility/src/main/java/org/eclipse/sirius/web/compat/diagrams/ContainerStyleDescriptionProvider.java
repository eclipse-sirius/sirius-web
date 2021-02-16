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

import org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Compute the proper style description to use for a container mapping.
 *
 * @author sbegaudeau
 */
public class ContainerStyleDescriptionProvider {
    private final AQLInterpreter interpreter;

    private final ContainerMapping containerMapping;

    public ContainerStyleDescriptionProvider(AQLInterpreter interpreter, ContainerMapping containerMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.containerMapping = Objects.requireNonNull(containerMapping);
    }

    public ContainerStyleDescription getContainerStyleDescription(VariableManager variableManager) {
        ContainerStyleDescription styleDescription = this.containerMapping.getStyle();
        List<ConditionalContainerStyleDescription> conditionnalStyles = this.containerMapping.getConditionnalStyles();
        for (ConditionalContainerStyleDescription conditionalStyle : conditionnalStyles) {
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
