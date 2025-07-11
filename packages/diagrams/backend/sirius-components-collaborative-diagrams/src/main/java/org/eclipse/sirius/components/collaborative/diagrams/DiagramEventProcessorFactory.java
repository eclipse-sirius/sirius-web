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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationRefreshPolicyRegistry;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.api.RepresentationEventProcessorFactoryConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInputReferencePositionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramPostProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.springframework.stereotype.Service;

/**
 * Used to create the diagram event processors.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationSearchService representationSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final List<IDiagramEventHandler> diagramEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationRefreshPolicyRegistry representationRefreshPolicyRegistry;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders;

    private final List<IDiagramPostProcessor> diagramPostProcessors;

    public DiagramEventProcessorFactory(RepresentationEventProcessorFactoryConfiguration configuration, IDiagramCreationService diagramCreationService,
            List<IDiagramEventHandler> diagramEventHandlers,
                                        IRepresentationPersistenceService representationPersistenceService, List<IDiagramInputReferencePositionProvider> diagramInputReferencePositionProviders,
                                        List<IDiagramPostProcessor> diagramPostProcessors) {
        this.representationSearchService = Objects.requireNonNull(configuration.getRepresentationSearchService());
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.diagramEventHandlers = Objects.requireNonNull(diagramEventHandlers);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.subscriptionManagerFactory = Objects.requireNonNull(configuration.getSubscriptionManagerFactory());
        this.representationDescriptionSearchService = Objects.requireNonNull(configuration.getRepresentationDescriptionSearchService());
        this.representationRefreshPolicyRegistry = Objects.requireNonNull(configuration.getRepresentationRefreshPolicyRegistry());
        this.diagramInputReferencePositionProviders = Objects.requireNonNull(diagramInputReferencePositionProviders);
        this.diagramPostProcessors = Objects.requireNonNull(diagramPostProcessors);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(representationId, List.of(Diagram.KIND));
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        var optionalDiagram = this.representationSearchService.findById(editingContext, representationId, Diagram.class);
        if (optionalDiagram.isPresent()) {
            Diagram diagram = optionalDiagram.get();
            DiagramContext diagramContext = new DiagramContext(diagram);

            var parameters = DiagramEventProcessorParameters.newDiagramEventProcessorParameters()
                    .editingContext(editingContext)
                    .diagramContext(diagramContext)
                    .diagramEventHandlers(this.diagramEventHandlers)
                    .subscriptionManager(this.subscriptionManagerFactory.create())
                    .diagramCreationService(this.diagramCreationService)
                    .representationDescriptionSearchService(this.representationDescriptionSearchService)
                    .representationRefreshPolicyRegistry(this.representationRefreshPolicyRegistry)
                    .representationPersistenceService(this.representationPersistenceService)
                    .representationSearchService(this.representationSearchService)
                    .diagramInputReferencePositionProviders(this.diagramInputReferencePositionProviders)
                    .diagramPostProcessors(this.diagramPostProcessors)
                    .build();

            IRepresentationEventProcessor diagramEventProcessor = new DiagramEventProcessor(parameters);

            return Optional.of(diagramEventProcessor);
        }
        return Optional.empty();
    }
}
