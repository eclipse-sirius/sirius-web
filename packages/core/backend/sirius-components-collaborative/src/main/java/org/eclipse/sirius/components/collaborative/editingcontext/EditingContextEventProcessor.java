/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IChangeDescriptionListener;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextManager;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
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

    private final List<IChangeDescriptionListener> changeDescriptionListeners;

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final Many<Boolean> canBeDisposedSink = Sinks.many().unicast().onBackpressureBuffer();

    private final Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();

    private final Disposable changeDescriptionDisposable;

    private final IEditingContextManager editingContextManager;

    private final IEditingContextExecutor editingContextExecutor;

    private final IEditingContext editingContext;

    public EditingContextEventProcessor(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IEditingContextManager editingContextManager, IEditingContextExecutor editingContextExecutor, IEditingContext editingContext, List<IChangeDescriptionListener> changeDescriptionListeners) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.editingContextManager = Objects.requireNonNull(editingContextManager);
        this.editingContextExecutor = Objects.requireNonNull(editingContextExecutor);
        this.editingContext = Objects.requireNonNull(editingContext);
        this.changeDescriptionListeners = Objects.requireNonNull(changeDescriptionListeners);
        this.changeDescriptionDisposable = this.setupChangeDescriptionSinkConsumer();
    }

    private Disposable setupChangeDescriptionSinkConsumer() {
        Consumer<ChangeDescription> consumer = changeDescription -> {
            if (ChangeKind.NOTHING.equals(changeDescription.getKind())) {
                return;
            }

            this.representationEventProcessorRegistry.onChange(changeDescription, this.editingContext, this.sink, this.canBeDisposedSink);
            this.editingContextManager.onChange(changeDescription, this.editingContext);
            this.changeDescriptionListeners.forEach(changeDescriptionListener -> changeDescriptionListener.onChangeDescription(changeDescription, this.editingContext, this.sink, this.canBeDisposedSink));
        };

        Consumer<Throwable> errorConsumer = throwable -> this.logger.warn(throwable.getMessage(), throwable);

        return this.changeDescriptionSink.asFlux().subscribe(consumer, errorConsumer);
    }

    @Override
    public String getEditingContextId() {
        return this.editingContext.getId();
    }

    @Override
    public Mono<IPayload> handle(IInput input) {
        return this.editingContextExecutor.handle(this.changeDescriptionSink, this.canBeDisposedSink, input);
    }

    @Override
    public Optional<IRepresentationEventProcessor> acquireRepresentationEventProcessor(String representationId, IInput input) {
        return this.representationEventProcessorRegistry.getOrCreateRepresentationEventProcessor(representationId, this.canBeDisposedSink, this.editingContextExecutor);
    }

    @Override
    public List<IRepresentationEventProcessor> getRepresentationEventProcessors() {
        return this.representationEventProcessorRegistry.values();
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

        this.editingContextExecutor.dispose();

        this.representationEventProcessorRegistry.dispose();

        this.editingContext.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }
}
