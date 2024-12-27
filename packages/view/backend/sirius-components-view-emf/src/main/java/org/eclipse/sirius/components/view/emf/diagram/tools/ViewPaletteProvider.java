/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IDiagramElementPaletteProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide the tools of the palette for diagram created from a view description.
 * <p>
 * Drop tools and edge reconnection tools are handled in a separate way, as they do not ever appear in the palette of
 * any element since they can only be triggered by direct gestures/interactions.
 *
 * @author sbegaudeau
 */
@Service
public class ViewPaletteProvider implements IPaletteProvider {

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IObjectSearchService objectSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final IDiagramElementPaletteProvider diagramElementPaletteProvider;

    public ViewPaletteProvider(IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramDescriptionService diagramDescriptionService, IObjectSearchService objectSearchService, IViewAQLInterpreterFactory aqlInterpreterFactory, IDiagramElementPaletteProvider diagramElementPaletteProvider) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.diagramElementPaletteProvider = Objects.requireNonNull(diagramElementPaletteProvider);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, List<String> diagramElementIds) {
        return diagramElementIds.size() == 1 && this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public Palette handle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, List<Object> diagramElements) {
        Palette palette = null;

        var optionalDiagramElement = diagramElements.stream().findFirst();
        var optionalTargetElement = optionalDiagramElement.flatMap(diagramElement -> this.findTargetElement(editingContext, diagramElement));
        var optionalDiagramElementDescription = optionalDiagramElement.flatMap(diagramElement -> this.findDiagramElementDescription(diagramDescription, diagramElement));

        if (optionalDiagramElement.isPresent() && optionalTargetElement.isPresent() && optionalDiagramElementDescription.isPresent()) {
            var diagramElement = optionalDiagramElement.get();
            var targetElement = optionalTargetElement.get();
            var diagramElementDescription = optionalDiagramElementDescription.get();

            VariableManager variableManager = new VariableManager();
            variableManager.put(VariableManager.SELF, targetElement);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);

            var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
            if (optionalDiagramDescription.isPresent()) {
                org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
                var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
                palette = this.diagramElementPaletteProvider.getPalette(editingContext, interpreter, diagramDescription, diagramContext, diagramElementDescription, diagramElement, variableManager);
            }
        }
        return palette;
    }

    private Optional<Object> findTargetElement(IEditingContext editingContext, Object diagramElement) {
        String targetObjectId = null;
        if (diagramElement instanceof Diagram diagram) {
            targetObjectId = diagram.getTargetObjectId();
        } else if (diagramElement instanceof Node node) {
            targetObjectId = node.getTargetObjectId();
        } else if (diagramElement instanceof Edge edge) {
            targetObjectId = edge.getTargetObjectId();
        }
        if (targetObjectId != null) {
            return this.objectSearchService.getObject(editingContext, targetObjectId);
        }
        return Optional.empty();
    }

    private Optional<Object> findDiagramElementDescription(DiagramDescription diagramDescription, Object diagramElement) {
        Object diagramElementDescription = null;

        if (diagramElement instanceof Diagram diagram && diagram.getDescriptionId().equals(diagramDescription.getId())) {
            diagramElementDescription = diagramDescription;
        } else if (diagramElement instanceof Node node) {
            String descriptionId = node.getDescriptionId();
            var optionalNodeDescription = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, descriptionId);
            if (optionalNodeDescription.isPresent()) {
                diagramElementDescription = optionalNodeDescription.get();
            }
        } else if (diagramElement instanceof Edge edge) {
            String descriptionId = edge.getDescriptionId();
            var optionalEdgeDescription = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, descriptionId);
            if (optionalEdgeDescription.isPresent()) {
                diagramElementDescription = optionalEdgeDescription.get();
            }
        }
        return Optional.ofNullable(diagramElementDescription);
    }
}
