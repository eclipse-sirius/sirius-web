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
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.RecordNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.dialogs.TypeContainerSelectionDialogDescriptionProvider;

/**
 * Used to create a new record.
 *
 * @author sbegaudeau
 */
public class NewRecordToolProvider {

    public NodeTool getTool(IViewDiagramElementFinder cache) {
        return new DiagramBuilders().newNodeTool()
                .name("New record")
                .iconURLsExpression("/icons/papaya/full/obj16/Record.svg")
                .dialogDescription(new TypeContainerSelectionDialogDescriptionProvider().getDialog("record"))
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
                .description("This Tool allows to create a new Papaya::Record concept.")
                .build();
    }

    private CreateInstance createInstance(IViewDiagramElementFinder cache) {
        var recordNodeDescription = cache.getNodeDescription(RecordNodeDescriptionProvider.NAME).orElse(null);

        return new ViewBuilders().newCreateInstance()
                .referenceName("types")
                .typeName("papaya::Record")
                .variableName("newRecord")
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:newRecord")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression("NewRecord")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("visibility")
                                                .valueExpression("aql:papaya::Visibility::PUBLIC")
                                                .build()
                                )
                                .build(),
                        new DiagramBuilders().newCreateView()
                                .elementDescription(recordNodeDescription)
                                .semanticElementExpression("aql:newRecord")
                                .parentViewExpression("aql:selectedNode")
                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                .build()
                )
                .build();
    }
}
