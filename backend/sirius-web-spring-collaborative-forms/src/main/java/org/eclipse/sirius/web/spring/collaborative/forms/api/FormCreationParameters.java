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
package org.eclipse.sirius.web.spring.collaborative.forms.api;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.forms.description.FormDescription;

/**
 * This class is used because creating a form requires sending at once multiple parameters. Since this list of parameter
 * will only grow over time, it will because too cumbersome to maintain and send such a list of parameters without a
 * common "container". This class uses the builder pattern because we know that it will grow bigger and it does not need
 * to be modified.
 *
 * @author hmarchadour
 */
@Immutable
public final class FormCreationParameters {

    private UUID id;

    private Object object;

    private FormDescription formDescription;

    private IEditingContext editingContext;

    private FormCreationParameters() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public Object getObject() {
        return this.object;
    }

    public FormDescription getFormDescription() {
        return this.formDescription;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public static Builder newFormCreationParameters(UUID id) {
        return new Builder(id);
    }

    public static Builder newFormCreationParameters(FormCreationParameters formCreationParameters) {
        // @formatter:off
        return new Builder(formCreationParameters.getId())
                .formDescription(formCreationParameters.getFormDescription())
                .editingContext(formCreationParameters.getEditingContext())
                .object(formCreationParameters.getObject());
        // @formatter:on
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, formDescriptionId: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.formDescription.getId());
    }

    /**
     * The builder of the form creation parameters.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private Object object;

        private FormDescription formDescription;

        private IEditingContext editingContext;

        private Builder(UUID id) {
            this.id = id;
        }

        public Builder object(Object object) {
            this.object = Objects.requireNonNull(object);
            return this;
        }

        public Builder formDescription(FormDescription formDescription) {
            this.formDescription = Objects.requireNonNull(formDescription);
            return this;
        }

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public FormCreationParameters build() {
            FormCreationParameters formCreationParameters = new FormCreationParameters();
            formCreationParameters.id = this.id;
            formCreationParameters.object = Objects.requireNonNull(this.object);
            formCreationParameters.formDescription = Objects.requireNonNull(this.formDescription);
            formCreationParameters.editingContext = Objects.requireNonNull(this.editingContext);
            return formCreationParameters;
        }
    }
}
