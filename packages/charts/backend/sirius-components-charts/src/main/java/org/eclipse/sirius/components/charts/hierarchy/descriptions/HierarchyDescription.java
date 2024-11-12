/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.charts.hierarchy.descriptions;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Description of the hierarchy representation.
 *
 * @author sbegaudeau
 * @author Jerome Gout
 */
@Immutable
public final class HierarchyDescription implements IRepresentationDescription {
    public static final String LABEL = "label";

    private String id;

    private String label;

    private String kind;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<Object>> childSemanticElementsProvider;

    private Function<VariableManager, List<String>> iconURLsProvider;

    private HierarchyDescription() {
        // Prevent instantiation
    }

    public static Builder newHierarchyDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<Object>> getChildSemanticElementsProvider() {
        return this.childSemanticElementsProvider;
    }

    public Function<VariableManager, List<String>> getIconURLsProvider() {
        return this.iconURLsProvider;
    }

    /**
     * Builder used to create the hierarchy description.
     *
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private String kind;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<Object>> childSemanticElementsProvider;

        private Function<VariableManager, List<String>> iconURLsProvider;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder childSemanticElementsProvider(Function<VariableManager, List<Object>> childSemanticElementsProvider) {
            this.childSemanticElementsProvider = Objects.requireNonNull(childSemanticElementsProvider);
            return this;
        }

        public Builder iconURLsProvider(Function<VariableManager, List<String>> iconURLsProvider) {
            this.iconURLsProvider = Objects.requireNonNull(iconURLsProvider);
            return this;
        }

        public HierarchyDescription build() {
            HierarchyDescription hierarchyDescription = new HierarchyDescription();
            hierarchyDescription.id = Objects.requireNonNull(this.id);
            hierarchyDescription.kind = Objects.requireNonNull(this.kind);
            hierarchyDescription.label = Objects.requireNonNull(this.label);
            hierarchyDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            hierarchyDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            hierarchyDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            hierarchyDescription.childSemanticElementsProvider = Objects.requireNonNull(this.childSemanticElementsProvider);
            hierarchyDescription.iconURLsProvider =  Objects.requireNonNull(this.iconURLsProvider);
            return hierarchyDescription;
        }
    }
}
