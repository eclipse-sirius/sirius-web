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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Used to create the hierarchy event processors.
 *
 * @author sbegaudeau
 */
@Service
public class HierarchyEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IRepresentationPersistenceService representationPersistenceService;

    private final IRepresentationSearchService representationSearchService;

    private final HierarchyCreationService hierarchyCreationService;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    public HierarchyEventProcessorFactory(IRepresentationPersistenceService representationPersistenceService, IRepresentationSearchService representationSearchService, HierarchyCreationService hierarchyCreationService,
            ISubscriptionManagerFactory subscriptionManagerFactory) {
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.hierarchyCreationService = Objects.requireNonNull(hierarchyCreationService);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(representationId, List.of(
                IRepresentation.KIND_PREFIX + "?type=ForceDirectedTree",
                IRepresentation.KIND_PREFIX + "?type=TreeMap",
                IRepresentation.KIND_PREFIX + "?type=ZoomableCirclePacking")
        );
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        var optionalHierarchy = this.representationSearchService.findById(editingContext, representationId, Hierarchy.class);
        if (optionalHierarchy.isPresent()) {
            Hierarchy hierarchy = optionalHierarchy.get();

            HierarchyContext hierarchyContext = new HierarchyContext(hierarchy);
            IRepresentationEventProcessor hierarchyEventProcessor = new HierarchyEventProcessor(editingContext, hierarchyContext,
                    this.subscriptionManagerFactory.create(), this.hierarchyCreationService, this.representationPersistenceService, this.representationSearchService);

            return Optional.of(hierarchyEventProcessor);
        }

        return Optional.empty();
    }
}
