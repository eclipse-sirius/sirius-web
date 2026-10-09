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
package org.eclipse.sirius.components.collaborative.gantt.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.gantt.GanttContext;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.gantt.renderer.GanttRenderer;
import org.eclipse.sirius.components.gantt.renderer.component.GanttComponent;
import org.eclipse.sirius.components.gantt.renderer.component.GanttComponentProps;
import org.eclipse.sirius.components.gantt.renderer.events.IGanttEvent;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to create gantt representations.
 *
 * @author lfasani
 */
@Service
public class GanttCreationService implements IGanttCreationService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectSearchService objectSearchService;

    private final Timer timer;

    public GanttCreationService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IObjectSearchService objectSearchService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "gantt")
                .register(meterRegistry);
    }

    @Override
    public Gantt create(IEditingContext editingContext, GanttDescription ganttDescription, Object targetObject, GanttContext ganttContext) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(RepresentationVariables.SELF.name(), targetObject);
        variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);

        Optional<Gantt> optionalPreviousGantt = Optional.ofNullable(ganttContext).map(GanttContext::representation);
        List<IGanttEvent> events = Optional.ofNullable(ganttContext).map(GanttContext::events).orElse(List.of());

        GanttComponentProps ganttComponentProps = new GanttComponentProps(variableManager, ganttDescription, optionalPreviousGantt, events);

        Element element = new Element(GanttComponent.class, ganttComponentProps);
        Gantt newGantt = new GanttRenderer().render(element);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newGantt;
    }

}
