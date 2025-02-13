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
package org.eclipse.sirius.components.tables.descriptions;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a table representation.
 *
 * @author arichard
 * @author lfasani
 */
@Immutable
public final class TableDescription implements IRepresentationDescription {

    public static final String LABEL = "label";

    /**
     * The variable name used to store a reference to a table.
     */
    public static final String TABLE = "table";

    private String id;

    private String label;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private Function<VariableManager, String> labelProvider;

    private Predicate<VariableManager> isStripeRowPredicate;

    private LineDescription lineDescription;

    private List<ColumnDescription> columnDescriptions;

    private List<ICellDescription> cellDescriptions;

    private Function<VariableManager, List<String>> iconURLsProvider;

    private boolean enableSubRows;

    private TableDescription() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
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

    public Predicate<VariableManager> getIsStripeRowPredicate() {
        return this.isStripeRowPredicate;
    }

    public LineDescription getLineDescription() {
        return this.lineDescription;
    }

    public List<ColumnDescription> getColumnDescriptions() {
        return this.columnDescriptions;
    }

    public List<ICellDescription> getCellDescriptions() {
        return this.cellDescriptions;
    }

    public Function<VariableManager, List<String>> getIconURLsProvider() {
        return this.iconURLsProvider;
    }

    public boolean isEnableSubRows() {
        return this.enableSubRows;
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

        private final String id;

        private String label;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private Function<VariableManager, String> labelProvider;

        private Predicate<VariableManager> isStripeRowPredicate;

        private LineDescription lineDescription;

        private List<ColumnDescription> columnDescriptions;

        private List<ICellDescription> cellDescriptions;

        private Function<VariableManager, List<String>> iconURLsProvider;

        private boolean enableSubRows;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
            return this;
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

        public Builder isStripeRowPredicate(Predicate<VariableManager> isStripeRowPredicate) {
            this.isStripeRowPredicate = Objects.requireNonNull(isStripeRowPredicate);
            return this;
        }

        public Builder lineDescription(LineDescription lineDescription) {
            this.lineDescription = Objects.requireNonNull(lineDescription);
            return this;
        }

        public Builder columnDescriptions(List<ColumnDescription> columnDescriptions) {
            this.columnDescriptions = Objects.requireNonNull(columnDescriptions);
            return this;
        }

        public Builder cellDescriptions(List<ICellDescription> cellDescriptions) {
            this.cellDescriptions = Objects.requireNonNull(cellDescriptions);
            return this;
        }

        public Builder iconURLsProvider(Function<VariableManager, List<String>> iconURLsProvider) {
            this.iconURLsProvider = Objects.requireNonNull(iconURLsProvider);
            return this;
        }

        public Builder enableSubRows(boolean enableSubRows) {
            this.enableSubRows = enableSubRows;
            return this;
        }

        public TableDescription build() {
            TableDescription tableDescription = new TableDescription();
            tableDescription.id = Objects.requireNonNull(this.id);
            tableDescription.label = Objects.requireNonNull(this.label);
            tableDescription.canCreatePredicate = this.canCreatePredicate;
            tableDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            tableDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            tableDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            tableDescription.isStripeRowPredicate = Objects.requireNonNull(this.isStripeRowPredicate);
            tableDescription.lineDescription = Objects.requireNonNull(this.lineDescription);
            tableDescription.columnDescriptions = Objects.requireNonNull(this.columnDescriptions);
            tableDescription.cellDescriptions = Objects.requireNonNull(this.cellDescriptions);
            tableDescription.iconURLsProvider = Objects.requireNonNull(this.iconURLsProvider);
            tableDescription.enableSubRows = this.enableSubRows;
            return tableDescription;
        }
    }
}
