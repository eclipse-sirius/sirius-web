/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams.description;

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
import org.eclipse.sirius.web.diagrams.tools.ToolSection;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The root concept of the description of a diagram representation.
 *
 * @author sbegaudeau
 */
@Immutable
@GraphQLObjectType
public final class DiagramDescription implements IRepresentationDescription {

    /**
     * The name of the variable used to store and retrieve the node or edge description id from a variable manager.
     */
    public static final String DESCRIPTION_ID = "descriptionId"; //$NON-NLS-1$

    public static final String CACHE = "cache"; //$NON-NLS-1$

    public static final String LABEL = "label"; //$NON-NLS-1$

    private UUID id;

    private String label;

    private boolean autoLayout;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private Function<VariableManager, String> labelProvider;

    private List<ToolSection> toolSections;

    private List<NodeDescription> nodeDescriptions;

    private List<EdgeDescription> edgeDescriptions;

    private Function<VariableManager, IStatus> dropHandler;

    private DiagramDescription() {
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

    @GraphQLField
    @GraphQLNonNull
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

    public Function<VariableManager, IStatus> getDropHandler() {
        return this.dropHandler;
    }

    public static Builder newDiagramDescription(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, nodeDescriptionCount: {3}, edgeDescriptionCount: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.nodeDescriptions.size(), this.edgeDescriptions.size());
    }

    /**
     * Builder used to create the diagram description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String label;

        private boolean autoLayout;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private Function<VariableManager, String> labelProvider;

        private List<ToolSection> toolSections;

        private List<NodeDescription> nodeDescriptions;

        private List<EdgeDescription> edgeDescriptions;

        private Function<VariableManager, IStatus> dropHandler;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
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
            diagramDescription.dropHandler = Objects.requireNonNull(this.dropHandler);
            return diagramDescription;
        }
    }
}
