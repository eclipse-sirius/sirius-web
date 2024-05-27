/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.componentdiagram.tools;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.nodedescriptions.ComponentNodeDescriptionProvider;

/**
 * Used to provide the drop tool of the diagram.
 *
 * @author sbegaudeau
 */
public class ComponentDiagramDropToolProvider {

    public DropTool getDropTool(IViewDiagramElementFinder cache) {
        var componentNodeDescription = cache.getNodeDescription(ComponentNodeDescriptionProvider.NAME).orElse(null);

        return new DiagramBuilders().newDropTool()
                .name("Drop on diagram")
                .body(
                        new ViewBuilders().newIf()
                                .conditionExpression("aql:self.eClass() = papaya::Component")
                                .children(
                                        new DiagramBuilders().newCreateView()
                                                .elementDescription(componentNodeDescription)
                                                .semanticElementExpression("aql:self")
                                                .parentViewExpression("aql:selectedNode")
                                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
