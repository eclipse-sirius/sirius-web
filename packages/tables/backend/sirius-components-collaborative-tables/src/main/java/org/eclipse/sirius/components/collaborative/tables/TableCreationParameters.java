/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.ColumnSort;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

/**
 * This class is used because creating tables requires sending at once multiple parameters.
 *
 * @author frouene
 */
@Immutable
public final class TableCreationParameters {

    private String id;

    private TableDescription tableDescription;

    private IEditingContext editingContext;

    private Object targetObject;

    private CursorBasedPaginationData cursorBasedPaginationData;

    private String globalFilter;

    private List<ColumnFilter> columnFilters;

    private List<String> expanded;

    private List<String> activeRowFilterIds;

    private List<ColumnSort> columnSort;

    private TableCreationParameters() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public TableDescription getTableDescription() {
        return this.tableDescription;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public CursorBasedPaginationData getCursorBasedPaginationData() {
        return this.cursorBasedPaginationData;
    }

    public String getGlobalFilter() {
        return this.globalFilter;
    }

    public List<ColumnFilter> getColumnFilters() {
        return this.columnFilters;
    }

    public List<String> getExpanded() {
        return this.expanded;
    }

    public List<String> getActiveRowFilterIds() {
        return this.activeRowFilterIds;
    }

    public List<ColumnSort> getColumnSort() {
        return this.columnSort;
    }

    public static Builder newTableCreationParameters(String id) {
        return new Builder(id);
    }

    /**
     * The builder of the table creation parameters.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private TableDescription tableDescription;

        private IEditingContext editingContext;

        private Object targetObject;

        private CursorBasedPaginationData cursorBasedPaginationData;

        private String globalFilter;

        private List<ColumnFilter> columnFilters;

        private List<String> expanded;

        private List<String> activeRowFilterIds;

        private List<ColumnSort> columnSort;

        private Builder(String id) {
            this.id = id;
        }

        public Builder tableDescription(TableDescription tableDescription) {
            this.tableDescription = Objects.requireNonNull(tableDescription);
            return this;
        }

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public Builder targetObject(Object targetObject) {
            this.targetObject = Objects.requireNonNull(targetObject);
            return this;
        }

        public Builder cursorBasedPaginationData(CursorBasedPaginationData cursorBasedPaginationData) {
            this.cursorBasedPaginationData = Objects.requireNonNull(cursorBasedPaginationData);
            return this;
        }

        public Builder globalFilter(String globalFilter) {
            this.globalFilter = Objects.requireNonNull(globalFilter);
            return this;
        }

        public Builder columnFilters(List<ColumnFilter> columnFilters) {
            this.columnFilters = Objects.requireNonNull(columnFilters);
            return this;
        }

        public Builder expanded(List<String> expanded) {
            this.expanded = Objects.requireNonNull(expanded);
            return this;
        }

        public Builder activeRowFilterIds(List<String> activeRowFilterIds) {
            this.activeRowFilterIds = Objects.requireNonNull(activeRowFilterIds);
            return this;
        }

        public Builder columnSort(List<ColumnSort> columnSort) {
            this.columnSort = Objects.requireNonNull(columnSort);
            return this;
        }

        public TableCreationParameters build() {
            TableCreationParameters tableCreationParameters = new TableCreationParameters();
            tableCreationParameters.id = Objects.requireNonNull(this.id);
            tableCreationParameters.tableDescription = Objects.requireNonNull(this.tableDescription);
            tableCreationParameters.editingContext = Objects.requireNonNull(this.editingContext);
            tableCreationParameters.cursorBasedPaginationData = Objects.requireNonNull(this.cursorBasedPaginationData);
            tableCreationParameters.targetObject = Objects.requireNonNull(this.targetObject);
            tableCreationParameters.globalFilter = Objects.requireNonNull(this.globalFilter);
            tableCreationParameters.columnFilters = Objects.requireNonNull(this.columnFilters);
            tableCreationParameters.expanded = Objects.requireNonNull(this.expanded);
            tableCreationParameters.activeRowFilterIds = Objects.requireNonNull(this.activeRowFilterIds);
            tableCreationParameters.columnSort = Objects.requireNonNull(this.columnSort);
            return tableCreationParameters;
        }
    }
}
