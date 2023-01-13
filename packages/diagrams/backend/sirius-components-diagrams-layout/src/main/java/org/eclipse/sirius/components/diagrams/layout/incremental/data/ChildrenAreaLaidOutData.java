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
package org.eclipse.sirius.components.diagrams.layout.incremental.data;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.layout.incremental.ILayoutStrategyEngine;
import org.eclipse.sirius.components.diagrams.layout.incremental.INodeIncrementalLayoutEngine;

/**
 * Returns by {@link ILayoutStrategyEngine#getChildrenAreaLayoutData}
 *
 *
 * The data structure used to represent the area of children in a node after it has been laid out.
 *
 * <p>
 * This is an internal data structure meant to be returned by {@link ILayoutStrategyEngine} to the
 * {@link INodeIncrementalLayoutEngine}.
 * </p>
 *
 * @author gcoutable
 */
@Immutable
public final class ChildrenAreaLaidOutData {

    private Position deltaPosition;

    private Size size;

    private Map<String, ChildLayoutData> nodeIdToChildLayoutData;

    private ChildrenAreaLaidOutData() {
        // Prevent instantiation
    }

    public Position getDeltaPosition() {
        return this.deltaPosition;
    }

    public Size getSize() {
        return this.size;
    }

    public Map<String, ChildLayoutData> getNodeIdToChildLayoutData() {
        return this.nodeIdToChildLayoutData;
    }

    public static Builder newChildrenAreaLaidOutData() {
        return new Builder();
    }

    /**
     * The builder used to create a children area laid out data.
     *
     * @author gcoutable
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private Position deltaPosition;

        private Size size;

        private Map<String, ChildLayoutData> nodeIdToChildLayoutData;

        public Builder deltaPosition(Position deltalPosition) {
            this.deltaPosition = Objects.requireNonNull(deltalPosition);
            return this;
        }

        public Builder size(Size size) {
            this.size = Objects.requireNonNull(size);
            return this;
        }

        public Builder nodeIdToChildLayoutData(Map<String, ChildLayoutData> nodeIdToChildLayoutData) {
            this.nodeIdToChildLayoutData = Objects.requireNonNull(nodeIdToChildLayoutData);
            return this;
        }

        public ChildrenAreaLaidOutData build() {
            ChildrenAreaLaidOutData childrenAreaLayoutData = new ChildrenAreaLaidOutData();
            childrenAreaLayoutData.deltaPosition = Objects.requireNonNull(this.deltaPosition);
            childrenAreaLayoutData.size = Objects.requireNonNull(this.size);
            childrenAreaLayoutData.nodeIdToChildLayoutData = Objects.requireNonNull(this.nodeIdToChildLayoutData);
            return childrenAreaLayoutData;
        }
    }
}
