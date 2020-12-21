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
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventProcessor;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.services.api.dto.IRepresentationInput;
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

    private final IEditingContext editingContext;

    private final IDiagramContext diagramContext;

    private final List<IDiagramEventHandler> diagramEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IDiagramCreationService diagramCreationService;

    private final DiagramEventFlux diagramEventFlux;

    public DiagramEventProcessor(IEditingContext editingContext, IDiagramContext diagramContext, List<IDiagramEventHandler> diagramEventHandlers, ISubscriptionManager subscriptionManager,
            IDiagramCreationService diagramCreationService) {
        this.editingContext = Objects.requireNonNull(editingContext);
        this.diagramContext = Objects.requireNonNull(diagramContext);
        this.diagramEventHandlers = Objects.requireNonNull(diagramEventHandlers);
        this.subscriptionManager = Objects.requireNonNull(subscriptionManager);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database. This is quite similar to the auto-refresh on loading in Sirius.
        Diagram diagram = this.diagramCreationService.refresh(editingContext, diagramContext).orElse(null);
        diagramContext.update(diagram);
        this.diagramEventFlux = new DiagramEventFlux(diagram);
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.diagramContext.getDiagram();
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
                EventHandlerResponse eventHandlerResponse = diagramEventHandler.handle(this.editingContext, this.diagramContext, diagramInput);
                if (eventHandlerResponse.getShouldRefreshPredicate().test(this.diagramContext.getDiagram())) {
                    this.refresh();
                }
                return Optional.of(eventHandlerResponse);
            } else {
                this.logger.warn("No handler found for event: {}", diagramInput); //$NON-NLS-1$
            }
        } else if (representationInput instanceof RenameRepresentationInput) {
            String newName = ((RenameRepresentationInput) representationInput).getNewLabel();
            Diagram diagram = this.diagramContext.getDiagram();

            // @formatter:off
            Diagram renamedDiagram = Diagram.newDiagram(diagram)
                    .label(newName)
                    .build();
            // @formatter:on

            this.diagramContext.update(renamedDiagram);
            this.diagramEventFlux.diagramRefreshed(renamedDiagram);
        }
        return Optional.empty();
    }

    @Override
    public void refresh() {
        Diagram refreshedDiagram = this.diagramCreationService.refresh(this.editingContext, this.diagramContext).orElse(null);
        this.diagramContext.update(refreshedDiagram);
        this.diagramEventFlux.diagramRefreshed(refreshedDiagram);
    }

    @Override
    public Flux<IPayload> getOutputEvents() {
        return Flux.merge(this.diagramEventFlux.getFlux(), this.subscriptionManager.getFlux());
    }

    @Override
    public void dispose() {
        this.subscriptionManager.dispose();
        this.diagramEventFlux.dispose();
    }

    @Override
    public void preDestroy() {
        this.diagramEventFlux.preDestroy();
    }
}
