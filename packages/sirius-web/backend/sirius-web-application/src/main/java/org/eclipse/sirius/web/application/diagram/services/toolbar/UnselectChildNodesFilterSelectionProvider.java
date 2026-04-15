/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.diagram.services.toolbar;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramToolbarFilterSelectionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.FilterSelectionMenuItem;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Handler used to get the unselect child nodes menu item of the FilterSelectionMenu in the diagram toolbar.
 *
 * @author mcharfadi
 */
@Service
public class UnselectChildNodesFilterSelectionProvider implements IDiagramToolbarFilterSelectionProvider {

    public static final String ID = "unselect_child_nodes";

    @Override
    public FilterSelectionMenuItem getDiagramToolbarFilterSelectionMenuItem(IEditingContext editingContext, DiagramContext diagramContext, List<String> diagramElementIds) {
        return new FilterSelectionMenuItem(ID, "Unselect child nodes");
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, List<String> diagramElementIds) {
        return !diagramElementIds.isEmpty();
    }
}
