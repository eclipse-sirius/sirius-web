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
package org.eclipse.sirius.components.forms.description;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.text.MessageFormat;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the tree widget.
 *
 * @author pcdavid
 */
@Immutable
public final class TreeDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private Function<VariableManager, String> nodeIdProvider;

    private Function<VariableManager, String> nodeLabelProvider;

    private Function<VariableManager, String> nodeKindProvider;

    private Function<VariableManager, List<String>> nodeIconURLProvider;

    private Function<VariableManager, List<List<String>>> nodeEndIconsURLProvider;

    private Function<VariableManager, Boolean> nodeSelectableProvider;

    private Function<VariableManager, List<?>> childrenProvider;

    private Function<VariableManager, List<String>> expandedNodeIdsProvider;

    private Function<VariableManager, Boolean> isCheckableProvider;

    private Function<VariableManager, Boolean> checkedValueProvider;

    private BiFunction<VariableManager, Boolean, IStatus> newCheckedValueHandler;

    private TreeDescription() {
        // Prevent instantiation
    }

    public static Builder newTreeDescription(String id) {
        return new Builder(id);
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<String>> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public Function<VariableManager, List<?>> getChildrenProvider() {
        return this.childrenProvider;
    }

    public Function<VariableManager, String> getNodeIdProvider() {
        return this.nodeIdProvider;
    }

    public Function<VariableManager, String> getNodeLabelProvider() {
        return this.nodeLabelProvider;
    }

    public Function<VariableManager, String> getNodeKindProvider() {
        return this.nodeKindProvider;
    }

    public Function<VariableManager, List<String>> getNodeIconURLProvider() {
        return this.nodeIconURLProvider;
    }

    public Function<VariableManager, Boolean> getIsCheckableProvider() {
        return this.isCheckableProvider;
    }

    public Function<VariableManager, Boolean> getCheckedValueProvider() {
        return this.checkedValueProvider;
    }

    public BiFunction<VariableManager, Boolean, IStatus> getNewCheckedValueHandler() {
        return this.newCheckedValueHandler;
    }

    public Function<VariableManager, List<List<String>>> getNodeEndIconsURLProvider() {
        return this.nodeEndIconsURLProvider;
    }

    public Function<VariableManager, Boolean> getNodeSelectableProvider() {
        return this.nodeSelectableProvider;
    }

    public Function<VariableManager, List<String>> getExpandedNodeIdsProvider() {
        return this.expandedNodeIdsProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the list description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<String>> iconURLProvider = variableManager -> List.of();

        private Function<VariableManager, List<?>> childrenProvider;

        private Function<VariableManager, String> nodeIdProvider;

        private Function<VariableManager, String> nodeLabelProvider;

        private Function<VariableManager, List<List<String>>> nodeEndIconsURLProvider;

        private Function<VariableManager, String> nodeKindProvider;

        private Function<VariableManager, List<String>> nodeIconURLProvider;

        private Function<VariableManager, Boolean> nodeSelectableProvider;

        private Function<VariableManager, List<String>> expandedNodeIdsProvider;

        private Function<VariableManager, Boolean> isCheckableProvider;

        private Function<VariableManager, Boolean> checkedValueProvider;

        private BiFunction<VariableManager, Boolean, IStatus> newCheckedValueHandler;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;
        
        private Function<VariableManager, String> helpTextProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
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

        public Builder iconURLProvider(Function<VariableManager, List<String>> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public Builder childrenProvider(Function<VariableManager, List<?>> childrenProvider) {
            this.childrenProvider = Objects.requireNonNull(childrenProvider);
            return this;
        }

        public Builder nodeIdProvider(Function<VariableManager, String> nodeIdProvider) {
            this.nodeIdProvider = Objects.requireNonNull(nodeIdProvider);
            return this;
        }

        public Builder nodeLabelProvider(Function<VariableManager, String> nodeLabelProvider) {
            this.nodeLabelProvider = Objects.requireNonNull(nodeLabelProvider);
            return this;
        }

        public Builder nodeKindProvider(Function<VariableManager, String> nodeKindProvider) {
            this.nodeKindProvider = Objects.requireNonNull(nodeKindProvider);
            return this;
        }

        public Builder nodeIconURLProvider(Function<VariableManager, List<String>> nodeIconURLProvider) {
            this.nodeIconURLProvider = Objects.requireNonNull(nodeIconURLProvider);
            return this;
        }

        public Builder nodeEndIconsURLProvider(Function<VariableManager, List<List<String>>> nodeEndIconsURLProvider) {
            this.nodeEndIconsURLProvider = Objects.requireNonNull(nodeEndIconsURLProvider);
            return this;
        }

        public Builder nodeSelectableProvider(Function<VariableManager, Boolean> nodeSelectableProvider) {
            this.nodeSelectableProvider = Objects.requireNonNull(nodeSelectableProvider);
            return this;
        }

        public Builder expandedNodeIdsProvider(Function<VariableManager, List<String>> expandedNodeIdsProvider) {
            this.expandedNodeIdsProvider = Objects.requireNonNull(expandedNodeIdsProvider);
            return this;
        }

        public Builder isCheckableProvider(Function<VariableManager, Boolean> isCheckableProvider) {
            this.isCheckableProvider = Objects.requireNonNull(isCheckableProvider);
            return this;
        }

        public Builder checkedValueProvider(Function<VariableManager, Boolean> checkedValueProvider) {
            this.checkedValueProvider = Objects.requireNonNull(checkedValueProvider);
            return this;
        }

        public Builder newCheckedValueHandler(BiFunction<VariableManager, Boolean, IStatus> newCheckedValueHandler) {
            this.newCheckedValueHandler = Objects.requireNonNull(newCheckedValueHandler);
            return this;
        }

        public Builder diagnosticsProvider(Function<VariableManager, List<?>> diagnosticsProvider) {
            this.diagnosticsProvider = Objects.requireNonNull(diagnosticsProvider);
            return this;
        }

        public Builder kindProvider(Function<Object, String> kindProvider) {
            this.kindProvider = Objects.requireNonNull(kindProvider);
            return this;
        }

        public Builder messageProvider(Function<Object, String> messageProvider) {
            this.messageProvider = Objects.requireNonNull(messageProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public Builder isReadOnlyProvider(Function<VariableManager, Boolean> isReadOnlyProvider) {
            this.isReadOnlyProvider = Objects.requireNonNull(isReadOnlyProvider);
            return this;
        }

        public TreeDescription build() {
            TreeDescription treeDescription = new TreeDescription();
            treeDescription.id = Objects.requireNonNull(this.id);
            treeDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            treeDescription.idProvider = Objects.requireNonNull(this.idProvider);
            treeDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            treeDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            treeDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            treeDescription.childrenProvider = Objects.requireNonNull(this.childrenProvider);
            treeDescription.nodeIdProvider = Objects.requireNonNull(this.nodeIdProvider);
            treeDescription.nodeLabelProvider = Objects.requireNonNull(this.nodeLabelProvider);
            treeDescription.nodeEndIconsURLProvider = Objects.requireNonNull(this.nodeEndIconsURLProvider);
            treeDescription.nodeKindProvider = Objects.requireNonNull(this.nodeKindProvider);
            treeDescription.nodeIconURLProvider = Objects.requireNonNull(this.nodeIconURLProvider);
            treeDescription.nodeSelectableProvider = Objects.requireNonNull(this.nodeSelectableProvider);
            treeDescription.expandedNodeIdsProvider = Objects.requireNonNull(this.expandedNodeIdsProvider);
            treeDescription.isCheckableProvider = Objects.requireNonNull(this.isCheckableProvider);
            treeDescription.checkedValueProvider = Objects.requireNonNull(this.checkedValueProvider);
            treeDescription.newCheckedValueHandler = Objects.requireNonNull(this.newCheckedValueHandler);
            treeDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            treeDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            treeDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            treeDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return treeDescription;
        }
    }

}
