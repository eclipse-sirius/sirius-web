/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.components.DiagramComponent;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps.Builder;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderer;
import org.eclipse.sirius.components.diagrams.renderer.IEdgeAppearanceHandler;
import org.eclipse.sirius.components.diagrams.renderer.INodeAppearanceHandler;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service used to create diagrams.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramCreationService implements IDiagramCreationService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectSearchService objectSearchService;

    private final IOperationValidator operationValidator;

    private final List<INodeAppearanceHandler> nodeAppearanceHandlers;

    private final List<IEdgeAppearanceHandler> edgeAppearanceHandlers;

    private final Timer timer;

    private final Logger logger = LoggerFactory.getLogger(DiagramCreationService.class);

    public DiagramCreationService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IObjectSearchService objectSearchService,
                                  IOperationValidator operationValidator, List<INodeAppearanceHandler> nodeAppearanceHandlers, List<IEdgeAppearanceHandler> edgeAppearanceHandlers, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.operationValidator = Objects.requireNonNull(operationValidator);
        this.nodeAppearanceHandlers = Objects.requireNonNull(nodeAppearanceHandlers);
        this.edgeAppearanceHandlers = Objects.requireNonNull(edgeAppearanceHandlers);
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "diagram")
                .register(meterRegistry);
    }

    @Override
    public Diagram create(Object targetObject, DiagramDescription diagramDescription, IEditingContext editingContext) {
        var allDiagramDescriptions = this.representationDescriptionSearchService.findAll(editingContext)
                .values()
                .stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .toList();

        return this.doRender(targetObject, editingContext, diagramDescription, allDiagramDescriptions, Optional.empty());
    }

    @Override
    public Optional<Diagram> refresh(IEditingContext editingContext, DiagramContext diagramContext) {
        Diagram previousDiagram = diagramContext.diagram();

        var optionalObject = this.objectSearchService.getObject(editingContext, previousDiagram.getTargetObjectId());
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, previousDiagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);

        var allDiagramDescriptions = this.representationDescriptionSearchService.findAll(editingContext)
                .values()
                .stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .toList();

        if (optionalObject.isPresent() && optionalDiagramDescription.isPresent()) {
            Object object = optionalObject.get();
            DiagramDescription diagramDescription = optionalDiagramDescription.get();
            Diagram diagram = this.doRender(object, editingContext, diagramDescription, allDiagramDescriptions, Optional.of(diagramContext));
            return Optional.of(diagram);
        }
        return Optional.empty();
    }

    private Diagram doRender(Object targetObject, IEditingContext editingContext, DiagramDescription diagramDescription, List<DiagramDescription> allDiagramDescriptions, Optional<DiagramContext> optionalDiagramContext) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(DiagramContext.DIAGRAM_CONTEXT, optionalDiagramContext.orElse(null));
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(optionalDiagramContext.orElse(null)));

        List<IDiagramEvent> diagramEvents = optionalDiagramContext.map(DiagramContext::diagramEvents).orElse(List.of());
        Optional<Diagram> optionalPreviousDiagram = optionalDiagramContext.map(DiagramContext::diagram);
        List<ViewCreationRequest> viewCreationRequests = optionalDiagramContext.map(DiagramContext::viewCreationRequests).orElse(List.of());
        List<ViewDeletionRequest> viewDeletionRequests = optionalDiagramContext.map(DiagramContext::viewDeletionRequests).orElse(List.of());

        Builder builder = DiagramComponentProps.newDiagramComponentProps()
                .variableManager(variableManager)
                .diagramDescription(diagramDescription)
                .allDiagramDescriptions(allDiagramDescriptions)
                .operationValidator(this.operationValidator)
                .viewCreationRequests(viewCreationRequests)
                .viewDeletionRequests(viewDeletionRequests)
                .previousDiagram(optionalPreviousDiagram)
                .diagramEvents(diagramEvents)
                .nodeAppearanceHandlers(this.nodeAppearanceHandlers)
                .edgeAppearanceHandlers(edgeAppearanceHandlers);

        DiagramComponentProps props = builder.build();
        Element element = new Element(DiagramComponent.class, props);

        Diagram newDiagram = new DiagramRenderer().render(element);

        var newLayoutData = optionalPreviousDiagram.map(Diagram::getLayoutData).orElse(new DiagramLayoutData(Map.of(), Map.of(), Map.of()));
        newDiagram = Diagram.newDiagram(newDiagram)
                .layoutData(newLayoutData)
                .build();

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        this.logger.trace("diagram refreshed in {}ms", end - start);

        return newDiagram;
    }
}
