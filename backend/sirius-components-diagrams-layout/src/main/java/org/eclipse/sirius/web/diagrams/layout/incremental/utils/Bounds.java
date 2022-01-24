/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental.utils;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;

/**
 * The bounds (Size and Position) of an element.
 *
 * @author fbarbin
 */
@Immutable
public final class Bounds {

    private Size size;

    private Position position;

    private Bounds() {
        // Prevent instantiation
    }

    public Size getSize() {
        return this.size;
    }

    public Position getPosition() {
        return this.position;
    }

    public static Builder newBounds() {
        return new Builder();
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'size: {1}, position: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.size, this.position);
    }

    /**
     * The builder used to create a new bounds.
     *
     * @author fbarbin
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private Size size;

        private Position position;

        private Builder() {
            // Prevent instantiation
        }

        public Builder size(Size size) {
            this.size = Objects.requireNonNull(size);
            return this;
        }

        public Builder position(Position position) {
            this.position = Objects.requireNonNull(position);
            return this;
        }

        public Bounds build() {
            Bounds bounds = new Bounds();
            bounds.size = Objects.requireNonNull(this.size);
            bounds.position = Objects.requireNonNull(this.position);
            return bounds;
        }
    }
}
