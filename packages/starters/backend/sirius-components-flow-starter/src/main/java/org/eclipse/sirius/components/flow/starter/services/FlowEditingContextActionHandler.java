/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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


import static org.eclipse.sirius.components.flow.starter.services.FlowEditingContextActionProvider.EMPTY_FLOW_ID;
import static org.eclipse.sirius.components.flow.starter.services.FlowEditingContextActionProvider.ROBOT_FLOW_ID;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextActionHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.flow.starter.services.api.IEmptyFlowProvider;
import org.eclipse.sirius.components.flow.starter.services.api.IRobotFlowProvider;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;


/**
 * Handler used to perform an action on the editingContext.
 *
 * @author frouene
 */
@Service
public class FlowEditingContextActionHandler implements IEditingContextActionHandler {

    private final IEmptyFlowProvider emptyFlowProvider;

    private final IRobotFlowProvider robotFlowProvider;

    public FlowEditingContextActionHandler(IEmptyFlowProvider emptyFlowProvider, IRobotFlowProvider robotFlowProvider) {
        this.emptyFlowProvider = Objects.requireNonNull(emptyFlowProvider);
        this.robotFlowProvider = Objects.requireNonNull(robotFlowProvider);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String actionId) {
        return List.of(EMPTY_FLOW_ID, ROBOT_FLOW_ID).contains(actionId);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, String actionId) {
        return Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getResourceSet)
                .map(resourceSet -> this.performActionOnResourceSet(resourceSet, actionId))
                .orElse(new Failure("Something went wrong while handling this action."));
    }

    private IStatus performActionOnResourceSet(ResourceSet resourceSet, String actionId) {
        return switch (actionId) {
            case EMPTY_FLOW_ID -> this.createEmptyFlowResource(resourceSet);
            case ROBOT_FLOW_ID -> this.createRobotFlowResource(resourceSet);
            default -> new Failure("Unknown action.");
        };
    }

    private IStatus createEmptyFlowResource(ResourceSet resourceSet) {
        this.emptyFlowProvider.addEmptyFlow(resourceSet, "Flow");
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
    }


    private IStatus createRobotFlowResource(ResourceSet resourceSet) {
        this.robotFlowProvider.addRobotFlow(resourceSet, "Robot Flow");
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
    }
}
