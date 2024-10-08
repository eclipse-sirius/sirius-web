/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the diagram element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class DiagramElementProps implements IProps {
    public static final String TYPE = "Diagram";

    private String id;

    private String targetObjectId;

    private String descriptionId;

    private List<Element> children;

    private DiagramElementProps() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getTargetObjectId() {
        return this.targetObjectId;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

    public static Builder newDiagramElementProps(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass(), this.id);
    }

    /**
     * The builder of the diagram element props.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String targetObjectId;

        private String descriptionId;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
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

        public DiagramElementProps build() {
            DiagramElementProps diagramElementProps = new DiagramElementProps();
            diagramElementProps.id = Objects.requireNonNull(this.id);
            diagramElementProps.targetObjectId = Objects.requireNonNull(this.targetObjectId);
            diagramElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            diagramElementProps.children = Objects.requireNonNull(this.children);
            return diagramElementProps;
        }
    }
}
