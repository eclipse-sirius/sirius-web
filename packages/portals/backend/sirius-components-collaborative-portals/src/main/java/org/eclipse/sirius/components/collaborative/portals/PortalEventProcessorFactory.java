/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.portals;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceStrategy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.portals.Portal;
import org.springframework.stereotype.Service;

/**
 * Used to create the portal event processors.
 *
 * @author pcdavid
 */
@Service
public class PortalEventProcessorFactory implements IRepresentationEventProcessorFactory {
    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceStrategy representationPersistenceStrategy;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final List<IPortalEventHandler> portalEventHandlers;

    public PortalEventProcessorFactory(IRepresentationSearchService representationSearchService, IRepresentationPersistenceStrategy representationPersistenceStrategy,
            ISubscriptionManagerFactory subscriptionManagerFactory, List<IPortalEventHandler> portalEventHandlers) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceStrategy = Objects.requireNonNull(representationPersistenceStrategy);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.portalEventHandlers = Objects.requireNonNull(portalEventHandlers);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(editingContext, representationId , List.of(Portal.KIND));
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        var optionalPortal = this.representationSearchService.findById(editingContext, representationId, Portal.class);
        if (optionalPortal.isPresent()) {
            Portal portal = optionalPortal.get();
            var portalEventProcessor = new PortalEventProcessor(editingContext, this.representationSearchService, this.representationPersistenceStrategy, this.portalEventHandlers, this.subscriptionManagerFactory.create(), portal);
            return Optional.of(portalEventProcessor);
        }

        return Optional.empty();
    }

}
