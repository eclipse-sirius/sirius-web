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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.flow.starter.services.api.IFlowCapablePredicate;
import org.eclipse.sirius.web.application.document.dto.Stereotype;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeProvider;
import org.springframework.stereotype.Service;

/**
 * Used to return the list of stereotypes to create documents.
 *
 * @author sbegaudeau
 */
@Service
public class FlowStereotypeProvider implements IStereotypeProvider {

    public static final String EMPTY_FLOW = "empty_flow";

    public static final String ROBOT_FLOW = "robot_flow";

    private final IFlowCapablePredicate flowCapablePredicate;

    public FlowStereotypeProvider(IFlowCapablePredicate flowCapablePredicate) {
        this.flowCapablePredicate = Objects.requireNonNull(flowCapablePredicate);
    }

    @Override
    public List<Stereotype> getStereotypes(IEditingContext editingContext) {
        var isFlowProject = this.flowCapablePredicate.test(editingContext.getId());

        if (isFlowProject) {
            return List.of(
                    new Stereotype(EMPTY_FLOW, "Flow"),
                    new Stereotype(ROBOT_FLOW, "Robot Flow")
            );
        }
        return List.of();
    }
}
