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
package org.eclipse.sirius.web.spring.collaborative.trees;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.web.collaborative.api.services.ChangeDescription;
import org.eclipse.sirius.web.collaborative.api.services.ChangeKind;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManager;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeEventProcessor;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeService;
import org.eclipse.sirius.web.collaborative.trees.api.TreeCreationParameters;
import org.eclipse.sirius.web.collaborative.trees.api.TreeRefreshedEventPayload;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.trees.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;

/**
 * Reacts to the input that target a tree representation and publishes updated versions of the {@link Tree} to
 * interested subscribers.
 *
 * @author pcdavid
 */
public class TreeEventProcessor implements ITreeEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(TreeEventProcessor.class);

    private final ITreeService treeService;

    private final TreeCreationParameters treeCreationParameters;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final AtomicReference<Tree> currentTree = new AtomicReference<>();

    private final Timer timer;

    public TreeEventProcessor(ITreeService treeService, TreeCreationParameters treeCreationParameters, List<ITreeEventHandler> treeEventHandlers, ISubscriptionManager subscriptionManager,
            MeterRegistry meterRegistry) {
        this.treeService = Objects.requireNonNull(treeService);
        this.treeCreationParameters = Objects.requireNonNull(treeCreationParameters);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);

        // @formatter:off
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "tree") //$NON-NLS-1$
                .register(meterRegistry);
        // @formatter:on

        Tree tree = this.refreshTree();
        this.currentTree.set(tree);
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.currentTree.get();
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public Optional<EventHandlerResponse> handle(IRepresentationInput representationInput) {
        if (representationInput instanceof ITreeInput) {
            ITreeInput treeInput = (ITreeInput) representationInput;
            Optional<ITreeEventHandler> optionalTreeEventHandler = this.treeEventHandlers.stream().filter(handler -> handler.canHandle(treeInput)).findFirst();

            if (optionalTreeEventHandler.isPresent()) {
                ITreeEventHandler treeEventHandler = optionalTreeEventHandler.get();
                EventHandlerResponse eventHandlerResponse = treeEventHandler.handle(this.currentTree.get(), treeInput);

                this.refresh(representationInput, eventHandlerResponse.getChangeDescription());

                return Optional.of(eventHandlerResponse);
            } else {
                this.logger.warn("No handler found for event: {}", treeInput); //$NON-NLS-1$
            }
        }

        return Optional.empty();
    }

    @Override
    public void refresh(IInput input, ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription.getKind())) {
            long start = System.currentTimeMillis();

            Tree tree = this.refreshTree();

            this.currentTree.set(tree);
            EmitResult emitResult = this.sink.tryEmitNext(new TreeRefreshedEventPayload(input.getId(), tree));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a TreeRefreshedEventPayload: {0}"; //$NON-NLS-1$
                this.logger.warn(MessageFormat.format(pattern, emitResult));
            }

            long end = System.currentTimeMillis();
            this.timer.record(end - start, TimeUnit.MILLISECONDS);
        }
    }

    private boolean shouldRefresh(String changeKind) {
        boolean shouldRefresh = false;

        switch (changeKind) {
        case ChangeKind.SEMANTIC_CHANGE:
            shouldRefresh = true;
            break;
        case ChangeKind.REPRESENTATION_CREATION:
            shouldRefresh = true;
            break;
        case ChangeKind.REPRESENTATION_DELETION:
            shouldRefresh = true;
            break;
        case ChangeKind.REPRESENTATION_RENAMING:
            shouldRefresh = true;
            break;
        default:
            shouldRefresh = false;
        }

        return shouldRefresh;
    }

    private Tree refreshTree() {
        Tree tree = this.treeService.create(this.treeCreationParameters);
        this.logger.debug(MessageFormat.format("Tree refreshed: {0})", tree)); //$NON-NLS-1$
        return tree;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new TreeRefreshedEventPayload(input.getId(), this.currentTree.get()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());

        return Flux.merge(refreshEventFlux, this.subscriptionManager.getFlux(input));
    }

    @Override
    public void dispose() {
        this.subscriptionManager.dispose();
        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {0}"; //$NON-NLS-1$
            this.logger.warn(MessageFormat.format(pattern, emitResult));
        }
    }

}
