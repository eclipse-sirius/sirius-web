/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventHandler;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeEventProcessor;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeInput;
import org.eclipse.sirius.components.collaborative.trees.api.ITreeService;
import org.eclipse.sirius.components.collaborative.trees.api.TreeCreationParameters;
import org.eclipse.sirius.components.collaborative.trees.dto.TreeRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.trees.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to the input that target a tree representation and publishes updated versions of the {@link Tree} to
 * interested subscribers.
 *
 * @author pcdavid
 */
public class TreeEventProcessor implements ITreeEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(TreeEventProcessor.class);

    private final IEditingContext editingContext;

    private final ITreeService treeService;

    private final TreeCreationParameters treeCreationParameters;

    private final List<ITreeEventHandler> treeEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final AtomicReference<Tree> currentTree = new AtomicReference<>();

    private final Timer timer;

    public TreeEventProcessor(IEditingContext editingContext, ITreeService treeService, TreeCreationParameters treeCreationParameters, List<ITreeEventHandler> treeEventHandlers,
            ISubscriptionManager subscriptionManager, MeterRegistry meterRegistry, IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.logger.trace("Creating the tree event processor {}", treeCreationParameters.getEditingContext().getId());

        this.editingContext = Objects.requireNonNull(editingContext);
        this.treeService = Objects.requireNonNull(treeService);
        this.treeCreationParameters = Objects.requireNonNull(treeCreationParameters);
        this.treeEventHandlers = Objects.requireNonNull(treeEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);

        // @formatter:off
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "tree")
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
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof ITreeInput) {
            ITreeInput treeInput = (ITreeInput) representationInput;
            Optional<ITreeEventHandler> optionalTreeEventHandler = this.treeEventHandlers.stream().filter(handler -> handler.canHandle(treeInput)).findFirst();

            if (optionalTreeEventHandler.isPresent()) {
                ITreeEventHandler treeEventHandler = optionalTreeEventHandler.get();
                treeEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.treeCreationParameters.getTreeDescription(), this.currentTree.get(), treeInput);
            } else {
                this.logger.warn("No handler found for event: {}", treeInput);
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            long start = System.currentTimeMillis();

            Tree tree = this.refreshTree();

            this.currentTree.set(tree);
            if (this.sink.currentSubscriberCount() > 0) {
                EmitResult emitResult = this.sink.tryEmitNext(new TreeRefreshedEventPayload(changeDescription.getInput().id(), tree));
                if (emitResult.isFailure()) {
                    String pattern = "An error has occurred while emitting a TreeRefreshedEventPayload: {}";
                    this.logger.warn(pattern, emitResult);
                }
            }

            long end = System.currentTimeMillis();
            this.timer.record(end - start, TimeUnit.MILLISECONDS);
        }
    }

    private boolean shouldRefresh(ChangeDescription changeDescription) {
        // @formatter:off
        return this.representationRefreshPolicyRegistry.getRepresentationRefreshPolicy(this.treeCreationParameters.getTreeDescription())
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);
        // @formatter:on
    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return changeDescription -> {
            boolean shouldRefresh = false;

            switch (changeDescription.getKind()) {
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
        };
    }

    private Tree refreshTree() {
        Tree tree = this.treeService.create(this.treeCreationParameters);
        this.logger.trace("Tree refreshed: {}", this.treeCreationParameters.getEditingContext().getId());
        return tree;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new TreeRefreshedEventPayload(input.id(), this.currentTree.get()));
        var refreshEventFlux = Flux.concat(initialRefresh, this.sink.asFlux());

        // @formatter:off
        return Flux.merge(
            refreshEventFlux,
            this.subscriptionManager.getFlux(input)
        );
        // @formatter:on
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the tree event processor {}", this.treeCreationParameters.getEditingContext().getId());

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }

}
