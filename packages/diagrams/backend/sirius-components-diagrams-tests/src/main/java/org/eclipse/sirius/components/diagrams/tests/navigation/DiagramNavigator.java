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
import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Used to navigate from a diagram.
 *
 * @author gdaniel
 */
public class DiagramNavigator {

    private final NavigatorCache cache;

    public DiagramNavigator(Diagram diagram) {
        this.cache = new NavigatorCache(Objects.requireNonNull(diagram));
    }

    public NodeNavigator nodeWithLabel(String label) {
        List<Node> nodes = this.cache.getLabelToNodes().get(label);
        if (nodes == null || nodes.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No node found with label \"{0}\"", label));
        }
        return new NodeNavigator(nodes.get(0), this.cache);
    }

    public NodeNavigator nodeWithId(String id) {
        Node node = this.cache.getIdToNode().get(id);
        if (node == null) {
            throw new IllegalArgumentException(MessageFormat.format("No node found with id \"{0}\"", id));
        }
        return new NodeNavigator(node, this.cache);
    }

    public NodeNavigator nodeWithTargetObjectLabel(String targetObjectLabel) {
        List<Node> nodes = this.cache.getTargetObjectLabelToNodes().get(targetObjectLabel);
        if (nodes == null || nodes.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No node found with target object label \"{0}\"", targetObjectLabel));
        }
        return new NodeNavigator(nodes.get(0), this.cache);
    }

    public NodeNavigator nodeWithTargetObjectId(String targetObjectId) {
        List<Node> nodes = this.cache.getTargetObjectIdToNodes().get(targetObjectId);
        if (nodes == null || nodes.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No node found with target object id \"{0}\"", targetObjectId));
        }
        return new NodeNavigator(nodes.get(0), this.cache);
    }

    public NodeNavigator nodeWithNodeDescriptionId(String nodeDescriptionId) {
        List<Node> nodes = this.cache.getNodeDescriptionIdToNodes().get(nodeDescriptionId);
        if (nodes == null || nodes.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No node found with description id \"{0}\"", nodeDescriptionId));
        }
        return new NodeNavigator(nodes.get(0), this.cache);
    }

    public EdgeNavigator edgeWithLabel(String label) {
        List<Edge> edges = this.cache.getLabelToEdges().get(label);
        if (edges == null || edges.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No edge found with label \"{0}\"", label));
        }
        return new EdgeNavigator(edges.get(0), this.cache);
    }

    public EdgeNavigator edgeWithTargetObjectLabel(String targetObjectLabel) {
        List<Edge> edges = this.cache.getTargetObjectLabelToEdges().get(targetObjectLabel);
        if (edges == null || edges.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No edge found with target object label \"{0}\"", targetObjectLabel));
        }
        return new EdgeNavigator(edges.get(0), this.cache);
    }

    public EdgeNavigator edgeWithTargetObjectId(String targetObjectId) {
        List<Edge> edges = this.cache.getTargetObjectIdToEdges().get(targetObjectId);
        if (edges == null || edges.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No edge found with target object id \"{0}\"", targetObjectId));
        }
        return new EdgeNavigator(edges.get(0), this.cache);
    }

    public EdgeNavigator edgeWithEdgeDescriptionId(String edgeDescriptionId) {
        List<Edge> edges = this.cache.getEdgeDescriptionIdToEdges().get(edgeDescriptionId);
        if (edges == null || edges.isEmpty()) {
            throw new IllegalArgumentException(MessageFormat.format("No edge found with description id \"{0}\"", edgeDescriptionId));
        }
        return new EdgeNavigator(edges.get(0), this.cache);
    }

    public int findDiagramNodeCount() {
        return this.cache.getDiagramNodeCount();
    }

    public int findDiagramEdgeCount() {
        return this.cache.getDiagramEdgeCount();
    }
}
