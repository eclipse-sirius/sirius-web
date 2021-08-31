/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.representations;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;

/**
 * Default implementation for {@link ISemanticRepresentationMetadata}.
 *
 * @author pcdavid
 */
@Immutable
public final class SemanticRepresentationMetadata implements ISemanticRepresentationMetadata {

    private String id;

    private UUID descriptionId;

    private String targetObjectId;

    private String label;

    private String kind;

    private SemanticRepresentationMetadata() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    public static Builder newRepresentationMetadata(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, targetObjectId: {3}, label: {4}, kind: {5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.targetObjectId, this.label, this.kind);
    }

    /**
     * The builder used to create the semantic representation metadata.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private UUID descriptionId;

        private String targetObjectId;

        private String label;

        private String kind;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public SemanticRepresentationMetadata build() {
            SemanticRepresentationMetadata metadata = new SemanticRepresentationMetadata();
            metadata.id = Objects.requireNonNull(this.id);
            metadata.descriptionId = Objects.requireNonNull(this.descriptionId);
            metadata.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            metadata.label = Objects.requireNonNull(this.label);
            metadata.kind = Objects.requireNonNull(this.kind);
            return metadata;
        }

    }

}
