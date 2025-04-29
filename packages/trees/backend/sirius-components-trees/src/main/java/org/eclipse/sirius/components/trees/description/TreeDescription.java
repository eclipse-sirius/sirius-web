/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

    /**
     * The variable name used for the id.
     */
    public static final String ID = "id";

    /**
     * The variable name used to store a reference to a tree.
     */
    public static final String TREE = "tree";

    public static final String LABEL = "label";

    private String id;

    private String label;

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> treeItemIdProvider;

    private Function<VariableManager, String> kindProvider;

    private Function<VariableManager, StyledString> labelProvider;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, Object> parentObjectProvider;

    private Function<VariableManager, List<String>> treeItemIconURLsProvider;

    private Function<VariableManager, Boolean> editableProvider;

    private Function<VariableManager, Boolean> deletableProvider;

    private Function<VariableManager, Boolean> selectableProvider;

    private Function<VariableManager, List<?>> elementsProvider;

    private Function<VariableManager, List<?>> childrenProvider;

    private Function<VariableManager, Boolean> hasChildrenProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, IStatus> deleteHandler;

    private BiFunction<VariableManager, String, IStatus> renameHandler;

    private Function<VariableManager, Object> treeItemObjectProvider;

    private Function<VariableManager, StyledString> treeItemLabelProvider;

    private Function<VariableManager, List<String>> iconURLsProvider;

    private Function<VariableManager, VariableManager> renderVariablesProvider;

    private TreeDescription() {
        // Prevent instantiation
    }

    public static Builder newTreeDescription(String id) {
        return new Builder(id);
    }

    public static Builder newTreeDescription(TreeDescription treeDescription) {
        return new Builder(treeDescription);
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

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, Object> getParentObjectProvider() {
        return this.parentObjectProvider;
    }

    public Function<VariableManager, List<String>> getTreeItemIconURLsProvider() {
        return this.treeItemIconURLsProvider;
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

    public Function<VariableManager, Object> getTreeItemObjectProvider() {
        return this.treeItemObjectProvider;
    }

    public Function<VariableManager, StyledString> getTreeItemLabelProvider() {
        return this.treeItemLabelProvider;
    }

    public Function<VariableManager, List<String>> getIconURLsProvider() {
        return this.iconURLsProvider;
    }

    public Function<VariableManager, VariableManager> getRenderVariablesProvider() {
        return this.renderVariablesProvider;
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

        private String id;

        private String label;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> treeItemIdProvider;

        private Function<VariableManager, String> kindProvider;

        private Function<VariableManager, StyledString> labelProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, Object> parentObjectProvider;

        private Function<VariableManager, List<String>> treeItemIconURLsProvider;

        private Function<VariableManager, Boolean> editableProvider;

        private Function<VariableManager, Boolean> deletableProvider;

        private Function<VariableManager, Boolean> selectableProvider = (variableManager) -> true;

        private Function<VariableManager, List<?>> elementsProvider;

        private Function<VariableManager, List<?>> childrenProvider;

        private Function<VariableManager, Boolean> hasChildrenProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, IStatus> deleteHandler;

        private BiFunction<VariableManager, String, IStatus> renameHandler;

        private Function<VariableManager, Object> treeItemObjectProvider;

        private Function<VariableManager, StyledString> treeItemLabelProvider;

        private Function<VariableManager, List<String>> iconURLsProvider;

        private Function<VariableManager, VariableManager> renderVariablesProvider = (vm) -> new VariableManager();

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(TreeDescription treeDescription) {
            this.id = treeDescription.getId();
            this.label = treeDescription.getLabel();
            this.idProvider = treeDescription.getIdProvider();
            this.treeItemIdProvider = treeDescription.getTreeItemIdProvider();
            this.kindProvider = treeDescription.getKindProvider();
            this.labelProvider = treeDescription.getLabelProvider();
            this.targetObjectIdProvider = treeDescription.getTargetObjectIdProvider();
            this.parentObjectProvider = treeDescription.getParentObjectProvider();
            this.treeItemIconURLsProvider = treeDescription.getTreeItemIconURLsProvider();
            this.editableProvider = treeDescription.getEditableProvider();
            this.deletableProvider = treeDescription.getDeletableProvider();
            this.selectableProvider = treeDescription.getSelectableProvider();
            this.elementsProvider = treeDescription.getElementsProvider();
            this.childrenProvider = treeDescription.getChildrenProvider();
            this.hasChildrenProvider = treeDescription.getHasChildrenProvider();
            this.canCreatePredicate = treeDescription.getCanCreatePredicate();
            this.deleteHandler = treeDescription.getDeleteHandler();
            this.renameHandler = treeDescription.getRenameHandler();
            this.treeItemObjectProvider = treeDescription.getTreeItemObjectProvider();
            this.treeItemLabelProvider = treeDescription.getTreeItemLabelProvider();
            this.iconURLsProvider = treeDescription.getIconURLsProvider();
            this.renderVariablesProvider = treeDescription.getRenderVariablesProvider();
        }

        public Builder id(String id) {
            this.id = Objects.requireNonNull(id);
            return this;
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

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder parentObjectProvider(Function<VariableManager, Object> parentObjectProvider) {
            this.parentObjectProvider = Objects.requireNonNull(parentObjectProvider);
            return this;
        }

        public Builder treeItemIconURLsProvider(Function<VariableManager, List<String>> treeItemIconURLsProvider) {
            this.treeItemIconURLsProvider = Objects.requireNonNull(treeItemIconURLsProvider);
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

        public Builder treeItemObjectProvider(Function<VariableManager, Object> treeItemObjectProvider) {
            this.treeItemObjectProvider = Objects.requireNonNull(treeItemObjectProvider);
            return this;
        }

        public Builder treeItemLabelProvider(Function<VariableManager, StyledString> treeItemLabelProvider) {
            this.treeItemLabelProvider = Objects.requireNonNull(treeItemLabelProvider);
            return this;
        }

        public Builder iconURLsProvider(Function<VariableManager, List<String>> iconURLsProvider) {
            this.iconURLsProvider =  Objects.requireNonNull(iconURLsProvider);
            return this;
        }

        public Builder renderVariablesProvider(Function<VariableManager, VariableManager> renderVariablesProvider) {
            this.renderVariablesProvider =  Objects.requireNonNull(renderVariablesProvider);
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
            treeDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            treeDescription.parentObjectProvider = Objects.requireNonNull(this.parentObjectProvider);
            treeDescription.treeItemIconURLsProvider = Objects.requireNonNull(this.treeItemIconURLsProvider);
            treeDescription.editableProvider = Objects.requireNonNull(this.editableProvider);
            treeDescription.deletableProvider = Objects.requireNonNull(this.deletableProvider);
            treeDescription.selectableProvider = Objects.requireNonNull(this.selectableProvider);
            treeDescription.elementsProvider = Objects.requireNonNull(this.elementsProvider);
            treeDescription.childrenProvider = Objects.requireNonNull(this.childrenProvider);
            treeDescription.hasChildrenProvider = Objects.requireNonNull(this.hasChildrenProvider);
            treeDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            treeDescription.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            treeDescription.renameHandler = Objects.requireNonNull(this.renameHandler);
            treeDescription.treeItemObjectProvider = Objects.requireNonNull(this.treeItemObjectProvider);
            treeDescription.treeItemLabelProvider = Objects.requireNonNull(this.treeItemLabelProvider);
            treeDescription.iconURLsProvider = Objects.requireNonNull(this.iconURLsProvider);
            treeDescription.renderVariablesProvider = Objects.requireNonNull(this.renderVariablesProvider);
            return treeDescription;
        }
    }
}
