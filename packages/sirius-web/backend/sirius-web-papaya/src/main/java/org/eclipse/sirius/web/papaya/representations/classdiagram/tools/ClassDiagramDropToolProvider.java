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
package org.eclipse.sirius.web.papaya.representations.classdiagram.tools;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.EnumNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.RecordNodeDescriptionProvider;

/**
 * Used to provide the drop tool of the diagram.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ClassDiagramDropToolProvider {

    public DropTool getDropTool(IViewDiagramElementFinder cache) {
        var classNodeDescription = cache.getNodeDescription(ClassNodeDescriptionProvider.NAME).orElse(null);
        var interfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME).orElse(null);
        var enumNodeDescription = cache.getNodeDescription(EnumNodeDescriptionProvider.NAME).orElse(null);
        var recordNodeDescription = cache.getNodeDescription(RecordNodeDescriptionProvider.NAME).orElse(null);

        return new DiagramBuilders().newDropTool()
                .name("Drop on diagram")
                .body(
                        new ViewBuilders().newIf()
                                .conditionExpression("aql:self.eClass() = papaya::Class")
                                .children(
                                        new DiagramBuilders().newCreateView()
                                                .elementDescription(classNodeDescription)
                                                .semanticElementExpression("aql:self")
                                                .parentViewExpression("aql:selectedNode")
                                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                                .build()
                                )
                                .build(),
                        new ViewBuilders().newIf()
                                .conditionExpression("aql:self.eClass() = papaya::Interface")
                                .children(
                                        new DiagramBuilders().newCreateView()
                                                .elementDescription(interfaceNodeDescription)
                                                .semanticElementExpression("aql:self")
                                                .parentViewExpression("aql:selectedNode")
                                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                                .build()
                                )
                                .build(),
                        new ViewBuilders().newIf()
                                .conditionExpression("aql:self.eClass() = papaya::Record")
                                .children(
                                        new DiagramBuilders().newCreateView()
                                                .elementDescription(recordNodeDescription)
                                                .semanticElementExpression("aql:self")
                                                .parentViewExpression("aql:selectedNode")
                                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                                .build()
                                )
                                .build(),
                        new ViewBuilders().newIf()
                                .conditionExpression("aql:self.eClass() = papaya::Enum")
                                .children(
                                        new DiagramBuilders().newCreateView()
                                                .elementDescription(enumNodeDescription)
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
