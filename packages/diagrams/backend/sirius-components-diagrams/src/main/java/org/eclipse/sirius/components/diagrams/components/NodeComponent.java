/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo and others.
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.LabelDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps.Builder;
import org.eclipse.sirius.components.diagrams.events.FadeDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render a node.
 *
 * @author sbegaudeau
 */
public class NodeComponent implements IComponent {

    public static final String SEMANTIC_ELEMENT_IDS = "semanticElementIds";

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
        Optional<IDiagramEvent> optionalDiagramEvent = this.props.getDiagramEvent();

        VariableManager nodeComponentVariableManager = variableManager.createChild();

        if (nodeDescription.getSynchronizationPolicy().equals(SynchronizationPolicy.UNSYNCHRONIZED)) {
            //@formatter:off
            List<String> creationRequestsIds = this.props.getViewCreationRequests().stream()
                    .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getDescriptionId(), nodeDescription.getId()))
                    .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getParentElementId(), this.props.getParentElementId()))
                    .map(ViewCreationRequest::getTargetObjectId)
                    .toList();
            //@formatter:on
            List<String> previousNodeIds = this.props.getPreviousTargetObjectIds();
            List<String> semanticElementIds = new ArrayList<>(creationRequestsIds);
            semanticElementIds.addAll(previousNodeIds);
            nodeComponentVariableManager.put(SEMANTIC_ELEMENT_IDS, semanticElementIds);
        }

        List<Element> children = new ArrayList<>();
        List<?> semanticElements = nodeDescription.getSemanticElementsProvider().apply(nodeComponentVariableManager);

        for (Object semanticElement : semanticElements) {
            VariableManager nodeVariableManager = variableManager.createChild();
            nodeVariableManager.put(VariableManager.SELF, semanticElement);

            String targetObjectId = nodeDescription.getTargetObjectIdProvider().apply(nodeVariableManager);
            var optionalPreviousNode = nodesRequestor.getByTargetObjectId(targetObjectId);

            if (this.shouldRender(targetObjectId, optionalPreviousNode)) {
                Element nodeElement = this.doRender(nodeVariableManager, targetObjectId, optionalPreviousNode, optionalDiagramEvent);
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
        String parentElementId = this.props.getParentElementId();
        UUID nodeDescriptionId = this.props.getNodeDescription().getId();
        // @formatter:off
        return this.props.getViewCreationRequests().stream()
                .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getDescriptionId(), nodeDescriptionId))
                .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getTargetObjectId(), targetObjectId))
                .anyMatch(viewCreationRequest -> Objects.equals(viewCreationRequest.getParentElementId(), parentElementId));
        // @formatter:on
    }

    private boolean existsViewDeletionRequested(String elementId) {
        // @formatter:off
        return this.props.getViewDeletionRequests().stream()
                .anyMatch(viewDeletionRequest -> Objects.equals(viewDeletionRequest.getElementId(), elementId));
        // @formatter:on
    }

    private Element doRender(VariableManager nodeVariableManager, String targetObjectId, Optional<Node> optionalPreviousNode, Optional<IDiagramEvent> optionalDiagramEvent) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();
        INodeDescriptionRequestor nodeDescriptionRequestor = this.props.getNodeDescriptionRequestor();

        String nodeId = optionalPreviousNode.map(Node::getId).orElseGet(() -> this.computeNodeId(targetObjectId));
        Optional<Label> optionalPreviousLabel = optionalPreviousNode.map(Node::getLabel);
        String type = nodeDescription.getTypeProvider().apply(nodeVariableManager);
        String targetObjectKind = nodeDescription.getTargetObjectKindProvider().apply(nodeVariableManager);
        String targetObjectLabel = nodeDescription.getTargetObjectLabelProvider().apply(nodeVariableManager);
        Set<ViewModifier> modifiers = this.computeModifiers(optionalDiagramEvent, optionalPreviousNode, nodeId);
        ViewModifier state = this.computeState(modifiers);

        INodeStyle style = nodeDescription.getStyleProvider().apply(nodeVariableManager);

        ILayoutStrategy layoutStrategy = nodeDescription.getChildrenLayoutStrategyProvider().apply(nodeVariableManager);

        var borderNodes = this.getBorderNodes(optionalPreviousNode, nodeVariableManager, nodeId, state, nodeDescriptionRequestor);
        var childNodes = this.getChildNodes(optionalPreviousNode, nodeVariableManager, nodeId, state, nodeDescriptionRequestor);

        LabelDescription labelDescription = nodeDescription.getLabelDescription();
        nodeVariableManager.put(LabelDescription.OWNER_ID, nodeId);

        LabelType nodeLabelType = LabelType.INSIDE_CENTER;
        if (containmentKind == NodeContainmentKind.BORDER_NODE) {
            nodeLabelType = LabelType.OUTSIDE;
        } else if (NodeType.NODE_IMAGE.equals(type)) {
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

        Builder nodeElementPropsBuilder = NodeElementProps.newNodeElementProps(nodeId)
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
                .modifiers(modifiers)
                .state(state);

        if (layoutStrategy != null) {
            nodeElementPropsBuilder.childrenLayoutStrategy(layoutStrategy);
        }

        // @formatter:on
        return new Element(NodeElementProps.TYPE, nodeElementPropsBuilder.build());
    }

    /**
     * Compute the modifiers set applied on the new node. The set is by default the set of the previous node or is empty
     * if it does not exist.
     *
     * If a diagram event is specified and this one requests a modification of the modifier set, applied the event on
     * the default set.
     *
     * @param optionalDiagramEvent
     *            The optional diagram event modifying the default modifier set of the node
     * @param optionalPreviousNode
     *            The previous node from which get the old modifier set. If empty, the old modifier set is set to an
     *            empty Set
     * @param id
     *            The ID of the current node
     */
    private Set<ViewModifier> computeModifiers(Optional<IDiagramEvent> optionalDiagramEvent, Optional<Node> optionalPreviousNode, String id) {
        Set<ViewModifier> modifiers = new HashSet<>(optionalPreviousNode.map(Node::getModifiers).orElse(Set.of()));
        if (optionalDiagramEvent.isPresent() && optionalDiagramEvent.get() instanceof HideDiagramElementEvent hideDiagramElementEvent) {
            if (hideDiagramElementEvent.getElementIds().contains(id)) {
                if (hideDiagramElementEvent.hideElement()) {
                    modifiers.add(ViewModifier.Hidden);
                } else {
                    modifiers.remove(ViewModifier.Hidden);
                }
            }
        } else if (optionalDiagramEvent.isPresent() && optionalDiagramEvent.get() instanceof FadeDiagramElementEvent fadeDiagramElementEvent) {
            if (fadeDiagramElementEvent.getElementIds().contains(id)) {
                if (fadeDiagramElementEvent.fadeElement()) {
                    modifiers.add(ViewModifier.Faded);
                } else {
                    modifiers.remove(ViewModifier.Faded);
                }
            }
        }
        return modifiers;
    }

    private ViewModifier computeState(Set<ViewModifier> modifiers) {
        ViewModifier parentState = this.props.getParentElementState();
        ViewModifier state = new ViewStateProvider().getState(modifiers);
        if (parentState == ViewModifier.Hidden) {
            state = ViewModifier.Hidden;
        }
        return state;
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

    private List<Element> getBorderNodes(Optional<Node> optionalPreviousNode, VariableManager nodeVariableManager, String nodeId, ViewModifier state,
            INodeDescriptionRequestor nodeDescriptionRequestor) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        DiagramRenderingCache cache = this.props.getCache();

        //@formatter:off
        var borderNodeDescriptions = new ArrayList<>(nodeDescription.getBorderNodeDescriptions());
        nodeDescription.getReusedBorderNodeDescriptionIds().stream()
            .map(nodeDescriptionRequestor::findById)
            .flatMap(Optional::stream)
            .forEach(borderNodeDescriptions::add);

        return borderNodeDescriptions.stream().map(borderNodeDescription -> {
            List<Node> previousBorderNodes = optionalPreviousNode.map(previousNode -> new DiagramElementRequestor().getBorderNodes(previousNode, borderNodeDescription)).orElse(List.of());
            List<String> previousBorderNodesTargetObjectIds = previousBorderNodes.stream().map(node -> node.getTargetObjectId()).toList();
            INodesRequestor borderNodesRequestor = new NodesRequestor(previousBorderNodes);
            var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                    .variableManager(nodeVariableManager)
                    .nodeDescription(borderNodeDescription)
                    .nodesRequestor(borderNodesRequestor)
                    .nodeDescriptionRequestor(nodeDescriptionRequestor)
                    .containmentKind(NodeContainmentKind.BORDER_NODE)
                    .cache(cache)
                    .viewCreationRequests(this.props.getViewCreationRequests())
                    .viewDeletionRequests(this.props.getViewDeletionRequests())
                    .parentElementId(nodeId)
                    .previousTargetObjectIds(previousBorderNodesTargetObjectIds)
                    .diagramEvent(this.props.getDiagramEvent().orElse(null))
                    .parentElementState(state)
                    .build();
            return new Element(NodeComponent.class, nodeComponentProps);
        }).toList();
        //@formatter:on
    }

    private List<Element> getChildNodes(Optional<Node> optionalPreviousNode, VariableManager nodeVariableManager, String nodeId, ViewModifier state,
            INodeDescriptionRequestor nodeDescriptionRequestor) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        DiagramRenderingCache cache = this.props.getCache();

        //@formatter:off
        var childNodeDescriptions = new ArrayList<>(nodeDescription.getChildNodeDescriptions());
        nodeDescription.getReusedChildNodeDescriptionIds().stream()
            .map(nodeDescriptionRequestor::findById)
            .flatMap(Optional::stream)
            .forEach(childNodeDescriptions::add);

        return childNodeDescriptions.stream().map(childNodeDescription -> {
            List<Node> previousChildNodes = optionalPreviousNode.map(previousNode -> new DiagramElementRequestor().getChildNodes(previousNode, childNodeDescription)).orElse(List.of());
            List<String> previousChildNodesTargetObjectIds = previousChildNodes.stream().map(node -> node.getTargetObjectId()).toList();
            INodesRequestor childNodesRequestor = new NodesRequestor(previousChildNodes);
            var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                    .variableManager(nodeVariableManager)
                    .nodeDescription(childNodeDescription)
                    .nodesRequestor(childNodesRequestor)
                    .nodeDescriptionRequestor(nodeDescriptionRequestor)
                    .containmentKind(NodeContainmentKind.CHILD_NODE)
                    .cache(cache)
                    .viewCreationRequests(this.props.getViewCreationRequests())
                    .viewDeletionRequests(this.props.getViewDeletionRequests())
                    .parentElementId(nodeId)
                    .previousTargetObjectIds(previousChildNodesTargetObjectIds)
                    .diagramEvent(this.props.getDiagramEvent().orElse(null))
                    .parentElementState(state)
                    .build();

            return new Element(NodeComponent.class, nodeComponentProps);
        }).toList();
        // @formatter:on
    }

    private String computeNodeId(String targetObjectId) {
        String parentElementId = this.props.getParentElementId();
        NodeDescription nodeDescription = this.props.getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();
        String rawIdentifier = parentElementId + containmentKind.toString() + nodeDescription.getId().toString() + targetObjectId;
        return UUID.nameUUIDFromBytes(rawIdentifier.getBytes()).toString();
    }

}
