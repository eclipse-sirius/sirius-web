/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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
package org.eclipse.sirius.components.validation;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * A diagnostic of the validation representation.
 *
 * @author gcoutable
 */
@Immutable
public final class ValidationDiagnostic {

    private UUID id;

    private String kind;

    private String message;

    private boolean fixable;

    private ValidationDiagnostic() {
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

    public boolean isFixable() {
        return this.fixable;
    }

    public static Builder newValidationDiagnostic(UUID id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a new validation diagnostic.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private UUID id;

        private String kind;

        private String message;

        private boolean fixable;

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

        public Builder fixable(boolean fixable) {
            this.fixable = fixable;
            return this;
        }

        public ValidationDiagnostic build() {
            ValidationDiagnostic diagnostic = new ValidationDiagnostic();
            diagnostic.id = Objects.requireNonNull(this.id);
            diagnostic.kind = Objects.requireNonNull(this.kind);
            diagnostic.message = Objects.requireNonNull(this.message);
            diagnostic.fixable = this.fixable;
            return diagnostic;
        }
    }
}
