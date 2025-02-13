/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
public record LineElementProps(
        UUID id,
        String descriptionId,
        String targetObjectId,
        String targetObjectKind,
        List<Element> children,
        String headerLabel,
        List<String> headerIconURLs,
        String headerIndexLabel,
        int height,
        boolean resizable,
        int depthLevel) implements IProps {

    public static final String TYPE = "Line";

    public LineElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(children);
        Objects.requireNonNull(headerLabel);
        Objects.requireNonNull(headerIconURLs);
        Objects.requireNonNull(headerIndexLabel);
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
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

        private final UUID id;

        private String targetObjectId;

        private String targetObjectKind;

        private String descriptionId;

        private List<Element> children;

        private String headerLabel;

        private List<String> headerIconURLs;

        private String headerIndexLabel;

        private int height;

        private boolean resizable;

        private int depthLevel;

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

        public Builder children(List<Element> children) {
            this.children = Objects.requireNonNull(children);
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

        public Builder depthLevel(int depthLevel) {
            this.depthLevel = depthLevel;
            return this;
        }


        public LineElementProps build() {
            return new LineElementProps(this.id, this.descriptionId, this.targetObjectId, this.targetObjectKind, this.children, this.headerLabel, this.headerIconURLs, this.headerIndexLabel,
                    this.height, this.resizable, this.depthLevel);
        }
    }
}
