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
package org.eclipse.sirius.components.diagrams.description;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the node.
 *
 * @author sbegaudeau
 */
@Immutable
public final class NodeDescription implements IDiagramElementDescription {

    private String id;

    private SynchronizationPolicy synchronizationPolicy;

    private Function<VariableManager, String> typeProvider;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private Function<VariableManager, String> targetObjectLabelProvider;

    private Function<VariableManager, List<?>> semanticElementsProvider;

    private Predicate<VariableManager> shouldRenderPredicate;

    private InsideLabelDescription insideLabelDescription;

    private List<OutsideLabelDescription> outsideLabelDescriptions;

    private Function<VariableManager, INodeStyle> styleProvider;

    private Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider;

    private Function<VariableManager, Size> sizeProvider;

    private boolean userResizable;

    private List<NodeDescription> borderNodeDescriptions;

    private List<NodeDescription> childNodeDescriptions;

    private boolean collapsible;

    private List<String> reusedBorderNodeDescriptionIds;

    private List<String> reusedChildNodeDescriptionIds;

    private BiFunction<VariableManager, String, IStatus> labelEditHandler;

    private Function<VariableManager, IStatus> deleteHandler;

    private boolean keepAspectRatio;

    private Function<VariableManager, Integer> defaultWidthProvider;

    private Function<VariableManager, Integer> defaultHeightProvider;

    private Function<VariableManager, IStatus> dropNodeHandler;

    private NodeDescription() {
        // Prevent instantiation
    }

    public static Builder newNodeDescription(String id) {
        return new Builder(id);
    }

    public static Builder newNodeDescription(NodeDescription nodeDescription) {
        return new Builder(nodeDescription);
    }

    @Override
    public String getId() {
        return this.id;
    }

    public SynchronizationPolicy getSynchronizationPolicy() {
        return this.synchronizationPolicy;
    }

    public Function<VariableManager, String> getTypeProvider() {
        return this.typeProvider;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, String> getTargetObjectKindProvider() {
        return this.targetObjectKindProvider;
    }

    public Function<VariableManager, String> getTargetObjectLabelProvider() {
        return this.targetObjectLabelProvider;
    }

    public Function<VariableManager, List<?>> getSemanticElementsProvider() {
        return this.semanticElementsProvider;
    }

    public Predicate<VariableManager> getShouldRenderPredicate() {
        return this.shouldRenderPredicate;
    }

    public InsideLabelDescription getInsideLabelDescription() {
        return this.insideLabelDescription;
    }

    public List<OutsideLabelDescription> getOutsideLabelDescriptions() {
        return this.outsideLabelDescriptions;
    }

    public Function<VariableManager, INodeStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public Function<VariableManager, ILayoutStrategy> getChildrenLayoutStrategyProvider() {
        return this.childrenLayoutStrategyProvider;
    }

    public Function<VariableManager, Size> getSizeProvider() {
        return this.sizeProvider;
    }

    public boolean isUserResizable() {
        return this.userResizable;
    }

    public List<NodeDescription> getBorderNodeDescriptions() {
        return this.borderNodeDescriptions;
    }

    public List<NodeDescription> getChildNodeDescriptions() {
        return this.childNodeDescriptions;
    }

    public boolean isCollapsible() {
        return this.collapsible;
    }

    public List<String> getReusedBorderNodeDescriptionIds() {
        return this.reusedBorderNodeDescriptionIds;
    }

    public List<String> getReusedChildNodeDescriptionIds() {
        return this.reusedChildNodeDescriptionIds;
    }

    public Function<VariableManager, IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    public Function<VariableManager, IStatus> getDropNodeHandler() {
        return this.dropNodeHandler;
    }

    public BiFunction<VariableManager, String, IStatus> getLabelEditHandler() {
        return this.labelEditHandler;
    }

    public boolean isKeepAspectRatio() {
        return this.keepAspectRatio;
    }

    public Function<VariableManager, Integer> getDefaultWidthProvider() {
        return this.defaultWidthProvider;
    }

    public Function<VariableManager, Integer> getDefaultHeightProvider() {
        return this.defaultHeightProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, borderNodeDescriptionCount: {2}, childNodeDescriptionCount: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.borderNodeDescriptions.size(), this.childNodeDescriptions.size());
    }

