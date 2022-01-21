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
package org.eclipse.sirius.components.forms.validation;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The diagnostic of a widget.
 *
 * @author gcoutable
 */
@Immutable
public final class Diagnostic {

    private UUID id;

    private String kind;

    private String message;

    private Diagnostic() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public String getMessage() {
        return this.message;
    }

    public static Builder newDiagnostic(UUID id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a new diagnostic.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String kind;

        private String message;

        public Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder message(String message) {
            this.message = Objects.requireNonNull(message);
            return this;
        }

        public Diagnostic build() {
            Diagnostic diagnostic = new Diagnostic();
            diagnostic.id = Objects.requireNonNull(this.id);
            diagnostic.kind = Objects.requireNonNull(this.kind);
            diagnostic.message = Objects.requireNonNull(this.message);
            return diagnostic;
        }
    }
}
