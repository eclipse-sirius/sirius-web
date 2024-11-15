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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IComposedEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.IEditingContextExecutor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Sinks;

/**
 * Editing context event handler that dispatch the input to the right handler.
 *
 * @author gcoutable
 */
public class ComposedEditingContextEventHandler implements IComposedEditingContextEventHandler {

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final Logger logger = LoggerFactory.getLogger(ComposedEditingContextEventHandler.class);

    public ComposedEditingContextEventHandler(List<IEditingContextEventHandler> editingContextEventHandlers,
            IRepresentationEventProcessorRegistry representationEventProcessorRegistry) {
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContextExecutor editingContextExecutor, IInput input) {
        if (input instanceof IRepresentationInput representationInput) {
            this.handleRepresentationInput(payloadSink, changeDescriptionSink, canBeDisposedSink, editingContextExecutor, representationInput);
        } else {
            this.handleInput(payloadSink, changeDescriptionSink, editingContextExecutor.getEditingContext(), input);
        }
    }

    private void handleInput(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
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

    private void handleRepresentationInput(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, Sinks.Many<Boolean> canBeDisposedSink, IEditingContextExecutor editingContextExecutor, IRepresentationInput representationInput) {
        Optional<IRepresentationEventProcessor> optionalRepresentationEventProcessor = this.representationEventProcessorRegistry.getOrCreateRepresentationEventProcessor(representationInput.representationId(),
                canBeDisposedSink, editingContextExecutor);

        if (optionalRepresentationEventProcessor.isPresent()) {
            IRepresentationEventProcessor representationEventProcessor = optionalRepresentationEventProcessor.get();
            representationEventProcessor.handle(payloadSink, changeDescriptionSink, representationInput);
        } else {
            this.logger.warn("No representation event processor found for event: {}", representationInput);
        }
    }
}
