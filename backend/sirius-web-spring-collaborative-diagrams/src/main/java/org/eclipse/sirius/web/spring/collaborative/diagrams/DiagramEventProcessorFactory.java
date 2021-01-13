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

import org.eclipse.sirius.web.collaborative.api.services.IRepresentationConfiguration;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.web.collaborative.api.services.ISubscriptionManagerFactory;
import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramConfiguration;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramEventProcessor;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.springframework.stereotype.Service;

/**
 * Used to create the diagram event processors.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IDiagramService diagramService;

    private final IDiagramCreationService diagramCreationService;

    private final List<IDiagramEventHandler> diagramEventHandlers;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    public DiagramEventProcessorFactory(IDiagramService diagramService, IDiagramCreationService diagramCreationService, List<IDiagramEventHandler> diagramEventHandlers,
            ISubscriptionManagerFactory subscriptionManagerFactory) {
        this.diagramService = Objects.requireNonNull(diagramService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.diagramEventHandlers = Objects.requireNonNull(diagramEventHandlers);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IDiagramEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof DiagramConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IDiagramEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof DiagramConfiguration) {
            DiagramConfiguration diagramConfiguration = (DiagramConfiguration) configuration;
            var optionalDiagram = this.diagramService.findById(diagramConfiguration.getId());
            if (optionalDiagram.isPresent()) {
                Diagram diagram = optionalDiagram.get();

                // @formatter:off
                DiagramContext diagramContext = new DiagramContext(diagram);
                IRepresentationEventProcessor diagramEventProcessor = new DiagramEventProcessor(editingContext, diagramContext,
                        this.diagramEventHandlers, this.subscriptionManagerFactory.create(), this.diagramCreationService);

                return Optional.of(diagramEventProcessor)
                        .filter(representationEventProcessorClass::isInstance)
                        .map(representationEventProcessorClass::cast);
                // @formatter:on
            }
        }
        return Optional.empty();
    }
}
