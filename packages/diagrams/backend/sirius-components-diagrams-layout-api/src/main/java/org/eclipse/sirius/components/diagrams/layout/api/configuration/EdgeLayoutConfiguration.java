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
package org.eclipse.sirius.components.diagrams.layout.api.configuration;

import java.util.Objects;

import org.eclipse.sirius.components.diagrams.layoutdata.EdgeLayoutData;

public final class EdgeLayoutConfiguration {
    private String id;
    private String displayName;
    private DiagramLayoutConfiguration parentLayoutConfiguration;
    private int width;
    private LabelLayoutConfiguration startLabelLayoutConfiguration;
    private LabelLayoutConfiguration centerLabelLayoutConfiguration;
    private LabelLayoutConfiguration endLabelLayoutConfiguration;
    private EdgeLayoutData previousEdgeLayoutData;

    private EdgeLayoutConfiguration() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public DiagramLayoutConfiguration getParentLayoutConfiguration() {
        return this.parentLayoutConfiguration;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    public static Builder newEdgeLayoutConfiguration(String id) {
        return new Builder(id);
    }

    /**
     * Used to create new diagram layout configuration.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;
        private String displayName;

        private DiagramLayoutConfiguration parentLayoutConfiguration;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder displayName(String displayName) {
            this.displayName = Objects.requireNonNull(displayName);
            return this;
        }

        public Builder parentLayoutConfiguration(DiagramLayoutConfiguration parentLayoutConfiguration) {
            this.parentLayoutConfiguration = Objects.requireNonNull(parentLayoutConfiguration);
            return this;
        }

        public EdgeLayoutConfiguration build() {
            var edgeLayoutConfiguration = new EdgeLayoutConfiguration();
            edgeLayoutConfiguration.id = Objects.requireNonNull(this.id);
            edgeLayoutConfiguration.displayName = Objects.requireNonNull(this.displayName);
            edgeLayoutConfiguration.parentLayoutConfiguration = Objects.requireNonNull(this.parentLayoutConfiguration);

            return edgeLayoutConfiguration;
        }
    }
}
