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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManager;
import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramCreationParameters;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventProcessor;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramRefreshManager;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.services.api.dto.IRepresentationInput;
import org.eclipse.sirius.web.services.api.monitoring.IStopWatch;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.representations.RenameRepresentationInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;

/**
 * Reacts to input that target a specific diagram, and {@link #getDiagramUpdates() publishes} updated versions of the
 * diagram to interested subscribers.
 *
 * @author sbegaudeau
 * @author pcdavid
 */
public class DiagramEventProcessor implements IDiagramEventProcessor {

    private final Logger logger = LoggerFactory.getLogger(DiagramEventProcessor.class);

    private DiagramCreationParameters diagramCreationParameters;

    private final IEditingContext editingContext;

    private final List<IDiagramEventHandler> diagramEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IDiagramRefreshManager diagramRefreshManager;

    public DiagramEventProcessor(DiagramCreationParameters diagramCreationParameters, IEditingContext editingContext, List<IDiagramEventHandler> diagramEventHandlers,
            ISubscriptionManager subscriptionManager, IDiagramRefreshManager diagramRefreshManager) {
        this.diagramCreationParameters = Objects.requireNonNull(diagramCreationParameters);
        this.editingContext = Objects.requireNonNull(editingContext);
        this.diagramEventHandlers = Objects.requireNonNull(diagramEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.diagramRefreshManager = Objects.requireNonNull(diagramRefreshManager);

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database. This is quite similar to the auto-refresh on loading in Sirius.
        this.diagramRefreshManager.initialize(editingContext.getProjectId(), diagramCreationParameters);
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.diagramRefreshManager.getDiagram();
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public Optional<EventHandlerResponse> handle(IRepresentationInput representationInput, Context context) {
        if (representationInput instanceof IDiagramInput) {
            IDiagramInput diagramInput = (IDiagramInput) representationInput;

            Optional<IDiagramEventHandler> optionalDiagramEventHandler = this.diagramEventHandlers.stream().filter(handler -> handler.canHandle(diagramInput)).findFirst();

            if (optionalDiagramEventHandler.isPresent()) {
                IDiagramEventHandler diagramEventHandler = optionalDiagramEventHandler.get();
                return Optional.of(diagramEventHandler.handle(this.editingContext, this.diagramRefreshManager.getDiagram(), diagramInput));
            } else {
                this.logger.warn("No handler found for event: {}", diagramInput); //$NON-NLS-1$
            }
        } else if (representationInput instanceof RenameRepresentationInput) {
            String newName = ((RenameRepresentationInput) representationInput).getNewLabel();
            this.diagramCreationParameters = DiagramCreationParameters.newDiagramCreationParameters(this.diagramCreationParameters).label(newName).build();
        }
        return Optional.empty();
    }

    @Override
    public void refresh(IStopWatch stopWatch) {
        this.diagramRefreshManager.refresh(this.editingContext.getProjectId(), this.diagramCreationParameters);
    }

    @Override
    public Flux<IPayload> getOutputEvents() {
        return Flux.merge(this.diagramRefreshManager.getFlux(), this.subscriptionManager.getFlux());
    }

    @Override
    public void dispose() {
        this.subscriptionManager.dispose();
        this.diagramRefreshManager.dispose();
    }

    @Override
    public void preDestroy() {
        this.diagramRefreshManager.preDestroy();
    }
}
