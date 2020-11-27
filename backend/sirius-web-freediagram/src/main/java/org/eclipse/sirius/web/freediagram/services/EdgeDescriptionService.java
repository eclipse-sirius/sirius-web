/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.freediagram.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.web.freediagram.behaviors.IEdgeMappingBehavior;
import org.eclipse.sirius.web.freediagram.styles.IEdgeStyleProvider;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.services.api.objects.IEditService;
import org.eclipse.sirius.web.services.api.objects.IObjectService;

/**
 * Create an OD Web EdgeDescription.
 *
 * @author hmarchadour
 */
public class EdgeDescriptionService {

    private final IObjectService objectService;

    private final IEditService editService;

    public EdgeDescriptionService(IObjectService objectService, IEditService editService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
    }

    public EdgeDescription create(UUID edgeDescriptionId, IEdgeMappingBehavior edgeBehavior, IEdgeStyleProvider edgeStyleProvider, List<NodeDescription> sourceNodeDescriptions,
            List<NodeDescription> targetNodeDescriptions) {
        Function<VariableManager, String> idProvider = variableManager -> {
            // @formatter:off
            var sourceId = Optional.of(variableManager.getVariables().get(EdgeDescription.SOURCE_NODE))
                    .filter(Element.class::isInstance)
                    .map(Element.class::cast)
                    .map(Element::getProps)
                    .filter(NodeElementProps.class::isInstance)
                    .map(NodeElementProps.class::cast)
                    .map(NodeElementProps::getId)
                    .orElse(""); //$NON-NLS-1$

            var targetId = Optional.of(variableManager.getVariables().get(EdgeDescription.TARGET_NODE))
                    .filter(Element.class::isInstance)
                    .map(Element.class::cast)
                    .map(Element::getProps)
                    .filter(NodeElementProps.class::isInstance)
                    .map(NodeElementProps.class::cast)
                    .map(NodeElementProps::getId)
                    .orElse(""); //$NON-NLS-1$

            var count = Optional.of(variableManager.getVariables().get(EdgeDescription.COUNT))
                    .filter(Integer.class::isInstance).orElse(0);
            // @formatter:on

            return edgeDescriptionId + ": " + sourceId + " --> " + targetId + " - " + count; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        };

        Function<VariableManager, String> targetIdProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null);
        };
        Function<VariableManager, String> targetKindProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getKind).orElse(null);
        };
        Function<VariableManager, String> targetLabelProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null);
        };

        Function<VariableManager, List<Object>> semanticElementsProvider = edgeBehavior::getSemanticElements;

        Function<VariableManager, List<Element>> sourceNodesProvider = variableManager -> this.findElements(variableManager, edgeBehavior::getSourceElements, sourceNodeDescriptions);

        Function<VariableManager, List<Element>> targetNodesProvider = variableManager -> this.findElements(variableManager, edgeBehavior::getTargetElements, targetNodeDescriptions);

        Function<VariableManager, Optional<Label>> beginLabelProvider = variableManager -> Optional.empty();

        Function<VariableManager, Optional<Label>> centerLabelProvider = this.createLabelProvider("_centerlabel", edgeStyleProvider); //$NON-NLS-1$

        Function<VariableManager, Optional<Label>> endLabelProvider = variableManager -> Optional.empty();

        // @formatter:off

        return EdgeDescription.newEdgeDescription(edgeDescriptionId)
                .idProvider(idProvider)
                .targetObjectIdProvider(targetIdProvider)
                .targetObjectKindProvider(targetKindProvider)
                .targetObjectLabelProvider(targetLabelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .beginLabelProvider(beginLabelProvider)
                .centerLabelProvider(centerLabelProvider)
                .endLabelProvider(endLabelProvider)
                .sourceNodeDescriptions(sourceNodeDescriptions)
                .targetNodeDescriptions(targetNodeDescriptions)
                .sourceNodesProvider(sourceNodesProvider)
                .targetNodesProvider(targetNodesProvider)
                .styleProvider(edgeStyleProvider::getEdgeStyle)
                .deleteHandler(this::handleDelete)
                .build();
        // @formatter:on
    }

    private List<Element> findElements(VariableManager variableManager, Function<VariableManager, List<Object>> semanticCandidatesFunction, List<NodeDescription> nodeDescriptions) {
        var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
        if (optionalCache.isEmpty()) {
            return List.of();
        }
        DiagramRenderingCache cache = optionalCache.get();
        List<Object> semanticCandidates = semanticCandidatesFunction.apply(variableManager);
        // @formatter:off
        return semanticCandidates.stream()
                .flatMap(semanticObject-> cache.getObjectToNodes().getOrDefault(semanticObject, Collections.emptyList()).stream())
                .filter(element -> this.isFromMapping(element, nodeDescriptions))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        // @formatter:on
    }

    private boolean isFromMapping(Element nodeElement, List<NodeDescription> nodeDescriptions) {
        if (nodeElement.getProps() instanceof NodeElementProps) {
            NodeElementProps props = (NodeElementProps) nodeElement.getProps();
            return nodeDescriptions.stream().anyMatch(nodeDescription -> Objects.equals(nodeDescription.getId(), props.getDescriptionId()));
        }
        return false;
    }

    private Function<VariableManager, Optional<Label>> createLabelProvider(String idSuffix, IEdgeStyleProvider edgeStyleProvider) {
        return variableManager -> {
            String ownerId = variableManager.get(LabelDescription.OWNER_ID, String.class).orElse(""); //$NON-NLS-1$

            Optional<String> optionalLabel = variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel);

            String labelId = ownerId + idSuffix;
            LabelStyle style = edgeStyleProvider.getLabelStyle(variableManager);
            // @formatter:off
            return optionalLabel.map(
                label -> Label.newLabel(labelId)
                .type("label:inside-center") //$NON-NLS-1$
                .text(label)
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .style(style)
                .build()
            );
            // @formatter:on
        };
    }

    private Status handleDelete(VariableManager variableManager) {
        // @formatter:off
        return variableManager.get(VariableManager.SELF, Object.class)
            .map(object -> {
                this.editService.delete(object);
                return Status.OK;
            })
            .orElse(Status.ERROR);
        // @formatter:on
    }

}
