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
package org.eclipse.sirius.components.diagrams.appearancedata;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;

/**
 * Edge style with nullable fields to store customizations.
 *
 * @author nvannier
 */
@Immutable
public final class EdgeCustomizedStyle {

    private Integer size;

    private LineStyle lineStyle;

    private ArrowStyle sourceArrow;

    private ArrowStyle targetArrow;

    private String color;

    private EdgeCustomizedStyle() {
        // Prevent instantiation
    }

    public Integer getSize() {
        return this.size;
    }

    public LineStyle getLineStyle() {
        return this.lineStyle;
    }

    public ArrowStyle getSourceArrow() {
        return this.sourceArrow;
    }

    public ArrowStyle getTargetArrow() {
        return this.targetArrow;
    }

    public String getColor() {
        return this.color;
    }

    public static Builder newEdgeCustomizedStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'size: {1}, lineStyle: {2}, sourceArrow: {3}, targetArrow: {4}, color: {5}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.size, this.lineStyle, this.sourceArrow, this.targetArrow, this.color);
    }

    /**
     * Builder for an edge customized style.
     *
     * @author nvannier
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private Integer size;

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

        public EdgeCustomizedStyle build() {
            EdgeCustomizedStyle edgeCustomizedStyle = new EdgeCustomizedStyle();
            edgeCustomizedStyle.size = this.size;
            edgeCustomizedStyle.lineStyle = this.lineStyle;
            edgeCustomizedStyle.sourceArrow = this.sourceArrow;
            edgeCustomizedStyle.targetArrow = this.targetArrow;
            edgeCustomizedStyle.color = this.color;
            return edgeCustomizedStyle;
        }
    }
}
