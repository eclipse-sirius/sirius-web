/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.trees.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The root concept of the description of a tree representation.
 *
 * @author hmarchadour
 */
@Immutable
@GraphQLObjectType
public final class TreeDescription implements IRepresentationDescription {
    private UUID id;

    private String label;

    private Function<VariableManager, UUID> idProvider;

    private Function<VariableManager, String> treeItemIdProvider;

    private Function<VariableManager, String> kindProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> imageURLProvider;

    private Function<VariableManager, Boolean> editableProvider;

    private Function<VariableManager, Boolean> deletableProvider;

    private Function<VariableManager, List<Object>> elementsProvider;

    private Function<VariableManager, List<Object>> childrenProvider;

    private Function<VariableManager, Boolean> hasChildrenProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private TreeDescription() {
        // Prevent instantiation
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @Override
    @GraphQLField
    @GraphQLNonNull
    public String getLabel() {
        return this.label;
    }

    public Function<VariableManager, UUID> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getTreeItemIdProvider() {
        return this.treeItemIdProvider;
    }

    public Function<VariableManager, String> getKindProvider() {
        return this.kindProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getImageURLProvider() {
        return this.imageURLProvider;
    }

    public Function<VariableManager, Boolean> getEditableProvider() {
        return this.editableProvider;
    }

    public Function<VariableManager, Boolean> getDeletableProvider() {
        return this.deletableProvider;
    }

    public Function<VariableManager, List<Object>> getElementsProvider() {
        return this.elementsProvider;
    }

    public Function<VariableManager, List<Object>> getChildrenProvider() {
        return this.childrenProvider;
    }

    public Function<VariableManager, Boolean> getHasChildrenProvider() {
        return this.hasChildrenProvider;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public static Builder newTreeDescription(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * Builder used to create the tree description.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String label;

        private Function<VariableManager, UUID> idProvider;

        private Function<VariableManager, String> treeItemIdProvider;

        private Function<VariableManager, String> kindProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> imageURLProvider;

        private Function<VariableManager, Boolean> editableProvider;

        private Function<VariableManager, Boolean> deletableProvider;

        private Function<VariableManager, List<Object>> elementsProvider;

        private Function<VariableManager, List<Object>> childrenProvider;

        private Function<VariableManager, Boolean> hasChildrenProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder idProvider(Function<VariableManager, UUID> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder treeItemIdProvider(Function<VariableManager, String> treeItemIdProvider) {
            this.treeItemIdProvider = Objects.requireNonNull(treeItemIdProvider);
            return this;
        }

        public Builder kindProvider(Function<VariableManager, String> kindProvider) {
            this.kindProvider = Objects.requireNonNull(kindProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder imageURLProvider(Function<VariableManager, String> imageURLProvider) {
            this.imageURLProvider = Objects.requireNonNull(imageURLProvider);
            return this;
        }

        public Builder editableProvider(Function<VariableManager, Boolean> editableProvider) {
            this.editableProvider = Objects.requireNonNull(editableProvider);
            return this;
        }

        public Builder deletableProvider(Function<VariableManager, Boolean> deletableProvider) {
            this.deletableProvider = Objects.requireNonNull(deletableProvider);
            return this;
        }

        public Builder elementsProvider(Function<VariableManager, List<Object>> elementsProvider) {
            this.elementsProvider = Objects.requireNonNull(elementsProvider);
            return this;
        }

        public Builder childrenProvider(Function<VariableManager, List<Object>> childrenProvider) {
            this.childrenProvider = Objects.requireNonNull(childrenProvider);
            return this;
        }

        public Builder hasChildrenProvider(Function<VariableManager, Boolean> hasChildrenProvider) {
            this.hasChildrenProvider = Objects.requireNonNull(hasChildrenProvider);
            return this;
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
            return this;
        }

        public TreeDescription build() {
            TreeDescription treeDescription = new TreeDescription();
            treeDescription.id = Objects.requireNonNull(this.id);
            treeDescription.label = Objects.requireNonNull(this.label);
            treeDescription.idProvider = Objects.requireNonNull(this.idProvider);
            treeDescription.treeItemIdProvider = Objects.requireNonNull(this.treeItemIdProvider);
            treeDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            treeDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            treeDescription.imageURLProvider = Objects.requireNonNull(this.imageURLProvider);
            treeDescription.editableProvider = Objects.requireNonNull(this.editableProvider);
            treeDescription.deletableProvider = Objects.requireNonNull(this.deletableProvider);
            treeDescription.elementsProvider = Objects.requireNonNull(this.elementsProvider);
            treeDescription.childrenProvider = Objects.requireNonNull(this.childrenProvider);
            treeDescription.hasChildrenProvider = Objects.requireNonNull(this.hasChildrenProvider);
            treeDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            return treeDescription;
        }
    }
}
