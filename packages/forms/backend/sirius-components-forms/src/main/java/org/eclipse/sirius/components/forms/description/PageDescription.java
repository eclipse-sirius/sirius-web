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
package org.eclipse.sirius.components.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description used to create pages of the form.
 *
 * @author sbegaudeau
 */
@Immutable
public final class PageDescription {

    private String id;

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, List<?>> semanticElementsProvider;

    private List<GroupDescription> groupDescriptions;

    private List<ButtonDescription> toolbarActionDescriptions;

    private Predicate<VariableManager> canCreatePredicate;

    private PageDescription() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, List<?>> getSemanticElementsProvider() {
        return this.semanticElementsProvider;
    }

    public List<GroupDescription> getGroupDescriptions() {
        return this.groupDescriptions;
    }

    public List<ButtonDescription> getToolbarActionDescriptions() {
        return this.toolbarActionDescriptions;
    }

    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public static Builder newPageDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create the page description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, List<?>> semanticElementsProvider;

        private List<GroupDescription> groupDescriptions;

        private List<ButtonDescription> toolbarActionDescriptions = List.of();

        private Predicate<VariableManager> canCreatePredicate;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder semanticElementsProvider(Function<VariableManager, List<?>> semanticElementsProvider) {
            this.semanticElementsProvider = Objects.requireNonNull(semanticElementsProvider);
            return this;
        }

        public Builder groupDescriptions(List<GroupDescription> groupDescriptions) {
            this.groupDescriptions = Objects.requireNonNull(groupDescriptions);
            return this;
        }

        public Builder toolbarActionDescriptions(List<ButtonDescription> toolbarActionDescriptions) {
            this.toolbarActionDescriptions = Objects.requireNonNull(toolbarActionDescriptions);
            return this;
        }

        public Builder canCreatePredicate(Predicate<VariableManager> canCreatePredicate) {
            this.canCreatePredicate = Objects.requireNonNull(canCreatePredicate);
            return this;
        }

        public PageDescription build() {
            PageDescription pageDescription = new PageDescription();
            pageDescription.id = Objects.requireNonNull(this.id);
            pageDescription.idProvider = Objects.requireNonNull(this.idProvider);
            pageDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            pageDescription.semanticElementsProvider = Objects.requireNonNull(this.semanticElementsProvider);
            pageDescription.groupDescriptions = Objects.requireNonNull(this.groupDescriptions);
            pageDescription.toolbarActionDescriptions = Objects.requireNonNull(this.toolbarActionDescriptions);
            pageDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            return pageDescription;
        }
    }

}
