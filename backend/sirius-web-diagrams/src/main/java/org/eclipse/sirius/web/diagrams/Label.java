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
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;

/**
 * A label.
 *
 * @author hmarchadour
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class Label {
    private UUID id;

    private String type;

    private String text;

    private Position position;

    private Size size;

    private Position alignment;

    private LabelStyle style;

    private Label() {
        // Prevent instantiation
    }

    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getType() {
        return this.type;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getText() {
        return this.text;
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
    public Position getAlignment() {
        return this.alignment;
    }

    @GraphQLField
    @GraphQLNonNull
    public LabelStyle getStyle() {
        return this.style;
    }

    public static Builder newLabel(UUID id) {
        return new Builder(id);
    }

    public static Builder newLabel(Label label) {
        return new Builder(label);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}, text: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.type, this.text);
    }

    /**
     * The builder used to create a label.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String type;

        private String text;

        private Position position;

        private Size size;

        private Position alignment;

        private LabelStyle style;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder(Label label) {
            this.id = label.getId();
            this.type = label.getType();
            this.text = label.getText();
            this.position = label.getPosition();
            this.size = label.getSize();
            this.alignment = label.getAlignment();
            this.style = label.getStyle();
        }

        public Builder type(String type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder text(String text) {
            this.text = Objects.requireNonNull(text);
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

        public Builder alignment(Position aligment) {
            this.alignment = Objects.requireNonNull(aligment);
            return this;
        }

        public Builder style(LabelStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Label build() {
            Label label = new Label();
            label.id = Objects.requireNonNull(this.id);
            label.type = Objects.requireNonNull(this.type);
            label.text = Objects.requireNonNull(this.text);
            label.position = Objects.requireNonNull(this.position);
            label.size = Objects.requireNonNull(this.size);
            label.alignment = Objects.requireNonNull(this.alignment);
            label.style = Objects.requireNonNull(this.style);
            return label;
        }
    }
}
