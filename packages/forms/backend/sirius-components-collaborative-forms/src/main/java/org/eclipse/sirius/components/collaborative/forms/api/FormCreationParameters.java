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
package org.eclipse.sirius.components.collaborative.forms.api;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.forms.description.FormDescription;

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

    private String id;

    private List<Object> objects;

    private FormDescription formDescription;

    private IEditingContext editingContext;

    private FormCreationParameters() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public List<Object> getObjects() {
        return this.objects;
    }

    public FormDescription getFormDescription() {
        return this.formDescription;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public static Builder newFormCreationParameters(String id) {
        return new Builder(id);
    }

    public static Builder newFormCreationParameters(FormCreationParameters formCreationParameters) {
        // @formatter:off
        return new Builder(formCreationParameters.getId())
                .formDescription(formCreationParameters.getFormDescription())
                .editingContext(formCreationParameters.getEditingContext())
                .objects(formCreationParameters.getObjects());
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
        private String id;

        private List<Object> objects;

        private FormDescription formDescription;

        private IEditingContext editingContext;

        private Builder(String id) {
            this.id = id;
        }

        public Builder objects(List<Object> objects) {
            this.objects = Objects.requireNonNull(objects);
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
            formCreationParameters.objects = Objects.requireNonNull(this.objects);
            formCreationParameters.formDescription = Objects.requireNonNull(this.formDescription);
            formCreationParameters.editingContext = Objects.requireNonNull(this.editingContext);
            return formCreationParameters;
        }
    }
}
