/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
 * A group of tools.
 *
 * @author nvannier
 */
@Immutable
public final class ToolSection {

    private String id;

    private List<ITool> tools;

    private String label;

    private List<String> iconURL;

    private ToolSection() {
        // Prevent instantiation
    }

    public static Builder newToolSection(String id) {
        return new Builder(id);
    }

    public static Builder newToolSection(ToolSection toolSection) {
        return new Builder(toolSection);
    }

    public String getId() {
        return this.id;
    }

    public List<ITool> getTools() {
        return this.tools;
    }

    public String getLabel() {
        return this.label;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }

    /**
     * The builder used to create a tool section.
     *
     * @author nvannier
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private List<String> iconURL;

        private List<ITool> tools;

        private String label;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        private Builder(ToolSection toolSection) {
            this.id = Objects.requireNonNull(toolSection.id);
            this.tools = Objects.requireNonNull(toolSection.tools);
            this.label = Objects.requireNonNull(toolSection.label);
            this.iconURL = Objects.requireNonNull(toolSection.iconURL);
        }

        public Builder tools(List<ITool> tools) {
            this.tools = Objects.requireNonNull(tools);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public ToolSection build() {
            ToolSection toolSection = new ToolSection();
            toolSection.id = Objects.requireNonNull(this.id);
            toolSection.tools = Objects.requireNonNull(this.tools);
            toolSection.label = Objects.requireNonNull(this.label);
            toolSection.iconURL = Objects.requireNonNull(this.iconURL);
            return toolSection;
        }
    }
}
