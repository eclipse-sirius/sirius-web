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

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, columnId: {2}, values: {3}', options: {4}}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.columnId, this.values, this.options);
    }

    /**
     * The builder used to create a cell.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String targetObjectId;

        private String targetObjectKind;

        private UUID columnId;

        private List<SelectCellOption> options;

        private List<String> values;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
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
            cell.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            cell.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            cell.columnId = Objects.requireNonNull(this.columnId);
            cell.options = Objects.requireNonNull(this.options);
            cell.values = this.values;
            return cell;
        }
    }
}
