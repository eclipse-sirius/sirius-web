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

import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.SquareDescription;
import org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Used to compute the size using the definition of a node mapping.
 *
 * @author fbarbin
 */
public class AbstractNodeMappingSizeProvider implements Function<VariableManager, Size> {
    /**
     * Inherit from Sirius Desktop. Currently, the size specified in the VSM is multiplied by 10.
     */
    private static final int SIZE_FACTOR = 10;

    private final AQLInterpreter interpreter;

    private final AbstractNodeMapping abstractNodeMapping;

    public AbstractNodeMappingSizeProvider(AQLInterpreter interpreter, AbstractNodeMapping abstractNodeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.abstractNodeMapping = Objects.requireNonNull(abstractNodeMapping);
    }

    @Override
    public Size apply(VariableManager variableManager) {
        LabelStyleDescription labelStyleDescription = new LabelStyleDescriptionProvider(this.interpreter, this.abstractNodeMapping).apply(variableManager);

        Size size = Size.UNDEFINED;
        if (labelStyleDescription instanceof SquareDescription) {
            SquareDescription squareDescription = (SquareDescription) labelStyleDescription;
            int width = squareDescription.getWidth() * SIZE_FACTOR;
            int height = squareDescription.getHeight() * SIZE_FACTOR;

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
        } else if (labelStyleDescription instanceof FlatContainerStyleDescription) {
            int width = -1;
            int height = -1;
            FlatContainerStyleDescription flatContainerStyleDescription = (FlatContainerStyleDescription) labelStyleDescription;
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
            size = Size.of(width, height);
        }
        return size;
    }

}
