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
 * A named style property for a widget.
 *
 * @author pcdavid
 */
@Immutable
public final class StyleProperty {
    private String name;

    private String value;

    public StyleProperty() {
        // Prevent instantiation
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public static Builder newStyleProperty() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'name: {1}, value: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.name, this.value);
    }

    /**
     * Builder used to create a style property.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String name;

        private String value;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = Objects.requireNonNull(name);
            return this;
        }

        public Builder value(String value) {
            this.value = Objects.requireNonNull(value);
            return this;
        }

        public StyleProperty build() {
            StyleProperty property = new StyleProperty();
            property.name = Objects.requireNonNull(this.name);
            property.value = Objects.requireNonNull(this.value);
            return property;
        }
    }
}
