/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The select option.
 *
 * @author lfasani
 */
@Immutable
public final class SelectOption {
    private String id;

    private String label;

    private String iconURL;

    private SelectOption() {
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

    public static Builder newSelectOption(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}', iconUrl: {3}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.iconURL);
    }

    /**
     * The builder used to create the select option.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String iconURL;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = iconURL;
            return this;
        }

        public SelectOption build() {
            SelectOption selectOption = new SelectOption();
            selectOption.id = Objects.requireNonNull(this.id);
            selectOption.label = Objects.requireNonNull(this.label);
            selectOption.iconURL = this.iconURL;
            return selectOption;
        }
    }
}
