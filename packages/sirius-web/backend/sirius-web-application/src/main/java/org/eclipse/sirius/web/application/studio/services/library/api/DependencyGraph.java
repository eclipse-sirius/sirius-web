/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.studio.services.library.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Graph used to store and manipulate dependencies.
 *
 * @param <T> the type of the nodes in the graph
 *
 * @author gdaniel
 */
public class DependencyGraph<T> {

    private final Logger logger = LoggerFactory.getLogger(DependencyGraph.class);

    private final Set<T> nodes = new LinkedHashSet<>();

    private final List<Edge<T>> edges = new ArrayList<>();

    public void addNode(T node) {
        Objects.requireNonNull(node);
        this.nodes.add(node);
    }

    public void removeNode(T node) {
        Objects.requireNonNull(node);
        if (this.nodes.remove(node)) {
            this.edges.removeIf(edge -> Objects.equals(edge.source(), node) || Objects.equals(edge.target(), node));
        }
    }

    public void addEdge(T source, T target) {
        this.addNode(source);
        this.addNode(target);
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        if (this.edges.stream().noneMatch(edge -> Objects.equals(edge.source(), source) && Objects.equals(edge.target(), target))) {
            this.edges.add(new Edge<>(source, target));
        }
    }

    public List<T> getDependencies(T source) {
        return this.getOutgoingEdges(source).stream().map(Edge::target).toList();
    }

    public List<T> computeTopologicalOrdering() {
        List<T> result = new ArrayList<>();
        List<Edge<T>> localEdges = new ArrayList<>(this.edges);
        List<T> nodesWithoutIncomingEdges = this.getRootNodes();
        while (!nodesWithoutIncomingEdges.isEmpty()) {
            T node = nodesWithoutIncomingEdges.remove(0);
            result.add(node);
            for (Edge<T> outgoingEdge : this.getOutgoingEdges(node)) {
                localEdges.remove(outgoingEdge);
                if (localEdges.stream().noneMatch(e -> Objects.equals(e.target(), outgoingEdge.target()))) {
                    nodesWithoutIncomingEdges.add(outgoingEdge.target());
                }
            }
        }
        if (!localEdges.isEmpty()) {
            this.logger.warn("Cannot compute dependency ordering: there is at least one cycle in the dependency graph");
            result = List.of();
        } else {
            // Reverse the list to ensure dependencies are before elements that depend on them.
            Collections.reverse(result);
        }
        return result;
    }

    public Set<List<T>> getComponents() {
        Set<List<T>> result = new LinkedHashSet<>();
        List<T> localNodes = new ArrayList<>(this.nodes);

        while (!localNodes.isEmpty()) {
            T node = localNodes.remove(0);
            result.add(this.findComponentNodes(node, localNodes));
        }
        return result;
    }

    public List<T> findComponentNodes(T startNode, List<T> graphNodes) {
        List<T> componentNodes = new ArrayList<>();
        List<T> connectedNodes = Stream.concat(this.getIncomingEdges(startNode).stream().map(Edge::source), this.getOutgoingEdges(startNode).stream().map(Edge::target)).toList();
        for (T connectedNode : connectedNodes) {
            if (graphNodes.contains(connectedNode)) {
                graphNodes.remove(connectedNode);
                componentNodes.add(connectedNode);
                componentNodes.addAll(this.findComponentNodes(connectedNode, graphNodes));
            }
        }
        return componentNodes;
    }

    private List<T> getRootNodes() {
        List<T> result = new ArrayList<>();
        for (T node : this.nodes) {
            if (this.getIncomingEdges(node).isEmpty()) {
                result.add(node);
            }
        }
        return result;
    }

    private List<Edge<T>> getIncomingEdges(T target) {
        return this.edges.stream().filter(e -> Objects.equals(e.target(), target)).toList();
    }

    private List<Edge<T>> getOutgoingEdges(T source) {
        return this.edges.stream().filter(e -> Objects.equals(e.source(), source)).toList();
    }

}
