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
package org.eclipse.sirius.components.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

/**
 * The root concept of the description of a table representation.
 *
 * @author lfasani
 */
@Immutable
public final class TableWidgetDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private TableDescription tableDescription;

    private TableWidgetDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<String>> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public TableDescription getTableDescription() {
        return this.tableDescription;
    }

    public static Builder newTableWidgetDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }


    /**
     * Builder used to create the table description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

        private TableDescription tableDescription;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public TableWidgetDescription.Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public TableWidgetDescription.Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public TableWidgetDescription.Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public TableWidgetDescription.Builder isReadOnlyProvider(Function<VariableManager, Boolean> isReadOnlyProvider) {
            this.isReadOnlyProvider = Objects.requireNonNull(isReadOnlyProvider);
            return this;
        }

        public TableWidgetDescription.Builder diagnosticsProvider(Function<VariableManager, List<?>> diagnosticsProvider) {
            this.diagnosticsProvider = Objects.requireNonNull(diagnosticsProvider);
            return this;
        }

        public TableWidgetDescription.Builder iconURLProvider(Function<VariableManager, List<String>> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public TableWidgetDescription.Builder kindProvider(Function<Object, String> kindProvider) {
            this.kindProvider = Objects.requireNonNull(kindProvider);
            return this;
        }

        public TableWidgetDescription.Builder messageProvider(Function<Object, String> messageProvider) {
            this.messageProvider = Objects.requireNonNull(messageProvider);
            return this;
        }

        public TableWidgetDescription.Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public TableWidgetDescription.Builder tableDescription(TableDescription tableDescription) {
            this.tableDescription = Objects.requireNonNull(tableDescription);
            return this;
        }

        public TableWidgetDescription build() {
            TableWidgetDescription tableWidgetDescription = new TableWidgetDescription();
            tableWidgetDescription.id = Objects.requireNonNull(this.id);
            tableWidgetDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            tableWidgetDescription.idProvider = Objects.requireNonNull(this.idProvider);
            tableWidgetDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            tableWidgetDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            tableWidgetDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            tableWidgetDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            tableWidgetDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            tableWidgetDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            tableWidgetDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            tableWidgetDescription.tableDescription = Objects.requireNonNull(this.tableDescription);
            return tableWidgetDescription;
        }
    }
}
