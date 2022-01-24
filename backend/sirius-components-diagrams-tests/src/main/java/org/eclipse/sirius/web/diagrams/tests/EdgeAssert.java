/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.AbstractAssert;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.EdgeStyle;
import org.eclipse.sirius.web.diagrams.Position;

/**
 * Custom assertion class used to perform some tests on an edge.
 *
 * @author sbegaudeau
 */
public class EdgeAssert extends AbstractAssert<EdgeAssert, Edge> {

    public EdgeAssert(Edge edge) {
        super(edge, EdgeAssert.class);
    }

    public EdgeAssert matches(Edge edge, IdPolicy idPolicy, LayoutPolicy layoutPolicy) {
        this.isNotNull();

        if (idPolicy == IdPolicy.WITH_ID) {
            assertThat(this.actual.getId()).isEqualTo(edge.getId());
            assertThat(this.actual.getSourceId()).isEqualTo(edge.getSourceId());
            assertThat(this.actual.getTargetId()).isEqualTo(edge.getTargetId());
        }

        assertThat(this.actual.getType()).isEqualTo(edge.getType());
        assertThat(this.actual.getTargetObjectId()).isEqualTo(edge.getTargetObjectId());
        assertThat(this.actual.getDescriptionId()).isEqualTo(edge.getDescriptionId());

        EdgeStyle actualStyle = this.actual.getStyle();
        EdgeStyle style = edge.getStyle();

        assertThat(actualStyle.getSize()).isEqualTo(style.getSize());
        assertThat(actualStyle.getLineStyle()).isEqualTo(style.getLineStyle());
        assertThat(actualStyle.getSourceArrow()).isEqualTo(style.getSourceArrow());
        assertThat(actualStyle.getTargetArrow()).isEqualTo(style.getTargetArrow());
        assertThat(actualStyle.getColor()).isEqualTo(style.getColor());

        if (layoutPolicy == LayoutPolicy.WITH_LAYOUT) {
            assertThat(this.actual.getRoutingPoints()).hasSize(edge.getRoutingPoints().size());
            int size = this.actual.getRoutingPoints().size();
            for (int i = 0; i < size; i++) {
                Position actualRoutingPoint = this.actual.getRoutingPoints().get(i);
                Position routingPoint = edge.getRoutingPoints().get(i);

                if (actualRoutingPoint == null) {
                    this.failWithMessage("Expected routing point to be <'{'x: %.2f, y: %.2f'}'> but was null", routingPoint.getX(), routingPoint.getY()); //$NON-NLS-1$
                } else {
                    if (actualRoutingPoint.getX() != routingPoint.getX()) {
                        this.failWithMessage("Expected routing point's x to be <%.2f> but was <%.2f>", actualRoutingPoint.getX(), routingPoint.getX()); //$NON-NLS-1$
                    }
                    if (actualRoutingPoint.getY() != routingPoint.getY()) {
                        this.failWithMessage("Expected routing point's y to be <%.2f> but was <%.2f>", actualRoutingPoint.getY(), routingPoint.getY()); //$NON-NLS-1$
                    }
                }
            }
        }

        return this;
    }

}
