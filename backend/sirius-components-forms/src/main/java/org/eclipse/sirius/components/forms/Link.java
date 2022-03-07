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
 * The Link widget.
 *
 * @author ldelaigue
 */
@Immutable
public final class Link extends AbstractWidget {
    private String label;

    private String url;

    private Link() {
        // Prevent instantiation
    }

    public String getLabel() {
        return this.label;
    }

    public String getUrl() {
        return this.url;
    }

    public static Builder newLink(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, url: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label, this.url);
    }

    /**
     * The builder used to create the Link.
     *
     * @author ldelaigue
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private final String id;

        private String label;

        private String url;

        private List<Diagnostic> diagnostics;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder url(String value) {
            this.url = Objects.requireNonNull(value);
            return this;
        }

        public Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public Link build() {
            Link link = new Link();
            link.id = Objects.requireNonNull(this.id);
            link.label = Objects.requireNonNull(this.label);
            link.url = Objects.requireNonNull(this.url);
            link.diagnostics = Objects.requireNonNull(this.diagnostics);
            return link;
        }
    }
}
