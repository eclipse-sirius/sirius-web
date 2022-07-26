/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.components.diagrams.elements;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the edge element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class EdgeElementProps implements IProps {

    public static final String TYPE = "Edge"; //$NON-NLS-1$

    private String id;

    private String type;

    private String targetObjectId;

    private String targetObjectKind;

    private String targetObjectLabel;

    private UUID descriptionId;

    private String sourceId;

    private String targetId;

    private EdgeStyle style;

    private List<Position> routingPoints;

    private Ratio sourceAnchorRelativePosition;

    private Ratio targetAnchorRelativePosition;

    private List<Element> children;

    private EdgeElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    public String getTargetObjectLabel() {
        return this.targetObjectLabel;
    }

    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public String getTargetId() {
        return this.targetId;
    }

    public EdgeStyle getStyle() {
        return this.style;
    }

    public List<Position> getRoutingPoints() {
        return this.routingPoints;
    }

    public Ratio getSourceAnchorRelativePosition() {
        return this.sourceAnchorRelativePosition;
    }

    public Ratio getTargetAnchorRelativePosition() {
        return this.targetAnchorRelativePosition;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newEdgeElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, descriptionId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.descriptionId);
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

        private String sourceId;

        private String targetId;

        private EdgeStyle style;

        private List<Position> routingPoints;

        private Ratio sourceAnchorRelativePosition;

        private Ratio targetAnchorRelativePosition;

        private List<Element> children = new ArrayList<>();

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
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

        public Builder sourceAnchorRelativePosition(Ratio sourceAnchorRelativePosition) {
            this.sourceAnchorRelativePosition = Objects.requireNonNull(sourceAnchorRelativePosition);
            return this;
        }

        public Builder targetAnchorRelativePosition(Ratio targetAnchorRelativePosition) {
            this.targetAnchorRelativePosition = Objects.requireNonNull(targetAnchorRelativePosition);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = children;
            return this;
        }

        public EdgeElementProps build() {
            EdgeElementProps edgeElementProps = new EdgeElementProps();
            edgeElementProps.id = Objects.requireNonNull(this.id);
            edgeElementProps.type = Objects.requireNonNull(this.type);
            edgeElementProps.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            edgeElementProps.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            edgeElementProps.targetObjectLabel = Objects.requireNonNull(this.targetObjectLabel);
            edgeElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            edgeElementProps.sourceId = Objects.requireNonNull(this.sourceId);
            edgeElementProps.targetId = Objects.requireNonNull(this.targetId);
            edgeElementProps.style = Objects.requireNonNull(this.style);
            edgeElementProps.routingPoints = Objects.requireNonNull(this.routingPoints);
            edgeElementProps.sourceAnchorRelativePosition = Objects.requireNonNull(this.sourceAnchorRelativePosition);
            edgeElementProps.targetAnchorRelativePosition = Objects.requireNonNull(this.targetAnchorRelativePosition);
            edgeElementProps.children = this.children;
            return edgeElementProps;
        }
    }
}
