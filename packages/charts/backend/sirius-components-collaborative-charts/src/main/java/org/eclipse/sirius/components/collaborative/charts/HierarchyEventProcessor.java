/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.charts;

import java.util.Objects;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to input that target a specific hierarchy representation.
 *
 * @author sbegaudeau
 */
public class HierarchyEventProcessor implements IHierarchyEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(HierarchyEventProcessor.class);

    private final ISubscriptionManager subscriptionManager;

    private final HierarchyEventFlux hierarchyEventFlux;

    private HierarchyContext hierarchyContext;

    public HierarchyEventProcessor(HierarchyContext hierarchyContext, ISubscriptionManager subscriptionManager) {
        this.hierarchyContext = Objects.requireNonNull(hierarchyContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.hierarchyEventFlux = new HierarchyEventFlux(hierarchyContext.hierarchy());
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.hierarchyContext.hierarchy();
    }

    @Override
    public void update(IInput input, Hierarchy hierarchy) {
        this.hierarchyContext = new HierarchyContext(hierarchy);
        this.hierarchyEventFlux.hierarchyRefreshed(input, hierarchy);
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
        // To be removed
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        return Flux.merge(
            this.hierarchyEventFlux.getFlux(input),
            this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the hierarchy event processor {}", this.hierarchyContext.hierarchy().getId());

        this.subscriptionManager.dispose();
        this.hierarchyEventFlux.dispose();
    }

}
