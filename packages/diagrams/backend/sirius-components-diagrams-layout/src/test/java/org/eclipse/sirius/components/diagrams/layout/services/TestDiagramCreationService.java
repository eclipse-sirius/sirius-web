/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.components.DiagramComponent;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps.Builder;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.api.ILayoutService;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderer;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Reproduces the behavior of the diagram creation service with the difference that {@link TestDiagramCreationService}
 * does the refresh and the layout from two distinct method call.
 *
 * @author gcoutable
 */
public class TestDiagramCreationService {

    private final IObjectService objectService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ILayoutService layoutService;

    public TestDiagramCreationService(IObjectService objectService, IRepresentationDescriptionSearchService representationDescriptionSearchService, ILayoutService layoutService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.layoutService = Objects.requireNonNull(layoutService);
    }

    public Optional<Diagram> performRefresh(IEditingContext editingContext, Diagram previousDiagram, IDiagramEvent diagramEvent) {
        var optionalObject = this.objectService.getObject(editingContext, previousDiagram.getTargetObjectId());

        // @formatter:off
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, previousDiagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
        // @formatter:on

        if (optionalObject.isPresent() && optionalDiagramDescription.isPresent()) {
            Object object = optionalObject.get();
            DiagramDescription diagramDescription = optionalDiagramDescription.get();
            Diagram refreshedDiagram = this.doRefresh(previousDiagram.getLabel(), object, editingContext, diagramDescription, Optional.of(previousDiagram), Optional.ofNullable(diagramEvent));
            return Optional.of(refreshedDiagram);

        }
        return Optional.empty();
    }

    private Diagram doRefresh(String label, Object targetObject, IEditingContext editingContext, DiagramDescription diagramDescription, Optional<Diagram> optionalPreviousDiagram,
            Optional<IDiagramEvent> optionalDiagramEvent) {
        VariableManager variableManager = new VariableManager();
        variableManager.put(DiagramDescription.LABEL, label);
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

      //@formatter:off
        Builder builder = DiagramComponentProps.newDiagramComponentProps()
                .variableManager(variableManager)
                .diagramDescription(diagramDescription)
                .viewCreationRequests(List.of())
                .viewDeletionRequests(List.of())
                .previousDiagram(optionalPreviousDiagram)
                .diagramEvent(optionalDiagramEvent);
        //@formatter:on

        DiagramComponentProps props = builder.build();
        Element element = new Element(DiagramComponent.class, props);

        return new DiagramRenderer().render(element);
    }

    public Diagram performLayout(IEditingContext editingContext, Diagram diagram, IDiagramEvent diagramEvent) {
        return this.layoutService.incrementalLayout(editingContext, diagram, Optional.ofNullable(diagramEvent));
    }

    public Diagram performElKLayout(IEditingContext editingContext, Diagram diagram) {
        return this.layoutService.layout(editingContext, diagram);
    }

}
