/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Description of a textarea cell.
 *
 * @author Jerome Gout
 */
@Immutable
public final class TextareaCellDescription implements ICellDescription {

    private String id;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private BiFunction<VariableManager, Object, String> cellValueProvider;

    private BiFunction<VariableManager, Object, String> cellTooltipValueProvider;

    private TextareaCellDescription() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
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

    public BiFunction<VariableManager, Object, String> getCellValueProvider() {
        return this.cellValueProvider;
    }

    public BiFunction<VariableManager, Object, String> getCellTooltipValueProvider() {
        return this.cellTooltipValueProvider;
    }

    public static Builder newTextareaCellDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create a textarea cell widget description.
     *
     * @author Jerome Gout
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private BiFunction<VariableManager, Object, String> cellValueProvider;

        private BiFunction<VariableManager, Object, String> cellTooltipValueProvider;

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

        public Builder cellValueProvider(BiFunction<VariableManager, Object, String> cellValueProvider) {
            this.cellValueProvider = Objects.requireNonNull(cellValueProvider);
            return this;
        }

        public Builder cellTooltipValueProvider(BiFunction<VariableManager, Object, String> cellTooltipValueProvider) {
            this.cellTooltipValueProvider = Objects.requireNonNull(cellTooltipValueProvider);
            return this;
        }


        public TextareaCellDescription build() {
            TextareaCellDescription textareaCellDescription = new TextareaCellDescription();
            textareaCellDescription.id = Objects.requireNonNull(this.id);
            textareaCellDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            textareaCellDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            textareaCellDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            textareaCellDescription.cellValueProvider = Objects.requireNonNull(this.cellValueProvider);
            textareaCellDescription.cellTooltipValueProvider = Objects.requireNonNull(this.cellTooltipValueProvider);
            return textareaCellDescription;
        }
    }

}
