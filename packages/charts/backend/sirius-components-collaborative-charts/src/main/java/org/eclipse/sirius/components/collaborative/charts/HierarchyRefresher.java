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

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.charts.api.IHierarchyCreationService;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationRefresher;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Used to refresh hierarchy representations.
 *
 * @author sbegaudeau
 */
@Service
public class HierarchyRefresher implements IRepresentationRefresher {

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationSearchService representationSearchService;

    private final IHierarchyCreationService hierarchyCreationService;

    private final IRepresentationPersistenceService representationPersistenceService;

    public HierarchyRefresher(IObjectSearchService objectSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationSearchService representationSearchService, IHierarchyCreationService hierarchyCreationService, IRepresentationPersistenceService representationPersistenceService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.hierarchyCreationService = Objects.requireNonNull(hierarchyCreationService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        var representation = representationEventProcessor.getRepresentation();
        return representation instanceof Hierarchy
                && (this.isRegularRefresh(changeDescription) || this.isReloadRefresh(changeDescription, representation));
    }

    private boolean isRegularRefresh(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }

    private boolean isReloadRefresh(ChangeDescription changeDescription, IRepresentation representation) {
        return changeDescription.getKind().equals(ChangeKind.RELOAD_REPRESENTATION) && changeDescription.getSourceId().equals(representation.getId());
    }

    @Override
    public void refresh(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        if (representationEventProcessor instanceof IHierarchyEventProcessor hierarchyEventProcessor && hierarchyEventProcessor.getRepresentation() instanceof Hierarchy existingHierarchy) {
            if (this.isRegularRefresh(changeDescription)) {
                var optionalHierarchyDescription = this.representationDescriptionSearchService.findById(editingContext, existingHierarchy.getDescriptionId())
                        .filter(HierarchyDescription.class::isInstance)
                        .map(HierarchyDescription.class::cast);

                var optionalObject = this.objectSearchService.getObject(editingContext, existingHierarchy.getTargetObjectId());
                if (optionalHierarchyDescription.isPresent() && optionalObject.isPresent()) {
                    var hierarchyDescription = optionalHierarchyDescription.get();
                    var object = optionalObject.get();

                    var refreshedHierarchy = this.hierarchyCreationService.create(editingContext, hierarchyDescription, object, new HierarchyContext(existingHierarchy));
                    this.representationPersistenceService.save(changeDescription.getInput(), editingContext, refreshedHierarchy);
                    hierarchyEventProcessor.update(changeDescription.getInput(), refreshedHierarchy);
                }
            } else if (this.isReloadRefresh(changeDescription, existingHierarchy)) {
                this.representationSearchService.findById(editingContext, existingHierarchy.getId(), Hierarchy.class)
                        .ifPresent(hierarchy -> hierarchyEventProcessor.update(changeDescription.getInput(), hierarchy));
            }
        }
    }
}
