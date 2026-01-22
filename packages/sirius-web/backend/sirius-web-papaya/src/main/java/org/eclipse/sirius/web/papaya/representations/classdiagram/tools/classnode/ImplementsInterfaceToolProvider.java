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
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;

/**
 * Used to create the "implements interface" tool.
 *
 * @author sbegaudeau
 */
public class ImplementsInterfaceToolProvider {

    public EdgeTool getTool(IViewDiagramElementFinder cache) {
        var interfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME).orElse(null);

        return new DiagramBuilders().newEdgeTool()
                .name("Implements interface")
                .targetElementDescriptions(interfaceNodeDescription)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticEdgeSource")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("implements")
                                                .valueExpression("aql:semanticEdgeTarget")
                                                .build()
                                )
                                .build()
                )
                .description("This Tool allows to create a new implements relationship between two interfaces.")
                .build();
    }
}
