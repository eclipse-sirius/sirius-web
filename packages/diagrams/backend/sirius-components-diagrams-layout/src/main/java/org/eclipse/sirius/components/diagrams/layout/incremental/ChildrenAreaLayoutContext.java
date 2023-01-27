/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildLayoutData;

/**
 * The context used to compute a node children area size.
 *
 * @author gcoutable
 */
@Immutable
public final class ChildrenAreaLayoutContext {

    private String parentId;

    private Size parentSize;

    private Size parentMinimalSize;

    private boolean parentResizedByUser;

    private List<ChildLayoutData> childrenLayoutData;

    private double nodeMargin;

    private Position absolutePosition;

    private boolean parentBeingCreated;

    private boolean parentBeingExpanded;

    private ILayoutStrategy childrenLayoutStrategy;

    private Optional<Double> optionalChildrenAreaWidth;

    private Optional<IDiagramEvent> optionalDiagramEvent;

    private ChildrenAreaLayoutContext() {
        // Prevent instantiation
    }

    public String getParentId() {
        return this.parentId;
    }

    public Size getParentSize() {
        return this.parentSize;
    }

    public Size getParentMinimalSize() {
        return this.parentMinimalSize;
    }

    public boolean isParentResizedByUser() {
        return this.parentResizedByUser;
    }

    public List<ChildLayoutData> getChildrenLayoutData() {
        return this.childrenLayoutData;
    }

    public double getNodeMargin() {
        return this.nodeMargin;
    }

    public Position getAbsolutePosition() {
        return this.absolutePosition;
    }

    public boolean isParentBeingCreated() {
        return this.parentBeingCreated;
    }

    public boolean isParentBeingExpanded() {
        return this.parentBeingExpanded;
    }

    public ILayoutStrategy getChildrenLayoutStrategy() {
        return this.childrenLayoutStrategy;
    }

    public Optional<Double> getOptionalChildrenAreaWidth() {
        return this.optionalChildrenAreaWidth;
    }

    public Optional<IDiagramEvent> getOptionalDiagramEvent() {
        return this.optionalDiagramEvent;
    }

    public static Builder newChildrenAreaLayoutContext(String parentId) {
        return new Builder(parentId);
    }

    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String parentId;

        private Size parentSize;

        private Size parentMinimalSize;

        private boolean parentResizedByUser;

        private List<ChildLayoutData> childrenLayoutData;

        private double nodeMargin;

        private Position absolutePosition;

        private boolean parentBeingCreated;

        private boolean parentBeingExpanded;

        private ILayoutStrategy childrenLayoutStrategy;

        private Optional<Double> optionalChildrenAreaWidth = Optional.empty();

        private Optional<IDiagramEvent> optionalDiagramEvent = Optional.empty();

        private Builder(String parentId) {
            this.parentId = Objects.requireNonNull(parentId);
        }

        public Builder parentSize(Size parentSize) {
            this.parentSize = Objects.requireNonNull(parentSize);
            return this;
        }

        public Builder parentMinimalSize(Size parentMinimalSize) {
            this.parentMinimalSize = Objects.requireNonNull(parentMinimalSize);
            return this;
        }

        public Builder parentResizedByUser(boolean parentResizedByUser) {
            this.parentResizedByUser = parentResizedByUser;
            return this;
        }

        public Builder childrenLayoutData(List<ChildLayoutData> childrenLayoutData) {
            this.childrenLayoutData = Objects.requireNonNull(childrenLayoutData);
            return this;
        }

        public Builder nodeMargin(double nodeMargin) {
            this.nodeMargin = nodeMargin;
            return this;
        }

        public Builder absolutePosition(Position absolutePosition) {
            this.absolutePosition = Objects.requireNonNull(absolutePosition);
            return this;
        }

        public Builder parentBeingCreated(boolean parentBeingCreated) {
            this.parentBeingCreated = parentBeingCreated;
            return this;
        }

        public Builder parentBeingExpanded(boolean parentBeingExpanded) {
            this.parentBeingExpanded = parentBeingExpanded;
            return this;
        }

        public Builder childrenLayoutStrategy(ILayoutStrategy childrenLayoutStrategy) {
            this.childrenLayoutStrategy = Objects.requireNonNull(childrenLayoutStrategy);
            return this;
        }

        public Builder optionalChildrenAreaWidth(Double optionalChildrenAreaWidth) {
            this.optionalChildrenAreaWidth = Optional.of(optionalChildrenAreaWidth);
            return this;
        }

        public Builder optionalDiagramEvent(IDiagramEvent optionalDiagramEvent) {
            this.optionalDiagramEvent = Optional.of(optionalDiagramEvent);
            return this;
        }

        public ChildrenAreaLayoutContext build() {
            ChildrenAreaLayoutContext childrenAreaLayoutContext = new ChildrenAreaLayoutContext();
            childrenAreaLayoutContext.parentId = Objects.requireNonNull(this.parentId);
            childrenAreaLayoutContext.parentSize = Objects.requireNonNull(this.parentSize);
            childrenAreaLayoutContext.parentMinimalSize = Objects.requireNonNull(this.parentMinimalSize);
            childrenAreaLayoutContext.parentResizedByUser = this.parentResizedByUser;
            childrenAreaLayoutContext.childrenLayoutData = Objects.requireNonNull(this.childrenLayoutData);
            childrenAreaLayoutContext.nodeMargin = this.nodeMargin;
            childrenAreaLayoutContext.absolutePosition = Objects.requireNonNull(this.absolutePosition);
            childrenAreaLayoutContext.parentBeingCreated = this.parentBeingCreated;
            childrenAreaLayoutContext.parentBeingExpanded = this.parentBeingExpanded;
            childrenAreaLayoutContext.childrenLayoutStrategy = Objects.requireNonNull(this.childrenLayoutStrategy);
            childrenAreaLayoutContext.optionalChildrenAreaWidth = this.optionalChildrenAreaWidth;
            childrenAreaLayoutContext.optionalDiagramEvent = this.optionalDiagramEvent;
            return childrenAreaLayoutContext;
        }

    }

}
