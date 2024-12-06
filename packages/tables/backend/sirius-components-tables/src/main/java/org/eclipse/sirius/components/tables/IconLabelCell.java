/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
 * IconLabel-based Cell concept of the table representation.
 *
 * @author frouene
 */
@Immutable
public final class IconLabelCell implements ICell {

    public static final String TYPE = "ICON_LABEL";

    private UUID id;

    private String targetObjectId;

    private String targetObjectKind;

    private UUID columnId;

    private String value;

    private List<String> iconURLs;

    private IconLabelCell() {
        // Prevent instantiation
    }

    public String getValue() {
        return this.value;
    }

    public List<String> getIconURLs() {
        return this.iconURLs;
    }

    public static Builder newIconLabelCell(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, columnId: {2}, value: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.columnId, this.value);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    @Override
    public String getTargetObjectKind() {
        return this.targetObjectKind;
    }

    @Override
    public UUID getColumnId() {
        return this.columnId;
    }

    /**
     * The builder used to create a cell.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private String targetObjectId;

        private String targetObjectKind;

        private UUID columnId;

        private String value;

        private List<String> iconURLs;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
            return this;
        }

        public Builder columnId(UUID columnId) {
            this.columnId = Objects.requireNonNull(columnId);
            return this;
        }

        public Builder value(String value) {
            this.value = Objects.requireNonNull(value);
            return this;
        }

        public Builder iconURLs(List<String> iconURLs) {
            this.iconURLs = Objects.requireNonNull(iconURLs);
            return this;
        }

        public IconLabelCell build() {
            IconLabelCell cell = new IconLabelCell();
            cell.id = Objects.requireNonNull(this.id);
            cell.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            cell.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            cell.columnId = Objects.requireNonNull(this.columnId);
            cell.value = Objects.requireNonNull(this.value);
            cell.iconURLs = Objects.requireNonNull(this.iconURLs);
            return cell;
        }
    }
}
