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
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Description of a multi select cell.
 *
 * @author frouene
 */
@Immutable
public final class MultiSelectCellDescription implements ICellDescription {

    private String id;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private BiFunction<VariableManager, Object, List<String>> cellValueProvider;

    private Function<VariableManager, String> cellOptionsIdProvider;

    private Function<VariableManager, String> cellOptionsLabelProvider;

    private BiFunction<VariableManager, Object, List<Object>> cellOptionsProvider;

    private MultiSelectCellDescription() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    @Override
    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    @Override
    public Function<VariableManager, String> getTargetObjectKindProvider() {
        return this.targetObjectKindProvider;
    }

    public BiFunction<VariableManager, Object, List<String>> getCellValueProvider() {
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


    public static Builder newMultiSelectCellDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create a multi select cell widget description.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private BiFunction<VariableManager, Object, List<String>> cellValueProvider;

        private Function<VariableManager, String> cellOptionsIdProvider;

        private Function<VariableManager, String> cellOptionsLabelProvider;

        private BiFunction<VariableManager, Object, List<Object>> cellOptionsProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder targetObjectKindProvider(Function<VariableManager, String> targetObjectKindProvider) {
            this.targetObjectKindProvider = Objects.requireNonNull(targetObjectKindProvider);
            return this;
        }

        public Builder cellValueProvider(BiFunction<VariableManager, Object, List<String>> cellValueProvider) {
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


        public MultiSelectCellDescription build() {
            MultiSelectCellDescription multiSelectCellDescription = new MultiSelectCellDescription();
            multiSelectCellDescription.id = Objects.requireNonNull(this.id);
            multiSelectCellDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            multiSelectCellDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            multiSelectCellDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            multiSelectCellDescription.cellValueProvider = Objects.requireNonNull(this.cellValueProvider);
            multiSelectCellDescription.cellOptionsIdProvider = Objects.requireNonNull(this.cellOptionsIdProvider);
            multiSelectCellDescription.cellOptionsLabelProvider = Objects.requireNonNull(this.cellOptionsLabelProvider);
            multiSelectCellDescription.cellOptionsProvider = Objects.requireNonNull(this.cellOptionsProvider);
            return multiSelectCellDescription;
        }
    }
}
