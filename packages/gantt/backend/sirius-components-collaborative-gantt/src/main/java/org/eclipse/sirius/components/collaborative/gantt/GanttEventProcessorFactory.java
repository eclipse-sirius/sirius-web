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
package org.eclipse.sirius.components.collaborative.gantt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventHandler;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventProcessorInitializer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.gantt.Gantt;
import org.springframework.stereotype.Service;

/**
 * Used to create the gantt event processors.
 *
 * @author lfasani
 */
@Service
public class GanttEventProcessorFactory implements IRepresentationEventProcessorFactory {

    private final IGanttEventProcessorInitializer ganttEventProcessorInitializer;

    private final IRepresentationSearchService representationSearchService;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final List<IGanttEventHandler> ganttEventHandlers;

    public GanttEventProcessorFactory(IGanttEventProcessorInitializer ganttEventProcessorInitializer, IRepresentationSearchService representationSearchService, ISubscriptionManagerFactory subscriptionManagerFactory, List<IGanttEventHandler> ganttEventHandlers) {
        this.ganttEventProcessorInitializer = Objects.requireNonNull(ganttEventProcessorInitializer);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.ganttEventHandlers = Objects.requireNonNull(ganttEventHandlers);
    }


    @Override
    public boolean canHandle(IEditingContext editingContext, String representationId) {
        return this.representationSearchService.existByIdAndKind(editingContext, representationId, List.of(Gantt.KIND));
    }

    @Override
    public Optional<IRepresentationEventProcessor> createRepresentationEventProcessor(IEditingContext editingContext, String representationId) {
        var optionalGantt = this.ganttEventProcessorInitializer.getRefreshedRepresentation(editingContext, representationId);
        if (optionalGantt.isPresent()) {
            Gantt gantt = optionalGantt.get();
            GanttContext ganttContext = new GanttContext(gantt, new ArrayList<>());
            IRepresentationEventProcessor ganttEventProcessor = new GanttEventProcessor(editingContext, this.subscriptionManagerFactory.create(), this.ganttEventHandlers, ganttContext);

            return Optional.of(ganttEventProcessor);
        }

        return Optional.empty();
    }
}
