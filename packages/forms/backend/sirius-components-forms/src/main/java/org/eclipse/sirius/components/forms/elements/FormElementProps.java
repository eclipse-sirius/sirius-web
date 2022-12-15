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
package org.eclipse.sirius.components.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * The properties of the form element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class FormElementProps implements IProps {
    public static final String TYPE = "Form";

    private String id;

    private String label;

    private String targetObjectId;

    private String descriptionId;

    private List<Element> children;

    private FormElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newFormElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the form element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String targetObjectId;

        private String descriptionId;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public FormElementProps build() {
            FormElementProps formElementProps = new FormElementProps();
            formElementProps.id = Objects.requireNonNull(this.id);
            formElementProps.label = Objects.requireNonNull(this.label);
            formElementProps.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            formElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            formElementProps.children = Objects.requireNonNull(this.children);
            return formElementProps;
        }
    }
}
