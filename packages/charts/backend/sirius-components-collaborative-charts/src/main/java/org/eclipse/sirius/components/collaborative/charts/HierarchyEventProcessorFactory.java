/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to create the hierarchy event processors.
 *
 * @author sbegaudeau
 */
@Service
public class HierarchyEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationSearchService representationSearchService;

    private final HierarchyCreationService hierarchyCreationService;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    public HierarchyEventProcessorFactory(IRepresentationSearchService representationSearchService, HierarchyCreationService hierarchyCreationService,
            ISubscriptionManagerFactory subscriptionManagerFactory) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.hierarchyCreationService = Objects.requireNonNull(hierarchyCreationService);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IHierarchyEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof HierarchyConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IHierarchyEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof HierarchyConfiguration) {
            HierarchyConfiguration hierarchyConfiguration = (HierarchyConfiguration) configuration;
            var optionalHierarchy = this.representationSearchService.findById(editingContext, hierarchyConfiguration.getId(), Hierarchy.class);
            if (optionalHierarchy.isPresent()) {
                Hierarchy hierarchy = optionalHierarchy.get();

                // @formatter:off
                HierarchyContext hierarchyContext = new HierarchyContext(hierarchy);
                IRepresentationEventProcessor hierarchyEventProcessor = new HierarchyEventProcessor(editingContext, hierarchyContext,
                        this.subscriptionManagerFactory.create(), this.hierarchyCreationService, this.representationSearchService);

                return Optional.of(hierarchyEventProcessor)
                        .filter(representationEventProcessorClass::isInstance)
                        .map(representationEventProcessorClass::cast);
                // @formatter:on
            }
        }
        return Optional.empty();
    }
}
