/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.CustomizableProperties;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.SynchronizationPolicy;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps.Builder;
import org.eclipse.sirius.components.diagrams.events.FadeDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.PinDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.ResetViewModifiersEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.UpdateCollapsingStateEvent;
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

    public static final String COLLAPSING_STATE = "collapsingState";

    public static final String IS_BORDER_NODE = "isBorderNode";

    public static final String SEMANTIC_ELEMENT_IDS = "semanticElementIds";

    private final NodeComponentProps props;

    public NodeComponent(NodeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        NodeDescription nodeDescription = this.props.getNodeDescription();
        INodesRequestor nodesRequestor = this.props.getNodesRequestor();
        DiagramRenderingCache cache = this.props.getCache();
        List<IDiagramEvent> diagramEvents = this.props.getDiagramEvents();

        VariableManager nodeComponentVariableManager = variableManager.createChild();

        if (nodeDescription.getSynchronizationPolicy().equals(SynchronizationPolicy.UNSYNCHRONIZED)) {
            List<String> creationRequestsIds = this.props.getViewCreationRequests().stream()
                    .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getDescriptionId(), nodeDescription.getId()))
                    .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getParentElementId(), this.props.getParentElementId()))
                    .map(ViewCreationRequest::getTargetObjectId)
                    .toList();
            List<String> previousNodeIds = this.props.getPreviousTargetObjectIds();
            Set<String> semanticElementIds = new HashSet<>(creationRequestsIds);
            semanticElementIds.addAll(previousNodeIds);
            nodeComponentVariableManager.put(SEMANTIC_ELEMENT_IDS, semanticElementIds.stream().toList());
        }

        List<Element> children = new ArrayList<>();
        this.props.getOperationValidator().validate("Node#semanticCandidates", nodeComponentVariableManager.getVariables());
        List<?> semanticElements = nodeDescription.getSemanticElementsProvider().apply(nodeComponentVariableManager);

        for (Object semanticElement : semanticElements) {
            VariableManager nodeVariableManager = variableManager.createChild();
            nodeVariableManager.put(VariableManager.SELF, semanticElement);

            String targetObjectId = nodeDescription.getTargetObjectIdProvider().apply(nodeVariableManager);
            var optionalPreviousNode = nodesRequestor.getByTargetObjectId(targetObjectId);

            if (this.shouldRender(targetObjectId, optionalPreviousNode, nodeVariableManager)) {
                Element nodeElement = this.doRender(nodeVariableManager, targetObjectId, optionalPreviousNode, diagramEvents);
                children.add(nodeElement);

                cache.put(nodeDescription.getId(), nodeElement);
                cache.put(semanticElement, nodeElement);
                cache.put(nodeElement, this.props.getParentElementId());
            }

        }

        FragmentProps fragmentProps = new FragmentProps(children);
        return new Fragment(fragmentProps);
    }

    private boolean shouldRender(String targetObjectId, Optional<Node> optionalPreviousNode, VariableManager variableManager) {
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

        return shouldRender && nodeDescription.getShouldRenderPredicate().test(variableManager);
    }

    private boolean existsViewCreationRequested(String targetObjectId) {
        String parentElementId = this.props.getParentElementId();
        String nodeDescriptionId = this.props.getNodeDescription().getId();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();
        return this.props.getViewCreationRequests().stream()
                .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getDescriptionId(), nodeDescriptionId))
                .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getTargetObjectId(), targetObjectId))
                .filter(viewCreationRequest -> Objects.equals(viewCreationRequest.getContainmentKind(), containmentKind))
                .anyMatch(viewCreationRequest -> Objects.equals(viewCreationRequest.getParentElementId(), parentElementId));
    }

    private boolean existsViewDeletionRequested(String elementId) {
        return this.props.getViewDeletionRequests().stream()
                .anyMatch(viewDeletionRequest -> Objects.equals(viewDeletionRequest.getElementId(), elementId));
    }

    private Element doRender(VariableManager nodeVariableManager, String targetObjectId, Optional<Node> optionalPreviousNode, List<IDiagramEvent> diagramEvents) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();
        boolean isBorderNode = containmentKind == NodeContainmentKind.BORDER_NODE;

        String nodeId = optionalPreviousNode.map(Node::getId).orElseGet(() -> this.computeNodeId(targetObjectId));

        Set<ViewModifier> defaultModifiers = this.computeDefaultModifiers(nodeDescription, nodeVariableManager);
        Set<ViewModifier> modifiers = this.computeModifiers(diagramEvents, optionalPreviousNode, nodeId, defaultModifiers);
        ViewModifier state = this.computeState(modifiers);

        boolean isPinned = this.isPinned(diagramEvents, nodeId, optionalPreviousNode);
        boolean isCollapsedByDefault = nodeDescription.getIsCollapsedByDefaultPredicate().test(nodeVariableManager);
        CollapsingState collapsingState = this.computeCollapsingState(nodeId, optionalPreviousNode, diagramEvents, isCollapsedByDefault);

        nodeVariableManager.put(NodeComponent.COLLAPSING_STATE, collapsingState);
        nodeVariableManager.put(NodeComponent.IS_BORDER_NODE, isBorderNode);

        String type = nodeDescription.getTypeProvider().apply(nodeVariableManager);
        String targetObjectKind = nodeDescription.getTargetObjectKindProvider().apply(nodeVariableManager);
        String targetObjectLabel = nodeDescription.getTargetObjectLabelProvider().apply(nodeVariableManager);

        INodeStyle style = nodeDescription.getStyleProvider().apply(nodeVariableManager);

        ILayoutStrategy layoutStrategy = nodeDescription.getChildrenLayoutStrategyProvider().apply(nodeVariableManager);


        var parentState = state;
        if (collapsingState == CollapsingState.COLLAPSED) {
            parentState = ViewModifier.Hidden;
        }

        NodeChildrenComponentProps nodeChildrenComponentProps = NodeChildrenComponentProps.newNodeChildrenComponentProps()
                .nodeComponentProps(this.props)
                .variableManager(nodeVariableManager)
                .parentState(parentState)
                .state(state)
                .build();
        Element nodeChildren = new Element(NodeChildrenComponent.class, nodeChildrenComponentProps);

        Position position = optionalPreviousNode.map(Node::getPosition)
                .orElse(Position.UNDEFINED);

        Set<CustomizableProperties> customizableProperties = Set.of();

        Size size = this.getSize(optionalPreviousNode, nodeDescription, nodeVariableManager);
        Optional<Size> newSize = this.getNodeSizeFromEvent(this.props.getDiagramEvents(), nodeId);
        if (newSize.isPresent()) {
            size = newSize.get();
        }

        if (CollapsingState.EXPANDED.equals(collapsingState)) {
            customizableProperties = optionalPreviousNode.map(Node::getCustomizedProperties).orElse(Set.of());
        }

        if (newSize.isPresent()) {
            var newProperties = new LinkedHashSet<>(customizableProperties);
            newProperties.add(CustomizableProperties.Size);
            customizableProperties = newProperties;
        }

        Integer defaultWidth = nodeDescription.getDefaultWidthProvider().apply(nodeVariableManager);
        Integer defaultHeight = nodeDescription.getDefaultHeightProvider().apply(nodeVariableManager);

        Builder nodeElementPropsBuilder = NodeElementProps.newNodeElementProps(nodeId)
                .type(type)
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .targetObjectLabel(targetObjectLabel)
                .descriptionId(nodeDescription.getId())
                .borderNode(isBorderNode)
                .style(style)
                .position(position)
                .size(size)
                .children(List.of(nodeChildren))
                .customizableProperties(customizableProperties)
                .modifiers(modifiers)
                .state(state)
                .pinned(isPinned)
                .collapsingState(collapsingState)
                .defaultWidth(defaultWidth)
                .defaultHeight(defaultHeight)
                .labelEditable(nodeDescription.getLabelEditHandler() != null);

        if (layoutStrategy != null) {
            nodeElementPropsBuilder.childrenLayoutStrategy(layoutStrategy);
        }

        return new Element(NodeElementProps.TYPE, nodeElementPropsBuilder.build());
    }

    private CollapsingState computeCollapsingState(String nodeId, Optional<Node> optionalPreviousNode, List<IDiagramEvent> diagramEvents, boolean isCollapsedByDefault) {
        CollapsingState newCollapsingState = CollapsingState.EXPANDED;

        if (optionalPreviousNode.isPresent()) {
            Node previousNode = optionalPreviousNode.get();
            newCollapsingState = previousNode.getCollapsingState();
        } else if (isCollapsedByDefault) {
            newCollapsingState = CollapsingState.COLLAPSED;
        }

        for (IDiagramEvent diagramEvent : diagramEvents) {
            if (diagramEvent instanceof UpdateCollapsingStateEvent updateCollapsingStateEvent) {
                if (updateCollapsingStateEvent.diagramElementId().equals(nodeId)) {
                    newCollapsingState = updateCollapsingStateEvent.collapsingState();
                }
            }
        }

        return newCollapsingState;
    }

    private Set<ViewModifier> computeDefaultModifiers(NodeDescription nodeDescription, VariableManager nodeVariableManager) {
        Set<ViewModifier> viewModifiers = new HashSet<>();
        if (nodeDescription.getIsHiddenByDefaultPredicate().test(nodeVariableManager)) {
            viewModifiers.add(ViewModifier.Hidden);
        }
        if (nodeDescription.getIsFadedByDefaultPredicate().test(nodeVariableManager)) {
            viewModifiers.add(ViewModifier.Faded);
        }
        return viewModifiers;
    }

    /**
     * Compute the modifiers set applied on the new node. The set is by default the set of the previous node or is empty
     * if it does not exist.
     * <p>
     * If a diagram event is specified and this one requests a modification of the modifier set, applied the event on
     * the default set.
     *
     * @param diagramEvents
     *         The diagram events modifying the default modifier set of the node
     * @param optionalPreviousNode
     *         The previous node from which get the old modifier set. If empty, the old modifier set is set to an
     *         empty Set
     * @param id
     *         The ID of the current node
     * @param defaultModifiers
     *         The default modifiers to use if it is the first render of the node
     */
    private Set<ViewModifier> computeModifiers(List<IDiagramEvent> diagramEvents, Optional<Node> optionalPreviousNode, String id, Set<ViewModifier> defaultModifiers) {
        Set<ViewModifier> modifiers = new HashSet<>(optionalPreviousNode.map(Node::getModifiers).orElse(defaultModifiers));
        for (IDiagramEvent diagramEvent : diagramEvents) {
            if (diagramEvent instanceof HideDiagramElementEvent hideDiagramElementEvent) {
                if (hideDiagramElementEvent.getElementIds().contains(id)) {
                    if (hideDiagramElementEvent.hideElement()) {
                        modifiers.add(ViewModifier.Hidden);
                    } else {
                        modifiers.remove(ViewModifier.Hidden);
                    }
                }
            } else if (diagramEvent instanceof FadeDiagramElementEvent fadeDiagramElementEvent) {
                if (fadeDiagramElementEvent.getElementIds().contains(id)) {
                    if (fadeDiagramElementEvent.fadeElement()) {
                        modifiers.add(ViewModifier.Faded);
                    } else {
                        modifiers.remove(ViewModifier.Faded);
                    }
                }
            } else if (diagramEvent instanceof ResetViewModifiersEvent resetViewModifiersEvent) {
                if (resetViewModifiersEvent.getElementIds().contains(id)) {
                    modifiers.clear();
                    modifiers.addAll(defaultModifiers);
                }
            }
        }
        return modifiers;
    }

    private boolean isPinned(List<IDiagramEvent> diagramEvents, String nodeId, Optional<Node> optionalPreviousNode) {
        boolean isPinned = optionalPreviousNode.map(Node::isPinned).orElse(false);
        for (IDiagramEvent diagramEvent : diagramEvents) {
            if (diagramEvent instanceof PinDiagramElementEvent pinDiagramElementEvent) {
                if (pinDiagramElementEvent.elementIds().contains(nodeId)) {
                    isPinned = pinDiagramElementEvent.pinned();
                }
            }
        }
        return isPinned;
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
     *         The previous node if this node existed during a previous rendering
     * @param nodeDescription
     *         The description of the node
     * @param nodeVariableManager
     *         The variable manager of the node
     * @return The size of the node
     */
    private Size getSize(Optional<Node> optionalPreviousNode, NodeDescription nodeDescription, VariableManager nodeVariableManager) {
        Size size;
        boolean customizedSize = optionalPreviousNode.map(Node::getCustomizedProperties)
                .filter(set -> set.contains(CustomizableProperties.Size))
                .isPresent();
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

    private Optional<Size> getNodeSizeFromEvent(List<IDiagramEvent> diagramEvents, String nodeId) {
        Optional<Size> size = Optional.empty();
        for (IDiagramEvent diagramEvent : diagramEvents) {
            if (diagramEvent instanceof ResizeEvent resizeEvent) {
                if (resizeEvent.nodeId().equals(nodeId)) {
                    size = Optional.ofNullable(resizeEvent.newSize());
                }
            }
        }
        return size;
    }


    private String computeNodeId(String targetObjectId) {
        String parentElementId = this.props.getParentElementId();
        NodeDescription nodeDescription = this.props.getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getContainmentKind();
        return new NodeIdProvider().getNodeId(parentElementId, nodeDescription.getId(), containmentKind, targetObjectId);
    }

}
