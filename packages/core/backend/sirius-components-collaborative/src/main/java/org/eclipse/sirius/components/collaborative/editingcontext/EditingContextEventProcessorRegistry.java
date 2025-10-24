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

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorInitializationHook;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

/**
 * Registry of the editing context event processors.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextEventProcessorRegistry implements IEditingContextEventProcessorRegistry {

    private final Logger logger = LoggerFactory.getLogger(EditingContextEventProcessorRegistry.class);

    private final IEditingContextEventProcessorFactory editingContextEventProcessorFactory;

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IEditingContextSearchService editingContextSearchService;

    private final List<IEditingContextEventProcessorInitializationHook> editingContextEventProcessorInitializationHooks;

    private final Duration disposeDelay;

    private final ICollaborativeMessageService messageService;

    private final Map<String, EditingContextEventProcessorEntry> editingContextEventProcessors = new ConcurrentHashMap<>();

    public EditingContextEventProcessorRegistry(IEditingContextEventProcessorFactory editingContextEventProcessorFactory,
                                                IRepresentationEventProcessorRegistry representationEventProcessorRegistry,
                                                IEditingContextSearchService editingContextSearchService,
                                                List<IEditingContextEventProcessorInitializationHook> editingContextEventProcessorInitializationHooks,
                                                @Value("${sirius.components.editingContext.disposeDelay:1s}") Duration disposeDelay,
                                                ICollaborativeMessageService messageService) {
        this.editingContextEventProcessorFactory = editingContextEventProcessorFactory;
        this.representationEventProcessorRegistry = representationEventProcessorRegistry;
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.editingContextEventProcessorInitializationHooks = Objects.requireNonNull(editingContextEventProcessorInitializationHooks);
        this.disposeDelay = disposeDelay;
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public List<IEditingContextEventProcessor> getEditingContextEventProcessors() {
        return this.editingContextEventProcessors.values().stream()
                .map(EditingContextEventProcessorEntry::getEditingContextEventProcessor)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Mono<IPayload> dispatchEvent(String editingContextId, IInput input) {
        var timeoutFallback = Mono.just(new ErrorPayload(input.id(), this.messageService.timeout()))
                .doOnSuccess(payload -> this.logger.warn("Timeout fallback for the input {}", input));

        return this.getOrCreateEditingContextEventProcessor(editingContextId)
                .map(processor -> processor.handle(input).timeout(Duration.ofSeconds(5), timeoutFallback))
                .orElse(Mono.empty());
    }

    @Override
    public synchronized Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(String editingContextId) {
        Optional<IEditingContextEventProcessor> optionalEditingContextEventProcessor = Optional.empty();
        if (this.editingContextSearchService.existsById(editingContextId)) {
            optionalEditingContextEventProcessor = Optional.ofNullable(this.editingContextEventProcessors.get(editingContextId))
                    .map(EditingContextEventProcessorEntry::getEditingContextEventProcessor);
            if (optionalEditingContextEventProcessor.isEmpty()) {
                Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(editingContextId);
                if (optionalEditingContext.isPresent()) {
                    IEditingContext editingContext = optionalEditingContext.get();

                    this.editingContextEventProcessorInitializationHooks.forEach(hook -> hook.preProcess(editingContext));

                    var editingContextEventProcessor = this.editingContextEventProcessorFactory.createEditingContextEventProcessor(editingContext);
                    Disposable subscription = editingContextEventProcessor.canBeDisposed().delayElements(this.disposeDelay).subscribe(canBeDisposed -> {
                        // We will wait for the delay before trying to dispose the editing context event processor
                        // We will check if the editing context event processor is still empty
                        if (canBeDisposed.booleanValue() && representationEventProcessorRegistry.values(editingContextId).isEmpty()) {
                            this.disposeEditingContextEventProcessor(editingContextId);
                        } else {
                            this.logger.trace("Stopping the disposal of the editing context");
                        }
                    });

                    var editingContextEventProcessorEntry = new EditingContextEventProcessorEntry(editingContextEventProcessor, subscription);
                    this.editingContextEventProcessors.put(editingContextId, editingContextEventProcessorEntry);

                    this.editingContextEventProcessorInitializationHooks.forEach(hook -> hook.postProcess(editingContext));
                    optionalEditingContextEventProcessor = Optional.of(editingContextEventProcessor);
                }
            }
        }

        return optionalEditingContextEventProcessor;
    }

    @Override
    public void disposeEditingContextEventProcessor(String editingContextId) {
        Optional.ofNullable(this.editingContextEventProcessors.remove(editingContextId)).ifPresent(EditingContextEventProcessorEntry::dispose);

        this.logger.trace("Editing context event processors count: {}", this.editingContextEventProcessors.size());
    }

    @PreDestroy
    public void dispose() {
        this.logger.debug("Shutting down all the editing context event processors");

        this.editingContextEventProcessors.values().forEach(EditingContextEventProcessorEntry::dispose);
        this.editingContextEventProcessors.clear();
    }
}
