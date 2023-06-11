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
package org.eclipse.sirius.components.dynamicdialogs;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Root concept of the dynamic dialog.
 *
 * @author lfasani
 */
@Immutable
public final class DynamicDialog {

    private String id;

    private String label;

    private List<DWidget> widgets;

    private String descriptionId;

    private DynamicDialog() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }
    public List<DWidget> getWidgets() {
        return this.widgets;
    }

    public static Builder newDynamicDialog(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, descriptionId: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.descriptionId);
    }

    /**
     * The builder used to create the dynamic dialog.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private String descriptionId;

        private List<DWidget> widgets;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder widgets(List<DWidget> widgets) {
            this.widgets = widgets;
            return this;
        }

        public DynamicDialog build() {
            DynamicDialog form = new DynamicDialog();
            form.id = Objects.requireNonNull(this.id);
            form.label = Objects.requireNonNull(this.label);
            form.descriptionId = Objects.requireNonNull(this.descriptionId);
            form.widgets = Objects.requireNonNull(this.widgets);
            return form;
        }
    }
}
