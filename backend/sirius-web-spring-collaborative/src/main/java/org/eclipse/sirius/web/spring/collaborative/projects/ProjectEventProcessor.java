/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.collaborative.api.dto.DeleteRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.PreDestroyPayload;
import org.eclipse.sirius.web.collaborative.api.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.dto.RepresentationRefreshedEvent;
import org.eclipse.sirius.web.collaborative.api.dto.RepresentationRenamedEventPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationConfiguration;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessorComposedFactory;
import org.eclipse.sirius.web.collaborative.api.services.SubscriptionDescription;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.ISemanticRepresentation;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.IInput;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;
import org.eclipse.sirius.web.services.api.dto.IRepresentationInput;
import org.eclipse.sirius.web.services.api.monitoring.IStopWatch;
import org.eclipse.sirius.web.services.api.monitoring.IStopWatchFactory;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.projects.IEditingContextManager;
import org.eclipse.sirius.web.services.api.representations.RenameRepresentationInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/**
 * Handles all the inputs which concern a particular project one at a time, in order of arrival, and in a dedicated
 * thread and emit the output events.
 *
 * @author sbegaudeau
 * @author pcdavid
 */
public class ProjectEventProcessor implements IProjectEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(ProjectEventProcessor.class);

    private final UUID projectId;

    private final IEditingContext editingContext;

    private final IEditingContextManager editingContextManager;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final List<IProjectEventHandler> projectEventHandlers;

    private final IObjectService objectService;

    private final IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory;

    private final IStopWatchFactory stopWatchFactory;

    private final ExecutorService executor;

    private final Map<UUID, IRepresentationEventProcessor> representationEventProcessors = new ConcurrentHashMap<>();

    private final DirectProcessor<IPayload> flux;

    private final FluxSink<IPayload> sink;

    public ProjectEventProcessor(UUID projectId, IEditingContextManager editingContextManager, ApplicationEventPublisher applicationEventPublisher, IObjectService objectService,
            List<IProjectEventHandler> projectEventHandlers, IRepresentationEventProcessorComposedFactory representationEventProcessorComposedFactory, IStopWatchFactory stopWatchFactory) {
        this.projectId = Objects.requireNonNull(projectId);
        this.editingContextManager = Objects.requireNonNull(editingContextManager);
        this.editingContext = this.editingContextManager.createEditingContext(projectId);
        this.applicationEventPublisher = Objects.requireNonNull(applicationEventPublisher);
        this.objectService = Objects.requireNonNull(objectService);
        this.projectEventHandlers = Objects.requireNonNull(projectEventHandlers);
        this.representationEventProcessorComposedFactory = Objects.requireNonNull(representationEventProcessorComposedFactory);
        this.stopWatchFactory = Objects.requireNonNull(stopWatchFactory);

        this.executor = Executors.newSingleThreadExecutor((Runnable runnable) -> {
            Thread thread = Executors.defaultThreadFactory().newThread(runnable);
            thread.setName("FIFO Event Handler for project " + this.projectId); //$NON-NLS-1$
            return thread;
        });

        this.flux = DirectProcessor.create();
        this.sink = this.flux.sink();
    }

    @Override
    public UUID getProjectId() {
        return this.projectId;
    }

    @Override
    public Optional<IPayload> handle(IInput input, Context context) {
        if (this.executor.isShutdown()) {
            this.logger.warn("Handler for project {} is shutdown", this.projectId); //$NON-NLS-1$
            return Optional.empty();
        }

        Optional<IPayload> optionalPayload = Optional.empty();
        Future<Optional<EventHandlerResponse>> future = this.executor.submit(() -> {
            Optional<EventHandlerResponse> optionalResponse = Optional.empty();
            if (context.getPrincipal() instanceof Authentication) {
                try {
                    SecurityContextHolder.getContext().setAuthentication((Authentication) context.getPrincipal());
                    optionalResponse = this.doHandle(input, context);
                } finally {
                    SecurityContextHolder.getContext().setAuthentication(null);
                }
            }
            return optionalResponse;
        });
        try {
            // Block until the event has been processed
            Optional<EventHandlerResponse> optionalResponse = future.get();

            optionalPayload = optionalResponse.map(EventHandlerResponse::getPayload);
        } catch (InterruptedException | ExecutionException exception) {
            this.logger.error(exception.getMessage(), exception);
        }

        if (input instanceof RenameRepresentationInput && optionalPayload.isPresent()) {
            IPayload payload = optionalPayload.get();
            if (payload instanceof RenameRepresentationSuccessPayload) {
                UUID representationId = ((RenameRepresentationInput) input).getRepresentationId();
                String newLabel = ((RenameRepresentationInput) input).getNewLabel();
                this.sink.next(new RepresentationRenamedEventPayload(representationId, newLabel));
            }
        }
        return optionalPayload;
    }

    /**
     * Finds the proper event handler to perform the task matching the given input event.
     *
     * @param inputEvent
     *            The input event
     * @return The response computed by the event handler
     */
    private Optional<EventHandlerResponse> doHandle(IInput input, Context context) {
        this.logger.debug(MessageFormat.format("Handling received event: {0}", input)); //$NON-NLS-1$

        Optional<EventHandlerResponse> optionalResponse = Optional.empty();

        IStopWatch stopWatch = this.stopWatchFactory.createStopWatch(input.getClass().getSimpleName());

        stopWatch.start("Processing event"); //$NON-NLS-1$
        if (input instanceof IProjectInput) {
            optionalResponse = this.handleProjectInput((IProjectInput) input, context);

            if (input instanceof RenameRepresentationInput) {
                UUID representationId = ((RenameRepresentationInput) input).getRepresentationId();
                if (this.representationEventProcessors.containsKey(representationId)) {
                    this.handleRepresentationInput((IRepresentationInput) input, context);
                }
            }
        } else if (input instanceof IRepresentationInput) {
            optionalResponse = this.handleRepresentationInput((IRepresentationInput) input, context);
        }
        stopWatch.stop();

        if (optionalResponse.isPresent()) {
            EventHandlerResponse response = optionalResponse.get();

            this.disposeRepresentationIfNeeded(context);

            // @formatter:off
            this.representationEventProcessors.values().stream()
                .filter(representationEventProcessor -> {
                    IRepresentation representation = representationEventProcessor.getRepresentation();
                    return response.getShouldRefreshPredicate().test(representation);
                })
                .forEach(representationEventProcessor -> {
                    representationEventProcessor.refresh(stopWatch);
                    IRepresentation representation = representationEventProcessor.getRepresentation();
                    this.applicationEventPublisher.publishEvent(new RepresentationRefreshedEvent(this.projectId, representation));
                });
            // @formatter:on

            if (response.isEditingContextDirty()) {
                this.editingContextManager.persist(this.projectId, this.editingContext, stopWatch);
            }
        }

        this.logger.debug(System.lineSeparator() + stopWatch.prettyPrint());

        return optionalResponse;
    }

    /**
     * Return <code>true</code> whether the given representation is not attached to a semantic element,
     * <code>false</code> otherwise.
     *
     * @param representation
     *            The representation that may be dangling
     * @return <code>true</code> whether the representation is dangling, <code>false</code> otherwise
     */
    private boolean isDangling(IRepresentation representation) {
        if (representation instanceof ISemanticRepresentation) {
            ISemanticRepresentation semanticRepresentation = (ISemanticRepresentation) representation;
            String targetObjectId = semanticRepresentation.getTargetObjectId();
            Optional<Object> optionalObject = this.objectService.getObject(this.editingContext, targetObjectId);
            return optionalObject.isEmpty();
        }
        return false;
    }

    /**
     * Disposes the representation when its target object has been removed.
     *
     * @param context
     *            the context
     */
    private void disposeRepresentationIfNeeded(Context context) {
        List<IRepresentationEventProcessor> representationEventProcessorToDispose = new ArrayList<>();
        for (IRepresentationEventProcessor representationEventProcessor : this.representationEventProcessors.values()) {
            if (this.isDangling(representationEventProcessor.getRepresentation())) {
                representationEventProcessorToDispose.add(representationEventProcessor);
            }
        }
        // @formatter:off
        representationEventProcessorToDispose.stream()
            .map(IRepresentationEventProcessor::getRepresentation)
            .map(IRepresentation::getId)
            .forEach(this::disposeRepresentation);
        // @formatter:on
    }

    private Optional<EventHandlerResponse> handleProjectInput(IProjectInput projectInput, Context context) {
        if (projectInput instanceof DeleteRepresentationInput) {
            DeleteRepresentationInput deleteRepresentationInput = (DeleteRepresentationInput) projectInput;
            this.disposeRepresentation(deleteRepresentationInput.getRepresentationId());
        }

        // @formatter:off
        Optional<IProjectEventHandler> optionalProjectEventHandler = this.projectEventHandlers.stream()
                .filter(handler -> handler.canHandle(projectInput))
                .findFirst();
        // @formatter:on

        Optional<EventHandlerResponse> optionalResponse = Optional.empty();
        if (optionalProjectEventHandler.isPresent()) {
            IProjectEventHandler projectEventHandler = optionalProjectEventHandler.get();
            EventHandlerResponse response = projectEventHandler.handle(this.editingContext, projectInput, context);
            optionalResponse = Optional.of(response);
        } else {
            this.logger.warn("No handler found for event: {}", projectInput); //$NON-NLS-1$
        }
        return optionalResponse;
    }

    private Optional<EventHandlerResponse> handleRepresentationInput(IRepresentationInput representationInput, Context context) {
        Optional<IRepresentationEventProcessor> optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessors.get(representationInput.getRepresentationId()));

        Optional<EventHandlerResponse> optionalResponse = Optional.empty();
        if (optionalRepresentationEventProcessor.isPresent()) {
            IRepresentationEventProcessor representationEventProcessor = optionalRepresentationEventProcessor.get();
            optionalResponse = representationEventProcessor.handle(representationInput, context);
        } else {
            this.logger.warn("No representation event processor found for event: {}", representationInput); //$NON-NLS-1$
        }
        return optionalResponse;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> acquireRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            SubscriptionDescription subscriptionDescription, Context context) {
        // @formatter:off
        var optionalRepresentationEventProcessor = Optional.ofNullable(this.representationEventProcessors.get(configuration.getId()))
                .filter(representationEventProcessorClass::isInstance)
                .map(representationEventProcessorClass::cast);
        // @formatter:on
        if (!optionalRepresentationEventProcessor.isPresent()) {
            optionalRepresentationEventProcessor = this.representationEventProcessorComposedFactory.createRepresentationEventProcessor(representationEventProcessorClass, configuration,
                    this.editingContext, context);
            if (optionalRepresentationEventProcessor.isPresent()) {
                var representationEventProcessor = optionalRepresentationEventProcessor.get();
                this.representationEventProcessors.put(configuration.getId(), representationEventProcessor);
                representationEventProcessor.getSubscriptionManager().add(subscriptionDescription);
            } else {
                this.logger.warn("The representation with the id {} does not exist", configuration.getId()); //$NON-NLS-1$
            }
        } else {
            var representationEventProcessor = optionalRepresentationEventProcessor.get();
            representationEventProcessor.getSubscriptionManager().add(subscriptionDescription);
        }

        return optionalRepresentationEventProcessor;
    }

    @Override
    public void release(SubscriptionDescription subscriptionDescription, Context context) {
        Optional<UUID> representationIDToRemove = Optional.empty();
        // @formatter:off
        Set<Entry<UUID, IRepresentationEventProcessor>> entries = this.representationEventProcessors.entrySet();
        for (Entry<UUID, IRepresentationEventProcessor> entry : entries) {
            var subscriptionManager = entry.getValue().getSubscriptionManager();
            subscriptionManager.remove(subscriptionDescription);

            if (subscriptionManager.isEmpty()) {
                representationIDToRemove = Optional.of(entry.getKey());
            }
        }

        representationIDToRemove.ifPresent(this::disposeRepresentation);
        // @formatter:on
    }

    @Override
    public List<IRepresentationEventProcessor> getRepresentationEventProcessors() {
        return this.representationEventProcessors.values().stream().collect(Collectors.toUnmodifiableList());
    }

    private void disposeRepresentation(UUID representationId) {
        Optional.ofNullable(this.representationEventProcessors.remove(representationId)).ifPresent(IRepresentationEventProcessor::dispose);
    }

    @Override
    public Flux<IPayload> getOutputEvents() {
        return this.flux;
    }

    @Override
    public void dispose() {
        this.executor.shutdown();

        this.representationEventProcessors.values().stream().forEach(IRepresentationEventProcessor::dispose);
        this.representationEventProcessors.clear();
        this.flux.onComplete();
    }

    public void preDestroy() {
        this.representationEventProcessors.values().stream().forEach(IRepresentationEventProcessor::preDestroy);
        this.sink.next(new PreDestroyPayload(this.getProjectId()));
        this.dispose();
    }

}
