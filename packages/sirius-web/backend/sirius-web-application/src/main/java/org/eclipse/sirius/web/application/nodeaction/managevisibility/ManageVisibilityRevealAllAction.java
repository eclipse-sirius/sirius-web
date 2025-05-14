/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.nodeaction.managevisibility;

import org.eclipse.sirius.components.collaborative.diagrams.api.nodeactions.IManageVisibilityMenuActionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.ManageVisibilityAction;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Used to provide the reveal all action for the manage visibility node action.
 *
 * @author mcharfadi
 */
@Service
public class ManageVisibilityRevealAllAction implements IManageVisibilityMenuActionProvider {

    public static final String ACTION_ID = "manage_visibility_menu_reveal_all_action";

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElement diagramElement) {
        return diagramElement instanceof Node node && !node.getChildNodes().isEmpty();
    }

    @Override
    public List<ManageVisibilityAction> handle(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElement diagramElement) {
        return List.of(new ManageVisibilityAction(ACTION_ID, "Reveal all"));
    }
}
