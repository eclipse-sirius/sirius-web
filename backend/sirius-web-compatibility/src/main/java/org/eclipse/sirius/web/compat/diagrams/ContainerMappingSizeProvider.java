/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Used to compute the size using the definition of a container mapping.
 *
 * @author fbarbin
 */
public class ContainerMappingSizeProvider implements Function<VariableManager, Size> {

    /**
     * Inherit from Sirius Desktop. Currently, the size specified in the VSM is multiplied by 10.
     */
    private static final int SIZE_FACTOR = 10;

    private final AQLInterpreter interpreter;

    private final ContainerMapping containerMapping;

    public ContainerMappingSizeProvider(AQLInterpreter interpreter, ContainerMapping containerMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.containerMapping = Objects.requireNonNull(containerMapping);
    }

    @Override
    public Size apply(VariableManager variableManager) {
        ContainerStyleDescription containerStyleDescription = new ContainerStyleDescriptionProvider(this.interpreter, this.containerMapping).getContainerStyleDescription(variableManager);
        return this.getNodeSize(variableManager, containerStyleDescription);
    }

    private Size getNodeSize(VariableManager variableManager, ContainerStyleDescription containerStyleDescription) {
        int width = 0;
        int height = 0;
        if (containerStyleDescription instanceof FlatContainerStyleDescription) {
            FlatContainerStyleDescription flatContainerStyleDescription = (FlatContainerStyleDescription) containerStyleDescription;
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), flatContainerStyleDescription.getWidthComputationExpression());
            int computedWidth = result.asInt().getAsInt();
            if (computedWidth > 0) {
                width = computedWidth * SIZE_FACTOR;
            }
            result = this.interpreter.evaluateExpression(variableManager.getVariables(), flatContainerStyleDescription.getHeightComputationExpression());
            int computedHeight = result.asInt().getAsInt();
            if (computedHeight > 0) {
                height = computedHeight * SIZE_FACTOR;
            }
        }

        return Size.of(width, height);
    }
}
