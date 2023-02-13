/*******************************************************************************
 * Copyright (c) 2021, 2023 THALES GLOBAL SERVICES.
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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.style.DotDescription;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.SquareDescription;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription;

/**
 * Used to compute the size using the definition of a node mapping.
 *
 * @author fbarbin
 */
public class AbstractNodeMappingSizeProvider implements Function<VariableManager, Size> {
    /**
     * The default native image size.
     */
    private static final int DEFAULT_NATIVE_IMAGE_SIZE = 100;

    /**
     * Scale factor for width and height from diagram node size to bounds.
     *
     * @see org.eclipse.sirius.diagram.ui.tools.api.layout.LayoutUtils
     */
    private static final int SCALE = 10;

    private final AQLInterpreter interpreter;

    private final AbstractNodeMapping abstractNodeMapping;

    private final ImageSizeProvider imageSizeProvider;

    public AbstractNodeMappingSizeProvider(AQLInterpreter interpreter, AbstractNodeMapping abstractNodeMapping, ImageSizeProvider imageSizeService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.abstractNodeMapping = Objects.requireNonNull(abstractNodeMapping);
        this.imageSizeProvider = Objects.requireNonNull(imageSizeService);
    }

    @Override
    public Size apply(VariableManager variableManager) {
        LabelStyleDescription labelStyleDescription = new LabelStyleDescriptionProvider(this.interpreter, this.abstractNodeMapping).apply(variableManager);

        Size size = Size.UNDEFINED;
        if (labelStyleDescription instanceof SquareDescription squareDescription) {
            int width = squareDescription.getWidth() * SCALE;
            int height = squareDescription.getHeight() * SCALE;

            // If the initial width and/or height have not been set by the specifier, we interpret the size computation
            // expression to set the width and/or height
            if (width == 0 || height == 0) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), squareDescription.getSizeComputationExpression());
                int computedSize = result.asInt().getAsInt() * SCALE;
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
        } else if (labelStyleDescription instanceof FlatContainerStyleDescription flatContainerStyleDescription) {
            int width = -1;
            int height = -1;
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), flatContainerStyleDescription.getWidthComputationExpression());
            int computedWidth = result.asInt().getAsInt();
            if (computedWidth > 0) {
                width = computedWidth * SCALE;
            }
            result = this.interpreter.evaluateExpression(variableManager.getVariables(), flatContainerStyleDescription.getHeightComputationExpression());
            int computedHeight = result.asInt().getAsInt();
            if (computedHeight > 0) {
                height = computedHeight * SCALE;
            }
            size = Size.of(width, height);
        } else if (labelStyleDescription instanceof DotDescription dotDescription) {
            int width = -1;
            int height = -1;
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), dotDescription.getSizeComputationExpression());
            int computedSize = result.asInt().getAsInt();
            if (computedSize > 0) {
                width = computedSize * SCALE;
                height = computedSize * SCALE;
            }
            size = Size.of(width, height);
        } else if (labelStyleDescription instanceof WorkspaceImageDescription workspaceImageDescription) {
            var converter = new WorkspaceImageDescriptionConverter(this.interpreter, variableManager, workspaceImageDescription);
            ImageNodeStyle nodeStyle = converter.convert();
            return this.getSize(nodeStyle);
        }
        return size;
    }

    private Size getSize(ImageNodeStyle imageNodeStyle) {
        String imageURL = imageNodeStyle.getImageURL();
        Optional<Size> imageSize = this.imageSizeProvider.getSize(imageURL);

        Size defaultSize = Size.of(DEFAULT_NATIVE_IMAGE_SIZE, DEFAULT_NATIVE_IMAGE_SIZE);
        Size nativeSize = imageSize.orElse(defaultSize);

        Size size = null;
        int scalingFactor = imageNodeStyle.getScalingFactor();
        if (scalingFactor <= -1) {
            size = nativeSize;
        } else {
            double ratio = nativeSize.getWidth() / nativeSize.getHeight();

            int width = scalingFactor * SCALE;
            double height = width * ratio;
            size = Size.of(width, height);
        }
        return size;
    }

}
