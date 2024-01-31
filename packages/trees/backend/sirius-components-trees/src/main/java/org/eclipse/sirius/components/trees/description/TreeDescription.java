/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.trees.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a tree representation.
 *
 * @author hmarchadour
 */
@Immutable
public final class TreeDescription implements IRepresentationDescription {

    private String id;

    private String label;

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> treeItemIdProvider;

    private Function<VariableManager, String> kindProvider;

    private Function<VariableManager, StyledString> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, Boolean> editableProvider;

    private Function<VariableManager, Boolean> deletableProvider;

    private Function<VariableManager, Boolean> selectableProvider;

    private Function<VariableManager, List<?>> elementsProvider;

    private Function<VariableManager, List<?>> childrenProvider;

    private Function<VariableManager, Boolean> hasChildrenProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, IStatus> deleteHandler;

    private BiFunction<VariableManager, String, IStatus> renameHandler;

    private TreeDescription() {
        // Prevent instantiation
    }

    public static Builder newTreeDescription(String id) {
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

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getTreeItemIdProvider() {
        return this.treeItemIdProvider;
    }

    public Function<VariableManager, String> getKindProvider() {
        return this.kindProvider;
    }

    public Function<VariableManager, StyledString> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<String>> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public Function<VariableManager, Boolean> getEditableProvider() {
        return this.editableProvider;
    }

    public Function<VariableManager, Boolean> getDeletableProvider() {
        return this.deletableProvider;
    }

    public Function<VariableManager, Boolean> getSelectableProvider() {
        return this.selectableProvider;
    }

    public Function<VariableManager, List<?>> getElementsProvider() {
        return this.elementsProvider;
    }

    public Function<VariableManager, List<?>> getChildrenProvider() {
        return this.childrenProvider;
    }

    public Function<VariableManager, Boolean> getHasChildrenProvider() {
        return this.hasChildrenProvider;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public Function<VariableManager, IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    public BiFunction<VariableManager, String, IStatus> getRenameHandler() {
        return this.renameHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * Builder used to create the tree description.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> treeItemIdProvider;

        private Function<VariableManager, String> kindProvider;

        private Function<VariableManager, StyledString> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider;

        private Function<VariableManager, Boolean> editableProvider;

        private Function<VariableManager, Boolean> deletableProvider;

        private Function<VariableManager, Boolean> selectableProvider = (variableManager) -> true;

        private Function<VariableManager, List<?>> elementsProvider;

        private Function<VariableManager, List<?>> childrenProvider;

        private Function<VariableManager, Boolean> hasChildrenProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, IStatus> deleteHandler;

        private BiFunction<VariableManager, String, IStatus> renameHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
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

        public Builder labelProvider(Function<VariableManager, StyledString> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, List<String>> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
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

        public Builder selectableProvider(Function<VariableManager, Boolean> selectableProvider) {
            this.selectableProvider = Objects.requireNonNull(selectableProvider);
            return this;
        }

        public Builder elementsProvider(Function<VariableManager, List<?>> elementsProvider) {
            this.elementsProvider = Objects.requireNonNull(elementsProvider);
            return this;
        }

        public Builder childrenProvider(Function<VariableManager, List<?>> childrenProvider) {
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

        public Builder deleteHandler(Function<VariableManager, IStatus> deleteHandler) {
            this.deleteHandler = Objects.requireNonNull(deleteHandler);
            return this;
        }

        public Builder renameHandler(BiFunction<VariableManager, String, IStatus> renameHandler) {
            this.renameHandler = Objects.requireNonNull(renameHandler);
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
            treeDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            treeDescription.editableProvider = Objects.requireNonNull(this.editableProvider);
            treeDescription.deletableProvider = Objects.requireNonNull(this.deletableProvider);
            treeDescription.selectableProvider = Objects.requireNonNull(this.selectableProvider);
            treeDescription.elementsProvider = Objects.requireNonNull(this.elementsProvider);
            treeDescription.childrenProvider = Objects.requireNonNull(this.childrenProvider);
            treeDescription.hasChildrenProvider = Objects.requireNonNull(this.hasChildrenProvider);
            treeDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            treeDescription.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            treeDescription.renameHandler = Objects.requireNonNull(this.renameHandler);
            return treeDescription;
        }
    }
}
