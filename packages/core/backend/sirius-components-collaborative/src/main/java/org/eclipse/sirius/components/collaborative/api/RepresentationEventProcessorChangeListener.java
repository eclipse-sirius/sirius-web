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
package org.eclipse.sirius.components.collaborative.api;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.RepresentationRenamedEventPayload;
import org.eclipse.sirius.components.collaborative.editingcontext.RepresentationEventProcessorEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Used to react to onChange events from event processors.
 *
 * @author mcharfadi
 */
@Service
public class RepresentationEventProcessorChangeListener implements IRepresentationEventProcessorChangeListener {

    public static final String REPRESENTATION_ID = "representationId";

    public static final String REPRESENTATION_LABEL = "representationLabel";

    private static final String LOG_TIMING_FORMAT = "%1$6s";

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final MeterRegistry meterRegistry;

    private final Logger logger = LoggerFactory.getLogger(RepresentationEventProcessorChangeListener.class);

    private final IDanglingRepresentationDeletionService danglingRepresentationDeletionService;

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    public RepresentationEventProcessorChangeListener(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, MeterRegistry meterRegistry, IDanglingRepresentationDeletionService danglingRepresentationDeletionService, List<IInputPreProcessor> inputPreProcessors, List<IInputPostProcessor> inputPostProcessors, IEditingContextPersistenceService editingContextPersistenceService, List<IEditingContextEventHandler> editingContextEventHandlers, IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
        this.danglingRepresentationDeletionService = Objects.requireNonNull(danglingRepresentationDeletionService);
        this.inputPreProcessors = Objects.requireNonNull(inputPreProcessors);
        this.inputPostProcessors = Objects.requireNonNull(inputPostProcessors);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
    }

