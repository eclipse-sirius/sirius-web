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

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IProps;

/**
 * The properties of the validation element.
 *
 * @author gcoutable
 */
@Immutable
public final class ValidationElementProps implements IProps {
    public static final String TYPE = "Validation"; //$NON-NLS-1$

    private String id;

    private List<Element> children;

    private ValidationElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
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
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder of the validation element props.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public ValidationElementProps build() {
            ValidationElementProps validationElementProps = new ValidationElementProps();
            validationElementProps.id = Objects.requireNonNull(this.id);
            validationElementProps.children = Objects.requireNonNull(this.children);
            return validationElementProps;
        }

    }

}
