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

package org.eclipse.sirius.components.forms;

import java.util.Objects;

/**
 * Class representing grid properties for widget.
 *
 * @author lfasani
 */
public record WidgetGridLayout(String gridTemplateColumns, String gridTemplateRows, String labelGridColumn, String labelGridRow, String widgetGridColumn, String widgetGridRow, String gap) {
    public WidgetGridLayout {
        Objects.requireNonNull(gridTemplateColumns);
        Objects.requireNonNull(gridTemplateRows);
        Objects.requireNonNull(labelGridColumn);
        Objects.requireNonNull(labelGridRow);
        Objects.requireNonNull(widgetGridColumn);
        Objects.requireNonNull(widgetGridRow);
        Objects.requireNonNull(gap);
    }

    public static Builder newWidgetGridLayout() {
        return new WidgetGridLayout.Builder();
    }

    /**
     * The builder of the WidgetGridLayout.
     *
     * @author lfdsani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String gridTemplateColumns;

        private String gridTemplateRows;

        private String labelGridColumn;

        private String labelGridRow;

        private String widgetGridColumn;

        private String widgetGridRow;

        private String gap;

        private Builder() {
        }

        public Builder gridTemplateColumns(String gridTemplateColumns) {
            this.gridTemplateColumns = Objects.requireNonNull(gridTemplateColumns);
            return this;
        }

        public Builder gridTemplateRows(String gridTemplateRows) {
            this.gridTemplateRows = Objects.requireNonNull(gridTemplateRows);
            return this;
        }

        public Builder labelGridColumn(String labelGridColumn) {
            this.labelGridColumn = Objects.requireNonNull(labelGridColumn);
            return this;
        }

        public Builder labelGridRow(String labelGridRow) {
            this.labelGridRow = Objects.requireNonNull(labelGridRow);
            return this;
        }

        public Builder widgetGridColumn(String widgetGridColumn) {
            this.widgetGridColumn = Objects.requireNonNull(widgetGridColumn);
            return this;
        }

        public Builder widgetGridRow(String widgetGridRow) {
            this.widgetGridRow = Objects.requireNonNull(widgetGridRow);
            return this;
        }

        public Builder gap(String gap) {
            this.gap = Objects.requireNonNull(gap);
            return this;
        }

        public WidgetGridLayout build() {
            WidgetGridLayout widgetGridLayout = new WidgetGridLayout(this.gridTemplateColumns, this.gridTemplateRows, this.labelGridColumn, this.labelGridRow, this.widgetGridColumn, this.widgetGridRow, this.gap);
            return widgetGridLayout;
        }
    }
}
