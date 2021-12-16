/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.elements;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.components.IProps;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;

/**
 * Properties of the diagram element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class DiagramElementProps implements IProps {
    public static final String TYPE = "Diagram"; //$NON-NLS-1$

    private String id;

    private String targetObjectId;

    private Position position;

    private Size size;

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

    public Position getPosition() {
        return this.position;
    }

    public Size getSize() {
        return this.size;
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
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
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

        private Position position;

        private Size size;

        private List<Element> children;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectId(String targetObjectId) {
            this.targetObjectId = Objects.requireNonNull(targetObjectId);
            return this;
        }

        public Builder position(Position position) {
            this.position = Objects.requireNonNull(position);
            return this;
        }

        public Builder size(Size size) {
            this.size = Objects.requireNonNull(size);
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
            diagramElementProps.position = Objects.requireNonNull(this.position);
            diagramElementProps.size = Objects.requireNonNull(this.size);
            diagramElementProps.children = Objects.requireNonNull(this.children);
            return diagramElementProps;
        }
    }
}
