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
package org.eclipse.sirius.web.diagrams;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.representations.IRepresentation;

/**
 * Root concept of the diagram representation.
 *
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class Diagram implements IRepresentation {

    public static final String KIND = "Diagram"; //$NON-NLS-1$

    private String id;

    private String targetObjectId;

    private Position position;

    private Size size;

    private List<Node> nodes;

    private List<Edge> edges;

    private Diagram() {
        // Prevent instantiation
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getId() {
        return this.id;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @GraphQLField
    @GraphQLNonNull
    public Position getPosition() {
        return this.position;
    }

    @GraphQLField
    @GraphQLNonNull
    public Size getSize() {
        return this.size;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull Node> getNodes() {
        return this.nodes;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<@GraphQLNonNull Edge> getEdges() {
        return this.edges;
    }

    public static Builder newDiagram(String id) {
        return new Builder(id);
    }

    public static Builder newDiagram(Diagram diagram) {
        return new Builder(diagram);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, nodeCount: {3}, edgeCount: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.nodes.size(), this.edges.size());
    }

    /**
     * The builder used to create a diagram.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String targetObjectId;

        private Position position;

        private Size size;

        private List<Node> nodes;

        private List<Edge> edges;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Diagram diagram) {
            this.id = diagram.getId();
            this.targetObjectId = diagram.getTargetObjectId();
            this.position = diagram.getPosition();
            this.size = diagram.getSize();
            this.nodes = diagram.getNodes();
            this.edges = diagram.getEdges();
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder position(Position position) {
            this.position = Objects.requireNonNull(position);
            return this;
        }

        public Builder size(Size size) {
            this.size = Objects.requireNonNull(size);
            return this;
        }

        public Builder nodes(List<Node> nodes) {
            this.nodes = Objects.requireNonNull(nodes);
            return this;
        }

        public Builder edges(List<Edge> edges) {
            this.edges = Objects.requireNonNull(edges);
            return this;
        }

        public Diagram build() {
            Diagram diagram = new Diagram();
            diagram.id = Objects.requireNonNull(this.id);
            diagram.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            diagram.position = Objects.requireNonNull(this.position);
            diagram.size = Objects.requireNonNull(this.size);
            diagram.nodes = Objects.requireNonNull(this.nodes);
            diagram.edges = Objects.requireNonNull(this.edges);
            return diagram;
        }
    }
}
