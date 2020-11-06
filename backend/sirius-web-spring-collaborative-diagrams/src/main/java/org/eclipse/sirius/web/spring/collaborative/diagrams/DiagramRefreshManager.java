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
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.web.collaborative.api.dto.PreDestroyPayload;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.diagrams.api.DiagramCreationParameters;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramRefreshManager;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.web.collaborative.diagrams.api.dto.DiagramRefreshedEventPayload;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

/**
 * Service used to manage the lifecycle of the diagram and its palette.
 *
 * @author sbegaudeau
 */
public class DiagramRefreshManager implements IDiagramRefreshManager {

    private final IRepresentationService representationService;

    private final IDiagramService diagramService;

    private final ILayoutService layoutService;

    private final DirectProcessor<IPayload> flux;

    private final FluxSink<IPayload> sink;

    private final Timer timer;

    private Diagram diagram;

    public DiagramRefreshManager(IRepresentationService representationService, IDiagramService diagramService, ILayoutService layoutService, MeterRegistry meterRegistry) {
        this.representationService = Objects.requireNonNull(representationService);
        this.diagramService = Objects.requireNonNull(diagramService);
        this.layoutService = Objects.requireNonNull(layoutService);
        this.flux = DirectProcessor.create();
        this.sink = this.flux.sink();

        // @formatter:off
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "diagram") //$NON-NLS-1$
                .register(meterRegistry);
        // @formatter:on
    }

    /**
     * This method is used to initialize the diagram refresh manager. It should be called when before subscribing to the
     * flux of events of the diagram refresh manager.
     *
     * @param projectId
     *            The identifier of the project in which the diagram will be saved
     * @param diagramCreationParameters
     *            The parameters of the diagram to create
     */
    @Override
    public void initialize(UUID projectId, DiagramCreationParameters diagramCreationParameters) {
        this.computeDiagram(projectId, diagramCreationParameters, Optional.empty());
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
    public void refresh(UUID projectId, DiagramCreationParameters diagramCreationParameters) {
        long start = System.currentTimeMillis();

        this.computeDiagram(projectId, diagramCreationParameters, Optional.of(this.diagram));

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        this.sink.next(new DiagramRefreshedEventPayload(this.diagram));
    }

    private void computeDiagram(UUID projectId, DiagramCreationParameters diagramCreationParameters, Optional<Diagram> optionalPrevDiagram) {
        // @formatter:off
        Diagram unlayoutedDiagram = optionalPrevDiagram
                .map(prevDiagram -> this.diagramService.refresh(diagramCreationParameters, this))
                .orElse(this.diagramService.create(diagramCreationParameters));
        // @formatter:on
        this.diagram = this.layoutService.layout(unlayoutedDiagram);

        RepresentationDescriptor representationDescriptor = this.getRepresentationDescriptor(projectId);
        this.representationService.save(representationDescriptor);
    }

    private RepresentationDescriptor getRepresentationDescriptor(UUID projectId) {
        // @formatter:off
        return RepresentationDescriptor.newRepresentationDescriptor(this.diagram.getId())
                .projectId(projectId)
                .targetObjectId(this.diagram.getTargetObjectId())
                .label(this.diagram.getLabel())
                .representation(this.diagram)
                .build();
        // @formatter:on
    }

    @Override
    public Diagram getDiagram() {
        return this.diagram;
    }

    @Override
    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public Flux<IPayload> getFlux() {
        var initialRefresh = Mono.fromCallable(() -> new DiagramRefreshedEventPayload(this.diagram));
        return Flux.concat(initialRefresh, this.flux);
    }

    @Override
    public void dispose() {
        this.flux.onComplete();
    }

    @Override
    public void preDestroy() {
        this.sink.next(new PreDestroyPayload(this.diagram.getId()));
    }
}
