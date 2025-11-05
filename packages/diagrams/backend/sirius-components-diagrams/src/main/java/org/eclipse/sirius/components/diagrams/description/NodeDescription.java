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
package org.eclipse.sirius.components.diagrams.description;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.UserResizableDirection;
import org.eclipse.sirius.components.diagrams.components.BorderNodePosition;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the node.
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@Immutable
public final class NodeDescription implements IDiagramElementDescription {

    /**
     * The name of the variable used to store the ancestors (all previous self) of the current context.
     */
    public static final String ANCESTORS = "ancestors";

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

    private UserResizableDirection userResizable;

    private List<NodeDescription> borderNodeDescriptions;

    private List<NodeDescription> childNodeDescriptions;

    private boolean collapsible;

    private List<String> reusedBorderNodeDescriptionIds;

    private List<String> reusedChildNodeDescriptionIds;

    private BiFunction<VariableManager, String, IStatus> labelEditHandler;

    private Function<VariableManager, IStatus> deleteHandler;

    private boolean keepAspectRatio;

    private Predicate<VariableManager> isCollapsedByDefaultPredicate;

    private Predicate<VariableManager> isHiddenByDefaultPredicate;

    private Predicate<VariableManager> isFadedByDefaultPredicate;

    private Function<VariableManager, Integer> defaultWidthProvider;

    private Function<VariableManager, Integer> defaultHeightProvider;

    private Function<VariableManager, IStatus> dropNodeHandler;

    private Map<String, BorderNodePosition> initialChildBorderNodePositions;

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

    /**
     * Provides a function used to compute the kind of the semantic element used as the target of the node.
     *
     * @return A function used to return the kind of the semantic element.
     *
     * @technical-debt This method should be removed since its addition was caused by some technical debt in the explorer.
     */
    public Function<VariableManager, String> getTargetObjectKindProvider() {
        return this.targetObjectKindProvider;
    }

    /**
     * Provides a function used to compute the label of the semantic element used as the target of the node.
     *
     * @return A function used to return the label of the semantic element.
     *
     * @technical-debt This method should be removed since its addition was caused by some technical debt in the explorer.
     */
    public Function<VariableManager, String> getTargetObjectLabelProvider() {
        return this.targetObjectLabelProvider;
    }

    /**
     * Provides the function which will be used to compute the semantic elements which will be used as the target of some
     * nodes created from this description.
     *
     * <p>
     *     The following variables will at least be available when this behavior is executed:
     * </p>
     *
     * <ul>
     *     <li><strong>self</strong> - The semantic element used by the parent element, for example the root of the diagram
     *     for top level nodes or the semantic element used as a target by the parent node</li>
     *     <li><strong>editingContext</strong> - The editing context used to access any semantic element thanks to the core
     *     services</li>
     *     <li><strong>previousDiagram</strong> - The previously rendered diagram or null if it is being rendered for the
     *     first time</li>
     *     <li><strong>diagramContext</strong> - The diagram context is used to access the various events along with the
     *     view creation and deletion requests</li>
     * </ul>
     *
     * @return A function used to return the semantic elements to use as the target of the nodes.
     */
    public Function<VariableManager, List<?>> getSemanticElementsProvider() {
        return this.semanticElementsProvider;
    }

    /**
     * Provides a predicate used to indicate if a node should be rendered from the node description.
     *
     * @return A predicate to determine if a node should be rendered or not
     *
     * @technical-debt This method should probably be removed and the predicate be integrated in the semantic elements
     * provider by downstream specifiers.
     */
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

