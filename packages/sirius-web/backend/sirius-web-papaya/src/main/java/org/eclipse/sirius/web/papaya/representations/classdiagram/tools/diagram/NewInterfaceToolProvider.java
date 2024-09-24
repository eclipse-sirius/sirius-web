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
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.dialogs.TypeContainerSelectionDialogDescriptionProvider;

/**
 * Used to create a new interface.
 *
 * @author sbegaudeau
 */
public class NewInterfaceToolProvider {

    public NodeTool getTool(IViewDiagramElementFinder cache) {
        var interfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME).orElse(null);

        var createInstance = new ViewBuilders().newCreateInstance()
                .referenceName("types")
                .typeName("papaya::Interface")
                .variableName("newInterface")
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:newInterface")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression("NewInterface")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("visibility")
                                                .valueExpression("aql:papaya::Visibility::PUBLIC")
                                                .build()
                                )
                                .build(),
                        new DiagramBuilders().newCreateView()
                                .elementDescription(interfaceNodeDescription)
                                .semanticElementExpression("aql:newInterface")
                                .parentViewExpression("aql:selectedNode")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        return new DiagramBuilders().newNodeTool()
                .name("New interface")
                .iconURLsExpression("/icons/full/obj16/Interface.svg")
                .dialogDescription(new TypeContainerSelectionDialogDescriptionProvider().getDialog())
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:selectedObject")
                                .children(
                                        createInstance
                                )
                                .build()
                )
                .build();
    }
}
