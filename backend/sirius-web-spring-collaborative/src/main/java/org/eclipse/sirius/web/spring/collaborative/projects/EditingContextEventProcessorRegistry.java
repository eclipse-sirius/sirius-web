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

import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IEditingContextPersistenceService;
import org.eclipse.sirius.web.core.api.IEditingContextSearchService;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import reactor.core.Disposable;

/**
 * Registry of the editing context event processors.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextEventProcessorRegistry implements IEditingContextEventProcessorRegistry {

    /**
     * The delay to wait before considering if we should dispose an EditingContextEventProcessor.
     */
    private static final Duration DISPOSE_DELAY = Duration.ofSeconds(30);

    private final Logger logger = LoggerFactory.getLogger(EditingContextEventProcessorRegistry.class);

    private final IEditingContextSearchService editingContextSearchService;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final IObjectService objectService;

    private final List<IEditingContextEventHandler> editingContextEventHandlers;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final Map<UUID, EditingContextEventProcessorEntry> editingContextEventProcessors = new ConcurrentHashMap<>();

    public EditingContextEventProcessorRegistry(IEditingContextSearchService editingContextSearchService, IEditingContextPersistenceService editingContextPersistenceService,
            IObjectService objectService, ApplicationEventPublisher applicationEventPublisher, List<IEditingContextEventHandler> editingContextEventHandlers,
            IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
        this.objectService = Objects.requireNonNull(objectService);
        this.editingContextEventHandlers = Objects.requireNonNull(editingContextEventHandlers);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
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
    public Optional<IPayload> dispatchEvent(UUID editingContextId, IInput input) {
        return this.getOrCreateEditingContextEventProcessor(editingContextId).flatMap(processor -> processor.handle(input));
    }

    @Override
    public Optional<IEditingContextEventProcessor> getOrCreateEditingContextEventProcessor(UUID editingContextId) {
        Optional<IEditingContextEventProcessor> optionalEditingContextEventProcessor = Optional.empty();
        if (this.editingContextSearchService.existsById(editingContextId)) {
            optionalEditingContextEventProcessor = Optional.ofNullable(this.editingContextEventProcessors.get(editingContextId))
                    .map(EditingContextEventProcessorEntry::getEditingContextEventProcessor);
            if (optionalEditingContextEventProcessor.isEmpty()) {
                Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(editingContextId);
                if (optionalEditingContext.isPresent()) {
                    IEditingContext editingContext = optionalEditingContext.get();

                    var editingContextEventProcessor = new EditingContextEventProcessor(editingContext, this.editingContextPersistenceService, this.applicationEventPublisher, this.objectService,
                            this.editingContextEventHandlers, this.representationEventProcessorComposedFactory);
                    Disposable subscription = editingContextEventProcessor.canBeDisposed().delayElements(DISPOSE_DELAY).subscribe(canBeDisposed -> {
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
