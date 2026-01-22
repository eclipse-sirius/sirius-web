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
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.dialogs.TypeSelectionDialogDescriptionProvider;

/**
 * Used to create a new operation.
 *
 * @author sbegaudeau
 */
public class NewOperationToolProvider {
    public NodeTool getTool(IViewDiagramElementFinder cache) {
        var setType = new ViewBuilders().newChangeContext()
                .expression("aql:newGenericType")
                .children(
                        new ViewBuilders().newSetValue()
                                .featureName("rawType")
                                .valueExpression("aql:selectedObject")
                                .build()
                )
                .build();

        var createInstance = new ViewBuilders().newCreateInstance()
                .referenceName("operations")
                .typeName("papaya::Operation")
                .variableName("newOperation")
                .children(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:newOperation")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("name")
                                                .valueExpression("newOperation")
                                                .build(),
                                        new ViewBuilders().newSetValue()
                                                .featureName("visibility")
                                                .valueExpression("aql:papaya::Visibility::PRIVATE")
                                                .build(),
                                        new ViewBuilders().newCreateInstance()
                                                .referenceName("type")
                                                .typeName("papaya::GenericType")
                                                .variableName("newGenericType")
                                                .children(
                                                        setType
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        return new DiagramBuilders().newNodeTool()
                .name("New operation")
                .iconURLsExpression("/icons/papaya/full/obj16/Operation.svg")
                .dialogDescription(new TypeSelectionDialogDescriptionProvider().getDialog())
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        createInstance
                                )
                                .build()
                )
                .description("This Tool allows to create a new operation in the class.")
                .build();
    }
}
