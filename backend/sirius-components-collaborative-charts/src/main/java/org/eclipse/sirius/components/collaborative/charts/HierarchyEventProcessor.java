/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.core.api.IEditingContext;
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

    private final IEditingContext editingContext;

    private final HierarchyContext hierarchyContext;

    private final ISubscriptionManager subscriptionManager;

    private final HierarchyCreationService hierarchyCreationService;

    private final HierarchyEventFlux hierarchyEventFlux;

    public HierarchyEventProcessor(IEditingContext editingContext, HierarchyContext hierarchyContext, ISubscriptionManager subscriptionManager, HierarchyCreationService hierarchyCreationService) {
        this.logger.trace("Creating the hierarchy event processor {}", hierarchyContext.getHierarchy().getId()); //$NON-NLS-1$

        this.editingContext = Objects.requireNonNull(editingContext);
        this.hierarchyContext = Objects.requireNonNull(hierarchyContext);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.hierarchyCreationService = Objects.requireNonNull(hierarchyCreationService);

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database. This is quite similar to the auto-refresh on loading in Sirius.
        Hierarchy hierarchy = this.hierarchyCreationService.refresh(editingContext, hierarchyContext).orElse(null);
        hierarchyContext.update(hierarchy);
        this.hierarchyEventFlux = new HierarchyEventFlux(hierarchy);

        this.logger.trace("Hierarchy refreshed: {})", hierarchy.getId()); //$NON-NLS-1$
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.hierarchyContext.getHierarchy();
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
            Hierarchy refreshedHierarchy = this.hierarchyCreationService.refresh(this.editingContext, this.hierarchyContext).orElse(null);

            this.logger.trace("Hierarchy refreshed: {}", refreshedHierarchy.getId()); //$NON-NLS-1$

            this.hierarchyContext.update(refreshedHierarchy);
            this.hierarchyEventFlux.hierarchyRefreshed(changeDescription.getInput(), refreshedHierarchy);
        }
    }

    /**
     * A hierarchy representation is refresh if there is a semantic change.
     *
     * @param changeDescription
     *            The change description
     * @return <code>true</code> if the representation should be refreshed, <code>false</code> otherwise
     */
    private boolean shouldRefresh(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        // @formatter:off
        return Flux.merge(
            this.hierarchyEventFlux.getFlux(input),
            this.subscriptionManager.getFlux(input)
        );
        // @formatter:on
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the hierarchy event processor {}", this.hierarchyContext.getHierarchy().getId()); //$NON-NLS-1$

        this.subscriptionManager.dispose();
        this.hierarchyEventFlux.dispose();
    }

}
