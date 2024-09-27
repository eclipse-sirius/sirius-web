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

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the column element.
 *
 * @author lfasani
 */
public record ColumnElementProps(UUID id, UUID descriptionId, String label, String targetObjectId, String targetObjectKind)  implements IProps {

    public static final String TYPE = "Column";

    public ColumnElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
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
        private UUID id;

        private UUID descriptionId;

        private String targetObjectId;

        private String targetObjectKind;

        private String label;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
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

        public ColumnElementProps build() {
            ColumnElementProps columnElementProps = new ColumnElementProps(this.id, this.descriptionId, this.label, this.targetObjectId, this.targetObjectKind);
            return columnElementProps;
        }
    }
}
