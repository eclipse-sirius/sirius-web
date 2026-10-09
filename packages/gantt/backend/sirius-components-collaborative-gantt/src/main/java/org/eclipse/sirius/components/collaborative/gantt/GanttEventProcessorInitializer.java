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
package org.eclipse.sirius.components.collaborative.gantt;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttCreationService;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventProcessorInitializer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.springframework.stereotype.Service;

/**
 * Used to perform the initial refresh of the representation representation for its event processor.
 *
 * @author sbegaudeau
 */
@Service
public class GanttEventProcessorInitializer implements IGanttEventProcessorInitializer {

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationSearchService representationSearchService;

    private final IGanttCreationService ganttCreationService;

    public GanttEventProcessorInitializer(IObjectSearchService objectSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationSearchService representationSearchService, IGanttCreationService ganttCreationService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.ganttCreationService = Objects.requireNonNull(ganttCreationService);
    }

    @Override
    public Optional<Gantt> getRefreshedRepresentation(IEditingContext editingContext, String representationId) {
        var optionalGantt = this.representationSearchService.findById(editingContext, representationId, Gantt.class);
        if (optionalGantt.isPresent()) {
            Gantt previousGantt = optionalGantt.get();

            var optionalGanttDescription = this.representationDescriptionSearchService.findById(editingContext, previousGantt.getDescriptionId())
                    .filter(GanttDescription.class::isInstance)
                    .map(GanttDescription.class::cast);

            var optionalObject = this.objectSearchService.getObject(editingContext, previousGantt.getTargetObjectId());

            if (optionalGanttDescription.isPresent() && optionalObject.isPresent()) {
                var ganttDescription = optionalGanttDescription.get();
                var object = optionalObject.get();

                return Optional.of(this.ganttCreationService.create(editingContext, ganttDescription, object, new GanttContext(previousGantt, List.of())));
            }
        }
        return Optional.empty();
    }
}
