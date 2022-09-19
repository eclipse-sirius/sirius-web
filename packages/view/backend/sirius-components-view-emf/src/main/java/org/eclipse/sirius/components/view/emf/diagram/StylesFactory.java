/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.view.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.emf.ViewConverter;

/**
 * Factory to create the basic style descriptions used by {@link ViewConverter}.
 *
 * @author pcdavid
 */
public final class StylesFactory {

    private static final String DEFAULT_COLOR = "black"; //$NON-NLS-1$

    public LabelStyleDescription createLabelStyleDescription(NodeStyleDescription nodeStyle) {
        // @formatter:off
        return LabelStyleDescription.newLabelStyleDescription()
                                    .colorProvider(variableManager -> nodeStyle.getLabelColor())
                                    .fontSizeProvider(variableManager -> nodeStyle.getFontSize())
                                    .boldProvider(variableManager -> nodeStyle.isBold())
                                    .italicProvider(variableManager -> nodeStyle.isItalic())
                                    .underlineProvider(variableManager -> nodeStyle.isUnderline())
                                    .strikeThroughProvider(variableManager -> nodeStyle.isStrikeThrough())
                                    .iconURLProvider(variableManager -> "") //$NON-NLS-1$
                                    .build();
        // @formatter:on
    }

    public LabelStyleDescription createEdgeLabelStyleDescription(org.eclipse.sirius.components.view.EdgeStyle edgeStyle) {
        // @formatter:off
        return LabelStyleDescription.newLabelStyleDescription()
                                    .colorProvider(variableManager -> edgeStyle.getColor())
                                    .fontSizeProvider(variableManager -> edgeStyle.getFontSize())
                                    .boldProvider(variableManager -> edgeStyle.isBold())
                                    .italicProvider(variableManager -> edgeStyle.isItalic())
                                    .underlineProvider(variableManager -> edgeStyle.isUnderline())
                                    .strikeThroughProvider(variableManager -> edgeStyle.isStrikeThrough())
                                    .iconURLProvider(variableManager -> "") //$NON-NLS-1$
                                    .build();
        // @formatter:on
    }

    public EdgeStyle createEdgeStyle(org.eclipse.sirius.components.view.EdgeStyle edgeStyle) {
        // @formatter:off
        return EdgeStyle.newEdgeStyle()
                        .color(edgeStyle.getColor())
                        .lineStyle(LineStyle.valueOf(edgeStyle.getLineStyle().getLiteral()))
                        .size(edgeStyle.getEdgeWidth())
                        .sourceArrow(ArrowStyle.valueOf(edgeStyle.getSourceArrowStyle().getLiteral()))
                        .targetArrow(ArrowStyle.valueOf(edgeStyle.getTargetArrowStyle().getLiteral()))
                        .build();
        // @formatter:on
    }

    public String getNodeType(NodeStyleDescription nodeStyle) {
        String type = NodeType.NODE_RECTANGLE;
        if (nodeStyle instanceof IconLabelNodeStyleDescription) {
            type = NodeType.NODE_ICON_LABEL;
        } else if (nodeStyle instanceof ImageNodeStyleDescription) {
            type = NodeType.NODE_IMAGE;
        }
        return type;
    }

    public INodeStyle createNodeStyle(NodeStyleDescription nodeStyle, Optional<String> optionalEditingContextId) {
        INodeStyle result = null;
        if (nodeStyle instanceof RectangularNodeStyleDescription) {
            // @formatter:off
            result = RectangularNodeStyle.newRectangularNodeStyle()
                   .withHeader(((RectangularNodeStyleDescription) nodeStyle).isWithHeader())
                   .color(Optional.ofNullable(nodeStyle.getColor()).orElse(DEFAULT_COLOR))
                   .borderColor(Optional.ofNullable(nodeStyle.getBorderColor()).orElse(DEFAULT_COLOR))
                   .borderSize(nodeStyle.getBorderSize())
                   .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                   .borderRadius(nodeStyle.getBorderRadius())
                   .build();
            // @formatter:on
        } else if (nodeStyle instanceof IconLabelNodeStyleDescription) {
            result = IconLabelNodeStyle.newIconLabelNodeStyle().backgroundColor("transparent").build(); //$NON-NLS-1$
        } else if (nodeStyle instanceof ImageNodeStyleDescription) {
            // @formatter:off
            result = ImageNodeStyle.newImageNodeStyle()
                    .scalingFactor(1)
                    .imageURL("/custom/" + optionalEditingContextId.get().toString() + "/" + ((ImageNodeStyleDescription) nodeStyle).getShape()) //$NON-NLS-1$ //$NON-NLS-2$
                    .borderColor(Optional.ofNullable(nodeStyle.getBorderColor()).orElse(DEFAULT_COLOR))
                    .borderSize(nodeStyle.getBorderSize())
                    .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                    .borderRadius(nodeStyle.getBorderRadius())
                    .build();
            // @formatter:on
        }

        return result;
    }
    // CHECKSTYLE:ON
}
