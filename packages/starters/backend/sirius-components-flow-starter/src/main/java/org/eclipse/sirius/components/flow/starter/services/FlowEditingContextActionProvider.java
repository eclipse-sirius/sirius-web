/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IEditingContextActionProvider;
import org.eclipse.sirius.components.collaborative.dto.EditingContextAction;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.flow.starter.services.api.IFlowCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Provides the list of possible actions on the editingContext.
 *
 * @author frouene
 */
@Service
public class FlowEditingContextActionProvider implements IEditingContextActionProvider {

    public static final String EMPTY_FLOW_ID = "empty_flow";

    public static final String ROBOT_FLOW_ID = "robot_flow";

    private static final EditingContextAction EMPTY_FLOW_EDITING_CONTEXT_ACTION = new EditingContextAction(EMPTY_FLOW_ID, "Flow");

    private static final EditingContextAction ROBOT_FLOW_EDITING_CONTEXT_ACTION = new EditingContextAction(ROBOT_FLOW_ID, "Robot Flow");

    private final IFlowCapableEditingContextPredicate flowCapableEditingContextPredicate;

    public FlowEditingContextActionProvider(IFlowCapableEditingContextPredicate flowCapableEditingContextPredicate) {
        this.flowCapableEditingContextPredicate = Objects.requireNonNull(flowCapableEditingContextPredicate);
    }

    @Override
    public List<EditingContextAction> getEditingContextAction(IEditingContext editingContext) {
        var isFlowProject = this.flowCapableEditingContextPredicate.test(editingContext.getId());

        if (isFlowProject) {
            return List.of(EMPTY_FLOW_EDITING_CONTEXT_ACTION, ROBOT_FLOW_EDITING_CONTEXT_ACTION);
        }

        return List.of();
    }
}
