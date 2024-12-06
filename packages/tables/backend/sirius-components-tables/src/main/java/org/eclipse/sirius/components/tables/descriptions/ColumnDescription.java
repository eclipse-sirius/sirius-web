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
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the column.
 *
 * @author arichard
 * @author lfasani
 */
@Immutable
public final class ColumnDescription {

    public static final String COLUMN_TARGET_OBJECT = "columTargetObject";

    public static final String COLUMN_TARGET_OBJECT_ID = "columTargetObjectId";

    private String id;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private Function<VariableManager, String> headerLabelProvider;

    private Function<VariableManager, List<String>> headerIconURLsProvider;

    private Function<VariableManager, String> headerIndexLabelProvider;

    private Function<VariableManager, List<Object>> semanticElementsProvider;

    private Predicate<VariableManager> shouldRenderPredicate;

    private Function<VariableManager, Integer> initialWidthProvider;

    private Predicate<VariableManager> isResizablePredicate;

    private ColumnDescription() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getHeaderLabelProvider() {
        return this.headerLabelProvider;
    }

    public Function<VariableManager, List<String>> getHeaderIconURLsProvider() {
        return this.headerIconURLsProvider;
    }

    public Function<VariableManager, String> getHeaderIndexLabelProvider() {
        return this.headerIndexLabelProvider;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, String> getTargetObjectKindProvider() {
        return this.targetObjectKindProvider;
    }

    public Function<VariableManager, List<Object>> getSemanticElementsProvider() {
        return this.semanticElementsProvider;
    }

    public Predicate<VariableManager> getShouldRenderPredicate() {
        return this.shouldRenderPredicate;
    }

    public Function<VariableManager, Integer> getInitialWidthProvider() {
        return this.initialWidthProvider;
    }

    public Predicate<VariableManager> getIsResizablePredicate() {
        return this.isResizablePredicate;
    }

    public static Builder newColumnDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create the column description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private Function<VariableManager, String> headerLabelProvider;

        private Function<VariableManager, List<String>> headerIconURLsProvider;

        private Function<VariableManager, String> headerIndexLabelProvider;

        private Function<VariableManager, List<Object>> semanticElementsProvider;

        private Predicate<VariableManager> shouldRenderPredicate = variableManager -> true;

        private Function<VariableManager, Integer> initialWidthProvider;

        private Predicate<VariableManager> isResizablePredicate;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder headerLabelProvider(Function<VariableManager, String> headerLabelProvider) {
            this.headerLabelProvider = Objects.requireNonNull(headerLabelProvider);
            return this;
        }

        public Builder headerIconURLsProvider(Function<VariableManager, List<String>> headerIconURLsProvider) {
            this.headerIconURLsProvider = Objects.requireNonNull(headerIconURLsProvider);
            return this;
        }

        public Builder headerIndexLabelProvider(Function<VariableManager, String> headerIndexLabelProvider) {
            this.headerIndexLabelProvider = Objects.requireNonNull(headerIndexLabelProvider);
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

        public Builder semanticElementsProvider(Function<VariableManager, List<Object>> semanticElementsProvider) {
            this.semanticElementsProvider = Objects.requireNonNull(semanticElementsProvider);
            return this;
        }

        public Builder shouldRenderPredicate(Predicate<VariableManager> shouldRenderPredicate) {
            this.shouldRenderPredicate = Objects.requireNonNull(shouldRenderPredicate);
            return this;
        }

        public Builder initialWidthProvider(Function<VariableManager, Integer> initialWidthProvider) {
            this.initialWidthProvider = Objects.requireNonNull(initialWidthProvider);
            return this;
        }

        public Builder isResizablePredicate(Predicate<VariableManager> isResizablePredicate) {
            this.isResizablePredicate = Objects.requireNonNull(isResizablePredicate);
            return this;
        }

        public ColumnDescription build() {
            ColumnDescription columnDescription = new ColumnDescription();
            columnDescription.id = Objects.requireNonNull(this.id);
            columnDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            columnDescription.headerLabelProvider = Objects.requireNonNull(this.headerLabelProvider);
            columnDescription.headerIconURLsProvider = Objects.requireNonNull(this.headerIconURLsProvider);
            columnDescription.headerIndexLabelProvider = Objects.requireNonNull(this.headerIndexLabelProvider);
            columnDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            columnDescription.semanticElementsProvider = Objects.requireNonNull(this.semanticElementsProvider);
            columnDescription.shouldRenderPredicate = Objects.requireNonNull(this.shouldRenderPredicate);
            columnDescription.initialWidthProvider = Objects.requireNonNull(this.initialWidthProvider);
            columnDescription.isResizablePredicate = Objects.requireNonNull(this.isResizablePredicate);
            return columnDescription;
        }
    }
}
