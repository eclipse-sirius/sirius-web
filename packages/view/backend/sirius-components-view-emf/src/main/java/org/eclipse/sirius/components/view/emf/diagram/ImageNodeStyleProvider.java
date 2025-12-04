/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
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

    @Override
    public Optional<String> getNodeType(NodeStyleDescription nodeStyle) {
        Optional<String> nodeType = Optional.empty();
        if (nodeStyle instanceof ImageNodeStyleDescription imageNodeStyleDescription) {
            String svgName = imageNodeStyleDescription.getShape();
            if (svgName != null) {
                nodeType = Optional.of(NodeType.NODE_IMAGE);
            }
        }
        return nodeType;
    }

    @Override
    public Optional<INodeStyle> createNodeStyle(NodeStyleDescription nodeStyle, ILayoutStrategy childrenLayoutStrategy, AQLInterpreter interpreter, VariableManager variableManager) {
        Optional<INodeStyle> optionalNodeStyle = Optional.empty();
        Optional<String> nodeType = this.getNodeType(nodeStyle);
        if (nodeType.equals(Optional.of(NodeType.NODE_IMAGE)) && nodeStyle instanceof ImageNodeStyleDescription imageNodeStyleDescription) {
            optionalNodeStyle = Optional.of(ImageNodeStyle.newImageNodeStyle()
                    .scalingFactor(1)
                    .imageURL(imageNodeStyleDescription.getShape())
                    .borderColor(Optional.ofNullable(nodeStyle.getBorderColor())
                            .filter(FixedColor.class::isInstance)
                            .map(FixedColor.class::cast)
                            .map(FixedColor::getValue)
                            .orElse(DEFAULT_COLOR))
                    .borderSize(nodeStyle.getBorderSize())
                    .borderStyle(LineStyle.valueOf(nodeStyle.getBorderLineStyle().getLiteral()))
                    .borderRadius(nodeStyle.getBorderRadius())
                    .positionDependentRotation(imageNodeStyleDescription.isPositionDependentRotation())
                    .childrenLayoutStrategy(childrenLayoutStrategy)
                    .build());
        }

        return optionalNodeStyle;
    }
}
