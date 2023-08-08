/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.gantt.renderer.GanttRenderer;
import org.eclipse.sirius.components.gantt.renderer.component.GanttComponent;
import org.eclipse.sirius.components.gantt.renderer.component.GanttComponentProps;
import org.eclipse.sirius.components.representations.Element;
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

    private final IObjectService objectService;

    private final Timer timer;

    public GanttCreationService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationPersistenceService representationPersistenceService,
            IObjectService objectService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "gantt")
                .register(meterRegistry);
    }

    @Override
    public Gantt create(String label, Object targetObject, GanttDescription ganttDescription, IEditingContext editingContext) {
        Gantt newGanttDiagram = this.doRender(label, targetObject, editingContext, ganttDescription, Optional.empty());
        return newGanttDiagram;
    }

    @Override
    public Optional<Gantt> refresh(IEditingContext editingContext, Gantt previousGantt) {
        var optionalObject = this.objectService.getObject(editingContext, previousGantt.targetObjectId());
        var optionalGanttDescription = this.representationDescriptionSearchService.findById(editingContext, previousGantt.getDescriptionId())
                .filter(GanttDescription.class::isInstance)
                .map(GanttDescription.class::cast);

        if (optionalObject.isPresent() && optionalGanttDescription.isPresent()) {
            Object object = optionalObject.get();
            GanttDescription ganttDescription = optionalGanttDescription.get();
            Gantt gantt = this.doRender(previousGantt.getLabel(), object, editingContext, ganttDescription, Optional.of(previousGantt));
            return Optional.of(gantt);
        }
        return Optional.empty();
    }

    private Gantt doRender(String label, Object targetObject, IEditingContext editingContext, GanttDescription ganttDescription, Optional<Gantt> optionalPreviousGantt) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(GanttDescription.LABEL, label);
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);


        GanttComponentProps ganttComponentProps = new GanttComponentProps(variableManager, ganttDescription, optionalPreviousGantt);

        Element element = new Element(GanttComponent.class, ganttComponentProps);
        Gantt newGantt = new GanttRenderer().render(element);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newGantt;
    }

}
