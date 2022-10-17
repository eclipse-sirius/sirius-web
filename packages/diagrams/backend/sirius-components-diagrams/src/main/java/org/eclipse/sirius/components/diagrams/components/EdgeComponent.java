/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

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
import org.eclipse.sirius.components.diagrams.elements.EdgeElementProps.Builder;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.events.RemoveEdgeEvent;
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
        DiagramRenderingCache cache = this.props.getCache();
        Optional<IDiagramEvent> optionalDiagramEvent = this.props.getDiagramEvent();

        List<Element> children = new ArrayList<>();

        // @formatter:off
        boolean hasCandidates = this.hasNodeCandidates(edgeDescription.getSourceNodeDescriptions(), cache)
                             && this.hasNodeCandidates(edgeDescription.getTargetNodeDescriptions(), cache);
        // @formatter:on

        if (hasCandidates) {
            VariableManager semanticElementsVariableManager = new VariableManager();
            variableManager.getVariables().forEach(semanticElementsVariableManager::put);
            semanticElementsVariableManager.put(DiagramDescription.CACHE, cache);

            Map<String, Integer> edgeIdPrefixToCount = new HashMap<>();
            List<String> lastPreviousRenderedEdgeIds = new ArrayList<>();
            List<?> semanticElements = edgeDescription.getSemanticElementsProvider().apply(semanticElementsVariableManager);
            for (Object semanticElement : semanticElements) {
                List<Element> edgesToRender = this.renderEdge(variableManager, edgeDescription, optionalDiagramEvent, edgeIdPrefixToCount, lastPreviousRenderedEdgeIds, semanticElement);
                children.addAll(edgesToRender);
            }
        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private List<Element> renderEdge(VariableManager variableManager, EdgeDescription edgeDescription, Optional<IDiagramEvent> optionalDiagramEvent, Map<String, Integer> edgeIdPrefixToCount,
            List<String> lastPreviousRenderedEdgeIds, Object semanticElement) {
        List<Element> edgeElements = new ArrayList<>();
        DiagramRenderingCache cache = this.props.getCache();

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
                    String edgeIdPrefix = this.computeEdgeIdPrefix(edgeDescription, sourceNode, targetNode);
                    int count = edgeIdPrefixToCount.getOrDefault(edgeIdPrefix, 0);

                    Function<Integer, String> edgeIdProvider = this.getEdgeIdProvider(edgeDescription, sourceNode, targetNode);
                    String id = edgeIdProvider.apply(count);
                    String sourceId = this.getId(sourceNode);
                    String targetId = this.getId(targetNode);

                    Optional<Edge> optionalPreviousEdge = this.props.getEdgesRequestor().getById(id);
                    Builder edgeElementPropsBuilder = EdgeElementProps.newEdgeElementProps(id);

                    if (optionalDiagramEvent.isPresent() && optionalDiagramEvent.get() instanceof RemoveEdgeEvent) {
                        RemoveEdgeEvent removeEdgeEvent = (RemoveEdgeEvent) optionalDiagramEvent.get();
                        optionalPreviousEdge = this.getPreviousEdge(id, lastPreviousRenderedEdgeIds, removeEdgeEvent, edgeIdProvider, count);
                        Ratio sourceAnchorRelativePosition = optionalPreviousEdge.map(Edge::getSourceAnchorRelativePosition).orElse(Ratio.UNDEFINED);
                        Ratio targetAnchorRelativePosition = optionalPreviousEdge.map(Edge::getTargetAnchorRelativePosition).orElse(Ratio.UNDEFINED);
                        edgeElementPropsBuilder.sourceAnchorRelativePosition(sourceAnchorRelativePosition);
                        edgeElementPropsBuilder.targetAnchorRelativePosition(targetAnchorRelativePosition);
                    } else if (optionalDiagramEvent.isPresent() && optionalDiagramEvent.get() instanceof ReconnectEdgeEvent) {
                        ReconnectEdgeEvent reconnectEdgeEvent = (ReconnectEdgeEvent) optionalDiagramEvent.get();
                        optionalPreviousEdge = this.getPreviousEdge(id, lastPreviousRenderedEdgeIds, reconnectEdgeEvent, edgeIdProvider, count, optionalPreviousEdge, edgeElementPropsBuilder);
                    } else {
                        Ratio sourceAnchorRelativePosition = optionalPreviousEdge.map(Edge::getSourceAnchorRelativePosition).orElse(Ratio.UNDEFINED);
                        Ratio targetAnchorRelativePosition = optionalPreviousEdge.map(Edge::getTargetAnchorRelativePosition).orElse(Ratio.UNDEFINED);
                        edgeElementPropsBuilder.sourceAnchorRelativePosition(sourceAnchorRelativePosition);
                        edgeElementPropsBuilder.targetAnchorRelativePosition(targetAnchorRelativePosition);
                    }

                    var edgeInstanceVariableManager = edgeVariableManager.createChild();
                    edgeInstanceVariableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, cache.getNodeToObject().get(sourceNode));
                    edgeInstanceVariableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, cache.getNodeToObject().get(targetNode));

                    SynchronizationPolicy synchronizationPolicy = edgeDescription.getSynchronizationPolicy();
                    boolean shouldRender = synchronizationPolicy == SynchronizationPolicy.SYNCHRONIZED
                            || (synchronizationPolicy == SynchronizationPolicy.UNSYNCHRONIZED && optionalPreviousEdge.isPresent());

                    if (shouldRender) {
                        EdgeStyle style = edgeDescription.getStyleProvider().apply(edgeInstanceVariableManager);

                        // @formatter:off
                        String edgeType = optionalPreviousEdge
                                .map(Edge::getType)
                                .orElse("edge:straight"); //$NON-NLS-1$

                        List<Position> routingPoints = optionalPreviousEdge.map(Edge::getRoutingPoints).orElse(List.of());
                        List<Element> labelChildren = this.getLabelsChildren(edgeDescription, edgeInstanceVariableManager, optionalPreviousEdge, id, routingPoints);
                        EdgeElementProps edgeElementProps = edgeElementPropsBuilder
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
                        edgeElements.add(edgeElement);
                        edgeIdPrefixToCount.put(edgeIdPrefix, ++count);
                        if (optionalPreviousEdge.isPresent()) {
                            lastPreviousRenderedEdgeIds.add(optionalPreviousEdge.get().getId());
                        }
                    }
                }
            }
        }
        return edgeElements;
    }

    /**
     * Returns the previous edge identified by the edge id, resets the anchor position of the edge end being
     * reconnected. Since a reconnected edge has changed its source or target, its id has also changed. The edge id of
     * the reconnect edge event should be updated accordingly.
     *
     * @param edgeId
     *            The id of the edge being rendered
     * @param lastPreviousRenderedEdgeIds
     *            The list of id of last previous rendered edges
     * @param reconnectEdgeEvent
     *            the reconnect edge event
     * @param edgeIdProvider
     *            the function used to compute the edge id by apply a count.
     * @param count
     *            The count used to compute the id of the edge being rendered
     * @param potentialPreviousEdge
     *            The potential previous edge that could exist for the edge being rendered
     * @param edgeElementPropsBuilder
     *            the edge element props build used to set the edge end anchor position according to the previous edge
     * @return The optional previous edge
     */
    private Optional<Edge> getPreviousEdge(String edgeId, List<String> lastPreviousRenderedEdgeIds, ReconnectEdgeEvent reconnectEdgeEvent, Function<Integer, String> edgeIdProvider, int count,
            Optional<Edge> potentialPreviousEdge, EdgeElementProps.Builder edgeElementPropsBuilder) {

        Optional<Edge> optionalPreviousEdge = potentialPreviousEdge;
        Ratio sourceAnchorRelativePosition = optionalPreviousEdge.map(Edge::getSourceAnchorRelativePosition).orElse(Ratio.UNDEFINED);
        Ratio targetAnchorRelativePosition = optionalPreviousEdge.map(Edge::getTargetAnchorRelativePosition).orElse(Ratio.UNDEFINED);

        if (edgeId.equals(reconnectEdgeEvent.getEdgeId()) || lastPreviousRenderedEdgeIds.contains(edgeId)) {
            // The edge being rendered has been reconnected or has already been rendered. Thus, the
            // previous edge correspond to next sibling (count + 1).
            String potentialPreviousEdgeId = edgeIdProvider.apply(count + 1);
            optionalPreviousEdge = this.props.getEdgesRequestor().getById(potentialPreviousEdgeId);
            sourceAnchorRelativePosition = optionalPreviousEdge.map(Edge::getSourceAnchorRelativePosition).orElse(Ratio.UNDEFINED);
            targetAnchorRelativePosition = optionalPreviousEdge.map(Edge::getTargetAnchorRelativePosition).orElse(Ratio.UNDEFINED);
        }

        if (optionalPreviousEdge.isEmpty()) {
            // We are creating an edge but since we are handling a reconnect edge event, we are
            // currently rendering the reconnected edge. Thus, we need to find the previous edge which
            // is the edgeId of the reconnect event.

            String potentialPreviousEdgeId = reconnectEdgeEvent.getEdgeId();
            optionalPreviousEdge = this.props.getEdgesRequestor().getById(potentialPreviousEdgeId);
            sourceAnchorRelativePosition = optionalPreviousEdge.map(Edge::getSourceAnchorRelativePosition).orElse(Ratio.UNDEFINED);
            targetAnchorRelativePosition = optionalPreviousEdge.map(Edge::getTargetAnchorRelativePosition).orElse(Ratio.UNDEFINED);

            // We have the previous edge, but whether the reconnection concern the edge source or the
            // edge target we must reset the anchor to makes it computed by the layout service.
            if (ReconnectEdgeKind.SOURCE.equals(reconnectEdgeEvent.getKind())) {
                // We are reconnecting the source of the edge, thus, the source anchor should be reset
                sourceAnchorRelativePosition = Ratio.UNDEFINED;
            }

            if (ReconnectEdgeKind.TARGET.equals(reconnectEdgeEvent.getKind())) {
                // We are reconnecting the source of the edge, thus, the target anchor should be reset
                targetAnchorRelativePosition = Ratio.UNDEFINED;
            }
            // Since the id of the reconnected edge has been updated, update the reconnected edge event edge id for next
            // refresh operations.
            reconnectEdgeEvent.setEdgeId(edgeId);
        }

        edgeElementPropsBuilder.sourceAnchorRelativePosition(sourceAnchorRelativePosition);
        edgeElementPropsBuilder.targetAnchorRelativePosition(targetAnchorRelativePosition);
        return optionalPreviousEdge;
    }

    private Function<Integer, String> getEdgeIdProvider(EdgeDescription edgeDescription, Element sourceNode, Element targetNode) {
        return (count) -> {
            return this.computeEdgeId(edgeDescription, sourceNode, targetNode, count);
        };
    }

    /**
     * Returns the previous edge identified by the given edge id.
     *
     * If the edge being rendered is referenced by the remove edge event or is present in the list of already rendered
     * edges, then, the previous edge will be more likely the previous edge of the next sibling. The potential previous
     * edge is the first edge with an id not referenced by the remove edge event nor contained in the list of already
     * rendered edges.
     *
     * @param edgeId
     *            The id of the edge being rendered
     * @param lastPreviousRenderedEdgeIds
     *            The list of id of last previous rendered edges
     * @param removeEdgeEvent
     *            The remove edge event used to check if the edge being rendered does not exist anymore
     * @param edgeIdProvider
     *            The function used to compute the id by applying a count
     * @param baseCount
     *            The count
     * @return The optional previous edge.
     */
    private Optional<Edge> getPreviousEdge(String edgeId, List<String> lastPreviousRenderedEdgeIds, RemoveEdgeEvent removeEdgeEvent, Function<Integer, String> edgeIdProvider, int baseCount) {
        String potentialPreviousEdgeId = edgeId;
        int count = baseCount;
        boolean foundPotentialPrevious = false;
        boolean hasBeenRemoved = false;
        boolean hasBeenRendered = false;

        while (!foundPotentialPrevious) {
            hasBeenRemoved = false;
            hasBeenRendered = false;
            if (removeEdgeEvent.getEdgeIds().contains(potentialPreviousEdgeId)) {
                count++;
                potentialPreviousEdgeId = edgeIdProvider.apply(count);
                hasBeenRemoved = true;
            }

            if (lastPreviousRenderedEdgeIds.contains(potentialPreviousEdgeId)) {
                count++;
                potentialPreviousEdgeId = edgeIdProvider.apply(count);
                hasBeenRendered = true;
            }
            foundPotentialPrevious = !hasBeenRemoved && !hasBeenRendered;
        }

        return this.props.getEdgesRequestor().getById(potentialPreviousEdgeId);
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
        return this.computeEdgeId(descriptionId, sourceId, targetId, count);
    }

    private String computeEdgeId(String edgeDescriptionId, String sourceId, String targetId, int count) {
        String rawIdentifier = edgeDescriptionId + ": " + sourceId + " --> " + targetId + " - " + count; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
    }

    private String computeEdgeIdPrefix(EdgeDescription edgeDescription, Element sourceNode, Element targetNode) {
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
        String rawPrefix = descriptionId + sourceId + targetId;
        return UUID.nameUUIDFromBytes(rawPrefix.getBytes()).toString();
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
