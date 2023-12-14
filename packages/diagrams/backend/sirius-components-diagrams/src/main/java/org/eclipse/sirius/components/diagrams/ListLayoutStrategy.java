/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The layout strategy that position children of a node following a list.
 *
 * @author gcoutable
 */
@Immutable
public final class ListLayoutStrategy implements ILayoutStrategy {

    public static final String KIND = "List";
    private boolean areChildNodesDraggable;

    private ListLayoutStrategy() {
        // Prevent instantiation
    }

    public static Builder newListLayoutStrategy() {
        return new Builder();
    }

    @Override
    public String getKind() {
        return KIND;
    }

    public boolean isAreChildNodesDraggable() {
        return this.areChildNodesDraggable;
    }

    /**
     * The builder used to create a listLayoutStrategy.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private boolean areChildNodesDraggable = true;

        private Builder() {
            // Prevent instantiation
        }

        public Builder areChildNodesDraggable(boolean areChildNodesDraggable) {
            this.areChildNodesDraggable = areChildNodesDraggable;
            return this;
        }

        public ListLayoutStrategy build() {
            ListLayoutStrategy listLayoutStrategy = new ListLayoutStrategy();
            listLayoutStrategy.areChildNodesDraggable = this.areChildNodesDraggable;
            return listLayoutStrategy;
        }
    }
}
