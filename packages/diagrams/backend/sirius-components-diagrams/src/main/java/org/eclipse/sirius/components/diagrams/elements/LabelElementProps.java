/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the label element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class LabelElementProps implements IProps {

    public static final String TYPE = "Label";

    private String id;

    private String type;

    private String text;

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

    public LabelStyle getStyle() {
        return this.style;
    }

    public static Builder newLabelElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, type: {2}, text: {3}'}'";
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

        public Builder style(LabelStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public LabelElementProps build() {
            LabelElementProps labelElementProps = new LabelElementProps();
            labelElementProps.id = Objects.requireNonNull(this.id);
            labelElementProps.type = Objects.requireNonNull(this.type);
            labelElementProps.text = Objects.requireNonNull(this.text);
            labelElementProps.style = Objects.requireNonNull(this.style);
            return labelElementProps;
        }
    }
}
