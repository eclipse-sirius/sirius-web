/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
 * The Rich Text widget.
 *
 * @author pcdavid
 */
@Immutable
public final class RichText extends AbstractWidget {

    private String value;

    private Function<String, IStatus> newValueHandler;

    private RichText() {
        // Prevent instantiation
    }

    public static Builder newRichText(String id) {
        return new Builder(id);
    }

    public String getValue() {
        return this.value;
    }

    public Function<String, IStatus> getNewValueHandler() {
        return this.newValueHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, value: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.value);
    }

    /**
     * The builder used to create the rich text editor.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL = List.of();

        private String value;

        private Function<String, IStatus> newValueHandler;

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

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder value(String value) {
            this.value = Objects.requireNonNull(value);
            return this;
        }

        public Builder newValueHandler(Function<String, IStatus> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
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

        public RichText build() {
            RichText richtext = new RichText();
            richtext.id = Objects.requireNonNull(this.id);
            richtext.label = Objects.requireNonNull(this.label);
            richtext.iconURL = Objects.requireNonNull(this.iconURL);
            richtext.value = Objects.requireNonNull(this.value);
            richtext.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            richtext.diagnostics = Objects.requireNonNull(this.diagnostics);
            richtext.helpTextProvider = this.helpTextProvider; // Optional on purpose
            richtext.readOnly = this.readOnly;
            return richtext;
        }
    }
}
