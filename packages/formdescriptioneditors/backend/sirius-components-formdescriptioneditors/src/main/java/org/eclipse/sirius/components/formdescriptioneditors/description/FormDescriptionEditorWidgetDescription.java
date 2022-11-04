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
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The root concept of the description of a form description editor widget.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorWidgetDescription extends AbstractFormDescriptionEditorWidgetDescription {

    private List<StyleProperty> styleProperties;

    private FormDescriptionEditorWidgetDescription() {
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

    public List<StyleProperty> getStyleProperties() {
        return this.styleProperties;
    }

    public static Builder newFormDescriptionEditorWidgetDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind);
    }

    /**
     * Builder used to create the form description editor widget description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String kind;

        private List<StyleProperty> styleProperties = List.of();

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

        public Builder styleProperties(List<StyleProperty> styleProperties) {
            this.styleProperties = List.copyOf(styleProperties);
            return this;
        }

        public FormDescriptionEditorWidgetDescription build() {
            FormDescriptionEditorWidgetDescription formDescriptionEditorDescription = new FormDescriptionEditorWidgetDescription();
            formDescriptionEditorDescription.id = Objects.requireNonNull(this.id);
            formDescriptionEditorDescription.label = Objects.requireNonNull(this.label);
            formDescriptionEditorDescription.kind = Objects.requireNonNull(this.kind);
            formDescriptionEditorDescription.styleProperties = this.styleProperties;
            return formDescriptionEditorDescription;
        }
    }
}
