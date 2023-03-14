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

import org.eclipse.sirius.components.diagrams.layout.api.Border;
import org.eclipse.sirius.components.diagrams.layout.api.Margin;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
public final class NodeLayoutConfiguration implements IParentLayoutConfiguration {
    private String id;
    private String displayName;
    private IParentLayoutConfiguration parentLayoutConfiguration;
    private Size requestedSize;
    private Border border;
    private Margin margin;
    private NodeLabelPosition labelPosition;
    private LabelLayoutConfiguration labelLayoutConfiguration;
    private List<NodeLayoutConfiguration> childNodeLayoutConfigurations = new ArrayList<>();
    private List<NodeLayoutConfiguration> borderNodeLayoutConfigurations = new ArrayList<>();
    private NodeLayoutData previousNodeLayoutData;

    private NodeLayoutConfiguration() {
        // Prevent instantiation
    }

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

    public Size getRequestedSize() {
        return this.requestedSize;
    }

    public Border getBorder() {
        return this.border;
    }

    public Margin getMargin() {
        return this.margin;
    }

    public NodeLabelPosition getLabelPosition() {
        return this.labelPosition;
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

    public NodeLayoutData getPreviousNodeLayoutData() {
        return this.previousNodeLayoutData;
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
        private Size requestedSize;
        private Border border;
        private Margin margin;
        private NodeLabelPosition labelPosition;
        private LabelLayoutConfiguration labelLayoutConfiguration;
        private NodeLayoutData previousNodeLayoutData;

        private IParentLayoutConfiguration parentLayoutConfiguration;

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

        public Builder requestedSize(Size requestedSize) {
            this.requestedSize = Objects.requireNonNull(requestedSize);
            return this;
        }

        public Builder border(Border border) {
            this.border = Objects.requireNonNull(border);
            return this;
        }

        public Builder margin(Margin margin) {
            this.margin = Objects.requireNonNull(margin);
            return this;
        }

        public Builder labelPosition(NodeLabelPosition nodeLabelPosition) {
            this.labelPosition = Objects.requireNonNull(nodeLabelPosition);
            return this;
        }

        public Builder labelLayoutConfiguration(LabelLayoutConfiguration labelLayoutConfiguration) {
            this.labelLayoutConfiguration = Objects.requireNonNull(labelLayoutConfiguration);
            return this;
        }

        public Builder previousNodeLayoutData(NodeLayoutData previousNodeLayoutData) {
            this.previousNodeLayoutData = Objects.requireNonNull(previousNodeLayoutData);
            return this;
        }

        public NodeLayoutConfiguration build() {
            var nodeLayoutConfiguration = new NodeLayoutConfiguration();
            nodeLayoutConfiguration.id = Objects.requireNonNull(id);
            nodeLayoutConfiguration.parentLayoutConfiguration = Objects.requireNonNull(this.parentLayoutConfiguration);
            nodeLayoutConfiguration.requestedSize = Objects.requireNonNull(this.requestedSize);

            return nodeLayoutConfiguration;
        }
    }
}
