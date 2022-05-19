/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The select widget.
 *
 * @author pcdavid
 * @author arichard
 */
@Immutable
public final class MultiSelect extends AbstractWidget {
    private String label;

    private List<SelectOption> options;

    private List<String> values;

    private Function<List<String>, IStatus> newValuesHandler;

    private MultiSelectStyle style;

    private MultiSelect() {
        // Prevent instantiation
    }

    public String getLabel() {
        return this.label;
    }

    public List<SelectOption> getOptions() {
        return this.options;
    }

    public List<String> getValues() {
        return this.values;
    }

    public Function<List<String>, IStatus> getNewValuesHandler() {
        return this.newValuesHandler;
    }

    public MultiSelectStyle getStyle() {
        return this.style;
    }

    public static Builder newMultiSelect(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, values: {3}, options: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.values, this.options);
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

        private List<SelectOption> options;

        private List<String> values;

        private Function<List<String>, IStatus> newValuesHandler;

        private MultiSelectStyle style;

        private List<Diagnostic> diagnostics;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder options(List<SelectOption> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder values(List<String> values) {
            this.values = List.copyOf(values);
            return this;
        }

        public Builder newValuesHandler(Function<List<String>, IStatus> newValuesHandler) {
            this.newValuesHandler = Objects.requireNonNull(newValuesHandler);
            return this;
        }

        public Builder style(MultiSelectStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public MultiSelect build() {
            MultiSelect select = new MultiSelect();
            select.id = Objects.requireNonNull(this.id);
            select.label = Objects.requireNonNull(this.label);
            select.options = Objects.requireNonNull(this.options);
            select.values = this.values;
            select.newValuesHandler = Objects.requireNonNull(this.newValuesHandler);
            select.style = this.style; // Optional on purpose
            select.diagnostics = Objects.requireNonNull(this.diagnostics);
            return select;
        }
    }
}
