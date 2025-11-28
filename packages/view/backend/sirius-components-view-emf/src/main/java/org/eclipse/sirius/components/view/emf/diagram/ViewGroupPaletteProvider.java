/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.IPaletteEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

/**
 * Used to provide the group palette for diagram created from a view description.
 *
 * @author mcharfadi
 */
@Service
public class ViewGroupPaletteProvider implements IPaletteProvider {

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramIdProvider diagramIdProvider;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final Function<EObject, UUID> idProvider = (eObject) -> UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());

    public ViewGroupPaletteProvider(IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
                                    IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IDiagramIdProvider diagramIdProvider,
                                    IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription, List<String> diagramElementIds) {
        return diagramElementIds.size() > 1 && this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public Palette handle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, List<Object> diagramElementDescriptions, List<Object> diagramElements, List<Object> targetElements) {
        Palette palette = null;
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetElements);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(Edge.SELECTED_EDGES, diagramElements.stream().filter(Edge.class::isInstance).toList());
        variableManager.put(Node.SELECTED_NODES, diagramElements.stream().filter(Node.class::isInstance).toList());
        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
            palette = this.getGroupDiagramPalette(diagramDescription, viewDiagramDescription, variableManager, interpreter, targetElements);
        }
        return palette;
    }

    private Palette getGroupDiagramPalette(DiagramDescription diagramDescription, org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, VariableManager variableManager, AQLInterpreter interpreter, List<Object> targetElements) {
        String paletteId = "siriusComponents://diagramGroupPalette";
        var paletteEntries = new ArrayList<IPaletteEntry>();
        var quickAccessTools = new ArrayList<ITool>();
        if (viewDiagramDescription.getGroupPalette() != null) {
            viewDiagramDescription.getGroupPalette().getNodeTools().stream()
                    .filter(nodeTool -> this.checkPrecondition(nodeTool, variableManager, interpreter))
                    .map(nodeTool -> this.createNodeTool(nodeTool, variableManager, interpreter))
                    .forEach(paletteEntries::add);
            viewDiagramDescription.getGroupPalette().getToolSections().stream()
                    .filter(toolSection -> this.checkPrecondition(toolSection, variableManager, interpreter))
                    .filter(org.eclipse.sirius.components.view.diagram.NodeToolSection.class::isInstance)
                    .map(org.eclipse.sirius.components.view.diagram.NodeToolSection.class::cast)
                    .map(toolSection -> this.createToolSection(toolSection, variableManager, interpreter))
                    .forEach(paletteEntries::add);

            viewDiagramDescription.getGroupPalette().getReusedToolSection().stream()
                    .filter(toolSection -> {
                        for (Object targetElement : targetElements) {
                            var childVariableManager = variableManager.createChild();
                            childVariableManager.put(VariableManager.SELF, targetElement);
                            var isCandidate = this.checkPrecondition(toolSection, childVariableManager, interpreter);
                            if (!isCandidate) {
                                return false;
                            }
                        }

                        return true;
                    })
                    .filter(org.eclipse.sirius.components.view.diagram.NodeToolSection.class::isInstance)
                    .map(org.eclipse.sirius.components.view.diagram.NodeToolSection.class::cast)
                    .map(toolSection -> this.createToolSection(toolSection, variableManager, interpreter))
                    .forEach(paletteEntries::add);

            viewDiagramDescription.getGroupPalette().getQuickAccessTools().stream()
                    .filter(nodeTool -> this.checkPrecondition(nodeTool, variableManager, interpreter))
                    .map(nodeTool -> this.createNodeTool(nodeTool, variableManager, interpreter))
                    .forEach(quickAccessTools::add);
        }
        return new Palette(paletteId, quickAccessTools, paletteEntries);
    }

    private ITool createNodeTool(NodeTool viewNodeTool, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolId = this.idProvider.apply(viewNodeTool).toString();
        List<String> iconURLProvider = this.nodeToolIconURLProvider(viewNodeTool, interpreter, variableManager);
        String dialogDescriptionId = "";
        if (viewNodeTool.getDialogDescription() != null) {
            dialogDescriptionId = this.diagramIdProvider.getId(viewNodeTool.getDialogDescription());
        }

        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                .label(viewNodeTool.getName())
                .iconURL(iconURLProvider)
                .dialogDescriptionId(dialogDescriptionId)
                .targetDescriptions(List.of())
                .appliesToDiagramRoot(false)
                .withImpactAnalysis(viewNodeTool.isWithImpactAnalysis())
                .build();
    }

    private ToolSection createToolSection(DiagramToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(toolSection.getNodeTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.createNodeTool(tool, variableManager, interpreter))
                        .toList())
                .build();
    }

    private ToolSection createToolSection(NodeToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = this.idProvider.apply(toolSection).toString();

        var tools = new ArrayList<>(toolSection.getNodeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(tool -> this.createNodeTool(tool, variableManager, interpreter))
                .toList());

        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(tools)
                .build();
    }

    private boolean checkPrecondition(Tool tool, VariableManager variableManager, AQLInterpreter interpreter) {
        String precondition = tool.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            return result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }
        return true;
    }

    private boolean checkPrecondition(org.eclipse.sirius.components.view.diagram.ToolSection toolSection, VariableManager variableManager, AQLInterpreter interpreter) {
        String precondition = toolSection.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            return result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }
        return true;
    }

    private List<String> nodeToolIconURLProvider(NodeTool nodeTool, AQLInterpreter interpreter, VariableManager variableManager) {
        List<String> iconURL = null;
        String iconURLsExpression = nodeTool.getIconURLsExpression();
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ViewToolImageProvider.NODE_CREATION_TOOL_ICON);
        } else {
            iconURL = this.evaluateListString(interpreter, variableManager, iconURLsExpression);
        }
        return iconURL;
    }

    private List<String> evaluateListString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        List<Object> objects = interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects().orElse(List.of());
        return objects.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }
}
