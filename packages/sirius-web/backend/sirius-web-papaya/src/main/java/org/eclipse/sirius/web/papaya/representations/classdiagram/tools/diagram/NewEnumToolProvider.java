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

import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.EnumNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.dialogs.TypeContainerSelectionDialogDescriptionProvider;

/**
 * Used to create a new enum.
 *
 * @author sbegaudeau
 */
public class NewEnumToolProvider {
    public NodeTool getTool(IViewDiagramElementFinder cache) {
        return new DiagramBuilders().newNodeTool()
                .name("New enum")
                .iconURLsExpression("/icons/papaya/full/obj16/Enum.svg")
                .dialogDescription(new TypeContainerSelectionDialogDescriptionProvider().getDialog("enum"))
                .body(
                        new ViewBuilders().newIf()
                                .conditionExpression("aql:selectedObject <> null")
                                .children(
                                        new ViewBuilders().newChangeContext()
                                                .expression("aql:selectedObject")
                                                .children(
                                                        this.createInstance(cache)
                                                )
                                                .build()
                                )
                                .build(),
                        new ViewBuilders().newIf()
                                .conditionExpression("aql:selectedObject = null and self.hasTypeContainer()")
                                .children(
                                        new ViewBuilders().newChangeContext()
                                                .expression("aql:self.getFirstTypeContainer()")
                                                .children(
                                                        this.createInstance(cache)
                                                )
                                                .build()
                                )
                                .build()
                )
                .description("This Tool allows to create a new Papaya::Enum concept.")
                .build();
    }

    private CreateInstance createInstance(IViewDiagramElementFinder cache) {
        var enumNodeDescription = cache.getNodeDescription(EnumNodeDescriptionProvider.NAME).orElse(null);

        return new ViewBuilders().newCreateInstance()
                .referenceName("types")
                .typeName("papaya::Enum")
                .variableName("newEnum")
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:newEnum")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression("NewEnum")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("visibility")
                                                .valueExpression("aql:papaya::Visibility::PUBLIC")
                                                .build()
                                )
                                .build(),
                        new DiagramBuilders().newCreateView()
                                .elementDescription(enumNodeDescription)
                                .semanticElementExpression("aql:newEnum")
                                .parentViewExpression("aql:selectedNode")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();
    }
}
