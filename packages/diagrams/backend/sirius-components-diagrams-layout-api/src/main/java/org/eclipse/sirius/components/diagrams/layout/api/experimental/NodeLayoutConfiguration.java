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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Aggregates all configuration information which is relevant for the layout of a node.
 *
 * @author pcdavid
 */
public record NodeLayoutConfiguration(
        String id,
        String displayName,
        IParentLayoutConfiguration parentLayoutConfiguration,
        Optional<LabelLayoutConfiguration> optionalLabelLayoutConfiguration,
        List<NodeLayoutConfiguration> childNodeLayoutConfigurations,
        List<NodeLayoutConfiguration> borderNodeLayoutConfigurations,
        Offsets border,
        Offsets padding,
        Offsets margin,
        Size minimumSize,
        NodeLayoutStrategy layoutStrategy
) implements IParentLayoutConfiguration {

    public NodeLayoutConfiguration {
        Objects.requireNonNull(id);
        Objects.requireNonNull(displayName);
        Objects.requireNonNull(parentLayoutConfiguration);
        Objects.requireNonNull(optionalLabelLayoutConfiguration);
        Objects.requireNonNull(childNodeLayoutConfigurations);
        Objects.requireNonNull(borderNodeLayoutConfigurations);
        Objects.requireNonNull(border);
        Objects.requireNonNull(padding);
        Objects.requireNonNull(margin);
        Objects.requireNonNull(minimumSize);
        Objects.requireNonNull(layoutStrategy);
    }

    public static Builder newNodeLayoutConfiguration(String id) {
        return new Builder(id);
    }

    /**
     * Used to create new node layout configuration.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    @org.eclipse.sirius.components.annotations.Builder
    public static final class Builder {

        private String id;

        private String displayName;

        private IParentLayoutConfiguration parentLayoutConfiguration;

        private LabelLayoutConfiguration labelLayoutConfiguration;

        private List<NodeLayoutConfiguration> childNodeLayoutConfigurations = new ArrayList<>();
        private List<NodeLayoutConfiguration> borderNodeLayoutConfigurations = new ArrayList<>();

        private Offsets border;

        private Offsets padding;

        private Offsets margin;

        private Size minimumSize;

        private NodeLayoutStrategy layoutStrategy;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder displayName(String displayName) {
            this.displayName = Objects.requireNonNull(displayName);
            return this;
        }

        public Builder parentLayoutConfiguration(IParentLayoutConfiguration parentLayoutConfiguration) {
            this.parentLayoutConfiguration = Objects.requireNonNull(parentLayoutConfiguration);
            return this;
        }

        public Builder labelLayoutConfiguration(LabelLayoutConfiguration labelLayoutConfiguration) {
            this.labelLayoutConfiguration = Objects.requireNonNull(labelLayoutConfiguration);
            return this;
        }

        public Builder childNodeLayoutConfigurations(List<NodeLayoutConfiguration> childNodeLayoutConfigurations) {
            this.childNodeLayoutConfigurations = Objects.requireNonNull(childNodeLayoutConfigurations);
            return this;
        }

        public Builder borderNodeLayoutConfigurations(List<NodeLayoutConfiguration> borderNodeLayoutConfigurations) {
            this.borderNodeLayoutConfigurations = Objects.requireNonNull(borderNodeLayoutConfigurations);
            return this;
        }

        public Builder border(Offsets border) {
            this.border = Objects.requireNonNull(border);
            return this;
        }

        public Builder padding(Offsets padding) {
            this.padding = Objects.requireNonNull(padding);
            return this;
        }

        public Builder margin(Offsets margin) {
            this.margin = Objects.requireNonNull(margin);
            return this;
        }

        public Builder minimumSize(Size minimumSize) {
            this.minimumSize = Objects.requireNonNull(minimumSize);
            return this;
        }

        public Builder layoutStrategy(NodeLayoutStrategy layoutStrategy) {
            this.layoutStrategy = Objects.requireNonNull(layoutStrategy);
            return this;
        }

        public NodeLayoutConfiguration build() {
            return new NodeLayoutConfiguration(
                    this.id,
                    this.displayName,
                    this.parentLayoutConfiguration,
                    Optional.ofNullable(this.labelLayoutConfiguration),
                    this.childNodeLayoutConfigurations,
                    this.borderNodeLayoutConfigurations,
                    this.border,
                    this.padding,
                    this.margin,
                    this.minimumSize,
                    this.layoutStrategy
            );
        }
    }
}
