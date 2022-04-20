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

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The Textfield concept of the description of a form description editor representation.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorTextfieldDescription extends AbstractFormDescriptionEditorWidgetDescription {

    private FormDescriptionEditorTextfieldDescription() {
        // Prevent instantiation
    }

    public static Builder newFormDescriptionEditorTextfieldDescription(String id) {
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
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public FormDescriptionEditorTextfieldDescription build() {
            FormDescriptionEditorTextfieldDescription formDescriptionEditorTextfieldDescription = new FormDescriptionEditorTextfieldDescription();
            formDescriptionEditorTextfieldDescription.id = Objects.requireNonNull(this.id);
            formDescriptionEditorTextfieldDescription.label = Objects.requireNonNull(this.label);
            return formDescriptionEditorTextfieldDescription;
        }
    }

}
