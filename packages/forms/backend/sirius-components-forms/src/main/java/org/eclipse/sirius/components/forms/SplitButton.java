/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import java.text.MessageFormat;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;

/**
 * The Splitbutton widget.
 * It contains a list of Button widget.
 *
 * @author mcharfadi
 */
@Immutable
public final class SplitButton extends AbstractWidget {

    private String label;

    private List<Button> actions = new ArrayList<>();

    private SplitButton() {
        // Prevent instantiation
    }

    public static SplitButton.Builder newSplitButton(String id) {
        return new SplitButton.Builder(id);
    }

    @Override
    public String getLabel() {
        return label;
    }

    public List<Button> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, actionsCount: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.actions.size());
    }

    /**
     * The builder used to create the splitButton.
     *
     * @author mcharfadi
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL = List.of();

        private List<Button> actions;

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public SplitButton.Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public SplitButton.Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public SplitButton.Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public SplitButton.Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public SplitButton.Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public SplitButton.Builder actions(List<Button> actions) {
            this.actions = Objects.requireNonNull(actions);
            return this;
        }

        public SplitButton build() {
            SplitButton splitButton = new SplitButton();
            splitButton.id = Objects.requireNonNull(this.id);
            splitButton.label = Objects.requireNonNull(this.label);
            splitButton.iconURL = Objects.requireNonNull(this.iconURL);
            splitButton.diagnostics = Objects.requireNonNull(this.diagnostics);
            splitButton.helpTextProvider = this.helpTextProvider; // Optional on purpose
            splitButton.actions = Objects.requireNonNull(this.actions);
            splitButton.readOnly = this.readOnly;
            return splitButton;
        }
    }
}
