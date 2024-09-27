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

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the line element.
 *
 * @author lfasani
 */
public record LineElementProps(UUID id, UUID descriptionId, String targetObjectId, String targetObjectKind, List<Element> children) implements IProps {

    public static final String TYPE = "Line";

    public LineElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(children);
    }

    @Override
    public List<Element> getChildren() {
        return children;
    }

    public static Builder newLineElementProps(UUID id) {
        return new Builder(id);
    }

    /**
     * The builder of the line element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private String targetObjectId;

        private String targetObjectKind;

        private UUID descriptionId;

        private List<Element> children;

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

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
            return this;
        }

        public LineElementProps build() {
            LineElementProps lineElementProps = new LineElementProps(this.id, this.descriptionId, this.targetObjectId, this.targetObjectKind, this.children);
            return lineElementProps;
        }
    }
}
