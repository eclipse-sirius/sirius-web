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

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.NodeDecoratorPosition;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The semantic decorator description.
 *
 * @author gdaniel
 */
@Immutable
public final class SemanticDecoratorDescription implements IDecoratorDescription {

    private String id;

    private Function<VariableManager, String> labelProvider;

    private Predicate<VariableManager> preconditionPredicate;

    private Function<VariableManager, String> iconURLProvider;

    private NodeDecoratorPosition position;

    private Predicate<VariableManager> domainTypePredicate;

    private SemanticDecoratorDescription() {
        // Prevent instantiation
    }

    public static Builder newSemanticDecoratorDescription(String id) {
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

    public Predicate<VariableManager> getDomainTypePredicate() {
        return this.domainTypePredicate;
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

        private Predicate<VariableManager> domainTypePredicate;

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

        public Builder domainTypePredicate(Predicate<VariableManager> domainTypePredicate) {
            this.domainTypePredicate = Objects.requireNonNull(domainTypePredicate);
            return this;
        }

        public SemanticDecoratorDescription build() {
            SemanticDecoratorDescription semanticDecoratorDescription = new SemanticDecoratorDescription();
            semanticDecoratorDescription.id = Objects.requireNonNull(this.id);
            semanticDecoratorDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            semanticDecoratorDescription.preconditionPredicate = Objects.requireNonNull(this.preconditionPredicate);
            semanticDecoratorDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            semanticDecoratorDescription.position = Objects.requireNonNull(this.position);
            semanticDecoratorDescription.domainTypePredicate = Objects.requireNonNull(this.domainTypePredicate);
            return semanticDecoratorDescription;
        }

    }
}
