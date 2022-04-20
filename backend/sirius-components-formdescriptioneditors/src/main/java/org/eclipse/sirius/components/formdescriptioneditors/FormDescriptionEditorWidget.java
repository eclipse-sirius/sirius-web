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
package org.eclipse.sirius.components.formdescriptioneditors;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Any widget contained in the form description editor representation.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorWidget implements IFormDescriptionEditorWidget {

    private String id;

    private String kind;

    private String label;

    private FormDescriptionEditorWidget() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public static Builder newFormDescriptionEditorWidget(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, kind: {2}, label: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.kind, this.label);
    }

    /**
     * The builder used to create the form description editor widget.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String kind;

        private String label;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public FormDescriptionEditorWidget build() {
            FormDescriptionEditorWidget formDescriptionEditorWidget = new FormDescriptionEditorWidget();
            formDescriptionEditorWidget.id = Objects.requireNonNull(this.id);
            formDescriptionEditorWidget.kind = Objects.requireNonNull(this.kind);
            formDescriptionEditorWidget.label = Objects.requireNonNull(this.label);
            return formDescriptionEditorWidget;
        }
    }
}
