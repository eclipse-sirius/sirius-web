/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.components.diagrams.NodeDecoratorPosition;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The node decorator description.
 *
 * @author gdaniel
 */
@Immutable
public final class NodeDecoratorDescription implements IDecoratorDescription {

    private String id;

    private Function<VariableManager, String> labelProvider;

    private Predicate<VariableManager> preconditionPredicate;

    private Function<VariableManager, String> iconURLProvider;

    private NodeDecoratorPosition position;

    private List<NodeDescription> nodeDescriptions;

    private NodeDecoratorDescription() {
        // Prevent instantiation
    }

    public static Builder newNodeDecoratorDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    @Override
    public Predicate<VariableManager> getPreconditionPredicate() {
        return this.preconditionPredicate;
    }

    @Override
    public Function<VariableManager, String> getIconURLProvider() {
        return this.iconURLProvider;
    }

    @Override
    public NodeDecoratorPosition getPosition() {
        return this.position;
    }

    public List<NodeDescription> getNodeDescriptions() {
        return this.nodeDescriptions;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create a new node decorator description.
     *
     * @author gdaniel
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> labelProvider;

        private Predicate<VariableManager> preconditionPredicate;

        private Function<VariableManager, String> iconURLProvider;

        private NodeDecoratorPosition position;

        private List<NodeDescription> nodeDescriptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder preconditionPredicate(Predicate<VariableManager> preconditionPredicate) {
            this.preconditionPredicate = Objects.requireNonNull(preconditionPredicate);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, String> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public Builder position(NodeDecoratorPosition position) {
            this.position = Objects.requireNonNull(position);
            return this;
        }

        public Builder nodeDescriptions(List<NodeDescription> nodeDescriptions) {
            this.nodeDescriptions = Objects.requireNonNull(nodeDescriptions);
            return this;
        }

        public NodeDecoratorDescription build() {
            NodeDecoratorDescription nodeDecoratorDescription = new NodeDecoratorDescription();
            nodeDecoratorDescription.id = Objects.requireNonNull(this.id);
            nodeDecoratorDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            nodeDecoratorDescription.preconditionPredicate = Objects.requireNonNull(this.preconditionPredicate);
            nodeDecoratorDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            nodeDecoratorDescription.position = Objects.requireNonNull(this.position);
            nodeDecoratorDescription.nodeDescriptions = Objects.requireNonNull(this.nodeDescriptions);
            return nodeDecoratorDescription;
        }

    }
}
