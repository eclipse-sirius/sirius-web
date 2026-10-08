/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorPaletteProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.palette.dto.IPaletteEntry;
import org.eclipse.sirius.components.palette.dto.ITool;
import org.eclipse.sirius.components.palette.dto.Palette;
import org.eclipse.sirius.components.palette.dto.PaletteDivider;
import org.eclipse.sirius.components.palette.dto.ToolSection;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.NodeToolSection;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IConnectorPaletteVariableManagerProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide the connector tools of the palette for diagram created from a view description.
 *
 * @author mcharfadi
 */
@Service
public class ViewConnectorPaletteProvider implements IConnectorPaletteProvider {

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IDiagramIdProvider diagramIdProvider;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    private final IConnectorPaletteVariableManagerProvider connectorPaletteVariableManagerProvider;

    public ViewConnectorPaletteProvider(
            IURLParser urlParser,
            IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
            IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService,
            IDiagramDescriptionService diagramDescriptionService,
            IDiagramIdProvider diagramIdProvider,
            IViewAQLInterpreterFactory aqlInterpreterFactory,
            IConnectorPaletteVariableManagerProvider connectorPaletteVariableManagerProvider) {
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
        this.connectorPaletteVariableManagerProvider = Objects.requireNonNull(connectorPaletteVariableManagerProvider);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public Palette handle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, Object sourceDiagramElement, Object targetDiagramElement, Object sourceElementDescription, Object targetElementDescription) {
        Palette palette = null;
        var optionalVariableManager = this.connectorPaletteVariableManagerProvider.getVariableManager(editingContext, diagramContext, sourceDiagramElement, targetDiagramElement);
        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());

        if (optionalDiagramDescription.isPresent() && optionalVariableManager.isPresent()) {
            var variableManager = optionalVariableManager.get();
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
            if (sourceElementDescription instanceof IDiagramElementDescription diagramElementDescription) {
                palette = this.getPalette(editingContext, diagramDescription, diagramElementDescription, variableManager, interpreter);

            }
        }

