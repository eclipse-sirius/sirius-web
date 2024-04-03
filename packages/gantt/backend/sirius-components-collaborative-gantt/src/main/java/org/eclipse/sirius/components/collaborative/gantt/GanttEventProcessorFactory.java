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
package org.eclipse.sirius.components.collaborative.gantt;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessorFactory;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.ISubscriptionManagerFactory;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventHandler;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventProcessor;
import org.eclipse.sirius.components.collaborative.gantt.service.GanttCreationService;
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

    private final IRepresentationSearchService representationSearchService;

    private final GanttCreationService ganttCreationService;

    private final ISubscriptionManagerFactory subscriptionManagerFactory;

    private final List<IGanttEventHandler> ganttEventHandlers;

    private final IRepresentationPersistenceService representationPersistenceService;

    public GanttEventProcessorFactory(IRepresentationSearchService representationSearchService, GanttCreationService ganttCreationService, ISubscriptionManagerFactory subscriptionManagerFactory,
            List<IGanttEventHandler> ganttEventHandlers, IRepresentationPersistenceService representationPersistenceService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.ganttCreationService = Objects.requireNonNull(ganttCreationService);
        this.subscriptionManagerFactory = Objects.requireNonNull(subscriptionManagerFactory);
        this.ganttEventHandlers = Objects.requireNonNull(ganttEventHandlers);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
    }

    @Override
    public <T extends IRepresentationEventProcessor> boolean canHandle(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration) {
        return IGanttEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof GanttConfiguration;
    }

    @Override
    public <T extends IRepresentationEventProcessor> Optional<T> createRepresentationEventProcessor(Class<T> representationEventProcessorClass, IRepresentationConfiguration configuration,
            IEditingContext editingContext) {
        if (IGanttEventProcessor.class.isAssignableFrom(representationEventProcessorClass) && configuration instanceof GanttConfiguration ganttConfiguration) {
            var optionalGantt = this.representationSearchService.findById(editingContext, ganttConfiguration.getId(), Gantt.class);
            if (optionalGantt.isPresent()) {
                GanttContext ganttContext = new GanttContext(optionalGantt.get());

                IRepresentationEventProcessor ganttEventProcessor = new GanttEventProcessor(editingContext, this.subscriptionManagerFactory.create(), this.ganttCreationService,
                        this.representationSearchService, this.ganttEventHandlers, ganttContext, this.representationPersistenceService);

                return Optional.of(ganttEventProcessor).map(representationEventProcessorClass::cast);
            }
        }
        return Optional.empty();
    }
}
