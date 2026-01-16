/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.IPaletteEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramVariables;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IGroupPaletteToolsProvider;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.INodeToolFactory;
import org.springframework.stereotype.Service;

/**
 * Used to provide the group palette for diagram created from a view description.
 *
 * @author mcharfadi
 */
@Service
public class ViewGroupPaletteProvider implements IPaletteProvider {

    private final IObjectSearchService objectSearchService;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final List<IGroupPaletteToolsProvider> paletteToolsProviders;

    private final INodeToolFactory nodeToolFactory;

    public ViewGroupPaletteProvider(IObjectSearchService objectSearchService, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
            IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramDescriptionService diagramDescriptionService, IViewAQLInterpreterFactory aqlInterpreterFactory,
            List<IGroupPaletteToolsProvider> paletteToolsProviders, INodeToolFactory nodeToolFactory) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.paletteToolsProviders = Objects.requireNonNull(paletteToolsProviders);
        this.nodeToolFactory = Objects.requireNonNull(nodeToolFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, List<String> diagramElementIds) {
        return diagramElementIds.size() > 1 && this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public Palette handle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, List<Object> diagramElements) {
        Palette palette = null;

        var targetElements = diagramElements.stream()
                .map(diagramElement -> this.findTargetElement(editingContext, diagramElement))
                .flatMap(Optional::stream)
                .toList();

        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetElements);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(DiagramVariables.SELECTED_EDGES.name(), diagramElements.stream().filter(Edge.class::isInstance).toList());
        variableManager.put(DiagramVariables.SELECTED_NODES.name(), diagramElements.stream().filter(Node.class::isInstance).toList());

        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            if (viewDiagramDescription.eContainer() instanceof  View view) {
                var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, view);
                palette = this.getGroupDiagramPalette(viewDiagramDescription, variableManager, interpreter);
            }
        }

        var diagramElementDescriptions = diagramElements.stream()
                .map(diagramElement -> this.findDiagramElementDescription(diagramDescription, diagramElement))
                .flatMap(Optional::stream).toList();

        List<ToolSection> extraToolSections = this.paletteToolsProviders.stream()
                .map(paletteToolsProvider -> paletteToolsProvider.createExtraToolSections(diagramContext, diagramElementDescriptions, diagramElements))
                .flatMap(List::stream)
                .toList();

        List<ITool> quickAccessTools = this.paletteToolsProviders.stream()
                .map(paletteToolsProvider -> paletteToolsProvider.createQuickAccessTools(diagramContext, diagramElementDescriptions, diagramElements))
                .flatMap(List::stream)
                .toList();

        if (palette != null) {
            palette.paletteEntries().addAll(extraToolSections);
            palette.quickAccessTools().addAll(quickAccessTools);
        }

        return palette;
    }

    private Optional<IDiagramElementDescription> findDiagramElementDescription(DiagramDescription diagramDescription, Object diagramElement) {
        Optional<IDiagramElementDescription> optionalElementDescription = Optional.empty();
        if (diagramElement instanceof Node node) {
            optionalElementDescription = this.diagramDescriptionService.findDiagramElementDescriptionById(diagramDescription, node.getDescriptionId());
        } else if (diagramElement instanceof Edge edge) {
            optionalElementDescription =  this.diagramDescriptionService.findDiagramElementDescriptionById(diagramDescription, edge.getDescriptionId());
        }
        return optionalElementDescription;
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

    private Palette getGroupDiagramPalette(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String paletteId = "";
        var paletteEntries = new ArrayList<IPaletteEntry>();
        var quickAccessTools = new ArrayList<ITool>();
        if (viewDiagramDescription.getGroupPalette() != null) {
            viewDiagramDescription.getGroupPalette().getNodeTools().stream()
                    .filter(nodeTool -> this.checkPrecondition(nodeTool, variableManager, interpreter))
                    .map(nodeTool -> this.nodeToolFactory.createNodeTool(interpreter, nodeTool, false, variableManager))
                    .forEach(paletteEntries::add);

            viewDiagramDescription.getGroupPalette().getQuickAccessTools().stream()
                    .filter(nodeTool -> this.checkPrecondition(nodeTool, variableManager, interpreter))
                    .map(nodeTool -> this.nodeToolFactory.createNodeTool(interpreter, nodeTool, false, variableManager))
                    .forEach(quickAccessTools::add);

            viewDiagramDescription.getGroupPalette().getToolSections().stream()
                    .filter(NodeToolSection.class::isInstance)
                    .map(NodeToolSection.class::cast)
                    .map(nodeToolSection -> this.createToolSection(nodeToolSection, variableManager, interpreter))
                    .forEach(paletteEntries::add);
        }
        return new Palette(paletteId, quickAccessTools, paletteEntries);
    }

    private boolean checkPrecondition(Tool tool, VariableManager variableManager, AQLInterpreter interpreter) {
        String precondition = tool.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            return result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }
        return true;
    }

    private org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection createToolSection(NodeToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(toolSection).toString().getBytes()).toString();

        var tools = new ArrayList<ITool>(toolSection.getNodeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(tool -> this.nodeToolFactory.createNodeTool(interpreter, tool, false, variableManager))
                .toList());

        return org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(tools)
                .build();
    }
}
