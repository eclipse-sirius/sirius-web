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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IComposedEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutor;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextEventProcessorExecutorServiceProvider;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
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

    private final IEditingContext editingContext;

    private final IComposedEditingContextEventHandler composedEditingContextEventHandler;

    private final ExecutorService executorService;

    private final ICollaborativeMessageService messageService;

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    private final MeterRegistry meterRegistry;

    public EditingContextExecutor(IEditingContext editingContext, IEditingContextEventProcessorExecutorServiceProvider executorServiceProvider,
            IComposedEditingContextEventHandler composedEditingContextEventHandler, ICollaborativeMessageService messageService,
            List<IInputPreProcessor> inputPreProcessors, List<IInputPostProcessor> inputPostProcessors, MeterRegistry meterRegistry) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.composedEditingContextEventHandler = Objects.requireNonNull(composedEditingContextEventHandler);
        this.executorService = Objects.requireNonNull(executorServiceProvider.getExecutorService(editingContext));
        this.messageService = Objects.requireNonNull(messageService);
        this.inputPreProcessors = Objects.requireNonNull(inputPreProcessors);
        this.inputPostProcessors = Objects.requireNonNull(inputPostProcessors);
        this.meterRegistry = Objects.requireNonNull(meterRegistry);
    }

    @Override
    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    @Override
    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    @Override
    public Mono<IPayload> handle(Sinks.Many<ChangeDescription> changeDescriptionSink, Sinks.Many<Boolean> canBeDisposedSink, IInput input) {
        Timer.Sample handleTimer = Timer.start(this.meterRegistry);
        if (this.executorService.isShutdown()) {
            this.logger.warn("Handler for editing context {} is shutdown", this.editingContext.getId());
            handleTimer.stop(this.meterRegistry.timer(Monitoring.EVENT_HANDLER, INPUT, input.getClass().getSimpleName(),
                    "inputId", input.id().toString()));
            return Mono.empty();
        }

        this.logger.trace(input.toString());

        Sinks.One<IPayload> payloadSink = Sinks.one();
        Future<?> future = this.executorService.submit(() -> this.doHandle(payloadSink, changeDescriptionSink, canBeDisposedSink, input));
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

    private void doHandle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, Sinks.Many<Boolean> canBeDisposedSink, IInput input) {
        this.logger.trace("Input received: {}", input);

        AtomicReference<IInput> inputAfterPreProcessing = new AtomicReference<>(input);
        this.inputPreProcessors.forEach(preProcessor -> inputAfterPreProcessing.set(preProcessor.preProcess(this.editingContext, inputAfterPreProcessing.get(), changeDescriptionSink)));

        this.composedEditingContextEventHandler.handle(payloadSink, changeDescriptionSink, canBeDisposedSink, this, inputAfterPreProcessing.get());

        this.inputPostProcessors.forEach(postProcessor -> postProcessor.postProcess(this.editingContext, inputAfterPreProcessing.get(), changeDescriptionSink));
    }
}
