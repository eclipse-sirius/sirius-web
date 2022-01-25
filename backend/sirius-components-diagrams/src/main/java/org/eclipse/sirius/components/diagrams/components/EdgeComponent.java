/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

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

        List<Element> children = new ArrayList<>();

        // @formatter:off
        boolean hasCandidates = this.hasNodeCandidates(edgeDescription.getSourceNodeDescriptions(), cache)
                             && this.hasNodeCandidates(edgeDescription.getTargetNodeDescriptions(), cache);

        // @formatter:on

        if (hasCandidates) {
            VariableManager semanticElementsVariableManager = new VariableManager();
            variableManager.getVariables().forEach(semanticElementsVariableManager::put);
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

                List<Element> sourceNodes = edgeDescription.getSourceNodesProvider().apply(edgeVariableManager);
                if (!sourceNodes.isEmpty()) {
                    List<Element> targetNodes = edgeDescription.getTargetNodesProvider().apply(edgeVariableManager);

                    for (Element sourceNode : sourceNodes) {
                        for (Element targetNode : targetNodes) {
                            String id = this.computeEdgeId(edgeDescription, sourceNode, targetNode, count);
                            var optionalPreviousEdge = edgesRequestor.getById(id);
                            var edgeInstanceVariableManager = edgeVariableManager.createChild();
                            edgeInstanceVariableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, cache.getNodeToObject().get(sourceNode));
                            edgeInstanceVariableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, cache.getNodeToObject().get(targetNode));

                            SynchronizationPolicy synchronizationPolicy = edgeDescription.getSynchronizationPolicy();
                            boolean shouldRender = synchronizationPolicy == SynchronizationPolicy.SYNCHRONIZED
                                    || (synchronizationPolicy == SynchronizationPolicy.UNSYNCHRONIZED && optionalPreviousEdge.isPresent());

                            if (shouldRender) {
                                EdgeStyle style = edgeDescription.getStyleProvider().apply(edgeInstanceVariableManager);

                                String sourceId = this.getId(sourceNode);
                                String targetId = this.getId(targetNode);

                                // @formatter:off
                                String edgeType = optionalPreviousEdge
                                        .map(Edge::getType)
                                        .orElse("edge:straight"); //$NON-NLS-1$

                                List<Position> routingPoints = optionalPreviousEdge.map(Edge::getRoutingPoints).orElse(List.of());
                                Ratio sourceAnchorRelativePosition = optionalPreviousEdge.map(Edge::getSourceAnchorRelativePosition).orElse(Ratio.UNDEFINED);
                                Ratio targetAnchorRelativePosition = optionalPreviousEdge.map(Edge::getTargetAnchorRelativePosition).orElse(Ratio.UNDEFINED);
                                List<Element> labelChildren = this.getLabelsChildren(edgeDescription, edgeVariableManager, optionalPreviousEdge, id, routingPoints);
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
                                        .sourceAnchorRelativePosition(sourceAnchorRelativePosition)
                                        .targetAnchorRelativePosition(targetAnchorRelativePosition)
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
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private List<Element> getLabelsChildren(EdgeDescription edgeDescription, VariableManager edgeVariableManager, Optional<Edge> optionalPreviousEdge, String edgeId, List<Position> routingPoints) {
        List<Element> edgeChildren = new ArrayList<>();

        VariableManager labelVariableManager = edgeVariableManager.createChild();
        labelVariableManager.put(LabelDescription.OWNER_ID, edgeId);

        Optional.ofNullable(edgeDescription.getBeginLabelDescription()).map(labelDescription -> {
            Optional<Label> optionalPreviousLabel = optionalPreviousEdge.map(Edge::getBeginLabel);
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, optionalPreviousLabel, LabelType.EDGE_BEGIN.getValue());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        Optional.ofNullable(edgeDescription.getCenterLabelDescription()).map(labelDescription -> {
            Optional<Label> optionalPreviousLabel = optionalPreviousEdge.map(Edge::getCenterLabel);
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, optionalPreviousLabel, LabelType.EDGE_CENTER.getValue());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        Optional.ofNullable(edgeDescription.getEndLabelDescription()).map(labelDescription -> {
            Optional<Label> optionalPreviousLabel = optionalPreviousEdge.map(Edge::getEndLabel);
            LabelComponentProps labelComponentProps = new LabelComponentProps(labelVariableManager, labelDescription, optionalPreviousLabel, LabelType.EDGE_END.getValue());
            return new Element(LabelComponent.class, labelComponentProps);
        }).ifPresent(edgeChildren::add);

        return edgeChildren;
    }

    private String computeEdgeId(EdgeDescription edgeDescription, Element sourceNode, Element targetNode, int count) {
        var descriptionId = edgeDescription.getId().toString();
        // @formatter:off
        var sourceId = Optional.of(sourceNode.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getId)
                .orElse(INVALID_NODE_ID);

        var targetId = Optional.of(targetNode.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getId)
                .orElse(INVALID_NODE_ID);
        // @formatter:on
        String rawIdentifier = descriptionId + ": " + sourceId + " --> " + targetId + " - " + count; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
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

    private String getId(Element nodeElement) {
        // @formatter:off
        return Optional.of(nodeElement.getProps())
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast)
                .map(NodeElementProps::getId)
                .orElse(UUID.randomUUID().toString());
        // @formatter:on
    }

}
