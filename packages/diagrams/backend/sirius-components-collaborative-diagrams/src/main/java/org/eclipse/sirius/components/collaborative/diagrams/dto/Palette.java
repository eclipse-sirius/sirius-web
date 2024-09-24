/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.List;
import java.util.Objects;

/**
 * A palette, contains direct tools and toolSections.
 *
 * @author frouene
 */
public record Palette(String id, List<ITool> quickAccessTools, List<IPaletteEntry> paletteEntries) {

    public Palette {
        Objects.requireNonNull(id);
        Objects.requireNonNull(quickAccessTools);
        Objects.requireNonNull(paletteEntries);
    }

    public static Builder newPalette(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a pallette.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private final String id;

        private List<ITool> quickAccessTools;

        private List<IPaletteEntry> paletteEntries;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder quickAccessTools(List<ITool> quickAccessTools) {
            this.quickAccessTools = Objects.requireNonNull(quickAccessTools);
            return this;
        }

        public Builder paletteEntries(List<IPaletteEntry> paletteEntries) {
            this.paletteEntries = Objects.requireNonNull(paletteEntries);
            return this;
        }

        public Palette build() {
            return new Palette(this.id, this.quickAccessTools, this.paletteEntries);
        }
    }

}
