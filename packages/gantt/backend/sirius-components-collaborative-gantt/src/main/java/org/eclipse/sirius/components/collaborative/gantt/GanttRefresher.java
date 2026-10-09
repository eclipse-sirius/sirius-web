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

import java.util.ArrayList;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceStrategy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttCreationService;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventProcessor;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationRefresher;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Used to refresh representation representations.
 *
 * @author sbegaudeau
 */
@Service
public class GanttRefresher implements IRepresentationRefresher {

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationSearchService representationSearchService;

    private final IGanttCreationService ganttCreationService;

    private final IRepresentationPersistenceStrategy representationPersistenceStrategy;

    public GanttRefresher(IObjectSearchService objectSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationSearchService representationSearchService, IGanttCreationService ganttCreationService, IRepresentationPersistenceStrategy representationPersistenceStrategy) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.ganttCreationService = Objects.requireNonNull(ganttCreationService);
        this.representationPersistenceStrategy = Objects.requireNonNull(representationPersistenceStrategy);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        var representation = representationEventProcessor.getRepresentation();
        return representation instanceof Gantt
                && (this.isRegularRefresh(changeDescription) || this.isReloadRefresh(changeDescription, representation));
    }

    private boolean isRegularRefresh(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind()) || GanttChangeKind.GANTT_REPRESENTATION_UPDATE.equals(changeDescription.getKind());
    }

    private boolean isReloadRefresh(ChangeDescription changeDescription, IRepresentation representation) {
        return changeDescription.getKind().equals(ChangeKind.RELOAD_REPRESENTATION) && changeDescription.getSourceId().equals(representation.getId());
    }

    @Override
    public void refresh(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        if (representationEventProcessor instanceof IGanttEventProcessor ganttEventProcessor && ganttEventProcessor.getRepresentation() instanceof Gantt existingGantt) {
            if (this.isRegularRefresh(changeDescription)) {
                var optionalGanttDescription = this.representationDescriptionSearchService.findById(editingContext, existingGantt.getDescriptionId())
                        .filter(GanttDescription.class::isInstance)
                        .map(GanttDescription.class::cast);

                var optionalObject = this.objectSearchService.getObject(editingContext, existingGantt.getTargetObjectId());
                if (optionalGanttDescription.isPresent() && optionalObject.isPresent()) {
                    var ganttDescription = optionalGanttDescription.get();
                    var object = optionalObject.get();

                    var refreshedGantt = this.ganttCreationService.create(editingContext, ganttDescription, object, new GanttContext(existingGantt, ganttEventProcessor.getRepresentationContext().events()));
                    this.representationPersistenceStrategy.applyPersistenceStrategy(changeDescription.getCause(), editingContext, refreshedGantt);
                    ganttEventProcessor.update(changeDescription.getCause(), new GanttContext(refreshedGantt, new ArrayList<>()));
                }
            } else if (this.isReloadRefresh(changeDescription, existingGantt)) {
                this.representationSearchService.findById(editingContext, existingGantt.getId(), Gantt.class)
                        .ifPresent(gantt -> ganttEventProcessor.update(changeDescription.getCause(), new GanttContext(gantt, new ArrayList<>())));
            }
        }
    }
}
