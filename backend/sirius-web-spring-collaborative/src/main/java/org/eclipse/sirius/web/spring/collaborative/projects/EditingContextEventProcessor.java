/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.projects;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.collaborative.api.dto.DeleteRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.PreDestroyPayload;
import org.eclipse.sirius.web.collaborative.api.dto.RenameRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.dto.RepresentationRefreshedEvent;
import org.eclipse.sirius.web.collaborative.api.dto.RepresentationRenamedEventPayload;
import org.eclipse.sirius.web.collaborative.api.services.ChangeKind;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationConfiguration;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.web.collaborative.api.services.SubscriptionDescription;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.ISemanticRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

/**
 * Handles all the inputs which concern a particular editing context one at a time, in order of arrival, and in a
 * dedicated thread and emit the output events.
 *
 * @author sbegaudeau
 * @author pcdavid
 */
public class EditingContextEventProcessor implements IEditingContextEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(EditingContextEventProcessor.class);

    private final IEditingContext editingContext;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IObjectService objectService;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final ExecutorService executor;

    private final Map<UUID, IRepresentationEventProcessor> representationEventProcessors = new ConcurrentHashMap<>();

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    public EditingContextEventProcessor(IEditingContext editingContext, IEditingContextPersistenceService editingContextPersistenceService, ApplicationEventPublisher applicationEventPublisher,
            IObjectService objectService, List<IEditingContextEventHandler> editingContextEventHandlers, IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
        this.objectService = Objects.requireNonNull(objectService);
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);

        this.executor = Executors.newSingleThreadExecutor((Runnable runnable) -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            thread.setName("FIFO Event Handler for editing context " + this.editingContext.getId()); //$NON-NLS-1$
            return thread;
        });
    }

    @Override
    public UUID getEditingContextId() {
        return this.editingContext.getId();
    }

    @Override
    public Optional<IPayload> handle(IInput input) {
        if (this.executor.isShutdown()) {
            this.logger.warn("Handler for editing context {} is shutdown", this.editingContext.getId()); //$NON-NLS-1$
            return Optional.empty();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<IPayload> optionalPayload = Optional.empty();
        Future<Optional<EventHandlerResponse>> future = this.executor.submit(() -> {
            Optional<EventHandlerResponse> optionalResponse = Optional.empty();
            try {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                optionalResponse = this.doHandle(input);
            } finally {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
            return optionalResponse;
        });
        try {
            // Block until the event has been processed
            Optional<EventHandlerResponse> optionalResponse = future.get();

            optionalPayload = optionalResponse.map(EventHandlerResponse::getPayload);
        } catch (InterruptedException | ExecutionException exception) {
            this.logger.error(exception.getMessage(), exception);
        }

        this.publishEvent(input, optionalPayload);
        return optionalPayload;
    }

    private void publishEvent(IInput input, Optional<IPayload> optionalPayload) {
        if (optionalPayload.isPresent()) {
            IPayload payload = optionalPayload.get();
            if (input instanceof RenameRepresentationInput && payload instanceof RenameRepresentationSuccessPayload) {
                UUID representationId = ((RenameRepresentationInput) input).getRepresentationId();
                String newLabel = ((RenameRepresentationInput) input).getNewLabel();
                this.sink.tryEmitNext(new RepresentationRenamedEventPayload(representationId, newLabel));
            }
        }
    }

    /**
     * Finds the proper event handler to perform the task matching the given input event.
     *
     * @param inputEvent
     *            The input event
     * @return The response computed by the event handler
     */
    private Optional<EventHandlerResponse> doHandle(IInput input) {
        this.logger.debug(MessageFormat.format("Handling received event: {0}", input)); //$NON-NLS-1$

        Optional<EventHandlerResponse> optionalResponse = Optional.empty();

        UUID representationId = null;
        if (input instanceof IRepresentationInput) {
            IRepresentationInput representationInput = (IRepresentationInput) input;
            representationId = representationInput.getRepresentationId();

            optionalResponse = this.handleRepresentationInput(representationInput);
            if (input instanceof RenameRepresentationInput) {
                this.publishEvent(input, optionalResponse.map(EventHandlerResponse::getPayload));
            }
        } else {
            optionalResponse = this.handleInput(input);
        }

        if (optionalResponse.isPresent()) {
            EventHandlerResponse response = optionalResponse.get();

            this.disposeRepresentationIfNeeded();
            this.refreshOtherRepresentations(representationId, response.getChangeKind());

            if (this.shouldPersistTheEditingContext(response.getChangeKind())) {
                this.editingContextPersistenceService.persist(this.editingContext);
            }
        }

        return optionalResponse;
    }

    /**
     * Refresh all the representations except the one with the given representationId.
     *
     * @param representationId
     *            The identifier of the representation which should not be refreshed
     * @param changeKind
     *            The kind of change to consider in order to determine if the representation should be refreshed
     */
    private void refreshOtherRepresentations(UUID representationId, String changeKind) {
        // @formatter:off
        this.representationEventProcessors.entrySet().stream()
            .filter(entry -> !Objects.equals(entry.getKey(), representationId))
            .map(Entry::getValue)
            .forEach(representationEventProcessor -> {
                representationEventProcessor.refresh(changeKind);
                IRepresentation representation = representationEventProcessor.getRepresentation();
                this.applicationEventPublisher.publishEvent(new RepresentationRefreshedEvent(this.editingContext.getId(), representation));
             });
        // @formatter:on
    }

    private boolean shouldPersistTheEditingContext(String changeKind) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeKind);
    }

    /**
     * Return <code>true</code> whether the given representation is not attached to a semantic element,
     * <code>false</code> otherwise.
     *
     * @param representation
     *            The representation that may be dangling
     * @return <code>true</code> whether the representation is dangling, <code>false</code> otherwise
     */
    private boolean isDangling(IRepresentation representation) {
        if (representation instanceof ISemanticRepresentation) {
            ISemanticRepresentation semanticRepresentation = (ISemanticRepresentation) representation;
            String targetObjectId = semanticRepresentation.getTargetObjectId();
            Optional<Object> optionalObject = this.objectService.getObject(this.editingContext, targetObjectId);
            return optionalObject.isEmpty();
        }
        return false;
    }

    /**
     * Disposes the representation when its target object has been removed.
     *
     * @param context
     *            the context
     */
    private void disposeRepresentationIfNeeded() {
        List<IRepresentationEventProcessor> representationEventProcessorToDispose = new ArrayList<>();
        for (IRepresentationEventProcessor representationEventProcessor : this.representationEventProcessors.values()) {
            if (this.isDangling(representationEventProcessor.getRepresentation())) {
                representationEventProcessorToDispose.add(representationEventProcessor);
            }
        }
        // @formatter:off
        representationEventProcessorToDispose.stream()
            .map(IRepresentationEventProcessor::getRepresentation)
            .map(IRepresentation::getId)
            .forEach(this::disposeRepresentation);
        // @formatter:on
    }

    private Optional<EventHandlerResponse> handleInput(IInput input) {
        if (input instanceof DeleteRepresentationInput) {
            DeleteRepresentationInput deleteRepresentationInput = (DeleteRepresentationInput) input;
            this.disposeRepresentation(deleteRepresentationInput.getRepresentationId());
        }

        // @formatter:off
        Optional<IEditingContextEventHandler> optionalEditingContextEventHandler = this.editingContextEventHandlers.stream()
                .filter(handler -> handler.canHandle(input))
                .findFirst();
        // @formatter:on

        Optional<EventHandlerResponse> optionalResponse = Optional.empty();
        if (optionalEditingContextEventHandler.isPresent()) {
            IEditingContextEventHandler editingContextEventHandler = optionalEditingContextEventHandler.get();
            EventHandlerResponse response = editingContextEventHandler.handle(this.editingContext, input);
            optionalResponse = Optional.of(response);
        } else {
            this.logger.warn("No handler found for event: {}", input); //$NON-NLS-1$
        }
        return optionalResponse;
    }

    private Optional<EventHandlerResponse> handleRepresentationInput(IRepresentationInput representationInput) {
        Optional<IRepresentationEventProcessor> optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessors.get(representationInput.getRepresentationId()));

        Optional<EventHandlerResponse> optionalResponse = Optional.empty();
        if (optionalRepresentationEventProcessor.isPresent()) {
            IRepresentationEventProcessor representationEventProcessor = optionalRepresentationEventProcessor.get();
            optionalResponse = representationEventProcessor.handle(representationInput);
        } else {
            this.logger.warn("No representation event processor found for event: {}", representationInput); //$NON-NLS-1$
        }
        return optionalResponse;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> acquireRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            SubscriptionDescription subscriptionDescription) {
        // @formatter:off
        var optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessors.get(configuration.getId()))
                .filter(representationEventProcessorClass::isInstance)
                .map(representationEventProcessorClass::cast);
        // @formatter:on
        if (!optionalRepresentationEventProcessor.isPresent()) {
            optionalRepresentationEventProcessor = this.representationEventProcessorComposedFactory.createRepresentationEventProcessor(representationEventProcessorClass, configuration,
                    this.editingContext);
            if (optionalRepresentationEventProcessor.isPresent()) {
                var representationEventProcessor = optionalRepresentationEventProcessor.get();
                this.representationEventProcessors.put(configuration.getId(), representationEventProcessor);
                representationEventProcessor.getSubscriptionManager().add(subscriptionDescription);
            } else {
                this.logger.warn("The representation with the id {} does not exist", configuration.getId()); //$NON-NLS-1$
            }
        } else {
            var representationEventProcessor = optionalRepresentationEventProcessor.get();
            representationEventProcessor.getSubscriptionManager().add(subscriptionDescription);
        }

        return optionalRepresentationEventProcessor;
    }

    @Override
    public void release(SubscriptionDescription subscriptionDescription) {
        Optional<UUID> representationIDToRemove = Optional.empty();
        // @formatter:off
        Set<Entry<UUID, IRepresentationEventProcessor>> entries = this.representationEventProcessors.entrySet();
        for (Entry<UUID, IRepresentationEventProcessor> entry : entries) {
            var subscriptionManager = entry.getValue().getSubscriptionManager();
            subscriptionManager.remove(subscriptionDescription);

            if (subscriptionManager.isEmpty()) {
                representationIDToRemove = Optional.of(entry.getKey());
            }
        }

        representationIDToRemove.ifPresent(this::disposeRepresentation);
        // @formatter:on
    }

    @Override
    public List<IRepresentationEventProcessor> getRepresentationEventProcessors() {
        return this.representationEventProcessors.values().stream().collect(Collectors.toUnmodifiableList());
    }

    private void disposeRepresentation(UUID representationId) {
        Optional.ofNullable(this.representationEventProcessors.remove(representationId)).ifPresent(IRepresentationEventProcessor::dispose);
    }

    @Override
    public Flux<IPayload> getOutputEvents() {
        return this.sink.asFlux();
    }

    @Override
    public void dispose() {
        this.executor.shutdown();

        this.representationEventProcessors.values().stream().forEach(IRepresentationEventProcessor::dispose);
        this.representationEventProcessors.clear();
        this.sink.tryEmitComplete();
    }

    public void preDestroy() {
        this.representationEventProcessors.values().stream().forEach(IRepresentationEventProcessor::preDestroy);
        this.sink.tryEmitNext(new PreDestroyPayload(this.editingContext.getId()));
        this.dispose();
    }

}
