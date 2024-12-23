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
package org.eclipse.sirius.components.tables.elements;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.tables.ColumnFilter;
import org.eclipse.sirius.components.tables.PaginationData;

/**
 * Properties of the table element.
 *
 * @author lfasani
 */
public record TableElementProps(String id, String descriptionId, String targetObjectId, String targetObjectKind,
                                PaginationData paginationData, boolean stripeRow, List<Element> children, String globalFilter, List<ColumnFilter> columnFilters) implements IProps {

    public static final String TYPE = "Table";

    public TableElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(children);
        Objects.requireNonNull(paginationData);
        Objects.requireNonNull(globalFilter);
        Objects.requireNonNull(columnFilters);
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newTableElementProps(String id) {
        return new Builder(id);
    }

    /**
     * The builder of the table element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String targetObjectId;

        private String targetObjectKind;

        private String descriptionId;

        private PaginationData paginationData;

        private boolean stripeRow;

        private List<Element> children;

        private String globalFilter;

        private List<ColumnFilter> columnFilters;

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

        public Builder paginationData(PaginationData paginationData) {
            this.paginationData = Objects.requireNonNull(paginationData);
            return this;
        }

        public Builder stripeRow(boolean stripeRow) {
            this.stripeRow = stripeRow;
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
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

        public TableElementProps build() {
            return new TableElementProps(this.id, this.descriptionId, this.targetObjectId, this.targetObjectKind, this.paginationData, this.stripeRow, this.children, this.globalFilter, this.columnFilters);
        }
    }
}
