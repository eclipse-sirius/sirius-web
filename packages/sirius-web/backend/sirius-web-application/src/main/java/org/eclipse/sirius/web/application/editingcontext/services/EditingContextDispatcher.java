/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityEvaluator;
import org.eclipse.sirius.web.application.library.services.api.ILibraryEditingContextService;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 *
 * Dispatch the given input for queries and mutations.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextDispatcher implements IEditingContextDispatcher {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IProjectEditingContextService projectEditingContextService;

    private final ILibraryEditingContextService libraryEditingContextService;

    private final ICapabilityEvaluator capabilityEvaluator;

    private final IMessageService messageService;

    public EditingContextDispatcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IProjectEditingContextService projectEditingContextService,
                                    ILibraryEditingContextService libraryEditingContextService, ICapabilityEvaluator capabilityEvaluator, IMessageService messageService) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
        this.libraryEditingContextService = Objects.requireNonNull(libraryEditingContextService);
        this.capabilityEvaluator = Objects.requireNonNull(capabilityEvaluator);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public Mono<IPayload> dispatchQuery(String editingContextId, IInput input) {
        var canView = false;
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContextId);
        if (optionalProjectId.isPresent()) {
            canView = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, optionalProjectId.get(), SiriusWebCapabilities.Project.VIEW);
        } else {
            var optionalLibraryId = this.libraryEditingContextService.getLibraryIdentifier(editingContextId);
            if (optionalLibraryId.isPresent()) {
                canView = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.LIBRARY, optionalLibraryId.get(), SiriusWebCapabilities.Library.VIEW);
            }
        }

        if (!canView) {
            return Mono.just(new ErrorPayload(input.id(), this.messageService.unauthorized()));
        }

        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .defaultIfEmpty(new ErrorPayload(input.id(), this.messageService.unexpectedError()));
    }

    @Override
    public Mono<IPayload> dispatchMutation(String editingContextId, IInput input) {
        var canView = false;
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContextId);
        if (optionalProjectId.isPresent()) {
            canView = this.capabilityEvaluator.hasCapability(SiriusWebCapabilities.PROJECT, optionalProjectId.get(), SiriusWebCapabilities.Project.EDIT);
        } else {
            var optionalLibraryId = this.libraryEditingContextService.getLibraryIdentifier(editingContextId);
            canView = optionalLibraryId.isEmpty();
        }

        if (!canView) {
            return Mono.just(new ErrorPayload(input.id(), this.messageService.unauthorized()));
        }

        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .defaultIfEmpty(new ErrorPayload(input.id(), this.messageService.unexpectedError()));
    }
}
