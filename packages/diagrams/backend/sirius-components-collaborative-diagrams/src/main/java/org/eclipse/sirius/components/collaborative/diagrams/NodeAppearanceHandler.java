/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.INodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.ResetNodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.renderer.INodeAppearanceHandler;
import org.springframework.stereotype.Service;

/**
 * Default implementation of a service used to handle the customization of a node's appearance.
 *
 * @author nvannier
 */
@Service
public class NodeAppearanceHandler implements INodeAppearanceHandler {

    @Override
    public boolean canHandle(INodeStyle nodeStyle) {
        return nodeStyle instanceof RectangularNodeStyle;
    }

    @Override
    public INodeStyle handle(INodeStyle providedStyle, List<INodeAppearanceChange> changes, Optional<INodeStyle> optPreviousNodeStyle, Set<String> customizedStyleProperties) {
        if (providedStyle instanceof RectangularNodeStyle providedRectangularNodeStyle) {
            RectangularNodeStyle.Builder styleBuilder = RectangularNodeStyle.newRectangularNodeStyle(providedRectangularNodeStyle);
            handleBackground(styleBuilder, changes, optPreviousNodeStyle, customizedStyleProperties);
            return styleBuilder.build();
        } else {
            return providedStyle;
        }
    }

    private void handleBackground(RectangularNodeStyle.Builder styleBuilder, List<INodeAppearanceChange> changes, Optional<INodeStyle> optPreviousNodeStyle, Set<String> customizedStyleProperties) {
        boolean backgroundReset =
                changes.stream().filter(ResetNodeAppearanceChange.class::isInstance).map(ResetNodeAppearanceChange.class::cast).anyMatch(reset -> Objects.equals(reset.propertyName(), "background"));
        if (!backgroundReset) {
            Optional<NodeBackgroundAppearanceChange> optBackgroundChange = changes.stream().filter(NodeBackgroundAppearanceChange.class::isInstance)
                    .map(NodeBackgroundAppearanceChange.class::cast).findAny();

            if (optBackgroundChange.isPresent()) {
                String newBackground = optBackgroundChange.get().background();
                styleBuilder.background(newBackground);
                customizedStyleProperties.add("background");
            } else if (customizedStyleProperties.contains("background") && optPreviousNodeStyle.isPresent() && optPreviousNodeStyle.get() instanceof RectangularNodeStyle previousNodeStyle) {
                String previousBackground = previousNodeStyle.getBackground();
                styleBuilder.background(previousBackground);
            } else {
                customizedStyleProperties.remove("background");
            }
        } else {
            customizedStyleProperties.remove("background");
        }
    }
}
