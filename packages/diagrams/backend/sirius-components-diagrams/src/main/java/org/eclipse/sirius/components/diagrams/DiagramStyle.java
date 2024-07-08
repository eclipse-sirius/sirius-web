/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
 * A diagram style.
 *
 * @author frouene
 */
@Immutable
public final class DiagramStyle {

    private String background;

    private DiagramStyle() {
        // prevent initialisation
    }

    public static Builder newDiagramStyle() {
        return new Builder();
    }

    public String getBackground() {
        return this.background;
    }

    /**
     * The builder used to create the diagram style.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String background = "transparent";

        public Builder background(String background) {
            this.background = Objects.requireNonNull(background);
            return this;
        }

        public DiagramStyle build() {
            DiagramStyle diagramStyle = new DiagramStyle();
            diagramStyle.background = Objects.requireNonNull(this.background);
            return diagramStyle;
        }

    }
}
