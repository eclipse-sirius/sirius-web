/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.validation.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.representations.Element;
import org.eclipse.sirius.web.representations.IProps;

/**
 * The properties of the validation element.
 *
 * @author gcoutable
 */
@Immutable
public final class ValidationElementProps implements IProps {
    public static final String TYPE = "Validation"; //$NON-NLS-1$

    private String id;

    private String label;

    private UUID descriptionId;

    private List<Element> children;

    private ValidationElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newValidationElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder of the validation element props.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private String label;

        private UUID descriptionId;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public ValidationElementProps build() {
            ValidationElementProps validationElementProps = new ValidationElementProps();
            validationElementProps.id = Objects.requireNonNull(this.id);
            validationElementProps.label = Objects.requireNonNull(this.label);
            validationElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            validationElementProps.children = Objects.requireNonNull(this.children);
            return validationElementProps;
        }

    }

}
