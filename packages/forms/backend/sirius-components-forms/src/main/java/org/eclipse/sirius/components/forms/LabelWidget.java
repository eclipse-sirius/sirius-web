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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;

/**
 * The label widget.
 *
 * @author fbarbin
 */
@Immutable
public final class LabelWidget extends AbstractWidget {
    private String label;

    private String value;

    private LabelWidgetStyle style;

    private LabelWidget() {
        // Prevent instantiation
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public String getValue() {
        return this.value;
    }

    public LabelWidgetStyle getStyle() {
        return this.style;
    }

    public static Builder newLabelWidget(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.value);
    }

    /**
     * The builder used to create the label widget.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String iconURL;

        private String value;

        private LabelWidgetStyle style;

        private List<Diagnostic> diagnostics;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
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

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public LabelWidget build() {
            LabelWidget labelWidget = new LabelWidget();
            labelWidget.id = Objects.requireNonNull(this.id);
            labelWidget.label = Objects.requireNonNull(this.label);
            labelWidget.iconURL = this.iconURL; // Optional on purpose
            labelWidget.value = Objects.requireNonNull(this.value);
            labelWidget.style = this.style; // Optional on purpose
            labelWidget.diagnostics = Objects.requireNonNull(this.diagnostics);
            return labelWidget;
        }
    }
}
