/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.api.IEditingContextActionProvider;
import org.eclipse.sirius.components.collaborative.dto.EditingContextAction;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
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

    public static final String BIG_GUY_FLOW_ID = "big_guy_flow";


    private static final EditingContextAction EMPTY_FLOW_EDITING_CONTEXT_ACTION = new EditingContextAction(EMPTY_FLOW_ID, "Flow");

    private static final EditingContextAction ROBOT_FLOW_EDITING_CONTEXT_ACTION = new EditingContextAction(ROBOT_FLOW_ID, "Robot Flow");

    private static final EditingContextAction BIG_GUY_FLOW_EDITING_CONTEXT_ACTION = new EditingContextAction(BIG_GUY_FLOW_ID, "Big Guy Flow (17k elements)");


    @Override
    public List<EditingContextAction> getEditingContextAction(IEditingContext editingContext) {
        var actions = new ArrayList<EditingContextAction>();
        if (editingContext instanceof EditingContext) {
            actions.add(EMPTY_FLOW_EDITING_CONTEXT_ACTION);
            actions.add(ROBOT_FLOW_EDITING_CONTEXT_ACTION);
            actions.add(BIG_GUY_FLOW_EDITING_CONTEXT_ACTION);
        }
        return actions;
    }
}
