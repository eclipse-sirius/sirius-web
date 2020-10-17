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
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * An edge.
 *
 * @author hmarchadour
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class Edge {
    private String id;

    private String type;

    private String targetObjectId;

    private String targetObjectKind;

    private String targetObjectLabel;

    private UUID descriptionId;

    private Label beginLabel;

    private Label centerLabel;

    private Label endLabel;

    private String sourceId;

    private String targetId;

    private EdgeStyle style;

    private List<Position> routingPoints;

    private Edge() {
        // Prevent instantiation
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public String getId() {
        return this.id;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getType() {
        return this.type;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getTargetObjectLabel() {
        return this.targetObjectLabel;
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    @GraphQLField
    public Label getBeginLabel() {
        return this.beginLabel;
    }

    @GraphQLField
    public Label getCenterLabel() {
        return this.centerLabel;
    }

    @GraphQLField
    public Label getEndLabel() {
        return this.endLabel;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getSourceId() {
        return this.sourceId;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getTargetId() {
        return this.targetId;
    }

    @GraphQLField
    @GraphQLNonNull
    public EdgeStyle getStyle() {
        return this.style;
    }

    @GraphQLField
    @GraphQLNonNull
    public List<Position> getRoutingPoints() {
        return this.routingPoints;
    }

    public static Builder newEdge(String id) {
        return new Builder(id);
    }

    public static Builder newEdge(Edge edge) {
        return new Builder(edge);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, targetObjectKind: {3}, targetObjectLabel: {4}, descriptionId: {5}, beginLabel: {6}, centerLabel: {7}, endLabel: {8}, sourceId: {9}, targetId: {10}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.targetObjectKind, this.targetObjectLabel, this.descriptionId,
                this.beginLabel.getText(), this.centerLabel.getText(), this.endLabel.getText(), this.sourceId, this.targetId);
    }

    /**
     * The builder used to create an edge.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String type;

        private String targetObjectId;

        private String targetObjectKind;

        private String targetObjectLabel;

        private UUID descriptionId;

        private Label beginLabel;

        private Label centerLabel;

        private Label endLabel;

        private String sourceId;

        private String targetId;

        private EdgeStyle style;

        private List<Position> routingPoints;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Edge edge) {
            this.id = edge.getId();
            this.type = edge.getType();
            this.targetObjectId = edge.getTargetObjectId();
            this.targetObjectKind = edge.getTargetObjectKind();
            this.targetObjectLabel = edge.getTargetObjectLabel();
            this.descriptionId = edge.getDescriptionId();
            this.beginLabel = edge.getBeginLabel();
            this.centerLabel = edge.getCenterLabel();
            this.endLabel = edge.getEndLabel();
            this.sourceId = edge.getSourceId();
            this.targetId = edge.getTargetId();
            this.style = edge.getStyle();
            this.routingPoints = edge.getRoutingPoints();
        }

        public Builder type(String type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
            return this;
        }

        public Builder targetObjectLabel(String targetObjectLabel) {
            this.targetObjectLabel = Objects.requireNonNull(targetObjectLabel);
            return this;
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder beginLabel(Label beginLabel) {
            this.beginLabel = beginLabel;
            return this;
        }

        public Builder centerLabel(Label centerLabel) {
            this.centerLabel = centerLabel;
            return this;
        }

        public Builder endLabel(Label endLabel) {
            this.endLabel = endLabel;
            return this;
        }

        public Builder sourceId(String sourceId) {
            this.sourceId = Objects.requireNonNull(sourceId);
            return this;
        }

        public Builder targetId(String targetId) {
            this.targetId = Objects.requireNonNull(targetId);
            return this;
        }

        public Builder style(EdgeStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder routingPoints(List<Position> routingPoints) {
            this.routingPoints = Objects.requireNonNull(routingPoints);
            return this;
        }

        public Edge build() {
            Edge edge = new Edge();
            edge.id = Objects.requireNonNull(this.id);
            edge.type = Objects.requireNonNull(this.type);
            edge.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            edge.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            edge.targetObjectLabel = Objects.requireNonNull(this.targetObjectLabel);
            edge.descriptionId = Objects.requireNonNull(this.descriptionId);
            edge.beginLabel = this.beginLabel;
            edge.centerLabel = this.centerLabel;
            edge.endLabel = this.endLabel;
            edge.sourceId = Objects.requireNonNull(this.sourceId);
            edge.targetId = Objects.requireNonNull(this.targetId);
            edge.style = Objects.requireNonNull(this.style);
            edge.routingPoints = Objects.requireNonNull(this.routingPoints);
            return edge;
        }
    }
}
