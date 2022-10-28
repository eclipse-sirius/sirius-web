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
 * The root concept of the description of a form description editor widget.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorToolbarActionDescription extends AbstractFormDescriptionEditorWidgetDescription {

    private FormDescriptionEditorToolbarActionDescription() {
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

    @Override
    public String getKind() {
        return this.kind;
    }

    public static Builder newFormDescriptionEditorToolbarActionDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind);
    }

    /**
     * Builder used to create the form description editor toolbar action description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String kind;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public FormDescriptionEditorToolbarActionDescription build() {
            FormDescriptionEditorToolbarActionDescription formDescriptionEditorToolbarActionDescription = new FormDescriptionEditorToolbarActionDescription();
            formDescriptionEditorToolbarActionDescription.id = Objects.requireNonNull(this.id);
            formDescriptionEditorToolbarActionDescription.label = Objects.requireNonNull(this.label);
            formDescriptionEditorToolbarActionDescription.kind = Objects.requireNonNull(this.kind);
            return formDescriptionEditorToolbarActionDescription;
        }
    }
}
