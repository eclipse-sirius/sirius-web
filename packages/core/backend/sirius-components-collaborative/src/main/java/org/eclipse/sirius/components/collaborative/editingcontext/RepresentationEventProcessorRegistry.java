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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.RepresentationRenamedEventPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.Disposable;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

/**
 * The registry of representation event processor held by an editing context.
 *
 * @author gcoutable
 */
public class RepresentationEventProcessorRegistry implements IRepresentationEventProcessorRegistry {

    private final Map<String, RepresentationEventProcessorEntry> representationEventProcessors = new ConcurrentHashMap<>();

    private final RepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final IDanglingRepresentationDeletionService danglingRepresentationDeletionService;

    private final MeterRegistry meterRegistry;

    private final Logger logger = LoggerFactory.getLogger(RepresentationEventProcessorRegistry.class);

    public RepresentationEventProcessorRegistry(RepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory,
            IDanglingRepresentationDeletionService danglingRepresentationDeletionService, MeterRegistry meterRegistry) {
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
        this.danglingRepresentationDeletionService = Objects.requireNonNull(danglingRepresentationDeletionService);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    public Optional<IRepresentationEventProcessor> getOrCreateRepresentationEventProcessor(String representationId, IEditingContext editingContext, Sinks.Many<Boolean> canBeDisposedSink,
            IEditingContextExecutor editingContextExecutor) {
        var getRepresentationEventProcessorSample = Timer.start(this.meterRegistry);

        var optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessors.get(representationId))
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor);

        if (optionalRepresentationEventProcessor.isEmpty()) {
            optionalRepresentationEventProcessor = this.representationEventProcessorComposedFactory.createRepresentationEventProcessor(editingContext, representationId);
            if (optionalRepresentationEventProcessor.isPresent()) {
                var representationEventProcessor = optionalRepresentationEventProcessor.get();

                Disposable subscription = representationEventProcessor.canBeDisposed()
                        .delayElements(Duration.ofSeconds(5))
                        .publishOn(Schedulers.fromExecutorService(editingContextExecutor.getExecutorService()))
                        .subscribe(canBeDisposed -> {
                            if (canBeDisposed && representationEventProcessor.getSubscriptionManager().isEmpty()) {
                                this.disposeRepresentation(canBeDisposedSink, representationId);
                            } else {
                                this.logger.trace("Stopping the disposal of the representation event processor {}", representationId);
                            }
                        }, throwable -> this.logger.warn(throwable.getMessage(), throwable));

                var representationEventProcessorEntry = new RepresentationEventProcessorEntry(representationEventProcessor, subscription);
                this.representationEventProcessors.put(representationId, representationEventProcessorEntry);
            } else {
                this.logger.debug("The representation with the id {} does not exist", representationId);
            }
        } else {
            var timer = this.meterRegistry.timer(Monitoring.TIMER_CREATE_REPRESENATION_EVENT_PROCESSOR,
                    "editingContext", editingContext.getId(),
                    REPRESENTATION_ID, representationId);
            getRepresentationEventProcessorSample.stop(timer);
        }

