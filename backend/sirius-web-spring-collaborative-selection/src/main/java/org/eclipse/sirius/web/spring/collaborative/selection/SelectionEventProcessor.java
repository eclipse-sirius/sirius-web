/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.selection;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.IRepresentationMetadata;
import org.eclipse.sirius.web.representations.SemanticRepresentationMetadata;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.selection.Selection;
import org.eclipse.sirius.web.selection.description.SelectionDescription;
import org.eclipse.sirius.web.selection.renderer.SelectionRenderer;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.web.spring.collaborative.selection.api.ISelectionEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.selection.dto.SelectionRefreshedEventPayload;
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

    private final String id;

    private final Object object;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final ISubscriptionManager subscriptionManager;

    private final Many<IPayload> sink = Sinks.many().multicast().directBestEffort();

    private final AtomicReference<Selection> currentSelection = new AtomicReference<>();

    private final IRepresentationMetadata selectionMetadata;

    public SelectionEventProcessor(IEditingContext editingContext, SelectionDescription selectionDescription, String id, Object object, String label, ISubscriptionManager subscriptionManager,
            IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry) {
        this.logger.trace("Creating the selection event processor {}", id); //$NON-NLS-1$

        this.selectionDescription = Objects.requireNonNull(selectionDescription);
        this.editingContext = Objects.requireNonNull(editingContext);
        this.id = Objects.requireNonNull(id);
        this.object = Objects.requireNonNull(object);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(representationRefreshPolicyRegistry);

        Selection selection = this.refreshSelection();
        this.currentSelection.set(selection);
        // @formatter:off
        this.selectionMetadata = SemanticRepresentationMetadata.newRepresentationMetadata(id)
                                                               .descriptionId(selectionDescription.getId())
                                                               .label(label)
                                                               .kind(Selection.KIND)
                                                               .targetObjectId(selection.getTargetObjectId())
                                                               .build();
        // @formatter:on
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.currentSelection.get();
    }

    @Override
    public IRepresentationMetadata getRepresentationMetadata() {
        return this.selectionMetadata;
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
            EmitResult emitResult = this.sink.tryEmitNext(new SelectionRefreshedEventPayload(changeDescription.getInput().getId(), selection));
            if (emitResult.isFailure()) {
                String pattern = "An error has occurred while emitting a SelectionRefreshedEventPayload: {}"; //$NON-NLS-1$
                this.logger.warn(pattern, emitResult);
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
        variableManager.put(VariableManager.SELF, this.object);
        variableManager.put(IEditingContext.EDITING_CONTEXT, this.editingContext);
        variableManager.put(GetOrCreateRandomIdProvider.PREVIOUS_REPRESENTATION_ID, this.id);

        Selection selection = new SelectionRenderer(variableManager, this.selectionDescription).render();

        this.logger.trace("Selection refreshed: {}", selection.getId()); //$NON-NLS-1$

        return selection;
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        var initialRefresh = Mono.fromCallable(() -> new SelectionRefreshedEventPayload(input.getId(), this.currentSelection.get()));
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
        this.logger.trace("Disposing the selection event processor {}", this.id); //$NON-NLS-1$

        this.subscriptionManager.dispose();

        EmitResult emitResult = this.sink.tryEmitComplete();
        if (emitResult.isFailure()) {
            String pattern = "An error has occurred while marking the publisher as complete: {}"; //$NON-NLS-1$
            this.logger.warn(pattern, emitResult);
        }
    }

}
