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
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.diagrams.ArrangeLayoutDirection;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a diagram representation.
 *
 * <p>
 *     This description is used to hold both the data and behavior to be executed during the rendering of the diagram.
 *     As such, it is divided into multiple description objects containing various pieces of information such as {@link NodeDescription}
 *     or {@link EdgeDescription}.
 * </p>
 *
 * @author sbegaudeau
 * @since v0.1.0
 */
@Immutable
@PublicApi
public final class DiagramDescription implements IRepresentationDescription {

    /**
     * The name of the variable used to store and retrieve the node or edge description id from a variable manager.
     */
    public static final String DESCRIPTION_ID = "descriptionId";

    /**
     * The name of the variable used to store the result of the rendering to allow specifiers to access data from the
     * rendering before the entire diagram has been rendered. This variable can be used to allow the rendering of the edges
     * to retrieve the nodes rendered beforehand.
     */
    public static final String CACHE = "cache";

    /**
     * The name of the variable used to give the user specified label to the label provider.
     *
     * @technical-debt This variable should be removed since it is not used during the rendering, and we have no need for
     * a variable manager to provide this feature.
     */
    public static final String LABEL = "label";

    private String id;

    private String label;

    private boolean autoLayout;

    private ArrangeLayoutDirection arrangeLayoutDirection;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> labelProvider;

    private List<NodeDescription> nodeDescriptions;

    private List<EdgeDescription> edgeDescriptions;

    private Function<VariableManager, IStatus> dropHandler;

    private Function<VariableManager, IStatus> dropNodeHandler;

    private Function<VariableManager, List<String>> iconURLsProvider;

    private DiagramDescription() {
        // Prevent instantiation
    }

    /**
     * Entry point of the creation of the diagram description.
     *
     * @param id The unique identifier of the description
     *
     * @return The builder used to create a diagram description
     */
    public static Builder newDiagramDescription(String id) {
        return new Builder(id);
    }

    /**
     * Entry point used to duplicate and modify an existing diagram description.
     *
     * @param diagramDescription The original description
     *
     * @return The builder used to create a diagram description
     */
    public static Builder newDiagramDescription(DiagramDescription diagramDescription) {
        return new Builder(diagramDescription);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    /**
     * Used to indicate if the diagram created should be laid out automatically.
     *
     * @return <code>true</code> if the diagram should be laid out automatically, <code>false</code> otherwise
     *
     * @technical-debt This property should not exist on the diagram description since it is not used by the rendering.
     */
    public boolean isAutoLayout() {
        return this.autoLayout;
    }

    /**
     * Used to indicate in which direction the diagram should be laid out.
     *
     * @return The main direction to be used by some layout algorithm.
     *
     * @technical-debt This property should not exist on the diagram description since it is not used by the rendering.
     */
    public ArrangeLayoutDirection getArrangeLayoutDirection() {
        return this.arrangeLayoutDirection;
    }

    /**
     * Provides a function used to retrieve the unique identifier of the target object on which this diagram is defined.
     *
     * @return A function to compute the identifier of the target object
     */
    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    /**
     * Provides a function which will be used when a new diagram is created in order to compute its label.<p>
     *
     * <p>
     *     The following variables will at least be available when this behavior is executed:
     * </p>
     *
     * <ul>
     *     <li><strong>self</strong> - The semantic element on which the diagram is being created</li>
     *     <li><strong>label</strong> - The text entered by the end user to use as the label of the diagram</li>
     * </ul>
     *
     * @return A function used to compute the label of the representation.
     *
     * @technical-debt This function is not used by the rendering, and it should not exist on the diagram description since
     * it is not relevant to the creation of a diagram. It is instead used to compute one specific kind of metadata (the label)
     * for one specific use case. Applications integrating Sirius Components could choose not to have any label at all or
     * to have more required metadata.
     */
    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    /**
     * Provides the descriptions of the node to be displayed at the top of the diagram.
     *
     * @return The descriptions of the node
     */
    public List<NodeDescription> getNodeDescriptions() {
        return this.nodeDescriptions;
    }

    /**
     * Provides all the descriptions of edges to be displayed in the diagram.
     *
     * @return The descriptions of the edges
     */
    public List<EdgeDescription> getEdgeDescriptions() {
        return this.edgeDescriptions;
    }

    /**
     * Provides a function which will be used when a semantic element is dropped in a diagram.
     *
     * <p>
     *     The following variables will at least be available when this behavior is executed:
     * </p>
     *
     * <ul>
     *     <li><strong>self</strong> - The semantic element which is being dropped on the diagram</li>
     *     <li><strong>selectedNode</strong> - The node on which the element is being dropped or <code>null</code> if it
     *     is being dropped on the diagram directly</li>
     * </ul>
     *
     * @return A function used to execute some behavior when a semantic element is dropped in a diagram.
     *
     * @technical-debt This function is unused during the rendering and should thus be removed. Hardcoding such behavior
     * into the description also provides a poor extensibility of the diagram representation by preventing downstream
     * consumers from updating the existing behavior or adding a new one easily
     */
    public Function<VariableManager, IStatus> getDropHandler() {
        return this.dropHandler;
    }

    /**
     * Provides a function which will be used when a node is being dropped in a diagram.
     *
     * <p>
     *     The following variables will at least be available when this behavior is executed:
     * </p>
     *
     * <ul>
     *     <li>
     *         <strong>droppedNode</strong> - The node being dropped on the diagram. This variable is deprecated and
     *         should be replaced by <strong>droppedNodes</strong>
     *     </li>
     *     <li>
     *         <strong>droppedElement</strong> - The semantic element used as a target by the node being dropped. This
     *         variable is deprecated and should be replaced by <strong>droppedElements</strong>
     *     </li>
     *     <li><strong>droppedNodes</strong> - All the nodes being dropped on the diagram</li>
     *     <li><strong>droppedElements</strong> - All the semantic element used as a target by the nodes being dropped</li>
     *     <li><strong>targetNode</strong> - The node on which the node is being dropped or <code>null</code> if it
     *     is being dropped on the diagram directly</li>
     *     <li><strong>targetElement</strong> - The semantic element used as a target by the node on which the drop is
     *     done or the target of the diagram itself if the node is being dropped on the diagram directly</li>
     * </ul>
     *
     * @return A function used to execute some behavior when a node is dropped in a diagram.
     *
     * @technical-debt This function is unused during the rendering and should thus be removed. Hardcoding such behavior
     * into the description also provides a poor extensibility of the diagram representation by preventing downstream
     * consumers from updating the existing behavior or adding a new one easily
     */
    public Function<VariableManager, IStatus> getDropNodeHandler() {
        return this.dropNodeHandler;
    }

    /**
     * Provides the function which will be used to retrieve the URL of the various images composing the icon of the diagram.
     *
     * <p>
     *     The following variables will at least be available when this behavior is executed:
     * </p>
     *
     * <ul>
     *     <li><strong>self</strong> - The semantic element on which the diagram is being created</li>
     * </ul>
     *
     * @return A function used to compute the URLs of the icon associated with the diagram.
     *
     * @technical-debt This function is not used by the rendering, and it should not exist on the diagram description since
     * it is not relevant to the creation of a diagram. It is instead used to compute one specific kind of metadata (the icon)
     * for one specific use case. Applications integrating Sirius Components could choose not to have any icon at all or
     * to have more required metadata.
     */
    public Function<VariableManager, List<String>> getIconURLsProvider() {
        return this.iconURLsProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, nodeDescriptionCount: {3}, edgeDescriptionCount: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.nodeDescriptions.size(), this.edgeDescriptions.size());
    }

