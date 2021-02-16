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

import org.eclipse.sirius.diagram.description.NodeMapping;
import org.eclipse.sirius.diagram.description.style.NodeStyleDescription;
import org.eclipse.sirius.diagram.description.style.SquareDescription;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Used to compute the size using the definition of a node mapping.
 *
 * @author fbarbin
 */
public class NodeMappingSizeProvider implements Function<VariableManager, Size> {
    /**
     * Inherit from Sirius Desktop. Currently, the size specified in the VSM is multiplied by 10.
     */
    private static final int SIZE_FACTOR = 10;

    private final AQLInterpreter interpreter;

    private final NodeMapping nodeMapping;

    public NodeMappingSizeProvider(AQLInterpreter interpreter, NodeMapping nodeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.nodeMapping = Objects.requireNonNull(nodeMapping);
    }

    @Override
    public Size apply(VariableManager variableManager) {
        NodeStyleDescription nodeStyleDescription = new NodeStyleDescriptionProvider(this.interpreter, this.nodeMapping).getNodeStyleDescription(variableManager);
        return this.getNodeSize(variableManager, nodeStyleDescription);
    }

    private Size getNodeSize(VariableManager variableManager, NodeStyleDescription nodeStyleDescription) {
        Size size = Size.of(0, 0);
        if (nodeStyleDescription instanceof SquareDescription) {
            SquareDescription squareDescription = (SquareDescription) nodeStyleDescription;
            Integer width = squareDescription.getWidth() * SIZE_FACTOR;
            Integer height = squareDescription.getHeight() * SIZE_FACTOR;

            // If the initial width and/or height have not been set by the specifier, we interpret the size computation
            // expression to set the width and/or height
            if (width == 0 || height == 0) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), squareDescription.getSizeComputationExpression());
                int computedSize = result.asInt().getAsInt() * SIZE_FACTOR;
                if (computedSize > 0) {
                    if (width == 0) {
                        width = computedSize;
                    }
                    if (height == 0) {
                        height = computedSize;
                    }
                }
            }
            size = Size.of(width, height);
        }
        return size;
    }
}
