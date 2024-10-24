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

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.tables.Table;

/**
 * Root concept of the table widget.
 *
 * @author lfasani
 */
@Immutable
public final class TableWidget extends AbstractWidget {

    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=Table";

    private Table table;

    private TableWidget() {
        // Prevent instantiation
    }

    public Table getTable() {
        return this.table;
    }

    public static Builder newTableWidget(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.label);
    }
    
    /**
     * The builder used to create the table widget.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL = List.of();

        private List<Diagnostic> diagnostics;

        private Supplier<String> helpTextProvider;

        private boolean readOnly;

        private Table table;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public TableWidget.Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public TableWidget.Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public TableWidget.Builder diagnostics(List<Diagnostic> diagnostics) {
            this.diagnostics = Objects.requireNonNull(diagnostics);
            return this;
        }

        public TableWidget.Builder helpTextProvider(Supplier<String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public TableWidget.Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder table(Table table) {
            this.table = Objects.requireNonNull(table);
            return this;
        }

        public TableWidget build() {
            TableWidget table = new TableWidget();
            table.id = Objects.requireNonNull(this.id);
            table.label = Objects.requireNonNull(this.label);
            table.iconURL = Objects.requireNonNull(this.iconURL);
            table.diagnostics = Objects.requireNonNull(this.diagnostics);
            table.table = Objects.requireNonNull(this.table);
            table.helpTextProvider = this.helpTextProvider; // Optional on purpose
            table.readOnly = this.readOnly;
            return table;
        }
    }
}
