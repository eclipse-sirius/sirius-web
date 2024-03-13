/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManager;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInputReferencePositionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReferencePosition;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RenameDiagramInput;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Reacts to input that target a specific diagram, and {@link #getOutputEvents(IInput)}  publishes} updated versions of the
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

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final DiagramEventFlux diagramEventFlux;

    private final List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders;

    private UUID currentRevisionId = UUID.randomUUID();

    private String currentRevisionCause = DiagramRefreshedEventPayload.CAUSE_REFRESH;

    public DiagramEventProcessor(DiagramEventProcessorParameters parameters) {
        this.logger.trace("Creating the diagram event processor {}", parameters.diagramContext().getDiagram().getId());

        this.editingContext = parameters.editingContext();
        this.diagramContext = parameters.diagramContext();
        this.diagramEventHandlers = parameters.diagramEventHandlers();
        this.subscriptionManager = parameters.subscriptionManager();
        this.representationDescriptionSearchService = parameters.representationDescriptionSearchService();
        this.representationRefreshPolicyRegistry = parameters.representationRefreshPolicyRegistry();
        this.representationPersistenceService = parameters.representationPersistenceService();
        this.diagramCreationService = parameters.diagramCreationService();
        this.diagramInputReferencePositionProviders = parameters.diagramInputReferencePositionProviders();

        // We automatically refresh the representation before using it since things may have changed since the moment it
        // has been saved in the database. This is quite similar to the auto-refresh on loading in Sirius.
        Diagram diagram = this.diagramCreationService.refresh(this.editingContext, this.diagramContext).orElse(null);
        this.representationPersistenceService.save(null, parameters.editingContext(), diagram);
        this.diagramContext.update(diagram);
        this.diagramEventFlux = new DiagramEventFlux(diagram);

        if (diagram != null) {
            this.logger.trace("Diagram refreshed: {})", diagram.getId());
        }
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
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IRepresentationInput representationInput) {
        if (representationInput instanceof LayoutDiagramInput layoutDiagramInput) {
            if (layoutDiagramInput.id().equals(this.currentRevisionId)) {
                var diagram = this.diagramContext.getDiagram();
                var nodeLayoutData = layoutDiagramInput.diagramLayoutData().nodeLayoutData().stream()
                        .collect(Collectors.toMap(
                                NodeLayoutDataInput::id,
                                nodeLayoutDataInput -> new NodeLayoutData(nodeLayoutDataInput.id(), nodeLayoutDataInput.position(), nodeLayoutDataInput.size(), nodeLayoutDataInput.resizedByUser())
                        ));

                var layoutData = new DiagramLayoutData(nodeLayoutData, Map.of(), Map.of());
                var laidOutDiagram = Diagram.newDiagram(diagram)
                        .layoutData(layoutData)
                        .build();

                this.representationPersistenceService.save(layoutDiagramInput, this.editingContext, laidOutDiagram);
                this.diagramContext.reset();
                this.diagramContext.update(laidOutDiagram);
                this.diagramEventFlux.diagramRefreshed(layoutDiagramInput.id(), laidOutDiagram, DiagramRefreshedEventPayload.CAUSE_LAYOUT, null);

                this.currentRevisionCause = DiagramRefreshedEventPayload.CAUSE_LAYOUT;

                payloadSink.tryEmitValue(new SuccessPayload(layoutDiagramInput.id()));
            } else {
                payloadSink.tryEmitValue(new SuccessPayload(layoutDiagramInput.id()));
            }

            return;
        }

        IRepresentationInput effectiveInput = representationInput;
        if (representationInput instanceof RenameRepresentationInput renameRepresentationInput) {
            effectiveInput = new RenameDiagramInput(renameRepresentationInput.id(), renameRepresentationInput.editingContextId(), renameRepresentationInput.representationId(),
                    renameRepresentationInput.newLabel());
        }
        if (effectiveInput instanceof IDiagramInput diagramInput) {
            Optional<IDiagramEventHandler> optionalDiagramEventHandler = this.diagramEventHandlers.stream().filter(handler -> handler.canHandle(diagramInput)).findFirst();

            if (optionalDiagramEventHandler.isPresent()) {
                IDiagramEventHandler diagramEventHandler = optionalDiagramEventHandler.get();
                diagramEventHandler.handle(payloadSink, changeDescriptionSink, this.editingContext, this.diagramContext, diagramInput);
            } else {
                this.logger.warn("No handler found for event: {}", diagramInput);
            }
        }
    }

    @Override
    public void refresh(ChangeDescription changeDescription) {
        if (this.shouldRefresh(changeDescription)) {
            Diagram refreshedDiagram = this.diagramCreationService.refresh(this.editingContext, this.diagramContext).orElse(null);
            this.representationPersistenceService.save(changeDescription.getInput(), this.editingContext, refreshedDiagram);

            if (refreshedDiagram != null) {
                this.logger.trace("Diagram refreshed: {}", refreshedDiagram.getId());
            }

            this.diagramContext.reset();
            this.diagramContext.update(refreshedDiagram);

            this.currentRevisionId = changeDescription.getInput().id();
            this.currentRevisionCause = DiagramRefreshedEventPayload.CAUSE_REFRESH;

            ReferencePosition referencePosition = this.getReferencePosition(changeDescription.getInput());
            this.diagramEventFlux.diagramRefreshed(changeDescription.getInput().id(), refreshedDiagram, DiagramRefreshedEventPayload.CAUSE_REFRESH, referencePosition);
        }
    }

    private ReferencePosition getReferencePosition(IInput diagramInput) {
        return this.diagramInputReferencePositionProviders.stream()
                .filter(handler -> handler.canHandle(diagramInput))
                .findFirst()
                .map(provider -> provider.getReferencePosition(diagramInput, this.diagramContext))
                .orElse(null);
    }

    /**
     * A diagram is refresh if there is a semantic change or if there is a diagram layout change coming from this very
     * diagram (not other diagrams).
     *
     * @param changeDescription
     *         The change description
     * @return <code>true</code> if the diagram should be refreshed, <code>false</code> otherwise
     */
    public boolean shouldRefresh(ChangeDescription changeDescription) {
        Diagram diagram = this.diagramContext.getDiagram();
        // @formatter:off
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(this.editingContext, diagram.getDescriptionId())
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
            boolean shouldRefresh = false;
            shouldRefresh = shouldRefresh || ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
            if (!shouldRefresh && changeDescription.getSourceId().equals(this.diagramContext.getDiagram().getId())) {
                shouldRefresh = shouldRefresh || DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE.equals(changeDescription.getKind());
                shouldRefresh = shouldRefresh || DiagramChangeKind.DIAGRAM_ELEMENT_VISIBILITY_CHANGE.equals(changeDescription.getKind());
                shouldRefresh = shouldRefresh || DiagramChangeKind.DIAGRAM_ELEMENT_COLLAPSING_STATE_CHANGE.equals(changeDescription.getKind());
            }
            return shouldRefresh;
        };
    }

    @Override
    public Flux<IPayload> getOutputEvents(IInput input) {
        // @formatter:off
        return Flux.merge(
            this.diagramEventFlux.getFlux(this.currentRevisionId, this.currentRevisionCause),
            this.subscriptionManager.getFlux(input)
        );
    }

    @Override
    public void dispose() {
        this.logger.trace("Disposing the diagram event processor {}", this.diagramContext.getDiagram().getId());

        this.subscriptionManager.dispose();
        this.diagramEventFlux.dispose();
    }

}
