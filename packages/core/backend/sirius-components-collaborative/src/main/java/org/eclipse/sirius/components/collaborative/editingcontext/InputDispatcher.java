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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IInputDispatcher;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.collaborative.api.IInputPreProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IRepresentationEventProcessorProvider;
import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Use to dispatch inputs to their handler.
 *
 * @author mcharfadi
 */
@Service
public class InputDispatcher implements IInputDispatcher {

    private static final String LOG_TIMING_FORMAT = "%1$6s";

    private final Logger logger = LoggerFactory.getLogger(InputDispatcher.class);

    private final List<IInputPreProcessor> inputPreProcessors;

    private final List<IInputPostProcessor> inputPostProcessors;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IRepresentationEventProcessorProvider representationEventProcessorProvider;

    public InputDispatcher(List<IInputPreProcessor> inputPreProcessors, List<IInputPostProcessor> inputPostProcessors, List<IEditingContextEventHandler> editingContextEventHandlers, IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IRepresentationEventProcessorProvider representationEventProcessorProvider) {
        this.inputPreProcessors = Objects.requireNonNull(inputPreProcessors);
        this.inputPostProcessors = Objects.requireNonNull(inputPostProcessors);
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.representationEventProcessorProvider = Objects.requireNonNull(representationEventProcessorProvider);
    }

    @Override
    public void dispatch(ExecutorService executorService, Sinks.One<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.logger.trace("Input received: {}", input);
        long start = System.currentTimeMillis();

        AtomicReference<IInput> inputAfterPreProcessing = new AtomicReference<>(input);
        this.inputPreProcessors.forEach(preProcessor -> inputAfterPreProcessing.set(preProcessor.preProcess(editingContext, inputAfterPreProcessing.get(), changeDescriptionSink)));

        if (inputAfterPreProcessing.get() instanceof IRepresentationInput representationInput) {
            this.handleRepresentationInput(executorService, payloadSink, canBeDisposedSink, changeDescriptionSink, editingContext, representationInput);
        } else {
            this.handleInput(payloadSink, canBeDisposedSink, changeDescriptionSink, editingContext, inputAfterPreProcessing.get());
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

    private void handleInput(Sinks.One<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
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

    private void handleRepresentationInput(ExecutorService executorService, Sinks.One<IPayload> payloadSink, Sinks.Many<Boolean> canBeDisposedSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IRepresentationInput representationInput) {
        Optional<IRepresentationEventProcessor> optionalRepresentationEventProcessor = this.representationEventProcessorProvider.acquireRepresentationEventProcessor(executorService, canBeDisposedSink, editingContext, representationInput.representationId(), representationInput);

        if (optionalRepresentationEventProcessor.isPresent()) {
            IRepresentationEventProcessor representationEventProcessor = optionalRepresentationEventProcessor.get();
            representationEventProcessor.handle(payloadSink, changeDescriptionSink, representationInput);
        } else {
            this.logger.warn("No representation event processor found for event: {}", representationInput);
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
