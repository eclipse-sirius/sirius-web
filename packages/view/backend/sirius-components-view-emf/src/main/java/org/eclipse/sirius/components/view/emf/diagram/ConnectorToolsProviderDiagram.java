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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IKindParser;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.springframework.stereotype.Service;

/**
 * Provides tools for the connector tool.
 *
 * @author mcharfadi
 */
@Service
public class ConnectorToolsProviderDiagram implements IConnectorToolsProvider {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IKindParser urlParser;

    public ConnectorToolsProviderDiagram(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramDescriptionService diagramDescriptionService, IKindParser urlParser) {
        this.representationDescriptionSearchService = representationDescriptionSearchService;
        this.diagramDescriptionService = diagramDescriptionService;
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        Map<String, List<String>> parameters = this.urlParser.getParameterValues(diagramDescription.getId());
        List<String> values = Optional.ofNullable(parameters.get(IDiagramIdProvider.SOURCE_KIND)).orElse(List.of());
        return values.contains(IDiagramIdProvider.VIEW_SOURCE_KIND);
    }

    @Override
    public List<ITool> getConnectorTools(Object sourceDiagramElement, Object targetDiagramElement, Diagram diagram, IEditingContext editingContext) {

        var optDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId());
        var optSourceDiagramElementDescriptionId = this.mapDiagramElementToDescriptionId(sourceDiagramElement);
        var optTargetDiagramElementDescriptionId = this.mapDiagramElementToDescriptionId(targetDiagramElement);

        boolean diagramElementDescriptionsPresent = optDiagramDescription.isPresent() && optSourceDiagramElementDescriptionId.isPresent() && optTargetDiagramElementDescriptionId.isPresent();
        List<ITool> result = null;
        if (diagramElementDescriptionsPresent && optDiagramDescription.get() instanceof DiagramDescription) {

            DiagramDescription diagramDescription = (DiagramDescription) optDiagramDescription.get();
            var optSourceDiagramElementDescription = this.mapDescriptionIdToDescription(optSourceDiagramElementDescriptionId.get(), diagramDescription, sourceDiagramElement);
            var optTargetDiagramElementDescription = this.mapDescriptionIdToDescription(optTargetDiagramElementDescriptionId.get(), diagramDescription, targetDiagramElement);

            if (optSourceDiagramElementDescription.isPresent() && optTargetDiagramElementDescription.isPresent()) {
                Object sourceDescription = optSourceDiagramElementDescription.get();
                Object targetDescription = optTargetDiagramElementDescription.get();
                result = diagramDescription.getToolSections().stream().flatMap(ts -> ts.getTools().stream())//
                        .filter(t -> t instanceof SingleClickOnTwoDiagramElementsTool)//
                        .map(SingleClickOnTwoDiagramElementsTool.class::cast)//
                        .filter(tool -> {
                            List<SingleClickOnTwoDiagramElementsCandidate> candidates = tool.getCandidates();
                            return candidates.stream().anyMatch(c -> c.getSources().contains(sourceDescription) && c.getTargets().contains(targetDescription));
                        }).collect(Collectors.toList());
            }
        }
        return result;
    }

    private Optional<String> mapDiagramElementToDescriptionId(Object object) {
        Optional<String> descriptionId = Optional.empty();
        if (object instanceof Node) {
            descriptionId = Optional.of(((Node) object).getDescriptionId());
        } else if (object instanceof Edge) {
            descriptionId = Optional.of(((Edge) object).getDescriptionId());
        }
        return descriptionId;
    }

    private Optional<Object> mapDescriptionIdToDescription(String descriptionId, DiagramDescription diagramDescription, Object diagramElement) {
        Optional<Object> result = Optional.empty();
        if (diagramElement instanceof Node) {
            var description = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, descriptionId);
            if (description.isPresent()) {
                result = Optional.of(description.get());
            }
        } else if (diagramElement instanceof Edge) {
            var description = this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, descriptionId);
            if (description.isPresent()) {
                result = Optional.of(description.get());
            }
        }
        return result;
    }
}
