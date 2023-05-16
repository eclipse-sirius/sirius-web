/*******************************************************************************
 * Copyright (c) 2023, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.api.experimental;

import java.util.Objects;
import java.util.Optional;

/**
 * Aggregates all configuration information which is relevant for the layout of an edge.
 *
 * @author pcdavid
 */
public record EdgeLayoutConfiguration(
        String id,
        String displayName,
        double width,
        DiagramLayoutConfiguration parentLayoutConfiguration,
        Optional<LabelLayoutConfiguration> optionalBeginLabelLayoutConfiguration,
        Optional<LabelLayoutConfiguration> optionalCenterLabelLayoutConfiguration,
        Optional<LabelLayoutConfiguration> optionalEndLabelLayoutConfiguration
) {

    public EdgeLayoutConfiguration {
        Objects.requireNonNull(id);
        Objects.requireNonNull(displayName);
        Objects.requireNonNull(parentLayoutConfiguration);
        Objects.requireNonNull(optionalBeginLabelLayoutConfiguration);
        Objects.requireNonNull(optionalCenterLabelLayoutConfiguration);
        Objects.requireNonNull(optionalEndLabelLayoutConfiguration);
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
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {
        private String id;

        private String displayName;

        private DiagramLayoutConfiguration parentLayoutConfiguration;

        private double width;

        private LabelLayoutConfiguration beginLabelLayoutConfiguration;

        private LabelLayoutConfiguration centerLabelLayoutConfiguration;

        private LabelLayoutConfiguration endLabelLayoutConfiguration;

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

        public Builder width(double width) {
            this.width = width;
            return this;
        }

        public Builder beginLabelLayoutConfiguration(LabelLayoutConfiguration beginLabelLayoutConfiguration) {
            this.beginLabelLayoutConfiguration = Objects.requireNonNull(beginLabelLayoutConfiguration);
            return this;
        }

        public Builder centerLabelLayoutConfiguration(LabelLayoutConfiguration centerLabelLayoutConfiguration) {
            this.centerLabelLayoutConfiguration = Objects.requireNonNull(centerLabelLayoutConfiguration);
            return this;
        }

        public Builder endLabelLayoutConfiguration(LabelLayoutConfiguration endLabelLayoutConfiguration) {
            this.endLabelLayoutConfiguration = Objects.requireNonNull(endLabelLayoutConfiguration);
            return this;
        }

        public EdgeLayoutConfiguration build() {
            return new EdgeLayoutConfiguration(
                    this.id,
                    this.displayName,
                    this.width,
                    this.parentLayoutConfiguration,
                    Optional.ofNullable(this.beginLabelLayoutConfiguration),
                    Optional.ofNullable(this.centerLabelLayoutConfiguration),
                    Optional.ofNullable(this.endLabelLayoutConfiguration)
            );
        }
    }
}
