/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
 * A group of tools.
 *
 * @author mcharfadi
 */
public record ToolSection(String id, String label, String imageURL, List<ITool> tools) {
    public ToolSection {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(imageURL);
        Objects.requireNonNull(tools);
    }

    public static Builder newToolSection(String id) {
        return new Builder(id);
    }

    /**
     * The builder used to create a tool section.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {
        private String id;

        private String label;

        private String imageURL;

        private List<ITool> tools;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder imageURL(String imageURL) {
            this.imageURL = Objects.requireNonNull(imageURL);
            return this;
        }

        public Builder tools(List<ITool> tools) {
            this.tools = Objects.requireNonNull(tools);
            return this;
        }

        public ToolSection build() {
            return new ToolSection(this.id, this.label, this.imageURL, this.tools);
        }

    }
}
