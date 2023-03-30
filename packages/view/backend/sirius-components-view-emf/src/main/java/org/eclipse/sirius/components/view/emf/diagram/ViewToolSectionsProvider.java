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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramImageConstants;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolSectionsProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IKindParser;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
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

    private static final String SIRIUS_PROTOCOL = "siriusComponents://";

    private static final String SOURCE_ID = "sourceId";

    private static final String SOURCE_ELEMENT_ID = "sourceElementId";

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IKindParser urlIdPaser;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public ViewToolSectionsProvider(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramDescriptionService diagramDescriptionService, IKindParser urlIdPaser) {
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.urlIdPaser = Objects.requireNonNull(urlIdPaser);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.getSourceDiagramDescription(diagramDescription).isPresent();
    }

    private boolean isDiagramDescriptionEqualSourceElementId(DiagramDescription diagramDescription, String diagramElementOrDiagramSourceId) {
        if (!diagramDescription.getId().startsWith(SIRIUS_PROTOCOL)) {
            return false;
        }
        Optional<String> diagramDescriptionSourceId = this.urlIdPaser.getParameterValues(diagramDescription.getId()).get(SOURCE_ID).stream().findAny();
        return diagramDescriptionSourceId.isPresent() && diagramDescriptionSourceId.get().equals(diagramElementOrDiagramSourceId);
    }

    private Optional<DiagramDescription> getDiagramDescriptionFromSourceElementId(IEditingContext editingContext, String sourceElementId) {
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream().filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast).filter(diagramDescription -> this.isDiagramDescriptionEqualSourceElementId(diagramDescription, sourceElementId)).findAny();
    }

    private Optional<DiagramDescription> getDiagramDescriptionFromDiagramOrDiagramElement(IEditingContext editingContext, Object diagramElementOrDiagram) {
        Map<String, List<String>> urlAttributes = new HashMap<>();
        if (diagramElementOrDiagram instanceof Diagram diagram) {
            urlAttributes =  this.urlIdPaser.getParameterValues(diagram.getDescriptionId());
        }
        else if (diagramElementOrDiagram instanceof IDiagramElement diagramElement) {
            urlAttributes =  this.urlIdPaser.getParameterValues(diagramElement.getDescriptionId());
        }
        Optional<String> sourceId = urlAttributes.get(SOURCE_ID).stream().findAny();
        if (sourceId.isPresent()) {
            return this.getDiagramDescriptionFromSourceElementId(editingContext, sourceId.get());
        }
        return Optional.empty();
    }

    @Override
    public List<ToolSection> handle(Object targetElement, IEditingContext editingContext, Object diagramElementOrDiagram) {
        List<ToolSection> toolSections = new ArrayList<>();

        if (diagramElementOrDiagram instanceof Diagram diagram) {
            Optional<DiagramDescription> diagramDescription = this.getDiagramDescriptionFromDiagramOrDiagramElement(editingContext, diagram);
            if (diagramDescription.isPresent()) {
                toolSections.addAll(this.getDiagramPalette(diagramDescription.get()));
            }
        } else if (diagramElementOrDiagram instanceof Node node) {
            Optional<DiagramDescription> diagramDescription = this.getDiagramDescriptionFromDiagramOrDiagramElement(editingContext, node);
            if (diagramDescription.isPresent()) {
                Optional<NodeDescription> nodeDescription = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription.get(), node.getDescriptionId());
                if (nodeDescription.isPresent()) {
                    toolSections.addAll(this.getNodeToolSections(diagramDescription.get(), nodeDescription.get()));
                    toolSections.addAll(this.createExtraToolSections(nodeDescription.get(), node));
                }
            }
        } else if (diagramElementOrDiagram instanceof Edge edge) {
            Optional<DiagramDescription> diagramDescription = this.getDiagramDescriptionFromDiagramOrDiagramElement(editingContext, edge);
            if (diagramDescription.isPresent()) {
                Optional<EdgeDescription> edgeDescription = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription.get(), edge.getDescriptionId());
                if (edgeDescription.isPresent()) {
                    toolSections.addAll(this.getEdgeToolSections(diagramDescription.get(), edgeDescription.get()));
                    toolSections.addAll(this.createExtraToolSections(edgeDescription.get(), edge));
                }
            }
        }

        return toolSections.stream().filter(toolSection -> !toolSection.getTools().isEmpty()).toList();
    }

    private Optional<org.eclipse.sirius.components.view.DiagramDescription> getSourceDiagramDescription(DiagramDescription diagramDescription) {
        if (diagramDescription != null && diagramDescription.getCanCreatePredicate() instanceof IViewDiagramCreationPredicate viewCreationPredicate) {
            return Optional.of(viewCreationPredicate.getSourceDiagramDescription());
        } else {
            return Optional.empty();
        }
    }

    private Optional<ToolSection> findToolSectionById(DiagramDescription diagramDescription, String id) {
        return diagramDescription.getToolSections().stream().filter(section -> Objects.equals(section.getId(), id)).findFirst();
    }

    private List<ToolSection> getDiagramPalette(DiagramDescription diagramDescription) {
        Optional<String> sourceElementId = this.urlIdPaser.getParameterValues(diagramDescription.getId()).get(SOURCE_ELEMENT_ID).stream().findAny();
        if (sourceElementId.isPresent()) {
            String diagramPaletteId = "siriusComponents://diagramPalette?diagramId=" + sourceElementId.get();
            return this.findToolSectionById(diagramDescription, diagramPaletteId).stream().toList();
        }
        else {
            return List.of();
        }
    }

    private List<ToolSection> getNodeToolSections(DiagramDescription diagramDescription, NodeDescription nodeDescription) {
        Optional<String> sourceElementId = this.urlIdPaser.getParameterValues(nodeDescription.getId()).get(SOURCE_ELEMENT_ID).stream().findAny();
        if (sourceElementId.isPresent()) {
            String diagramPaletteId = "siriusComponents://nodePalette?nodeId=" + sourceElementId.get();
            return this.findToolSectionById(diagramDescription, diagramPaletteId).stream().toList();
        }
        else {
            return List.of();
        }
    }

    private List<ToolSection> getEdgeToolSections(DiagramDescription diagramDescription, EdgeDescription edgeDescription) {
        Optional<String> sourceElementId = this.urlIdPaser.getParameterValues(edgeDescription.getId()).get(SOURCE_ELEMENT_ID).stream().findAny();
        if (sourceElementId.isPresent()) {
            String diagramPaletteId = "siriusComponents://edgePalette?edgeId=" + sourceElementId.get();
            return this.findToolSectionById(diagramDescription, diagramPaletteId).stream().toList();
        }
        else {
            return List.of();
        }
    }


    private List<ToolSection> createExtraToolSections(Object diagramElementDescription, Object diagramElement) {
        List<ToolSection> extraToolSections = new ArrayList<>();

        List<IDiagramElementDescription> targetDescriptions = new ArrayList<>();
        boolean unsynchronizedMapping = false;
        //@formatter:off
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
        //@formatter:on
    }

    private ToolSection createExtraExpandCollapseTool(List<IDiagramElementDescription> targetDescriptions, Object diagramElement) {
        Function<VariableManager, IStatus> fakeHandler = variableManager -> new Success();
        // @formatter:off
        var expandCollapseToolSectionBuilder = ToolSection.newToolSection("expand-collapse-section")
                .label("")
                .imageURL("")
                .tools(List.of());
        // @formatter:on

        if (diagramElement instanceof Node node) {
            List<ITool> collapsingTools = new ArrayList<>();
            // @formatter:off
            SingleClickOnDiagramElementTool collapseTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("collapse")
                    .label("Collapse")
                    .imageURL(DiagramImageConstants.COLLAPSE_SVG)
                    .targetDescriptions(targetDescriptions)
                    .handler(fakeHandler)
                    .appliesToDiagramRoot(false)
                    .build();
            SingleClickOnDiagramElementTool expandTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("expand")
                    .label("Expand")
                    .imageURL(DiagramImageConstants.EXPAND_SVG)
                    .targetDescriptions(targetDescriptions)
                    .handler(fakeHandler)
                    .appliesToDiagramRoot(false)
                    .build();
            // @formatter:on
            switch (node.getCollapsingState()) {
                case EXPANDED:
                    collapsingTools.add(collapseTool);
                    break;
                case COLLAPSED:
                    collapsingTools.add(expandTool);
                    break;
                default:
                    break;
            }
            expandCollapseToolSectionBuilder.tools(collapsingTools);
        }
        return expandCollapseToolSectionBuilder.build();
    }

    private ToolSection createExtraSemanticDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        // @formatter:off
        SingleClickOnDiagramElementTool semanticDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("semantic-delete")
                .label("Delete from model")
                .imageURL(DiagramImageConstants.SEMANTIC_DELETE_SVG)
                .targetDescriptions(targetDescriptions)
                .handler(variableManager -> new Success())
                .appliesToDiagramRoot(false)
                .build();
        return ToolSection.newToolSection("semantic-delete-section")
                .label("")
                .imageURL("")
                .tools(List.of(semanticDeleteTool))
                .build();
        // @formatter:on
    }

    private ToolSection createExtraGraphicalDeleteTool(List<IDiagramElementDescription> targetDescriptions) {
        // @formatter:off
        SingleClickOnDiagramElementTool graphicalDeleteTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("graphical-delete")
                .label("Delete from diagram")
                .imageURL(DiagramImageConstants.GRAPHICAL_DELETE_SVG)
                .targetDescriptions(targetDescriptions)
                .handler(variableManager -> new Success())
                .appliesToDiagramRoot(false)
                .build();
        return ToolSection.newToolSection("graphical-delete-section")
                .label("")
                .imageURL("")
                .tools(List.of(graphicalDeleteTool))
                .build();
        // @formatter:on
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
        // @formatter:off
        SingleClickOnDiagramElementTool editTool = SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool("edit")
                .label("Edit")
                .imageURL(DiagramImageConstants.EDIT_SVG)
                .targetDescriptions(targetDescriptions)
                .handler(variableManager -> new Success())
                .appliesToDiagramRoot(false)
                .build();
        return ToolSection.newToolSection("edit-section")
                .label("")
                .imageURL("")
                .tools(List.of(editTool))
                .build();
        // @formatter:on
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
