/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors.description;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.annotations.PublicApi;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The root concept of the description of a form description editor representation.
 *
 * @author arichard
 */
@PublicApi
@Immutable
public final class FormDescriptionEditorDescription implements IRepresentationDescription {

    private String id;

    private String label;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Predicate<VariableManager> canCreatePredicate;

    private FormDescriptionEditorDescription() {
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

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    @Override
    public Predicate<VariableManager> getCanCreatePredicate() {
        return this.canCreatePredicate;
    }

    public static Builder newFormDescriptionEditorDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * Builder used to create the form description editor description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Predicate<VariableManager> canCreatePredicate;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
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

        public FormDescriptionEditorDescription build() {
            FormDescriptionEditorDescription formDescriptionEditorDescription = new FormDescriptionEditorDescription();
            formDescriptionEditorDescription.id = Objects.requireNonNull(this.id);
            formDescriptionEditorDescription.label = Objects.requireNonNull(this.label);
            formDescriptionEditorDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            formDescriptionEditorDescription.canCreatePredicate = Objects.requireNonNull(this.canCreatePredicate);
            return formDescriptionEditorDescription;
        }
    }
}
