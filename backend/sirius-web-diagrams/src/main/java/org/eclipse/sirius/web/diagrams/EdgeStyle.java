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
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * The style of the edge.
 *
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class EdgeStyle {
    private int size;

    private LineStyle lineStyle;

    private ArrowStyle sourceArrow;

    private ArrowStyle targetArrow;

    private String color;

    private EdgeStyle() {
        // Prevent instantiation
    }

    @GraphQLField
    @GraphQLNonNull
    public int getSize() {
        return this.size;
    }

    @GraphQLField
    @GraphQLNonNull
    public LineStyle getLineStyle() {
        return this.lineStyle;
    }

    @GraphQLField
    @GraphQLNonNull
    public ArrowStyle getSourceArrow() {
        return this.sourceArrow;
    }

    @GraphQLField
    @GraphQLNonNull
    public ArrowStyle getTargetArrow() {
        return this.targetArrow;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getColor() {
        return this.color;
    }

    public static Builder newEdgeStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'size: {1}, lineStyle: {2}, sourceArrow: {3}, targetArrow: {4}, color: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.size, this.lineStyle, this.sourceArrow, this.targetArrow, this.color);
    }

    /**
     * The builder used to create the edge style.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private int size;

        private LineStyle lineStyle;

        private ArrowStyle sourceArrow;

        private ArrowStyle targetArrow;

        private String color;

        private Builder() {
            // Prevent instantiation
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder lineStyle(LineStyle lineStyle) {
            this.lineStyle = Objects.requireNonNull(lineStyle);
            return this;
        }

        public Builder sourceArrow(ArrowStyle sourceArrow) {
            this.sourceArrow = Objects.requireNonNull(sourceArrow);
            return this;
        }

        public Builder targetArrow(ArrowStyle targetArrow) {
            this.targetArrow = Objects.requireNonNull(targetArrow);
            return this;
        }

        public Builder color(String color) {
            this.color = Objects.requireNonNull(color);
            return this;
        }

        public EdgeStyle build() {
            EdgeStyle edgeStyle = new EdgeStyle();
            edgeStyle.size = this.size;
            edgeStyle.lineStyle = Objects.requireNonNull(this.lineStyle);
            edgeStyle.sourceArrow = Objects.requireNonNull(this.sourceArrow);
            edgeStyle.targetArrow = Objects.requireNonNull(this.targetArrow);
            edgeStyle.color = Objects.requireNonNull(this.color);
            return edgeStyle;
        }
    }
}
