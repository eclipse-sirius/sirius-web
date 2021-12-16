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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.core.api.IRepresentationInput;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.representations.IRepresentationMetadata;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.web.spring.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventProcessor;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.RenameDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.dto.RenameRepresentationInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

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

    private final ISemanticRepresentationMetadata diagramMetadata;

    private final List<IDiagramEventHandler> diagramEventHandlers;

    private final ISubscriptionManager subscriptionManager;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final DiagramEventFlux diagramEventFlux;

    public DiagramEventProcessor(DiagramEventProcessorParameters parameters) {
        this.logger.trace("Creating the diagram event processor {}", parameters.getDiagramMetadata().getId()); //$NON-NLS-1$
        this.editingContext = parameters.getEditingContext();
        this.diagramContext = parameters.getDiagramContext();
        this.diagramMetadata = parameters.getDiagramMetadata();
        this.diagramEventHandlers = parameters.getDiagramEventHandlers();
        this.subscriptionManager = parameters.getSubscriptionManager();
        this.representationDescriptionSearchService = parameters.getRepresentationDescriptionSearchService();
        this.representationRefreshPolicyRegistry = parameters.getRepresentationRefreshPolicyRegistry();
        this.diagramCreationService = parameters.getDiagramCreationService();

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database. This is quite similar to the auto-refresh on loading in Sirius.
        Diagram diagram = this.diagramCreationService.refresh(this.editingContext, this.diagramMetadata, this.diagramContext).orElse(null);
        this.diagramContext.update(diagram);
        this.diagramEventFlux = new DiagramEventFlux(diagram);

        if (diagram != null) {
            this.logger.trace("Diagram refreshed: {})", diagram.getId()); //$NON-NLS-1$
        }
    }

    @Override
    public IRepresentation getRepresentation() {
        return this.diagramContext.getDiagram();
    }

    @Override
    public IRepresentationMetadata getRepresentationMetadata() {
        return this.diagramMetadata;
    }

    @Override
    public ISubscriptionManager getSubscriptionManager() {
        return this.subscriptionManager;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        IRepresentationInput effectiveInput = representationInput;
        if (representationInput instanceof RenameRepresentationInput) {
            RenameRepresentationInput renameRepresentationInput = (RenameRepresentationInput) representationInput;
            effectiveInput = new RenameDiagramInput(renameRepresentationInput.getId(), renameRepresentationInput.getEditingContextId(), renameRepresentationInput.getRepresentationId(),
                    renameRepresentationInput.getNewLabel());
        }
        if (effectiveInput instanceof IDiagramInput) {
            IDiagramInput diagramInput = (IDiagramInput) effectiveInput;

            Optional<IDiagramEventHandler> optionalDiagramEventHandler = this.diagramEventHandlers.stream().filter(handler -> handler.canHandle(diagramInput)).findFirst();

            if (optionalDiagramEventHandler.isPresent()) {
                IDiagramEventHandler diagramEventHandler = optionalDiagramEventHandler.get();
                diagramEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.diagramContext, this.diagramMetadata, diagramInput);
            } else {
                this.logger.warn("No handler found for event: {}", diagramInput); //$NON-NLS-1$
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            Diagram refreshedDiagram = this.diagramCreationService.refresh(this.editingContext, this.diagramMetadata, this.diagramContext).orElse(null);
            if (refreshedDiagram != null) {
                this.logger.trace("Diagram refreshed: {}", refreshedDiagram.getId()); //$NON-NLS-1$
            }

            this.diagramContext.reset();
            this.diagramContext.update(refreshedDiagram);
            this.diagramEventFlux.diagramRefreshed(changeDescription.getInput(), refreshedDiagram);
        }
    }

    /**
     * A diagram is refresh if there is a semantic change or if there is a diagram layout change coming from this very
     * diagram (not other diagrams).
     *
     * @param changeDescription
     *            The change description
     * @return <code>true</code> if the diagram should be refreshed, <code>false</code> otherwise
     */
    public boolean shouldRefresh(ChangeDescription changeDescription) {
        // @formatter:off
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(this.editingContext, this.diagramMetadata.getDescriptionId().toString())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
        // @formatter:on

        // @formatter:off
        return optionalDiagramDescription.flatMap(this.representationRefreshPolicyRegistry::getRepresentationRefreshPolicy)
                .orElseGet(this::getDefaultRefreshPolicy)
                .shouldRefresh(changeDescription);
        // @formatter:on
    }

    private IRepresentationRefreshPolicy getDefaultRefreshPolicy() {
        return changeDescription -> {
            return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind())
                    || (DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE.equals(changeDescription.getKind()) && changeDescription.getSourceId().equals(this.diagramContext.getDiagram().getId()));
        };
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        // @formatter:off
        return Flux.merge(
            this.diagramEventFlux.getFlux(input),
            this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the diagram event processor {}", this.diagramContext.getDiagram().getId()); //$NON-NLS-1$

        this.subscriptionManager.dispose();
        this.diagramEventFlux.dispose();
    }

}
