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

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Used to navigate from a node.
 *
 * @author gdaniel
 */
public class NodeNavigator {

    private final Node node;

    private final NavigatorCache cache;


    public NodeNavigator(Node node, NavigatorCache cache) {
        this.node = node;
        this.cache = cache;
    }

    public Node getNode() {
        return this.node;
    }

    public NodeNavigator childNodeWithLabel(String label) {
        return this.findNodeMatching(this.node.getChildNodes(), n -> n.getInsideLabel().getText().equals(label))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No child node found with label \"{0}\"", label)));
    }

    public NodeNavigator childNodeWithTargetObjectLabel(String targetObjectLabel) {
        return this.findNodeMatching(this.node.getChildNodes(), n -> n.getTargetObjectLabel().equals(targetObjectLabel))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No child node found with target object label \"{0}\"", targetObjectLabel)));
    }

    public NodeNavigator childNodeWithTargetObjectId(String targetObjectId) {
        return this.findNodeMatching(this.node.getChildNodes(), n -> n.getTargetObjectId().equals(targetObjectId))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No child node found with target object id \"{0}\"", targetObjectId)));
    }

    public NodeNavigator childNodeWithNodeDescriptionId(String nodeDescriptionId) {
        return this.findNodeMatching(this.node.getChildNodes(), n -> n.getDescriptionId().equals(nodeDescriptionId))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No child node found with description id \"{0}\"", nodeDescriptionId)));
    }

    public NodeNavigator borderNodeWithLabel(String label) {
        return this.findNodeMatching(this.node.getBorderNodes(), n -> n.getInsideLabel().getText().equals(label))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No border node found with label \"{0}\"", label)));
    }

    public NodeNavigator borderNodeWithTargetObjectLabel(String targetObjectLabel) {
        return this.findNodeMatching(this.node.getBorderNodes(), n -> n.getTargetObjectLabel().equals(targetObjectLabel))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No border node found with target object label \"{0}\"", targetObjectLabel)));
    }

    public NodeNavigator borderNodeWithTargetObjectId(String targetObjectId) {
        return this.findNodeMatching(this.node.getBorderNodes(), n -> n.getTargetObjectId().equals(targetObjectId))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No border node found with target object id \"{0}\"", targetObjectId)));

    }

    public NodeNavigator borderNodeWithNodeDescriptionId(String nodeDescriptionId) {
        return this.findNodeMatching(this.node.getBorderNodes(), n -> n.getDescriptionId().equals(nodeDescriptionId))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No border node found with description id \"{0}\"", nodeDescriptionId)));
    }

    public NodeNavigator parentNode() {
        List<Node> ancestors = this.cache.getNodeIdToAncestors().get(this.node.getId());
        if (ancestors == null || ancestors.isEmpty()) {
            throw new IllegalArgumentException("No parent found");
        } else {
            return new NodeNavigator(ancestors.get(ancestors.size() - 1), this.cache);
        }
    }


    public EdgeNavigator edgeWithLabel(String label) {
        return this.findConnectedEdgeMatching(e -> e.getCenterLabel().getText().equals(label))
            .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No edge found with label \"{0}\"", label)));
    }

    public EdgeNavigator edgeWithTargetObjectLabel(String targetObjectLabel) {
        return this.findConnectedEdgeMatching(e -> e.getTargetObjectLabel().equals(targetObjectLabel))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No edge found with target object label \"{0}\"", targetObjectLabel)));
    }

    public EdgeNavigator edgeWithTargetObjectId(String targetObjectId) {
        return this.findConnectedEdgeMatching(e -> e.getTargetObjectId().equals(targetObjectId))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No edge found with target object id \"{0}\"", targetObjectId)));
    }

    public EdgeNavigator edgeWithEdgeDescriptionId(String edgeDescriptionId) {
        return this.findConnectedEdgeMatching(e -> e.getDescriptionId().equals(edgeDescriptionId))
                .orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("No edge found with description id \"{0}\"", edgeDescriptionId)));
    }

    public List<Node> findAncestorNodes() {
        return this.cache.getNodeIdToAncestors().computeIfAbsent(this.node.getId(), k -> List.of());
    }

    public InsideLabel findLabel() {
        return this.node.getInsideLabel();
    }

    private Optional<NodeNavigator> findNodeMatching(List<Node> nodes, Predicate<Node> predicate) {
        return nodes.stream()
            .filter(predicate)
            .findFirst()
            .map(n -> new NodeNavigator(n, this.cache));
    }

    private Optional<EdgeNavigator> findConnectedEdgeMatching(Predicate<Edge> predicate) {
        List<Edge> edgesConnectedToNode = this.cache.getNodeIdToEdges().computeIfAbsent(this.node.getId(), k -> List.of());
        return edgesConnectedToNode.stream()
                .filter(predicate)
                .findFirst()
                .map(e -> new EdgeNavigator(e, this.cache));
    }

}
