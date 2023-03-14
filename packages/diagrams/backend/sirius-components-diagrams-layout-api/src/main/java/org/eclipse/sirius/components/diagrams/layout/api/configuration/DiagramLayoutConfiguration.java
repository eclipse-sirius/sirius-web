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

import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;

public final class DiagramLayoutConfiguration implements IParentLayoutConfiguration {
    private String id;

    private String displayName;
    private List<NodeLayoutConfiguration> childNodeLayoutConfigurations = new ArrayList<>();
    private List<EdgeLayoutConfiguration> edgeLayoutConfigurations = new ArrayList<>();
    private DiagramLayoutData previousDiagramLayoutData;

    private DiagramLayoutConfiguration() {
        // Prevent instantiation
    }

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

    public DiagramLayoutData getPreviousDiagramLayoutData() {
        return this.previousDiagramLayoutData;
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

        private DiagramLayoutData previousDiagramLayoutData;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder displayName(String displayName) {
            this.displayName = Objects.requireNonNull(this.displayName);
            return this;
        }

        public Builder previousDiagramLayoutData(DiagramLayoutData previousDiagramLayoutData) {
            this.previousDiagramLayoutData = Objects.requireNonNull(previousDiagramLayoutData);
            return this;
        }

        public DiagramLayoutConfiguration build() {
            var diagramLayoutConfiguration = new DiagramLayoutConfiguration();
            diagramLayoutConfiguration.id = Objects.requireNonNull(this.id);
            diagramLayoutConfiguration.displayName = Objects.requireNonNull(this.displayName);
            diagramLayoutConfiguration.previousDiagramLayoutData = Objects.requireNonNull(this.previousDiagramLayoutData);

            return diagramLayoutConfiguration;
        }
    }
}
