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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.ISemanticRepresentation;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.web.spring.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.web.spring.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.web.spring.collaborative.dto.RepresentationRefreshedEvent;
import org.eclipse.sirius.web.spring.collaborative.dto.RepresentationRenamedEventPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

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

    private final Map<UUID, RepresentationEventProcessorEntry> representationEventProcessors = new ConcurrentHashMap<>();

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Many<Boolean> canBeDisposedSink = Sinks.many().unicast().onBackpressureBuffer();

    private final Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

    private final ExecutorService executor;

    private final IDanglingRepresentationDeletionService danglingRepresentationDeletionService;

    private final Disposable changeDescriptionDisposable;

    public EditingContextEventProcessor(IEditingContext editingContext, IEditingContextPersistenceService editingContextPersistenceService, ApplicationEventPublisher applicationEventPublisher,
            IObjectService objectService, List<IEditingContextEventHandler> editingContextEventHandlers, IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory,
            IDanglingRepresentationDeletionService danglingRepresentationDeletionService) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
        this.objectService = Objects.requireNonNull(objectService);
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
        this.danglingRepresentationDeletionService = Objects.requireNonNull(danglingRepresentationDeletionService);

        var delegateExecutorService = Executors.newSingleThreadExecutor((Runnable runnable) -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            thread.setName("Editing context " + this.editingContext.getId()); //$NON-NLS-1$
            return thread;
        });
        this.executor = new DelegatingSecurityContextExecutorService(delegateExecutorService);
        this.changeDescriptionDisposable = this.setupChangeDescriptionSinkConsumer();
    }

    private Disposable setupChangeDescriptionSinkConsumer() {
        return this.changeDescriptionSink.asFlux().subscribe(changeDescription -> {
            this.publishEvent(changeDescription);
            this.disposeRepresentationIfNeeded();

            RepresentationEventProcessorEntry representationEventProcessorEntry = this.representationEventProcessors.get(changeDescription.getSourceId());
            if (representationEventProcessorEntry != null) {
                IRepresentationEventProcessor representationEventProcessor = representationEventProcessorEntry.getRepresentationEventProcessor();
                representationEventProcessor.refresh(changeDescription);
                IRepresentation representation = representationEventProcessor.getRepresentation();
                this.applicationEventPublisher.publishEvent(new RepresentationRefreshedEvent(this.editingContext.getId(), representation));
            }
            this.refreshOtherRepresentations(changeDescription);

            if (this.shouldPersistTheEditingContext(changeDescription)) {
                this.editingContextPersistenceService.persist(this.editingContext);
            }
            this.danglingRepresentationDeletionService.deleteDanglingRepresentations(this.editingContext.getId());
        });
    }

    private void publishEvent(ChangeDescription changeDescription) {
        if (this.sink.currentSubscriberCount() > 0) {
            IInput input = changeDescription.getInput();
            if (input instanceof RenameRepresentationInput && ChangeKind.REPRESENTATION_RENAMING.equals(changeDescription.getKind())) {
                UUID representationId = ((RenameRepresentationInput) input).getRepresentationId();
                String newLabel = ((RenameRepresentationInput) input).getNewLabel();
                EmitResult emitResult = this.sink.tryEmitNext(new RepresentationRenamedEventPayload(input.getId(), representationId, newLabel));
                if (emitResult.isFailure()) {
                    String pattern = "An error has occurred while emitting a RepresentationRenamedEventPayload: {}"; //$NON-NLS-1$
                    this.logger.warn(pattern, emitResult);
                }
            }
        }
    }

    @Override
    public UUID getEditingContextId() {
        return this.editingContext.getId();
    }

    @Override
    public Mono<IPayload> handle(IInput input) {
        if (this.executor.isShutdown()) {
            this.logger.warn("Handler for editing context {} is shutdown", this.editingContext.getId()); //$NON-NLS-1$
            return Mono.empty();
        }

        One<IPayload> payloadSink = Sinks.one();
        Future<?> future = this.executor.submit(() -> this.doHandle(payloadSink, input));
        try {
            // Block until the event has been processed
            future.get();
        } catch (InterruptedException | ExecutionException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return payloadSink.asMono();
    }

    /**
     * Finds the proper event handler to perform the task matching the given input event.
     *
     * @param payloadSink
     *            The sink to publish payload
     * @param inputEvent
     *            The input event
     * @return The response computed by the event handler
     */
    private void doHandle(One<IPayload> payloadSink, IInput input) {
        this.logger.trace("Input received: {}", input); //$NON-NLS-1$

        if (input instanceof IRepresentationInput) {
            IRepresentationInput representationInput = (IRepresentationInput) input;
            this.handleRepresentationInput(payloadSink, representationInput);
        } else {
            this.handleInput(payloadSink, input);
        }

    }

    /**
     * Refresh all the representations except the one with the given representationId.
     *
     *
     * @param input
     *            The input which has triggered the refresh sequence
     * @param representationId
     *            The identifier of the representation which should not be refreshed
     * @param changeDescription
     *            The description of change to consider in order to determine if the representation should be refreshed
     */
    private void refreshOtherRepresentations(ChangeDescription changeDescription) {
        // @formatter:off
        this.representationEventProcessors.entrySet().stream()
            .filter(entry -> !Objects.equals(entry.getKey(), changeDescription.getSourceId()))
            .map(Entry::getValue)
            .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor)
            .forEach(representationEventProcessor -> {
                representationEventProcessor.refresh(changeDescription);
                IRepresentation representation = representationEventProcessor.getRepresentation();
                this.applicationEventPublisher.publishEvent(new RepresentationRefreshedEvent(this.editingContext.getId(), representation));
             });
        // @formatter:on
    }

    private boolean shouldPersistTheEditingContext(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
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
     */
    private void disposeRepresentationIfNeeded() {
        List<RepresentationEventProcessorEntry> entriesToDispose = new ArrayList<>();
        for (var entry : this.representationEventProcessors.values()) {
            if (this.isDangling(entry.getRepresentationEventProcessor().getRepresentation())) {
                entriesToDispose.add(entry);
            }
        }
        // @formatter:off
        entriesToDispose.stream()
            .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor)
            .map(IRepresentationEventProcessor::getRepresentation)
            .map(IRepresentation::getId)
            .forEach(this::disposeRepresentation);
        // @formatter:on
    }

    private void handleInput(One<IPayload> payloadSink, IInput input) {
        if (input instanceof DeleteRepresentationInput) {
            DeleteRepresentationInput deleteRepresentationInput = (DeleteRepresentationInput) input;
            this.disposeRepresentation(deleteRepresentationInput.getRepresentationId());
        }

        // @formatter:off
        Optional<IEditingContextEventHandler> optionalEditingContextEventHandler = this.editingContextEventHandlers.stream()
                .filter(handler -> handler.canHandle(input))
                .findFirst();
        // @formatter:on

        if (optionalEditingContextEventHandler.isPresent()) {
            IEditingContextEventHandler editingContextEventHandler = optionalEditingContextEventHandler.get();
            editingContextEventHandler.handle(payloadSink, this.changeDescriptionSink, this.editingContext, input);
        } else {
            this.logger.warn("No handler found for event: {}", input); //$NON-NLS-1$
        }
    }

    private void handleRepresentationInput(One<IPayload> payloadSink, IRepresentationInput representationInput) {
        Optional<IRepresentationEventProcessor> optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessors.get(representationInput.getRepresentationId()))
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor);

        if (optionalRepresentationEventProcessor.isPresent()) {
            IRepresentationEventProcessor representationEventProcessor = optionalRepresentationEventProcessor.get();
            representationEventProcessor.handle(payloadSink, this.changeDescriptionSink, representationInput);
        } else {
            this.logger.warn("No representation event processor found for event: {}", representationInput); //$NON-NLS-1$
        }
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> acquireRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IInput input) {
        // @formatter:off
        var optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessors.get(configuration.getId()))
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor)
                .filter(representationEventProcessorClass::isInstance)
                .map(representationEventProcessorClass::cast);
        // @formatter:on

        if (!optionalRepresentationEventProcessor.isPresent()) {
            optionalRepresentationEventProcessor = this.representationEventProcessorComposedFactory.createRepresentationEventProcessor(representationEventProcessorClass, configuration,
                    this.editingContext);
            if (optionalRepresentationEventProcessor.isPresent()) {
                var representationEventProcessor = optionalRepresentationEventProcessor.get();
                Disposable subscription = representationEventProcessor.canBeDisposed().subscribe(canBeDisposed -> {
                    if (canBeDisposed.booleanValue()) {
                        this.disposeRepresentation(configuration.getId());
                    }
                });

                var representationEventProcessorEntry = new RepresentationEventProcessorEntry(representationEventProcessor, subscription);
                this.representationEventProcessors.put(configuration.getId(), representationEventProcessorEntry);
            } else {
                this.logger.warn("The representation with the id {} does not exist", configuration.getId()); //$NON-NLS-1$
            }
        }

        this.logger.trace("Representation event processors count: {}", this.representationEventProcessors.size()); //$NON-NLS-1$

        return optionalRepresentationEventProcessor;
    }

    @Override
    public List<IRepresentationEventProcessor> getRepresentationEventProcessors() {
        // @formatter:off
        return this.representationEventProcessors.values().stream()
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    private void disposeRepresentation(UUID representationId) {
        Optional.ofNullable(this.representationEventProcessors.remove(representationId)).ifPresent(RepresentationEventProcessorEntry::dispose);

        if (this.representationEventProcessors.isEmpty()) {
            EmitResult emitResult = this.canBeDisposedSink.tryEmitNext(Boolean.TRUE);
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting that the processor can be disposed: {}"; //$NON-NLS-1$
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    @Override
    public Flux<IPayload> getOutputEvents() {
        return this.sink.asFlux();
    }

    @Override
    public Flux<Boolean> canBeDisposed() {
        return this.canBeDisposedSink.asFlux();
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the editing context event processor {}", this.editingContext.getId()); //$NON-NLS-1$

        this.changeDescriptionDisposable.dispose();

        this.executor.shutdown();

        this.representationEventProcessors.values().forEach(RepresentationEventProcessorEntry::dispose);
        this.representationEventProcessors.clear();

        EmitResult changeDescriptionEmitResult = this.changeDescriptionSink.tryEmitComplete();
        if (changeDescriptionEmitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, changeDescriptionEmitResult);
        }

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, emitResult);
        }

    }

}
