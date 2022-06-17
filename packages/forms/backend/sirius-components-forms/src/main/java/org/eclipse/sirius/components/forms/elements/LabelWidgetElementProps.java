/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.LabelWidgetStyle;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the label widget element.
 *
 * @author fbarbin
 */
@Immutable
public final class LabelWidgetElementProps implements IProps {
    public static final String TYPE = "LabelWidget"; //$NON-NLS-1$

    private String id;

    private String label;

    private String value;

    private LabelWidgetStyle style;

    private List<Element> children;

    private LabelWidgetElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public LabelWidgetStyle getStyle() {
        return this.style;
    }

    public static Builder newLabelWidgetElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.value);
    }

    /**
     * The builder of the label element props.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String value;

        private LabelWidgetStyle style;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder value(String value) {
            this.value = Objects.requireNonNull(value);
            return this;
        }

        public Builder style(LabelWidgetStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public LabelWidgetElementProps build() {
            LabelWidgetElementProps labelWidgetElementProps = new LabelWidgetElementProps();
            labelWidgetElementProps.id = Objects.requireNonNull(this.id);
            labelWidgetElementProps.label = Objects.requireNonNull(this.label);
            labelWidgetElementProps.value = Objects.requireNonNull(this.value);
            labelWidgetElementProps.style = this.style; // Optional on purpose
            labelWidgetElementProps.children = Objects.requireNonNull(this.children);
            return labelWidgetElementProps;
        }
    }
}
