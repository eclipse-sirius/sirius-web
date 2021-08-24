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
package org.eclipse.sirius.web.diagrams.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.Fragment;
import org.eclipse.sirius.web.components.FragmentProps;
import org.eclipse.sirius.web.components.IComponent;
import org.eclipse.sirius.web.diagrams.CustomizableProperties;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.NodeType;
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
            if (optionalPreviousNode.isPresent()) {
                Node previousNode = optionalPreviousNode.get();
                shouldRender = !this.existsViewDeletionRequested(previousNode.getId());
            } else {
                shouldRender = this.existsViewCreationRequested(targetObjectId);
            }
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

    private boolean existsViewDeletionRequested(UUID elementId) {
        // @formatter:off
        return this.props.getViewDeletionRequests().stream()
                .anyMatch(viewDeletionRequest -> Objects.equals(viewDeletionRequest.getElementId(), elementId));
        // @formatter:on
    }

    private Element doRender(VariableManager nodeVariableManager, String targetObjectId, Optional<Node> optionalPreviousNode) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();

        UUID nodeId = optionalPreviousNode.map(Node::getId).orElseGet(() -> this.computeNodeId(targetObjectId));
        Optional<Label> optionalPreviousLabel = optionalPreviousNode.map(Node::getLabel);
        String type = nodeDescription.getTypeProvider().apply(nodeVariableManager);
        String targetObjectKind = nodeDescription.getTargetObjectKindProvider().apply(nodeVariableManager);
        String targetObjectLabel = nodeDescription.getTargetObjectLabelProvider().apply(nodeVariableManager);

        INodeStyle style = nodeDescription.getStyleProvider().apply(nodeVariableManager);

        var borderNodes = this.getBorderNodes(optionalPreviousNode, nodeVariableManager, nodeId);
        var childNodes = this.getChildNodes(optionalPreviousNode, nodeVariableManager, nodeId);

        LabelDescription labelDescription = nodeDescription.getLabelDescription();
        nodeVariableManager.put(LabelDescription.OWNER_ID, nodeId);

        LabelType nodeLabelType = LabelType.INSIDE_CENTER;
        if (NodeType.NODE_IMAGE.equals(type)) {
            nodeLabelType = LabelType.OUTSIDE_CENTER;
        }

        LabelComponentProps labelComponentProps = new LabelComponentProps(nodeVariableManager, labelDescription, optionalPreviousLabel, nodeLabelType.getValue());
        Element labelElement = new Element(LabelComponent.class, labelComponentProps);

        List<Element> nodeChildren = new ArrayList<>();
        nodeChildren.add(labelElement);
        nodeChildren.addAll(borderNodes);
        nodeChildren.addAll(childNodes);
        // @formatter:off

        Position position = optionalPreviousNode.map(Node::getPosition)
                .orElse(Position.UNDEFINED);

        Size size = this.getSize(optionalPreviousNode, nodeDescription, nodeVariableManager);
        Set<CustomizableProperties> customizableProperties = optionalPreviousNode.map(Node::getCustomizedProperties).orElse(Set.of());

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
                .customizableProperties(customizableProperties)
                .build();
        // @formatter:on
        return new Element(NodeElementProps.TYPE, nodeElementProps);
    }

    /**
     * Computes the size of the node.
     *
     * <p>
     * Four different sizes can be returned by this function (by priority order):
     * </p>
     * <ul>
     * <li>The size of the previous node if it exists and if this size has been customized by a user (manual
     * resize)</li>
     * <li>The size computed by the description if it is valid (width > 0 and height > 0)</li>
     * <li>The size of the previous node if a previous node existed</li>
     * <li>The undefined size (width = -1, height = -1) if the node did not exist before and if we have no valid size
     * from the description</li>
     * </ul>
     *
     * @param optionalPreviousNode
     *            The previous node if this node existed during a previous rendering
     * @param nodeDescription
     *            The description of the node
     * @param nodeVariableManager
     *            The variable manager of the node
     * @return The size of the node
     */
    private Size getSize(Optional<Node> optionalPreviousNode, NodeDescription nodeDescription, VariableManager nodeVariableManager) {
        Size size;
        //@formatter:off
        boolean customizedSize = optionalPreviousNode.map(Node::getCustomizedProperties)
                .filter(set -> set.contains(CustomizableProperties.Size))
                .isPresent();
        //@formatter:on
        if (customizedSize) {
            size = optionalPreviousNode.map(Node::getSize).orElse(Size.UNDEFINED);
        } else {
            size = nodeDescription.getSizeProvider().apply(nodeVariableManager);
            if (size.getHeight() <= 0 || size.getWidth() <= 0) {
                size = optionalPreviousNode.map(Node::getSize).orElse(Size.UNDEFINED);
            }
        }
        return size;
    }

    private List<Element> getBorderNodes(Optional<Node> optionalPreviousNode, VariableManager nodeVariableManager, UUID nodeId) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        DiagramRenderingCache cache = this.props.getCache();

        return nodeDescription.getBorderNodeDescriptions().stream().map(borderNodeDescription -> {
            List<Node> previousBorderNodes = optionalPreviousNode.map(previousNode -> new DiagramElementRequestor().getBorderNodes(previousNode, borderNodeDescription)).orElse(List.of());
            INodesRequestor borderNodesRequestor = new NodesRequestor(previousBorderNodes);
            //@formatter:off
            var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                    .variableManager(nodeVariableManager)
                    .nodeDescription(borderNodeDescription)
                    .nodesRequestor(borderNodesRequestor)
                    .containmentKind(NodeContainmentKind.BORDER_NODE)
                    .cache(cache)
                    .viewCreationRequests(this.props.getViewCreationRequests())
                    .viewDeletionRequests(this.props.getViewDeletionRequests())
                    .parentElementId(nodeId)
                    .build();
            //@formatter:on
            return new Element(NodeComponent.class, nodeComponentProps);
        }).collect(Collectors.toList());
    }

    private List<Element> getChildNodes(Optional<Node> optionalPreviousNode, VariableManager nodeVariableManager, UUID nodeId) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        DiagramRenderingCache cache = this.props.getCache();

        //@formatter:off
        return nodeDescription.getChildNodeDescriptions().stream().map(childNodeDescription -> {
            List<Node> previousChildNodes = optionalPreviousNode.map(previousNode -> new DiagramElementRequestor().getChildNodes(previousNode, childNodeDescription)).orElse(List.of());
            INodesRequestor childNodesRequestor = new NodesRequestor(previousChildNodes);
            var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                    .variableManager(nodeVariableManager)
                    .nodeDescription(childNodeDescription)
                    .nodesRequestor(childNodesRequestor)
                    .containmentKind(NodeContainmentKind.CHILD_NODE)
                    .cache(cache)
                    .viewCreationRequests(this.props.getViewCreationRequests())
                    .viewDeletionRequests(this.props.getViewDeletionRequests())
                    .parentElementId(nodeId)
                    .build();

            // @formatter:on
            return new Element(NodeComponent.class, nodeComponentProps);
        }).collect(Collectors.toList());
    }

    private UUID computeNodeId(String targetObjectId) {
        UUID parentElementId = this.props.getParentElementId();
        NodeDescription nodeDescription = this.props.getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();

        String rawIdentifier = parentElementId.toString() + containmentKind.toString() + nodeDescription.getId().toString() + targetObjectId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes());
    }

}
