/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.tools;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * A group of tools and tool sections.
 *
 * @technical-debt This class and its related concepts, such as {@link ITool} or {@link ToolSection} should be deleted.
 * Those concepts have no reason to exists in the code related to the rendering of the diagram since they should be
 * requested dynamically. Specifiers can indeed need to make tools available conditionally. Concepts such as these
 * surrounding the diagram but not strictly used during the rendering should be removed from sirius-components-diagrams.
 * These classes are not to be improved or connected to additional pieces of code.
 *
 * @author frouene
 * @since v2023.10.0
 */
@Immutable
public final class Palette {

    private String id;

    private List<ITool> tools;

    private List<ToolSection> toolSections;


    private Palette() {
        // Prevent instantiation
    }

    public static Builder newPalette(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public List<ITool> getTools() {
        return this.tools;
    }

    public List<ToolSection> getToolSections() {
        return this.toolSections;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create a palette.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private List<ITool> tools;

        private List<ToolSection> toolSections;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(Palette palette) {
            this.id = Objects.requireNonNull(palette.id);
            this.tools = Objects.requireNonNull(palette.tools);
            this.toolSections = Objects.requireNonNull(palette.toolSections);
        }

        public Builder tools(List<ITool> tools) {
            this.tools = Objects.requireNonNull(tools);
            return this;
        }

        public Builder toolSections(List<ToolSection> toolSections) {
            this.toolSections = Objects.requireNonNull(toolSections);
            return this;
        }

        public Palette build() {
            Palette palette = new Palette();
            palette.id = Objects.requireNonNull(this.id);
            palette.tools = Objects.requireNonNull(this.tools);
            palette.toolSections = Objects.requireNonNull(this.toolSections);
            return palette;
        }
    }
}
