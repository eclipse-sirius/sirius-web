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
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;

/**
 * The Image widget.
 *
 * @author pcdavid
 */
@Immutable
public final class Image extends AbstractWidget {

    private String url;

    private String maxWidth;

    private Image() {
        // Prevent instantiation
    }

    public static Builder newImage(String id) {
        return new Builder(id);
    }

    public String getUrl() {
        return this.url;
    }

    public String getMaxWidth() {
        return this.maxWidth;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, url: {3}, maxWidth: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.url, this.maxWidth);
    }

    /**
     * The builder used to create the Image.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private final boolean readOnly = true; // Read-only by nature

        private String label;

        private List<String> iconURL = List.of();

        private String url;

        private String maxWidth;

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

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

        public Builder url(String url) {
            this.url = Objects.requireNonNull(url);
            return this;
        }

        public Builder maxWidth(String maxWidth) {
            this.maxWidth = Objects.requireNonNull(maxWidth);
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

        public Image build() {
            Image image = new Image();
            image.id = Objects.requireNonNull(this.id);
            image.label = Objects.requireNonNull(this.label);
            image.iconURL = Objects.requireNonNull(this.iconURL);
            image.url = Objects.requireNonNull(this.url);
            image.maxWidth = this.maxWidth;
            image.diagnostics = Objects.requireNonNull(this.diagnostics);
            image.helpTextProvider = this.helpTextProvider; // Optional on purpose
            image.readOnly = this.readOnly;
            return image;
        }
    }
}
