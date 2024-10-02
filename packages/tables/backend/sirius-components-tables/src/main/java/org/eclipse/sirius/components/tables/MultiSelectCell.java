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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 *
 * Mult-Select-based Cell concept of the table representation.
 *
 * @author arichard
 */
@Immutable
public final class MultiSelectCell extends AbstractCell {

    private List<SelectCellOption> options;

    private List<String> values;

    private MultiSelectCell() {
        // Prevent instantiation
    }

    public List<SelectCellOption> getOptions() {
        return this.options;
    }

    public List<String> getValues() {
        return this.values;
    }

    public static Builder newCell(UUID id) {
        return new Builder(id);
    }

    public static Builder newCell(MultiSelectCell cell) {
        return new Builder(cell);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, parentLineId: {2}, columnId: {3}, cellType:{4}, values: {5}', options: {6}}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.parentLineId, this.columnId, this.cellType, this.values, this.options);
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

        private List<SelectCellOption> options;

        private List<String> values;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(MultiSelectCell cell) {
            this.id = cell.getId();
            this.parentLineId = cell.getParentLineId();
            this.columnId = cell.getColumnId();
            this.options = cell.getOptions();
            this.cellType = cell.getCellType();
            this.values = cell.getValues();
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

        public Builder options(List<SelectCellOption> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder values(List<String> values) {
            this.values = values;
            return this;
        }

        public MultiSelectCell build() {
            MultiSelectCell cell = new MultiSelectCell();
            cell.id = Objects.requireNonNull(this.id);
            cell.parentLineId = Objects.requireNonNull(this.parentLineId);
            cell.columnId = Objects.requireNonNull(this.columnId);
            cell.cellType = Objects.requireNonNull(this.cellType);
            cell.options = Objects.requireNonNull(this.options);
            cell.values = this.values;
            return cell;
        }
    }
}
