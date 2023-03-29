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
import org.eclipse.sirius.components.diagrams.layout.api.NodeLayoutStrategy;
import org.eclipse.sirius.components.diagrams.layout.api.Offsets;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Aggregates all configuration information which is relevant for the layout of a node.
 *
 * @author pcdavid
 */
@Immutable
public final class NodeLayoutConfiguration implements IParentLayoutConfiguration {
    private String id;

    private String displayName;

    private IParentLayoutConfiguration parentLayoutConfiguration;

    private LabelLayoutConfiguration labelLayoutConfiguration;

    private List<NodeLayoutConfiguration> childNodeLayoutConfigurations = new ArrayList<>();

    private List<NodeLayoutConfiguration> borderNodeLayoutConfigurations = new ArrayList<>();

    private boolean withHeader;

    private boolean labelInside;

    private Offsets border;

    private Offsets padding;

    private Offsets margin;

    private Size minimumSize;

    private NodeLayoutStrategy layoutStrategy;

    private NodeLayoutConfiguration() {
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

    public IParentLayoutConfiguration getParentLayoutConfiguration() {
        return this.parentLayoutConfiguration;
    }

    public LabelLayoutConfiguration getLabelLayoutConfiguration() {
        return this.labelLayoutConfiguration;
    }

    @Override
    public List<NodeLayoutConfiguration> getChildNodeLayoutConfigurations() {
        return this.childNodeLayoutConfigurations;
    }

    public List<NodeLayoutConfiguration> getBorderNodeLayoutConfigurations() {
        return this.borderNodeLayoutConfigurations;
    }

    public boolean isWithHeader() {
        return this.withHeader;
    }

    public boolean isLabelInside() {
        return this.labelInside;
    }

    public Offsets getBorder() {
        return this.border;
    }

    public Offsets getPadding() {
        return this.padding;
    }

    public Offsets getMargin() {
        return this.margin;
    }

    public NodeLayoutStrategy getLayoutStrategy() {
        return this.layoutStrategy;
    }

    public Size getMinimumSize() {
        return this.minimumSize;
    }

    @Override
    public String toString() {
        return this.displayName;
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
    public static final class Builder {

        private String id;

        private String displayName;

        private IParentLayoutConfiguration parentLayoutConfiguration;

        private LabelLayoutConfiguration labelLayoutConfiguration;

        private boolean withHeader;

        private boolean labelInside = true;

        private Offsets border = Offsets.of(1.0);

        private Offsets padding = Offsets.of(12.0);

        private Offsets margin = Offsets.of(25.0);

        private Size minimumSize = new Size(150, 70);

        private NodeLayoutStrategy layoutStrategy = NodeLayoutStrategy.FREEFORM;

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

        public Builder withHeader(boolean withHeader) {
            this.withHeader = withHeader;
            return this;
        }

        public Builder labelInside(boolean labelInside) {
            this.labelInside = labelInside;
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
            var nodeLayoutConfiguration = new NodeLayoutConfiguration();
            nodeLayoutConfiguration.id = Objects.requireNonNull(this.id);
            nodeLayoutConfiguration.displayName = Objects.requireNonNull(this.displayName);
            nodeLayoutConfiguration.parentLayoutConfiguration = Objects.requireNonNull(this.parentLayoutConfiguration);
            nodeLayoutConfiguration.labelLayoutConfiguration = Objects.requireNonNull(this.labelLayoutConfiguration);
            nodeLayoutConfiguration.withHeader = this.withHeader;
            nodeLayoutConfiguration.labelInside = this.labelInside;
            nodeLayoutConfiguration.border = Objects.requireNonNull(this.border);
            nodeLayoutConfiguration.padding = Objects.requireNonNull(this.padding);
            nodeLayoutConfiguration.margin = Objects.requireNonNull(this.margin);
            nodeLayoutConfiguration.minimumSize = Objects.requireNonNull(this.minimumSize);
            nodeLayoutConfiguration.layoutStrategy = Objects.requireNonNull(this.layoutStrategy);
            return nodeLayoutConfiguration;
        }
    }
}
