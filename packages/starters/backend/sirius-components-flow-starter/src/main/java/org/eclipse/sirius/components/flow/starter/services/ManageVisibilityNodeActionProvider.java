/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.api.IActionsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Action;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.flow.starter.services.api.IFlowCapablePredicate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to provide the show label node action.
 *
 * @author mcharfadi
 */
@Service
public class ManageVisibilityNodeActionProvider implements IActionsProvider {

    private static final String ACTION_ID = "siriusweb_manage_visibility";

    private final IFlowCapablePredicate flowCapablePredicate;

    public ManageVisibilityNodeActionProvider(IFlowCapablePredicate flowCapablePredicate) {
        this.flowCapablePredicate = Objects.requireNonNull(flowCapablePredicate);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canHandle(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElement diagramElement) {
        var isFlowProject = this.flowCapablePredicate.test(editingContext.getId());

        return isFlowProject && diagramElement instanceof Node node && !node.getChildNodes().isEmpty();
    }

    @Override
    public List<Action> handle(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElement diagramElement) {
        return List.of(new Action(ACTION_ID, List.of(), ""));
    }
}
