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
package org.eclipse.sirius.components.diagrams;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The layout strategy that position children of a node following a list.
 *
 * @author gcoutable
 */
@Immutable
public final class ListLayoutStrategy implements ILayoutStrategy {
    public static final String KIND = "List"; //$NON-NLS-1$

    private LayoutDirection direction;

    private String kind;

    private ListLayoutStrategy() {
        // Prevent instantiation
    }

    public LayoutDirection getDirection() {
        return this.direction;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    public static Builder newListLayoutStrategy() {
        return new Builder();
    }

    /**
     * The builder used to build a list layout strategy.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private LayoutDirection direction;

        private String kind = KIND;

        private Builder() {
            // Prevent instantiation
        }

        public Builder direction(LayoutDirection direction) {
            this.direction = Objects.requireNonNull(direction);
            return this;
        }

        public ListLayoutStrategy build() {
            ListLayoutStrategy layoutStrategy = new ListLayoutStrategy();
            layoutStrategy.direction = Objects.requireNonNull(this.direction);
            layoutStrategy.kind = Objects.requireNonNull(this.kind);
            return layoutStrategy;
        }
    }
}
