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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * Aggregates all configuration information which are relevant for the layout of a diagram.
 *
 * @author pcdavid
 */
@Immutable
public final class DiagramLayoutConfiguration implements IParentLayoutConfiguration {
    private String id;

    private String displayName;

    private List<NodeLayoutConfiguration> childNodeLayoutConfigurations = new ArrayList<>();

    private List<EdgeLayoutConfiguration> edgeLayoutConfigurations = new ArrayList<>();

    private DiagramLayoutConfiguration() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public List<NodeLayoutConfiguration> getChildNodeLayoutConfigurations() {
        return this.childNodeLayoutConfigurations;
    }

    public List<EdgeLayoutConfiguration> getEdgeLayoutConfigurations() {
        return this.edgeLayoutConfigurations;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    public static Builder newDiagramLayoutConfiguration(String id) {
        return new Builder(id);
    }

    /**
     * Used to create diagram layout configurations.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String displayName;

        private List<NodeLayoutConfiguration> childNodeLayoutConfigurations = new ArrayList<>();

        private List<EdgeLayoutConfiguration> edgeLayoutConfigurations = new ArrayList<>();

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder displayName(String displayName) {
            this.displayName = Objects.requireNonNull(displayName);
            return this;
        }

        public Builder childNodeLayoutConfigurations(List<NodeLayoutConfiguration> childNodeLayoutConfigurations) {
            this.childNodeLayoutConfigurations = Objects.requireNonNull(childNodeLayoutConfigurations);
            return this;
        }

        public Builder edgeLayoutConfigurations(List<EdgeLayoutConfiguration> edgeLayoutConfigurations) {
            this.edgeLayoutConfigurations = Objects.requireNonNull(edgeLayoutConfigurations);
            return this;
        }

        public DiagramLayoutConfiguration build() {
            var diagramLayoutConfiguration = new DiagramLayoutConfiguration();
            diagramLayoutConfiguration.id = Objects.requireNonNull(this.id);
            diagramLayoutConfiguration.displayName = Objects.requireNonNull(this.displayName);
            diagramLayoutConfiguration.childNodeLayoutConfigurations = Objects.requireNonNull(this.childNodeLayoutConfigurations);
            diagramLayoutConfiguration.edgeLayoutConfigurations = Objects.requireNonNull(this.edgeLayoutConfigurations);
            return diagramLayoutConfiguration;
        }
    }
}
