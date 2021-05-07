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
package org.eclipse.sirius.web.emf.view;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.ArrowStyle;
import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.web.services.api.images.CustomImage;
import org.eclipse.sirius.web.services.api.images.ICustomImagesService;
import org.eclipse.sirius.web.view.NodeStyle;

/**
 * Factory to create the basic style descriptions used by {@link ViewConverter}.
 *
 * @author pcdavid
 */
public final class StylesFactory {

    private static final String DEFAULT_SHAPE_FILE = "shape_square.svg"; //$NON-NLS-1$

    private static final String DEFAULT_COLOR = "black"; //$NON-NLS-1$

    public LabelStyleDescription createLabelStyleDescription(NodeStyle nodeStyle) {
        // @formatter:off
        return LabelStyleDescription.newLabelStyleDescription()
                                    .colorProvider(variableManager -> nodeStyle.getColor())
                                    .fontSizeProvider(variableManager -> nodeStyle.getFontSize())
                                    .boldProvider(variableManager -> false)
                                    .italicProvider(variableManager -> false)
                                    .underlineProvider(variableManager -> false)
                                    .strikeThroughProvider(variableManager -> false)
                                    .iconURLProvider(variableManager -> "") //$NON-NLS-1$
                                    .build();
        // @formatter:on
    }

    public EdgeStyle createEdgeStyle(org.eclipse.sirius.web.view.EdgeStyle edgeStyle) {
        // @formatter:off
        return EdgeStyle.newEdgeStyle()
                        .color(edgeStyle.getColor())
                        .lineStyle(LineStyle.valueOf(edgeStyle.getLineStyle().getLiteral()))
                        .size(1)
                        .sourceArrow(ArrowStyle.valueOf(edgeStyle.getSourceArrowStyle().getLiteral()))
                        .targetArrow(ArrowStyle.valueOf(edgeStyle.getTargetArrowStyle().getLiteral()))
                        .build();
        // @formatter:on
    }

    public String getNodeType(NodeStyle nodeStyle) {
        if (nodeStyle.getShape() == null || nodeStyle.getShape().isBlank()) {
            return NodeType.NODE_RECTANGLE;
        } else {
            return NodeType.NODE_IMAGE;
        }
    }

    public INodeStyle createNodeStyle(NodeStyle nodeStyle, ICustomImagesService customImagesService) {
        if (NodeType.NODE_IMAGE.equals(this.getNodeType(nodeStyle))) {
            String shapeId = nodeStyle.getShape();
            String shapeFileName = customImagesService.findById(UUID.fromString(shapeId)).map(CustomImage::getFileName).orElse(DEFAULT_SHAPE_FILE);
            // @formatter:off
            return ImageNodeStyle.newImageNodeStyle()
                                 .scalingFactor(1)
                                 .imageURL("/custom/" + shapeFileName) //$NON-NLS-1$
                                 .build();
            // @formatter:on
        } else {
            // @formatter:off
            return RectangularNodeStyle.newRectangularNodeStyle()
                    .color(Optional.ofNullable(nodeStyle.getColor()).orElse(DEFAULT_COLOR))
                    .borderColor(Optional.ofNullable(nodeStyle.getBorderColor()).orElse(DEFAULT_COLOR))
                    .borderSize(1)
                    .borderStyle(LineStyle.Solid)
                    .build();
            // @formatter:on
        }
    }
}
