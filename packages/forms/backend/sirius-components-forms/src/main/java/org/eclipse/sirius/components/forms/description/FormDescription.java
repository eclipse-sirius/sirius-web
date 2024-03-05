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
package org.eclipse.sirius.components.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a form representation.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@PublicApi
@Immutable
public final class FormDescription implements IRepresentationDescription {

    private String id;

    private String label;

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private List<PageDescription> pageDescriptions;

    private FormDescription() {
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

    public static Builder newFormDescription(String id) {
        return new Builder(id);
    }

    public static Builder newFormDescription(FormDescription formDescription) {
        return new Builder(formDescription);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * Builder used to create the form description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private List<PageDescription> pageDescriptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(FormDescription formDescription) {
            this.id = formDescription.getId();
            this.label = formDescription.getLabel();
            this.idProvider = formDescription.getIdProvider();
            this.labelProvider = formDescription.getLabelProvider();
            this.targetObjectIdProvider = formDescription.getTargetObjectIdProvider();
            this.canCreatePredicate = formDescription.getCanCreatePredicate();
            this.pageDescriptions = formDescription.getPageDescriptions();
        }

        public Builder id(String id) {
            this.id = Objects.requireNonNull(id);
            return this;
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

        public FormDescription build() {
            FormDescription formDescription = new FormDescription();
            formDescription.id = Objects.requireNonNull(this.id);
            formDescription.label = Objects.requireNonNull(this.label);
            formDescription.idProvider = Objects.requireNonNull(this.idProvider);
            formDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            formDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            formDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            formDescription.pageDescriptions = Objects.requireNonNull(this.pageDescriptions);
            return formDescription;
        }
    }
}
