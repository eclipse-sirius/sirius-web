/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.classdiagram.tools.classnode;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;

/**
 * Used to create the palette of the class node.
 *
 * @author sbegaudeau
 */
public class ClassNodePaletteProvider {

    public NodePalette getNodePalette(IViewDiagramElementFinder cache) {
        var newAttributeTool = new NewAttributeToolProvider().getTool(cache);
        var newOperationTool = new NewOperationToolProvider().getTool(cache);

        var newMembersToolSection = new DiagramBuilders().newNodeToolSection()
                .name("New members")
                .nodeTools(
                        newAttributeTool,
                        newOperationTool
                )
                .build();

        return new DiagramBuilders().newNodePalette()
                .toolSections(newMembersToolSection)
                .build();
    }

    public EdgeTool getConnectorTool(IViewDiagramElementFinder cache) {
        var classNodeDescription = cache.getNodeDescription(ClassNodeDescriptionProvider.NAME).orElse(null);
        var interfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME).orElse(null);

        return new DiagramBuilders().newEdgeTool()
                .name("Class relationships")
                .targetElementDescriptions(classNodeDescription, interfaceNodeDescription)
                .palette(new DiagramBuilders().newNodePalette()
                        .nodeTools(new ExtendsClassToolProvider().getTool(), new ImplementsInterfaceToolProvider().getTool())
                        .build())
                .build();
    }

}
