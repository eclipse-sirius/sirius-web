/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.tables;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Column concept of the table representation.
 *
 * @author arichard
 * @author lfasani
 */
@Immutable
public final class Column {

    private UUID id;

    private String targetObjectId;

    private String targetObjectKind;

    private String descriptionId;

    private String headerLabel;

    private List<String> headerIconURLs;

    private String headerIndexLabel;

    private int width;

    private boolean resizable;

    private boolean hidden;

    private Column() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getHeaderLabel() {
        return this.headerLabel;
    }

    public List<String> getHeaderIconURLs() {
        return this.headerIconURLs;
    }

    public String getHeaderIndexLabel() {
        return this.headerIndexLabel;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isResizable() {
        return this.resizable;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public static Builder newColumn(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.headerLabel);
    }

    /**
     * The builder used to create a column.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private String descriptionId;

        private String targetObjectId;

        private String targetObjectKind;

        private String headerLabel;

        private List<String> headerIconURLs;

        private String headerIndexLabel;

        private int width;

        private boolean resizable;

        private boolean hidden;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
            return this;
        }

        public Builder headerLabel(String headerLabel) {
            this.headerLabel = Objects.requireNonNull(headerLabel);
            return this;
        }

        public Builder headerIconURLs(List<String> headerIconURLs) {
            this.headerIconURLs = Objects.requireNonNull(headerIconURLs);
            return this;
        }

        public Builder headerIndexLabel(String headerIndexLabel) {
            this.headerIndexLabel = Objects.requireNonNull(headerIndexLabel);
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder resizable(boolean resizable) {
            this.resizable = resizable;
            return this;
        }

        public Builder hidden(boolean visible) {
            this.hidden = visible;
            return this;
        }

        public Column build() {
            Column column = new Column();
            column.id = Objects.requireNonNull(this.id);
            column.descriptionId = Objects.requireNonNull(this.descriptionId);
            column.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            column.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            column.headerLabel = Objects.requireNonNull(this.headerLabel);
            column.headerIconURLs = Objects.requireNonNull(this.headerIconURLs);
            column.headerIndexLabel = Objects.requireNonNull(this.headerIndexLabel);
            column.width = this.width;
            column.resizable = this.resizable;
            column.hidden = this.hidden;
            return column;
        }
    }
}
