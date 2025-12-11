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
package org.eclipse.sirius.web.papaya.representations.dashboarddiagram.services;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.papaya.representations.dashboarddiagram.PapayaDashboardDiagramDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * A specific Palette provider for the Papaya Dashboard diagram.
 *
 * @author fbarbin
 */
@Service
public class PapayaPaletteProvider implements IPaletteProvider {
    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, List<String> diagramElementIds) {
        return PapayaDashboardDiagramDescriptionProvider.DASHBOARD_DESCRIPTION_ID.equals(diagramDescription.getId());
    }

    @Override
    public Palette handle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, List<Object> diagramElements) {
        return Palette.newPalette("papayaPalette")
                .quickAccessTools(List.of())
                .paletteEntries(List.of())
                .build();
    }
}
