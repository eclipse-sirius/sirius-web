/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.builder.edge;

import java.util.Objects;

/**
 * Represents an edge end.
 *
 * @author gcoutable
 */
public final class EdgeEnd {
    private String endId;

    private EdgeEnd() {
        // Prevent instantiation
    }

    public static EdgeEndBuilder newEdgeEnd(EdgeBuilder edgeBuilder, String targetObjectLabel) {
        return new EdgeEndBuilder(edgeBuilder, targetObjectLabel);
    }

    public String getEndId() {
        return this.endId;
    }

    /**
     * Builder used to build an edge end.
     *
     * @author gcoutable
     */
    public static final class EdgeEndBuilder {
        private EdgeBuilder edgeBuilder;

        private String endId;

        private EdgeEndBuilder(EdgeBuilder edgeBuilder, String targetObjectLabel) {
            this.edgeBuilder = Objects.requireNonNull(edgeBuilder);
            this.endId = targetObjectLabel;
        }

        public EdgeEnd build() {
            EdgeEnd edgeEnd = new EdgeEnd();
            edgeEnd.endId = Objects.requireNonNull(this.endId);
            return edgeEnd;
        }
    }
}
