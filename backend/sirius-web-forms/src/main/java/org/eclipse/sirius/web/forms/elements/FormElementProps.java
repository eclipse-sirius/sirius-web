/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.forms.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IProps;

/**
 * The properties of the form element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class FormElementProps implements IProps {
    public static final String TYPE = "Form"; //$NON-NLS-1$

    private UUID id;

    private String label;

    private String targetObjectId;

    private List<Element> children;

    private FormElementProps() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newFormElementProps(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the form element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String label;

        private String targetObjectId;

        private List<Element> children;

        private Builder(UUID id) {
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

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public FormElementProps build() {
            FormElementProps formElementProps = new FormElementProps();
            formElementProps.id = Objects.requireNonNull(this.id);
            formElementProps.label = Objects.requireNonNull(this.label);
            formElementProps.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            formElementProps.children = Objects.requireNonNull(this.children);
            return formElementProps;
        }
    }
}
