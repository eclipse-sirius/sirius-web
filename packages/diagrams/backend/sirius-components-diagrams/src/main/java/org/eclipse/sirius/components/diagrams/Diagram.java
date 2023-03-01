/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.ISemanticRepresentation;

/**
 * Root concept of the diagram representation.
 *
 * @author sbegaudeau
 */
@Immutable
public final class Diagram implements IRepresentation, ISemanticRepresentation {
    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Diagram";

    private String id;

    private String kind;

    private String targetObjectId;

    private String descriptionId;

    private String label;

    private Position position;

    private Size size;

    private List<Node> nodes;

    private List<Edge> edges;

    private DiagramLayoutData layoutData;

    private Diagram() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public Position getPosition() {
        return this.position;
    }

    public Size getSize() {
        return this.size;
    }

    public List<Node> getNodes() {
        return this.nodes;
    }

    public List<Edge> getEdges() {
        return this.edges;
    }

    public DiagramLayoutData getLayoutData() {
        return this.layoutData;
    }

    public static Builder newDiagram(String id) {
        return new Builder(id);
    }

    public static Builder newDiagram(Diagram diagram) {
        return new Builder(diagram);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, descriptionId: {3}, label: {4}, nodeCount: {5}, edgeCount: {6}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.descriptionId, this.label, this.nodes.size(), this.edges.size());
    }

    /**
     * The builder used to create a diagram.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind = KIND;

        private String targetObjectId;

        private String descriptionId;

        private String label;

        private Position position;

        private Size size;

        private List<Node> nodes;

        private List<Edge> edges;

        private DiagramLayoutData layoutData = new DiagramLayoutData(Map.of(), Map.of(), Map.of());

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Diagram diagram) {
            this.id = diagram.getId();
            this.targetObjectId = diagram.getTargetObjectId();
            this.descriptionId = diagram.getDescriptionId();
            this.label = diagram.getLabel();
            this.position = diagram.getPosition();
            this.size = diagram.getSize();
            this.nodes = diagram.getNodes();
            this.edges = diagram.getEdges();
            this.layoutData = diagram.getLayoutData();
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
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

        public Builder layoutData(DiagramLayoutData layoutData) {
            this.layoutData = Objects.requireNonNull(layoutData);
            return this;
        }

        public Diagram build() {
            Diagram diagram = new Diagram();
            diagram.id = Objects.requireNonNull(this.id);
            diagram.kind = Objects.requireNonNull(this.kind);
            diagram.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            diagram.descriptionId = Objects.requireNonNull(this.descriptionId);
            diagram.label = Objects.requireNonNull(this.label);
            diagram.position = Objects.requireNonNull(this.position);
            diagram.size = Objects.requireNonNull(this.size);
            diagram.nodes = Objects.requireNonNull(this.nodes);
            diagram.edges = Objects.requireNonNull(this.edges);
            diagram.layoutData = Objects.requireNonNull(this.layoutData);
            return diagram;
        }
    }
}
