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
package org.eclipse.sirius.web.papaya.representations.classdiagram.tools.interfacenode;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.diagram.NodePalette;

/**
 * Used to create the palette of the interface node.
 *
 * @author sbegaudeau
 */
public class InterfaceNodePaletteProvider {
    public NodePalette getNodePalette(IViewDiagramElementFinder cache) {
        var importImplementationTool = new ImportInterfaceImplementationsNodeToolProvider().create(cache);
        var importAllSubtypes = new ImportAllInterfaceSubtypesNodeToolProvider().create(cache);

        var importExistingTypesToolSection = new DiagramBuilders().newNodeToolSection()
                .name("Import existing types")
                .nodeTools(
                        importImplementationTool,
                        importAllSubtypes
                )
                .build();

        return new DiagramBuilders().newNodePalette()
                .toolSections(importExistingTypesToolSection)
                .build();
    }
}
