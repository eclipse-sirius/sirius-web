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

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the line element.
 *
 * @author arichard
 */
@Immutable
public final class LineElementProps implements IProps {

    public static final String TYPE = "Line"; //$NON-NLS-1$

    private UUID id;

    private String targetObjectId;

    private UUID descriptionId;

    private List<Element> children;

    private LineElementProps() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newLineElementProps(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, targetObjectId: {2}, descriptionId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.targetObjectId, this.descriptionId);
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

        private UUID descriptionId;

        private List<Element> children;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
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
            LineElementProps lineElementProps = new LineElementProps();
            lineElementProps.id = Objects.requireNonNull(this.id);
            lineElementProps.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            lineElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            lineElementProps.children = Objects.requireNonNull(this.children);
            return lineElementProps;
        }
    }
}
