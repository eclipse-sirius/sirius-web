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

    private String label;

    private List<Line> lines;

    private List<Column> columns;

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

    @Override
    public String getLabel() {
        return this.label;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public static Builder newTable(String id) {
        return new Builder(id);
    }

    public static Builder newTable(Table table) {
        return new Builder(table);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, descriptionId: {3}, label: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.descriptionId, this.label);
    }

    /**
     * The builder used to create a table.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind = KIND;

        private String targetObjectId;

        private String targetObjectKind;

        private String descriptionId;

        private String label;

        private List<Line> lines;

        private List<Column> columns;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Table table) {
            this.id = table.getId();
            this.targetObjectId = table.getTargetObjectId();
            this.descriptionId = table.getDescriptionId();
            this.label = table.getLabel();
            this.lines = table.getLines();
            this.columns = table.getColumns();
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

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
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

        public Table build() {
            Table table = new Table();
            table.id = Objects.requireNonNull(this.id);
            table.kind = Objects.requireNonNull(this.kind);
            table.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            table.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            table.descriptionId = Objects.requireNonNull(this.descriptionId);
            table.label = Objects.requireNonNull(this.label);
            table.lines = Objects.requireNonNull(this.lines);
            table.columns = Objects.requireNonNull(this.columns);
            return table;
        }
    }
}
