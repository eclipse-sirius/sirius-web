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
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.components.NodeAppearance;
import org.eclipse.sirius.components.diagrams.events.appearance.INodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderStyleAppearanceChange;
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
    public static final String BORDER_COLOR = "BORDER_COLOR";
    public static final String BORDER_RADIUS = "BORDER_RADIUS";
    public static final String BORDER_SIZE = "BORDER_SIZE";
    public static final String BORDER_STYLE = "BORDER_STYLE";

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
            this.handleBorderColor(styleBuilder, changes, optionalPreviousNodeStyle, customizedStyleProperties);
            this.handleBorderRadius(styleBuilder, changes, optionalPreviousNodeStyle, customizedStyleProperties);
            this.handleBorderSize(styleBuilder, changes, optionalPreviousNodeStyle, customizedStyleProperties);
            this.handleBorderStyle(styleBuilder, changes, optionalPreviousNodeStyle, customizedStyleProperties);
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

    private void handleBorderColor(RectangularNodeStyle.Builder styleBuilder, List<INodeAppearanceChange> changes, Optional<INodeStyle> optionalPreviousNodeStyle,
            Set<String> customizedStyleProperties) {
        boolean borderColorReset = changes.stream()
                .filter(ResetNodeAppearanceChange.class::isInstance)
                .map(ResetNodeAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BORDER_COLOR));

        if (borderColorReset) {
            customizedStyleProperties.remove(BORDER_COLOR);
        } else {
            Optional<NodeBorderColorAppearanceChange> optionalBorderColorChange = changes.stream()
                    .filter(NodeBorderColorAppearanceChange.class::isInstance)
                    .map(NodeBorderColorAppearanceChange.class::cast)
                    .findFirst();

            if (optionalBorderColorChange.isPresent()) {
                String newBorderColor = optionalBorderColorChange.get().borderColor();
                styleBuilder.borderColor(newBorderColor);
                customizedStyleProperties.add(BORDER_COLOR);
            } else if (customizedStyleProperties.contains(BORDER_COLOR) && optionalPreviousNodeStyle.isPresent() && optionalPreviousNodeStyle.get() instanceof RectangularNodeStyle previousNodeStyle) {
                String previousBorderColor = previousNodeStyle.getBorderColor();
                styleBuilder.borderColor(previousBorderColor);
            } else {
                customizedStyleProperties.remove(BORDER_COLOR);
            }
        }
    }

    private void handleBorderRadius(RectangularNodeStyle.Builder styleBuilder, List<INodeAppearanceChange> changes, Optional<INodeStyle> optionalPreviousNodeStyle,
            Set<String> customizedStyleProperties) {
        boolean borderRadiusReset = changes.stream()
                .filter(ResetNodeAppearanceChange.class::isInstance)
                .map(ResetNodeAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BORDER_RADIUS));

        if (borderRadiusReset) {
            customizedStyleProperties.remove(BORDER_RADIUS);
        } else {
            Optional<NodeBorderRadiusAppearanceChange> optionalBorderRadiusChange = changes.stream()
                    .filter(NodeBorderRadiusAppearanceChange.class::isInstance)
                    .map(NodeBorderRadiusAppearanceChange.class::cast)
                    .findFirst();

            if (optionalBorderRadiusChange.isPresent()) {
                int newBorderRadius = optionalBorderRadiusChange.get().borderRadius();
                styleBuilder.borderRadius(newBorderRadius);
                customizedStyleProperties.add(BORDER_RADIUS);
            } else if (customizedStyleProperties.contains(BORDER_RADIUS) && optionalPreviousNodeStyle.isPresent() && optionalPreviousNodeStyle.get() instanceof RectangularNodeStyle previousNodeStyle) {
                int previousBorderRadius = previousNodeStyle.getBorderRadius();
                styleBuilder.borderRadius(previousBorderRadius);
            } else {
                customizedStyleProperties.remove(BORDER_RADIUS);
            }
        }
    }

    private void handleBorderSize(RectangularNodeStyle.Builder styleBuilder, List<INodeAppearanceChange> changes, Optional<INodeStyle> optionalPreviousNodeStyle,
            Set<String> customizedStyleProperties) {
        boolean borderSizeReset = changes.stream()
                .filter(ResetNodeAppearanceChange.class::isInstance)
                .map(ResetNodeAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BORDER_SIZE));

        if (borderSizeReset) {
            customizedStyleProperties.remove(BORDER_SIZE);
        } else {
            Optional<NodeBorderSizeAppearanceChange> optionalBorderSizeChange = changes.stream()
                    .filter(NodeBorderSizeAppearanceChange.class::isInstance)
                    .map(NodeBorderSizeAppearanceChange.class::cast)
                    .findFirst();

            if (optionalBorderSizeChange.isPresent()) {
                int newBorderSize = optionalBorderSizeChange.get().borderSize();
                styleBuilder.borderSize(newBorderSize);
                customizedStyleProperties.add(BORDER_SIZE);
            } else if (customizedStyleProperties.contains(BORDER_SIZE) && optionalPreviousNodeStyle.isPresent() && optionalPreviousNodeStyle.get() instanceof RectangularNodeStyle previousNodeStyle) {
                int previousBorderSize = previousNodeStyle.getBorderSize();
                styleBuilder.borderSize(previousBorderSize);
            } else {
                customizedStyleProperties.remove(BORDER_SIZE);
            }
        }
    }

    private void handleBorderStyle(RectangularNodeStyle.Builder styleBuilder, List<INodeAppearanceChange> changes, Optional<INodeStyle> optionalPreviousNodeStyle,
            Set<String> customizedStyleProperties) {
        boolean borderStyleReset = changes.stream()
                .filter(ResetNodeAppearanceChange.class::isInstance)
                .map(ResetNodeAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BORDER_STYLE));

        if (borderStyleReset) {
            customizedStyleProperties.remove(BORDER_STYLE);
        } else {
            Optional<NodeBorderStyleAppearanceChange> optionalBorderStyleChange = changes.stream()
                    .filter(NodeBorderStyleAppearanceChange.class::isInstance)
                    .map(NodeBorderStyleAppearanceChange.class::cast)
                    .findFirst();

            if (optionalBorderStyleChange.isPresent()) {
                LineStyle newBorderStyle = optionalBorderStyleChange.get().borderStyle();
                styleBuilder.borderStyle(newBorderStyle);
                customizedStyleProperties.add(BORDER_STYLE);
            } else if (customizedStyleProperties.contains(BORDER_STYLE) && optionalPreviousNodeStyle.isPresent() && optionalPreviousNodeStyle.get() instanceof RectangularNodeStyle previousNodeStyle) {
                LineStyle previousBorderStyle = previousNodeStyle.getBorderStyle();
                styleBuilder.borderStyle(previousBorderStyle);
            } else {
                customizedStyleProperties.remove(BORDER_STYLE);
            }
        }
    }
}
