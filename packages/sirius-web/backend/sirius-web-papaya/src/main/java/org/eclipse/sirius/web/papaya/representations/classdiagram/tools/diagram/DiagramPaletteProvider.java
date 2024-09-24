/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.classdiagram.tools.diagram;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;

/**
 * Used to create the palette of the diagram.
 *
 * @author sbegaudeau
 */
public class DiagramPaletteProvider {
    public DiagramPalette getDiagramPalette(IViewDiagramElementFinder cache) {
        var newClassTool = new NewClassToolProvider().getTool(cache);
        var newInterfaceTool = new NewInterfaceToolProvider().getTool(cache);
        var newRecordTool = new NewRecordToolProvider().getTool(cache);
        var newEnumTool = new NewEnumToolProvider().getTool(cache);

        var newTypesToolSection = new DiagramBuilders().newDiagramToolSection()
                .name("New types")
                .nodeTools(
                        newClassTool,
                        newInterfaceTool,
                        newRecordTool,
                        newEnumTool
                )
                .build();

        var importExistingTypesTool = new ImportExistingTypesToolProvider().getNodeTool(cache);
        var dropTool = new ClassDiagramDropToolProvider().getDropTool(cache);

        return new DiagramBuilders().newDiagramPalette()
                .toolSections(newTypesToolSection)
                .nodeTools(importExistingTypesTool)
                .dropTool(dropTool)
                .build();
    }
}
