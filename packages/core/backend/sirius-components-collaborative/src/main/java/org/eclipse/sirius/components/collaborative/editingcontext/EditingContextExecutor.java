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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutor;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Sinks;

/**
 * Used to execute editing context event handlers in an executor service.
 *
 * @author gcoutable
 */
public class EditingContextExecutor implements IEditingContextExecutor {

    private final Logger logger = LoggerFactory.getLogger(EditingContextExecutor.class);

    private final ExecutorService executorService;

    private final ICollaborativeMessageService messageService;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    private final MeterRegistry meterRegistry;

    public EditingContextExecutor(ExecutorService executorService, ICollaborativeMessageService messageService, List<IEditingContextEventHandler> editingContextEventHandlers,
            List<IInputPreProcessor> inputPreProcessors, List<IInputPostProcessor> inputPostProcessors, MeterRegistry meterRegistry) {
        this.executorService = Objects.requireNonNull(executorService);
        this.messageService = Objects.requireNonNull(messageService);
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.inputPreProcessors = Objects.requireNonNull(inputPreProcessors);
        this.inputPostProcessors = Objects.requireNonNull(inputPostProcessors);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    public ExecutorService getExecutorService() {
        return this.executorService;
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

        Sinks.One<IPayload> payloadSink = Sinks.one();
        Future<?> future = this.executorService.submit(() -> this.doHandle(payloadSink, input));
        try {
            // Block until the event has been processed
            future.get();
        } catch (InterruptedException | ExecutionException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        handleTimer.stop(this.meterRegistry.timer(Monitoring.TIMER_PROCESSING_INPUT, "input", input.getClass().getSimpleName(),
                "inputId", input.id().toString()));

        var timeoutFallback = Mono.just(new ErrorPayload(input.id(), this.messageService.timeout()))
                .doOnSuccess(payload -> this.logger.warn("Timeout fallback for the input {}", input));
        return payloadSink.asMono()
                .log(this.getClass().getName(), Level.FINEST, SignalType.ON_NEXT, SignalType.ON_ERROR)
                .timeout(Duration.ofSeconds(5), timeoutFallback)
                .doOnError(throwable -> this.logger.warn(throwable.getMessage(), throwable));
    }

    @Override
    public void dispose() {
        this.executorService.shutdown();
    }

    /**
     * Finds the proper event handler to perform the task matching the given input event.
     *
     * @param payloadSink
     *         The sink to publish payload
     * @param input
     *         The input event

     */
    private void doHandle(Sinks.One<IPayload> payloadSink, IInput input) {
        this.logger.trace("Input received: {}", input);

        AtomicReference<IInput> inputAfterPreProcessing = new AtomicReference<>(input);
        this.inputPreProcessors.forEach(preProcessor -> inputAfterPreProcessing.set(preProcessor.preProcess(this.editingContext, inputAfterPreProcessing.get(), this.changeDescriptionSink)));

        if (inputAfterPreProcessing.get() instanceof IRepresentationInput representationInput) {
            this.handleRepresentationInput(payloadSink, representationInput);
        } else {
            this.handleInput(payloadSink, inputAfterPreProcessing.get());
        }

        this.inputPostProcessors.forEach(postProcessor -> postProcessor.postProcess(this.editingContext, inputAfterPreProcessing.get(), this.changeDescriptionSink));
    }

    private void handleInput(Sinks.One<IPayload> payloadSink, IInput input) {
        Optional<IEditingContextEventHandler> optionalEditingContextEventHandler = this.editingContextEventHandlers.stream()
                .filter(handler -> handler.canHandle(this.editingContext, input))
                .findFirst();

        if (optionalEditingContextEventHandler.isPresent()) {
            IEditingContextEventHandler editingContextEventHandler = optionalEditingContextEventHandler.get();
            editingContextEventHandler.handle(payloadSink, this.changeDescriptionSink, this.editingContext, input);
        } else {
            this.logger.warn("No handler found for event: {}", input);
        }
    }

    private void handleRepresentationInput(Sinks.One<IPayload> payloadSink, IRepresentationInput representationInput) {
        Optional<IRepresentationEventProcessor> optionalRepresentationEventProcessor = this.acquireRepresentationEventProcessor(representationInput.representationId(), representationInput);

        if (optionalRepresentationEventProcessor.isPresent()) {
            IRepresentationEventProcessor representationEventProcessor = optionalRepresentationEventProcessor.get();
            representationEventProcessor.handle(payloadSink, this.changeDescriptionSink, representationInput);
        } else {
            this.logger.warn("No representation event processor found for event: {}", representationInput);
        }
    }
}
