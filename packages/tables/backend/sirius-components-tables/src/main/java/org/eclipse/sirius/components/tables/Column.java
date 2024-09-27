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
package org.eclipse.sirius.components.tables;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Column concept of the table representation.
 *
 * @author arichard
 * @author lfasani
 */
@Immutable
public final class Column {

    private UUID id;

    private String targetObjectId;

    private String targetObjectKind;

    private UUID descriptionId;

    private String label;

    private Column() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    public String getLabel() {
        return this.label;
    }

    public static Builder newColumn(UUID id) {
        return new Builder(id);
    }

    public static Builder newColumn(Column column) {
        return new Builder(column);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.label);
    }

    /**
     * The builder used to create a column.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private UUID descriptionId;

        private String targetObjectId;

        private String targetObjectKind;

        private String label;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Column column) {
            this.id = column.getId();
            this.descriptionId = column.getDescriptionId();
            this.label = column.getLabel();
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Column build() {
            Column column = new Column();
            column.id = Objects.requireNonNull(this.id);
            column.descriptionId = Objects.requireNonNull(this.descriptionId);
            column.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            column.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            column.label = Objects.requireNonNull(this.label);
            return column;
        }
    }
}
