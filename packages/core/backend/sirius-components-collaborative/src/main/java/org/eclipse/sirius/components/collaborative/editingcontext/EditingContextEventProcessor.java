/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.time.Duration;
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
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.RepresentationRefreshedEvent;
import org.eclipse.sirius.components.collaborative.dto.RepresentationRenamedEventPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;
import reactor.core.scheduler.Schedulers;

/**
 * Handles all the inputs which concern a particular editing context one at a time, in order of arrival, and in a
 * dedicated thread and emit the output events.
 *
 * @author sbegaudeau
 * @author pcdavid
 */
public class EditingContextEventProcessor implements IEditingContextEventProcessor {

    public static final String REPRESENTATION_ID = "representationId";

    public static final String REPRESENTATION_LABEL = "representationLabel";

    private final Logger logger = LoggerFactory.getLogger(EditingContextEventProcessor.class);

    private final ICollaborativeMessageService messageService;

    private final IEditingContext editingContext;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final IDanglingRepresentationDeletionService danglingRepresentationDeletionService;

    private final Map<String, RepresentationEventProcessorEntry> representationEventProcessors = new ConcurrentHashMap<>();

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Many<Boolean> canBeDisposedSink = Sinks.many().unicast().onBackpressureBuffer();

    private final Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

    private final ExecutorService executorService;

    private final Disposable changeDescriptionDisposable;

    public EditingContextEventProcessor(EditingContextEventProcessorParameters parameters) {
        this.messageService = parameters.getMessageService();
        this.editingContext = parameters.getEditingContext();
        this.editingContextPersistenceService = parameters.getEditingContextPersistenceService();
        this.applicationEventPublisher = parameters.getApplicationEventPublisher();
        this.editingContextEventHandlers = parameters.getEditingContextEventHandlers();
        this.representationEventProcessorComposedFactory = parameters.getRepresentationEventProcessorComposedFactory();
        this.danglingRepresentationDeletionService = parameters.getDanglingRepresentationDeletionService();
        this.executorService = parameters.getExecutorServiceProvider().getExecutorService(this.editingContext);
        this.changeDescriptionDisposable = this.setupChangeDescriptionSinkConsumer();
    }

    @SuppressWarnings("checkstyle:IllegalCatch")
    private Disposable setupChangeDescriptionSinkConsumer() {
        Consumer<ChangeDescription> consumer = changeDescription -> {
            if (ChangeKind.REPRESENTATION_TO_DELETE.equals(changeDescription.getKind())) {
                Object representationId = changeDescription.getParameters().get(REPRESENTATION_ID);
                if (representationId instanceof String) {
                    DeleteRepresentationInput deleteRepresentationInput = new DeleteRepresentationInput(UUID.randomUUID(), (String) representationId);
                    this.doHandle(Sinks.one(), deleteRepresentationInput);
                }
            } else if (ChangeKind.REPRESENTATION_TO_RENAME.equals(changeDescription.getKind())) {
                Object representationId = changeDescription.getParameters().get(REPRESENTATION_ID);
                Object representationLabel = changeDescription.getParameters().get(REPRESENTATION_LABEL);
                if (representationId instanceof String && representationLabel instanceof String) {
                    RenameRepresentationInput renameRepresentationInput = new RenameRepresentationInput(UUID.randomUUID(), this.getEditingContextId(), (String) representationId,
                            (String) representationLabel);
                    this.doHandle(Sinks.one(), renameRepresentationInput);
                }
            }

            this.publishEvent(changeDescription);
            this.disposeRepresentationIfNeeded();

            RepresentationEventProcessorEntry representationEventProcessorEntry = this.representationEventProcessors.get(changeDescription.getSourceId());
            if (representationEventProcessorEntry != null) {
                try {
                    IRepresentationEventProcessor representationEventProcessor = representationEventProcessorEntry.getRepresentationEventProcessor();
                    representationEventProcessor.refresh(changeDescription);
                    IRepresentation representation = representationEventProcessor.getRepresentation();
                    this.applicationEventPublisher.publishEvent(new RepresentationRefreshedEvent(this.editingContext.getId(), representation));
                } catch (Exception exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }
            }
            this.refreshOtherRepresentations(changeDescription);

            if (this.shouldPersistTheEditingContext(changeDescription)) {
                this.editingContextPersistenceService.persist(this.editingContext);
            }
            this.danglingRepresentationDeletionService.deleteDanglingRepresentations(this.editingContext.getId());
        };

        Consumer<Throwable> errorConsumer = throwable -> this.logger.warn(throwable.getMessage(), throwable);

        return this.changeDescriptionSink.asFlux().subscribe(consumer, errorConsumer);
    }

    private void publishEvent(ChangeDescription changeDescription) {
        if (this.sink.currentSubscriberCount() > 0) {
            IInput input = changeDescription.getInput();
            UUID correlationId = input.getId();
            if (input instanceof RenameRepresentationInput && ChangeKind.REPRESENTATION_RENAMING.equals(changeDescription.getKind())) {
                String representationId = ((RenameRepresentationInput) input).getRepresentationId();
                String newLabel = ((RenameRepresentationInput) input).getNewLabel();
                this.tryEmitRepresentationRenamedEvent(correlationId, representationId, newLabel);
            } else if (ChangeKind.REPRESENTATION_TO_RENAME.equals(changeDescription.getKind()) && !changeDescription.getParameters().isEmpty()) {
                Map<String, Object> parameters = changeDescription.getParameters();
                // @formatter:off
                var optionalRepresentationId = Optional.ofNullable(parameters.get(REPRESENTATION_ID))
                        .filter(String.class::isInstance)
                        .map(String.class::cast);
                var optionalRepresentationLabel = Optional.ofNullable(parameters.get(REPRESENTATION_LABEL))
                        .filter(String.class::isInstance)
                        .map(String.class::cast);
                // @formatter:on
                if (optionalRepresentationId.isPresent() && optionalRepresentationLabel.isPresent()) {
                    this.tryEmitRepresentationRenamedEvent(correlationId, optionalRepresentationId.get(), optionalRepresentationLabel.get());
                }
            }
        }
    }

