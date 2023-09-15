/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.validation.Diagnostic;

/**
 * A widget to represent an "If" element when rendering a Form in the context of a FormDescriptionEditor.
 *
 * @author pcdavid
 */
@Immutable
public final class FormDescriptionEditorIf extends AbstractWidget {

    private List<AbstractWidget> children;

    private FormDescriptionEditorIf() {
        // Prevent instantiation
    }

    public static Builder newFormDescriptionEditorIf(String id) {
        return new Builder(id);
    }

    public List<AbstractWidget> getChildren() {
        return this.children;
    }

    public boolean hasHelpText() {
        return false;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label);
    }

    /**
     * The builder used to create the If widget.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL = List.of();

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private List<AbstractWidget> children;

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

        public Builder children(List<AbstractWidget> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public FormDescriptionEditorIf build() {
            FormDescriptionEditorIf formDescriptionEditorIf = new FormDescriptionEditorIf();
            formDescriptionEditorIf.id = Objects.requireNonNull(this.id);
            formDescriptionEditorIf.label = Objects.requireNonNull(this.label);
            formDescriptionEditorIf.iconURL = Objects.requireNonNull(this.iconURL);
            formDescriptionEditorIf.diagnostics = Objects.requireNonNull(this.diagnostics);
            formDescriptionEditorIf.helpTextProvider = this.helpTextProvider; // Optional on purpose
            formDescriptionEditorIf.readOnly = this.readOnly;
            formDescriptionEditorIf.children = Objects.requireNonNull(this.children);
            return formDescriptionEditorIf;
        }
    }
}
