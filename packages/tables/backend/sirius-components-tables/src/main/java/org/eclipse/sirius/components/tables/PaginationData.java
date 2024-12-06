/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

/**
 * Pagination data for a table.
 *
 * @author frouene
 */
public record PaginationData(boolean hasPreviousPage, boolean hasNextPage, int totalRowCount) {


    public static Builder newPagination() {
        return new Builder();
    }

    /**
     * The builder used to create a paginationData.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private boolean hasPreviousPage;

        private boolean hasNextPage;

        private int totalRowCount;

        public Builder hasPreviousPage(boolean hasPreviousPage) {
            this.hasPreviousPage = hasPreviousPage;
            return this;
        }

        public Builder hasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
            return this;
        }

        public Builder totalRowCount(int totalRowCount) {
            this.totalRowCount = totalRowCount;
            return this;
        }

        public PaginationData build() {
            return new PaginationData(this.hasPreviousPage, this.hasNextPage, this.totalRowCount);
        }
    }
}
