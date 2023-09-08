/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDropNodeCompatibilityProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeCompatibilityEntry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * The IDropNodeCompatibilityProvider for View-based diagrams.
 *
 * @author pcdavid
 */
@Service
public class DropNodeCompatibiliyProvider implements IDropNodeCompatibilityProvider {
    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IDiagramIdProvider idProvider;

    public DropNodeCompatibiliyProvider(IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate,
            IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IDiagramIdProvider idProvider) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.idProvider = Objects.requireNonNull(idProvider);
    }

    @Override
    public boolean canHandle(org.eclipse.sirius.components.diagrams.description.DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public List<DropNodeCompatibilityEntry> getDropNodeCompatibility(Diagram diagram, IEditingContext editingContext) {
        var optionalDiagramDescription = this.viewRepresentationDescriptionSearchService.findById(diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);

        if (optionalDiagramDescription.isPresent()) {
            return this.computeDropNodeCompatibility(optionalDiagramDescription.get());
        } else {
            return List.of();
        }
    }

    private List<DropNodeCompatibilityEntry> computeDropNodeCompatibility(DiagramDescription diagramDescription) {
        Map<String, DropNodeCompatibilityEntry> entries = new LinkedHashMap<>();

        new ToolFinder().findDropNodeTool(diagramDescription).ifPresent(dropNodeOnDiagramTool -> {
            for (var acceptedNodeDescription : dropNodeOnDiagramTool.getAcceptedNodeTypes()) {
                var acceptedNodeDescriptionId = this.idProvider.getId(acceptedNodeDescription);
                entries.put(acceptedNodeDescriptionId, new DropNodeCompatibilityEntry(acceptedNodeDescriptionId, true, new ArrayList<>()));
            }
            dropNodeOnDiagramTool.getAcceptedNodeTypes();
        });

        this.forEachNodeDescription(diagramDescription, nodeDescription -> {
            var targetNodeDescriptionId = this.idProvider.getId(nodeDescription);
            new ToolFinder().findDropNodeTool(nodeDescription).ifPresent(dropNodeTool -> {
                for (var sourceNodeDescription : dropNodeTool.getAcceptedNodeTypes()) {
                    var sourceNodeDescriptionId = this.idProvider.getId(sourceNodeDescription);
                    var entry = entries.get(sourceNodeDescriptionId);
                    if (entry == null) {
                        entry = new DropNodeCompatibilityEntry(sourceNodeDescriptionId, false, new ArrayList<>());
                        entries.put(sourceNodeDescriptionId, entry);
                    }
                    entry.droppableOnNodeTypes().add(targetNodeDescriptionId);
                }
            });
        });
        return entries.values().stream().toList();
    }

    private void forEachNodeDescription(DiagramDescription diagramDescription, Consumer<NodeDescription> operation) {
        Set<NodeDescription> seen = new HashSet<>();
        this.visitUnseenNodeDescriptions(diagramDescription.getNodeDescriptions(), seen, operation);
    }

    private void visitUnseenNodeDescriptions(Collection<NodeDescription> nodeDescriptions, Set<NodeDescription> seen, Consumer<NodeDescription> operation) {
        for (NodeDescription nodeDescription : nodeDescriptions) {
            if (!seen.contains(nodeDescription)) {
                operation.accept(nodeDescription);
                seen.add(nodeDescription);
                this.visitUnseenNodeDescriptions(nodeDescription.getChildrenDescriptions(), seen, operation);
                this.visitUnseenNodeDescriptions(nodeDescription.getReusedChildNodeDescriptions(), seen, operation);
                this.visitUnseenNodeDescriptions(nodeDescription.getBorderNodesDescriptions(), seen, operation);
                this.visitUnseenNodeDescriptions(nodeDescription.getReusedBorderNodeDescriptions(), seen, operation);
            }
        }
    }
}
