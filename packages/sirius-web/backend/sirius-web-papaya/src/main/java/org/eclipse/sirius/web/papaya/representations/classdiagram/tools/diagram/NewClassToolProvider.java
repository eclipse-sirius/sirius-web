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
package org.eclipse.sirius.web.papaya.representations.classdiagram.tools.diagram;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.dialogs.TypeContainerSelectionDialogDescriptionProvider;

/**
 * Used to create a new class.
 *
 * @author sbegaudeau
 */
public class NewClassToolProvider {

    public NodeTool getTool(IViewDiagramElementFinder cache) {
        var classNodeDescription = cache.getNodeDescription(ClassNodeDescriptionProvider.NAME).orElse(null);

        var createInstance = new ViewBuilders().newCreateInstance()
                .referenceName("types")
                .typeName("papaya::Class")
                .variableName("newClass")
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:newClass")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression("NewClass")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("visibility")
                                                .valueExpression("aql:papaya::Visibility::PUBLIC")
                                                .build()
                                )
                                .build(),
                        new DiagramBuilders().newCreateView()
                                .elementDescription(classNodeDescription)
                                .semanticElementExpression("aql:newClass")
                                .parentViewExpression("aql:selectedNode")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        return new DiagramBuilders().newNodeTool()
                .name("New class")
                .iconURLsExpression("/icons/papaya/full/obj16/Class.svg")
                .dialogDescription(new TypeContainerSelectionDialogDescriptionProvider().getDialog())
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:selectedObject")
                                .children(
                                        createInstance
                                )
                                .build()
                )
                .description("This Tool allows to create a new Papaya::Class concept.")
                .build();
    }
}
