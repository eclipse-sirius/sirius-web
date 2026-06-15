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
import java.util.Objects;
import java.util.Optional;
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
import org.eclipse.sirius.components.representations.Variable;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.Tool;
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

    private final List<IRepresentationMetadataProvider> representationMetadataProviders;

    private final IViewEMFMessageService messageService;

    public ToolExecutionLabelProvider(List<IRepresentationMetadataProvider> representationMetadataProviders, IViewEMFMessageService messageService) {
        this.representationMetadataProviders = Objects.requireNonNull(representationMetadataProviders);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public String getLabel(Tool tool, VariableManager variableManager) {
        String toolName = Optional.ofNullable(tool.getName())
                .orElseGet(() -> tool.getClass().getSimpleName());

        return this.getTargetLabel(variableManager)
                .map(targetLabel -> MessageFormat.format(this.messageService.usedToolOnTargetLabel(), toolName, targetLabel))
                .orElseGet(() -> MessageFormat.format(this.messageService.usedToolUnknownTargetLabel(), toolName));
    }

    private Optional<String> getTargetLabel(VariableManager variableManager) {
        return variableManager.get(CommonVariables.EDITING_CONTEXT.name(), IEditingContext.class)
                .flatMap(editingContext -> variableManager.get(DiagramVariables.DIAGRAM_CONTEXT.name(), DiagramContext.class)
                        .map(diagramContext -> this.getTargetLabel(variableManager, editingContext, diagramContext)));
    }

    private String getTargetLabel(VariableManager variableManager, IEditingContext editingContext, DiagramContext diagramContext) {
        String selectedElementsLabel = this.getSelectedElementsLabel(variableManager);
        if (!selectedElementsLabel.isEmpty()) {
            return selectedElementsLabel;
        }
        return this.getDiagramLabel(editingContext, diagramContext);
    }

    private String getSelectedElementsLabel(VariableManager variableManager) {
        Stream<String> selectedNodeLabels = this.getSelectedElements(variableManager, DiagramVariables.SELECTED_NODE, DiagramVariables.SELECTED_NODES, Node.class)
                .map(this::getNodeLabel);
        Stream<String> selectedEdgeLabels = this.getSelectedElements(variableManager, DiagramVariables.SELECTED_EDGE, DiagramVariables.SELECTED_EDGES, Edge.class)
                .map(this::getEdgeLabel);

        return Stream.concat(selectedNodeLabels, selectedEdgeLabels)
                .filter(label -> !label.isBlank())
                .collect(Collectors.joining(", "));
    }

    private <T> Stream<T> getSelectedElements(VariableManager variableManager, Variable selectedElementVariable, Variable selectedElementsVariable, Class<T> type) {
        List<?> selectedElements = variableManager.get(selectedElementsVariable.name(), List.class).orElse(List.of());
        return Stream.concat(
                variableManager.get(selectedElementVariable.name(), type).stream(),
                selectedElements.stream().filter(type::isInstance).map(type::cast)
        );
    }

    private String getDiagramLabel(IEditingContext editingContext, DiagramContext diagramContext) {
        return this.representationMetadataProviders.stream()
                .flatMap(representationMetadataProvider -> representationMetadataProvider.getMetadata(editingContext.getId(), diagramContext.diagram().getId()).stream())
                .findFirst()
                .map(RepresentationMetadata::label)
                .orElse("");
    }

    private String getNodeLabel(Node node) {
        return Optional.ofNullable(node.getInsideLabel())
                .map(InsideLabel::getText)
                .orElse("");
    }

    private String getEdgeLabel(Edge edge) {
        return Optional.ofNullable(edge.getCenterLabel())
                .map(Label::text)
                .orElse("");
    }
}
