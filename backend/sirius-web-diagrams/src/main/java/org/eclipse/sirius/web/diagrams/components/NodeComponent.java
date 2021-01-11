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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.Fragment;
import org.eclipse.sirius.web.components.FragmentProps;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.description.LabelDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The component used to render a node.
 *
 * @author sbegaudeau
 */
public class NodeComponent implements IComponent {

    private NodeComponentProps props;

    public NodeComponent(NodeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        NodeDescription nodeDescription = this.props.getNodeDescription();
        INodesRequestor nodesRequestor = this.props.getNodesRequestor();
        DiagramRenderingCache cache = this.props.getCache();

        List<Element> children = new ArrayList<>();
        List<Object> semanticElements = nodeDescription.getSemanticElementsProvider().apply(variableManager);

        for (Object semanticElement : semanticElements) {
            VariableManager nodeVariableManager = variableManager.createChild();
            nodeVariableManager.put(VariableManager.SELF, semanticElement);

            String targetObjectId = nodeDescription.getTargetObjectIdProvider().apply(nodeVariableManager);
            var optionalPreviousNode = nodesRequestor.getByTargetObjectId(targetObjectId);

            if (this.shouldRender(targetObjectId, optionalPreviousNode)) {
                Element nodeElement = this.doRender(nodeVariableManager, targetObjectId, optionalPreviousNode);
                children.add(nodeElement);

                cache.put(nodeDescription.getId(), nodeElement);
                cache.put(semanticElement, nodeElement);
            }

        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private boolean shouldRender(String targetObjectId, Optional<Node> optionalPreviousNode) {
        boolean shouldRender = false;
        NodeDescription nodeDescription = this.props.getNodeDescription();
        SynchronizationPolicy synchronizationPolicy = nodeDescription.getSynchronizationPolicy();
        if (synchronizationPolicy == SynchronizationPolicy.SYNCHRONIZED) {
            shouldRender = true;
        } else if (synchronizationPolicy == SynchronizationPolicy.UNSYNCHRONIZED) {
            shouldRender = optionalPreviousNode.isPresent() || this.existsViewCreationRequested(targetObjectId);
        }
        return shouldRender;
    }

    private boolean existsViewCreationRequested(String targetObjectId) {
        UUID parentElementId = this.props.getParentElementId();
        UUID nodeDescriptionId = this.props.getNodeDescription().getId();
        // @formatter:off
        return this.props.getViewCreationRequests().stream()
                .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getDescriptionId(), nodeDescriptionId))
                .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getTargetObjectId(), targetObjectId))
                .anyMatch(viewCreationRequest -> Objects.equals(viewCreationRequest.getParentElementId(), parentElementId));
        // @formatter:on
    }

    private Element doRender(VariableManager nodeVariableManager, String targetObjectId, Optional<Node> optionalPreviousNode) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();
        DiagramRenderingCache cache = this.props.getCache();
        NodePositionProvider nodePositionProvider = this.props.getNodePositionProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider();

        UUID nodeId = optionalPreviousNode.map(Node::getId).orElseGet(() -> this.computeNodeId(targetObjectId));
        Optional<Label> optionalPreviousLabel = optionalPreviousNode.map(Node::getLabel);
        String type = nodeDescription.getTypeProvider().apply(nodeVariableManager);
        String targetObjectKind = nodeDescription.getTargetObjectKindProvider().apply(nodeVariableManager);
        String targetObjectLabel = nodeDescription.getTargetObjectLabelProvider().apply(nodeVariableManager);

        IDiagramElementRequestor diagramElementRequestor = new DiagramElementRequestor();
        INodeStyle style = nodeDescription.getStyleProvider().apply(nodeVariableManager);

        Position position = nodePositionProvider.getPosition(nodeId, optionalPreviousNode, this.props.getPreviousParentElement(), nodeSizeProvider, style);

        //@formatter:off
        Position absolutePosition = this.computeAbsolutePosition(position, this.props.getParentAbsolutePosition());

        var borderNodes = nodeDescription.getBorderNodeDescriptions().stream().map(borderNodeDescription -> {
            List<Node> previousBorderNodes = optionalPreviousNode.map(previousNode -> diagramElementRequestor.getBorderNodes(previousNode, borderNodeDescription)).orElse(List.of());
            INodesRequestor borderNodesRequestor = new NodesRequestor(previousBorderNodes);
                    var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                            .variableManager(nodeVariableManager)
                            .nodeDescription(borderNodeDescription)
                            .nodesRequestor(borderNodesRequestor)
                            .containmentKind(NodeContainmentKind.BORDER_NODE)
                            .cache(cache)
                            .viewCreationRequests(this.props.getViewCreationRequests())
                            .parentElementId(nodeId)
                            .nodePositionProvider(nodePositionProvider)
                            .previousParentElement(optionalPreviousNode.map(Object.class::cast))
                            .parentAbsolutePosition(absolutePosition)
                            .build();
                    return new Element(NodeComponent.class, nodeComponentProps);
                    })
                .collect(Collectors.toList());
        var childNodes = nodeDescription.getChildNodeDescriptions()
                .stream()
                .map(childNodeDescription -> {
            List<Node> previousChildNodes = optionalPreviousNode
                    .map(previousNode -> diagramElementRequestor.getChildNodes(previousNode, childNodeDescription))
                    .orElse(List.of());
            INodesRequestor childNodesRequestor = new NodesRequestor(previousChildNodes);
            var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                    .variableManager(nodeVariableManager)
                    .nodeDescription(childNodeDescription)
                    .nodesRequestor(childNodesRequestor)
                    .containmentKind(NodeContainmentKind.CHILD_NODE)
                    .cache(cache)
                    .viewCreationRequests(this.props.getViewCreationRequests())
                    .parentElementId(nodeId)
                    .nodePositionProvider(nodePositionProvider)
                    .previousParentElement(optionalPreviousNode.map(Object.class::cast))
                    .parentAbsolutePosition(absolutePosition)
                    .build();
            return new Element(NodeComponent.class, nodeComponentProps);
        })
                .collect(Collectors.toList());
        //@formatter:on

        Size size = optionalPreviousNode.map(Node::getSize).orElseGet(() -> nodeSizeProvider.getSize(style, childNodes));
        NodeLabelPositionProvider labelBoundsProvider = new NodeLabelPositionProvider(type, size);
        LabelDescription labelDescription = nodeDescription.getLabelDescription();
        nodeVariableManager.put(LabelDescription.OWNER_ID, nodeId);
        LabelComponentProps labelComponentProps = new LabelComponentProps(nodeVariableManager, labelDescription, optionalPreviousLabel, labelBoundsProvider, LabelType.INSIDE_CENTER.getValue());
        Element labelElement = new Element(LabelComponent.class, labelComponentProps);
        List<Element> nodeChildren = new ArrayList<>();
        nodeChildren.add(labelElement);
        nodeChildren.addAll(borderNodes);
        nodeChildren.addAll(childNodes);
        // @formatter:off
        NodeElementProps nodeElementProps = NodeElementProps.newNodeElementProps(nodeId)
                .type(type)
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .targetObjectLabel(targetObjectLabel)
                .descriptionId(nodeDescription.getId())
                .borderNode(containmentKind == NodeContainmentKind.BORDER_NODE)
                .style(style)
                .position(position)
                .size(size)
                .children(nodeChildren)
                .absolutePosition(absolutePosition)
                .build();
        // @formatter:on
        return new Element(NodeElementProps.TYPE, nodeElementProps);
    }

    private UUID computeNodeId(String targetObjectId) {
        UUID parentElementId = this.props.getParentElementId();
        NodeDescription nodeDescription = this.props.getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();

        String rawIdentifier = parentElementId.toString() + containmentKind.toString() + nodeDescription.getId().toString() + targetObjectId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes());
    }

    private Position computeAbsolutePosition(Position currentNodeRelativePosition, Position parentNodeAbsolutePosition) {
        // @formatter:off
        return Position.newPosition()
                .x(parentNodeAbsolutePosition.getX() + currentNodeRelativePosition.getX())
                .y(parentNodeAbsolutePosition.getY() + currentNodeRelativePosition.getY())
                .build();
        // @formatter:on
    }

}
