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

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IRepresentationEventProcessorProvider;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.editingcontext.RepresentationEventProcessorEntry;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

/**
 * Use to get the existing representation event processor or create a new one.
 *
 * @author mcharfadi
 */
@Service
public class RepresentationEventProcessorProvider implements IRepresentationEventProcessorProvider {

    public static final String REPRESENTATION_ID = "representationId";

    private final Logger logger = LoggerFactory.getLogger(RepresentationEventProcessorProvider.class);

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final MeterRegistry meterRegistry;

    public RepresentationEventProcessorProvider(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory, MeterRegistry meterRegistry) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    public Optional<IRepresentationEventProcessor> acquireRepresentationEventProcessor(ExecutorService executorService, Sinks.Many<Boolean> canBeDisposedSink, IEditingContext editingContext, String representationId, IInput input) {
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
