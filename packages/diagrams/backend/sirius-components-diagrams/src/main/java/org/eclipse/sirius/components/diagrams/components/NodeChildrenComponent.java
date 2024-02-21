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
package org.eclipse.sirius.components.diagrams.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.OutsideLabelDescription;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Fragment;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;

/**
 * The component used to render the node children.
 *
 * @author frouene
 */
public class NodeChildrenComponent implements IComponent {

    private final NodeChildrenComponentProps props;

    public NodeChildrenComponent(NodeChildrenComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        List<Element> nodeChildren = new ArrayList<>();

        String targetObjectId = this.props.getNodeComponentProps().getNodeDescription().getTargetObjectIdProvider().apply(this.props.getVariableManager());
        var optionalPreviousNode = this.props.getNodeComponentProps().getNodesRequestor().getByTargetObjectId(targetObjectId);
        String nodeId = optionalPreviousNode.map(Node::getId).orElseGet(() -> this.computeNodeId(targetObjectId));

        nodeChildren.addAll(this.getInsideLabel(nodeId));
        nodeChildren.addAll(this.getOutsideLabel(nodeId));
        nodeChildren.addAll(this.getBorderNodes(optionalPreviousNode, nodeId));
        nodeChildren.addAll(this.getChildNodes(optionalPreviousNode, nodeId));

        FragmentProps fragmentProps = new FragmentProps(nodeChildren);
        return new Fragment(fragmentProps);
    }

    private List<Element> getInsideLabel(String nodeId) {
        List<Element> nodeChildren = new ArrayList<>();
        InsideLabelDescription labelDescription = this.props.getNodeComponentProps().getNodeDescription().getInsideLabelDescription();
        if (labelDescription != null) {
            this.props.getVariableManager().put(InsideLabelDescription.OWNER_ID, nodeId);

            InsideLabelComponentProps insideLabelComponentProps = new InsideLabelComponentProps(this.props.getVariableManager(), labelDescription);
            Element insideLabelElement = new Element(InsideLabelComponent.class, insideLabelComponentProps);
            nodeChildren.add(insideLabelElement);
        }
        return nodeChildren;
    }

    private List<Element> getOutsideLabel(String nodeId) {

        return this.props.getNodeComponentProps().getNodeDescription().getOutsideLabelDescriptions().stream().map(outsideLabelDescription -> {
            this.props.getVariableManager().put(OutsideLabelDescription.OWNER_ID, nodeId);

            OutsideLabelComponentProps outsideLabelComponentProps = new OutsideLabelComponentProps(this.props.getVariableManager(), outsideLabelDescription);
            return new Element(OutsideLabelComponent.class, outsideLabelComponentProps);
        }).toList();

    }

    private List<Element> getBorderNodes(Optional<Node> optionalPreviousNode, String nodeId) {
        NodeDescription nodeDescription = this.props.getNodeComponentProps().getNodeDescription();
        DiagramRenderingCache cache = this.props.getNodeComponentProps().getCache();

        var borderNodeDescriptions = new ArrayList<>(nodeDescription.getBorderNodeDescriptions());
        nodeDescription.getReusedBorderNodeDescriptionIds().stream()
                .map(this.props.getNodeComponentProps().getNodeDescriptionRequestor()::findById)
                .flatMap(Optional::stream)
                .forEach(borderNodeDescriptions::add);

        return borderNodeDescriptions.stream().map(borderNodeDescription -> {
            List<Node> previousBorderNodes = optionalPreviousNode.map(previousNode -> new DiagramElementRequestor().getBorderNodes(previousNode, borderNodeDescription))
                    .orElse(List.of());
            List<String> previousBorderNodesTargetObjectIds = previousBorderNodes.stream().map(Node::getTargetObjectId).toList();
            INodesRequestor borderNodesRequestor = new NodesRequestor(previousBorderNodes);
            var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                    .variableManager(this.props.getVariableManager())
                    .nodeDescription(borderNodeDescription)
                    .nodesRequestor(borderNodesRequestor)
                    .nodeDescriptionRequestor(this.props.getNodeComponentProps().getNodeDescriptionRequestor())
                    .containmentKind(NodeContainmentKind.BORDER_NODE)
                    .cache(cache)
                    .viewCreationRequests(this.props.getNodeComponentProps().getViewCreationRequests())
                    .viewDeletionRequests(this.props.getNodeComponentProps().getViewDeletionRequests())
                    .parentElementId(nodeId)
                    .previousTargetObjectIds(previousBorderNodesTargetObjectIds)
                    .diagramEvents(this.props.getNodeComponentProps().getDiagramEvents())
                    .parentElementState(this.props.getState())
                    .operationValidator(this.props.getNodeComponentProps().getOperationValidator())
                    .build();
            return new Element(NodeComponent.class, nodeComponentProps);
        }).toList();
    }

    private List<Element> getChildNodes(Optional<Node> optionalPreviousNode, String nodeId) {
        NodeDescription nodeDescription = this.props.getNodeComponentProps().getNodeDescription();
        DiagramRenderingCache cache = this.props.getNodeComponentProps().getCache();

        var childNodeDescriptions = new ArrayList<>(nodeDescription.getChildNodeDescriptions());
        nodeDescription.getReusedChildNodeDescriptionIds().stream()
                .map(this.props.getNodeComponentProps().getNodeDescriptionRequestor()::findById)
                .flatMap(Optional::stream)
                .forEach(childNodeDescriptions::add);

        return childNodeDescriptions.stream().map(childNodeDescription -> {
            List<Node> previousChildNodes = optionalPreviousNode.map(previousNode -> new DiagramElementRequestor().getChildNodes(previousNode, childNodeDescription))
                    .orElse(List.of());
            List<String> previousChildNodesTargetObjectIds = previousChildNodes.stream().map(Node::getTargetObjectId).toList();
            INodesRequestor childNodesRequestor = new NodesRequestor(previousChildNodes);
            var nodeComponentProps = NodeComponentProps.newNodeComponentProps()
                    .variableManager(this.props.getVariableManager())
                    .nodeDescription(childNodeDescription)
                    .nodesRequestor(childNodesRequestor)
                    .nodeDescriptionRequestor(this.props.getNodeComponentProps().getNodeDescriptionRequestor())
                    .containmentKind(NodeContainmentKind.CHILD_NODE)
                    .cache(cache)
                    .viewCreationRequests(this.props.getNodeComponentProps().getViewCreationRequests())
                    .viewDeletionRequests(this.props.getNodeComponentProps().getViewDeletionRequests())
                    .parentElementId(nodeId)
                    .previousTargetObjectIds(previousChildNodesTargetObjectIds)
                    .diagramEvents(this.props.getNodeComponentProps().getDiagramEvents())
                    .parentElementState(this.props.getParentState())
                    .operationValidator(this.props.getNodeComponentProps().getOperationValidator())
                    .build();

            return new Element(NodeComponent.class, nodeComponentProps);
        }).toList();
    }

    private String computeNodeId(String targetObjectId) {
        String parentElementId = this.props.getNodeComponentProps().getParentElementId();
        NodeDescription nodeDescription = this.props.getNodeComponentProps().getNodeDescription();
        NodeContainmentKind containmentKind = this.props.getNodeComponentProps().getContainmentKind();
        return new NodeIdProvider().getNodeId(parentElementId, nodeDescription.getId(), containmentKind, targetObjectId);
    }
}
