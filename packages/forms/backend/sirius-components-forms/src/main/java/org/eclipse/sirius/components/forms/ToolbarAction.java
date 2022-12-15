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
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * The toolbar action widget.
 *
 * @author arichard
 */
@Immutable
public final class ToolbarAction extends AbstractWidget {
    private String label;

    private String buttonLabel;

    private String imageURL;

    private Supplier<IStatus> pushButtonHandler;

    private ButtonStyle style;

    private ToolbarAction() {
        // Prevent instantiation
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public String getButtonLabel() {
        return this.buttonLabel;
    }

    public ButtonStyle getStyle() {
        return this.style;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public Supplier<IStatus> getPushButtonHandler() {
        return this.pushButtonHandler;
    }

    public static Builder newToolbarAction(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, buttonLabel: {3}, imageURL: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.buttonLabel, this.imageURL);
    }

    /**
     * The builder used to create the toolbar action.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String iconURL;

        private String buttonLabel;

        private String imageURL;

        private Supplier<IStatus> pushButtonHandler;

        private ButtonStyle style;

        private List<Diagnostic> diagnostics;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder buttonLabel(String buttonLabel) {
            this.buttonLabel = Objects.requireNonNull(buttonLabel);
            return this;
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder pushButtonHandler(Supplier<IStatus> pushButtonHandler) {
            this.pushButtonHandler = Objects.requireNonNull(pushButtonHandler);
            return this;
        }

        public Builder style(ButtonStyle style) {
            this.style = Objects.requireNonNull(style);
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public ToolbarAction build() {
            ToolbarAction button = new ToolbarAction();
            button.id = Objects.requireNonNull(this.id);
            button.label = Objects.requireNonNull(this.label);
            button.buttonLabel = this.buttonLabel;
            button.iconURL = this.iconURL;
            button.imageURL = this.imageURL;
            button.pushButtonHandler = Objects.requireNonNull(this.pushButtonHandler);
            button.style = this.style; // Optional on purpose
            button.diagnostics = Objects.requireNonNull(this.diagnostics);
            return button;
        }
    }
}
