/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.selection;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * An object of the selection.
 *
 * @author arichard
 */
@Immutable

public final class SelectionObject {

    private UUID id;

    private String label;

    private List<String> iconURL;

    private SelectionObject() {
        // Prevent instantiation
    }

    public static Builder newSelectionObject(UUID id) {
        return new Builder(id);
    }

    public UUID getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, imageURL: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.iconURL);
    }

    /**
     * The builder used to create the selection object.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private String label;

        private List<String> iconURL;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public SelectionObject build() {
            SelectionObject selectionObject = new SelectionObject();
            selectionObject.id = Objects.requireNonNull(this.id);
            selectionObject.label = Objects.requireNonNull(this.label);
            selectionObject.iconURL = Objects.requireNonNull(this.iconURL);
            return selectionObject;
        }
    }
}
