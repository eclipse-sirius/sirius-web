/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.projects;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventProcessorFactory;
import org.eclipse.sirius.web.spring.collaborative.api.IEditingContextEventProcessorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    private final IEditingContextSearchService editingContextSearchService;

    private final Duration disposeDelay;

    private final Map<UUID, EditingContextEventProcessorEntry> editingContextEventProcessors = new ConcurrentHashMap<>();

    public EditingContextEventProcessorRegistry(IEditingContextEventProcessorFactory editingContextEventProcessorFactory, IEditingContextSearchService editingContextSearchService,
            @Value("${org.eclipse.sirius.web.editingContextEventProcessorRegistry.disposeDelay:30s}") Duration disposeDelay) {
        this.editingContextEventProcessorFactory = editingContextEventProcessorFactory;
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.disposeDelay = disposeDelay;
    }

    @Override
    public List<IEditingContextEventProcessor> getEditingContextEventProcessors() {
        // @formatter:off
        return this.editingContextEventProcessors.values().stream()
                .map(EditingContextEventProcessorEntry::getEditingContextEventProcessor)
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

    @Override
    public Mono<IPayload> dispatchEvent(UUID editingContextId, IInput input) {
        return this.getOrCreateEditingContextEventProcessor(editingContextId).map(processor -> processor.handle(input)).orElse(Mono.empty());
    }

    @Override
    public synchronized Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(UUID editingContextId) {
        Optional<IEditingContextEventProcessor> optionalEditingContextEventProcessor = Optional.empty();
        if (this.editingContextSearchService.existsById(editingContextId)) {
            optionalEditingContextEventProcessor = Optional.ofNullable(this.editingContextEventProcessors.get(editingContextId))
                    .map(EditingContextEventProcessorEntry::getEditingContextEventProcessor);
            if (optionalEditingContextEventProcessor.isEmpty()) {
                Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(editingContextId);
                if (optionalEditingContext.isPresent()) {
                    IEditingContext editingContext = optionalEditingContext.get();

                    var editingContextEventProcessor = this.editingContextEventProcessorFactory.createEditingContextEventProcessor(editingContext);
                    Disposable subscription = editingContextEventProcessor.canBeDisposed().delayElements(this.disposeDelay).subscribe(canBeDisposed -> {
                        // We will wait for the delay before trying to dispose the editing context event processor
                        // We will check if the editing context event processor is still empty
                        if (canBeDisposed.booleanValue() && editingContextEventProcessor.getRepresentationEventProcessors().isEmpty()) {
                            this.disposeEditingContextEventProcessor(editingContextId);
                        } else {
                            this.logger.trace("Stopping the disposal of the editing context"); //$NON-NLS-1$
                        }
                    });

                    var editingContextEventProcessorEntry = new EditingContextEventProcessorEntry(editingContextEventProcessor, subscription);
                    this.editingContextEventProcessors.put(editingContextId, editingContextEventProcessorEntry);

                    optionalEditingContextEventProcessor = Optional.of(editingContextEventProcessor);
                }
            }
        }

        return optionalEditingContextEventProcessor;
    }

    @Override
    public void disposeEditingContextEventProcessor(UUID editingContextId) {
        Optional.ofNullable(this.editingContextEventProcessors.remove(editingContextId)).ifPresent(EditingContextEventProcessorEntry::dispose);

        this.logger.trace("Editing context event processors count: {}", this.editingContextEventProcessors.size()); //$NON-NLS-1$
    }

    @PreDestroy
    public void dispose() {
        this.logger.debug("Shutting down all the editing context event processors"); //$NON-NLS-1$

        this.editingContextEventProcessors.values().forEach(EditingContextEventProcessorEntry::dispose);
        this.editingContextEventProcessors.clear();
    }
}
