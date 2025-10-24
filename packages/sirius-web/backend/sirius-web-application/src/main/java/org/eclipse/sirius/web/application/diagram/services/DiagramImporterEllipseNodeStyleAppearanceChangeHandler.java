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
package org.eclipse.sirius.web.application.diagram.services;

import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderStyleAppearanceChange;
import org.eclipse.sirius.web.application.diagram.EllipseNodeStyle;
import org.eclipse.sirius.web.application.project.services.api.IDiagramImporterNodeStyleAppearanceChangeHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation to handle ellipse node style.
 *
 * @author mcharfadi
 */
@Service
public class DiagramImporterEllipseNodeStyleAppearanceChangeHandler implements IDiagramImporterNodeStyleAppearanceChangeHandler {

    @Override
    public boolean canHandle(INodeStyle nodeStyle) {
        return nodeStyle instanceof EllipseNodeStyle;
    }

    @Override
    public Optional<IAppearanceChange> handle(String nodeId, INodeStyle nodeStyle, String customizedStyleProperty) {
        if (nodeStyle instanceof EllipseNodeStyle ellipseNodeStyle) {
            return switch (customizedStyleProperty) {
                case EllipseNodeAppearanceHandler.BACKGROUND -> Optional.of(new NodeBackgroundAppearanceChange(nodeId, ellipseNodeStyle.getBackground()));
                case EllipseNodeAppearanceHandler.BORDER_COLOR -> Optional.of(new NodeBorderColorAppearanceChange(nodeId, ellipseNodeStyle.getBorderColor()));
                case EllipseNodeAppearanceHandler.BORDER_SIZE -> Optional.of(new NodeBorderSizeAppearanceChange(nodeId, ellipseNodeStyle.getBorderSize()));
                case EllipseNodeAppearanceHandler.BORDER_STYLE -> Optional.of(new NodeBorderStyleAppearanceChange(nodeId, ellipseNodeStyle.getBorderStyle()));
                default -> Optional.empty();
            };
        }
        return Optional.empty();
    }
}
