/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolSectionsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.configuration.ViewToolConfiguration;
import org.springframework.stereotype.Service;

/**
 * Used to provide the tools of the palette for diagram created from a view description.
 * <p>
 * "Node tools" (SingleClickOnDiagramElementTool) and "Edge tools" (SingleClickOnTwoDiagramElementsTool) are obtained
 * from the ToolSection produced by ToolConverter for the target element (diagram, node or edge).
 * <p>
 * For nodes and edges (but not diagrams), the "extra tools" which can be exposed in the palette but are not implemented
 * as ITools but as plain Java handlers (label edit and delete) are added as pseudo-entries if the target element has
 * specified an explicit behavior for them. At runtime their invocation goes through distinct GraphQL mutation
 * operations than plain ITool invocation, so the body we provide here for them is never actually invoked.
 * <p>
 * Drop tools and edge reconnection tools are handled in a separate way, as they do not ever appear in the palette of
 * any element since they can only be triggered by direct gestures/interactions.
 *
 * @author sbegaudeau
 */
@Service
public class ViewToolSectionsProvider implements IToolSectionsProvider {

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IObjectService objectService;

    private final Function<EObject, UUID> idProvider = (eObject) -> {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes());
    };

    public ViewToolSectionsProvider(ViewToolConfiguration configuration, IDiagramDescriptionService diagramDescriptionService) {
        this.urlParser = Objects.requireNonNull(configuration.getUrlParser());
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(configuration.getViewRepresentationDescriptionPredicate());
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(configuration.getViewRepresentationDescriptionSearchService());
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.objectService = Objects.requireNonNull(configuration.getObjectService());
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public List<ToolSection> handle(Object targetElement, Object diagramElement, Object diagramElementDescription, DiagramDescription diagramDescription) {
        List<ToolSection> toolSections = new ArrayList<>();

        if (diagramElement instanceof Diagram) {
            toolSections.addAll(this.getDiagramPalette(diagramDescription));
        } else if (diagramElement instanceof Node && diagramElementDescription instanceof NodeDescription nodeDescription) {
            toolSections.addAll(this.getNodePalette(diagramDescription, nodeDescription));
            toolSections.addAll(this.createExtraToolSections(diagramElementDescription, diagramElement));
        } else if (diagramElement instanceof Edge && diagramElementDescription instanceof EdgeDescription edgeDescription) {
            toolSections.addAll(this.getEdgePalette(edgeDescription));
            toolSections.addAll(this.createExtraToolSections(diagramElementDescription, diagramElement));
        }

        return toolSections.stream().filter(toolSection -> !toolSection.tools().isEmpty()).toList();
    }

    private List<ToolSection> getDiagramPalette(DiagramDescription diagramDescription) {
        var allToolSections = new ArrayList<ToolSection>();
        Optional<String> sourceElementId = this.getSourceElementId(diagramDescription.getId());
        if (sourceElementId.isPresent()) {
            String diagramPaletteId = "siriusComponents://diagramPalette?diagramId=" + sourceElementId.get();
            var optionalDiagramDescription = this.viewRepresentationDescriptionSearchService.findById(diagramDescription.getId())
                    .filter(org.eclipse.sirius.components.view.DiagramDescription.class::isInstance)
                    .map(org.eclipse.sirius.components.view.DiagramDescription.class::cast);

            if (optionalDiagramDescription.isPresent()) {
                org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
                viewDiagramDescription.getPalette();

                var diagramPalette = ToolSection.newToolSection(diagramPaletteId)
                        .label(viewDiagramDescription.getName())
                        .imageURL("")
                        .tools(this.createDiagramPaletteTools(viewDiagramDescription))
                        .build();
                allToolSections.add(diagramPalette);
            }
        }
        return allToolSections;
    }

    private List<ITool> createDiagramPaletteTools(org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription) {
        List<ITool> diagramTools = new ArrayList<>();
        for (NodeTool nodeTool : viewDiagramDescription.getPalette().getNodeTools()) {
            String toolId = this.idProvider.apply(nodeTool).toString();
            String selectionDescriptionId = "";
            if (nodeTool.getSelectionDescription() != null) {
                selectionDescriptionId = this.objectService.getId(nodeTool.getSelectionDescription());
            }
            var tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                    .label(nodeTool.getName())
                    .imageURL(ViewToolImageProvider.NODE_CREATION_TOOL_ICON)
                    .selectionDescriptionId(selectionDescriptionId)
                    .targetDescriptions(List.of())
                    .appliesToDiagramRoot(true)
                    .build();
            diagramTools.add(tool);
        }
        return diagramTools;
    }

    private List<ToolSection> getNodePalette(DiagramDescription diagramDescription, NodeDescription nodeDescription) {
        Optional<String> sourceElementId = this.getSourceElementId(nodeDescription.getId());
        var allToolSections = new ArrayList<ToolSection>();
        if (sourceElementId.isPresent()) {
            String nodePaletteId = "siriusComponents://nodePalette?nodeId=" + sourceElementId.get();
            var optionalNodeDescription = this.viewRepresentationDescriptionSearchService.findViewNodeDescriptionById(nodeDescription.getId());
            if (optionalNodeDescription.isPresent()) {
                org.eclipse.sirius.components.view.NodeDescription viewNodeDescription = optionalNodeDescription.get();
                var nodePalette = ToolSection.newToolSection(nodePaletteId)
                        .label(viewNodeDescription.getName())
                        .tools(this.createNodePaletteTools(diagramDescription, viewNodeDescription, nodeDescription))
                        .imageURL("")
                        .build();
                allToolSections.add(nodePalette);
            }
        }
        return allToolSections;
    }

    private List<ITool> createNodePaletteTools(DiagramDescription diagramDescription, org.eclipse.sirius.components.view.NodeDescription viewNodeDescription, NodeDescription nodeDescription) {
        List<ITool> tools = new ArrayList<>();

        for (NodeTool nodeTool : new ToolFinder().findNodeTools(viewNodeDescription)) {
            String toolId = this.idProvider.apply(nodeTool).toString();
            String selectionDescriptionId = "";
            if (nodeTool.getSelectionDescription() != null) {
                selectionDescriptionId = this.objectService.getId(nodeTool.getSelectionDescription());
            }
            var tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                    .label(nodeTool.getName())
                    .imageURL(ViewToolImageProvider.NODE_CREATION_TOOL_ICON)
                    .selectionDescriptionId(selectionDescriptionId)
                    .targetDescriptions(List.of())
                    .appliesToDiagramRoot(false)
                    .build();
            tools.add(tool);
        }

        for (EdgeTool edgeTool : new ToolFinder().findEdgeTools(viewNodeDescription)) {
            List<NodeDescription> targetNodeDescriptionCandidates = new ArrayList<>();
            for (DiagramElementDescription viewDiagramElementDescription : edgeTool.getTargetElementDescriptions()) {
                if (viewDiagramElementDescription instanceof org.eclipse.sirius.components.view.NodeDescription viewTagetNodeDescriptionCandidates) {
                    String sourceElementId = this.objectService.getId(viewTagetNodeDescriptionCandidates);
                    Optional<String> sourceId = this.getSourceId(diagramDescription.getId());

                    if (sourceId.isPresent()) {
                        String formattedNodeDescriptionId = IDiagramIdProvider.NODE_DESCRIPTION_KIND + '?' + IDiagramIdProvider.SOURCE_KIND + '=' + IDiagramIdProvider.VIEW_SOURCE_KIND + '&' + IDiagramIdProvider.SOURCE_ID + '=' + sourceId.get() + '&' + IDiagramIdProvider.SOURCE_ELEMENT_ID + '=' + sourceElementId;
                        Optional<NodeDescription> nodeDescriptionTargetCandidate = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, formattedNodeDescriptionId);
                        if (nodeDescriptionTargetCandidate.isPresent()) {
                            targetNodeDescriptionCandidates.add(nodeDescriptionTargetCandidate.get());
                        }
                    }
                }
            }
            String toolId = this.idProvider.apply(edgeTool).toString();
            var tool = SingleClickOnTwoDiagramElementsTool.newSingleClickOnTwoDiagramElementsTool(toolId)
                    .label(edgeTool.getName())
                    .imageURL(ViewToolImageProvider.EDGE_CREATION_TOOL_ICON)
                    .candidates(List.of(SingleClickOnTwoDiagramElementsCandidate.newSingleClickOnTwoDiagramElementsCandidate()
                            .sources(List.of(nodeDescription))
                            .targets(targetNodeDescriptionCandidates)
                            .build()))
                    .build();

            tools.add(tool);
        }
        return tools;
    }

    private List<ITool> createEdgePaletteTools(org.eclipse.sirius.components.view.EdgeDescription viewEdgeDescription) {
        List<ITool> tools = new ArrayList<>();
        List<NodeTool> paletteSingleTargetTools = new ToolFinder().findNodeTools(viewEdgeDescription);
        for (NodeTool nodeTool : paletteSingleTargetTools) {
            String toolId = this.idProvider.apply(nodeTool).toString();
            String selectionDescriptionId = "";
            if (nodeTool.getSelectionDescription() != null) {
                selectionDescriptionId = this.objectService.getId(nodeTool.getSelectionDescription());
            }
            var tool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                    .label(nodeTool.getName())
                    .imageURL(ViewToolImageProvider.NODE_CREATION_TOOL_ICON)
                    .targetDescriptions(List.of())
                    .selectionDescriptionId(selectionDescriptionId)
                    .appliesToDiagramRoot(false)
                    .build();

            tools.add(tool);
        }
        return tools;
    }

    private List<ToolSection> getEdgePalette(EdgeDescription edgeDescription) {
        Optional<String> optionalSourceElementId = this.getSourceElementId(edgeDescription.getId());
        var allToolSections = new ArrayList<ToolSection>();
        if (optionalSourceElementId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();

            var optionalEdgeDescription = this.viewRepresentationDescriptionSearchService.findViewEdgeDescriptionById(edgeDescription.getId());
            if (optionalEdgeDescription.isPresent()) {
                org.eclipse.sirius.components.view.EdgeDescription viewEdgeDescription = optionalEdgeDescription.get();

                String nodePaletteId = "siriusComponents://edgePalette?edgeId=" + sourceElementId;
                var nodePalette = ToolSection.newToolSection(nodePaletteId)
                        .label(viewEdgeDescription.getName())
                        .tools(this.createEdgePaletteTools(viewEdgeDescription))
                        .imageURL("")
                        .build();
                allToolSections.add(nodePalette);
            }
        }
        return allToolSections;
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }

    private List<ToolSection> createExtraToolSections(Object diagramElementDescription, Object diagramElement) {
        List<ToolSection> extraToolSections = new ArrayList<>();

        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        boolean unsynchronizedMapping = false;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            targetDescriptions.add(nodeDescription);
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(nodeDescription.getSynchronizationPolicy());
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            targetDescriptions.addAll(edgeDescription.getSourceNodeDescriptions());
            unsynchronizedMapping = SynchronizationPolicy.UNSYNCHRONIZED.equals(((EdgeDescription) diagramElementDescription).getSynchronizationPolicy());
        }

        // Graphical Delete Tool for unsynchronized mapping only (the handler is never called)
        if (diagramElementDescription instanceof NodeDescription || diagramElementDescription instanceof EdgeDescription) {
            if (this.hasLabelEditTool(diagramElementDescription)) {
                // Edit Tool (the handler is never called)
                var editToolSection = this.createExtraEditLabelEditTool(targetDescriptions);
                extraToolSections.add(editToolSection);
            }
            if (unsynchronizedMapping) {
                // Graphical Delete Tool (the handler is never called)
                var graphicalDeleteToolSection = this.createExtraGraphicalDeleteTool(targetDescriptions);
                extraToolSections.add(graphicalDeleteToolSection);
            }
            if (this.hasDeleteTool(diagramElementDescription)) {
                // Semantic Delete Tool (the handler is never called)
                var semanticDeleteToolSection = this.createExtraSemanticDeleteTool(targetDescriptions);
                extraToolSections.add(semanticDeleteToolSection);
            }
            if (this.isCollapsible(diagramElementDescription, diagramElement)) {
                // Collapse or expand Tool (the handler is never called)
                var expandCollapseToolSection = this.createExtraExpandCollapseTool(targetDescriptions, diagramElement);
                extraToolSections.add(expandCollapseToolSection);
            }
        }
        return extraToolSections;
    }

    private ToolSection createExtraExpandCollapseTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {
        var expandCollapseToolSectionBuilder = ToolSection.newToolSection("expand-collapse-section")
                .label("")
                .imageURL("")
                .tools(List.of());

        if (diagramElement instanceof Node node) {
            List<ITool> collapsingTools = new ArrayList<>();
            SingleClickOnDiagramElementTool collapseTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("collapse")
                    .label("Collapse")
                    .imageURL(DiagramImageConstants.COLLAPSE_SVG)
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .build();

            SingleClickOnDiagramElementTool expandTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("expand")
                    .label("Expand")
                    .imageURL(DiagramImageConstants.EXPAND_SVG)
                    .targetDescriptions(targetDescriptions)
                    .appliesToDiagramRoot(false)
                    .build();

            switch (node.getCollapsingState()) {
                case EXPANDED -> collapsingTools.add(collapseTool);
                case COLLAPSED -> collapsingTools.add(expandTool);
                default -> {
                    // Nothing on purpose
                }
            }
            expandCollapseToolSectionBuilder.tools(collapsingTools);
        }
        return expandCollapseToolSectionBuilder.build();
    }

    private ToolSection createExtraSemanticDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        SingleClickOnDiagramElementTool semanticDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete")
                .label("Delete from model")
                .imageURL(DiagramImageConstants.SEMANTIC_DELETE_SVG)
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();

        return ToolSection.newToolSection("semantic-delete-section")
                .label("")
                .imageURL("")
                .tools(List.of(semanticDeleteTool))
                .build();
    }

    private ToolSection createExtraGraphicalDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        SingleClickOnDiagramElementTool graphicalDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("graphical-delete")
                .label("Delete from diagram")
                .imageURL(DiagramImageConstants.GRAPHICAL_DELETE_SVG)
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();

        return ToolSection.newToolSection("graphical-delete-section")
                .label("")
                .imageURL("")
                .tools(List.of(graphicalDeleteTool))
                .build();
    }

    private boolean isCollapsible(Object diagramElementDescription, Object diagramElement) {
        if (diagramElementDescription instanceof NodeDescription nodeDescription && diagramElement instanceof Node) {
            return nodeDescription.isCollapsible();
        }
        return false;
    }

    private boolean hasLabelEditTool(Object diagramElementDescription) {
        boolean result = true;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            if (nodeDescription.getLabelEditHandler() instanceof IViewNodeLabelEditHandler viewNodeLabelEditHandler) {
                result = viewNodeLabelEditHandler.hasLabelEditTool();
            }
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            if (edgeDescription.getLabelEditHandler() instanceof IViewEdgeLabelEditHandler viewEdgeLabelEditHandler) {
                result = viewEdgeLabelEditHandler.hasLabelEditTool(EdgeLabelKind.CENTER_LABEL);
            }
        }
        return result;
    }

    private ToolSection createExtraEditLabelEditTool(List<IDiagramElementDescription> targetDescriptions) {
        SingleClickOnDiagramElementTool editTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit")
                .label("Edit")
                .imageURL(DiagramImageConstants.EDIT_SVG)
                .targetDescriptions(targetDescriptions)
                .appliesToDiagramRoot(false)
                .build();

        return ToolSection.newToolSection("edit-section")
                .label("")
                .imageURL("")
                .tools(List.of(editTool))
                .build();
    }

    private boolean hasDeleteTool(Object diagramElementDescription) {
        boolean result = true;
        if (diagramElementDescription instanceof NodeDescription nodeDescription) {
            if (nodeDescription.getDeleteHandler() instanceof IViewNodeDeleteHandler viewNodeDeleteHandler) {
                result = viewNodeDeleteHandler.hasSemanticDeleteTool();
            }
        } else if (diagramElementDescription instanceof EdgeDescription edgeDescription) {
            if (edgeDescription.getDeleteHandler() instanceof IViewNodeDeleteHandler viewElementDeleteHandler) {
                result = viewElementDeleteHandler.hasSemanticDeleteTool();
            }
        }
        return result;
    }
}
