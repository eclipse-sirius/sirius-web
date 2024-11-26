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
 * Line concept of the table representation.
 *
 * @author arichard
 */
@Immutable
public final class Line {

    private UUID id;

    private String targetObjectId;

    private String targetObjectKind;

    private String descriptionId;

    private List<ICell> cells;

    private String headerLabel;

    private List<String> headerIconURLs;

    private String headerIndexLabel;

    private int height;

    private boolean resizable;

    private Line() {
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

    public List<ICell> getCells() {
        return this.cells;
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

    public int getHeight() {
        return this.height;
    }

    public boolean isResizable() {
        return this.resizable;
    }

    public static Builder newLine(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, descriptionId: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.descriptionId);
    }

    /**
     * The builder used to create a line.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private String targetObjectId;

        private String targetObjectKind;

        private String descriptionId;

        private List<ICell> cells;

        private String headerLabel;

        private List<String> headerIconURLs;

        private String headerIndexLabel;

        private int height;

        private boolean resizable;

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

        public Builder descriptionId(String descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder cells(List<ICell> cells) {
            this.cells = Objects.requireNonNull(cells);
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

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder resizable(boolean resizable) {
            this.resizable = resizable;
            return this;
        }

        public Line build() {
            Line line = new Line();
            line.id = Objects.requireNonNull(this.id);
            line.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            line.targetObjectKind = Objects.requireNonNull(this.targetObjectKind);
            line.descriptionId = Objects.requireNonNull(this.descriptionId);
            line.cells = Objects.requireNonNull(this.cells);
            line.headerLabel = Objects.requireNonNull(this.headerLabel);
            line.headerIconURLs = Objects.requireNonNull(this.headerIconURLs);
            line.headerIndexLabel = Objects.requireNonNull(this.headerIndexLabel);
            line.height = this.height;
            line.resizable = this.resizable;
            return line;
        }
    }
}
