/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.services.diagram;

import java.util.Optional;
import java.util.UUID;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ParametricSVGNodeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.springframework.stereotype.Service;

/**
 * Customize the label position for parametric SVG node styled nodes.
 *
 * @author lfasani
 */
@Service
public class ParametricSVGNodeStyleLabelPositionProvider implements ICustomNodeLabelPositionProvider {

    @Override
    public Optional<Position> getLabelPosition(ISiriusWebLayoutConfigurator layoutConfigurator, Size initialLabelSize, Size nodeSize, String nodeType, INodeStyle nodeStyle) {
        Optional<Position> positionOpt = Optional.empty();
        if (nodeStyle instanceof ParametricSVGNodeStyle parametricNodeStyle) {
            String svgURL = parametricNodeStyle.getSvgURL();
            if (svgURL.contains(UUID.nameUUIDFromBytes("Class".getBytes()).toString())) {
                // horizontally centered
                ElkPadding labelPadding = layoutConfigurator.configureByType(nodeType).getProperty(CoreOptions.NODE_LABELS_PADDING);
                positionOpt = Optional.of(Position.at(nodeSize.getWidth() / 2 - initialLabelSize.getWidth() / 2, labelPadding.getTop()));
            } else {
                // horizontally left
                ElkPadding labelPadding = layoutConfigurator.configureByType(nodeType).getProperty(CoreOptions.NODE_LABELS_PADDING);
                positionOpt = Optional.of(Position.at(labelPadding.getLeft(), labelPadding.getTop()));
            }
        }
        return positionOpt;
    }
}
