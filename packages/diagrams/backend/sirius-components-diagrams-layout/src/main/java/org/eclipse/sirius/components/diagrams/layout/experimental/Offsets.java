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
package org.eclipse.sirius.components.diagrams.layout.experimental;

/**
 * Specifies the offsets in all 4 directions. Can be used to represent a margin, border, or padding.
 *
 * @author pcdavid
 */
public record Offsets(double top, double bottom, double left, double right) {

    private static final Offsets EMPTY = Offsets.of(0.0);

    public Offsets(double value) {
        this(value, value, value, value);
    }

    public Offsets(double top, double bottom, double left, double right) {
        this.top = checkNonNegative(top, "top");
        this.bottom = checkNonNegative(bottom, "bottom");
        this.left = checkNonNegative(left, "left");
        this.right = checkNonNegative(right, "right");
    }

    public static Offsets of(double value) {
        return new Offsets(value);
    }

    public static Offsets empty() {
        return EMPTY;
    }

    public double width() {
        return this.left + this.right;
    }

    public double height() {
        return this.top + this.bottom;
    }

    public Offsets combine(Offsets other) {
        return new Offsets(this.top + other.top, this.bottom + other.bottom, this.left + other.left, this.right + other.right);
    }

    public Builder newOffsets() {
        return new Builder();
    }

    private static double checkNonNegative(double value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " can not be negative");
        }
        return value;
    }

    public static class Builder {
        private double top;

        private double bottom;

        private double left;

        private double right;

        public Builder vertical(double value) {
            return this.top(value).bottom(value);
        }

        public Builder horizontal(double value) {
            return this.left(value).right(value);
        }

        public Builder top(double newTop) {
            this.top = checkNonNegative(newTop, "top");
            return this;
        }

        public Builder bottom(double newBottom) {
            this.bottom = checkNonNegative(newBottom, "bottom");
            return this;
        }

        public Builder left(double newLeft) {
            this.left = checkNonNegative(newLeft, "left");
            return this;
        }

        public Builder right(double newRight) {
            this.right = checkNonNegative(newRight, "right");
            return this;
        }

        public Offsets build() {
            return new Offsets(this.top, this.bottom, this.left, this.right);
        }

    }
}
