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
package org.eclipse.sirius.components.diagrams.tests.navigation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Caches diagram navigation information.
 *
 * @author gdaniel
 */
public class NavigatorCache {

    private Map<String, Node> idToNode = new HashMap<>();

    private Map<String, List<Node>> labelToNodes = new HashMap<>();

    private Map<String, List<Node>> targetObjectLabelToNodes = new HashMap<>();

    private Map<String, List<Edge>> targetObjectLabelToEdges = new HashMap<>();

    private Map<String, List<Node>> targetObjectIdToNodes = new HashMap<>();

    private Map<String, List<Edge>> targetObjectIdToEdges = new HashMap<>();

    private Map<String, List<Edge>> labelToEdges = new HashMap<>();

    private Map<String, List<Node>> nodeIdToAncestors = new HashMap<>();

    private Map<String, List<Node>> nodeDescriptionIdToNodes = new HashMap<>();

    private Map<String, List<Edge>> edgeDescriptionIdToEdges = new HashMap<>();

    private Map<String, List<Edge>> nodeIdToEdges = new HashMap<>();

    private int diagramNodeCount;

    private int diagramEdgeCount;

    public NavigatorCache(Diagram diagram) {
        this.cacheDiagram(diagram);
    }

    public Map<String, Node> getIdToNode() {
        return this.idToNode;
    }

    public Map<String, List<Node>> getLabelToNodes() {
        return this.labelToNodes;
    }

    public Map<String, List<Node>> getTargetObjectLabelToNodes() {
        return this.targetObjectLabelToNodes;
    }

    public Map<String, List<Node>> getTargetObjectIdToNodes() {
        return this.targetObjectIdToNodes;
    }

    public Map<String, List<Edge>> getTargetObjectLabelToEdges() {
        return this.targetObjectLabelToEdges;
    }

    public Map<String, List<Edge>> getTargetObjectIdToEdges() {
        return this.targetObjectIdToEdges;
    }

    public Map<String, List<Edge>> getLabelToEdges() {
        return this.labelToEdges;
    }

    public Map<String, List<Node>> getNodeIdToAncestors() {
        return this.nodeIdToAncestors;
    }

    public Map<String, List<Node>> getNodeDescriptionIdToNodes() {
        return this.nodeDescriptionIdToNodes;
    }

    public Map<String, List<Edge>> getEdgeDescriptionIdToEdges() {
        return this.edgeDescriptionIdToEdges;
    }

    public Map<String, List<Edge>> getNodeIdToEdges() {
        return this.nodeIdToEdges;
    }

    public int getDiagramNodeCount() {
        return this.diagramNodeCount;
    }

    public int getDiagramEdgeCount() {
        return this.diagramEdgeCount;
    }

    private void cacheDiagram(Diagram diagram) {
        diagram.getNodes().stream()
            .forEach(node -> this.cacheNode(node, List.of()));
        diagram.getEdges().stream()
            .forEach(edge -> this.cacheEdge(edge));
    }

    private void cacheNode(Node node, List<Node> ancestors) {
        this.diagramNodeCount++;
        this.idToNode.put(node.getId(), node);
        List<Node> nodesWithDescription = this.nodeDescriptionIdToNodes.computeIfAbsent(node.getDescriptionId(), k -> new ArrayList<>());
        nodesWithDescription.add(node);
        if (node.getInsideLabel() != null) {
            List<Node> nodesWithLabel = this.labelToNodes.computeIfAbsent(node.getInsideLabel().getText(), k -> new ArrayList<>());
            nodesWithLabel.add(node);
        }
        node.getOutsideLabels().forEach(outsideLabel -> {
            List<Node> nodesWithLabel = this.labelToNodes.computeIfAbsent(outsideLabel.text(), k -> new ArrayList<>());
            nodesWithLabel.add(node);
        });
        List<Node> nodesWithTargetObjectLabel = this.targetObjectLabelToNodes.computeIfAbsent(node.getTargetObjectLabel(), k -> new ArrayList<>());
        nodesWithTargetObjectLabel.add(node);
        List<Node> nodesWithTargetObjectId = this.targetObjectIdToNodes.computeIfAbsent(node.getTargetObjectId(), k -> new ArrayList<>());
        nodesWithTargetObjectId.add(node);

        this.nodeIdToAncestors.put(node.getId(), ancestors);

        List<Node> childAncestors = new ArrayList<>(ancestors);
        childAncestors.add(node);
        Stream.concat(node.getChildNodes().stream(), node.getBorderNodes().stream())
            .forEach(childNode -> this.cacheNode(childNode, childAncestors));
    }

    private void cacheEdge(Edge edge) {
        this.diagramEdgeCount++;
        List<Edge> edgesWithDescription = this.edgeDescriptionIdToEdges.computeIfAbsent(edge.getDescriptionId(), k -> new ArrayList<>());
        edgesWithDescription.add(edge);
        List<Edge> edgesConnectedToSourceNode = this.nodeIdToEdges.computeIfAbsent(edge.getSourceId(), k -> new ArrayList<>());
        edgesConnectedToSourceNode.add(edge);
        List<Edge> edgesConnectedToTargetNode = this.nodeIdToEdges.computeIfAbsent(edge.getTargetId(), k -> new ArrayList<>());
        edgesConnectedToTargetNode.add(edge);
        List<Edge> edgesWithTargetObjectLabel = this.targetObjectLabelToEdges.computeIfAbsent(edge.getTargetObjectLabel(), k -> new ArrayList<>());
        edgesWithTargetObjectLabel.add(edge);
        List<Edge> edgesWithTargetObjectId = this.targetObjectIdToEdges.computeIfAbsent(edge.getTargetObjectId(), k -> new ArrayList<>());
        edgesWithTargetObjectId.add(edge);
        if (edge.getCenterLabel() != null) {
            List<Edge> edgesWithLabel = this.labelToEdges.computeIfAbsent(edge.getCenterLabel().getText(), k -> new ArrayList<>());
            edgesWithLabel.add(edge);
        }
    }

}
