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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFluxCustomizer;
import org.eclipse.sirius.components.collaborative.representations.api.IEventProcessorSubscriptionSchedulerProvider;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IEventProcessorSubscriptionProvider;
import org.eclipse.sirius.web.application.capability.SiriusWebCapabilities;
import org.eclipse.sirius.web.application.capability.services.CapabilityVote;
import org.eclipse.sirius.web.application.capability.services.api.ICapabilityVoter;
import org.eclipse.sirius.web.application.library.services.api.ILibraryEditingContextService;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to provide the subscription of a representation in an editing context.
 *
 * @author gcoutable
 */
@Service
public class EventProcessorSubscriptionProvider implements IEventProcessorSubscriptionProvider {

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IProjectEditingContextService projectEditingContextService;

    private final ILibraryEditingContextService libraryEditingContextService;

    private final List<ICapabilityVoter> capabilityVoters;

    private final List<IRepresentationEventProcessorFluxCustomizer> representationEventProcessorFluxCustomizers;

    private final IEventProcessorSubscriptionSchedulerProvider eventProcessorSubscriptionSchedulerProvider;

    private final IMessageService messageService;

    public EventProcessorSubscriptionProvider(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IProjectEditingContextService projectEditingContextService, ILibraryEditingContextService libraryEditingContextService, List<ICapabilityVoter> capabilityVoters,
                                              List<IRepresentationEventProcessorFluxCustomizer> representationEventProcessorFluxCustomizers, IEventProcessorSubscriptionSchedulerProvider eventProcessorSubscriptionSchedulerProvider, IMessageService messageService) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
        this.libraryEditingContextService = Objects.requireNonNull(libraryEditingContextService);
        this.capabilityVoters = Objects.requireNonNull(capabilityVoters);
        this.representationEventProcessorFluxCustomizers = Objects.requireNonNull(representationEventProcessorFluxCustomizers);
        this.eventProcessorSubscriptionSchedulerProvider = Objects.requireNonNull(eventProcessorSubscriptionSchedulerProvider);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public Flux<IPayload> getSubscription(String editingContextId, String representationId, IInput input) {
        var canView = false;
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContextId);
        if (optionalProjectId.isPresent()) {
            canView = this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.PROJECT, optionalProjectId.get(), SiriusWebCapabilities.Project.VIEW) == CapabilityVote.GRANTED);
        } else {
            var optionalLibraryId = this.libraryEditingContextService.getLibraryIdentifier(editingContextId);
            if (optionalLibraryId.isPresent()) {
                canView = this.capabilityVoters.stream().allMatch(voter -> voter.vote(SiriusWebCapabilities.LIBRARY, optionalLibraryId.get(), SiriusWebCapabilities.Library.VIEW) == CapabilityVote.GRANTED);
            }
        }

        if (!canView) {
            return Flux.just(new ErrorPayload(input.id(), this.messageService.unauthorized()));
        }

        return this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(editingContextId)
                .flatMap(processor -> processor.acquireRepresentationEventProcessor(representationId, input))
                .map(representationEventProcessor -> this.customizeFlux(editingContextId, representationId, input, representationEventProcessor))
                .orElse(Flux.empty())
                .publishOn(this.eventProcessorSubscriptionSchedulerProvider.getScheduler(editingContextId));
    }

    private Flux<IPayload> customizeFlux(String editingContextId, String representationId, IInput input, IRepresentationEventProcessor representationEventProcessor) {
        Flux<IPayload> flux = representationEventProcessor.getOutputEvents(input);
        for (IRepresentationEventProcessorFluxCustomizer representationEventProcessorFluxCustomizer : this.representationEventProcessorFluxCustomizers) {
            if (representationEventProcessorFluxCustomizer.canHandle(editingContextId, representationId, input, representationEventProcessor)) {
                flux = representationEventProcessorFluxCustomizer.customize(editingContextId, representationId, input, representationEventProcessor, flux);
            }
        }
        return flux;
    }
}
