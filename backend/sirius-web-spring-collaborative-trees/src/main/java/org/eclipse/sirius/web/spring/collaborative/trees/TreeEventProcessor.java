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
package org.eclipse.sirius.web.spring.collaborative.trees;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.web.collaborative.api.dto.PreDestroyPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManager;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeEventProcessor;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeService;
import org.eclipse.sirius.web.collaborative.trees.api.TreeCreationParameters;
import org.eclipse.sirius.web.collaborative.trees.api.TreeRefreshedEventPayload;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.services.api.dto.IRepresentationInput;
import org.eclipse.sirius.web.trees.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

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

    private final DirectProcessor<IPayload> flux;

    private final FluxSink<IPayload> sink;

    private final AtomicReference<Tree> currentTree = new AtomicReference<>();

    private final Timer timer;

    public TreeEventProcessor(ITreeService treeService, TreeCreationParameters treeCreationParameters, List<ITreeEventHandler> treeEventHandlers, ISubscriptionManager subscriptionManager,
            MeterRegistry meterRegistry) {
        this.treeService = Objects.requireNonNull(treeService);
        this.treeCreationParameters = Objects.requireNonNull(treeCreationParameters);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);

        this.flux = DirectProcessor.create();
        this.sink = this.flux.sink();

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
    public Optional<EventHandlerResponse> handle(IRepresentationInput representationInput, Context context) {
        if (representationInput instanceof ITreeInput) {
            ITreeInput treeInput = (ITreeInput) representationInput;
            Optional<ITreeEventHandler> optionalTreeEventHandler = this.treeEventHandlers.stream().filter(handler -> handler.canHandle(treeInput)).findFirst();

            if (optionalTreeEventHandler.isPresent()) {
                ITreeEventHandler treeEventHandler = optionalTreeEventHandler.get();
                EventHandlerResponse eventHandlerResponse = treeEventHandler.handle(this.currentTree.get(), treeInput);
                if (eventHandlerResponse.getShouldRefreshPredicate().test(this.currentTree.get())) {
                    this.refresh();
                }
                return Optional.of(eventHandlerResponse);
            } else {
                this.logger.warn("No handler found for event: {}", treeInput); //$NON-NLS-1$
            }
        }

        return Optional.empty();
    }

    @Override
    public void refresh() {
        long start = System.currentTimeMillis();

        Tree tree = this.refreshTree();

        this.currentTree.set(tree);
        this.sink.next(new TreeRefreshedEventPayload(tree));

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
    }

    private Tree refreshTree() {
        Tree tree = this.treeService.create(this.treeCreationParameters);
        this.logger.debug(MessageFormat.format("Tree refreshed: {0})", tree)); //$NON-NLS-1$
        return tree;
    }

    @Override
    public Flux<IPayload> getOutputEvents() {
        var initialRefresh = Mono.fromCallable(() -> new TreeRefreshedEventPayload(this.currentTree.get()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.flux);

        return Flux.merge(refreshEventFlux, this.subscriptionManager.getFlux());
    }

    @Override
    public void dispose() {
        this.subscriptionManager.dispose();
        this.flux.onComplete();
    }

    @Override
    public void preDestroy() {
        this.sink.next(new PreDestroyPayload(this.getRepresentation().getId()));
    }
}