    public UserResizableDirection getUserResizable() {
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

    /**
     * Provides a function used to execute the deletion of the node.
     *
     * <p>
     *     The following variables will at least be available when this behavior is executed:
     * </p>
     *
     * <ul>
     *     <li><strong>self</strong> - The semantic element used as a target by the node to be deleted</li>
     *     <li><strong>editingContext</strong> - The editing context used to access any semantic element thanks to the
     *     core services</li>
     *     <li><strong>diagramContext</strong> - The diagram context is used to access the various events along with the
     *     view creation and deletion requests</li>
     *     <li><strong>selectedNode</strong> - The node which should be deleted</li>
     * </ul>
     *
     * @return A function provided by a specifier to trigger the deletion of the node
     *
     * @technical-debt This function is unused during the rendering and should thus be removed. Hardcoding such behavior
     * into the description also provides a poor extensibility of the diagram representation by preventing downstream
     * consumers from updating the existing behavior or adding a new one easily
     */
    public Function<VariableManager, IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    /**
     * Provides a function used to let end users drop nodes on another node.
     *
     * <p>
     *     The following variables will at least be available when this behavior is executed:
     * </p>
     *
     * <ul>
     *     <li><strong>editingContext</strong> - The editing context used to access any semantic element thanks to the
     *     core services</li>
     *     <li><strong>diagramContext</strong> - The diagram context is used to access the various events along with the
     *     view creation and deletion requests</li>
     *     <li><strong>droppedElement</strong> - The semantic element used as a target by the dropped node</li>
     *     <li><strong>droppedNode</strong> - The node being dropped</li>
     *     <li><strong>targetElement</strong> - The semantic element used as a target by the target of the drop (another
     *     node or the diagram itself)</li>
     *     <li><strong>targetNode</strong> - The node in which the node is being dropped or null if dropped on the diagram
     *     itself</li>
     * </ul>
     *
     * @return A function provided by a specifier to drop nodes on another node
     *
     * @technical-debt This function is unused during the rendering and should thus be removed. Hardcoding such behavior
     * into the description also provides a poor extensibility of the diagram representation by preventing downstream
     * consumers from updating the existing behavior or adding a new one easily
     */
    public Function<VariableManager, IStatus> getDropNodeHandler() {
        return this.dropNodeHandler;
    }

    public BiFunction<VariableManager, String, IStatus> getLabelEditHandler() {
        return this.labelEditHandler;
    }

    public boolean isKeepAspectRatio() {
        return this.keepAspectRatio;
    }

    public Predicate<VariableManager> getIsCollapsedByDefaultPredicate() {
        return this.isCollapsedByDefaultPredicate;
    }

    public Predicate<VariableManager> getIsHiddenByDefaultPredicate() {
        return this.isHiddenByDefaultPredicate;
    }

    public Predicate<VariableManager> getIsFadedByDefaultPredicate() {
        return this.isFadedByDefaultPredicate;
    }

    public Function<VariableManager, Integer> getDefaultWidthProvider() {
        return this.defaultWidthProvider;
    }

    public Function<VariableManager, Integer> getDefaultHeightProvider() {
        return this.defaultHeightProvider;
    }

    public Map<String, BorderNodePosition> getInitialChildBorderNodePositions() {
        return this.initialChildBorderNodePositions;
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

        private UserResizableDirection userResizable = UserResizableDirection.BOTH;

        private List<NodeDescription> borderNodeDescriptions = new ArrayList<>();

        private List<NodeDescription> childNodeDescriptions = new ArrayList<>();

        private boolean collapsible;

        private List<String> reusedBorderNodeDescriptionIds = new ArrayList<>();

        private List<String> reusedChildNodeDescriptionIds = new ArrayList<>();

        private BiFunction<VariableManager, String, IStatus> labelEditHandler;

        private Function<VariableManager, IStatus> deleteHandler;

        private boolean keepAspectRatio;

        private Predicate<VariableManager> isCollapsedByDefaultPredicate = variableManager -> false;

        private Predicate<VariableManager> isHiddenByDefaultPredicate = variableManager -> false;

        private Predicate<VariableManager> isFadedByDefaultPredicate = variableManager -> false;

        private Function<VariableManager, Integer> defaultWidthProvider = variableManager -> null;

        private Function<VariableManager, Integer> defaultHeightProvider = variableManager -> null;

        private Function<VariableManager, IStatus> dropNodeHandler;

        private Map<String, BorderNodePosition> initialChildBorderNodePositions;

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
            this.isCollapsedByDefaultPredicate = nodeDescription.getIsCollapsedByDefaultPredicate();
            this.isHiddenByDefaultPredicate = nodeDescription.getIsHiddenByDefaultPredicate();
            this.isFadedByDefaultPredicate = nodeDescription.getIsFadedByDefaultPredicate();
            this.defaultWidthProvider = nodeDescription.getDefaultWidthProvider();
            this.defaultHeightProvider = nodeDescription.getDefaultHeightProvider();
            this.initialChildBorderNodePositions = nodeDescription.getInitialChildBorderNodePositions();
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

        public Builder userResizable(UserResizableDirection userResizable) {
            this.userResizable = Objects.requireNonNull(userResizable);
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

        public Builder isCollapsedByDefaultPredicate(Predicate<VariableManager> isCollapsedByDefaultPredicate) {
            this.isCollapsedByDefaultPredicate = Objects.requireNonNull(isCollapsedByDefaultPredicate);
            return this;
        }

        public Builder isHiddenByDefaultPredicate(Predicate<VariableManager> isHiddenByDefaultPredicate) {
            this.isHiddenByDefaultPredicate = Objects.requireNonNull(isHiddenByDefaultPredicate);
            return this;
        }

        public Builder isFadedByDefaultPredicate(Predicate<VariableManager> isFadedByDefaultPredicate) {
            this.isFadedByDefaultPredicate = Objects.requireNonNull(isFadedByDefaultPredicate);
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

        public Builder initialChildBorderNodePositions(Map<String, BorderNodePosition> initialChildBorderNodePositions) {
            this.initialChildBorderNodePositions = Objects.requireNonNull(initialChildBorderNodePositions);
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
            nodeDescription.userResizable = Objects.requireNonNull(this.userResizable);
            nodeDescription.borderNodeDescriptions = Objects.requireNonNull(this.borderNodeDescriptions);
            nodeDescription.childNodeDescriptions = Objects.requireNonNull(this.childNodeDescriptions);
            nodeDescription.collapsible = this.collapsible;
            nodeDescription.reusedBorderNodeDescriptionIds = Objects.requireNonNull(this.reusedBorderNodeDescriptionIds);
            nodeDescription.reusedChildNodeDescriptionIds = Objects.requireNonNull(this.reusedChildNodeDescriptionIds);
            nodeDescription.labelEditHandler = this.labelEditHandler; // Optional on purpose
            nodeDescription.deleteHandler = this.deleteHandler; // Optional on purpose
            nodeDescription.dropNodeHandler = this.dropNodeHandler; // Optional on purpose.
            nodeDescription.keepAspectRatio = this.keepAspectRatio;
            nodeDescription.isCollapsedByDefaultPredicate = Objects.requireNonNull(this.isCollapsedByDefaultPredicate);
            nodeDescription.isHiddenByDefaultPredicate = Objects.requireNonNull(this.isHiddenByDefaultPredicate);
            nodeDescription.isFadedByDefaultPredicate = Objects.requireNonNull(this.isFadedByDefaultPredicate);
            nodeDescription.defaultWidthProvider = Objects.requireNonNull(this.defaultWidthProvider);
            nodeDescription.defaultHeightProvider = Objects.requireNonNull(this.defaultHeightProvider);
            nodeDescription.initialChildBorderNodePositions = Objects.requireNonNull(this.initialChildBorderNodePositions);
            return nodeDescription;
        }
    }
}
