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
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Aggregates all configuration information which is relevant for the layout of an edge.
 *
 * @author pcdavid
 */
@Immutable
public final class EdgeLayoutConfiguration {
    private String id;

    private String displayName;

    private double width;

    private DiagramLayoutConfiguration parentLayoutConfiguration;

    private Optional<LabelLayoutConfiguration> beginLabelLayoutConfiguration;

    private Optional<LabelLayoutConfiguration> centerLabelLayoutConfiguration;

    private Optional<LabelLayoutConfiguration> endLabelLayoutConfiguration;

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

    public double getWidth() {
        return this.width;
    }

    public Optional<LabelLayoutConfiguration> getBeginLabelLayoutConfiguration() {
        return this.beginLabelLayoutConfiguration;
    }

    public void setStartLabelLayoutConfiguration(Optional<LabelLayoutConfiguration> startLabelLayoutConfiguration) {
        this.beginLabelLayoutConfiguration = startLabelLayoutConfiguration;
    }

    public Optional<LabelLayoutConfiguration> getCenterLabelLayoutConfiguration() {
        return this.centerLabelLayoutConfiguration;
    }

    public void setCenterLabelLayoutConfiguration(Optional<LabelLayoutConfiguration> centerLabelLayoutConfiguration) {
        this.centerLabelLayoutConfiguration = centerLabelLayoutConfiguration;
    }

    public Optional<LabelLayoutConfiguration> getEndLabelLayoutConfiguration() {
        return this.endLabelLayoutConfiguration;
    }

    public void setEndLabelLayoutConfiguration(Optional<LabelLayoutConfiguration> endLabelLayoutConfiguration) {
        this.endLabelLayoutConfiguration = endLabelLayoutConfiguration;
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

        private double width;

        private Optional<LabelLayoutConfiguration> beginLabelLayoutConfiguration = Optional.empty();

        private Optional<LabelLayoutConfiguration> centerLabelLayoutConfiguration = Optional.empty();

        private Optional<LabelLayoutConfiguration> endLabelLayoutConfiguration = Optional.empty();

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
            this.beginLabelLayoutConfiguration = Optional.ofNullable(beginLabelLayoutConfiguration);
            return this;
        }

        public Builder centerLabelLayoutConfiguration(LabelLayoutConfiguration centerLabelLayoutConfiguration) {
            this.centerLabelLayoutConfiguration = Optional.ofNullable(centerLabelLayoutConfiguration);
            return this;
        }

        public Builder endLabelLayoutConfiguration(LabelLayoutConfiguration endLabelLayoutConfiguration) {
            this.endLabelLayoutConfiguration = Optional.ofNullable(endLabelLayoutConfiguration);
            return this;
        }

        public EdgeLayoutConfiguration build() {
            var edgeLayoutConfiguration = new EdgeLayoutConfiguration();
            edgeLayoutConfiguration.id = Objects.requireNonNull(this.id);
            edgeLayoutConfiguration.displayName = Objects.requireNonNull(this.displayName);
            edgeLayoutConfiguration.parentLayoutConfiguration = Objects.requireNonNull(this.parentLayoutConfiguration);
            edgeLayoutConfiguration.width = this.width;
            edgeLayoutConfiguration.beginLabelLayoutConfiguration = Objects.requireNonNull(this.beginLabelLayoutConfiguration);
            edgeLayoutConfiguration.centerLabelLayoutConfiguration = Objects.requireNonNull(this.centerLabelLayoutConfiguration);
            edgeLayoutConfiguration.endLabelLayoutConfiguration = Objects.requireNonNull(this.endLabelLayoutConfiguration);
            return edgeLayoutConfiguration;
        }
    }
}
