/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
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
public final class NodeDescription {
    private UUID id;

    private SynchronizationPolicy synchronizationPolicy;

    private Function<VariableManager, String> typeProvider;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, String> targetObjectKindProvider;

    private Function<VariableManager, String> targetObjectLabelProvider;

    private Function<VariableManager, List<Object>> semanticElementsProvider;

    private LabelDescription labelDescription;

    private Function<VariableManager, INodeStyle> styleProvider;

    private Function<VariableManager, Size> sizeProvider;

    private List<NodeDescription> borderNodeDescriptions;

    private List<NodeDescription> childNodeDescriptions = new ArrayList<>();

    private BiFunction<VariableManager, String, IStatus> labelEditHandler;

    private Function<VariableManager, IStatus> deleteHandler;

    private NodeDescription() {
        // Prevent instantiation
    }

    public UUID getId() {
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

    public Function<VariableManager, List<Object>> getSemanticElementsProvider() {
        return this.semanticElementsProvider;
    }

    public LabelDescription getLabelDescription() {
        return this.labelDescription;
    }

    public Function<VariableManager, INodeStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public Function<VariableManager, Size> getSizeProvider() {
        return this.sizeProvider;
    }

    public List<NodeDescription> getBorderNodeDescriptions() {
        return this.borderNodeDescriptions;
    }

    public List<NodeDescription> getChildNodeDescriptions() {
        return this.childNodeDescriptions;
    }

    public Function<VariableManager, IStatus> getDeleteHandler() {
        return this.deleteHandler;
    }

    public BiFunction<VariableManager, String, IStatus> getLabelEditHandler() {
        return this.labelEditHandler;
    }

    public static Builder newNodeDescription(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, borderNodeDescriptionCount: {2}, childNodeDescriptionCount: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.borderNodeDescriptions.size(), this.childNodeDescriptions.size());
    }

    /**
     * The builder used to create the node description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private SynchronizationPolicy synchronizationPolicy = SynchronizationPolicy.SYNCHRONIZED;

        private Function<VariableManager, String> typeProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> targetObjectKindProvider;

        private Function<VariableManager, String> targetObjectLabelProvider;

        private Function<VariableManager, List<Object>> semanticElementsProvider;

        private LabelDescription labelDescription;

        private Function<VariableManager, INodeStyle> styleProvider;

        private Function<VariableManager, Size> sizeProvider;

        private List<NodeDescription> borderNodeDescriptions;

        private List<NodeDescription> childNodeDescriptions;

        private BiFunction<VariableManager, String, IStatus> labelEditHandler;

        private Function<VariableManager, IStatus> deleteHandler;

        public Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
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

        public Builder semanticElementsProvider(Function<VariableManager, List<Object>> semanticElementsProvider) {
            this.semanticElementsProvider = Objects.requireNonNull(semanticElementsProvider);
            return this;
        }

        public Builder labelDescription(LabelDescription labelDescription) {
            this.labelDescription = Objects.requireNonNull(labelDescription);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, INodeStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder sizeProvider(Function<VariableManager, Size> sizeProvider) {
            this.sizeProvider = Objects.requireNonNull(sizeProvider);
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

        public Builder deleteHandler(Function<VariableManager, IStatus> deleteHandler) {
            this.deleteHandler = Objects.requireNonNull(deleteHandler);
            return this;
        }

        public Builder labelEditHandler(BiFunction<VariableManager, String, IStatus> labelEditHandler) {
            this.labelEditHandler = Objects.requireNonNull(labelEditHandler);
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
            nodeDescription.labelDescription = Objects.requireNonNull(this.labelDescription);
            nodeDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            nodeDescription.sizeProvider = Objects.requireNonNull(this.sizeProvider);
            nodeDescription.borderNodeDescriptions = Objects.requireNonNull(this.borderNodeDescriptions);
            nodeDescription.childNodeDescriptions = Objects.requireNonNull(this.childNodeDescriptions);
            nodeDescription.labelEditHandler = Objects.requireNonNull(this.labelEditHandler);
            nodeDescription.deleteHandler = Objects.requireNonNull(this.deleteHandler);
            return nodeDescription;
        }
    }

}
