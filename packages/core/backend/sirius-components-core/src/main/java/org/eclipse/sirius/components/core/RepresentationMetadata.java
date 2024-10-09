/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.core;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * The metadata of a representation.
 *
 * @author sbegaudeau
 */
public record RepresentationMetadata(String id, String kind, String label, String descriptionId) {
    public RepresentationMetadata {
        Objects.requireNonNull(id);
        Objects.requireNonNull(kind);
        Objects.requireNonNull(label);
        Objects.requireNonNull(descriptionId);
    }

    public static Builder newRepresentationMetadata(String id) {
        return new Builder(id);
    }

    public static Builder newRepresentationMetadata(RepresentationMetadata representationMetadata) {
        return new Builder(representationMetadata);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, kind: {2}, label: {3}, descriptionId: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.kind, this.label, this.descriptionId);
    }

    /**
     * The Builder to create a new {@link RepresentationMetadata}.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private final String id;

        private String kind;

        private String label;

        private String descriptionId;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(RepresentationMetadata representationMetadata) {
            this.id = representationMetadata.id;
            this.kind = representationMetadata.kind;
            this.label = representationMetadata.label;
            this.descriptionId = representationMetadata.descriptionId;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public RepresentationMetadata build() {
            return new RepresentationMetadata(
                    Objects.requireNonNull(this.id),
                    Objects.requireNonNull(this.kind),
                    Objects.requireNonNull(this.label),
                    Objects.requireNonNull(this.descriptionId)
            );
        }

    }
}
