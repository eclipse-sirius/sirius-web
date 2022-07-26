/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.sirius.components.diagrams.Ratio;

/**
 * Represents an edge end.
 *
 * @author gcoutable
 */
public final class EdgeEnd {
    private String endId;

    private Ratio endRatio;

    private EdgeEnd() {
        // Prevent instantiation
    }

    public static EdgeEndBuilder newEdgeEnd(EdgeBuilder edgeBuilder, String targetObjectLabel) {
        return new EdgeEndBuilder(edgeBuilder, targetObjectLabel);
    }

    public String getEndId() {
        return this.endId;
    }

    public Ratio getEndRatio() {
        return this.endRatio;
    }

    /**
     * Builder used to build an edge end.
     *
     * @author gcoutable
     */
    public static final class EdgeEndBuilder {
        private EdgeBuilder edgeBuilder;

        private String endId;

        private Ratio endRatio;

        private EdgeEndBuilder(EdgeBuilder edgeBuilder, String targetObjectLabel) {
            this.edgeBuilder = Objects.requireNonNull(edgeBuilder);
            this.endId = targetObjectLabel;
        }

        public EdgeBuilder at(double x, double y) {
            this.endRatio = Ratio.of(x, y);
            return this.edgeBuilder;
        }

        public EdgeEnd build() {
            EdgeEnd edgeEnd = new EdgeEnd();
            edgeEnd.endId = Objects.requireNonNull(this.endId);
            edgeEnd.endRatio = Objects.requireNonNull(this.endRatio);
            return edgeEnd;
        }
    }
}
