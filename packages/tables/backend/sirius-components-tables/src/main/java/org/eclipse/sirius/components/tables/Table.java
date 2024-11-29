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

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Root concept of the table representation.
 *
 * @author arichard
 */
@Immutable
public final class Table implements IRepresentation {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Table";

    private String id;

    private String kind;

    private String targetObjectId;

    private String targetObjectKind;

    private String descriptionId;

    private boolean stripeRow;

    private List<Line> lines;

    private List<Column> columns;

    private PaginationData paginationData;

    private String globalFilter;

    private Table() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }

    public boolean isStripeRow() {
        return this.stripeRow;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public PaginationData getPaginationData() {
        return this.paginationData;
    }

    public String getGlobalFilter() {
        return this.globalFilter;
    }

    public static Builder newTable(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, descriptionId: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.descriptionId);
    }

    /**
     * The builder used to create a table.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private final String kind = KIND;

        private String targetObjectId;

        private String targetObjectKind;

        private String descriptionId;

        private boolean stripeRow;

        private List<Line> lines;

        private List<Column> columns;

        private PaginationData paginationData;

        private String globalFilter;

        private Builder(String id) {
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

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder stripeRow(boolean stripeRow) {
            this.stripeRow = stripeRow;
            return this;
        }

        public Builder lines(List<Line> lines) {
            this.lines = Objects.requireNonNull(lines);
            return this;
        }

        public Builder columns(List<Column> columns) {
            this.columns = Objects.requireNonNull(columns);
            return this;
        }

        public Builder paginationData(PaginationData paginationData) {
            this.paginationData = Objects.requireNonNull(paginationData);
            return this;
        }

        public Builder globalFilter(String globalFilter) {
            this.globalFilter = Objects.requireNonNull(globalFilter);
            return this;
        }

        public Table build() {
            Table table = new Table();
            table.id = Objects.requireNonNull(this.id);
            table.kind = Objects.requireNonNull(this.kind);
            table.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            table.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            table.descriptionId = Objects.requireNonNull(this.descriptionId);
            table.stripeRow = this.stripeRow;
            table.lines = Objects.requireNonNull(this.lines);
            table.columns = Objects.requireNonNull(this.columns);
            table.paginationData = Objects.requireNonNull(this.paginationData);
            table.globalFilter = Objects.requireNonNull(this.globalFilter);
            return table;
        }
    }
}
