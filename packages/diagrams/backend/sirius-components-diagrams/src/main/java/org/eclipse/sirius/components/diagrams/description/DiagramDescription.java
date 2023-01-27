/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a diagram representation.
 *
 * @author sbegaudeau
 */
@Immutable
public final class DiagramDescription implements IRepresentationDescription {

    /**
     * The name of the variable used to store and retrieve the node or edge description id from a variable manager.
     */
    public static final String DESCRIPTION_ID = "descriptionId";

    public static final String CACHE = "cache";

    public static final String LABEL = "label";

    private String id;

    private String label;

    private boolean autoLayout;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> labelProvider;

    private List<ToolSection> toolSections;

    private List<NodeDescription> nodeDescriptions;

    private List<EdgeDescription> edgeDescriptions;

    private List<ITool> tools;

    private Function<VariableManager, IStatus> dropHandler;

    private DiagramDescription() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public boolean isAutoLayout() {
        return this.autoLayout;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public List<ToolSection> getToolSections() {
        return this.toolSections;
    }

    public List<NodeDescription> getNodeDescriptions() {
        return this.nodeDescriptions;
    }

    public List<EdgeDescription> getEdgeDescriptions() {
        return this.edgeDescriptions;
    }

    public List<ITool> getTools() {
        return this.tools;
    }

    public Function<VariableManager, IStatus> getDropHandler() {
        return this.dropHandler;
    }

    public static Builder newDiagramDescription(String id) {
        return new Builder(id);
    }

    public static Builder newDiagramDescription(DiagramDescription diagramDescription) {
        return new Builder(diagramDescription);
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
        private String id;

        private String label;

        private boolean autoLayout;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> labelProvider;

        private List<ToolSection> toolSections;

        private List<NodeDescription> nodeDescriptions;

        private List<EdgeDescription> edgeDescriptions;

        private List<ITool> tools;

        private Function<VariableManager, IStatus> dropHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(DiagramDescription diagramDescription) {
            this.id = diagramDescription.getId();
            this.label = diagramDescription.getLabel();
            this.autoLayout = diagramDescription.isAutoLayout();
            this.targetObjectIdProvider = diagramDescription.getTargetObjectIdProvider();
            this.canCreatePredicate = diagramDescription.getCanCreatePredicate();
            this.labelProvider = diagramDescription.getLabelProvider();
            this.toolSections = diagramDescription.getToolSections();
            this.nodeDescriptions = diagramDescription.getNodeDescriptions();
            this.edgeDescriptions = diagramDescription.getEdgeDescriptions();
            this.tools = diagramDescription.getTools();
            this.dropHandler = diagramDescription.getDropHandler();
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder autoLayout(boolean autoLayout) {
            this.autoLayout = autoLayout;
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

        public Builder toolSections(List<ToolSection> toolSections) {
            this.toolSections = Objects.requireNonNull(toolSections);
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

        public Builder tools(List<ITool> tools) {
            this.tools = Objects.requireNonNull(tools);
            return this;
        }

        public Builder dropHandler(Function<VariableManager, IStatus> dropHandler) {
            this.dropHandler = Objects.requireNonNull(dropHandler);
            return this;
        }

        public DiagramDescription build() {
            DiagramDescription diagramDescription = new DiagramDescription();
            diagramDescription.id = Objects.requireNonNull(this.id);
            diagramDescription.label = Objects.requireNonNull(this.label);
            diagramDescription.autoLayout = this.autoLayout;
            diagramDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            diagramDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            diagramDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            diagramDescription.toolSections = Objects.requireNonNull(this.toolSections);
            diagramDescription.nodeDescriptions = Objects.requireNonNull(this.nodeDescriptions);
            diagramDescription.edgeDescriptions = Objects.requireNonNull(this.edgeDescriptions);
            diagramDescription.tools = Objects.requireNonNull(this.tools);
            diagramDescription.dropHandler = Objects.requireNonNull(this.dropHandler);
            return diagramDescription;
        }
    }
}
