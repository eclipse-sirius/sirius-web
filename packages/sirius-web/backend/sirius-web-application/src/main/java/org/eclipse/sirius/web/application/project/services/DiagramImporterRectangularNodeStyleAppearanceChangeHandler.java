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
            if (RectangularNodeAppearanceHandler.BACKGROUND.equals(customizedStyleProperty)) {
                return Optional.of(new NodeBackgroundAppearanceChange(nodeId, rectangularNodeStyle.getBackground()));
            }
        }
        return Optional.empty();
    }
}
