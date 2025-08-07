/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorHandler;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorChangeListener;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorGetter;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
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

    public static final String REPRESENTATION_ID = "representationId";

    public static final String REPRESENTATION_LABEL = "representationLabel";

    public static final String INPUT = "INPUT";

    private final Logger logger = LoggerFactory.getLogger(EditingContextEventProcessor.class);

    private final IEditingContext editingContext;

    private final IEditingContextEventProcessorHandler editingContextEventProcessorHandler;

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IRepresentationEventProcessorChangeListener representationEventProcessorChangeListener;

    private final IRepresentationEventProcessorGetter representationEventProcessorGetter;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Many<Boolean> canBeDisposedSink = Sinks.many().unicast().onBackpressureBuffer();

    private final Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

    private final ExecutorService executorService;

    private final Disposable changeDescriptionDisposable;

    private final MeterRegistry meterRegistry;

    public EditingContextEventProcessor(IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider, IEditingContext editingContext, IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IRepresentationEventProcessorChangeListener representationEventProcessorChangeListener, IEditingContextEventProcessorHandler editingContextEventProcessorHandler, IRepresentationEventProcessorGetter representationEventProcessorGetter, MeterRegistry meterRegistry) {
        this.editingContext = editingContext;
        this.representationEventProcessorRegistry = representationEventProcessorRegistry;
        this.representationEventProcessorChangeListener = representationEventProcessorChangeListener;
        this.executorService = executorServiceProvider.getExecutorService(this.editingContext);
        this.editingContextEventProcessorHandler = editingContextEventProcessorHandler;
        this.representationEventProcessorGetter = representationEventProcessorGetter;
        this.meterRegistry = meterRegistry;
        this.changeDescriptionDisposable = this.setupChangeDescriptionSinkConsumer();

    }

    private Disposable setupChangeDescriptionSinkConsumer() {
        Consumer<ChangeDescription> consumer = changeDescription ->
                representationEventProcessorChangeListener.onChange(this.executorService, this.sink, this.canBeDisposedSink, this.changeDescriptionSink, this.editingContext, changeDescription);

        Consumer<Throwable> errorConsumer = throwable -> this.logger.warn(throwable.getMessage(), throwable);

        return this.changeDescriptionSink.asFlux().subscribe(consumer, errorConsumer);
    }

    @Override
    public String getEditingContextId() {
        return this.editingContext.getId();
    }

    @Override
    public Mono<IPayload> handle(IInput input) {
        Timer.Sample handleTimer = Timer.start(this.meterRegistry);
        if (this.executorService.isShutdown()) {
            this.logger.warn("Handler for editing context {} is shutdown", this.editingContext.getId());
            handleTimer.stop(this.meterRegistry.timer(Monitoring.EVENT_HANDLER, INPUT, input.getClass().getSimpleName(),
                    "inputId", input.id().toString()));
            return Mono.empty();
        }

        this.logger.trace(input.toString());

        One<IPayload> payloadSink = Sinks.one();
        Future<?> future = this.executorService.submit(() -> this.editingContextEventProcessorHandler.handle(this.executorService, payloadSink, this.canBeDisposedSink, this.changeDescriptionSink, this.editingContext, input));
        try {
            // Block until the event has been processed
            future.get();
        } catch (InterruptedException | ExecutionException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        handleTimer.stop(this.meterRegistry.timer(Monitoring.TIMER_PROCESSING_INPUT, "input", input.getClass().getSimpleName(),
                "inputId", input.id().toString()));

        return payloadSink.asMono()
                .log(this.getClass().getName(), Level.FINEST, SignalType.ON_NEXT, SignalType.ON_ERROR)
                .doOnError(throwable -> this.logger.warn(throwable.getMessage(), throwable));
    }

    @Override
    public Optional<IRepresentationEventProcessor> acquireRepresentationEventProcessor(String representationId, IInput input) {
        return this.representationEventProcessorGetter.acquireRepresentationEventProcessor(this.executorService, this.canBeDisposedSink, this.editingContext, representationId, input);
    }

    @Override
    public List<IRepresentationEventProcessor> getRepresentationEventProcessors() {
        return this.representationEventProcessorRegistry.values(editingContext.getId());
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

        this.representationEventProcessorRegistry.dispose(editingContext.getId());

        this.editingContext.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }

    }
}
