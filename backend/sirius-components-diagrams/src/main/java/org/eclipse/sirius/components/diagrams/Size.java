/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The size of an element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class Size {
    public static final Size UNDEFINED = Size.of(-1, -1);

    private double width;

    private double height;

    private Size() {
        // Prevent instantiation
    }

    public static Size of(double width, double height) {
        Size size = new Size();
        size.width = width;
        size.height = height;
        return size;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Size) {
            return this.getHeight() == ((Size) obj).getHeight() && this.getWidth() == ((Size) obj).getWidth();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getHeight(), this.getWidth());
    }

    public static Builder newSize() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'width: {1}, height: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.width, this.height);
    }

    /**
     * The builder used to create a new size.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private double width;

        private double height;

        private Builder() {
            // Prevent instantiation
        }

        public Builder width(double width) {
            this.width = width;
            return this;
        }

        public Builder height(double height) {
            this.height = height;
            return this;
        }

        public Size build() {
            Size size = new Size();
            size.width = this.width;
            size.height = this.height;
            return size;
        }
    }
}
