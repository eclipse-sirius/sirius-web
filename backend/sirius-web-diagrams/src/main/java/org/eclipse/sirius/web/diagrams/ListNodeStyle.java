/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
 * The list container node style.
 *
 * @author gcoutable
 */
@Immutable
@GraphQLObjectType
public final class ListNodeStyle implements INodeStyle {

    private String color;

    private String borderColor;

    private int borderSize;

    private LineStyle borderStyle;

    private ListNodeStyle() {
        // Prevent instantiation
    }

    @GraphQLNonNull
    @GraphQLField
    public String getColor() {
        return this.color;
    }

    @GraphQLNonNull
    @GraphQLField
    public String getBorderColor() {
        return this.borderColor;
    }

    @GraphQLNonNull
    @GraphQLField
    public int getBorderSize() {
        return this.borderSize;
    }

    @GraphQLNonNull
    @GraphQLField
    public LineStyle getBorderStyle() {
        return this.borderStyle;
    }

    public static Builder newListNodeStyle() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'color: {1}, borderColor: {2}, borderSize: {3}, borderStyle: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.color, this.borderColor, this.borderSize, this.borderStyle);
    }

    /**
     * The builder used to create the list container node style.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String color;

        private String borderColor;

        private int borderSize;

        private LineStyle borderStyle;

        private Builder() {
            // Prevent instantiation
        }

        public Builder color(String color) {
            this.color = Objects.requireNonNull(color);
            return this;
        }

        public Builder borderColor(String borderColor) {
            this.borderColor = Objects.requireNonNull(borderColor);
            return this;
        }

        public Builder borderSize(int borderSize) {
            this.borderSize = Objects.requireNonNull(borderSize);
            return this;
        }

        public Builder borderStyle(LineStyle borderStyle) {
            this.borderStyle = Objects.requireNonNull(borderStyle);
            return this;
        }

        public ListNodeStyle build() {
            ListNodeStyle nodeStyleDescription = new ListNodeStyle();
            nodeStyleDescription.color = Objects.requireNonNull(this.color);
            nodeStyleDescription.borderColor = Objects.requireNonNull(this.borderColor);
            nodeStyleDescription.borderSize = Objects.requireNonNull(this.borderSize);
            nodeStyleDescription.borderStyle = Objects.requireNonNull(this.borderStyle);
            return nodeStyleDescription;
        }

    }

}
