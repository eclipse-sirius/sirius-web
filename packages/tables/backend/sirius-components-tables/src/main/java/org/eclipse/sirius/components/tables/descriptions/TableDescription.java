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
package org.eclipse.sirius.components.tables.descriptions;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a table representation.
 *
 * @author arichard
 * @author lfasani
 */
@Immutable
public final class TableDescription {
    public static final String LABEL = "label";

    private String id;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private Function<VariableManager, String> labelProvider;

    private List<LineDescription> lineDescriptions;

    private List<ColumnDescription> columnDescriptions;

    private CellDescription cellDescription;

    private TableDescription() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, String> getTargetObjectKindProvider() {
        return this.targetObjectKindProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public List<LineDescription> getLineDescriptions() {
        return this.lineDescriptions;
    }

    public List<ColumnDescription> getColumnDescriptions() {
        return this.columnDescriptions;
    }

    public CellDescription getCellDescription() {
        return this.cellDescription;
    }


    public static Builder newTableDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the table description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private Function<VariableManager, String> labelProvider;

        private List<LineDescription> lineDescriptions;

        private List<ColumnDescription> columnDescriptions;

        private CellDescription cellDescription;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder targetObjectKindProvider(Function<VariableManager, String> targetObjectKindProvider) {
            this.targetObjectKindProvider = Objects.requireNonNull(targetObjectKindProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder lineDescriptions(List<LineDescription> lineDescriptions) {
            this.lineDescriptions = Objects.requireNonNull(lineDescriptions);
            return this;
        }

        public Builder columnDescriptions(List<ColumnDescription> columnDescriptions) {
            this.columnDescriptions = Objects.requireNonNull(columnDescriptions);
            return this;
        }

        public Builder cellDescription(CellDescription cellDescription) {
            this.cellDescription = Objects.requireNonNull(cellDescription);
            return this;
        }

        public TableDescription build() {
            TableDescription tableDescription = new TableDescription();
            tableDescription.id = Objects.requireNonNull(this.id);
            tableDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            tableDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            tableDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            tableDescription.lineDescriptions = Objects.requireNonNull(this.lineDescriptions);
            tableDescription.columnDescriptions = Objects.requireNonNull(this.columnDescriptions);
            tableDescription.cellDescription = Objects.requireNonNull(this.cellDescription);
            return tableDescription;
        }
    }
}
