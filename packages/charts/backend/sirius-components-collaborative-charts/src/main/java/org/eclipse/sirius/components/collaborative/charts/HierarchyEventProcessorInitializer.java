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
package org.eclipse.sirius.components.collaborative.charts;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.charts.api.IHierarchyCreationService;
import org.eclipse.sirius.components.collaborative.charts.api.IHierarchyEventProcessorInitializer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to perform the initial refresh of the hierarchy representation for its event processor.
 *
 * @author sbegaudeau
 */
@Service
public class HierarchyEventProcessorInitializer implements IHierarchyEventProcessorInitializer {

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationSearchService representationSearchService;

    private final IHierarchyCreationService hierarchyCreationService;

    public HierarchyEventProcessorInitializer(IObjectSearchService objectSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationSearchService representationSearchService, IHierarchyCreationService hierarchyCreationService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.hierarchyCreationService = Objects.requireNonNull(hierarchyCreationService);
    }

    @Override
    public Optional<Hierarchy> getRefreshedRepresentation(IEditingContext editingContext, String representationId) {
        var optionalHierarchy = this.representationSearchService.findById(editingContext, representationId, Hierarchy.class);
        if (optionalHierarchy.isPresent()) {
            Hierarchy previousHierarchy = optionalHierarchy.get();

            var optionalHierarchyDescription = this.representationDescriptionSearchService.findById(editingContext, previousHierarchy.getDescriptionId())
                    .filter(HierarchyDescription.class::isInstance)
                    .map(HierarchyDescription.class::cast);

            var optionalObject = this.objectSearchService.getObject(editingContext, previousHierarchy.getTargetObjectId());

            if (optionalHierarchyDescription.isPresent() && optionalObject.isPresent()) {
                var hierarchyDescription = optionalHierarchyDescription.get();
                var object = optionalObject.get();

                return Optional.of(this.hierarchyCreationService.create(editingContext, hierarchyDescription, object, new HierarchyContext(previousHierarchy)));
            }
        }
        return Optional.empty();
    }
}
