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
import org.eclipse.sirius.components.view.builder.providers.INodeToolProvider;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.web.papaya.representations.componentdiagram.nodedescriptions.ComponentNodeDescriptionProvider;

/**
 * Used to create a component.
 *
 * @author sbegaudeau
 */
public class CreateComponentNodeToolProvider implements INodeToolProvider {
    @Override
    public NodeTool create(IViewDiagramElementFinder cache) {
        var componentNodeDescription = cache.getNodeDescription(ComponentNodeDescriptionProvider.NAME).orElse(null);

        var createNewComponent = new ViewBuilders().newCreateInstance()
                .typeName("papaya:Component")
                .referenceName("components")
                .variableName("newComponent")
                .children(
                        new ViewBuilders().newSetValue()
                                .featureName("components")
                                .valueExpression("aql:newComponent")
                                .children(
                                        new DiagramBuilders().newCreateView()
                                                .elementDescription(componentNodeDescription)
                                                .semanticElementExpression("aql:newComponent")
                                                .parentViewExpression("aql:selectedNode")
                                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                                .build()
                                )
                                .build()
                )
                .build();

        return new DiagramBuilders().newNodeTool()
                .name("New component")
                .iconURLsExpression("/icons/full/obj16/Component.svg")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(createNewComponent)
                                .build()
                )
                .build();
    }
}
