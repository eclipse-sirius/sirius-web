/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramCreationParameters;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramRefreshManager;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to manage the lifecycle of the diagram and its palette.
 *
 * @author sbegaudeau
 */
public class DiagramRefreshManager implements IDiagramRefreshManager {

    private final IRepresentationService representationService;

    private final IDiagramService diagramService;

    private final ILayoutService layoutService;

    private final Timer timer;

    public DiagramRefreshManager(IRepresentationService representationService, IDiagramService diagramService, ILayoutService layoutService, MeterRegistry meterRegistry) {
        this.representationService = Objects.requireNonNull(representationService);
        this.diagramService = Objects.requireNonNull(diagramService);
        this.layoutService = Objects.requireNonNull(layoutService);
        // @formatter:off
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "diagram") //$NON-NLS-1$
                .register(meterRegistry);
        // @formatter:on
    }

    /**
     * This method is used to refresh the diagram and its palette and emit an event on the flux.
     *
     * @param projectId
     *            The identifier of the project in which the diagram will be saved
     * @param diagramCreationParameters
     *            The parameters of the diagram to create
     */
    @Override
    public Diagram refresh(DiagramCreationParameters diagramCreationParameters, Diagram previousDiagram) {
        long start = System.currentTimeMillis();

        Diagram unlayoutedDiagram = this.diagramService.create(diagramCreationParameters, previousDiagram);
        Diagram newDiagram = this.layoutService.layout(unlayoutedDiagram);

        UUID projectId = diagramCreationParameters.getEditingContext().getProjectId();
        RepresentationDescriptor representationDescriptor = this.getRepresentationDescriptor(projectId, newDiagram);
        this.representationService.save(representationDescriptor);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newDiagram;
    }

    private RepresentationDescriptor getRepresentationDescriptor(UUID projectId, Diagram diagram) {
        // @formatter:off
        return RepresentationDescriptor.newRepresentationDescriptor(diagram.getId())
                .projectId(projectId)
                .targetObjectId(diagram.getTargetObjectId())
                .label(diagram.getLabel())
                .representation(diagram)
                .build();
        // @formatter:on
    }

}
