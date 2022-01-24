/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.web.diagrams;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;

/**
 * The position ratio of an element.
 *
 * @author gcoutable
 */
@Immutable
public final class Ratio {
    public static final Ratio UNDEFINED = Ratio.of(-1, -1);

    private double x;

    private double y;

    private Ratio() {
        // Prevent instatiation
    }

    public static Ratio of(double x, double y) {
        Ratio ratio = new Ratio();
        ratio.x = x;
        ratio.y = y;
        return ratio;
    }

    public static Builder newRatio() {
        return new Builder();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Ratio) {
            return this.getX() == ((Ratio) obj).getX() && this.getY() == ((Ratio) obj).getY();
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getX(), this.getY());
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'x: {1}, y: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.x, this.y);
    }

    /**
     * The builder used to create a new ratio.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private double x;

        private double y;

        private Builder() {
            // Prevent instantiation
        }

        public Builder x(double x) {
            this.x = x;
            return this;
        }

        public Builder y(double y) {
            this.y = y;
            return this;
        }

        public Ratio build() {
            Ratio ratio = new Ratio();
            ratio.x = this.x;
            ratio.y = this.y;
            return ratio;
        }
    }
}
