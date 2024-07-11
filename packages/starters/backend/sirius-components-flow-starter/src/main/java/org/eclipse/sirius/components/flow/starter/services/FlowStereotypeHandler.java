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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.flow.starter.services.api.IEmptyFlowProvider;
import org.eclipse.sirius.components.flow.starter.services.api.IRobotFlowProvider;
import org.eclipse.sirius.web.application.document.dto.DocumentDTO;
import org.eclipse.sirius.web.application.document.services.api.IStereotypeHandler;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Used to create documents from a stereotype.
 *
 * @author sbegaudeau
 */
@Service
public class FlowStereotypeHandler implements IStereotypeHandler {

    private final IEmptyFlowProvider emptyFlowProvider;

    private final IRobotFlowProvider robotFlowProvider;

    public FlowStereotypeHandler(IEmptyFlowProvider emptyFlowProvider, IRobotFlowProvider robotFlowProvider) {
        this.emptyFlowProvider = Objects.requireNonNull(emptyFlowProvider);
        this.robotFlowProvider = Objects.requireNonNull(robotFlowProvider);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String stereotypeId) {
        return List.of(
                FlowStereotypeProvider.EMPTY_FLOW,
                FlowStereotypeProvider.ROBOT_FLOW
        ).contains(stereotypeId);
    }

    @Override
    public Optional<DocumentDTO> handle(IEditingContext editingContext, String stereotypeId, String name) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            return switch (stereotypeId) {
                case FlowStereotypeProvider.EMPTY_FLOW -> createEmptyFlowDocument(emfEditingContext, name);
                case FlowStereotypeProvider.ROBOT_FLOW -> createRobotFlowDocument(emfEditingContext, name);
                default -> Optional.empty();
            };
        }
        return Optional.empty();
    }

    private Optional<DocumentDTO> createEmptyFlowDocument(IEMFEditingContext editingContext, String name) {
        var documentId = this.emptyFlowProvider.addEmptyFlow(editingContext.getDomain().getResourceSet(), name);
        return Optional.of(new DocumentDTO(documentId, name, ExplorerDescriptionProvider.DOCUMENT_KIND));
    }

    private Optional<DocumentDTO> createRobotFlowDocument(IEMFEditingContext editingContext, String name) {
        var documentId = this.robotFlowProvider.addRobotFlow(editingContext.getDomain().getResourceSet(), name);
        return Optional.of(new DocumentDTO(documentId, name, ExplorerDescriptionProvider.DOCUMENT_KIND));
    }
}
