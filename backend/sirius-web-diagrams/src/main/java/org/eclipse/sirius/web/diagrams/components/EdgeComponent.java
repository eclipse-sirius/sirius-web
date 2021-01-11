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
package org.eclipse.sirius.web.diagrams.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.Fragment;
import org.eclipse.sirius.web.components.FragmentProps;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.web.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The component used to render an edge.
 *
 * @author sbegaudeau
 */
public class EdgeComponent implements IComponent {

    private static final String INVALID_NODE_ID = "INVALID_NODE_ID"; //$NON-NLS-1$

    private final EdgeComponentProps props;

    public EdgeComponent(EdgeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        EdgeDescription edgeDescription = this.props.getEdgeDescription();
        IEdgesRequestor edgesRequestor = this.props.getEdgesRequestor();
        DiagramRenderingCache cache = this.props.getCache();
        Set<UUID> movedElementIds = this.props.getMovedElementIds();
        EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

        List<Element> children = new ArrayList<>();

        // @formatter:off
        boolean hasCandidates = this.hasNodeCandidates(edgeDescription.getSourceNodeDescriptions(), cache)
                             && this.hasNodeCandidates(edgeDescription.getTargetNodeDescriptions(), cache);
        // @formatter:on

        if (hasCandidates) {
            VariableManager semanticElementsVariableManager = new VariableManager();
            variableManager.getVariables().forEach((key, value) -> semanticElementsVariableManager.put(key, value));
            semanticElementsVariableManager.put(DiagramDescription.CACHE, cache);

            List<Object> semanticElements = edgeDescription.getSemanticElementsProvider().apply(semanticElementsVariableManager);
            int count = 0;
            for (Object semanticElement : semanticElements) {
                VariableManager edgeVariableManager = variableManager.createChild();
                edgeVariableManager.put(VariableManager.SELF, semanticElement);
                edgeVariableManager.put(DiagramDescription.CACHE, cache);

                String targetObjectId = edgeDescription.getTargetObjectIdProvider().apply(edgeVariableManager);
                String targetObjectKind = edgeDescription.getTargetObjectKindProvider().apply(edgeVariableManager);
                String targetObjectLabel = edgeDescription.getTargetObjectLabelProvider().apply(edgeVariableManager);

                var optionalPreviousEdge = edgesRequestor.getByTargetObjectId(targetObjectId);
                SynchronizationPolicy synchronizationPolicy = edgeDescription.getSynchronizationPolicy();
                boolean shouldRender = synchronizationPolicy == SynchronizationPolicy.SYNCHRONIZED
                        || (synchronizationPolicy == SynchronizationPolicy.UNSYNCHRONIZED && optionalPreviousEdge.isPresent());

                List<Element> sourceNodes = edgeDescription.getSourceNodesProvider().apply(edgeVariableManager);
                if (shouldRender && !sourceNodes.isEmpty()) {
                    List<Element> targetNodes = edgeDescription.getTargetNodesProvider().apply(edgeVariableManager);

                    for (Element sourceNode : sourceNodes) {
                        for (Element targetNode : targetNodes) {
                            UUID id = this.computeEdgeId(sourceNode, targetNode, count);

                            EdgeStyle style = edgeDescription.getStyleProvider().apply(edgeVariableManager);

                            UUID sourceId = this.getId(sourceNode);
                            UUID targetId = this.getId(targetNode);

                            // @formatter:off
                            String edgeType = optionalPreviousEdge
                                    .map(Edge::getType)
                                    .orElse("edge:straight"); //$NON-NLS-1$

                            List<Position> routingPoints;
                            List<Element> labelChildren;
                            if (movedElementIds.contains(sourceId) || movedElementIds.contains(targetId)) {
                                routingPoints = edgeRoutingPointsProvider.getRoutingPoints(sourceNode, targetNode);
                                labelChildren = this.getLabelsChildren(edgeDescription, edgeVariableManager, Optional.empty(), id, routingPoints);
                            } else {
                                routingPoints = optionalPreviousEdge
                                        .map(Edge::getRoutingPoints)
                                        .orElseGet(()-> edgeRoutingPointsProvider.getRoutingPoints(sourceNode, targetNode));
                                labelChildren = this.getLabelsChildren(edgeDescription, edgeVariableManager, optionalPreviousEdge, id, routingPoints);
                            }

                            EdgeElementProps edgeElementProps = EdgeElementProps.newEdgeElementProps(id)
                                    .type(edgeType)
                                    .descriptionId(edgeDescription.getId())
                                    .targetObjectId(targetObjectId)
                                    .targetObjectKind(targetObjectKind)
                                    .targetObjectLabel(targetObjectLabel)
                                    .sourceId(sourceId)
                                    .targetId(targetId)
                                    .style(style)
                                    .routingPoints(routingPoints)
                                    .children(labelChildren)
                                    .build();
                            // @formatter:on

                            Element edgeElement = new Element(EdgeElementProps.TYPE, edgeElementProps);
                            children.add(edgeElement);
                            count++;
                        }
                    }
                }
            }
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private List<Element> getLabelsChildren(EdgeDescription edgeDescription, VariableManager edgeVariableManager, Optional<Edge> optionalPreviousEdge, UUID edgeId, List<Position> routingPoints) {
        List<Element> edgeChildren = new ArrayList<>();

        VariableManager labelVariableManager = edgeVariableManager.createChild();
        labelVariableManager.put(LabelDescription.OWNER_ID, edgeId);

        EdgeLabelPositionProvider labelBoundsProvider = new EdgeLabelPositionProvider(routingPoints);

        Optional.ofNullable(edgeDescription.getBeginLabelDescription()).map(labelDescription -> {
            Optional<Label> optionalPreviousLabel = optionalPreviousEdge.map(Edge::getBeginLabel);
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, optionalPreviousLabel, labelBoundsProvider, LabelType.EDGE_BEGIN.getValue());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        Optional.ofNullable(edgeDescription.getCenterLabelDescription()).map(labelDescription -> {
            Optional<Label> optionalPreviousLabel = optionalPreviousEdge.map(Edge::getCenterLabel);
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, optionalPreviousLabel, labelBoundsProvider, LabelType.EDGE_CENTER.getValue());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        Optional.ofNullable(edgeDescription.getEndLabelDescription()).map(labelDescription -> {
            Optional<Label> optionalPreviousLabel = optionalPreviousEdge.map(Edge::getEndLabel);
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, optionalPreviousLabel, labelBoundsProvider, LabelType.EDGE_END.getValue());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        return edgeChildren;
    }

    private UUID computeEdgeId(Element sourceNode, Element targetNode, int count) {
        // @formatter:off
        var sourceId = Optional.of(sourceNode.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getId)
                .map(UUID::toString)
                .orElse(INVALID_NODE_ID);

        var targetId = Optional.of(targetNode.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getId)
                .map(UUID::toString)
                .orElse(INVALID_NODE_ID);
        // @formatter:on
        String rawIdentifier = sourceId + " --> " + targetId + " - " + count; //$NON-NLS-1$ //$NON-NLS-2$
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes());
    }

    private boolean hasNodeCandidates(List<NodeDescription> nodeDescriptions, DiagramRenderingCache cache) {
        // @formatter:off
        return nodeDescriptions.stream()
                .map(NodeDescription::getId)
                .map(cache.getNodeDescriptionIdToNodes()::get)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .findAny()
                .isPresent();
        // @formatter:on
    }

    private UUID getId(Element nodeElement) {
        // @formatter:off
        return Optional.of(nodeElement.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getId)
                .orElse(UUID.randomUUID());
        // @formatter:on
    }

}
