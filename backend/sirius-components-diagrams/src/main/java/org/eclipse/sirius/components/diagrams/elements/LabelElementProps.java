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
package org.eclipse.sirius.components.diagrams.elements;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the label element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class LabelElementProps implements IProps {

    public static final String TYPE = "Label"; //$NON-NLS-1$

    private String id;

    private String type;

    private String text;

    private Position position;

    private Size size;

    private Position alignment;

    private LabelStyle style;

    private LabelElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }

    public Position getPosition() {
        return this.position;
    }

    public Size getSize() {
        return this.size;
    }

    public Position getAlignment() {
        return this.alignment;
    }

    public LabelStyle getStyle() {
        return this.style;
    }

    public static Builder newLabelElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}, text: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.type, this.text);
    }

    /**
     * The builder used to create a label element.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String type;

        private String text;

        private Position position;

        private Size size;

        private Position alignment;

        private LabelStyle style;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
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

        public LabelElementProps build() {
            LabelElementProps labelElementProps = new LabelElementProps();
            labelElementProps.id = Objects.requireNonNull(this.id);
            labelElementProps.type = Objects.requireNonNull(this.type);
            labelElementProps.text = Objects.requireNonNull(this.text);
            labelElementProps.position = Objects.requireNonNull(this.position);
            labelElementProps.size = Objects.requireNonNull(this.size);
            labelElementProps.alignment = Objects.requireNonNull(this.alignment);
            labelElementProps.style = Objects.requireNonNull(this.style);
            return labelElementProps;
        }
    }
}
