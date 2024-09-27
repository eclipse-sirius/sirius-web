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
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of a cell.
 *
 * @author lfasani
 */
@Immutable
public final class CellDescription {
    public static final String LABEL = "label";

    private String id;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private BiFunction<VariableManager, Object, String> cellTypeProvider;

    private BiFunction<VariableManager, Object, Object> cellValueProvider;

    private Function<VariableManager, String> cellOptionsIdProvider;

    private Function<VariableManager, String> cellOptionsLabelProvider;

    private BiFunction<VariableManager, Object, List<Object>> cellOptionsProvider;

    private BiFunction<VariableManager, Object, IStatus> newCellValueHandler;

    private CellDescription() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, String> getTargetObjectKindProvider() {
        return this.targetObjectKindProvider;
    }

    public BiFunction<VariableManager, Object, String> getCellTypeProvider() {
        return this.cellTypeProvider;
    }

    public BiFunction<VariableManager, Object, Object> getCellValueProvider() {
        return this.cellValueProvider;
    }

    public Function<VariableManager, String> getCellOptionsIdProvider() {
        return this.cellOptionsIdProvider;
    }

    public Function<VariableManager, String> getCellOptionsLabelProvider() {
        return this.cellOptionsLabelProvider;
    }

    public BiFunction<VariableManager, Object, List<Object>> getCellOptionsProvider() {
        return this.cellOptionsProvider;
    }

    public BiFunction<VariableManager, Object, IStatus> getNewCellValueHandler() {
        return this.newCellValueHandler;
    }

    public static Builder newCellDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the table description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private BiFunction<VariableManager, Object, String> cellTypeProvider;

        private BiFunction<VariableManager, Object, Object> cellValueProvider;

        private Function<VariableManager, String> cellOptionsIdProvider;

        private Function<VariableManager, String> cellOptionsLabelProvider;

        private BiFunction<VariableManager, Object, List<Object>> cellOptionsProvider;

        private BiFunction<VariableManager, Object, IStatus> newCellValueHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder targetObjectKindProvider(Function<VariableManager, String> targetObjectKindProvider) {
            this.targetObjectKindProvider = Objects.requireNonNull(targetObjectKindProvider);
            return this;
        }

        public Builder cellTypeProvider(BiFunction<VariableManager, Object, String> cellTypeProvider) {
            this.cellTypeProvider = Objects.requireNonNull(cellTypeProvider);
            return this;
        }

        public Builder cellValueProvider(BiFunction<VariableManager, Object, Object> cellValueProvider) {
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

        public Builder cellOptionsProvider(BiFunction<VariableManager, Object, List<Object>> cellOptionsProvider) {
            this.cellOptionsProvider = cellOptionsProvider;
            return this;
        }

        public Builder newCellValueHandler(BiFunction<VariableManager, Object, IStatus> newCellValueHandler) {
            this.newCellValueHandler = Objects.requireNonNull(newCellValueHandler);
            return this;
        }

        public CellDescription build() {
            CellDescription cellDescription = new CellDescription();
            cellDescription.id = Objects.requireNonNull(this.id);
            cellDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            cellDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            cellDescription.cellTypeProvider = Objects.requireNonNull(this.cellTypeProvider);
            cellDescription.cellValueProvider = Objects.requireNonNull(this.cellValueProvider);
            cellDescription.cellOptionsIdProvider = this.cellOptionsIdProvider;
            cellDescription.cellOptionsLabelProvider = this.cellOptionsLabelProvider;
            cellDescription.cellOptionsProvider = this.cellOptionsProvider;
            cellDescription.newCellValueHandler = Objects.requireNonNull(this.newCellValueHandler);
            return cellDescription;
        }
    }
}
