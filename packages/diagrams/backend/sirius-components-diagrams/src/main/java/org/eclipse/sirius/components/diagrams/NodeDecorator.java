/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * A node decorator.
 *
 * @author gdaniel
 */
@Immutable
public final class NodeDecorator {

    private String label;

    private NodeDecoratorPosition position;

    private String iconURL;

    private NodeDecorator(String label, NodeDecoratorPosition position, String iconURL) {
        this.label = Objects.requireNonNull(label);
        this.position = Objects.requireNonNull(position);
        this.iconURL = Objects.requireNonNull(iconURL);
    }

    public String getLabel() {
        return this.label;
    }

    public NodeDecoratorPosition getPosition() {
        return this.position;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public static Builder newNodeDecorator() {
        return new Builder();
    }

    /**
     * The builder used to create a node decorator.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String label;

        private NodeDecoratorPosition position;

        private String iconURL;

        private Builder() {
            // Prevent instantiation
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder position(NodeDecoratorPosition position) {
            this.position = Objects.requireNonNull(position);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public NodeDecorator build() {
            return new NodeDecorator(this.label, this.position, this.iconURL);
        }
    }
}
