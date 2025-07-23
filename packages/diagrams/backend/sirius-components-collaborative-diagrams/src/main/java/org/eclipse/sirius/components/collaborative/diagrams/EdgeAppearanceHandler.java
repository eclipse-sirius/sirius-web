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

import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.components.EdgeAppearance;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeLineStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeSourceArrowStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeTargetArrowStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.IEdgeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.ResetEdgeAppearanceChange;
import org.eclipse.sirius.components.diagrams.renderer.IEdgeAppearanceHandler;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Default implementation of a service used to handle the customization of an edge appearance.
 *
 * @author mcharfadi
 */
@Service
public class EdgeAppearanceHandler implements IEdgeAppearanceHandler {

    public static final String SIZE = "SIZE";
    public static final String COLOR = "COLOR";
    public static final String LINESTYLE = "LINESTYLE";
    public static final String SOURCE_ARROW = "SOURCE_ARROW";
    public static final String TARGET_ARROW = "TARGET_ARROW";

    @Override
    public boolean canHandle(EdgeStyle edgeStyle) {
        return true;
    }

    @Override
    public EdgeAppearance handle(EdgeStyle providedStyle, List<IEdgeAppearanceChange> changes, Optional<EdgeAppearance> previousEdgeAppearance) {
        Set<String> customizedStyleProperties = previousEdgeAppearance.map(EdgeAppearance::customizedStyleProperties).orElse(new LinkedHashSet<>());
        Optional<EdgeStyle> optionalPreviousEdgeStyle = previousEdgeAppearance.map(EdgeAppearance::style);

        EdgeStyle.Builder styleBuilder = EdgeStyle.newEdgeStyle(providedStyle);
        this.handleColor(styleBuilder, changes, optionalPreviousEdgeStyle, customizedStyleProperties);
        this.handleSize(styleBuilder, changes, optionalPreviousEdgeStyle, customizedStyleProperties);
        this.handleLineStyle(styleBuilder, changes, optionalPreviousEdgeStyle, customizedStyleProperties);
        this.handleSourceArrow(styleBuilder, changes, optionalPreviousEdgeStyle, customizedStyleProperties);
        this.handleTargetArrow(styleBuilder, changes, optionalPreviousEdgeStyle, customizedStyleProperties);
        return new EdgeAppearance(styleBuilder.build(), customizedStyleProperties);

    }

    private void handleColor(EdgeStyle.Builder styleBuilder, List<IEdgeAppearanceChange> changes, Optional<EdgeStyle> optionalPreviousEdgeStyle, Set<String> customizedStyleProperties) {
        if (!handleResetChange(COLOR, changes, customizedStyleProperties)) {
            Optional<EdgeColorAppearanceChange> optionalColorChange = changes.stream()
                    .filter(EdgeColorAppearanceChange.class::isInstance)
                    .map(EdgeColorAppearanceChange.class::cast)
                    .findFirst();

            if (optionalColorChange.isPresent()) {
                String newColor = optionalColorChange.get().color();
                styleBuilder.color(newColor);
                customizedStyleProperties.add(COLOR);
            } else if (customizedStyleProperties.contains(COLOR) && optionalPreviousEdgeStyle.isPresent()) {
                String previousColor = optionalPreviousEdgeStyle.get().getColor();
                styleBuilder.color(previousColor);
            } else {
                customizedStyleProperties.remove(COLOR);
            }
        }
    }

    private void handleSize(EdgeStyle.Builder styleBuilder, List<IEdgeAppearanceChange> changes, Optional<EdgeStyle> optionalPreviousEdgeStyle, Set<String> customizedStyleProperties) {
        if (!handleResetChange(SIZE, changes, customizedStyleProperties)) {
            Optional<EdgeSizeAppearanceChange> optionalEdgeSizeChange = changes.stream()
                    .filter(EdgeSizeAppearanceChange.class::isInstance)
                    .map(EdgeSizeAppearanceChange.class::cast)
                    .findFirst();

            if (optionalEdgeSizeChange.isPresent()) {
                int newSize = optionalEdgeSizeChange.get().size();
                styleBuilder.size(newSize);
                customizedStyleProperties.add(SIZE);
            } else if (customizedStyleProperties.contains(SIZE) && optionalPreviousEdgeStyle.isPresent()) {
                int previousSize = optionalPreviousEdgeStyle.get().getSize();
                styleBuilder.size(previousSize);
            } else {
                customizedStyleProperties.remove(SIZE);
            }
        }
    }

