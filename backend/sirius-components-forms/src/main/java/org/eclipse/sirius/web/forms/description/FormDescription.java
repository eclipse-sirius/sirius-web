/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.annotations.PublicApi;
import org.eclipse.sirius.web.annotations.graphql.GraphQLField;
import org.eclipse.sirius.web.annotations.graphql.GraphQLID;
import org.eclipse.sirius.web.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.web.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The root concept of the description of a form representation.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@PublicApi
@Immutable
@GraphQLObjectType
public final class FormDescription implements IRepresentationDescription {
    private UUID id;

    private String label;

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private List<PageDescription> pageDescriptions;

    private List<GroupDescription> groupDescriptions;

    private FormDescription() {
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

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public List<PageDescription> getPageDescriptions() {
        return this.pageDescriptions;
    }

    public List<GroupDescription> getGroupDescriptions() {
        return this.groupDescriptions;
    }

    public static Builder newFormDescription(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * Builder used to create the form description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String label;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private List<PageDescription> pageDescriptions;

        private List<GroupDescription> groupDescriptions;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
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

        public Builder pageDescriptions(List<PageDescription> pageDescriptions) {
            this.pageDescriptions = Objects.requireNonNull(pageDescriptions);
            return this;
        }

        public Builder groupDescriptions(List<GroupDescription> groupDescriptions) {
            this.groupDescriptions = Objects.requireNonNull(groupDescriptions);
            return this;
        }

        public FormDescription build() {
            FormDescription formDescription = new FormDescription();
            formDescription.id = Objects.requireNonNull(this.id);
            formDescription.label = Objects.requireNonNull(this.label);
            formDescription.idProvider = Objects.requireNonNull(this.idProvider);
            formDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            formDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            formDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            formDescription.pageDescriptions = Objects.requireNonNull(this.pageDescriptions);
            formDescription.groupDescriptions = Objects.requireNonNull(this.groupDescriptions);
            return formDescription;
        }
    }
}
