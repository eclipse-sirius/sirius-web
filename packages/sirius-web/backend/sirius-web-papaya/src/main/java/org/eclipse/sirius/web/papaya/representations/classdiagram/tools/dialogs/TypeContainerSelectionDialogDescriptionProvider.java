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
package org.eclipse.sirius.web.papaya.representations.classdiagram.tools.dialogs;

import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;

/**
 * Used to create a dialog description to find a type container.
 *
 * @author sbegaudeau
 */
public class TypeContainerSelectionDialogDescriptionProvider {

    public SelectionDialogDescription getDialog() {
        var treeDescription = new DiagramBuilders().newSelectionDialogTreeDescription()
                .elementsExpression("aql:self.eResource().getResourceSet().getResources()")
                .childrenExpression("aql:self.getChildren()")
                .isSelectableExpression("aql:self.oclIsKindOf(papaya::Package) or self.oclIsKindOf(papaya::Type)")
                .build();

        return new DiagramBuilders().newSelectionDialogDescription()
                .selectionMessage("Select the container of the new type")
                .selectionDialogTreeDescription(treeDescription)
                .build();
    }
}
