/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.DiagramStyle;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.diagram.DiagramStyleDescription;
import org.eclipse.sirius.components.view.diagram.IconLabelNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.OutsideLabelStyle;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.emf.ViewConverter;

/**
 * Factory to create the basic style descriptions used by {@link ViewConverter}.
 *
 * @author pcdavid
 * @author lfasani
 */
public final class StylesFactory {

    private static final String DEFAULT_COLOR = "black";

    private static final String DEFAULT_TRANSPARENT_COLOR = "transparent";

    private final List<INodeStyleProvider> iNodeStyleProviders;

    private final IObjectService objectService;

    private final AQLInterpreter interpreter;

    public StylesFactory(List<INodeStyleProvider> iNodeStyleProviders, IObjectService objectService, AQLInterpreter interpreter) {
        this.iNodeStyleProviders = Objects.requireNonNull(iNodeStyleProviders);
        this.objectService = Objects.requireNonNull(objectService);
        this.interpreter = Objects.requireNonNull(interpreter);
    }

    public LabelStyleDescription createEdgeLabelStyleDescription(org.eclipse.sirius.components.view.diagram.EdgeStyle edgeStyle) {
        return LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> this.getFixedColorValue(edgeStyle.getColor(), DEFAULT_COLOR))
                .fontSizeProvider(variableManager -> edgeStyle.getFontSize())
                .boldProvider(variableManager -> edgeStyle.isBold())
                .italicProvider(variableManager -> edgeStyle.isItalic())
                .underlineProvider(variableManager -> edgeStyle.isUnderline())
                .strikeThroughProvider(variableManager -> edgeStyle.isStrikeThrough())
                .iconURLProvider(variableManager -> {
                    List<String> iconURL = List.of();
                    if (edgeStyle.isShowIcon() && edgeStyle.getLabelIcon() == null) {
                        iconURL = variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getImagePath).orElse(List.of());
                    }
                    if (edgeStyle.isShowIcon() && edgeStyle.getLabelIcon() != null) {
                        iconURL = List.of(edgeStyle.getLabelIcon());
                    }
                    return iconURL;
                })
                .backgroundProvider(variableManager -> this.getFixedColorValue(edgeStyle.getBackground(), DEFAULT_TRANSPARENT_COLOR))
                .borderColorProvider(variableManager -> this.getFixedColorValue(edgeStyle.getBorderColor(), DEFAULT_COLOR))
                .borderSizeProvider(variableManager -> edgeStyle.getBorderSize())
                .borderRadiusProvider(variableManager -> edgeStyle.getBorderRadius())
                .borderStyleProvider(variableManager -> LineStyle.valueOf(edgeStyle.getBorderLineStyle().getLiteral()))
                .maxWidthProvider(variableManager -> this.computeMaxWidthProvider(edgeStyle.getMaxWidthExpression(), variableManager))
                .build();
    }

    public EdgeStyle createEdgeStyle(org.eclipse.sirius.components.view.diagram.EdgeStyle edgeStyle) {
        return EdgeStyle.newEdgeStyle()
                .color(this.getFixedColorValue(edgeStyle.getColor(), DEFAULT_COLOR))
                .lineStyle(LineStyle.valueOf(edgeStyle.getLineStyle().getLiteral()))
                .size(edgeStyle.getEdgeWidth())
                .sourceArrow(ArrowStyle.valueOf(edgeStyle.getSourceArrowStyle().getLiteral()))
                .targetArrow(ArrowStyle.valueOf(edgeStyle.getTargetArrowStyle().getLiteral()))
                .build();
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
                    type = nodeType;
                    break;
                }
            }
        }
        return type.orElse(NodeType.NODE_RECTANGLE);
    }

    public DiagramStyle createDiagramStyle(DiagramStyleDescription diagramStyleDescription) {
        return DiagramStyle.newDiagramStyle()
                .background(Optional.ofNullable(diagramStyleDescription.getBackground())
                        .filter(FixedColor.class::isInstance)
                        .map(FixedColor.class::cast)
                        .map(FixedColor::getValue)
                        .orElse(DEFAULT_TRANSPARENT_COLOR))
                .build();
    }

    public INodeStyle createNodeStyle(NodeStyleDescription nodeStyle, Optional<String> optionalEditingContextId) {
        INodeStyle result = null;
        switch (this.getNodeType(nodeStyle)) {
            case NodeType.NODE_ICON_LABEL:
                result = IconLabelNodeStyle.newIconLabelNodeStyle()
                        .background(DEFAULT_TRANSPARENT_COLOR)
                        .build();
                break;
            case NodeType.NODE_RECTANGLE:
                result = RectangularNodeStyle.newRectangularNodeStyle()
                        .background(this.getFixedColorValue(((RectangularNodeStyleDescription) nodeStyle).getBackground(), DEFAULT_COLOR))
                        .borderColor(this.getFixedColorValue(nodeStyle.getBorderColor(), ""))
                        .borderSize(nodeStyle.getBorderSize())
                        .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                        .borderRadius(nodeStyle.getBorderRadius())
                        .build();
                break;
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
            result = RectangularNodeStyle.newRectangularNodeStyle()
                    .background(this.getFixedColorValue(((RectangularNodeStyleDescription) nodeStyle).getBackground(), DEFAULT_COLOR))
                    .borderColor(this.getFixedColorValue(nodeStyle.getBorderColor(), ""))
                    .borderSize(nodeStyle.getBorderSize())
                    .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                    .borderRadius(nodeStyle.getBorderRadius())
                    .build();
        }

        return result;
    }

    public LabelStyleDescription createInsideLabelStyle(InsideLabelStyle labelStyle) {
        return LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> this.getFixedColorValue(labelStyle.getLabelColor(), DEFAULT_COLOR))
                .fontSizeProvider(variableManager -> labelStyle.getFontSize())
                .boldProvider(variableManager -> labelStyle.isBold())
                .italicProvider(variableManager -> labelStyle.isItalic())
                .underlineProvider(variableManager -> labelStyle.isUnderline())
                .strikeThroughProvider(variableManager -> labelStyle.isStrikeThrough())
                .iconURLProvider(variableManager -> {
                    List<String> iconURL = List.of();
                    boolean isShowIcon = false;
                    String showIconExpression = labelStyle.getShowIconExpression();
                    if (showIconExpression != null && !showIconExpression.isBlank()) {
                        isShowIcon = this.interpreter.evaluateExpression(variableManager.getVariables(), showIconExpression).asBoolean().orElse(false);
                    }
                    if (isShowIcon && labelStyle.getLabelIcon() == null) {
                        iconURL = variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getImagePath).orElse(List.of());
                    }
                    if (isShowIcon && labelStyle.getLabelIcon() != null) {
                        iconURL = List.of(labelStyle.getLabelIcon());
                    }
                    return iconURL;
                })
                .backgroundProvider(variableManager -> this.getFixedColorValue(labelStyle.getBackground(), DEFAULT_TRANSPARENT_COLOR))
                .borderColorProvider(variableManager -> this.getFixedColorValue(labelStyle.getBorderColor(), DEFAULT_COLOR))
                .borderSizeProvider(variableManager -> labelStyle.getBorderSize())
                .borderRadiusProvider(variableManager -> labelStyle.getBorderRadius())
                .borderStyleProvider(variableManager -> LineStyle.valueOf(labelStyle.getBorderLineStyle().getLiteral()))
                .maxWidthProvider(variableManager -> this.computeMaxWidthProvider(labelStyle.getMaxWidthExpression(), variableManager))
                .build();
    }

    public LabelStyleDescription createOutsideLabelStyle(OutsideLabelStyle labelStyle) {
        return LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> this.getFixedColorValue(labelStyle.getLabelColor(), DEFAULT_COLOR))
                .fontSizeProvider(variableManager -> labelStyle.getFontSize())
                .boldProvider(variableManager -> labelStyle.isBold())
                .italicProvider(variableManager -> labelStyle.isItalic())
                .underlineProvider(variableManager -> labelStyle.isUnderline())
                .strikeThroughProvider(variableManager -> labelStyle.isStrikeThrough())
                .iconURLProvider(variableManager -> {
                    List<String> iconURL = List.of();
                    boolean isShowIcon = false;
                    String showIconExpression = labelStyle.getShowIconExpression();
                    if (showIconExpression != null && !showIconExpression.isBlank()) {
                        isShowIcon = this.interpreter.evaluateExpression(variableManager.getVariables(), showIconExpression).asBoolean().orElse(false);
                    }
                    if (isShowIcon && labelStyle.getLabelIcon() == null) {
                        iconURL = variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getImagePath).orElse(List.of());
                    }
                    if (isShowIcon && labelStyle.getLabelIcon() != null) {
                        iconURL = List.of(labelStyle.getLabelIcon());
                    }
                    return iconURL;
                })
                .backgroundProvider(variableManager -> this.getFixedColorValue(labelStyle.getBackground(), DEFAULT_TRANSPARENT_COLOR))
                .borderColorProvider(variableManager -> this.getFixedColorValue(labelStyle.getBorderColor(), DEFAULT_COLOR))
                .borderSizeProvider(variableManager -> labelStyle.getBorderSize())
                .borderRadiusProvider(variableManager -> labelStyle.getBorderRadius())
                .borderStyleProvider(variableManager -> LineStyle.valueOf(labelStyle.getBorderLineStyle().getLiteral()))
                .maxWidthProvider(variableManager -> this.computeMaxWidthProvider(labelStyle.getMaxWidthExpression(), variableManager))
                .build();
    }

    private String getFixedColorValue(UserColor color, String defaultValue) {
        return Optional.ofNullable(color)
                .filter(FixedColor.class::isInstance)
                .map(FixedColor.class::cast)
                .map(FixedColor::getValue)
                .orElse(defaultValue);
    }

    private String computeMaxWidthProvider(String maxWidthExpression, VariableManager variableManager) {
        if (maxWidthExpression != null && !maxWidthExpression.isBlank()) {
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), maxWidthExpression);
            if (result.getStatus().compareTo(Status.WARNING) <= 0 && result.asString().isPresent()) {
                return result.asString().get();
            }
        }
        return null;
    }
}
