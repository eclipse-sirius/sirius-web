/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
 * Checkbox-based Cell concept of the table representation.
 *
 * @author arichard
 */
@Immutable
public final class CheckboxCell implements ICell {

    public static final String TYPE = "CHECKBOX";

    private UUID id;

    private String descriptionId;

    private String targetObjectId;

    private String targetObjectKind;

    private UUID columnId;

    private boolean value;

    private CheckboxCell() {
        // Prevent instantiation
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    @Override
    public UUID getColumnId() {
        return this.columnId;
    }

    public boolean isValue() {
        return this.value;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, columnId: {2}, value: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.columnId, this.value);
    }

    public static Builder newCheckboxCell(UUID id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a cell.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private String descriptionId;

        private String targetObjectId;

        private String targetObjectKind;

        private UUID columnId;

        private boolean value;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(String descriptionId) {
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

        public Builder columnId(UUID columnId) {
            this.columnId = Objects.requireNonNull(columnId);
            return this;
        }

        public Builder value(boolean value) {
            this.value = value;
            return this;
        }

        public CheckboxCell build() {
            CheckboxCell cell = new CheckboxCell();
            cell.id = Objects.requireNonNull(this.id);
            cell.descriptionId = Objects.requireNonNull(this.descriptionId);
            cell.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            cell.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            cell.columnId = Objects.requireNonNull(this.columnId);
            cell.value = this.value;
            return cell;
        }
    }
}