    @Override
    public void onChange(ExecutorService executorService, Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        if (ChangeKind.REPRESENTATION_TO_DELETE.equals(changeDescription.getKind())) {
            Object representationId = changeDescription.getParameters().get(REPRESENTATION_ID);
            if (representationId instanceof String) {
                DeleteRepresentationInput deleteRepresentationInput = new DeleteRepresentationInput(UUID.randomUUID(), (String) representationId);
                this.doHandle(executorService, editingContext, canBeDisposedSink, Sinks.one(), changeDescriptionSink, deleteRepresentationInput);
            }
        } else if (ChangeKind.REPRESENTATION_TO_RENAME.equals(changeDescription.getKind())) {
            Object representationId = changeDescription.getParameters().get(REPRESENTATION_ID);
            Object representationLabel = changeDescription.getParameters().get(REPRESENTATION_LABEL);
            if (representationId instanceof String && representationLabel instanceof String) {
                RenameRepresentationInput renameRepresentationInput = new RenameRepresentationInput(UUID.randomUUID(), editingContext.getId(), (String) representationId,
                        (String) representationLabel);
                this.doHandle(executorService, editingContext, canBeDisposedSink, Sinks.one(), changeDescriptionSink, renameRepresentationInput);
            }
        } else if (ChangeKind.NOTHING.equals(changeDescription.getKind())) {
            return;
        }

        this.publishEvent(payloadSink, changeDescription);
        this.disposeRepresentationIfNeeded(editingContext, canBeDisposedSink);

        var refreshRepresentationSample = Timer.start(this.meterRegistry);

        try {
            RepresentationEventProcessorEntry representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), changeDescription.getSourceId());
            if (representationEventProcessorEntry != null) {
                IRepresentationEventProcessor representationEventProcessor = representationEventProcessorEntry.getRepresentationEventProcessor();

                long start = System.currentTimeMillis();
                representationEventProcessor.refresh(changeDescription);
                long end = System.currentTimeMillis();

                this.logger.atDebug()
                        .setMessage("EditingContext {}: {}ms to refresh the {} with id {}")
                        .addArgument(editingContext.getId())
                        .addArgument(() -> String.format(LOG_TIMING_FORMAT, end - start))
                        .addArgument(representationEventProcessor.getClass().getSimpleName())
                        .addArgument(representationEventProcessor.getRepresentation().getId())
                        .log();
            }
            this.refreshOtherRepresentations(editingContext, changeDescription);
        } catch (Exception exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        var timer = this.meterRegistry.timer(Monitoring.TIMER_REFRESH_REPRESENTATION, "changeDescription", changeDescription.getSourceId());
        refreshRepresentationSample.stop(timer);

        if (this.shouldPersistTheEditingContext(changeDescription)) {
            this.editingContextPersistenceService.persist(changeDescription.getInput(), editingContext);
        }
        this.danglingRepresentationDeletionService.deleteDanglingRepresentations(changeDescription.getInput(), editingContext);
    }

    /**
     * Finds the proper event handler to perform the task matching the given input event.
     *
     * @param payloadSink
     *         The sink to publish payload
     * @param input
     *         The input event
     */
    @Override
    public void doHandle(ExecutorService executorService, IEditingContext editingContext, Sinks.Many<Boolean> canBeDisposedSink, Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IInput input) {
        this.logger.trace("Input received: {}", input);
        long start = System.currentTimeMillis();

        AtomicReference<IInput> inputAfterPreProcessing = new AtomicReference<>(input);
        this.inputPreProcessors.forEach(preProcessor -> inputAfterPreProcessing.set(preProcessor.preProcess(editingContext, inputAfterPreProcessing.get(), changeDescriptionSink)));

        if (inputAfterPreProcessing.get() instanceof IRepresentationInput representationInput) {
            this.handleRepresentationInput(executorService, editingContext, payloadSink, canBeDisposedSink, changeDescriptionSink, representationInput);
        } else {
            this.handleInput(editingContext, canBeDisposedSink, payloadSink, changeDescriptionSink, inputAfterPreProcessing.get());
        }

        this.inputPostProcessors.forEach(postProcessor -> postProcessor.postProcess(editingContext, inputAfterPreProcessing.get(), changeDescriptionSink));

        long end = System.currentTimeMillis();
        this.logger.atDebug()
                .setMessage("EditingContext {}: {}ms to handle the {} with id {}")
                .addArgument(editingContext.getId())
                .addArgument(() -> String.format(LOG_TIMING_FORMAT, end - start))
                .addArgument(input.getClass().getSimpleName())
                .addArgument(input.id())
                .log();
    }

    private void handleInput(IEditingContext editingContext, Sinks.Many<Boolean> canBeDisposedSink, Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IInput input) {
        if (input instanceof DeleteRepresentationInput deleteRepresentationInput) {
            this.disposeRepresentation(editingContext, canBeDisposedSink, deleteRepresentationInput.representationId());
        }

        Optional<IEditingContextEventHandler> optionalEditingContextEventHandler = this.editingContextEventHandlers.stream()
                .filter(handler -> handler.canHandle(editingContext, input))
                .findFirst();

        if (optionalEditingContextEventHandler.isPresent()) {
            IEditingContextEventHandler editingContextEventHandler = optionalEditingContextEventHandler.get();
            editingContextEventHandler.handle(payloadSink, changeDescriptionSink, editingContext, input);
        } else {
            this.logger.warn("No handler found for event: {}", input);
        }
    }

    private void handleRepresentationInput(ExecutorService executorService, IEditingContext editingContext, Sinks.One<IPayload> payloadSink, Sinks. Many<Boolean> canBeDisposedSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        Optional<IRepresentationEventProcessor> optionalRepresentationEventProcessor = this.acquireRepresentationEventProcessor(executorService, editingContext, canBeDisposedSink, representationInput.representationId(), representationInput);

        if (optionalRepresentationEventProcessor.isPresent()) {
            IRepresentationEventProcessor representationEventProcessor = optionalRepresentationEventProcessor.get();
            representationEventProcessor.handle(payloadSink, changeDescriptionSink, representationInput);
        } else {
            this.logger.warn("No representation event processor found for event: {}", representationInput);
        }
    }

    private void publishEvent(Sinks.Many<IPayload> payloadSink, ChangeDescription changeDescription) {
        if (payloadSink.currentSubscriberCount() > 0) {
            IInput input = changeDescription.getInput();
            UUID correlationId = input.id();
            if (input instanceof RenameRepresentationInput renameRepresentationInput && ChangeKind.REPRESENTATION_RENAMING.equals(changeDescription.getKind())) {
                String representationId = renameRepresentationInput.representationId();
                String newLabel = renameRepresentationInput.newLabel();
                this.tryEmitRepresentationRenamedEvent(payloadSink, correlationId, representationId, newLabel);
            } else if (ChangeKind.REPRESENTATION_TO_RENAME.equals(changeDescription.getKind()) && !changeDescription.getParameters().isEmpty()) {
                Map<String, Object> parameters = changeDescription.getParameters();

                var optionalRepresentationId = Optional.ofNullable(parameters.get(REPRESENTATION_ID))
                        .filter(String.class::isInstance)
                        .map(String.class::cast);
                var optionalRepresentationLabel = Optional.ofNullable(parameters.get(REPRESENTATION_LABEL))
                        .filter(String.class::isInstance)
                        .map(String.class::cast);

                if (optionalRepresentationId.isPresent() && optionalRepresentationLabel.isPresent()) {
                    this.tryEmitRepresentationRenamedEvent(payloadSink, correlationId, optionalRepresentationId.get(), optionalRepresentationLabel.get());
                }
            }
        }
    }

    private void tryEmitRepresentationRenamedEvent(Sinks.Many<IPayload> payloadSink, UUID correlationId, String representationId, String newLabel) {
        if (payloadSink.currentSubscriberCount() > 0) {
            Sinks.EmitResult emitResult = payloadSink.tryEmitNext(new RepresentationRenamedEventPayload(correlationId, representationId, newLabel));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a RepresentationRenamedEventPayload: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    private boolean shouldPersistTheEditingContext(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

    /**
     * Refresh all the representations except the one with the given representationId.
     *
     * @param changeDescription
     *         The description of change to consider in order to determine if the representation should be refreshed
     */
    private void refreshOtherRepresentations(IEditingContext editingContext, ChangeDescription changeDescription) {
        this.representationEventProcessorRegistry.values(editingContext.getId()).stream()
                .filter(registry -> !Objects.equals(changeDescription.getSourceId(), registry.getRepresentation().getId()))
                .forEach(representationEventProcessor -> {
                    long start = System.currentTimeMillis();
                    representationEventProcessor.refresh(changeDescription);
                    long end = System.currentTimeMillis();

                    this.logger.atDebug()
                            .setMessage("EditingContext {}: {}ms to refresh the {} with id {}")
                            .addArgument(editingContext.getId())
                            .addArgument(() -> String.format(LOG_TIMING_FORMAT, end - start))
                            .addArgument(representationEventProcessor.getClass().getSimpleName())
                            .addArgument(representationEventProcessor.getRepresentation().getId())
                            .log();
                });
    }

    /**
     * Disposes the representation when its target object has been removed.
     */
    private void disposeRepresentationIfNeeded(IEditingContext editingContext, Sinks.Many<Boolean> canBeDisposedSink) {
        for (var representationEventProcessor : this.representationEventProcessorRegistry.values(editingContext.getId())) {
            if (this.danglingRepresentationDeletionService.isDangling(editingContext, representationEventProcessor.getRepresentation())) {
                this.disposeRepresentation(editingContext, canBeDisposedSink, representationEventProcessor.getRepresentation().getId());
            }
        }
    }

    private void disposeRepresentation(IEditingContext editingContext, Sinks.Many<Boolean> canBeDisposedSink, String representationId) {
        this.representationEventProcessorRegistry.disposeRepresentation(editingContext.getId(), representationId);

        if (this.representationEventProcessorRegistry.values(editingContext.getId()).isEmpty()) {
            Sinks.EmitResult emitResult = canBeDisposedSink.tryEmitNext(Boolean.TRUE);
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting that the processor can be disposed: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    @Override
    public Optional<IRepresentationEventProcessor> acquireRepresentationEventProcessor(ExecutorService executorService, IEditingContext editingContext, Sinks.Many<Boolean> canBeDisposedSink, String representationId, IInput input) {
        var getRepresentationEventProcessorSample = Timer.start(this.meterRegistry);

        var optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessorRegistry.get(editingContext.getId(), representationId))
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor);

        if (optionalRepresentationEventProcessor.isEmpty()) {
            optionalRepresentationEventProcessor = this.representationEventProcessorComposedFactory.createRepresentationEventProcessor(editingContext, representationId);
            if (optionalRepresentationEventProcessor.isPresent()) {
                var representationEventProcessor = optionalRepresentationEventProcessor.get();

                Disposable subscription = representationEventProcessor.canBeDisposed()
                        .delayElements(Duration.ofSeconds(5))
                        .publishOn(Schedulers.fromExecutorService(executorService))
                        .subscribe(canBeDisposed -> {
                            if (canBeDisposed.booleanValue() && representationEventProcessor.getSubscriptionManager().isEmpty()) {
                                this.disposeRepresentation(editingContext, canBeDisposedSink, representationId);
                            } else {
                                this.logger.trace("Stopping the disposal of the representation event processor {}", representationId);
                            }
                        }, throwable -> this.logger.warn(throwable.getMessage(), throwable));

                var representationEventProcessorEntry = new RepresentationEventProcessorEntry(representationEventProcessor, subscription);
                this.representationEventProcessorRegistry.put(editingContext.getId(), representationId, representationEventProcessorEntry);
            } else {
                this.logger.debug("The representation with the id {} does not exist", representationId);
            }
        } else {
            var timer = this.meterRegistry.timer(Monitoring.TIMER_CREATE_REPRESENATION_EVENT_PROCESSOR,
                    "editingContext", editingContext.getId(),
                    "input", input.getClass().getSimpleName(),
                    REPRESENTATION_ID, representationId);
            getRepresentationEventProcessorSample.stop(timer);
        }

        this.logger.trace("Representation event processors count: {}", this.representationEventProcessorRegistry.values(editingContext.getId()).size());
        return optionalRepresentationEventProcessor;
    }
}
