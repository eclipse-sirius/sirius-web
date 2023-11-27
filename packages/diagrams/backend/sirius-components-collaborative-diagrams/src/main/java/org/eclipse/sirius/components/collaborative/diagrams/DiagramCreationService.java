/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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

import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.components.DiagramComponent;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps.Builder;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.ArrangeAllEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.IDiagramLayoutConfigurationProvider;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.IDiagramLayoutEngine;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to create diagrams.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramCreationService implements IDiagramCreationService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectService objectService;

    private final ILayoutService layoutService;

    private final IDiagramLayoutEngine diagramLayoutEngine;

    private final IDiagramLayoutConfigurationProvider diagramLayoutConfigurationProvider;

    private final IOperationValidator operationValidator;

    private final Timer timer;

    private final Logger logger = LoggerFactory.getLogger(DiagramCreationService.class);

    public DiagramCreationService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IObjectService objectService,
                                  ILayoutService layoutService, IDiagramLayoutEngine diagramLayoutEngine, IDiagramLayoutConfigurationProvider diagramLayoutConfigurationProvider,
                                  IOperationValidator operationValidator, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.layoutService = Objects.requireNonNull(layoutService);
        this.diagramLayoutEngine = Objects.requireNonNull(diagramLayoutEngine);
        this.diagramLayoutConfigurationProvider = Objects.requireNonNull(diagramLayoutConfigurationProvider);
        this.operationValidator = Objects.requireNonNull(operationValidator);
        // @formatter:off
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "diagram")
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public Diagram create(String label, Object targetObject, DiagramDescription diagramDescription, IEditingContext editingContext) {
        // @formatter:off
        var allDiagramDescriptions = this.representationDescriptionSearchService.findAll(editingContext)
                .values()
                .stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .toList();
        // @formatter:on

        return this.doRender(label, targetObject, editingContext, diagramDescription, allDiagramDescriptions, Optional.empty());
    }

    @Override
    public Optional<Diagram> refresh(IEditingContext editingContext, IDiagramContext diagramContext) {
        Diagram previousDiagram = diagramContext.getDiagram();

        var optionalObject = this.objectService.getObject(editingContext, previousDiagram.getTargetObjectId());
        // @formatter:off
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, previousDiagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);

        var allDiagramDescriptions = this.representationDescriptionSearchService.findAll(editingContext)
                .values()
                .stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .toList();
        // @formatter:on

        if (optionalObject.isPresent() && optionalDiagramDescription.isPresent()) {
            Object object = optionalObject.get();
            DiagramDescription diagramDescription = optionalDiagramDescription.get();
            Diagram diagram = this.doRender(previousDiagram.getLabel(), object, editingContext, diagramDescription, allDiagramDescriptions, Optional.of(diagramContext));
            return Optional.of(diagram);
        }
        return Optional.empty();
    }

    private Diagram doRender(String label, Object targetObject, IEditingContext editingContext, DiagramDescription diagramDescription, List<DiagramDescription> allDiagramDescriptions, Optional<IDiagramContext> optionalDiagramContext) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(DiagramDescription.LABEL, label);
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));

        Optional<IDiagramEvent> optionalDiagramElementEvent = optionalDiagramContext.map(IDiagramContext::getDiagramEvent);
        Optional<Diagram> optionalPreviousDiagram = optionalDiagramContext.map(IDiagramContext::getDiagram);
        List<ViewCreationRequest> viewCreationRequests = optionalDiagramContext.map(IDiagramContext::getViewCreationRequests).orElse(List.of());
        List<ViewDeletionRequest> viewDeletionRequests = optionalDiagramContext.map(IDiagramContext::getViewDeletionRequests).orElse(List.of());

        //@formatter:off
        Builder builder = DiagramComponentProps.newDiagramComponentProps()
                .variableManager(variableManager)
                .diagramDescription(diagramDescription)
                .allDiagramDescriptions(allDiagramDescriptions)
                .operationValidator(this.operationValidator)
                .viewCreationRequests(viewCreationRequests)
                .viewDeletionRequests(viewDeletionRequests)
                .previousDiagram(optionalPreviousDiagram)
                .diagramEvent(optionalDiagramElementEvent);
        //@formatter:on

        DiagramComponentProps props = builder.build();
        Element element = new Element(DiagramComponent.class, props);

        Diagram newDiagram = new DiagramRenderer().render(element);

        // The auto layout is used for the first rendering and after that if it is activated
        if (this.shouldPerformFullLayout(optionalDiagramContext, diagramDescription)) {
            newDiagram = this.layoutService.layout(editingContext, newDiagram);
        } else if (optionalDiagramContext.isPresent()) {
            newDiagram = this.layoutService.incrementalLayout(editingContext, newDiagram, optionalDiagramElementEvent);
        }

        var newLayoutData = optionalPreviousDiagram.map(Diagram::getLayoutData).orElse(new DiagramLayoutData(Map.of(), Map.of(), Map.of()));
        newDiagram = Diagram.newDiagram(newDiagram)
                .layoutData(newLayoutData)
                .build();

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        this.logger.trace("diagram refreshed in {}ms", end - start);

        return newDiagram;
    }

    /**
     * Indicates when the full layout should be performed.
     *
     * This method will return true in the following situations:
     *
     * <ul>
     * <li>The first rendering of the diagram</li>
     * <li>The description of the diagram indicates that layout should be automatic</li>
     * <li>The arrange all event is currently being processed</li>
     * </ul>
     *
     * @param optionalDiagramContext
     *            The diagram context if one is available
     * @param diagramDescription
     *            The description of the diagram
     * @return <code>true</code> if the full layout of the diagram should be performed, <code>false</code> otherwise
     */
    private boolean shouldPerformFullLayout(Optional<IDiagramContext> optionalDiagramContext, DiagramDescription diagramDescription) {
        // @formatter:off
        return optionalDiagramContext.isEmpty()
                || diagramDescription.isAutoLayout()
                || optionalDiagramContext.map(IDiagramContext::getDiagramEvent)
                .filter(ArrangeAllEvent.class::isInstance)
                .isPresent();
        // @formatter:on
    }

}
