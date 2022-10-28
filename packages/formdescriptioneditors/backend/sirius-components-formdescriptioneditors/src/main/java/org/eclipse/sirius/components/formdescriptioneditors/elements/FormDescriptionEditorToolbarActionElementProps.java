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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the form description editor toolbar action element.
 *
 * @author arichard
 */
@Immutable
public final class FormDescriptionEditorToolbarActionElementProps implements IProps {
    public static final String TYPE = "FormDescriptionEditorToolbarAction"; //$NON-NLS-1$

    private String id;

    private String label;

    private String kind;

    private FormDescriptionEditorToolbarActionElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    @Override
    public List<Element> getChildren() {
        return new ArrayList<>();
    }

    public static Builder newFormDescriptionEditorToolbarActionElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the form description editor toolbar action element props.
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

        public FormDescriptionEditorToolbarActionElementProps build() {
            FormDescriptionEditorToolbarActionElementProps toolbarActionElementProps = new FormDescriptionEditorToolbarActionElementProps();
            toolbarActionElementProps.id = Objects.requireNonNull(this.id);
            toolbarActionElementProps.label = Objects.requireNonNull(this.label);
            toolbarActionElementProps.kind = Objects.requireNonNull(this.kind);
            return toolbarActionElementProps;
        }
    }
}
