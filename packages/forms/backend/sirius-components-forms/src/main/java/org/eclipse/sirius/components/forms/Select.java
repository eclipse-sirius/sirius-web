/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The select widget.
 *
 * @author lfasani
 */
@Immutable
public final class Select extends AbstractWidget {
    private List<SelectOption> options;

    private String value;

    private Function<String, IStatus> newValueHandler;

    private SelectStyle style;

    private Select() {
        // Prevent instantiation
    }

    public List<SelectOption> getOptions() {
        return this.options;
    }

    public String getValue() {
        return this.value;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    public SelectStyle getStyle() {
        return this.style;
    }

    public static Builder newSelect(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}, options: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.value, this.options);
    }

    /**
     * The builder used to create the select.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String iconURL;

        private List<SelectOption> options;

        private String value;

        private Function<String, IStatus> newValueHandler;

        private SelectStyle style;

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

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

        public Builder options(List<SelectOption> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder newValueHandler(Function<String, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public Builder style(SelectStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Select build() {
            Select select = new Select();
            select.id = Objects.requireNonNull(this.id);
            select.label = Objects.requireNonNull(this.label);
            select.iconURL = this.iconURL; // Optional on purpose
            select.options = Objects.requireNonNull(this.options);
            select.value = this.value;
            select.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            select.style = this.style; // Optional on purpose
            select.diagnostics = Objects.requireNonNull(this.diagnostics);
            select.helpTextProvider = this.helpTextProvider; // Optional on purpose
            select.readOnly = this.readOnly;
            return select;
        }
    }
}