    private void handleLineStyle(EdgeStyle.Builder styleBuilder, List<IEdgeAppearanceChange> changes, Optional<EdgeStyle> optionalPreviousEdgeStyle, Set<String> customizedStyleProperties) {
        if (!handleResetChange(LINESTYLE, changes, customizedStyleProperties)) {
            Optional<EdgeLineStyleAppearanceChange> optionalLineStyleChange = changes.stream()
                    .filter(EdgeLineStyleAppearanceChange.class::isInstance)
                    .map(EdgeLineStyleAppearanceChange.class::cast)
                    .findFirst();

            if (optionalLineStyleChange.isPresent()) {
                var newLineStyle = optionalLineStyleChange.get().lineStyle();
                styleBuilder.lineStyle(newLineStyle);
                customizedStyleProperties.add(LINESTYLE);
            } else if (customizedStyleProperties.contains(LINESTYLE) && optionalPreviousEdgeStyle.isPresent()) {
                var previousLineStyle = optionalPreviousEdgeStyle.get().getLineStyle();
                styleBuilder.lineStyle(previousLineStyle);
            } else {
                customizedStyleProperties.remove(LINESTYLE);
            }
        }
    }

    private void handleSourceArrow(EdgeStyle.Builder styleBuilder, List<IEdgeAppearanceChange> changes, Optional<EdgeStyle> optionalPreviousEdgeStyle, Set<String> customizedStyleProperties) {
        if (!handleResetChange(SOURCE_ARROW, changes, customizedStyleProperties)) {
            Optional<EdgeSourceArrowStyleAppearanceChange> optionalEdgeSourceArrowAppearanceChange = changes.stream()
                    .filter(EdgeSourceArrowStyleAppearanceChange.class::isInstance)
                    .map(EdgeSourceArrowStyleAppearanceChange.class::cast)
                    .findFirst();

            if (optionalEdgeSourceArrowAppearanceChange.isPresent()) {
                var newSourceArrow = optionalEdgeSourceArrowAppearanceChange.get().arrowStyle();
                styleBuilder.sourceArrow(newSourceArrow);
                customizedStyleProperties.add(SOURCE_ARROW);
            } else if (customizedStyleProperties.contains(SOURCE_ARROW) && optionalPreviousEdgeStyle.isPresent()) {
                var previousSourceArrow = optionalPreviousEdgeStyle.get().getSourceArrow();
                styleBuilder.sourceArrow(previousSourceArrow);
            } else {
                customizedStyleProperties.remove(SOURCE_ARROW);
            }
        }
    }

    private void handleTargetArrow(EdgeStyle.Builder styleBuilder, List<IEdgeAppearanceChange> changes, Optional<EdgeStyle> optionalPreviousEdgeStyle, Set<String> customizedStyleProperties) {
        if (!handleResetChange(TARGET_ARROW, changes, customizedStyleProperties)) {
            Optional<EdgeTargetArrowStyleAppearanceChange> optionalEdgeTargetArrowAppearanceChange = changes.stream()
                    .filter(EdgeTargetArrowStyleAppearanceChange.class::isInstance)
                    .map(EdgeTargetArrowStyleAppearanceChange.class::cast)
                    .findFirst();

            if (optionalEdgeTargetArrowAppearanceChange.isPresent()) {
                var newTargetArrow = optionalEdgeTargetArrowAppearanceChange.get().arrowStyle();
                styleBuilder.targetArrow(newTargetArrow);
                customizedStyleProperties.add(TARGET_ARROW);
            } else if (customizedStyleProperties.contains(TARGET_ARROW) && optionalPreviousEdgeStyle.isPresent()) {
                var previousTargetArrow = optionalPreviousEdgeStyle.get().getTargetArrow();
                styleBuilder.targetArrow(previousTargetArrow);
            } else {
                customizedStyleProperties.remove(TARGET_ARROW);
            }
        }
    }

    private boolean handleResetChange(String propertyName, List<IEdgeAppearanceChange> changes, Set<String> customizedStyleProperties) {
        boolean resetChange = changes.stream()
                .filter(ResetEdgeAppearanceChange.class::isInstance)
                .map(ResetEdgeAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), propertyName));

        if (resetChange) {
            customizedStyleProperties.remove(propertyName);
        }
        return resetChange;
    }
}