        return palette;
    }

    private Palette getPalette(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Optional<String> sourceElementId = this.getSourceElementId(diagramElementDescription.getId());
        Palette nodePalette = null;

        if (sourceElementId.isPresent()) {
            List<EdgeTool> edgeTools = new ArrayList<>();
            this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, diagramElementDescription.getId())
                    .ifPresent(nodeDescription -> nodeDescription.getEdgeTools().stream()
                            .filter(edgeTool -> this.checkPrecondition(edgeTool, variableManager, interpreter))
                            .forEach(edgeTools::add));
            this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, diagramElementDescription.getId())
                    .ifPresent(edgeDescription -> edgeDescription.getEdgeTools().stream()
                            .filter(edgeTool -> this.checkPrecondition(edgeTool, variableManager, interpreter))
                            .forEach(edgeTools::add));

            List<IPaletteEntry> paletteEntries = new ArrayList<>();
            List<ITool> quickAccessTools = new ArrayList<>();
            if (!edgeTools.isEmpty() && edgeTools.getFirst().getPalette() != null) {
                EdgeTool firstEdgeTool = edgeTools.getFirst();
                NodePalette palette = firstEdgeTool.getPalette();

                palette.getNodeTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.createTool(tool, firstEdgeTool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                palette.getToolSections().stream()
                        .map(nodeToolSection -> this.createToolSection(nodeToolSection, firstEdgeTool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);

                palette.getQuickAccessTools().stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.createTool(tool, firstEdgeTool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                        .forEach(quickAccessTools::add);
            }

            if (this.isPaletteEmpty(paletteEntries, quickAccessTools)) {
                edgeTools.stream()
                        .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                        .map(tool -> this.createEdgeTool(tool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                        .forEach(paletteEntries::add);
            }

            paletteEntries.add(new PaletteDivider(UUID.randomUUID().toString()));

            String paletteId = "siriusComponents://connectorPalette?diagramElementId=" + sourceElementId.get();
            nodePalette = Palette.newPalette(paletteId)
                    .quickAccessTools(quickAccessTools)
                    .paletteEntries(paletteEntries)
                    .build();
        }
        return nodePalette;
    }

    private boolean isPaletteEmpty(List<IPaletteEntry> paletteEntries, List<ITool> quickAccessTools) {
        return Stream.of(paletteEntries.stream().filter(ITool.class::isInstance),
                paletteEntries.stream()
                        .filter(ToolSection.class::isInstance)
                        .map(ToolSection.class::cast)
                        .map(ToolSection::tools)
                        .filter(ITool.class::isInstance))
                .toList().isEmpty() && quickAccessTools.isEmpty();
    }

    private ToolSection createToolSection(NodeToolSection toolSection, EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolSelectionId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(toolSection).toString().getBytes()).toString();
        var tools = new ArrayList<ITool>(toolSection.getNodeTools().stream()
                .filter(tool -> this.checkPrecondition(tool, variableManager, interpreter))
                .map(tool -> this.createTool(tool, viewEdgeTool, diagramDescription, diagramElementDescription, variableManager, interpreter))
                .toList());
        
        return ToolSection.newToolSection(toolSelectionId)
                .label(toolSection.getName())
                .iconURL(List.of())
                .tools(tools)
                .build();
    }

    private ITool createTool(NodeTool viewNodeTool, EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(viewNodeTool).toString().getBytes()).toString();

        List<String> iconURLProvider = this.edgeToolIconURLProvider(viewNodeTool.getIconURLsExpression(), interpreter, variableManager);
        String dialogDescriptionId = "";
        if (viewNodeTool.getDialogDescription() != null) {
            dialogDescriptionId = this.diagramIdProvider.getId(viewNodeTool.getDialogDescription());
        }

        List<SingleClickOnTwoDiagramElementsCandidate> candidates = List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                .sources(List.of(diagramElementDescription))
                .targets(viewEdgeTool.getTargetElementDescriptions().stream()
                        .map(viewDiagramElementDescription -> this.diagramDescriptionService.findDiagramElementDescriptionById(diagramDescription, this.diagramIdProvider.getId(viewDiagramElementDescription)))
                        .flatMap(Optional::stream)
                        .toList())
                .build());

        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(viewNodeTool.getName())
                .iconURL(iconURLProvider)
                .candidates(candidates)
                .dialogDescriptionId(dialogDescriptionId)
                .build();
    }

    private ITool createEdgeTool(EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String toolId = UUID.nameUUIDFromBytes(EcoreUtil.getURI(viewEdgeTool).toString().getBytes()).toString();
        String dialogDescriptionId = "";
        if (viewEdgeTool.getDialogDescription() != null) {
            dialogDescriptionId = this.diagramIdProvider.getId(viewEdgeTool.getDialogDescription());
        }

        List<SingleClickOnTwoDiagramElementsCandidate> candidates = List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                .sources(List.of(diagramElementDescription))
                .targets(viewEdgeTool.getTargetElementDescriptions().stream()
                        .map(viewDiagramElementDescription -> this.diagramDescriptionService.findDiagramElementDescriptionById(diagramDescription, this.diagramIdProvider.getId(viewDiagramElementDescription)))
                        .flatMap(Optional::stream)
                        .toList())
                .build());

        return SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                .label(viewEdgeTool.getName())
                .iconURL(this.edgeToolIconURLProvider(viewEdgeTool.getIconURLsExpression(), interpreter, variableManager))
                .candidates(candidates)
                .dialogDescriptionId(dialogDescriptionId)
                .build();
    }



    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private boolean checkPrecondition(Tool tool, VariableManager variableManager, AQLInterpreter interpreter) {
        String precondition = tool.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            return result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }
        return true;
    }

    private List<String> edgeToolIconURLProvider(String iconURLsExpression, AQLInterpreter interpreter, VariableManager variableManager) {
        List<String> iconURL = new ArrayList<>();
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON);
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
