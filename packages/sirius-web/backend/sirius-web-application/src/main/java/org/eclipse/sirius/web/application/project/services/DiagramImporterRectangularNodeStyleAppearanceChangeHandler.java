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
package org.eclipse.sirius.web.application.project.services;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.RectangularNodeAppearanceHandler;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderStyleAppearanceChange;
import org.eclipse.sirius.web.application.project.services.api.IDiagramImporterNodeStyleAppearanceChangeHandler;
import org.springframework.stereotype.Service;

/**
 * Implementation to handle rectangular node style.
 *
 * @author frouene
 */
@Service
public class DiagramImporterRectangularNodeStyleAppearanceChangeHandler implements IDiagramImporterNodeStyleAppearanceChangeHandler {

    @Override
    public boolean canHandle(INodeStyle nodeStyle) {
        return nodeStyle instanceof RectangularNodeStyle;
    }

    @Override
    public Optional<IAppearanceChange> handle(String nodeId, INodeStyle nodeStyle, String customizedStyleProperty) {
        if (nodeStyle instanceof RectangularNodeStyle rectangularNodeStyle) {
            return switch (customizedStyleProperty) {
                case RectangularNodeAppearanceHandler.BACKGROUND -> Optional.of(new NodeBackgroundAppearanceChange(nodeId, rectangularNodeStyle.getBackground()));
                case RectangularNodeAppearanceHandler.BORDER_COLOR -> Optional.of(new NodeBorderColorAppearanceChange(nodeId, rectangularNodeStyle.getBorderColor()));
                case RectangularNodeAppearanceHandler.BORDER_RADIUS -> Optional.of(new NodeBorderRadiusAppearanceChange(nodeId, rectangularNodeStyle.getBorderRadius()));
                case RectangularNodeAppearanceHandler.BORDER_SIZE -> Optional.of(new NodeBorderSizeAppearanceChange(nodeId, rectangularNodeStyle.getBorderSize()));
                case RectangularNodeAppearanceHandler.BORDER_STYLE -> Optional.of(new NodeBorderStyleAppearanceChange(nodeId, rectangularNodeStyle.getBorderStyle()));
                default -> Optional.empty();
            };
        }
        return Optional.empty();
    }
}
