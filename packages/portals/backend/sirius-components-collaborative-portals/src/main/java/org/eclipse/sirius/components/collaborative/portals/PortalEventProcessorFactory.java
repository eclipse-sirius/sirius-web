/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.portals.api.IPortalEventHandler;
import org.eclipse.sirius.components.collaborative.portals.api.PortalConfiguration;
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

    private final IRepresentationPersistenceService representationPersistenceService;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final List<IPortalEventHandler> portalEventHandlers;

    public PortalEventProcessorFactory(IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService,
            ISubscriptionManagerFactory subscriptionManagerFactory, List<IPortalEventHandler> portalEventHandlers) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.portalEventHandlers = Objects.requireNonNull(portalEventHandlers);
    }

    @Override
    public boolean canHandle(IRepresentationConfiguration configuration) {
        return configuration instanceof PortalConfiguration;
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IRepresentationConfiguration configuration, IEditingContext editingContext) {
        if (configuration instanceof PortalConfiguration portalConfiguration) {
            var optionalPortal = this.representationSearchService.findById(editingContext, portalConfiguration.getId(), Portal.class);
            if (optionalPortal.isPresent()) {
                Portal portal = optionalPortal.get();
                var portalEventProcessor = new PortalEventProcessor(editingContext, this.representationSearchService, this.representationPersistenceService, this.portalEventHandlers, this.subscriptionManagerFactory.create(), portal);
                return Optional.of(portalEventProcessor);
            }
        }
        return Optional.empty();
    }

}
