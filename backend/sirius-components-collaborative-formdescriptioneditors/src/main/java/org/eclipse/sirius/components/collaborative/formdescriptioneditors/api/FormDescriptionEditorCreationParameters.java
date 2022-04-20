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
package org.eclipse.sirius.components.collaborative.formdescriptioneditors.api;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.formdescriptioneditors.description.FormDescriptionEditorDescription;

/**
 * Container class for FormDescriptionEditor creation parameters.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorCreationParameters {

    private String id;

    private FormDescriptionEditorDescription formDescriptionEditorDescription;

    private IEditingContext editingContext;

    private Object targetObject;

    private FormDescriptionEditorCreationParameters() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public FormDescriptionEditorDescription getFormDescriptionEditorDescription() {
        return this.formDescriptionEditorDescription;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public static Builder newFormDescriptionEditorCreationParameters(String id) {
        return new Builder(id);
    }

    public static Builder newFormDescriptionEditorCreationParameters(FormDescriptionEditorCreationParameters formDescriptionEditorCreationParameters) {
        // @formatter:off
        return new Builder(formDescriptionEditorCreationParameters.getId())
                .formDescriptionEditorDescription(formDescriptionEditorCreationParameters.getFormDescriptionEditorDescription())
                .editingContext(formDescriptionEditorCreationParameters.getEditingContext())
                .targetObject(formDescriptionEditorCreationParameters.getTargetObject());
        // @formatter:on
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, formDescriptionEditorDescriptionId: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.formDescriptionEditorDescription.getId());
    }

    /**
     * The builder of the form description editor creation parameters.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private FormDescriptionEditorDescription formDescriptionEditorDescription;

        private IEditingContext editingContext;

        private Object targetObject;

        private Builder(String id) {
            this.id = id;
        }

        public Builder formDescriptionEditorDescription(FormDescriptionEditorDescription formDescriptionEditorDescription) {
            this.formDescriptionEditorDescription = Objects.requireNonNull(formDescriptionEditorDescription);
            return this;
        }

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public Builder targetObject(Object targetObject) {
            this.targetObject = Objects.requireNonNull(targetObject);
            return this;
        }

        public FormDescriptionEditorCreationParameters build() {
            FormDescriptionEditorCreationParameters formDescriptionEditorCreationParameters = new FormDescriptionEditorCreationParameters();
            formDescriptionEditorCreationParameters.id = this.id;
            formDescriptionEditorCreationParameters.formDescriptionEditorDescription = Objects.requireNonNull(this.formDescriptionEditorDescription);
            formDescriptionEditorCreationParameters.editingContext = Objects.requireNonNull(this.editingContext);
            formDescriptionEditorCreationParameters.targetObject = Objects.requireNonNull(this.targetObject);
            return formDescriptionEditorCreationParameters;
        }
    }
}
