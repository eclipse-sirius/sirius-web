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
 * Checkbox-based Cell concept of the table representation.
 *
 * @author arichard
 */
@Immutable
public final class CheckboxCell extends AbstractCell {

    private boolean value;

    private CheckboxCell() {
        // Prevent instantiation
    }

    public boolean isValue() {
        return this.value;
    }

    public static Builder newCell(UUID id) {
        return new Builder(id);
    }

    public static Builder newCell(CheckboxCell cell) {
        return new Builder(cell);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, parentLineId: {2}, columnId: {3}, value: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.parentLineId, this.columnId, this.value);
    }

    /**
     * The builder used to create a cell.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private UUID parentLineId;

        private UUID columnId;

        private String cellType;

        private boolean value;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(CheckboxCell cell) {
            this.id = cell.getId();
            this.parentLineId = cell.getParentLineId();
            this.columnId = cell.getColumnId();
            this.cellType = cell.getCellType();
            this.value = cell.isValue();
        }

        public Builder parentLineId(UUID parentLineId) {
            this.parentLineId = Objects.requireNonNull(parentLineId);
            return this;
        }

        public Builder columnId(UUID columnId) {
            this.columnId = Objects.requireNonNull(columnId);
            return this;
        }

        public Builder cellType(String cellType) {
            this.cellType = Objects.requireNonNull(cellType);
            return this;
        }

        public Builder value(boolean value) {
            this.value = value;
            return this;
        }

        public CheckboxCell build() {
            CheckboxCell cell = new CheckboxCell();
            cell.id = Objects.requireNonNull(this.id);
            cell.parentLineId = Objects.requireNonNull(this.parentLineId);
            cell.columnId = Objects.requireNonNull(this.columnId);
            cell.cellType = Objects.requireNonNull(this.cellType);
            cell.value = this.value;
            return cell;
        }
    }
}