    /**
     * Builder used to create the diagram description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private boolean autoLayout;

        private ArrangeLayoutDirection arrangeLayoutDirection = ArrangeLayoutDirection.UNDEFINED;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> labelProvider;

        private List<NodeDescription> nodeDescriptions;

        private List<EdgeDescription> edgeDescriptions;

        private Function<VariableManager, IStatus> dropHandler;

        private Function<VariableManager, IStatus> dropNodeHandler;

        private Function<VariableManager, List<String>> iconURLsProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(DiagramDescription diagramDescription) {
            this.id = diagramDescription.getId();
            this.label = diagramDescription.getLabel();
            this.autoLayout = diagramDescription.isAutoLayout();
            this.arrangeLayoutDirection = diagramDescription.getArrangeLayoutDirection();
            this.targetObjectIdProvider = diagramDescription.getTargetObjectIdProvider();
            this.canCreatePredicate = diagramDescription.getCanCreatePredicate();
            this.labelProvider = diagramDescription.getLabelProvider();
            this.nodeDescriptions = diagramDescription.getNodeDescriptions();
            this.edgeDescriptions = diagramDescription.getEdgeDescriptions();
            this.dropHandler = diagramDescription.getDropHandler();
            this.dropNodeHandler = diagramDescription.getDropNodeHandler();
            this.iconURLsProvider = diagramDescription.getIconURLsProvider();
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder autoLayout(boolean autoLayout) {
            this.autoLayout = autoLayout;
            return this;
        }

        public Builder arrangeLayoutDirection(ArrangeLayoutDirection arrangeLayoutDirection) {
            this.arrangeLayoutDirection = Objects.requireNonNull(arrangeLayoutDirection);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder nodeDescriptions(List<NodeDescription> nodeDescriptions) {
            this.nodeDescriptions = Objects.requireNonNull(nodeDescriptions);
            return this;
        }

        public Builder edgeDescriptions(List<EdgeDescription> edgeDescriptions) {
            this.edgeDescriptions = Objects.requireNonNull(edgeDescriptions);
            return this;
        }

        public Builder dropHandler(Function<VariableManager, IStatus> dropHandler) {
            this.dropHandler = Objects.requireNonNull(dropHandler);
            return this;
        }

        public Builder dropNodeHandler(Function<VariableManager, IStatus> dropNodeHandler) {
            this.dropNodeHandler = Objects.requireNonNull(dropNodeHandler);
            return this;
        }

        public Builder iconURLsProvider(Function<VariableManager, List<String>> iconURLsProvider) {
            this.iconURLsProvider =  Objects.requireNonNull(iconURLsProvider);
            return this;
        }

        public DiagramDescription build() {
            DiagramDescription diagramDescription = new DiagramDescription();
            diagramDescription.id = Objects.requireNonNull(this.id);
            diagramDescription.label = Objects.requireNonNull(this.label);
            diagramDescription.autoLayout = this.autoLayout;
            diagramDescription.arrangeLayoutDirection = Objects.requireNonNull(this.arrangeLayoutDirection);
            diagramDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            diagramDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            diagramDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            diagramDescription.nodeDescriptions = Objects.requireNonNull(this.nodeDescriptions);
            diagramDescription.edgeDescriptions = Objects.requireNonNull(this.edgeDescriptions);
            diagramDescription.dropHandler = Objects.requireNonNull(this.dropHandler);
            diagramDescription.dropNodeHandler = this.dropNodeHandler; // Optional on purpose.
            diagramDescription.iconURLsProvider =  Objects.requireNonNull(this.iconURLsProvider);
            return diagramDescription;
        }
    }
}
