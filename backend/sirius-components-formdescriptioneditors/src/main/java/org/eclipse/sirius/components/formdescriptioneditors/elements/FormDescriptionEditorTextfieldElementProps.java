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
package org.eclipse.sirius.components.formdescriptioneditors.elements;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the form description editor Textfield element.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorTextfieldElementProps implements IProps {
    public static final String TYPE = "Textfield"; //$NON-NLS-1$

    private String id;

    private String kind;

    private String label;

    private FormDescriptionEditorTextfieldElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getKind() {
        return this.kind;
    }

    public String getLabel() {
        return this.label;
    }

    public static Builder newFormDescriptionEditorTextfieldElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, kind: {2}, label: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.kind, this.label);
    }

    /**
     * The builder of the form description editor Textfield element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind = TYPE;

        private String label;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public FormDescriptionEditorTextfieldElementProps build() {
            FormDescriptionEditorTextfieldElementProps formDescriptionEditorElementProps = new FormDescriptionEditorTextfieldElementProps();
            formDescriptionEditorElementProps.id = Objects.requireNonNull(this.id);
            formDescriptionEditorElementProps.kind = Objects.requireNonNull(this.kind);
            formDescriptionEditorElementProps.label = Objects.requireNonNull(this.label);
            return formDescriptionEditorElementProps;
        }
    }
}
