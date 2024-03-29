/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.formdescriptioneditors.FormDescriptionEditorIf;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties for the {@link FormDescriptionEditorIf} element.
 *
 * @author pcdavid
 */
@Immutable
public final class FormDescriptionEditorIfElementProps implements IProps {
    public static final String TYPE = "FormDescriptionEditorIf";

    private String id;

    private String label;

    private String iconURL;

    private List<Element> children;

    private FormDescriptionEditorIfElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newFormDescriptionEditorIfElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the FormDescriptionEditorIf element props.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private String iconURL;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public FormDescriptionEditorIfElementProps build() {
            FormDescriptionEditorIfElementProps formDescriptionEditorIfElementProps = new FormDescriptionEditorIfElementProps();
            formDescriptionEditorIfElementProps.id = Objects.requireNonNull(this.id);
            formDescriptionEditorIfElementProps.label = Objects.requireNonNull(this.label);
            formDescriptionEditorIfElementProps.iconURL = this.iconURL;
            formDescriptionEditorIfElementProps.children = Objects.requireNonNull(this.children);
            return formDescriptionEditorIfElementProps;
        }
    }
}
