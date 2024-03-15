/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to find information from view based diagram descriptions.
 *
 * @author sbegaudeau
 */
@Service
public class ViewDiagramDescriptionSearchService implements IViewDiagramDescriptionSearchService {

    private final IIdentityService identityService;

    private final IURLParser urlParser;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    public ViewDiagramDescriptionSearchService(IIdentityService identityService, IURLParser urlParser, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.urlParser = Objects.requireNonNull(urlParser);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
    }

    @Override
    public Optional<DiagramDescription> findById(IEditingContext editingContext, String diagramDescriptionId) {
        return this.viewRepresentationDescriptionSearchService.findById(editingContext, diagramDescriptionId)
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
    }

    @Override
    public Optional<NodeDescription> findViewNodeDescriptionById(IEditingContext editingContext, String nodeDescriptionId) {
        Optional<String> optionalSourceElementId = this.getSourceElementId(nodeDescriptionId);
        Optional<String> optionalSourceId = this.getSourceId(nodeDescriptionId);
        if (optionalSourceElementId.isPresent() && optionalSourceId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();
            var sourceId = optionalSourceId.get();

            var views = this.viewRepresentationDescriptionSearchService.findViewsBySourceId(editingContext, sourceId);

            return views.stream()
                    .flatMap(view -> view.getDescriptions().stream())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast)
                    .map(viewDiagramDescription -> this.findNodeDescriptionById(viewDiagramDescription, sourceElementId))
                    .flatMap(Optional::stream)
                    .findFirst();
        }
        return Optional.empty();
    }

    public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
        return this.findNodeDescription(nodeDescription -> Objects.equals(this.identityService.getId(nodeDescription), nodeDescriptionId), diagramDescription.getNodeDescriptions());
    }

    private Optional<NodeDescription> findNodeDescription(Predicate<NodeDescription> condition, List<NodeDescription> candidates) {
        Optional<NodeDescription> result = Optional.empty();
        ListIterator<NodeDescription> candidatesListIterator = candidates.listIterator();
        while (result.isEmpty() && candidatesListIterator.hasNext()) {
            org.eclipse.sirius.components.view.diagram.NodeDescription node = candidatesListIterator.next();
            if (condition.test(node)) {
                result = Optional.of(node);
            } else {
                List<NodeDescription> nodeDescriptionChildren = Stream.concat(node.getBorderNodesDescriptions().stream(), node.getChildrenDescriptions().stream()).toList();
                result = this.findNodeDescription(condition, nodeDescriptionChildren);
            }
        }
        return result;
    }

    @Override
    public Optional<EdgeDescription> findViewEdgeDescriptionById(IEditingContext editingContext, String edgeDescriptionId) {
        Optional<String> optionalSourceElementId = this.getSourceElementId(edgeDescriptionId);
        Optional<String> optionalSourceId = this.getSourceId(edgeDescriptionId);
        if (optionalSourceElementId.isPresent() && optionalSourceId.isPresent()) {
            var sourceElementId = optionalSourceElementId.get();
            var sourceId = optionalSourceId.get();

            var views = this.viewRepresentationDescriptionSearchService.findViewsBySourceId(editingContext, sourceId);

            return views.stream()
                    .flatMap(view -> view.getDescriptions().stream())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast)
                    .map(viewDiagramDescription -> this.findEdgeDescriptionById(viewDiagramDescription, sourceElementId))
                    .flatMap(Optional::stream)
                    .findFirst();
        }
        return Optional.empty();
    }

    private Optional<EdgeDescription> findEdgeDescriptionById(DiagramDescription diagramDescription, String edgeDescriptionId) {
        return diagramDescription.getEdgeDescriptions().stream()
                .filter(edgeDescription -> this.identityService.getId(edgeDescription).equals(edgeDescriptionId))
                .findFirst();
    }

    private Optional<String> getSourceElementId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ELEMENT_ID)).orElse(List.of()).stream().findFirst();
    }

    private Optional<String> getSourceId(String descriptionId) {
        var parameters = this.urlParser.getParameterValues(descriptionId);
        return Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_ID)).orElse(List.of()).stream().findFirst();
    }
}
