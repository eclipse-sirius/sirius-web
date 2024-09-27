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
package org.eclipse.sirius.components.tables.components;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the Select-based cell component.
 *
 * @author arichard
 */
public final class MultiSelectCellComponentProps implements IProps {

    private VariableManager variableManager;

    private BiFunction<VariableManager, String, Object> cellValuesProvider;

    private String featureName;

    private UUID cellId;

    private UUID parentLineId;

    private UUID columnId;

    private BiFunction<VariableManager, String, List<Object>> cellOptionsProvider;

    private Function<VariableManager, String> cellOptionIdProvider;

    private Function<VariableManager, String> cellOptionLabelProvider;

    private MultiSelectCellComponentProps() {
        // Prevent instantiation
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public BiFunction<VariableManager, String, Object> getCellValuesProvider() {
        return this.cellValuesProvider;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public UUID getCellId() {
        return this.cellId;
    }

    public UUID getParentLineId() {
        return this.parentLineId;
    }

    public UUID getColumnId() {
        return this.columnId;
    }

    public BiFunction<VariableManager, String, List<Object>> getCellOptionsProvider() {
        return this.cellOptionsProvider;
    }

    public Function<VariableManager, String> getCellOptionIdProvider() {
        return this.cellOptionIdProvider;
    }

    public Function<VariableManager, String> getCellOptionLabelProvider() {
        return this.cellOptionLabelProvider;
    }

    public static Builder newMultiSelectCellComponentProps() {
        return new Builder();
    }

    /**
     * The builder of the cell element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private VariableManager variableManager;

        private BiFunction<VariableManager, String, Object> cellValuesProvider;

        private String featureName;

        private UUID cellId;

        private UUID parentLineId;

        private UUID columnId;

        private BiFunction<VariableManager, String, List<Object>> cellOptionsProvider;

        private Function<VariableManager, String> cellOptionIdProvider;

        private Function<VariableManager, String> cellOptionLabelProvider;

        private Builder() {
        }

        public Builder variableManager(VariableManager variableManager) {
            this.variableManager = Objects.requireNonNull(variableManager);
            return this;
        }

        public Builder cellValuesProvider(BiFunction<VariableManager, String, Object> cellValuesProvider) {
            this.cellValuesProvider = Objects.requireNonNull(cellValuesProvider);
            return this;
        }

        public Builder featureName(String featureName) {
            this.featureName = Objects.requireNonNull(featureName);
            return this;
        }

        public Builder cellId(UUID cellId) {
            this.cellId = Objects.requireNonNull(cellId);
            return this;
        }

        public Builder parentLineId(UUID parentLineId) {
            this.parentLineId = Objects.requireNonNull(parentLineId);
            return this;
        }

        public Builder columnId(UUID columnId) {
            this.columnId = Objects.requireNonNull(columnId);
            return this;
        }

        public Builder cellOptionsProvider(BiFunction<VariableManager, String, List<Object>> cellOptionsProvider) {
            this.cellOptionsProvider = Objects.requireNonNull(cellOptionsProvider);
            return this;
        }

        public Builder cellOptionIdProvider(Function<VariableManager, String> cellOptionIdProvider) {
            this.cellOptionIdProvider = Objects.requireNonNull(cellOptionIdProvider);
            return this;
        }

        public Builder cellOptionLabelProvider(Function<VariableManager, String> cellOptionLabelProvider) {
            this.cellOptionLabelProvider = Objects.requireNonNull(cellOptionLabelProvider);
            return this;
        }

        public MultiSelectCellComponentProps build() {
            MultiSelectCellComponentProps cellComponentProps = new MultiSelectCellComponentProps();
            cellComponentProps.variableManager = Objects.requireNonNull(this.variableManager);
            cellComponentProps.cellValuesProvider = Objects.requireNonNull(this.cellValuesProvider);
            cellComponentProps.featureName = Objects.requireNonNull(this.featureName);
            cellComponentProps.cellId = Objects.requireNonNull(this.cellId);
            cellComponentProps.parentLineId = Objects.requireNonNull(this.parentLineId);
            cellComponentProps.columnId = Objects.requireNonNull(this.columnId);
            cellComponentProps.cellOptionsProvider = Objects.requireNonNull(this.cellOptionsProvider);
            cellComponentProps.cellOptionIdProvider = Objects.requireNonNull(this.cellOptionIdProvider);
            cellComponentProps.cellOptionLabelProvider = Objects.requireNonNull(this.cellOptionLabelProvider);
            return cellComponentProps;
        }
    }
}
