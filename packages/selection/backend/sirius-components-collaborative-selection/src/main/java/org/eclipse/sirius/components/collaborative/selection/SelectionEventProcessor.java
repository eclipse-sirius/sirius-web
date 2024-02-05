/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.selection;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.selection.api.ISelectionEventProcessor;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionRefreshedEventPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.Selection;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.selection.renderer.SelectionRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to the input that target the selection and publishes updated versions of the {@link Selection} to interested
 * subscribers.
 *
 * @author arichard
 */
public class SelectionEventProcessor implements ISelectionEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(SelectionEventProcessor.class);

    private final IEditingContext editingContext;

    private final SelectionDescription selectionDescription;

    private final IObjectService objectService;

    private final String id;

    private final String objectId;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final ISubscriptionManager subscriptionManager;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final AtomicReference<Selection> currentSelection = new AtomicReference<>();

    public SelectionEventProcessor(IEditingContext editingContext, IObjectService objectService, SelectionDescription selectionDescription, String id, String objectId, ISubscriptionManager subscriptionManager,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.logger.trace("Creating the selection event processor {}", id);
        this.objectService = Objects.requireNonNull(objectService);
        this.selectionDescription = Objects.requireNonNull(selectionDescription);
        this.editingContext = Objects.requireNonNull(editingContext);
        this.id = Objects.requireNonNull(id);
        this.objectId = Objects.requireNonNull(objectId);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);

        Selection selection = this.refreshSelection();
        this.currentSelection.set(selection);

    }

    @Override
    public IRepresentation getRepresentation() {
        return this.currentSelection.get();
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        // Do nothing
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            Selection selection = this.refreshSelection();

            this.currentSelection.set(selection);
            if (this.sink.currentSubscriberCount() > 0) {
                EmitResult emitResult = this.sink.tryEmitNext(new SelectionRefreshedEventPayload(changeDescription.getInput().id(), selection));
                if (emitResult.isFailure()) {
                    String pattern = "An error has occurred while emitting a SelectionRefreshedEventPayload: {}";
                    this.logger.warn(pattern, emitResult);
                }
            }
        }
    }

    private boolean shouldRefresh(ChangeDescription changeDescription) {
        // @formatter:off
        return this.representationRefreshPolicyRegistry.getRepresentationRefreshPolicy(this.selectionDescription)
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);
        // @formatter:on
    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return (changeDescription) -> ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

    private Selection refreshSelection() {
        VariableManager variableManager = new VariableManager();
        var optionalObject = this.objectService.getObject(this.editingContext, this.objectId);
        variableManager.put(VariableManager.SELF, optionalObject.orElse(null));
        variableManager.put(IEditingContext.EDITING_CONTEXT, this.editingContext);
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, this.id);

        Selection selection = new SelectionRenderer(variableManager, this.selectionDescription).render();

        this.logger.trace("Selection refreshed: {}", selection.getId());

        return selection;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new SelectionRefreshedEventPayload(input.id(), this.currentSelection.get()));
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
        this.logger.trace("Disposing the selection event processor {}", this.id);

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}";
            this.logger.warn(pattern, emitResult);
        }
    }

}
