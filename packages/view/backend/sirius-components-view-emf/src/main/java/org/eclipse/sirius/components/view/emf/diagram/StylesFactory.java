/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.NodeStyleDescription;
import org.eclipse.sirius.components.view.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.emf.ViewConverter;

/**
 * Factory to create the basic style descriptions used by {@link ViewConverter}.
 *
 * @author pcdavid
 * @author lfasani
 */
public final class StylesFactory {

    private static final String DEFAULT_COLOR = "black";

    private List<INodeStyleProvider> iNodeStyleProviders = new ArrayList<>();

    private IObjectService objectService;

    public StylesFactory(List<INodeStyleProvider> iNodeStyleProviders, IObjectService objectService) {
        this.iNodeStyleProviders = Objects.requireNonNull(iNodeStyleProviders);
        this.objectService = Objects.requireNonNull(objectService);
    }

    public LabelStyleDescription createLabelStyleDescription(NodeStyleDescription nodeStyle) {
        // @formatter:off
        return LabelStyleDescription.newLabelStyleDescription()
                                    .colorProvider(variableManager -> nodeStyle.getLabelColor())
                                    .fontSizeProvider(variableManager -> nodeStyle.getFontSize())
                                    .boldProvider(variableManager -> nodeStyle.isBold())
                                    .italicProvider(variableManager -> nodeStyle.isItalic())
                                    .underlineProvider(variableManager -> nodeStyle.isUnderline())
                                    .strikeThroughProvider(variableManager -> nodeStyle.isStrikeThrough())
                                    .iconURLProvider(variableManager -> {
                                        String iconURL = "";
                                        if (nodeStyle.isShowIcon()) {
                                            iconURL = variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getImagePath).orElse("");
                                        }
                                        return iconURL;
                                    })
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
                                    .iconURLProvider(variableManager -> {
                                        String iconURL = "";
                                        if (edgeStyle.isShowIcon()) {
                                            iconURL = variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getImagePath).orElse("");
                                        }
                                        return iconURL;
                                    })
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

    public String getNodeType(NodeStyleDescription nodeStyleDescription) {
        Optional<String> type = Optional.empty();
        if (nodeStyleDescription instanceof RectangularNodeStyleDescription) {
            type = Optional.of(NodeType.NODE_RECTANGLE);
        } else if (nodeStyleDescription instanceof IconLabelNodeStyleDescription) {
            type = Optional.of(NodeType.NODE_ICON_LABEL);
        } else {
            for (INodeStyleProvider iNodeStyleProvider : this.iNodeStyleProviders) {
                Optional<String> nodeType = iNodeStyleProvider.getNodeType(nodeStyleDescription);
                if (nodeType.isPresent()) {
                    type = Optional.of(nodeType.get());
                    break;
                }
            }
        }
        return type.orElse(NodeType.NODE_RECTANGLE);
    }

    public INodeStyle createNodeStyle(NodeStyleDescription nodeStyle, Optional<String> optionalEditingContextId) {
        INodeStyle result = null;
        switch (this.getNodeType(nodeStyle)) {
            case NodeType.NODE_ICON_LABEL:
                result = IconLabelNodeStyle.newIconLabelNodeStyle().backgroundColor("transparent").build();
                break;
            case NodeType.NODE_RECTANGLE:
                // @formatter:off
                result = RectangularNodeStyle.newRectangularNodeStyle()
                    .withHeader(((RectangularNodeStyleDescription) nodeStyle).isWithHeader())
                    .color(Optional.ofNullable(nodeStyle.getColor()).orElse(DEFAULT_COLOR))
                    .borderColor(Optional.ofNullable(nodeStyle.getBorderColor()).orElse(DEFAULT_COLOR))
                    .borderSize(nodeStyle.getBorderSize())
                    .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                    .borderRadius(nodeStyle.getBorderRadius())
                    .build();
                break;
                // @formatter:on
            default:
                for (INodeStyleProvider iNodeStyleProvider : this.iNodeStyleProviders) {
                    Optional<INodeStyle> nodeStyleOpt = iNodeStyleProvider.createNodeStyle(nodeStyle, optionalEditingContextId);
                    if (nodeStyleOpt.isPresent()) {
                        result = nodeStyleOpt.get();
                        break;
                    }
                }
        }
        if (result == null) {
            // @formatter:off
            result = RectangularNodeStyle.newRectangularNodeStyle()
                   .withHeader(true)
                   .color(Optional.ofNullable(nodeStyle.getColor()).orElse(DEFAULT_COLOR))
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
