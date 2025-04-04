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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.components.NodeAppearance;
import org.eclipse.sirius.components.diagrams.events.appearance.INodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.ResetNodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.renderer.INodeAppearanceHandler;
import org.springframework.stereotype.Service;

/**
 * Default implementation of a service used to handle the customization of a rectangular node's appearance.
 *
 * @author nvannier
 */
@Service
public class RectangularNodeAppearanceHandler implements INodeAppearanceHandler {

    public static final String BACKGROUND = "BACKGROUND";

    @Override
    public boolean canHandle(INodeStyle nodeStyle) {
        return nodeStyle instanceof RectangularNodeStyle;
    }

    @Override
    public NodeAppearance handle(INodeStyle providedStyle, List<INodeAppearanceChange> changes, Optional<NodeAppearance> previousNodeAppearance) {
        Set<String> customizedStyleProperties = previousNodeAppearance.map(NodeAppearance::customizedStyleProperties).orElse(new LinkedHashSet<>());
        Optional<INodeStyle> optionalPreviousNodeStyle = previousNodeAppearance.map(NodeAppearance::style);
        if (providedStyle instanceof RectangularNodeStyle providedRectangularNodeStyle) {
            RectangularNodeStyle.Builder styleBuilder = RectangularNodeStyle.newRectangularNodeStyle(providedRectangularNodeStyle);
            this.handleBackground(styleBuilder, changes, optionalPreviousNodeStyle, customizedStyleProperties);
            return new NodeAppearance(styleBuilder.build(), customizedStyleProperties);
        } else {
            return new NodeAppearance(providedStyle, customizedStyleProperties);
        }
    }

    private void handleBackground(RectangularNodeStyle.Builder styleBuilder, List<INodeAppearanceChange> changes, Optional<INodeStyle> optionalPreviousNodeStyle, Set<String> customizedStyleProperties) {
        boolean backgroundReset = changes.stream()
                .filter(ResetNodeAppearanceChange.class::isInstance)
                .map(ResetNodeAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BACKGROUND));

        if (backgroundReset) {
            customizedStyleProperties.remove(BACKGROUND);
        } else {
            Optional<NodeBackgroundAppearanceChange> optionalBackgroundChange = changes.stream()
                    .filter(NodeBackgroundAppearanceChange.class::isInstance)
                    .map(NodeBackgroundAppearanceChange.class::cast)
                    .findFirst();

            if (optionalBackgroundChange.isPresent()) {
                String newBackground = optionalBackgroundChange.get().background();
                styleBuilder.background(newBackground);
                customizedStyleProperties.add(BACKGROUND);
            } else if (customizedStyleProperties.contains(BACKGROUND) && optionalPreviousNodeStyle.isPresent() && optionalPreviousNodeStyle.get() instanceof RectangularNodeStyle previousNodeStyle) {
                String previousBackground = previousNodeStyle.getBackground();
                styleBuilder.background(previousBackground);
            } else {
                customizedStyleProperties.remove(BACKGROUND);
            }
        }
    }
}
