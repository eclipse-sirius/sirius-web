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
package org.eclipse.sirius.components.collaborative.representations;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IDanglingRepresentationDeletionService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorChangeListener;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.dto.RepresentationRenamedEventPayload;
import org.eclipse.sirius.components.collaborative.editingcontext.RepresentationEventProcessorEntry;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

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

    private final IEditingContextPersistenceService editingContextPersistenceService;

    public RepresentationEventProcessorChangeListener(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, MeterRegistry meterRegistry,
                                                      IDanglingRepresentationDeletionService danglingRepresentationDeletionService,
                                                      IEditingContextPersistenceService editingContextPersistenceService) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
        this.danglingRepresentationDeletionService = Objects.requireNonNull(danglingRepresentationDeletionService);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
    }

    @SuppressWarnings("checkstyle:IllegalCatch")
    @Override
    public void onChange(ExecutorService executorService, Sinks.Many<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, ChangeDescription changeDescription) {
        if (changeDescription.getKind().equals(ChangeKind.REPRESENTATION_DELETION)) {
            var representationIdParameter = changeDescription.getParameters().get(REPRESENTATION_ID);
            if (representationIdParameter instanceof String representationId) {
                this.disposeRepresentation(editingContext, canBeDisposedSink, representationId);
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

    private void publishEvent(Sinks.Many<IPayload> payloadSink, ChangeDescription changeDescription) {
        if (payloadSink.currentSubscriberCount() > 0) {
            IInput input = changeDescription.getInput();
            UUID correlationId = input.id();

            if (ChangeKind.REPRESENTATION_RENAMING.equals(changeDescription.getKind()) && !changeDescription.getParameters().isEmpty()) {
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
}
