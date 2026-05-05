/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
 *
 * @technical-debt The id of the palette should be deleted since it does not provide any value. The only feature relying
 * on it is the retrieval of the last tool invoked by the frontend. It doesn't even really need this identifier since
 * it is only returning a piece of data sent by the frontend. This feature could use exclusively pieces of data already
 * known to the frontend.
 *
 * See <a href="https://github.com/eclipse-sirius/sirius-web/issues/6470">GitHub Issue</a>
 */
public record Palette(@Deprecated(forRemoval = true) String id, List<ITool> quickAccessTools, List<IPaletteEntry> paletteEntries) {

    public Palette {
        Objects.requireNonNull(id);
        Objects.requireNonNull(quickAccessTools);
        Objects.requireNonNull(paletteEntries);
    }

    public static Builder newPalette(@Deprecated(forRemoval = true) String id) {
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
