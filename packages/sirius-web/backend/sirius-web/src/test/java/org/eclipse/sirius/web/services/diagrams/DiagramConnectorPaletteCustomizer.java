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
package org.eclipse.sirius.web.services.diagrams;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.palette.api.IDiagramConnectorPaletteCustomizer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.palette.dto.IPaletteEntry;
import org.eclipse.sirius.components.palette.dto.ITool;
import org.eclipse.sirius.components.palette.dto.Palette;
import org.springframework.stereotype.Service;

/**
 * Used to customize the connector palette in the tests.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramConnectorPaletteCustomizer implements IDiagramConnectorPaletteCustomizer {

    public static final String CUSTOMIZED_DIAGRAM_CONNECTOR_TOOL_ID = "customized-diagram-connector-tool-id";

    public static final String CUSTOMIZED_DIAGRAM_CONNECTOR_TOOL_LABEL = "Customized diagram connector tool";

    @Override
    public boolean canHandle(IEditingContext editingContext, Diagram diagram, Object sourceDiagramElement, Object targetDiagramElement) {
        return true;
    }

    @Override
    public Palette customize(IEditingContext editingContext, Diagram diagram, Object sourceDiagramElement, Object targetDiagramElement, Palette palette) {
        List<ITool> quickAccessTools = new ArrayList<>();
        quickAccessTools.addAll(palette.quickAccessTools());

        var customizedTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(CUSTOMIZED_DIAGRAM_CONNECTOR_TOOL_ID)
                .label(CUSTOMIZED_DIAGRAM_CONNECTOR_TOOL_LABEL)
                .iconURL(List.of())
                .targetDescriptions(List.of())
                .keyBindings(List.of())
                .build();
        quickAccessTools.add(customizedTool);

        List<IPaletteEntry> paletteEntries = new ArrayList<>();
        paletteEntries.addAll(palette.paletteEntries());

        return Palette.newPalette(palette.id())
                .quickAccessTools(quickAccessTools)
                .paletteEntries(paletteEntries)
                .build();
    }
}
