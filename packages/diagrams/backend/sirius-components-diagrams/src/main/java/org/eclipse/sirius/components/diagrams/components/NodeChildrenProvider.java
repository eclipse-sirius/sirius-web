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

import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.description.OutsideLabelDescription;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Provide the children element of node.
 *
 * @author frouene
 */
public class NodeChildrenProvider {

    private final NodeComponentProps props;

    public NodeChildrenProvider(NodeComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    public List<Element> getNodeChildrenElements(VariableManager nodeVariableManager, Optional<Node> optionalPreviousNode, NodeDescription nodeDescription,
            String nodeId, ViewModifier state, ViewModifier parentState, INodeDescriptionRequestor nodeDescriptionRequestor) {
        List<Element> nodeChildren = new ArrayList<>();

        nodeChildren.addAll(this.getInsideLabel(nodeVariableManager, optionalPreviousNode, nodeDescription, nodeId));
        nodeChildren.addAll(this.getOutsideLabel(nodeVariableManager, nodeDescription, nodeId));
        nodeChildren.addAll(this.getBorderNodes(optionalPreviousNode, nodeVariableManager, nodeId, state, nodeDescriptionRequestor));
        nodeChildren.addAll(this.getChildNodes(optionalPreviousNode, nodeVariableManager, nodeId, parentState, nodeDescriptionRequestor));

        return nodeChildren;
    }


    private List<Element> getInsideLabel(VariableManager nodeVariableManager, Optional<Node> optionalPreviousNode, NodeDescription nodeDescription, String nodeId) {
        List<Element> nodeChildren = new ArrayList<>();
        InsideLabelDescription labelDescription = nodeDescription.getInsideLabelDescription();
        if (labelDescription != null) {
            nodeVariableManager.put(InsideLabelDescription.OWNER_ID, nodeId);

            Optional<InsideLabel> optionalPreviousInsideLabel = optionalPreviousNode.map(Node::getInsideLabel);
            InsideLabelComponentProps insideLabelComponentProps = new InsideLabelComponentProps(nodeVariableManager, labelDescription, optionalPreviousInsideLabel);
            Element insideLabelElement = new Element(InsideLabelComponent.class, insideLabelComponentProps);
            nodeChildren.add(insideLabelElement);
        }
        return nodeChildren;
    }

    private List<Element> getOutsideLabel(VariableManager nodeVariableManager, NodeDescription nodeDescription, String nodeId) {

        return nodeDescription.getOutsideLabelDescriptions().stream().map(outsideLabelDescription -> {
            nodeVariableManager.put(OutsideLabelDescription.OWNER_ID, nodeId);

            OutsideLabelComponentProps outsideLabelComponentProps = new OutsideLabelComponentProps(nodeVariableManager, outsideLabelDescription);
            return new Element(OutsideLabelComponent.class, outsideLabelComponentProps);
        }).toList();

    }

    private List<Element> getBorderNodes(Optional<Node> optionalPreviousNode, VariableManager nodeVariableManager, String nodeId, ViewModifier state,
            INodeDescriptionRequestor nodeDescriptionRequestor) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        DiagramRenderingCache cache = this.props.getCache();

        var borderNodeDescriptions = new ArrayList<>(nodeDescription.getBorderNodeDescriptions());
        nodeDescription.getReusedBorderNodeDescriptionIds().stream()
                .map(nodeDescriptionRequestor::findById)
                .flatMap(Optional::stream)
                .forEach(borderNodeDescriptions::add);

        return borderNodeDescriptions.stream().map(borderNodeDescription -> {
            List<Node> previousBorderNodes = optionalPreviousNode.map(previousNode -> new DiagramElementRequestor().getBorderNodes(previousNode, borderNodeDescription))
                    .orElse(List.of());
            List<String> previousBorderNodesTargetObjectIds = previousBorderNodes.stream().map(Node::getTargetObjectId).toList();
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
                    .operationValidator(this.props.getOperationValidator())
                    .build();
            return new Element(NodeComponent.class, nodeComponentProps);
        }).toList();
    }

    private List<Element> getChildNodes(Optional<Node> optionalPreviousNode, VariableManager nodeVariableManager, String nodeId, ViewModifier state,
            INodeDescriptionRequestor nodeDescriptionRequestor) {
        NodeDescription nodeDescription = this.props.getNodeDescription();
        DiagramRenderingCache cache = this.props.getCache();

        var childNodeDescriptions = new ArrayList<>(nodeDescription.getChildNodeDescriptions());
        nodeDescription.getReusedChildNodeDescriptionIds().stream()
                .map(nodeDescriptionRequestor::findById)
                .flatMap(Optional::stream)
                .forEach(childNodeDescriptions::add);

        return childNodeDescriptions.stream().map(childNodeDescription -> {
            List<Node> previousChildNodes = optionalPreviousNode.map(previousNode -> new DiagramElementRequestor().getChildNodes(previousNode, childNodeDescription))
                    .orElse(List.of());
            List<String> previousChildNodesTargetObjectIds = previousChildNodes.stream().map(Node::getTargetObjectId).toList();
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
                    .operationValidator(this.props.getOperationValidator())
                    .build();

            return new Element(NodeComponent.class, nodeComponentProps);
        }).toList();
    }
}
