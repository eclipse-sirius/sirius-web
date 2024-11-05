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
package org.eclipse.sirius.components.tables.elements;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the column element.
 *
 * @author lfasani
 */
public record ColumnElementProps(
        UUID id,
        UUID descriptionId,
        String headerLabel,
        List<String> headerIconURLs,
        String headerIndexLabel,
        String targetObjectId,
        String targetObjectKind, int width, boolean resizable) implements IProps {

    public static final String TYPE = "Column";

    public ColumnElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(headerLabel);
        Objects.requireNonNull(headerIconURLs);
        Objects.requireNonNull(headerIndexLabel);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
    }

    public static Builder newColumnElementProps(UUID id) {
        return new Builder(id);
    }

    /**
     * The builder of the column element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final UUID id;

        private UUID descriptionId;

        private String targetObjectId;

        private String targetObjectKind;

        private String headerLabel;

        private List<String> headerIconURLs;

        private String headerIndexLabel;

        private int width;

        private boolean resizable;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
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

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder targetObjectKind(String targetObjectKind) {
            this.targetObjectKind = Objects.requireNonNull(targetObjectKind);
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

        public ColumnElementProps build() {
            return new ColumnElementProps(this.id, this.descriptionId, this.headerLabel, this.headerIconURLs, this.headerIndexLabel, this.targetObjectId, this.targetObjectKind, this.width,
                    this.resizable);
        }
    }
}
