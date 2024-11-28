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
 * Description of an icon label cell.
 *
 * @author frouene
 */
@Immutable
public final class IconLabelCellDescription implements ICellDescription {

    private String id;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private BiFunction<VariableManager, Object, String> cellValueProvider;

    private BiFunction<VariableManager, Object, List<String>> cellIconURLsProvider;

    private IconLabelCellDescription() {
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

    public BiFunction<VariableManager, Object, List<String>> getCellIconURLsProvider() {
        return this.cellIconURLsProvider;
    }

    public static Builder newIconLabelCellDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create an icon label cell widget description.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private BiFunction<VariableManager, Object, String> cellValueProvider;

        private BiFunction<VariableManager, Object, List<String>> cellIconURLsProvider;


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

        public Builder cellIconURLsProvider(BiFunction<VariableManager, Object, List<String>> cellIconURLsProvider) {
            this.cellIconURLsProvider = Objects.requireNonNull(cellIconURLsProvider);
            return this;
        }


        public IconLabelCellDescription build() {
            IconLabelCellDescription iconLabelCellDescription = new IconLabelCellDescription();
            iconLabelCellDescription.id = Objects.requireNonNull(this.id);
            iconLabelCellDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            iconLabelCellDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            iconLabelCellDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            iconLabelCellDescription.cellValueProvider = Objects.requireNonNull(this.cellValueProvider);
            iconLabelCellDescription.cellIconURLsProvider = Objects.requireNonNull(this.cellIconURLsProvider);
            return iconLabelCellDescription;
        }
    }

}
