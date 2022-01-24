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
import java.util.function.Function;

import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ConditionalContainerStyleDescription;
import org.eclipse.sirius.diagram.description.ConditionalNodeStyleDescription;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Compute the proper style description to use for an abstract node mapping.
 *
 * @author arichard
 */
public class LabelStyleDescriptionProvider implements Function<VariableManager, LabelStyleDescription> {

    private final AQLInterpreter interpreter;

    private final AbstractNodeMapping abstractNodeMapping;

    public LabelStyleDescriptionProvider(AQLInterpreter interpreter, AbstractNodeMapping abstractNodeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.abstractNodeMapping = Objects.requireNonNull(abstractNodeMapping);
    }

    @Override
    public LabelStyleDescription apply(VariableManager variableManager) {
        LabelStyleDescription labelStyleDescription = null;

        if (this.abstractNodeMapping instanceof NodeMapping) {
            NodeMapping nodeMapping = (NodeMapping) this.abstractNodeMapping;

            labelStyleDescription = nodeMapping.getStyle();
            List<ConditionalNodeStyleDescription> conditionnalStyles = nodeMapping.getConditionnalStyles();
            for (ConditionalNodeStyleDescription conditionalStyle : conditionnalStyles) {
                String predicateExpression = conditionalStyle.getPredicateExpression();
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), predicateExpression);
                boolean shouldUseStyle = result.asBoolean().orElse(Boolean.FALSE).booleanValue();
                if (shouldUseStyle) {
                    labelStyleDescription = conditionalStyle.getStyle();
                    break;
                }
            }
        } else if (this.abstractNodeMapping instanceof ContainerMapping) {
            ContainerMapping containerMapping = (ContainerMapping) this.abstractNodeMapping;

            labelStyleDescription = containerMapping.getStyle();
            List<ConditionalContainerStyleDescription> conditionnalStyles = containerMapping.getConditionnalStyles();
            for (ConditionalContainerStyleDescription conditionalStyle : conditionnalStyles) {
                String predicateExpression = conditionalStyle.getPredicateExpression();
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), predicateExpression);
                boolean shouldUseStyle = result.asBoolean().orElse(Boolean.FALSE).booleanValue();
                if (shouldUseStyle) {
                    labelStyleDescription = conditionalStyle.getStyle();
                    break;
                }
            }
        }
        return labelStyleDescription;
    }
}
