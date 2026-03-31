/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
 * A layout configuration, contains the identifier, the label, the icon URLs and the layout options.
 *
 * @author ocailleau
 */

public record LayoutConfiguration(String id, String label, List<String> iconURL, List<LayoutOptionEntry> layoutOptions) {

    public LayoutConfiguration {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(iconURL);
        Objects.requireNonNull(layoutOptions);
    }

    public static Builder newLayoutConfiguration(String id) {
        return new Builder(id);
    }
    /**
     * The builder used to create a layoutConfiguration.
     *
     * @author ocailleau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private final String id;

        private String label;

        private List<String> iconURL;

        private List<LayoutOptionEntry> layoutOptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public  Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder iconURL(List<String> iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder layoutOptions(List<LayoutOptionEntry> layoutOptions) {
            this.layoutOptions = Objects.requireNonNull(layoutOptions);
            return this;
        }

        public LayoutConfiguration build() {
            return new LayoutConfiguration(this.id, this.label, this.iconURL, this.layoutOptions);
        }
    }

}
