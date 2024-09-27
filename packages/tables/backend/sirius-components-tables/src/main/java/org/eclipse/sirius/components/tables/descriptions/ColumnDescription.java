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
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 *
 * The description of the column.
 *
 * @author arichard
 */
@Immutable
public final class ColumnDescription {

    private UUID id;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> featureNameProvider;

    private BiFunction<VariableManager, String, String> cellTypeProvider;

    private BiFunction<VariableManager, String, Object> cellValueProvider;

    private Function<VariableManager, String> cellOptionsIdProvider;

    private Function<VariableManager, String> cellOptionsLabelProvider;

    private BiFunction<VariableManager, String, List<Object>> cellOptionsProvider;

    private ColumnDescription() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getFeatureNameProvider() {
        return this.featureNameProvider;
    }

    public BiFunction<VariableManager, String, String> getCellTypeProvider() {
        return this.cellTypeProvider;
    }

    public BiFunction<VariableManager, String, Object> getCellValueProvider() {
        return this.cellValueProvider;
    }

    public Function<VariableManager, String> getCellOptionsIdProvider() {
        return this.cellOptionsIdProvider;
    }

    public Function<VariableManager, String> getCellOptionsLabelProvider() {
        return this.cellOptionsLabelProvider;
    }

    public BiFunction<VariableManager, String, List<Object>> getCellOptionsProvider() {
        return this.cellOptionsProvider;
    }

    public static Builder newColumnDescription(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create the column description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> featureNameProvider;

        private BiFunction<VariableManager, String, String> cellTypeProvider;

        private BiFunction<VariableManager, String, Object> cellValueProvider;

        private Function<VariableManager, String> cellOptionsIdProvider;

        private Function<VariableManager, String> cellOptionsLabelProvider;

        private BiFunction<VariableManager, String, List<Object>> cellOptionsProvider;

        public Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder featureNameProvider(Function<VariableManager, String> featureNameProvider) {
            this.featureNameProvider = Objects.requireNonNull(featureNameProvider);
            return this;
        }

        public Builder cellTypeProvider(BiFunction<VariableManager, String, String> cellTypeProvider) {
            this.cellTypeProvider = Objects.requireNonNull(cellTypeProvider);
            return this;
        }

        public Builder cellValueProvider(BiFunction<VariableManager, String, Object> cellValueProvider) {
            this.cellValueProvider = Objects.requireNonNull(cellValueProvider);
            return this;
        }

        public Builder cellOptionsIdProvider(Function<VariableManager, String> cellOptionsIdProvider) {
            this.cellOptionsIdProvider = cellOptionsIdProvider;
            return this;
        }

        public Builder cellOptionsLabelProvider(Function<VariableManager, String> cellOptionsLabelProvider) {
            this.cellOptionsLabelProvider = cellOptionsLabelProvider;
            return this;
        }

        public Builder cellOptionsProvider(BiFunction<VariableManager, String, List<Object>> cellOptionsProvider) {
            this.cellOptionsProvider = cellOptionsProvider;
            return this;
        }

        public ColumnDescription build() {
            ColumnDescription columnDescription = new ColumnDescription();
            columnDescription.id = Objects.requireNonNull(this.id);
            columnDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            columnDescription.featureNameProvider = Objects.requireNonNull(this.featureNameProvider);
            columnDescription.cellTypeProvider = Objects.requireNonNull(this.cellTypeProvider);
            columnDescription.cellValueProvider = Objects.requireNonNull(this.cellValueProvider);
            columnDescription.cellOptionsIdProvider = this.cellOptionsIdProvider;
            columnDescription.cellOptionsLabelProvider = this.cellOptionsLabelProvider;
            columnDescription.cellOptionsProvider = this.cellOptionsProvider;
            return columnDescription;
        }
    }
}
