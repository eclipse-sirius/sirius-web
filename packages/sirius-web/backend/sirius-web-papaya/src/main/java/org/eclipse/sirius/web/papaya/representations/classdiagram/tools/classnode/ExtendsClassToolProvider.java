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
package org.eclipse.sirius.web.papaya.representations.classdiagram.tools.classnode;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.ClassNodeDescriptionProvider;

/**
 * Used to create the "extends class" tool.
 *
 * @author sbegaudeau
 */
public class ExtendsClassToolProvider {

    public EdgeTool getTool(IViewDiagramElementFinder cache) {
        var classNodeDescription = cache.getNodeDescription(ClassNodeDescriptionProvider.NAME).orElse(null);

        return new DiagramBuilders().newEdgeTool()
                .name("Extends class")
                .targetElementDescriptions(classNodeDescription)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticEdgeSource")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("extends")
                                                .valueExpression("aql:semanticEdgeTarget")
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
