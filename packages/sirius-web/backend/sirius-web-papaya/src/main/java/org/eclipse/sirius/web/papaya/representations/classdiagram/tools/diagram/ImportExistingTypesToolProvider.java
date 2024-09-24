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
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.EnumNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.RecordNodeDescriptionProvider;

/**
 * Used to provide the tool used to import existing types.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ImportExistingTypesToolProvider {

    public NodeTool getNodeTool(IViewDiagramElementFinder cache) {
        var classNodeDescription = cache.getNodeDescription(ClassNodeDescriptionProvider.NAME).orElse(null);
        var interfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME).orElse(null);
        var enumNodeDescription = cache.getNodeDescription(EnumNodeDescriptionProvider.NAME).orElse(null);
        var recordNodeDescription = cache.getNodeDescription(RecordNodeDescriptionProvider.NAME).orElse(null);

        var treeDescription = new DiagramBuilders().newSelectionDialogTreeDescription()
                .elementsExpression("aql:self.eResource().getResourceSet().getResources()")
                .childrenExpression("aql:self.getChildren()")
                .isSelectableExpression("aql:self.oclIsKindOf(papaya::Type)")
                .build();

        var dialogDescription = new DiagramBuilders().newSelectionDialogDescription()
                .selectionMessage("Select the types to import")
                .selectionDialogTreeDescription(treeDescription)
                .build();

        var ifClass = new ViewBuilders().newIf()
                .conditionExpression("aql:selectedObject.eClass() = papaya::Class")
                .children(
                        new DiagramBuilders().newCreateView()
                                .elementDescription(classNodeDescription)
                                .semanticElementExpression("aql:selectedObject")
                                .parentViewExpression("aql:selectedNode")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        var ifInterface = new ViewBuilders().newIf()
                .conditionExpression("aql:selectedObject.eClass() = papaya::Interface")
                .children(
                        new DiagramBuilders().newCreateView()
                                .elementDescription(interfaceNodeDescription)
                                .semanticElementExpression("aql:selectedObject")
                                .parentViewExpression("aql:selectedNode")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        var ifRecord = new ViewBuilders().newIf()
                .conditionExpression("aql:selectedObject.eClass() = papaya::Record")
                .children(
                        new DiagramBuilders().newCreateView()
                                .elementDescription(recordNodeDescription)
                                .semanticElementExpression("aql:selectedObject")
                                .parentViewExpression("aql:selectedNode")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        var ifEnum = new ViewBuilders().newIf()
                .conditionExpression("aql:selectedObject.eClass() = papaya::Enum")
                .children(
                        new DiagramBuilders().newCreateView()
                                .elementDescription(enumNodeDescription)
                                .semanticElementExpression("aql:selectedObject")
                                .parentViewExpression("aql:selectedNode")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();

        return new DiagramBuilders().newNodeTool()
                .name("Import existing types")
                .iconURLsExpression("/icons/full/obj16/Class.svg")
                .dialogDescription(dialogDescription)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        ifClass,
                                        ifInterface,
                                        ifRecord,
                                        ifEnum
                                )
                                .build()
                )
                .build();
    }
}
