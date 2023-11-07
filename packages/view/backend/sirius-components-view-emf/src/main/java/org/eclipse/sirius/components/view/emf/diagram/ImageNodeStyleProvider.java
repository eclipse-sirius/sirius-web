/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IParametricSVGImageRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.api.ParametricSVGImage;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeStyle;
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeType;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.springframework.stereotype.Service;

/**
 * This class provides style information for the svg node style.
 *
 * @author lfasani
 */
@Service
public class ImageNodeStyleProvider implements INodeStyleProvider {

    private static final String DEFAULT_COLOR = "black";

    private static final String DEFAULT_BACKGROUND_COLOR = "white";

    private static final String DEFAULT_BORDER_COLOR = "black";

    private final List<IParametricSVGImageRegistry> parametricSVGImageServices;

    public ImageNodeStyleProvider(List<IParametricSVGImageRegistry> parametricSVGImageServices) {
        this.parametricSVGImageServices = Objects.requireNonNull(parametricSVGImageServices);
    }

    @Override
    public Optional<String> getNodeType(NodeStyleDescription nodeStyle) {
        Optional<String> nodeType = Optional.empty();
        if (nodeStyle instanceof ImageNodeStyleDescription) {
            String svgName = ((ImageNodeStyleDescription) nodeStyle).getShape();
            if (svgName != null) {
                // @formatter:off
                boolean parametricImageFound = this.parametricSVGImageServices.stream()
                        .flatMap(service -> service.getImages().stream())
                        .map(ParametricSVGImage::getId)
                        .anyMatch(UUID.fromString(svgName)::equals);
                // @formatter:on
                if (parametricImageFound) {
                    nodeType = Optional.of(ParametricSVGNodeType.NODE_TYPE_PARAMETRIC_IMAGE);
                } else {
                    nodeType = Optional.of(NodeType.NODE_IMAGE);
                }
            }
        }
        return nodeType;
    }

    @Override
    public Optional<INodeStyle> createNodeStyle(NodeStyleDescription nodeStyle, Optional<String> optionalEditingContextId) {
        Optional<INodeStyle> iNodeStyle = Optional.empty();
        Optional<String> nodeType = this.getNodeType(nodeStyle);
        if (nodeType.equals(Optional.of(ParametricSVGNodeType.NODE_TYPE_PARAMETRIC_IMAGE))) {
            iNodeStyle = Optional.of(ParametricSVGNodeStyle.newParametricSVGNodeStyle()
                    .backgroundColor(Optional.ofNullable(nodeStyle.getColor())
                            .filter(FixedColor.class::isInstance)
                            .map(FixedColor.class::cast)
                            .map(FixedColor::getValue)
                            .orElse(DEFAULT_BACKGROUND_COLOR))
                    .borderColor(Optional.ofNullable(nodeStyle.getBorderColor())
                            .filter(FixedColor.class::isInstance)
                            .map(FixedColor.class::cast)
                            .map(FixedColor::getValue)
                            .orElse(DEFAULT_BORDER_COLOR))
                    .borderSize(nodeStyle.getBorderSize())
                    .borderRadius(nodeStyle.getBorderRadius())
                    .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                    .svgURL("/api/parametricsvgs/" + ((ImageNodeStyleDescription) nodeStyle).getShape())
                    .build());
        } else if (nodeType.equals(Optional.of(NodeType.NODE_IMAGE))) {
            iNodeStyle = Optional.of(ImageNodeStyle.newImageNodeStyle()
                    .scalingFactor(1)
                    .imageURL("/custom/" + ((ImageNodeStyleDescription) nodeStyle).getShape())
                    .borderColor(Optional.ofNullable(nodeStyle.getBorderColor())
                            .filter(FixedColor.class::isInstance)
                            .map(FixedColor.class::cast)
                            .map(FixedColor::getValue)
                            .orElse(DEFAULT_COLOR))
                    .borderSize(nodeStyle.getBorderSize())
                    .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                    .borderRadius(nodeStyle.getBorderRadius())
                    .positionDependentRotation(((ImageNodeStyleDescription) nodeStyle).isPositionDependentRotation())
                    .build());
        }

        return iNodeStyle;
    }
}
