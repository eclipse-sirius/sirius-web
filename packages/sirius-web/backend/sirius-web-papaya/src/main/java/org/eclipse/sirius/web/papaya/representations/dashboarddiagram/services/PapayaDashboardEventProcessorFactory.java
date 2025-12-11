/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.dashboarddiagram.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceStrategy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.api.RepresentationEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramEventProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramEventProcessorParameters;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInputReferencePositionProvider;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.papaya.representations.dashboarddiagram.PapayaDashboardDiagramDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * A Specific Dashboard Event Processor Factory for Papaya.
 *
 * @author fbarbin
 */
@Service
public class PapayaDashboardEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationSearchService representationSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final List<IDiagramEventHandler> diagramEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders;

    private final List<IDiagramEventConsumer> diagramEventConsumers;

    private final List<IRepresentationMetadataProvider> representationMetadataProviders;

    private final IRepresentationPersistenceStrategy representationPersistenceStrategy;

    public PapayaDashboardEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, IDiagramCreationService diagramCreationService, List<IDiagramEventHandler> diagramEventHandlers,
            org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceStrategy representationPersistenceStrategy,
            List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders, List<IDiagramEventConsumer> diagramEventConsumers, List<IRepresentationMetadataProvider> representationMetadataProviders) {
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.diagramEventHandlers = Objects.requireNonNull(diagramEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
        this.diagramInputReferencePositionProviders = Objects.requireNonNull(diagramInputReferencePositionProviders);
        this.diagramEventConsumers = Objects.requireNonNull(diagramEventConsumers);
        this.representationMetadataProviders = Objects.requireNonNull(representationMetadataProviders);
        this.representationPersistenceStrategy = Objects.requireNonNull(representationPersistenceStrategy);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return PapayaDashboardDiagramDescriptionProvider.DASHBOARD_REPRESENTATION_ID.equals(representationId);
    }

    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        String targetObjectId = editingContext.getId();
        var optionalRepresentationMetadata = this.representationMetadataProviders.stream()
                .flatMap(provider -> provider.getMetadata(editingContext.getId(), representationId).stream())
                .findFirst();
        String diagramDescriptionId = optionalRepresentationMetadata.map(RepresentationMetadata::descriptionId).orElse("");

        //The diagram does not exist (transient)
        var diagram = buildTransientDiagram(representationId, diagramDescriptionId, targetObjectId);
        var optionalDiagramDescription = representationDescriptionSearchService.findById(editingContext, diagramDescriptionId)
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
        if (optionalDiagramDescription.isPresent()) {
            var parameters = DiagramEventProcessorParameters.newDiagramEventProcessorParameters()
                    .editingContext(editingContext)
                    .diagramContext(new DiagramContext(diagram))
                    .diagramEventHandlers(this.diagramEventHandlers)
                    .subscriptionManager(this.subscriptionManagerFactory.create())
                    .diagramCreationService(this.diagramCreationService)
                    .representationDescriptionSearchService(this.representationDescriptionSearchService)
                    .representationPersistenceStrategy(this.representationPersistenceStrategy)
                    .representationRefreshPolicyRegistry(this.representationRefreshPolicyRegistry)
                    .representationSearchService(this.representationSearchService)
                    .diagramInputReferencePositionProviders(this.diagramInputReferencePositionProviders)
                    .diagramEventConsumers(this.diagramEventConsumers)
                    .build();

            IRepresentationEventProcessor diagramEventProcessor = new DiagramEventProcessor(parameters);
            return Optional.of(diagramEventProcessor);
        }

        return Optional.empty();
    }

    private Diagram buildTransientDiagram(String representationId, String diagramDescriptionId, String targetObjectId) {
        return Diagram.newDiagram(representationId)
                .descriptionId(diagramDescriptionId)
                .targetObjectId(targetObjectId)
                .edges(List.of())
                .nodes(List.of())
                .build();
    }
}
