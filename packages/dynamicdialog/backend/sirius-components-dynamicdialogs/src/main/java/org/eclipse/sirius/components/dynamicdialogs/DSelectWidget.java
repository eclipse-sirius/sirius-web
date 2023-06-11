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
 * Concept of select widget for dynamic dialog.
 *
 * @author lfasani
 */
@Immutable
public final class DSelectWidget extends DWidget {

    private DSelectWidget() {
        // Prevent instantiation
    }

    public static Builder newDSelectWidget(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, descriptionId: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.descriptionId);
    }

    /**
     * The builder used to create the select widget.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String descriptionId;

        private String parentId;

        private String label;

        private String outputVariableName;

        private List<String> inputVariableNames;

        private Boolean required;

        private String initialValue;

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

        public Builder parentId(String parentId) {
            this.parentId = Objects.requireNonNull(parentId);
            return this;
        }

        public Builder outputVariableName(String outputVariableName) {
            this.outputVariableName = Objects.requireNonNull(outputVariableName);
            return this;
        }

        public Builder initialValue(String initialValue) {
            this.initialValue = Objects.requireNonNull(initialValue);
            return this;
        }

        public Builder inputVariableNames(List<String> inputVariableNames) {
            this.inputVariableNames = Objects.requireNonNull(inputVariableNames);
            return this;
        }

        public Builder required(Boolean required) {
            this.required = Objects.requireNonNull(required);
            return this;
        }

        public DSelectWidget build() {
            DSelectWidget selectWidget = new DSelectWidget();
            selectWidget.id = Objects.requireNonNull(this.id);
            selectWidget.descriptionId = Objects.requireNonNull(this.descriptionId);
            selectWidget.parentId = Objects.requireNonNull(this.parentId);
            selectWidget.label = Objects.requireNonNull(this.label);
            selectWidget.outputVariableName = Objects.requireNonNull(this.outputVariableName);
            selectWidget.inputVariableNames = Objects.requireNonNull(this.inputVariableNames);
            selectWidget.required = this.required;
            selectWidget.initialValue = this.initialValue;
            return selectWidget;
        }
    }
}