    private void tryEmitRepresentationRenamedEvent(UUID correlationId, String representationId, String newLabel) {
        if (this.sink.currentSubscriberCount() > 0) {
            EmitResult emitResult = this.sink.tryEmitNext(new RepresentationRenamedEventPayload(correlationId, representationId, newLabel));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a RepresentationRenamedEventPayload: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    @Override
    public String getEditingContextId() {
        return this.editingContext.getId();
    }

    @Override
    public Mono<IPayload> handle(IInput input) {
        if (this.executorService.isShutdown()) {
            this.logger.warn("Handler for editing context {} is shutdown", this.editingContext.getId());
            return Mono.empty();
        }

        this.logger.trace(input.toString());

        One<IPayload> payloadSink = Sinks.one();
        Future<?> future = this.executorService.submit(() -> this.doHandle(payloadSink, input));
        try {
            // Block until the event has been processed
            future.get();
        } catch (InterruptedException | ExecutionException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        // @formatter:off
        var timeoutFallback = Mono.just(new ErrorPayload(input.getId(), this.messageService.timeout()))
                .doOnSuccess(payload -> this.logger.warn("Timeout fallback for the input {}", input));
        return payloadSink.asMono()
                .log(this.getClass().getName(), Level.FINEST, SignalType.ON_NEXT, SignalType.ON_ERROR)
                .timeout(Duration.ofSeconds(5), timeoutFallback)
                .doOnError(throwable -> this.logger.warn(throwable.getMessage(), throwable));
        // @formatter:on
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
        this.logger.trace("Input received: {}", input);

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
     * Disposes the representation when its target object has been removed.
     */
    private void disposeRepresentationIfNeeded() {
        List<RepresentationEventProcessorEntry> entriesToDispose = new ArrayList<>();
        for (var entry : this.representationEventProcessors.values()) {
            if (this.danglingRepresentationDeletionService.isDangling(this.editingContext, entry.getRepresentationEventProcessor().getRepresentation())) {
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
                .filter(handler -> handler.canHandle(this.editingContext, input))
                .findFirst();
        // @formatter:on

        if (optionalEditingContextEventHandler.isPresent()) {
            IEditingContextEventHandler editingContextEventHandler = optionalEditingContextEventHandler.get();
            editingContextEventHandler.handle(payloadSink, this.changeDescriptionSink, this.editingContext, input);
        } else {
            this.logger.warn("No handler found for event: {}", input);
        }
    }

    private void handleRepresentationInput(One<IPayload> payloadSink, IRepresentationInput representationInput) {
        Optional<IRepresentationEventProcessor> optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessors.get(representationInput.getRepresentationId()))
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor);

        if (optionalRepresentationEventProcessor.isPresent()) {
            IRepresentationEventProcessor representationEventProcessor = optionalRepresentationEventProcessor.get();
            representationEventProcessor.handle(payloadSink, this.changeDescriptionSink, representationInput);
        } else {
            this.logger.warn("No representation event processor found for event: {}", representationInput);
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

                // @formatter:off
                Disposable subscription = representationEventProcessor.canBeDisposed()
                        .delayElements(Duration.ofSeconds(5))
                        .publishOn(Schedulers.fromExecutorService(this.executorService))
                        .subscribe(canBeDisposed -> {
                            if (canBeDisposed.booleanValue() && representationEventProcessor.getSubscriptionManager().isEmpty()) {
                                this.disposeRepresentation(configuration.getId());
                            } else {
                                this.logger.trace("Stopping the disposal of the representation event processor {}", configuration.getId());
                            }
                        }, throwable -> this.logger.warn(throwable.getMessage(), throwable));
                // @formatter:on

                var representationEventProcessorEntry = new RepresentationEventProcessorEntry(representationEventProcessor, subscription);
                this.representationEventProcessors.put(configuration.getId(), representationEventProcessorEntry);
            } else {
                this.logger.warn("The representation with the id {} does not exist", configuration.getId());
            }
        }

        this.logger.trace("Representation event processors count: {}", this.representationEventProcessors.size());

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

    private void disposeRepresentation(String representationId) {
        Optional.ofNullable(this.representationEventProcessors.remove(representationId)).ifPresent(RepresentationEventProcessorEntry::dispose);

        if (this.representationEventProcessors.isEmpty()) {
            EmitResult emitResult = this.canBeDisposedSink.tryEmitNext(Boolean.TRUE);
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting that the processor can be disposed: {}";
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
        this.logger.trace("Disposing the editing context event processor {}", this.editingContext.getId());

        EmitResult changeDescriptionEmitResult = this.changeDescriptionSink.tryEmitComplete();
        if (changeDescriptionEmitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, changeDescriptionEmitResult);
        }
        this.changeDescriptionDisposable.dispose();

        this.executorService.shutdown();

        this.representationEventProcessors.values().forEach(RepresentationEventProcessorEntry::dispose);
        this.representationEventProcessors.clear();

        this.editingContext.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }

    }
}
