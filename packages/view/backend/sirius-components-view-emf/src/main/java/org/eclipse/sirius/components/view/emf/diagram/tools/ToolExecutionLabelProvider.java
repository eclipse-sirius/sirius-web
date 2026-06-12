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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramVariables;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataProvider;
import org.eclipse.sirius.components.core.api.variables.CommonVariables;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolExecutionLabelProvider;
import org.eclipse.sirius.components.view.emf.messages.IViewEMFMessageService;
import org.springframework.stereotype.Service;

/**
 * Provides a label explaining what a tool does.
 *
 * @author gdaniel
 */
@Service
public class ToolExecutionLabelProvider implements IToolExecutionLabelProvider {

    private static final String SPACE = " ";

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final List<IRepresentationMetadataProvider> representationMetadataProviders;

    private IDiagramIdProvider diagramIdProvider;

    private IViewEMFMessageService messageService;

    public ToolExecutionLabelProvider(IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, List<IRepresentationMetadataProvider> representationMetadataProviders,
            IDiagramIdProvider diagramIdProvider, IViewEMFMessageService messageService) {
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.representationMetadataProviders = Objects.requireNonNull(representationMetadataProviders);
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public String getLabel(Tool tool, VariableManager variableManager) {

        String toolName = tool.getName();
        if (toolName == null) {
            toolName = tool.getClass().getSimpleName();
        }

        String result = MessageFormat.format(this.messageService.usedToolUnknownTargetLabel(), toolName);

        Optional<IEditingContext> optionalEditingContext = variableManager.get(CommonVariables.EDITING_CONTEXT.name(), IEditingContext.class);
        Optional<DiagramContext> optionalDiagramContext = variableManager.get(DiagramVariables.DIAGRAM_CONTEXT.name(), DiagramContext.class);

        if (optionalEditingContext.isPresent() && optionalDiagramContext.isPresent()) {
            IEditingContext editingContext = optionalEditingContext.get();

            Optional<DiagramDescription> optionalDiagramDescription = optionalDiagramContext
                    .flatMap(diagramContext -> this.viewRepresentationDescriptionSearchService.findById(editingContext, diagramContext.diagram().getDescriptionId())
                            .filter(DiagramDescription.class::isInstance)
                            .map(DiagramDescription.class::cast));

            if (optionalDiagramDescription.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();

                Optional<Node> optionalSelectedNode = variableManager.get(DiagramVariables.SELECTED_NODE.name(), Node.class);
                Optional<Edge> optionalSelectedEdge = variableManager.get(DiagramVariables.SELECTED_EDGE.name(), Edge.class);
                @SuppressWarnings("unchecked")
                List<Node> selectedNodes = variableManager.get(DiagramVariables.SELECTED_NODES.name(), List.class).orElse(List.of());
                @SuppressWarnings("unchecked")
                List<Edge> selectedEdges = variableManager.get(DiagramVariables.SELECTED_EDGES.name(), List.class).orElse(List.of());

                String targetLabel = Stream.of(
                                optionalSelectedNode.stream(),
                                selectedNodes.stream(),
                                optionalSelectedEdge.stream(),
                                selectedEdges.stream()
                        ).flatMap(Function.identity())
                        .map(diagramElement -> {
                            String label = "";
                            if (diagramElement instanceof Edge edge) {
                                label = this.getEdgeLabel(edge, diagramDescription);
                            } else if (diagramElement instanceof Node node) {
                                label = this.getNodeLabel(node, variableManager);
                            }
                            return label;
                        }).collect(Collectors.joining(", "));

                if (!targetLabel.isEmpty()) {
                    result = MessageFormat.format(this.messageService.usedToolOnTargetLabel(), toolName, targetLabel);
                } else {
                    // The tool is executed on the diagram
                    String diagramLabel = optionalDiagramContext.flatMap(diagramContext -> this.representationMetadataProviders.stream()
                                    .flatMap(representationMetadataProvider -> representationMetadataProvider.getMetadata(editingContext.getId(), diagramContext.diagram().getId()).stream())
                                    .findFirst())
                            .map(RepresentationMetadata::label)
                            .orElse("");
                    String diagramDescriptionName = optionalDiagramDescription
                            .map(DiagramDescription::getName)
                            .orElse("");
                    String diagramTargetLabel = diagramDescriptionName + SPACE + diagramLabel;
                    result = MessageFormat.format(this.messageService.usedToolOnTargetLabel(), toolName, diagramTargetLabel);
                }
            }
        }

        return result;
    }

    private String getNodeLabel(Node node, VariableManager variableManager) {
        String nodeLabel = Optional.ofNullable(node.getInsideLabel())
                .map(InsideLabel::getText)
                .orElse("");
        String nodeDescriptionName = "";
        Optional<Map> optionalConvertedNodes = variableManager.get(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, Map.class);
        if (optionalConvertedNodes.isPresent()) {
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, org.eclipse.sirius.components.diagrams.description.NodeDescription> convertedNodes = optionalConvertedNodes.get();
            nodeDescriptionName = convertedNodes.entrySet().stream()
                    .filter(entry -> Objects.equals(entry.getValue().getId(), node.getDescriptionId()))
                    .findFirst()
                    .map(Map.Entry::getKey)
                    .map(NodeDescription::getName)
                    .orElse("");
        }
        return nodeDescriptionName + SPACE + nodeLabel;
    }

    private String getEdgeLabel(Edge edge, DiagramDescription diagramDescription) {
        String edgeLabel = Optional.ofNullable(edge.getCenterLabel())
                .map(Label::text)
                .orElse("");
        String edgeDescriptionName = diagramDescription.getEdgeDescriptions().stream()
                .filter(edgeDescription -> Objects.equals(this.diagramIdProvider.getId(edgeDescription), edge.getDescriptionId()))
                .findFirst()
                .map(EdgeDescription::getName)
                .orElse("");
        return edgeDescriptionName + SPACE + edgeLabel;
    }
}