    /**
     * The builder used to create the node description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.SYNCHRONIZED;

        private Function<VariableManager, String> typeProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private Function<VariableManager, String> targetObjectLabelProvider;

        private Function<VariableManager, List<?>> semanticElementsProvider;

        private Predicate<VariableManager> shouldRenderPredicate = variableManager -> true;

        private InsideLabelDescription insideLabelDescription;

        private List<OutsideLabelDescription> outsideLabelDescriptions = List.of();

        private Function<VariableManager, INodeStyle> styleProvider;

        private Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider;

        private Function<VariableManager, Size> sizeProvider;

        private boolean userResizable = true;

        private List<NodeDescription> borderNodeDescriptions = new ArrayList<>();

        private List<NodeDescription> childNodeDescriptions = new ArrayList<>();

        private boolean collapsible;

        private List<String> reusedBorderNodeDescriptionIds = new ArrayList<>();

        private List<String> reusedChildNodeDescriptionIds = new ArrayList<>();

        private BiFunction<VariableManager, String, IStatus> labelEditHandler;

        private Function<VariableManager, IStatus> deleteHandler;

        private boolean keepAspectRatio;

        private Function<VariableManager, Integer> defaultWidthProvider = variableManager -> null;

        private Function<VariableManager, Integer> defaultHeightProvider = variableManager -> null;

        private Function<VariableManager, IStatus> dropNodeHandler;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(NodeDescription nodeDescription) {
            this.id = nodeDescription.getId();
            this.synchronizationPolicy = nodeDescription.getSynchronizationPolicy();
            this.typeProvider = nodeDescription.getTypeProvider();
            this.targetObjectIdProvider = nodeDescription.getTargetObjectIdProvider();
            this.targetObjectKindProvider = nodeDescription.getTargetObjectKindProvider();
            this.targetObjectLabelProvider = nodeDescription.getTargetObjectLabelProvider();
            this.semanticElementsProvider = nodeDescription.getSemanticElementsProvider();
            this.insideLabelDescription = nodeDescription.getInsideLabelDescription();
            this.outsideLabelDescriptions = nodeDescription.getOutsideLabelDescriptions();
            this.styleProvider = nodeDescription.getStyleProvider();
            this.sizeProvider = nodeDescription.getSizeProvider();
            this.childrenLayoutStrategyProvider = nodeDescription.getChildrenLayoutStrategyProvider();
            this.borderNodeDescriptions = nodeDescription.getBorderNodeDescriptions();
            this.childNodeDescriptions = nodeDescription.getChildNodeDescriptions();
            this.collapsible = nodeDescription.isCollapsible();
            this.reusedBorderNodeDescriptionIds = nodeDescription.getReusedBorderNodeDescriptionIds();
            this.reusedChildNodeDescriptionIds = nodeDescription.getReusedChildNodeDescriptionIds();
            this.labelEditHandler = nodeDescription.getLabelEditHandler();
            this.deleteHandler = nodeDescription.getDeleteHandler();
            this.shouldRenderPredicate = nodeDescription.getShouldRenderPredicate();
            this.dropNodeHandler = nodeDescription.getDropNodeHandler();
            this.keepAspectRatio = nodeDescription.isKeepAspectRatio();
            this.defaultWidthProvider = nodeDescription.getDefaultWidthProvider();
            this.defaultHeightProvider = nodeDescription.getDefaultHeightProvider();

        }

        public Builder synchronizationPolicy(SynchronizationPolicy synchronizationPolicy) {
            this.synchronizationPolicy = synchronizationPolicy;
            return this;
        }

        public Builder typeProvider(Function<VariableManager, String> typeProvider) {
            this.typeProvider = Objects.requireNonNull(typeProvider);
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

        public Builder targetObjectLabelProvider(Function<VariableManager, String> targetObjectLabelProvider) {
            this.targetObjectLabelProvider = Objects.requireNonNull(targetObjectLabelProvider);
            return this;
        }

        public Builder semanticElementsProvider(Function<VariableManager, List<?>> semanticElementsProvider) {
            this.semanticElementsProvider = Objects.requireNonNull(semanticElementsProvider);
            return this;
        }

        public Builder shouldRenderPredicate(Predicate<VariableManager> shouldRenderPredicate) {
            this.shouldRenderPredicate = Objects.requireNonNull(shouldRenderPredicate);
            return this;
        }

        public Builder insideLabelDescription(InsideLabelDescription insideLabelDescription) {
            this.insideLabelDescription = Objects.requireNonNull(insideLabelDescription);
            return this;
        }

        public Builder outsideLabelDescriptions(List<OutsideLabelDescription> outsideLabelDescriptions) {
            this.outsideLabelDescriptions = Objects.requireNonNull(outsideLabelDescriptions);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, INodeStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder childrenLayoutStrategyProvider(Function<VariableManager, ILayoutStrategy> childrenLayoutStrategyProvider) {
            this.childrenLayoutStrategyProvider = Objects.requireNonNull(childrenLayoutStrategyProvider);
            return this;
        }

        public Builder sizeProvider(Function<VariableManager, Size> sizeProvider) {
            this.sizeProvider = Objects.requireNonNull(sizeProvider);
            return this;
        }

        public Builder userResizable(boolean userResizable) {
            this.userResizable = userResizable;
            return this;
        }

        public Builder borderNodeDescriptions(List<NodeDescription> borderNodeDescriptions) {
            this.borderNodeDescriptions = Objects.requireNonNull(borderNodeDescriptions);
            return this;
        }

        public Builder childNodeDescriptions(List<NodeDescription> childNodeDescriptions) {
            this.childNodeDescriptions = Objects.requireNonNull(childNodeDescriptions);
            return this;
        }

        public Builder collapsible(boolean collapsible) {
            this.collapsible = collapsible;
            return this;
        }

        public Builder reusedBorderNodeDescriptionIds(List<String> reusedBorderNodeDescriptionIds) {
            this.reusedBorderNodeDescriptionIds = Objects.requireNonNull(reusedBorderNodeDescriptionIds);
            return this;
        }

        public Builder reusedChildNodeDescriptionIds(List<String> reusedChildNodeDescriptionIds) {
            this.reusedChildNodeDescriptionIds = Objects.requireNonNull(reusedChildNodeDescriptionIds);
            return this;
        }

        public Builder deleteHandler(Function<VariableManager, IStatus> deleteHandler) {
            this.deleteHandler = Objects.requireNonNull(deleteHandler);
            return this;
        }

        public Builder labelEditHandler(BiFunction<VariableManager, String, IStatus> labelEditHandler) {
            this.labelEditHandler = Objects.requireNonNull(labelEditHandler);
            return this;
        }

        public Builder dropNodeHandler(Function<VariableManager, IStatus> dropNodeHandler) {
            this.dropNodeHandler = Objects.requireNonNull(dropNodeHandler);
            return this;
        }

        public Builder keepAspectRatio(boolean keepAspectRatio) {
            this.keepAspectRatio = keepAspectRatio;
            return this;
        }

        public Builder defaultWidthProvider(Function<VariableManager, Integer> defaultWidthProvider) {
            this.defaultWidthProvider = Objects.requireNonNull(defaultWidthProvider);
            return this;
        }

        public Builder defaultHeightProvider(Function<VariableManager, Integer> defaultHeightProvider) {
            this.defaultHeightProvider = Objects.requireNonNull(defaultHeightProvider);
            return this;
        }

        public NodeDescription build() {
            NodeDescription nodeDescription = new NodeDescription();
            nodeDescription.id = Objects.requireNonNull(this.id);
            nodeDescription.synchronizationPolicy = this.synchronizationPolicy;
            nodeDescription.typeProvider = Objects.requireNonNull(this.typeProvider);
            nodeDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            nodeDescription.targetObjectKindProvider = Objects.requireNonNull(this.targetObjectKindProvider);
            nodeDescription.targetObjectLabelProvider = Objects.requireNonNull(this.targetObjectLabelProvider);
            nodeDescription.semanticElementsProvider = Objects.requireNonNull(this.semanticElementsProvider);
            nodeDescription.shouldRenderPredicate = Objects.requireNonNull(this.shouldRenderPredicate);
            nodeDescription.insideLabelDescription = this.insideLabelDescription; // Optional on purpose
            nodeDescription.outsideLabelDescriptions = Objects.requireNonNull(this.outsideLabelDescriptions);
            nodeDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            nodeDescription.sizeProvider = Objects.requireNonNull(this.sizeProvider);
            nodeDescription.userResizable = this.userResizable;
            nodeDescription.childrenLayoutStrategyProvider = Objects.requireNonNull(this.childrenLayoutStrategyProvider);
            nodeDescription.borderNodeDescriptions = Objects.requireNonNull(this.borderNodeDescriptions);
            nodeDescription.childNodeDescriptions = Objects.requireNonNull(this.childNodeDescriptions);
            nodeDescription.collapsible = this.collapsible;
            nodeDescription.reusedBorderNodeDescriptionIds = Objects.requireNonNull(this.reusedBorderNodeDescriptionIds);
            nodeDescription.reusedChildNodeDescriptionIds = Objects.requireNonNull(this.reusedChildNodeDescriptionIds);
            nodeDescription.labelEditHandler = this.labelEditHandler; // Optional on purpose
            nodeDescription.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            nodeDescription.dropNodeHandler = this.dropNodeHandler; // Optional on purpose.
            nodeDescription.keepAspectRatio = this.keepAspectRatio;
            nodeDescription.defaultWidthProvider = Objects.requireNonNull(this.defaultWidthProvider);
            nodeDescription.defaultHeightProvider = Objects.requireNonNull(this.defaultHeightProvider);
            return nodeDescription;
        }
    }

}