        this.logger.trace("Representation event processors count: {}", this.representationEventProcessors.size());
        return optionalRepresentationEventProcessor;
    }

    @Override
    public List<IRepresentationEventProcessor> values() {
        return this.representationEventProcessors.values().stream()
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor)
                .toList();
    }

    @Override
    @SuppressWarnings("checkstyle:IllegalCatch")
    public void onChange(ChangeDescription changeDescription, IEditingContext editingContext, Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink) {
        if (changeDescription.getKind().equals(ChangeKind.REPRESENTATION_DELETION)) {
            Object representationIdObject = changeDescription.getParameters().get(IRepresentationEventProcessorRegistry.REPRESENTATION_ID);
            if (representationIdObject instanceof String representationId) {
                this.disposeRepresentation(canBeDisposedSink, representationId);
            }
        }

        this.publishEvent(changeDescription, payloadSink);
        this.disposeRepresentationIfNeeded(canBeDisposedSink, editingContext);
        var refreshRepresentationSample = Timer.start(this.meterRegistry);

        RepresentationEventProcessorEntry representationEventProcessorEntry = this.representationEventProcessors.get(changeDescription.getSourceId());
        if (representationEventProcessorEntry != null) {
            try {
                IRepresentationEventProcessor representationEventProcessor = representationEventProcessorEntry.getRepresentationEventProcessor();
                representationEventProcessor.refresh(changeDescription);
                representationEventProcessor.postRefresh(changeDescription);
            } catch (Exception exception) {
                this.logger.warn(exception.getMessage(), exception);
            }
        }

        this.refreshOtherRepresentations(canBeDisposedSink, changeDescription);

        var timer = this.meterRegistry.timer(Monitoring.TIMER_REFRESH_REPRESENTATION, "changeDescription", changeDescription.getSourceId());
        refreshRepresentationSample.stop(timer);

        this.danglingRepresentationDeletionService.deleteDanglingRepresentations(changeDescription.getInput(), editingContext);
    }

    @Override
    public void dispose() {
        this.representationEventProcessors.values().forEach(RepresentationEventProcessorEntry::dispose);
        this.representationEventProcessors.clear();
    }

    private void publishEvent(ChangeDescription changeDescription, Sinks.Many<IPayload> payloadSink) {
        if (payloadSink.currentSubscriberCount() > 0) {
            IInput input = changeDescription.getInput();
            UUID correlationId = input.id();
            if (input instanceof IRepresentationInput && ChangeKind.REPRESENTATION_RENAMING.equals(changeDescription.getKind())) {
                var representationLabelObject = changeDescription.getParameters().get(IRepresentationEventProcessorRegistry.REPRESENTATION_LABEL);
                var representationIdObject = changeDescription.getParameters().get(IRepresentationEventProcessorRegistry.REPRESENTATION_ID);
                if (representationLabelObject instanceof String representationLabel && representationIdObject instanceof String representationId) {
                    this.tryEmitRepresentationRenamedEvent(correlationId, representationId, representationLabel, payloadSink);
                }
            }
        }
    }

    private void tryEmitRepresentationRenamedEvent(UUID correlationId, String representationId, String newLabel, Sinks.Many<IPayload> payloadSink) {
        if (payloadSink.currentSubscriberCount() > 0) {
            Sinks.EmitResult emitResult = payloadSink.tryEmitNext(new RepresentationRenamedEventPayload(correlationId, representationId, newLabel));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a RepresentationRenamedEventPayload: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    private void disposeRepresentationIfNeeded(Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext) {
        List<RepresentationEventProcessorEntry> entriesToDispose = new ArrayList<>();
        for (var entry : this.representationEventProcessors.values()) {
            if (this.danglingRepresentationDeletionService.isDangling(editingContext, entry.getRepresentationEventProcessor().getRepresentation())) {
                entriesToDispose.add(entry);
            }
        }

        entriesToDispose.stream()
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor)
                .map(IRepresentationEventProcessor::getRepresentation)
                .map(IRepresentation::getId)
                .forEach(id -> this.disposeRepresentation(canBeDisposedSink, id));
    }

    private void disposeRepresentation(Sinks.Many<Boolean> canBeDisposedSink, String representationId) {
        Optional.ofNullable(this.representationEventProcessors.remove(representationId)).ifPresent(RepresentationEventProcessorEntry::dispose);

        if (this.representationEventProcessors.isEmpty()) {
            Sinks.EmitResult emitResult = canBeDisposedSink.tryEmitNext(Boolean.TRUE);
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting that the processor can be disposed: {}";
                this.logger.warn(pattern, emitResult);
            }
        }
    }

    private void refreshOtherRepresentations(Sinks.Many<Boolean> canBeDisposedSink, ChangeDescription changeDescription) {
        this.representationEventProcessors.entrySet().stream()
                .filter(entry -> !Objects.equals(entry.getKey(), changeDescription.getSourceId()))
                .map(Map.Entry::getValue)
                .map(RepresentationEventProcessorEntry::getRepresentationEventProcessor)
                .forEach(representationEventProcessor -> {
                    representationEventProcessor.refresh(changeDescription);
                    representationEventProcessor.postRefresh(changeDescription);
                });
    }
}
