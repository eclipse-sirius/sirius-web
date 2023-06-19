/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.forms;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The style of the borders for a container.
 *
 * @author frouene
 */
@Immutable
public final class ContainerBorderStyle {

    private String color;

    private int radius;

    private int size;

    private ContainerBorderLineStyle lineStyle;

    private ContainerBorderStyle() {
        // Prevent instantiation
    }

    public String getColor() {
        return this.color;
    }

    public int getRadius() {
        return this.radius;
    }

    public int getSize() {
        return this.size;
    }

    public ContainerBorderLineStyle getLineStyle() {
        return this.lineStyle;
    }

    public static Builder newContainerBorderStyle() {
        return new Builder();
    }

    /**
     * Builder used to create the Container border style.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String color;

        private int radius;

        private int size;

        private ContainerBorderLineStyle lineStyle;

        private Builder() {
        }

        public Builder color(String color) {
            this.color = Objects.requireNonNull(color);
            return this;
        }

        public Builder radius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder lineStyle(ContainerBorderLineStyle lineStyle) {
            this.lineStyle = Objects.requireNonNull(lineStyle);
            return this;
        }

        public ContainerBorderStyle build() {
            ContainerBorderStyle borderStyle = new ContainerBorderStyle();
            borderStyle.color = this.color; // Optional on purpose
            borderStyle.radius = this.radius;
            borderStyle.size = this.size;
            borderStyle.lineStyle = Objects.requireNonNull(this.lineStyle);
            return borderStyle;
        }

    }
}
